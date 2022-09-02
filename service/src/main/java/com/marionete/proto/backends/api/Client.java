package com.marionete.proto.backends.api;

import com.twitter.finagle.http.Method;

/**
 * Interface defines apiRequest method
 */
public interface Client {
    String apiRequest(String requestUrl, String requestToken, Method method) throws Exception;
}
