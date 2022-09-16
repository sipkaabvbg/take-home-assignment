package com.marionete.rest;

import com.marionete.proto.backends.constants.Constants;
import com.marionete.proto.rest.RestServer;
import com.marionete.proto.rest.UserAccountRest;
import com.sun.net.httpserver.HttpHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * Count Down Latch Test
 */
public class CountDownLatchTest
{
    private static final Logger logger = Logger.getLogger(CountDownLatchTest.class.getName());

    RestServer restServer;

    /**
     * Test Parallel Requests
     * @throws InterruptedException
     */
    @Test
    public void testParallelRequests()
            throws InterruptedException, IOException {
        final Set<String> initialSet;
        final Set<String> synchronizedSet;
        final CountDownLatch latch;
        final ParallelUserAccountRestClient first;
        final ParallelUserAccountRestClient second;
        final ParallelUserAccountRestClient third;
        final ParallelUserAccountRestClient fourth;

        // Task that is going to wait for four threads before it starts
        latch = new CountDownLatch(4);
        initialSet = new HashSet<>();
        synchronizedSet = Collections.synchronizedSet(initialSet);
        // Four worker threads and start them.
        first = new ParallelUserAccountRestClient(Constants.REST_URL,Constants.POST_REQUEST_TYPE, latch,synchronizedSet);
        second = new ParallelUserAccountRestClient( Constants.REST_URL,Constants.POST_REQUEST_TYPE,latch,synchronizedSet);
        third = new ParallelUserAccountRestClient( Constants.REST_URL,Constants.POST_REQUEST_TYPE,latch,synchronizedSet);
        fourth = new ParallelUserAccountRestClient( Constants.REST_URL,Constants.POST_REQUEST_TYPE,latch,synchronizedSet);

        first.start();
        second.start();
        third.start();
        fourth.start();

        // The main task waits for four threads
        latch.await();
        //This code simulate business operation (database connection or other time consuming operations)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(synchronizedSet.size()> 2) {
            throw new AssertionError("There cannot be two results with times other than 2 seconds apart");
        }
        restServer.stopHttpServer();
    }
    @Before
    public void beforeEachTest() throws IOException {
        UserAccountRest userAccountRest;
        HttpHandler userAccount;
        userAccountRest = new UserAccountRest();
        restServer = new RestServer();

        userAccount = restServer.getHttpHandler(Constants.POST_REQUEST_TYPE, userAccountRest);
        restServer.startHttpServer( Constants.REST_SERVER_PORT,Constants.REST_URL_CONTEXT,userAccount);
    }

}