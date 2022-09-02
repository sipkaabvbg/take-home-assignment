package com.marionete.proto.services;

import io.grpc.stub.StreamObserver;
import services.UserAccountResponse;

import java.util.logging.Logger;

/**
 * StreamObserver for asynchronous UserAccount
 */
public class UserAccountCallback implements StreamObserver<UserAccountResponse> {
    private static final Logger logger = Logger.getLogger(UserAccountCallback.class.getName());

    @Override
    public void onNext(UserAccountResponse value) {
        logger.info("Received:" + value);
    }

    @Override
    public void onError(Throwable cause) {
        logger.severe("Error occurred:" + cause.getMessage());
    }

    @Override
    public void onCompleted() {
        logger.info("Stream completed");
    }


}
