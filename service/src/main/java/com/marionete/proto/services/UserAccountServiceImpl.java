package com.marionete.proto.services;

import io.grpc.stub.StreamObserver;
import services.*;

/**
 * UserAccountService implementation
 */
public class UserAccountServiceImpl extends UserAccountServiceGrpc.UserAccountServiceImplBase {

    /**
     * Server-Side streaming/Asynchronous communication
     */

    @Override
    public void userAccount(UserAccountRequest req, StreamObserver<UserAccountResponse> responseObserver) {
        UserAccountResponse reply = UserAccountResponse.newBuilder().setAccountInfo(getAccountInfo())
                .setUserInfo(getUserInfo()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    public static AccountInfo getAccountInfo() {
        return AccountInfo.newBuilder().setAccountNumber("12345-3346-3335-4456").build();
    }

    public static UserInfo getUserInfo() {
        return UserInfo.newBuilder()
                .setAge(32)
                .setName("John")
                .setSex("male")
                .setSurname("Doe").build();
    }

}
