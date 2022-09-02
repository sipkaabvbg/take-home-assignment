package com.marionete.proto.backends;

import com.marionete.proto.backends.constants.Constants;
import com.twitter.finagle.http.Method;
import com.marionete.backends.*;

import java.util.logging.Logger;

public class UserInfoClient extends ClientImp {
    private static final Logger logger = Logger.getLogger(UserInfoClient.class.getName());

    /**
     * A custom client.
     */
    public UserInfoClient(String host) {
        super(UserInfoMock.start(), host);
    }

    /**
     * Main start the client from the command line.
     */
    public static void main(String[] args) throws Exception {
        String result;
        UserInfoClient userInfoClient = new UserInfoClient(Constants.USER_SERVER_ADDRESS);

        try {
            result = userInfoClient.apiRequest(Constants.CORRECT_REST_URL_USER, Constants.VALID_TOKEN, Method.Get());
            logger.info(result);
        } finally {
            userInfoClient.shutdown();
        }
    }
}
