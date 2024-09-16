# Job Radar API

Job Radar API is a backend service that automatically collects job posting data from an external API. The service provides functionality to retrieve, store, and manage job posting data in database with endpoints for listing jobs, retrieving latest jobs, and collecting location-based statistics. The project is built using Java 21, Spring Boot, and H2 Database, and JPA.

## Table of Contents

- [Technologies](#technologies)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Database](#database)
- [Testing](#testing)
- [License](#license)

## Technologies:
- **Backend**: Java v21.0.1, Spring Boot v3.3.3, Maven, Lombok
- **Database**: H2 Database (in-memory for development/testing)
- **Testing**: JUnit 5 and Mockito
- **Tools**: Intellij IDEA

## Features
- Fetches job listings from an external API and stores them in an H2 database.
- Provides a REST API with the following functionalities:
    - List all jobs with pagination and sorting.
    - Get top 10 most recent jobs.
    - Generate job statistics based on location.
- Periodically fetches and updates job listings to ensure the data remains current.
- Unit tests for service and controller layers using JUnit and Mockito.

## Installation
1. Clone the repository:

```
git clone https://github.com/your-repository/job-radar-api.git
cd job-radar-api
```

2. Build the project:

```
./mvnw clean install
```

3. Run the application:

```
./mvnw spring-boot:run
```

The application will start on http://localhost:8080.

## Usage

### Configuring the API URL

The API URL for fetching jobs is configurable in the application.properties file:

```
api.url=https://www.example.com/api/job-board-api
```

### H2 Database
The application uses an H2 in-memory database by default. To access the H2 database console, navigate to:

```
http://localhost:8080/h2-console
```

- Username: sa
- Password: (leave blank)

### Scheduled Data Fetching

The service periodically fetches new job listings every 3 minutes. You can adjust the fetch rate by modifying the @Scheduled annotation in the JobService class.

## Endpoints

| Method   | Endpoint       | Description   |
| ---------|:--------------:|:-------------:|
| GET      | /api/jobs      | Retrieve all jobs with pagination & sorting|
| GET      | /api/jobs/top10| Retrieve top 10 most recent jobs           |
| GET      | /api/jobs/stats| Get statistics of job counts by location   |

### Example Requests

1. Get All Jobs with Pagination:

```
GET http://localhost:8080/api/jobs?page=0&size=10&sortBy=createdAt
```

2. Get Top 10 Latest Jobs:

```
GET http://localhost:8080/api/jobs/top10
```

3. Get Location Statistics:

```
GET http://localhost:8080/api/jobs/stats
```

## Database

The H2 database schema is generated automatically using JPA annotations from the Job entity. The jobs table stores job listings, with the following columns:

- **id:** Auto-generated ID
- **slug:** Job identifier
- **title:** Job title
- **company_name:** Company offering the job
- **description:** Job description
- **location:** Job location
- **remote:** Boolean indicating if the job is remote
- **url:** URL to the job posting
- **created_at:** Job creation date

## Testing

Unit tests for services and controllers have been implemented using JUnit 5 and Mockito.

### Running the Tests

To execute all tests:

```
./mvnw test
```

### Test Coverage

- **Service Tests:** Covers the main business logic for fetching jobs, retrieving job statistics, and handling job data storage.
- **Controller Tests:** Ensures the correctness of the API responses for job listings, top 10 jobs, and location statistics.

## License
This project is licensed under the terms of the MIT License.