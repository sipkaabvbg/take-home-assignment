package com.marionete.proto;

import com.marionete.backends.*;
import com.marionete.proto.backends.AccountInfoClient;
import com.marionete.proto.backends.UserInfoClient;
import com.marionete.proto.backends.constants.Constants;
import com.marionete.proto.rest.RestServer;
import com.marionete.proto.rest.UserAccountRest;
import com.marionete.proto.rest.UserAccountRestClient;
import com.sun.net.httpserver.HttpHandler;
import com.twitter.finagle.http.Method;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Simple Marionete platform
 */
public class MarionetePlatformApp {
    private static final Logger logger = Logger.getLogger(MarionetePlatformApp.class.getName());

    private String token;
    private MarioneteServer grpcServer;
    private RestServer restServer;
    private UserAccountRestClient userAccountRestClient;
    private LoginServiceClient loginClient;
    private UserInfoClient userInfoClient;
    private AccountInfoClient accountInfoClient;

    /**
     * Main launches the App from the command line.
     */
    public static void main(String[] args) throws Exception {

        char choice;
        String userAccountRestClientResponse;
        final Scanner S = new Scanner(System.in);
        final MarionetePlatformApp app;


        app = new MarionetePlatformApp();

        app.init();

        // Menu for the user to see multiple operations.
        System.out.println("------------------");
        System.out.println("    Main Menu: ");
        System.out.println("------------------");
        System.out.println("------------------");
        System.out.println(" 1 Restful:POST /marionete/useraccount/\n" +
                "{\n" +
                "    \"username\":\"bla\",\n" +
                "    \"password\":\"foo\"\n" +
                "}" +
                "and call gRPC server, to obtain and keep the token" );
        System.out.println("------------------");
        System.out.println(" 2 Rest Connector UserAccount:" +
                "Request:\n" +
                "    GET http://localhost:8898/marionete/user/\n" +
                "    HEADER Authorization: token");
        System.out.println("------------------");
        System.out.println(" 3 Rest Connector AccountInfo" +
                "Request:\n" +
                "    GET http://localhost:8899/marionete/account/\n" +
                "    HEADER Authorization: token");
        System.out.println("------------------");
        System.out.println(" 4 Exit");
        do {
            System.out.println("Enter your choice: ");

            choice = S.next().charAt(0);

            switch (choice) {
                case '1':
                    userAccountRestClientResponse=app.userAccountRestClient.sendPost(Constants.USER_NAME, Constants.PASSWORD);
                    // print response body
                    System.out.println("UserAccountRestResponse:"+userAccountRestClientResponse);
                    System.out.println("login request:");
                    app.token = app.loginClient.loginRequestBlockingStub(Constants.USER_NAME, Constants.PASSWORD);
                    System.out.println("token:" + app.token);
                    break;

                case '2':
                    System.out.println("Result: " + app.userInfoClient.apiRequest(Constants.CORRECT_REST_URL_USER, app.token, Method.Get()));
                    break;

                case '3':
                    System.out.println("Result: " + app.accountInfoClient.apiRequest(Constants.CORRECT_REST_URL_ACCOUNT, app.token, Method.Get()));
                    break;

                case '4':
                    app.stopApp();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        } while (choice != 4);

    }
    private void init() throws Exception {
        UserAccountRest userAccountRest;
        HttpHandler userAccount;

        grpcServer = new MarioneteServer();
        restServer = new RestServer();
        grpcServer.start();

        userAccountRest = new UserAccountRest();
        userAccount = restServer.getHttpHandler(Constants.POST_REQUEST_TYPE, userAccountRest);
        restServer.startHttpServer( Constants.REST_SERVER_PORT,Constants.REST_URL_CONTEXT,userAccount);
        userAccountRestClient=new UserAccountRestClient();
        userInfoClient = new UserInfoClient(Constants.USER_SERVER_ADDRESS);
        accountInfoClient = new AccountInfoClient(Constants.ACCOUNT_SERVER_ADDRESS);
        accountInfoClient.shutdown();
        userInfoClient.shutdown();
        loginClient = new LoginServiceClient(Constants.HOST, Constants.GRPC_SERVER_PORT);

        AccountInfoMock.start();
        UserInfoMock.start();
    }
    private void stopApp() throws Exception {
        grpcServer.stop();
        restServer.stopHttpServer();
        accountInfoClient.shutdown();
        userInfoClient.shutdown();
    }
}
