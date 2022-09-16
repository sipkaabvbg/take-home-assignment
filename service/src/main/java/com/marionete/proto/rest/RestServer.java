package com.marionete.proto.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.*;
import java.util.logging.Logger;

import com.marionete.proto.backends.constants.Constants;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Rest Server
 */
public class RestServer {

    private static final Logger logger = Logger.getLogger(RestServer.class.getName());

    private HttpServer server;

    /**
     * Start HttpServer
     * @param serverPort
     * @param context
     * @param httpHandler
     * @throws IOException
     */
    public void startHttpServer(int serverPort,String context,HttpHandler httpHandler) throws IOException {
        server = HttpServer.create();
        server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext(context, httpHandler);
        //server.setExecutor(Executors.newCachedThreadPool()); // creates a default executor
        server.setExecutor(Executors.newFixedThreadPool(200));
        server.start();
        logger.info("RestServer is started on port:"+serverPort);
    }

    /**
     * Stop HttpServer
     * @throws IOException
     */
    public void stopHttpServer() throws IOException {
        server.stop(2);
        logger.info("RestServer is stopped");
    }

    public static void main(String[] args) throws IOException {
        UserAccountRest userAccountRest;
        HttpHandler userAccount;
        userAccountRest = new UserAccountRest();
        RestServer restServer = new RestServer();

        userAccount = getHttpHandler(Constants.POST_REQUEST_TYPE, userAccountRest);
        restServer.startHttpServer( Constants.REST_SERVER_PORT,Constants.REST_URL_CONTEXT,userAccount);
       // restServer.stopHttpServer();
    }

    /**
     *
     */
    public static HttpHandler getHttpHandler(String requestType, UserAccountRest userAccountRest) {
        return new HttpHandler() {
            public void handle(HttpExchange t) throws IOException {
                String response;
                OutputStream os;
                InputStream is;
                if (requestType.equals(t.getRequestMethod())) {
                    //is = t.getRequestBody();
                    logger.info(t.getRemoteAddress().toString());
                    response = userAccountRest.getUserAccount();
                    t.sendResponseHeaders(200, response.length());
                    os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.flush();
                    os.close();
                } else {
                    t.sendResponseHeaders(405, -1);
                }
            }
        };
    }
}