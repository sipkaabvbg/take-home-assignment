package com.marionete.proto.rest;

import com.marionete.proto.backends.constants.Constants;

/**
 * Mock User Account Rest service
 */
public class UserAccountRest {

    /**
     * Mock method
     * @return
     */
    public String getUserAccount() {
        //This code simulate business operation (database connection or other time consuming operations)
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Constants.USER_ACCOUNT_RESPONSE;
    }
}
