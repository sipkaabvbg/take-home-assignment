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
The package "com.marionete.proto.services" contains classes implementing Login and UserAccount
services and StreamObservers for asynchronous calls.
The package "com.marionete.proto" has gRPC server(MarioneteServer), MarioneteApp, 
LoginServiceClient and UserAccountServiceClient. All this classes have main method 
and can start independent.
The class MarioneteApp can start gRPC server and execute request to LoginService and then ceep
token recived from Login request and then send the token to UserAccountService.

There are tests in the package "com.marionete.backends" that test mocked servers in the 
backends module.
Other test classes are in the package "com.marionete.proto" that test MarioneteApp services.

There is proto file useraccount_service.proto with definition of UserAccountService.

The implementation is done without any additional libraries, only with libraries that are 
imported in existing POM files.

###Future improvements:
There is not implemented asynchronous tests and they should be implemented.
There is possible to have bugs and they should be fixed.
The MarioneteApp should have more user-friendly functions.

### Application build and start

From project root directory can start command "mvn clean install"
This should build all clasess and then can start from IDE or command line 
MarioneteApp class, or separately can start MarioneteServer and then 
LoginService client or UserAcountService.