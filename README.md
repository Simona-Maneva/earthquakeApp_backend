# Earthquake Backend API

A Spring Boot REST API that fetches real-time earthquake data from the USGS API, processes it, stores it in a database, and exposes endpoints for filtering and managing the data.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- PostgreSQL
- Mockito & JUnit (Testing)

---

## Features

- Fetch real-time earthquake data from USGS GeoJSON API
- Parse and map external JSON data into domain model
- Filter earthquakes by magnitude (> 2.0)
- Filter earthquakes by time (Instant-based filtering)
- Store earthquake data in relational database
- Delete specific earthquake records
- Automatic data synchronization
- Global exception handling with custom exceptions
- Unit testing using Mockito

---

## Project Setup

### 1. Clone repository

```bash
git clone https://github.com/Simona-Maneva/earthquake-backend.git
cd earthquake-backend
```

### 2. Configure database

Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/earthquake_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

### 3. Run the application

```bash
mvn clean install
mvn spring-boot:run
```
Backend will start on http://localhost:8080

## API Endpoints

### 1. Sync Data

POST /api/earthquakes/sync

Fetches latest earthquake data from USGS API, filters it, and stores it in the database.

### 2. Get All Earthquakes

GET /api/earthquakes

Returns all stored earthquake records.

### 3. Filter By Time

GET /api/earthquakes/after?time={ISO-8601}

Returns earthquakes that occurred after the given timestamp.

### 4. Filter By Magnitude

GET /api/earthquakes/greater

Returns all earthquakes with a magnitude greater than 2.0

### 5. Delete Eartquake

DELETE /api/earthquakes/{id}

Deletes a specific earthquake record by ID.

---

## Business Logic

- Only earthquakes with magnitude > 2.0 are stored in the database

- Data is fetched from USGS hourly feed:
  https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson

- Time is handled using Instant
- Old data is cleared before inserting new synced data

---

## Exception Handling

The application handles:

- API failures (ApiException)
- Invalid JSON structure (ParsingException)
- Null or missing fields in response
- Database errors

Global exception handling is implemented using @RestControllerAdvice

---

## Testing

### Run Tests 

```bash
mvn test
```

### Covered scenarios:

- Filtering logic
- Sync logic (repository interaction)
- Time-based filtering
- Delete operations

---

## Optional Improvements Implemented

- Global exception handler (@RestControllerAdvice)
- Service layer unit tests (Mockito)
- Clean layered architecture
- External API integration (USGS)
- Automatic data refresh via sync endpoint

---

## Assumptions

- Earthquake data is fetched from USGS hourly feed
- Only earthquakes with magnitude > 2.0 are stored
- No authentication/authorization is implemented
- Database is locally configured
- Frontend is separate React application