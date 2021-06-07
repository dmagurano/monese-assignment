# Monese Assignment

## Prerequisites
- Java 11
- Maven
- Docker

## Setup

Run `db_setup.sh` on your terminal. This will create a docker container running SQL Server, 
which is required to run  the application and run the tests
- The DB will be listening on port 4567

## How to run

Run `mvn spring-boot:run` on your terminal. The application will be listening for requests on port 8080.

## Tests

To run the test, you can run `mvn test` on your terminal.\
An E2E test that sends money from one account to another 
and verifies that resulting account balances are correct is located in the `TransactionE2ETest` class.

## API

The application starts with 2 predefined accounts:

1. Account Id: 1, Balance: 200
2. Account Id: 2, Balance: 300

- In order to request an account statement with the list of transactions,
you can send an `HTTP GET` request to the following endpoint:
  - `GET /account/{accountId}` where accountId is the account you're requesting the statement for.
  - Example: `GET /account/1`
  - Response: 
    ```json
    {
        "id": 1,
        "balance": 201.78,
        "transactions": [
            {
                "transactionId": 1,
                "foreignAccount": 2,
                "amount": -5.34,
                "timestamp": "2021-06-07T18:46:06.21"
            },
            {
                "transactionId": 2,
                "foreignAccount": 2,
                "amount": 7.12,
                "timestamp": "2021-06-07T18:46:10.4"
            }
        ]
    }
    ```
- In order to create a new transaction between two accounts,
  you can send a `POST` request to the `/transaction` endpoint, with the following payload:
  ```json
     {
        "sourceAccount": <the account id where the money is sent from>,
        "destinationAccount": <the account id receiving the money>,
        "amount": <the transaction amount, e.g. 5.34>
     }
  ```
  - Example: 
    - Request `/POST transaction`:
      ```json
      {
        "sourceAccount": 1,
        "destinationAccount": 2,
        "amount": 3
      }
      ```
    - Response:
      ```json
      {
        "id": 2,
        "sourceAccount": 2,
        "destinationAccount": 1,
        "amount": 5.34,
        "timestamp": "2021-06-07T18:46:10.40035"
      }
      ```



