package com.marionete.proto;

import io.grpc.ManagedChannel;

import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Base client class
 */
public abstract class BaseClient {

    private final ManagedChannel managedChannel;

    /**
     * Constructor
     */
    public BaseClient(String host, int port) {
        managedChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }

    public ManagedChannel getManagedChannel() {
        return managedChannel;
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
