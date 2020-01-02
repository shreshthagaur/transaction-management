#**Transfer Money API**

##Application Objective :

1. Create New Account 
2. Initiate Money Transfer between two parties

To access both these operations, below rest service endpoints are exposed:

1. _/create_

This endpoint creates a new account with minimum balance of zero.

2. _/transfer_

This endpoints trigger transfer of money between two parties.

##Technology Used

1. Spring-Boot
2. Java8

##H2 Embedded Database

To access embedded database, use below url : 

```
http://localhost:8090/h2_console
```

##Build Application

Execute below command to build and run the application:

```
mvn clean install spring-boot:run
```



