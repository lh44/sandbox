package com.sandbox.internal.others.infra.kafka.lib.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandbox.internal.others.infra.kafka.lib.model.Event;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.sandbox.internal.others.infra.kafka.lib.configuration.IKafkaConstants.PARTITION_COUNT;

public class EventIOConfigurator implements Deserializer<Event>, Serializer<Event>, Partitioner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public Event deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Event.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public byte[] serialize(String topic, Event event) {
        try {
            return objectMapper.writeValueAsString(event).getBytes();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new byte[0];
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        return Math.toIntExact(((Event) value).getTransactionId() % PARTITION_COUNT);
    }

    @Override
    public void configure(Map<String, ?> map) {}

    @Override
    public void close() {}

}