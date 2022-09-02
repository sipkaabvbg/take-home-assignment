package com.marionete.backends;

import com.marionete.proto.backends.AccountInfoClient;
import com.marionete.proto.backends.constants.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.twitter.finagle.http.*;

import java.io.IOException;

import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * This class runs 3 tests:
 * 1.Good request with Authentication in header and return result successfully
 * 2.Bad request with wrong endpoint
 * 3.Bad request without Authentication in header and return result Error
 */
public class AccountInfoTest {

    private static final Logger logger = Logger.getLogger(AccountInfoTest.class.getName());

    private AccountInfoClient accountInfoClient;

    public AccountInfoTest() {
        super();
    }


    /**
     * Good request with Authentication in header and return result successfully
     *
     * @throws Exception
     */
    @Test
    public void requestWithAuthentication() throws Exception {
        logger.info("requestWithAuthentication ");
        assertNotNull(accountInfoClient.apiRequest(Constants.CORRECT_REST_URL_ACCOUNT, Constants.VALID_TOKEN, Method.Get()));
    }

    /**
     * Bad request with wrong endpoint
     *
     * @throws Exception
     */
    @Test
    public void badRequestWrongEndpoint() throws Exception {
        logger.info("badRequestWithoutAuthentication() ");
        assertEquals(Constants.NOT_FOUND_MESSAGE, accountInfoClient.apiRequest(Constants.WRONG_REST_URL, Constants.VALID_TOKEN, Method.Get()));
    }

    /**
     * Bad request without Authentication in header and return result Error
     *
     * @throws Exception
     */
    @Test
    public void badRequestWithoutAuthentication() throws Exception {
        logger.info("badRequestWithoutAuthentication() ");
        assertEquals(Constants.INTERNAL_SERVER_ERROR_MESSAGE, accountInfoClient.apiRequest(Constants.CORRECT_REST_URL_ACCOUNT, Constants.INVALID_TOKEN, Method.Get()));
    }

    @Before
    public void beforeEachTest() throws InstantiationException, IllegalAccessException, IOException {
        accountInfoClient = new AccountInfoClient(Constants.ACCOUNT_SERVER_ADDRESS);
    }

    @After
    public void afterEachTest() throws Exception {
        accountInfoClient.shutdown();
    }
}