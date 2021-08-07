package com.sandbox.internal.others.infra.kafka.app;

import com.sandbox.internal.others.infra.kafka.lib.KafkaClientFactory;
import com.sandbox.internal.others.infra.kafka.lib.configuration.IKafkaConstants;
import com.sandbox.internal.others.infra.kafka.lib.model.Event;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KafkaApp {

    private final static Logger logger = LoggerFactory.getLogger(KafkaApp.class);
    final static ScheduledExecutorService producerService = Executors.newSingleThreadScheduledExecutor();
    final static ScheduledExecutorService consumerService = Executors.newScheduledThreadPool(2);
    final static Producer<Long, Event> producer = KafkaClientFactory.createProducer();
    final static Consumer<Long, Event> consumer1 = KafkaClientFactory.createConsumer();
    final static Consumer<Long, Event> consumer2 = KafkaClientFactory.createConsumer();

    public static void main(String[] args) {
        startConsuming();
        startProducing();
    }

    private static void startProducing() {
        Runnable runnable = () -> {
            try {
                Event event = new Event(System.currentTimeMillis());
                ProducerRecord<Long, Event> record = new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, event);
                RecordMetadata metadata = producer.send(record).get();
                System.out.printf("Sender Partition - %s :: %s\n", metadata.partition(), event.getTransactionId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        };
        producerService.scheduleAtFixedRate(runnable, 1, 2, TimeUnit.SECONDS);
    }

    private static void startConsuming() {
        consumerService.scheduleAtFixedRate(getConsumerRunnable(consumer1), 1, 2, TimeUnit.SECONDS);
        consumerService.scheduleAtFixedRate(getConsumerRunnable(consumer2), 1, 2, TimeUnit.SECONDS);
    }

    private static Runnable getConsumerRunnable(Consumer<Long, Event> consumer) {
        return  () -> {
            try {
                ConsumerRecords<Long, Event> records = consumer.poll(Duration.ofMillis(1000));
                records.forEach(r -> System.out.printf("Receiver Partition - %s :: %s\n", r.partition(), r.value()));
                consumer.commitAsync();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        };
    }

}
