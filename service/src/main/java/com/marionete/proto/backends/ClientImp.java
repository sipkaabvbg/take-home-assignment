package com.marionete.proto.backends;

import com.marionete.proto.backends.api.Client;
import com.twitter.finagle.Http;
import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Method;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Await;
import com.twitter.util.Future;

import java.util.logging.Logger;

/**
 * This class is super class for requests to backends servers
 */
public class ClientImp implements Client {
    private static final Logger logger = Logger.getLogger(ClientImp.class.getName());
    private ListeningServer server;
    private Service client;

    public ListeningServer getServer() {
        return server;
    }

    public void setServer(ListeningServer server) {
        this.server = server;
    }

    public Service getClient() {
        return client;
    }

    public void setClient(String host) {
        this.client = Http.newService(host);
    }

    /**
     * Constructor
     *
     * @param server
     * @param host
     */
    public ClientImp(ListeningServer server, String host) {
        setServer(server);
        setClient(host);
    }

    /**
     * This method executes a request to the REST API
     *
     * @param requestUrl   - request Url
     * @param requestToken - Authentication token
     * @return UserAccount object as String
     * @throws Exception
     */
    public String apiRequest(String requestUrl, String requestToken, Method method) throws Exception {
        int responseStatus;
        String result;
        Future future;
        Response response;
        Request request;

        request = Request.apply(method, requestUrl);
        if (!requestToken.equals("")) {
            request.headerMap().add("Authorization", requestToken);
        }
        client.apply(request);
        client.apply(request);
        future = client.apply(request);
        response = (Response) Await.result(future);
        responseStatus = response.statusCode();
        if (responseStatus == 200) {
            result = response.contentString();
        } else {
            result = response.status().reason();
            logger.severe(response.status().reason() + ": " + responseStatus);
        }

        return result;
    }

    /**
     * Server stop
     *
     * @throws Exception
     */
    public void shutdown() throws Exception {
        Await.result(getServer().close());
    }
}
