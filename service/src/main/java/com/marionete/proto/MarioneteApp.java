package com.marionete.proto;


import com.marionete.proto.backends.constants.Constants;
import services.UserAccountResponse;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Simple Marionete platform
 */
public class MarioneteApp {
    private static final Logger logger = Logger.getLogger(MarioneteApp.class.getName());

    private String token;

    /**
     * Main launches the App from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final MarioneteApp app;
        final MarioneteServer server;
        final LoginServiceClient loginClient;
        final UserAccountServiceClient userAccountClient;
        final UserAccountResponse userAccountResponse;

        app = new MarioneteApp();
        server = new MarioneteServer();
        loginClient = new LoginServiceClient(Constants.HOST, Constants.PORT);
        userAccountClient = new UserAccountServiceClient(Constants.HOST, Constants.PORT);
        server.start();
        logger.info("login request:");
        app.token = loginClient.loginRequestBlockingStub(Constants.USER_NAME, Constants.PASSWORD);
        logger.info("token:" + app.token);
        logger.info("UerAccount request:");
        userAccountResponse = userAccountClient.getUserAccountBlockingStub(Constants.USER_NAME, Constants.PASSWORD, app.token);
        logger.info("userAccountResponse:" + userAccountResponse.toString());
        server.blockUntilShutdown();
    }
}
