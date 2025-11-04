# Authorization Service

A Spring Boot-based authorization service that provides JWT token-based authentication and user management capabilities. The service is built using Spring Boot 3.2.5 and Java 21.

## Features

- User registration and login
- JWT token-based authentication
- Role-based authorization
- H2 in-memory database
- Swagger UI for API documentation
- Spring Security integration

## Prerequisites

- Java 21 or higher
- Maven 3.8.x or higher
- Git

## Getting Started

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on port 8081.

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
- http://localhost:8081/swagger-ui.html

## API Endpoints

### Authentication

#### Register a New User
```http
POST /api/auth/signup
Content-Type: application/json

{
    "username": "user",
    "email": "user@example.com",
    "password": "password123"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "username": "user",
    "password": "password123"
}
```

Response includes a JWT token that should be included in subsequent requests.

## Configuration

The main configuration can be found in `application.yml`. Key configurations include:

- Server port: 8081
- H2 Database settings
- JWT token settings
- Swagger UI configuration

## Security

- JWT token expiration: 24 hours
- Password encryption using BCrypt
- Role-based access control
- Protected endpoints require valid JWT token

## Database

The application uses H2 in-memory database with the following settings:
- Console URL: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:mem:authdb
- Username: sa
- Password: password

## Built With

- Spring Boot 3.2.5
- Spring Security
- JSON Web Tokens (JWT)
- H2 Database
- Swagger UI/OpenAPI
- Maven
- Java 21

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── demo/
│   │               ├── config/
│   │               ├── controller/
│   │               ├── dto/
│   │               ├── model/
│   │               ├── repository/
│   │               ├── security/
│   │               └── service/
│   └── resources/
│       └── application.yml
```
