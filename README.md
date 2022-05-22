# WALLET MICROSERVICE

A simple wallet microservice running on the JVM that manages credit/debit transactions of the players.

## DESCRIPTION

This Rest API Application is to access monetary accounts with the current balance of a user. The balance can be updated by adding transactions to the user, 
either debit transactions (reducing funds) or credit transactions (adding funds).

## Functionalities Added 

1. A debit transaction will succeed only if will be a sufficient funds on the respective users account (balance amount - debit amount >= 0).
2. Unique global user id must be provided when registering a new account. 
3. Unique global transaction id must be provided when adding/reducing funds. 

### Added Api's: 
1. It will be possible to create a new wallet 
2. It will be possible to add a new transaction for registered users  
3. It will be possible to fetch all the transactions for the specified user id.  
4. It will be possible to fetch all the users accounts with their transactions. 
5. It will be possible to fetch the Wallet details with transactions based on the input userId
6. It will be possible to remove all the wallet details from the application 

## Api requirements and running instructions
1. Java 8
2. Maven 4 to build the application.
3. Transactions can be viewed from the database by hitting this url from browser : http://localhost:8080/h2-console/
4. In memory database H2 is used 
5. The schema and database related configuration can be found in this location :  leovegas-wallet-service\src\main\resources\application.properties
```
spring.datasource.url=jdbc:h2:mem:wallet     
spring.datasource.driverClassName=org.h2.Driver     
spring.datasource.username=sa
spring.datasource.password=sa 
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect    
spring.h2.console.enabled=true    
spring.jpa.show-sql=true
``` 
6. From the root folder of the application run this command : mvn clean install
7. To Run this application, open command prompt from root directory of this project and run this command : 
	mvn spring-boot:run
	(or)
java -jar target/leovegas-wallet-service-1.0-SNAPSHOT.jar 

8. To check that application started successfully go to browser and enter this URL : http://localhost:8080/wallets/test
        This should produce result as below : 
            Hello Application started Successfully !!!

# API Endpoints:

### Post Endpoints:

1. To Create a new Wallet, pass the following JSON in the body of the post request:
    
    #### Request : 
    URL: http://localhost:8080/wallets/users/create    
    ``` 
     {
	"userId":"user1",
    "transId":"tran1",
	"balance":100.0,
	"updatedBy":"Senthil"
     }
    ```     
    #### Response :
        
    ``` 
    {
    "walletId": 1,
    "userId": "user1",
    "balance": 100.0,
    "updatedBy": "Senthil",
    "transactions": [
        {
            "id": 1,
            "tranId": "tran1",
            "userId": "user1",
            "amount": 100.0,
            "transactionType": "CREDIT",
            "updatedBy": "Senthil"
        }
    ]
    }
    ``` 
    
2. To Create a new Transaction, pass the following JSON in the body of the post request:
   #### Request : 1 
    URL: http://localhost:8080/wallets/transactions/create
    ``` 
    {
    "userId":"user1",
    "transId":"tran2",
    "amount":10,
	"transactionType":"CREDIT",
	"updatedBy":"Senthil"
    }
    
    ``` 
   #### Response : 1
   ``` 
    {
    "walletId": 1,
    "userId": "user1",
    "balance": 110.00,
    "updatedBy": "Senthil",
    "transactions": [
        {
            "id": 1,
            "tranId": "tran1",
            "userId": "user1",
            "amount": 100.00,
            "transactionType": "CREDIT",
            "updatedBy": "Senthil"
        },
        {
            "id": 2,
            "tranId": "tran2",
            "userId": "user1",
            "amount": 10,
            "transactionType": "CREDIT",
            "updatedBy": "Senthil"
        }
    ]
   }
   
   ``` 
   #### Request : 2
    URL : http://localhost:8080/wallets/transactions/create
	
    ``` 
    {
    "userId":"user1",
    "transId":"tran3",
    "amount":30,
	"transactionType":"DEBIT",
	"updatedBy":"Senthil"
    }
    ``` 
    
    #### Response : 2
    ``` 
    {
    "walletId": 1,
    "userId": "user1",
    "balance": 80.00,
    "updatedBy": "Senthil",
    "transactions": [
        {
            "id": 1,
            "tranId": "tran1",
            "userId": "user1",
            "amount": 100.00,
            "transactionType": "CREDIT",
            "updatedBy": "Senthil"
        },
        {
            "id": 2,
            "tranId": "tran2",
            "userId": "user1",
            "amount": 10.00,
            "transactionType": "CREDIT",
            "updatedBy": "Senthil"
        },
        {
            "id": 3,
            "tranId": "tran3",
            "userId": "user1",
            "amount": 30,
            "transactionType": "DEBIT",
            "updatedBy": "Senthil"
        }
    ]
    }
    ``` 
 
### GET Endpoints : 

 1. To fetch all the transactions for a specific user :

    #### Request : 
    URL: http://localhost:8080/wallets/transactions/user1
    
    #### Response : 
    
    ``` 
    [
    {
        "id": 1,
        "tranId": "tran1",
        "userId": "user1",
        "amount": 100.00,
        "transactionType": "CREDIT",
        "updatedBy": "Senthil"
    },
    {
        "id": 2,
        "tranId": "tran2",
        "userId": "user1",
        "amount": 10.00,
        "transactionType": "CREDIT",
        "updatedBy": "Senthil"
    },
    {
        "id": 3,
        "tranId": "tran3",
        "userId": "user1",
        "amount": 30.00,
        "transactionType": "DEBIT",
        "updatedBy": "Senthil"
    }
    ]
    
    ``` 
    
   2. To fetch all the wallets :
   
   #### Request : 
   URL: http://localhost:8080/wallets
    
   #### Response : 
   
   ``` 
    [
    {
        "walletId": 1,
        "userId": "user1",
        "balance": 80.00,
        "updatedBy": "Senthil",
        "transactions": [
            {
                "id": 1,
                "tranId": "tran1",
                "userId": "user1",
                "amount": 100.00,
                "transactionType": "CREDIT",
                "updatedBy": "Senthil"
            },
            {
                "id": 2,
                "tranId": "tran2",
                "userId": "user1",
                "amount": 10.00,
                "transactionType": "CREDIT",
                "updatedBy": "Senthil"
            },
            {
                "id": 3,
                "tranId": "tran3",
                "userId": "user1",
                "amount": 30.00,
                "transactionType": "DEBIT",
                "updatedBy": "Senthil"
            }
        ]
    }
    ]
   ``` 
   
  3. To fetch wallet for a specific user :
  
  #### Request :
  URL: http://localhost:8080/wallets/users/user1
    
  #### Response : 
  
  ``` 
    {
    "walletId": 1,
    "userId": "user1",
    "balance": 80.00,
    "updatedBy": "Senthil",
    "transactions": [
        {
            "id": 1,
            "tranId": "tran1",
            "userId": "user1",
            "amount": 100.00,
            "transactionType": "CREDIT",
            "updatedBy": "Senthil"
        },
        {
            "id": 2,
            "tranId": "tran2",
            "userId": "user1",
            "amount": 10.00,
            "transactionType": "CREDIT",
            "updatedBy": "Senthil"
        },
        {
            "id": 3,
            "tranId": "tran3",
            "userId": "user1",
            "amount": 30.00,
            "transactionType": "DEBIT",
            "updatedBy": "Senthil"
        }
    ]
    }
  ```
  
### Delete Endpoints: 

1. To remove all wallets : 

 #### Request : 
 URL: http://localhost:8080/wallets/users/remove
    
 #### Response : 
 
  ``` 
    Wallets are removed Successfully!    
  ``` 
## Technology used
- H2 in memory database.
- Spring Boot, including Spring Data JPA.
- slf4j for logging.

## Features not implemented
1. Authentication and Authorization can be implemented using Spring Security.
