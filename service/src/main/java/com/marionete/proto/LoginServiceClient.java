package com.marionete.proto;

import com.marionete.proto.backends.constants.Constants;
import com.marionete.proto.services.LoginCallback;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import services.*;
import services.LoginServiceGrpc.LoginServiceBlockingStub;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LoginService client
 */
public class LoginServiceClient extends BaseClient {
    private static final Logger logger = Logger.getLogger(LoginServiceClient.class.getName());

    private final LoginServiceBlockingStub blockingStub;
    private final LoginServiceGrpc.LoginServiceStub asyncStub;

    /**
     * Constructor
     */
    public LoginServiceClient(String host, int port) {
        super(host, port);
        blockingStub = LoginServiceGrpc.newBlockingStub(getManagedChannel());
        asyncStub = LoginServiceGrpc.newStub(getManagedChannel());

    }

    /**
     * Client method sends asynchronous request
     */
    public void loginRequest(String username, String password, String token) {
        Metadata.Key<String> key;
        LoginRequest request;
        Metadata header;

        key = Metadata.Key.of(Constants.TOKEN_KEY, Metadata.ASCII_STRING_MARSHALLER);
        header = new Metadata();
        header.put(key, token);
        request = LoginRequest.newBuilder().setUsername(username).setPassword(password).build();
        asyncStub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(header));
        asyncStub.login(request, new LoginCallback());
    }


    /**
     * Client method blocking stub
     */
    public String loginRequestBlockingStub(String username, String password) {
        String result;
        LoginRequest request;
        LoginResponse response = null;

        request = LoginRequest.newBuilder().setUsername(username).setPassword(password).build();
        try {
            response = blockingStub.login(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "Request failed: {0}", e.getStatus());
        }
        result = response.getToken();

        return result;
    }

    /**
     * Main start the client from the CMD.
     */
    public static void main(String[] args) throws Exception {
        String token;
        LoginServiceClient client;

        client = new LoginServiceClient(Constants.HOST, Constants.PORT);
        try {
            token = client.loginRequestBlockingStub(Constants.USER_NAME, Constants.PASSWORD);
            logger.info("LoginService - token: " + token);
        } finally {
            client.shutdown();
        }
    }
}