## Description

This implementation should cover all topics described in README file in 
main project directory.
All classes are in ```service``` module.
The implementation done with Java 11. 
### Project structure 
In the package "com.marionete.proto.backends" are all classes that execute API calls/requests 
to the mocked servers in "backends" module. 
In the package "com.marionete.proto.backends.api" is located interface Client.
That interface sets main apiRequest method for API calls.
In the package "com.marionete.proto.backends" are classes that implement Client interface
and AccountInfoClient and UserInfoClient and can start and send request to mocked servers.

In the package "com.marionete.proto.secure" - there is TokenGenerator. THis class generates token.
The package "com.marionete.proto.services" contains classes implementing Login
service and StreamObservers for asynchronous calls.
The package "com.marionete.proto" has gRPC server(MarioneteServer), MarionetePlatformApp, 
LoginServiceClient. All this classes have main method and can start independent.
The class MarionetePlatformApp can start gRPC server and execute request to LoginService and then ceep
token recived from Login request and then send the token to mocked servers.
The package "com.marionete.proto.rest" contains classes implementing simple Restful service which 
have to implement the endpoint: POST /marionete/useraccount/ .
There are tests in the package "com.marionete.backends" that test mocked servers in the 
backends module.
In the package "com.marionete.rest" are tests that test parallel simple rest server.
Other test classes are in the package "com.marionete.proto" that test MarionetePlatformApp services.

The implementation is done without any additional libraries, only with libraries that are 
imported in existing POM files.

###Future improvements:
There is not implemented classes to create JSON objects.
There is possible to have bugs and they should be fixed.
The MarionetePlatformApp should have more user-friendly functions.

### Application build and start

From project root directory can be started command "mvn clean install"
This should build all classes and then can start from IDE or command line 
MarionetePlatformApp class.

### Application manual

 The application has a user interface with  possibility 4 operations to be selected.
As can see:

    Main Menu: 
------------------
------------------
 1 Restful:POST /marionete/useraccount/
{
    "username":"bla",
    "password":"foo"
}and calls gRPC server, to obtain and keep the token
------------------
 2 Rest Connector UserAccount:Request:
    GET http://localhost:8898/marionete/user/
    HEADER Authorization: token
------------------
 3 Rest Connector AccountInfoRequest:
    GET http://localhost:8899/marionete/account/
    HEADER Authorization: token
------------------
 4 Exit
Enter your choice: 

Option 1 executes a request to the rest server and then creates a token
In option 2 and 3 this token is used as a Header.
With option 4, user exits from the application.