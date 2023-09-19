
# Purchase Transaction Service

The Transactions Service is a Spring Boot application that provides functionality for managing and converting purchase transactions. It allows users to create, retrieve, and convert transactions to different currencies based on exchange rates.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) 11 or higher
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)
## Installation

### Installation

Follow these steps to set up and run the Transactions Service:

1. Clone the repository:

   ```bash
   $ git clone https://github.com/TiagoGIM/wex-challenger.git
   ```
2. Navigate to the project directory:
    ```bash
    $ cd wex-challenger

    ```
3. Build the project using Maven:

    ```bash
    $    mvn clean install
    ```
### Database

This project uses an in-memory H2 database for storing transactions. This means that data is stored only temporarily during the application's runtime and does not persist between restarts.

### Run the application:
    

 ```bash
$ mvn spring-boot:run

```

### Running Tests

To run the project's tests, you can use the following Maven command:

```bash
$ mvn test
```

5. Access the API documentation:

Open a web browser and go to http://localhost:8080/swagger-ui.html to view and interact with the API using Swagger.

Explore the API endpoints and start managing and converting purchase transactions!

Feel free to use tools like Postman or curl to make API requests as well.

## API Reference

#### Get a transaction with converted amount

```http
  GET /api/public/transactions
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `currency` | `string` | **Required**. desired currency (ex.: Real ) |
| `id` | `string` | **Required**. UUID from existente transaction |

#### Post purchase transactions

```http
  POST /api/transaction
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `body`      | `Transaction` | **Required**. date, description and amount |




## Acknowledgements

 - [Spring Quickstart](https://spring.io/quickstart)
 - [Guide to Spring Boot REST API Error Handling](https://www.toptal.com/java/spring-boot-rest-api-error-handling#:~:text=ExceptionHandler%20is%20a%20Spring%20annotation,thrown%20within%20this%20controller%20only.)
 - [springdoc-openapi](https://springdoc.org/)


## License

[MIT](https://choosealicense.com/licenses/mit/)

