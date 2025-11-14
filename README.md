# Auction Rest Server

Auction Rest Server is a RESTful API built with Spring Boot that manages an auction system including users, sales, bids, categories, and JWT-based authentication.
The API is documented with Swagger UI.

## Features

- JWT authentication & authorization
- CRUD for users, sales, categories
- Complete bidding system (place bid, credit checks, sale status)
- Multiple environment profiles: `mock`, `mongo`, `sql`
- Swagger UI integration
- Unit tests for all core services

## Configuration

The project uses a `.env` file (excluded from Git) to store environment variables:

```bash
pip install foobar
```

## Usage

```env
# Admin account
ADMIN_USERNAME=your_admin_username_here (email)
ADMIN_PASSWORD=your_admin_password

# MS SQL SERVER
MS_DB_URL=jdbc:sqlserver://localhost;databasename=AUCTION;
MS_DB_USERNAME=your_ms_username
MS_DB_PASSWORD=your_ms_password

# MONGO DB
MONGO_HOST=mongo_server_address
MONGO_PORT=mongo_port

# JWT SECRET
JWT_SECRET=your_jwt_secret_here_256bit
```
Le `.env` est automatiquement importé via :
```properties
spring.config.import=optional:file:.env[.properties]
```
## Running the project

```bash
mvn clean install
mvn spring-boot:run
```
When the API starts, fixtures will automatically generate sample data depending on the active profile
```properties
# Profile (mock, mongo, sql)
spring.profiles.active=mock
```
The API starts on:
[http://localhost:8080/](http://localhost:8080/)
→ automatically redirects to Swagger UI:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
## Tech Stack
Spring Boot

- Spring Web / Spring Security
- Spring Data (MongoDB + JPA with SQL Server)
- JWT auth
- Swagger / Springdoc OpenAPI
- JUnit 5 & Mockito
