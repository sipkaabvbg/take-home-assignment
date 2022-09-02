package com.marionete.proto;

import com.marionete.proto.backends.constants.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

/**
 * Runs a test by starting a MarioneteServer(gRPC server) and
 * checks the token from the LoginService request, then
 * stopping the server
 */
public class LoginServiceTest {

    private static final Logger logger = Logger.getLogger(LoginServiceTest.class.getName());

    private MarioneteServer server;

    public LoginServiceTest() {
        super();
    }

    @Test
    public void getTokenBlockingStubTest() throws InterruptedException {
        assertNotNull(getTokenBlockingStub());
    }

    /**
     * Request for the token
     */
    public String getTokenBlockingStub() throws InterruptedException {
        logger.info("Will try to get token...");
        String token;
        LoginServiceClient client = new LoginServiceClient(Constants.HOST, Constants.PORT);
        try {
            token = client.loginRequestBlockingStub(Constants.USER_NAME, Constants.PASSWORD);
            logger.info("client" + token);

        } finally {
            client.shutdown();
        }
        return token;
    }

    @Before
    public void beforeEachTest() throws IOException {
        server = new MarioneteServer();
        server.start();
    }

    @After
    public void afterEachTest() throws InterruptedException {
        server.stop();
    }

}