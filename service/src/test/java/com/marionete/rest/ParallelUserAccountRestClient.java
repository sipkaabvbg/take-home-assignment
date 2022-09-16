package com.marionete.rest;

import com.marionete.proto.LoginServiceTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

/**
 * Parallel User Account Rest Client
 */
public class ParallelUserAccountRestClient extends Thread
{
    private static final Logger logger = Logger.getLogger(ParallelUserAccountRestClient.class.getName());

    private String urlPart;
    private String requestType;
    private CountDownLatch latch;
    private Set<String> synchronizedSet;
    public ParallelUserAccountRestClient(String urlPart, String requestType, CountDownLatch latch,Set<String> synchronizedSet){
        this.latch=latch;
        this.urlPart=urlPart;
        this.requestType=requestType;
        this.synchronizedSet=synchronizedSet;
    }
    @Override
    public void run()
    {
        String apiOutput;
        URL url;
        HttpURLConnection conn;
        BufferedReader br;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");
            try
            {
                url = new URL(urlPart);
                latch.countDown();
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(requestType);
                conn.setRequestProperty("Accept", "application/json");


                if (conn.getResponseCode() != 200)
                {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                apiOutput = br.readLine();
                logger.info(apiOutput +"/ "+dtf.format(LocalDateTime.now()));
                synchronizedSet.add(dtf.format(LocalDateTime.now()));
                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
           }
            //latch.countDown();
        logger.info(Thread.currentThread().getName()
                    + " finished");
        }
}