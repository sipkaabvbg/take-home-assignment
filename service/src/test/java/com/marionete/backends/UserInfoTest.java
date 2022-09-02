package com.marionete.backends;

import com.marionete.proto.backends.UserInfoClient;
import com.marionete.proto.backends.constants.Constants;

import com.twitter.finagle.http.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class runs 3 tests:
 * 1.Good request with Authentication in header and return result successfully
 * 2.Bad request without Authentication in header and return result Error
 * 3.Bad request with wrong endpoint and return result Error
 */
public class UserInfoTest {

    private static final Logger logger = Logger.getLogger(UserInfoTest.class.getName());

    private UserInfoClient userInfoClient;

    public UserInfoTest() {
        super();
    }

    /**
     * Good request with Authentication in header and return result successfully
     */
    @Test
    public void goodRequestWithAuthentication() throws Exception {
        logger.info("goodRequestWithAuthentication() ");
        assertNotNull(userInfoClient.apiRequest(Constants.CORRECT_REST_URL_USER, Constants.VALID_TOKEN, Method.Get()));
    }

    /**
     * Bad request without Authentication in header and return result Error
     */
    @Test
    public void badRequestWithoutAuthentication() throws Exception {
        logger.info("badRequestWithoutAuthentication() ");
        assertEquals(Constants.INTERNAL_SERVER_ERROR_MESSAGE, userInfoClient.apiRequest(Constants.CORRECT_REST_URL_USER, Constants.INVALID_TOKEN, Method.Get()));
    }

    /**
     * Bad request with wrong endpoint and return result Error
     */
    @Test
    public void badRequestWithWrongEndpoint() throws Exception {
        logger.info("badRequestWithWrongEndpoint() ");
        assertEquals(Constants.NOT_FOUND_MESSAGE, userInfoClient.apiRequest(Constants.WRONG_REST_URL, Constants.INVALID_TOKEN, Method.Get()));

    }

    @Before
    public void beforeEachTest() throws InstantiationException, IllegalAccessException, IOException {
        userInfoClient = new UserInfoClient(Constants.USER_SERVER_ADDRESS);
    }

    @After
    public void afterEachTest() throws Exception {
        userInfoClient.shutdown();
    }
}