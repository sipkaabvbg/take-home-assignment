package com.marionete.proto.backends.constants;

public class Constants {
    public final static int GRPC_SERVER_PORT = 80;
    public final static int REST_SERVER_PORT = 8000;
    public final static String HOST = "localhost";
    public final static String ACCOUNT_SERVER_ADDRESS = "localhost:8899";
    public final static String USER_SERVER_ADDRESS = "localhost:8898";
    public final static String CORRECT_REST_URL_USER = "/marionete/user/";
    public final static String CORRECT_REST_URL_ACCOUNT = "/marionete/account/";
    public final static String WRONG_REST_URL = "/marionete/wrong/";
    public final static String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
    public final static String NOT_FOUND_MESSAGE = "Not Found";
    public final static String VALID_TOKEN = "Y7bm4G0shpWabcXVg8eGnP4RBsJIWGPfz4EJTQNWx8zZaeksavsty60ws5G28xEf4W1R4Z0qN0f7DXUht";
    public final static String INVALID_TOKEN = "";
    public final static String USER_NAME = "bla";
    public final static String PASSWORD = "foo";
    public final static String TOKEN_KEY = "Authorization";
    public final static String POST_REQUEST_TYPE ="POST";
    public final static String REST_URL = "http://localhost:8000/marionete/useraccount/";
    public final static String REST_URL_CONTEXT = "/marionete/useraccount/";
    public final static String USER_ACCOUNT_RESPONSE="{\"accountInfo\": {\"accountNumber\": \"12345-3346-3335-4456\"},\"userInfo\": \"name\": \"John\",\"surname\": \"Doe\",\"sex\": \"male\",\"age\": 32}";
}
