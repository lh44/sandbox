package com.sandbox.internal.others.infra.kafka.lib.model;

public class Event {

    public Event(long transactionId) {
        this.transactionId = transactionId;
    }
    private long transactionId;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}
