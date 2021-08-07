package com.sandbox.internal.design.patterns.v1;

import java.util.HashSet;
import java.util.Set;

public class Factory {
    //In Factory pattern, we create object without exposing the creation logic
    // to the client and refer to newly created object using a common interface.
    static Set<CloudConnector> connectors = new HashSet<>();

    class CloudConnectorFactory {

        //@Autowired
        private Set<CloudConnector> connectors;

        public CloudConnector getConnector(Type type) {
            return connectors.stream().filter(c -> c.getType().equals(type))
                    .findFirst().orElseThrow(() -> new RuntimeException(""));
        }
    }

    interface CloudConnector {

        Type getType();

        Object launchMachine(Object configuration);
    }

    enum Type {
        AWS, GCP, OPENSTACK;
    }

    //@Component
    @interface Connector {}

    @Connector
    static class AWSConnector implements CloudConnector {

        @Override
        public Type getType() {
            return Type.AWS;
        }

        @Override
        public Object launchMachine(Object configuration) {
            return null;
        }
    }

}
