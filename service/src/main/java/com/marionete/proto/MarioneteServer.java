package com.marionete.proto;


import com.marionete.proto.backends.constants.Constants;
import com.marionete.proto.services.LoginServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * gRPC Server
 */
public class MarioneteServer {
    private static final Logger logger = Logger.getLogger(MarioneteServer.class.getName());
    private Server server;

    public void start() throws IOException {

        server = ServerBuilder.forPort(Constants.GRPC_SERVER_PORT)
                .addService(new LoginServiceImpl())
                .executor((Executors.newFixedThreadPool(200)))
                .build()
                .start();
        logger.info("Server started, listening on " + Constants.GRPC_SERVER_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("* shutting down gRPC server");
                try {
                    MarioneteServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                logger.info("* server shut down");
            }
        });
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread
     */
    void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final MarioneteServer server;

        server = new MarioneteServer();
        server.start();
        server.blockUntilShutdown();
    }

}
