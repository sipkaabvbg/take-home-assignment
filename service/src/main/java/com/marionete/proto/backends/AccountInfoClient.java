package com.marionete.proto.backends;

import com.marionete.proto.backends.constants.Constants;
import com.twitter.finagle.http.Method;

import java.util.logging.Logger;

import com.marionete.backends.*;

public class AccountInfoClient extends ClientImp {
    private static final Logger logger = Logger.getLogger(AccountInfoClient.class.getName());

    /**
     * A custom client.
     */
    public AccountInfoClient(String host) {
        super(AccountInfoMock.start(), host);
    }


    /**
     * Main start the client from the command line.
     */
    public static void main(String[] args) throws Exception {
        String result;
        AccountInfoClient accountInfoClient = new AccountInfoClient(Constants.ACCOUNT_SERVER_ADDRESS);
        try {
            result = accountInfoClient.apiRequest(Constants.CORRECT_REST_URL_ACCOUNT, Constants.VALID_TOKEN, Method.Get());
            logger.info(result);
        } finally {
            accountInfoClient.shutdown();
        }
    }
}
