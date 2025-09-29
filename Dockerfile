# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/number-analysis-1.0.0.jar .
ENTRYPOINT ["java", "-jar", "number-analysis-1.0.0.jar"]
