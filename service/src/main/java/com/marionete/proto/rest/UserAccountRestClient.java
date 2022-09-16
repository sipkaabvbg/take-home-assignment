package com.marionete.proto.rest;

import com.marionete.proto.backends.constants.Constants;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * User Account Rest Client
 */
public class UserAccountRestClient {

    private static final Logger logger = Logger.getLogger(UserAccountRestClient.class.getName());

    private final HttpClient httpClient = HttpClient.newBuilder()
            .build();

    public static void main(String[] args) throws Exception {

        UserAccountRestClient obj = new UserAccountRestClient();
        logger.info("Send Http POST request");
        obj.sendPost(Constants.USER_NAME, Constants.PASSWORD);

    }

    /**
     * Send Post request
     * @param userName
     * @param password
     * @throws Exception
     */
    public String sendPost(String userName, String password) throws Exception {

        Map<Object, Object> data;
        HttpResponse<String> response;
        HttpRequest request;

        data = new HashMap<>();
        data.put( "username",userName);
        data.put( "password",password);
        request = HttpRequest.newBuilder()
                .POST(buildFormData(data))
                .uri(URI.create(Constants.REST_URL))
                .header("Content-Type", "application/json")
                .header( "Accept", "application/json" ).version(httpClient.version())
                .build();

        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // print status code
        logger.info("response status code:"+response.statusCode());
        return response.body();
    }

    /**
     * Build Form Data
     * @param data
     * @return
     */
    private static HttpRequest.BodyPublisher buildFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        logger.info(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
