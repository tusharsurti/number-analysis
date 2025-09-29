# Advanced Number Availability & Prime Extraction Service

This is a Spring Boot application that performs availability analysis and prime extraction across a configurable number of randomly generated arrays.

## Tech Stack

- **Language:** Java 17 (Spring Boot 3)
- **Web Framework:** Spring WebFlux (Reactive)
- **Testing:** JUnit 5, Project Reactor Test
- **Build:** Maven
- **Container:** Docker

## How to Build and Run

### Prerequisites

- Java 17
- Maven 3.x
- Docker

### Building the Jar

To build the application, run the following command from the root directory:

```bash
mvn clean package
```

This will generate a `number-analysis-1.0.0.jar` file in the `target` directory.

### Running the Application

You can run the application directly from the command line:

```bash
java -jar target/number-analysis-1.0.0.jar
```

### Building and Running with Docker

To build the Docker image, run the following command from the root directory:

```bash
docker build -t number-analysis .
```

To run the application in a Docker container:

```bash
docker run --rm -p 8080:8080 number-analysis
```

### Building and Running with Docker Compose

This project includes a `docker-compose.yml` file to run the application along with a Zipkin instance for distributed tracing and a Spring Boot Admin server for monitoring.

To build and run the application with Docker Compose, use the following command:

```bash
docker-compose up --build
```

The application will be available at `http://localhost:8080`.
The Zipkin UI will be available at `http://localhost:9411`.
The Spring Boot Admin server will be available at `http://localhost:9090`.
The Swagger UI will be available at `http://localhost:8080/swagger-ui.html`.

## API Endpoint

The application exposes a single REST endpoint for performing the number analysis.

### `POST /analyze`

This endpoint triggers the analysis. It accepts a JSON request body with the following fields:

- `numberOfArrays` (integer, required): The number of arrays to generate.
- `arraySize` (integer, required): The size of each array.
- `rangeMin` (integer, required): The minimum value for the random numbers (inclusive).
- `rangeMax` (integer, required): The maximum value for the random numbers (exclusive).

**Example Request:**

```json
{
  "numberOfArrays": 5,
  "arraySize": 20,
  "rangeMin": 0,
  "rangeMax": 100
}
```

**Example Response:**

```json
{
  "generatedArrays": [
    [68, 2, 71, 7, 74, 12, 80, 19, 84, 55, 24, 91, 60, 30, 96, 35, 41, 10, 46, 50],
    // ... more arrays
  ],
  "availableNumbers": [
    0, 1, 3, 4, 5, 6, 8, 9, 11, // ... more numbers
  ],
  "largestPrime": "97"
}
```

## Actuator Endpoints & Security

The Spring Boot Actuator endpoints are exposed on a separate management port: `9091`.

The following endpoints are publicly accessible:
- `/actuator/health`
- `/actuator/info`

All other actuator endpoints are secured and require the `ACTUATOR_ADMIN` role. For demonstration purposes, a default user is configured in `application.properties` with the following credentials:
- **Username**: `admin`
- **Password**: `password`

## Code Quality Tools

This project is configured with several tools to ensure code quality and maintainability.

### Spotless

Spotless is used to enforce a consistent code style. To apply the formatting, run:

```bash
mvn spotless:apply
```

### Pitest

Pitest is used for mutation testing to measure the effectiveness of the tests. It runs automatically during the `verify` phase of the Maven build.

To run the mutation tests manually:

```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

The report will be generated in `target/pit-reports`.

### SonarQube

SonarQube is used for static code analysis. To run an analysis, you need a running SonarQube instance.

First, set the SonarQube host URL in your `pom.xml` or on the command line. Then, run the analysis:

```bash
mvn sonar:sonar
```

## System Design

The application is designed with a reactive, service-oriented architecture, with clear separation of concerns.

- **`NumberAnalysisApplication`**: The main entry point for the Spring Boot application.
- **`AnalysisController`**: A REST controller that exposes the `/analyze` endpoint and orchestrates the analysis process.
- **`ArrayGeneratorService`**: Responsible for generating the random arrays based on the provided configuration. This service is reactive and returns a `Mono<List<int[]>>`.
- **`NumberAvailabilityService`**: Responsible for finding the available numbers across all the generated arrays. This service uses Project Reactor's `parallel()` and `Schedulers.parallel()` to perform the existence checks in parallel, improving performance.
- **`PrimeService`**: A utility service for prime number calculations, including checking if a number is prime and finding the largest prime in a list. This service is also reactive.
- **`SecurityConfig`**: Configures the security rules for the application, including securing the actuator endpoints.
- **`GlobalExceptionHandler`**: A centralized exception handler that provides consistent, structured error responses for validation failures.

### Data Flow

1. A `POST` request is sent to the `/analyze` endpoint with the analysis configuration.
2. The `AnalysisController` calls the `ArrayGeneratorService` to generate the configured number of random integer arrays.
3. The generated arrays are then passed to the `NumberAvailabilityService`, which calculates the set of numbers that are not present in any of the arrays.
4. The list of available numbers is then passed to the `PrimeService` to find the largest prime number.
5. The `AnalysisController` combines the results into an `AnalysisResponse` and returns it as a JSON response.

The entire process is non-blocking, from the request to the response, thanks to the use of Spring WebFlux and Project Reactor.
Note: I've used copilot for reactive code snippets for reference but made sure to understand and modify as needed. 
 
 