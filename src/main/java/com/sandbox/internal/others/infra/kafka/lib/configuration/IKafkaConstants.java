package com.sandbox.internal.others.infra.kafka.lib.configuration;

public interface IKafkaConstants {
    String KAFKA_BROKERS = "localhost:39092;localhost:29092";
    Integer MESSAGE_COUNT = 1000;
    String CLIENT_ID = "skynet";
    String TOPIC_NAME = "events";
    String GROUP_ID_CONFIG = "cg1";
    Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
    String OFFSET_RESET_LATEST="latest";
    String OFFSET_RESET_EARLIER="earliest";
    Integer MAX_POLL_RECORDS=1;
    int PARTITION_COUNT = 2;
}