# API Platform

A Spring Boot backend service with MySQL database.

## Tech Stack

- **Java** 17
- **Spring Boot** 3.2.5
- **Spring Data JPA** (Hibernate)
- **MySQL** 8.x
- **Lombok**
- **Maven**

## Project Structure

```
src/main/java/com/vuong/api_platform/
├── ApiPlatformApplication.java    # Main entry point
├── config/                        # Configuration classes
├── controller/                    # REST controllers
├── domain/                        # JPA entities
├── exception/                     # Exception handling
├── repository/                    # Data access layer
└── service/                       # Business logic
```

## Prerequisites

- JDK 17+
- MySQL 8.x running on `localhost:3306`
- Maven 3.8+

## Getting Started

1. **Create the MySQL database** (auto-created if `createDatabaseIfNotExist=true`):
   ```sql
   CREATE DATABASE api_platform;
   ```

2. **Update database credentials** in `src/main/resources/application.yml` if needed.

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. The server starts on **http://localhost:8080**.

## API Health Check

```
GET http://localhost:8080/actuator/health
```
