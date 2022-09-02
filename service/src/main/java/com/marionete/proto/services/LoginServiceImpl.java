package com.marionete.proto.services;

import com.marionete.proto.secure.TokenGenerator;
import io.grpc.stub.StreamObserver;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

/**
 * LoginService implementation
 */
public class LoginServiceImpl extends LoginServiceGrpc.LoginServiceImplBase {

    /**
     * Server-Side streaming/Asynchronous communication
     */
    @Override
    public void login(LoginRequest req, StreamObserver<LoginResponse> responseObserver) {
        LoginResponse reply = LoginResponse.newBuilder().setToken(TokenGenerator.getToken()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
