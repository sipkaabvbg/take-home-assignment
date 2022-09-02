package com.marionete.proto.services;

import io.grpc.stub.StreamObserver;
import services.LoginResponse;

import java.util.logging.Logger;

/**
 * StreamObserver for asynchronous Login
 */
public class LoginCallback implements StreamObserver<LoginResponse> {
    private static final Logger logger = Logger.getLogger(LoginCallback.class.getName());

    @Override
    public void onNext(LoginResponse value) {
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
