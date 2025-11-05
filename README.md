# Authorization Service

A Spring Boot-based authorization service that provides JWT token-based authentication and user management capabilities. The service is built using Spring Boot 3.2.5 and Java 21.

## Features

- User registration and login
- JWT token-based authentication
- Role-based authorization with three roles (USER, MODERATOR, ADMIN)
- Method-level security with @PreAuthorize annotations
- H2 in-memory database
- Swagger UI for API documentation
- Spring Security integration
- Comprehensive error handling with standardized responses

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
- Role-based access control (RBAC)
  - ROLE_USER: Basic access to protected endpoints
  - ROLE_MODERATOR: Access to moderation features (/api/mod/**)
  - ROLE_ADMIN: Full access including admin features (/api/admin/**)
- Protected endpoints require valid JWT token
- Method-level security using @PreAuthorize annotations

## Error Handling

The service provides standardized error responses for various scenarios:

- 400 Bad Request: Validation errors
- 401 Unauthorized: Invalid credentials or JWT token
- 403 Forbidden: Insufficient permissions
- 404 Not Found: Resource not found
- 405 Method Not Allowed: Unsupported HTTP method
- 409 Conflict: Resource already exists (e.g., duplicate username)
- 500 Internal Server Error: Unexpected server errors

All error responses follow a consistent format:
```json
{
    "status": 404,
    "error": "Not Found",
    "message": "The requested resource was not found",
    "path": "/api/resource",
    "details": null
}
```

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
