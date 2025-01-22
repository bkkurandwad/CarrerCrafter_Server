# Use an official Maven image to build the project
FROM maven:3.8.7-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a base image with Java for runtime
FROM eclipse-temurin:17-jre-alpine

# Install additional tools: g++, Python, Java, and Git
RUN apk update && apk add --no-cache \
    g++ \
    python3 \
    python3-dev \
    make \
    git \
    bash

# Verify installations (optional)
RUN g++ --version && python3 --version && java --version && git --version

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
