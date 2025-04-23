# Springboot Coindesk API

A Spring Boot application that integrates with the Coindesk API to fetch and manage cryptocurrency data. This service provides both direct access to Coindesk data and the ability to manage currency information through a RESTful API.

## Technology Stack

- Java 8
- Spring Boot 2.7.17
- Spring Data JPA
- H2 Database (In-memory database)
- Lombok
- Maven
- Jackson (JSON processing)
- Apache HttpClient

## Features

- RESTful API architecture
- Integration with Coindesk API
- Data persistence with JPA
- Exception handling
- Input validation
- HTTP client integration
- Currency management system

## Project Structure

```
src/main/java/com/example/coindesk/
├── config/         # Configuration classes
├── controller/     # REST API controllers
├── dto/           # Data Transfer Objects
├── entity/        # JPA entities
├── exception/     # Exception handling
├── repository/    # Data access layer
└── service/       # Business logic layer
```

## API Documentation

### Coindesk API Integration Endpoints

#### 1. Get Original Coindesk Data
```
GET /api/coindesk/original
```
Returns the original response from the Coindesk API.

#### 2. Get Transformed Coindesk Data
```
GET /api/coindesk/transformed
```
Returns transformed and formatted Coindesk data with additional information.

### Currency Management Endpoints

#### 1. List All Currencies
```
GET /api/currencies
```
Returns a list of all available currencies.

#### 2. Get Currency by ID
```
GET /api/currencies/{id}
```
Returns details of a specific currency.

#### 3. Create New Currency
```
POST /api/currencies
Content-Type: application/json

{
    "code": "string",
    "name": "string",
    "symbol": "string"
}
```
Creates a new currency entry.

#### 4. Update Currency
```
PUT /api/currencies/{id}
Content-Type: application/json

{
    "name": "string",
    "symbol": "string"
}
```
Updates an existing currency.

#### 5. Delete Currency
```
DELETE /api/currencies/{id}
```
Deletes a currency entry.

## Quick Start

### Prerequisites

- JDK 1.8 or higher
- Maven 3.x

### Installation Steps

1. Clone the repository
```bash
git clone https://github.com/yourusername/springboot-coindesk.git
```

2. Navigate to project directory
```bash
cd springboot-coindesk
```

3. Build the project
```bash
./mvnw clean package
```

4. Run the application
```bash
./mvnw spring-boot:run
```

The application will start at `http://localhost:8080`

## Development

### Compile
```bash
./mvnw clean compile
```

### Run Tests
```bash
./mvnw test
```

### Package
```bash
./mvnw package
```

## Response Format

All API endpoints return responses in the following format:
```json
{
    "success": boolean,
    "message": "string",
    "data": object
}
```

## Error Handling

The API uses standard HTTP status codes:
- 200: Success
- 400: Bad Request
- 404: Not Found
- 500: Internal Server Error

## Contributing

1. Fork the project
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

[MIT License](LICENSE)

## Contact

For any inquiries, please open an issue in the GitHub repository.