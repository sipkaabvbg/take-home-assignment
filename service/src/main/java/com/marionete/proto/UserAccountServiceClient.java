package com.marionete.proto;

import com.marionete.proto.backends.constants.Constants;
import com.marionete.proto.services.UserAccountCallback;
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import services.UserAccountRequest;
import services.UserAccountResponse;
import services.UserAccountServiceGrpc;
import services.UserAccountServiceGrpc.UserAccountServiceStub;
import services.UserAccountServiceGrpc.UserAccountServiceBlockingStub;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple UserAccountService client
 */
public class UserAccountServiceClient extends BaseClient {
    private static final Logger logger = Logger.getLogger(UserAccountServiceClient.class.getName());

    private final UserAccountServiceBlockingStub blockingStub;
    private final UserAccountServiceStub asyncStub;

    /**
     * Constructor
     */
    public UserAccountServiceClient(String host, int port) {
        super(host, port);
        blockingStub = UserAccountServiceGrpc.newBlockingStub(getManagedChannel());
        asyncStub = UserAccountServiceGrpc.newStub(getManagedChannel());
    }

    /**
     * Client method sends asynchronous request with header and token
     */
    public void getUserAccount(String username, String password, String token) {
        Metadata.Key<String> key;
        UserAccountRequest request;
        Metadata header;

        key = Metadata.Key.of(Constants.TOKEN_KEY, Metadata.ASCII_STRING_MARSHALLER);
        header = new Metadata();
        header.put(key, token);
        request = UserAccountRequest.newBuilder().setUsername(username).setPassword(password).build();
        asyncStub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(header));
        asyncStub.userAccount(request, new UserAccountCallback());
    }

    /**
     * Client method sends synchronous request with header and token
     */
    public UserAccountResponse getUserAccountBlockingStub(String username, String password, String token) {
        Metadata.Key<String> key;
        UserAccountRequest request;
        UserAccountResponse response = null;
        Metadata header = new Metadata();

        key = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        header.put(key, token);
        blockingStub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(header));
        request = UserAccountRequest.newBuilder().setUsername(username).setPassword(password).build();
        try {
            response = blockingStub.userAccount(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "Request failed: {0}", e.getStatus());
        }

        return response;
    }

    /**
     * Main start the client from the CMD.
     */
    public static void main(String[] args) throws Exception {

        UserAccountServiceClient client;
        UserAccountResponse response;

        client = new UserAccountServiceClient(Constants.HOST, Constants.PORT);
        try {
            response = client.getUserAccountBlockingStub(Constants.USER_NAME, Constants.PASSWORD, Constants.VALID_TOKEN);
            logger.info("UserAccountService - accountNumber: " + response.getAccountInfo().getAccountNumber());
            client.getUserAccount(Constants.USER_NAME, Constants.PASSWORD, Constants.VALID_TOKEN);
        } finally {
            client.shutdown();
        }
    }
}