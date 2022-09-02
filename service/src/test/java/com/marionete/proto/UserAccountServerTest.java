package com.marionete.proto;

import static org.junit.Assert.*;

import com.marionete.proto.backends.constants.Constants;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import services.*;

/**
 * Runs a test by starting a MarioneteServer(gRPC server) and taking
 * a token from the LoginService request, then passing the token to
 * UserAccount and then stopping the server
 */
public class UserAccountServerTest extends LoginServiceTest {

    private static final Logger logger = Logger.getLogger(UserAccountServerTest.class.getName());

    public UserAccountServerTest() {
        super();
    }

    @Test
    public void testIsUserAccountNotNull() throws InterruptedException {
        String token;
        UserAccountResponse userAccountResponse;
        UserAccountServiceClient userAccountClient;

        userAccountClient = new UserAccountServiceClient(Constants.HOST, Constants.PORT);
        try {
            token = getTokenBlockingStub();

            logger.info("token:" + token);
            userAccountResponse = userAccountClient.getUserAccountBlockingStub(Constants.USER_NAME, Constants.PASSWORD, token);
            assertNotNull(userAccountResponse.toString());
            logger.info("userAccount:" + userAccountResponse.toString());
        } finally {
            userAccountClient.shutdown();
        }
    }

    @Before
    public void beforeEachTest() throws IOException {
        super.beforeEachTest();
    }

    @After
    public void afterEachTest() throws InterruptedException {
        super.afterEachTest();
    }
}