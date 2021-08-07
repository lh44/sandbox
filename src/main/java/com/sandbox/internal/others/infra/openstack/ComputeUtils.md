```java
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.net.HostAndPort;
import com.jcraft.jsch.agentproxy.AgentProxyException;
import com.jcraft.jsch.agentproxy.Connector;
import com.jcraft.jsch.agentproxy.ConnectorFactory;
import com.musigma.die.domain.Instance;
import com.musigma.die.domain.InstanceStatus;
import com.musigma.die.domain.OpenstackAccountDetails;
import com.musigma.die.domain.UserAccount;
import com.musigma.die.model.AccountQuota;
import com.musigma.die.model.CreateInstance;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jclouds.ContextBuilder;
import org.jclouds.collect.IterableWithMarker;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.config.ComputeServiceProperties;
import org.jclouds.compute.domain.*;
import org.jclouds.compute.domain.internal.NodeMetadataImpl;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.enterprise.config.EnterpriseConfigurationModule;
import org.jclouds.http.handlers.BackoffLimitedRetryHandler;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.auth.config.CredentialTypes;
import org.jclouds.openstack.keystone.config.KeystoneProperties;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.NeutronApiMetadata;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.NovaApiMetadata;
import org.jclouds.openstack.nova.v2_0.compute.options.NovaTemplateOptions;
import org.jclouds.openstack.nova.v2_0.config.NovaProperties;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;
import org.jclouds.openstack.nova.v2_0.domain.Quota;
import org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi;
import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.jclouds.ssh.SshClient;
import org.jclouds.sshj.SshjSshClient;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.jclouds.util.Closeables2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Component("OPENSTACK")
@Scope("prototype")
public class OpenstackComputeUtils extends  CloudUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ComputeService computeService;
    private OpenstackAccountDetails openstackAccountDetails;
    private String keyPairName;

    public OpenstackComputeUtils(UserAccount userAccount) {
        super(userAccount);
        this.openstackAccountDetails =  (OpenstackAccountDetails) userAccount.getDetails();
        this.keyPairName =  userAccount.getName() + "_" +  userAccount.getId() + "_modellify";
        createComputeService();
    }

    public Set<String> getConfiguredRegions() {
        NovaApi novaApi = getNovaApi();
        Set<String> regions = novaApi.getConfiguredRegions();
        destroyCloseable(novaApi);
        return regions;
    }

    public Set<Instance> createNodes(CreateInstance instance) {
        NeutronApi neutronApi = getNeutronApi();
        String internalNetwork;
        IterableWithMarker<Network> networks = neutronApi.getNetworkApi(instance.getRegion())
                .list().get(0);
        internalNetwork = networks.filter(network ->
                network.getName().equalsIgnoreCase("Internal") ||
                        network.getName().equalsIgnoreCase("Private"))
                .first().get().getId();

        if (!keyPairExists(instance))
            createKeyPair(instance);

        TemplateOptions options = NovaTemplateOptions.Builder
                .keyPairName(keyPairName).networks(internalNetwork)
                .autoAssignFloatingIp(true)
                .blockOnComplete(true)
                .runAsRoot(false)
                .securityGroups("default");
        Template template = computeService.templateBuilder()
                .locationId(instance.getRegion())
                .options(options)
                .fromHardware(getHardware(instance))
                .fromImage(getImage(instance))
                .build();
        Set<? extends NodeMetadata> nodes;
        try {
            nodes = computeService
                    .createNodesInGroup(instance.getClusterName(), instance.getNumberOfInstances(), template);
        } catch (RunNodesException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        } finally {
            destroyCloseable();
            destroyCloseable(neutronApi);
        }
        return getInstances(nodes, instance.getRegion(), instance.getMachineProfile());
    }


    private boolean keyPairExists(CreateInstance instance) {
        KeyPairApi keyPairApi = getNovaApi().getKeyPairApi(instance.getRegion()).get();
        try {
            KeyPair keyPair = keyPairApi.get(keyPairName);
            if (keyPair != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    public KeyPair createKeyPair(CreateInstance instance) {
        try {
            KeyPairApi keyPairApi = getNovaApi().getKeyPairApi(instance.getRegion()).get();

            KeyPair keyPair = null;

            try {
                //keypair exists on openstack
                keyPair = keyPairApi.get(keyPairName);

            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
            }

            //check if private key exists
            File keyPairFile = new File("/keys/" + keyPairName + ".pem");

            if (keyPair == null || !keyPairFile.exists()) {
                logger.info("Keypair {} not found, creating a new keypair", keyPairName);
                keyPair = keyPairApi.create(keyPairName);

                Files.createFile(keyPairFile.toPath());
                FileUtils.writeStringToFile(keyPairFile, keyPair.getPrivateKey());
            }

            if(keyPairFile.exists()) {
                logger.info("Private key and public key exists");
                keyPair = KeyPair.builder().fromKeyPair(keyPair)
                        .privateKey(FileUtils.readFileToString(keyPairFile))
                        .build();
            }

            return keyPair;
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    public void destroyNode(Instance instance) {
        try {
            computeService.destroyNode(instance.getNodeId());
            NovaApi novaApi = getNovaApi();
            FloatingIPApi api = novaApi.getFloatingIPApi(instance.getRegion()).get();
            api.list().filter(ip -> ip.getIp()
                    .equals(instance.getFloatingIp()))
                    .forEach(ip -> api.delete(ip.getId()));
        } catch (NoSuchElementException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        } finally {
            destroyCloseable();
        }
    }

    public void suspendNode(String nodeId) {
        try {
            computeService.suspendNode(nodeId);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        } finally {
            destroyCloseable();
        }
    }

    public void resumeNode(String nodeId) {
        try {
            computeService.resumeNode(nodeId);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        } finally {
            destroyCloseable();
        }
    }

    public void rebootNode(String nodeId) {
        try {
            computeService.rebootNode(nodeId);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        } finally {
            destroyCloseable();
        }
    }

    public Set<? extends Image> listImages() {
        try {
            return computeService.listImages();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        } finally {
            destroyCloseable();
        }
    }

    public Set<? extends Hardware> listHardwareProfiles() {
        try {
            return computeService.listHardwareProfiles();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        } finally {
            destroyCloseable();
        }
    }


    public AccountQuota getAccountQuota(String region) {
        AccountQuota accountQuota = new AccountQuota();
        NovaApi novaApi = getNovaApi();
        Quota overallQuota = novaApi.getQuotaApi(region).get()
                .getByTenant(openstackAccountDetails.getTenantId());
        accountQuota.setTotal(overallQuota);
        Set<? extends ComputeMetadata> nodes = computeService.listNodes();
        int ram = 0;
        int cores = 0;
        for(ComputeMetadata node : nodes) {

            Hardware hardware = ((NodeMetadataImpl)node).getHardware();
            if (hardware != null) {
                for (Processor processor : hardware.getProcessors()) {
                    cores+=Math.round(processor.getCores());
                }
                ram+=hardware.getRam();
            }
        }
        Quota used = Quota.builder().cores(cores)
                .floatingIps(nodes.size())
                .ram(ram)
                .instances(nodes.size())
                .id("fake-id")
                .build();
        accountQuota.setUsed(used);
        destroyCloseable(novaApi);
        return accountQuota;
    }

    public int getMaxPossibleNodes(String region, Instance masterInstance) {

        NovaApi novaApi = getNovaApi();
        Quota overallQuota = novaApi.getQuotaApi(region).get()
                .getByTenant(openstackAccountDetails.getTenantId());

        Set<? extends ComputeMetadata> nodes = computeService.listNodes();
        int ram = 0;
        int cores = 0;

        int profileRam = 0, profileCore = 0;
        for(ComputeMetadata node : nodes) {


            Hardware hardware = ((NodeMetadataImpl) node).getHardware();
            if (hardware != null) {
                for (Processor processor : hardware.getProcessors()) {
                    cores += Math.round(processor.getCores());
                }
                ram += hardware.getRam();

                if(node.getId().equals(masterInstance.getNodeId())) {

                    profileRam = hardware.getRam();
                    for (Processor processor : hardware.getProcessors()) {
                        profileCore += Math.round(processor.getCores());
                    }
                }
            }

        }

        int totalCores = overallQuota.getCores();
        int totalRam = overallQuota.getRam();

        int remainingRam = totalRam - ram;
        int remainingCores = totalCores - cores;

        destroyCloseable(novaApi);
        return Math.min(remainingCores/profileCore, remainingRam/profileRam);
    }

    public String runCommand(String nodeId, String statement, String privateKey) {
        logger.info("runCommand - Running Command : {}", statement);
        SshClient client = getSshjSshClient(nodeId, privateKey);
        client.connect();
        ExecResponse response = null;
        if (client.isConnected()) {
            response = client.exec(statement);
            return response.getOutput();
        } else {
            return response.getError();
        }
    }

    private SshClient getSshjSshClient(String publicIp, String privateKey) {
        final HostAndPort socket = HostAndPort.fromParts(publicIp, 22);
        final SshjSshClient client = new SshjSshClient(
                new BackoffLimitedRetryHandler(), socket, LoginCredentials.builder().privateKey(privateKey).noPassword()
                .user("ubuntu").build(), 500000, getAgentConnector());
        return computeService.getContext().utils().sshFactory().create(HostAndPort.fromParts(publicIp, 22), LoginCredentials.builder().privateKey(privateKey).noPassword()
                .user("ubuntu").build());
    }

    Optional<Connector> getAgentConnector() {
        ConnectorFactory sshAgentOverNetcatOnly = new ConnectorFactory() {
            {
                setPreferredConnectors("ssh-agent");
                setPreferredUSocketFactories("nc");
            }
        };
        try {
            return Optional.of(sshAgentOverNetcatOnly.createConnector());
        } catch (final AgentProxyException e) {
            return Optional.absent();
        }
    }

    private static String getClasspathResource(String name) {
        try {
            Resource resource = new ClassPathResource(name);
            return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Instance> getInstances(Set<? extends NodeMetadata> nodes, String region, String machineProfile) {
        return nodes.stream().map(node -> {
            Instance instance = new Instance();
            instance.setFloatingIp(node.getPublicAddresses()
                    .stream().findFirst().get());
            instance.setUserAccount(userAccount);
            instance.setImageId(node.getImageId());
            instance.setNodeId(node.getId());
            instance.setName(node.getName());
            instance.setStatus(InstanceStatus.UP);
            instance.setRegion(region);
//            instance.setHardwareProfile(machineProfile);
            return instance;
        }).collect(Collectors.toSet());
    }

    private void createComputeService() {
        ComputeServiceContext context = ContextBuilder.newBuilder(new NovaApiMetadata())
                .credentials(openstackAccountDetails.getIdentity(), openstackAccountDetails.getPassword())
                .overrides(getOverrides())
                .modules(ImmutableSet.of(new SshjSshClientModule(), new SLF4JLoggingModule(),
                        new EnterpriseConfigurationModule()))
                .endpoint(openstackAccountDetails.getProviderUrl())
                .buildView(ComputeServiceContext.class);
        computeService = context.getComputeService();
    }

    private Image getImage(CreateInstance instance) {
        try {
            return computeService.listImages().stream()
                    .filter(image -> image.getOperatingSystem().getName()
                            .startsWith(instance.getOsType()))
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        }
    }

    private Hardware getHardware(CreateInstance instance) {
        try {
            return computeService.listHardwareProfiles().stream()
                    .filter(profile -> profile.getName().equalsIgnoreCase(instance.getMachineProfile()))
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        }
    }

    private NeutronApi getNeutronApi() {
        NeutronApi neutronApi = ContextBuilder.newBuilder(new NeutronApiMetadata())
                .credentials(openstackAccountDetails.getIdentity(), openstackAccountDetails.getPassword())
                .overrides(getOverrides())
                .endpoint(openstackAccountDetails.getProviderUrl())
                .buildApi(NeutronApi.class);
        return neutronApi;
    }

    private NovaApi getNovaApi() {
        NovaApi novaApi = computeService.getContext()
                .unwrapApi(NovaApi.class);
        return novaApi;
    }

    private Properties getOverrides() {
        Properties overrides = new Properties();
        overrides.put(ComputeServiceProperties.TIMEOUT_PORT_OPEN, "40");
        overrides.put(KeystoneProperties.KEYSTONE_VERSION, "3");
        overrides.put(NovaProperties.AUTO_ALLOCATE_FLOATING_IPS, true);
        //overrides.put(ComputeServiceProperties.	SOCKET_FINDER_ALLOWED_INTERFACES, "PRIVATE");
        overrides.setProperty(KeystoneProperties.CREDENTIAL_TYPE, CredentialTypes.PASSWORD_CREDENTIALS);
        overrides.put(KeystoneProperties.SCOPE, "project:" + openstackAccountDetails.getProject());
        return overrides;
    }

    private void destroyCloseable() {
        Closeables2.closeQuietly(computeService.getContext());
    }

    private void destroyCloseable(Closeable closeable) {
        Closeables2.closeQuietly(computeService.getContext());
        Closeables2.closeQuietly(closeable);
    }

    @Override
    public void verifyUserAccount() {
        CreateInstance createInstance = new CreateInstance();

        String region = getConfiguredRegions().iterator().next();
        createInstance.setRegion(region);

        KeyPair keyPair = createKeyPair(createInstance);
        openstackAccountDetails.setPrivateKey(keyPair.getPrivateKey());
    }
}
```