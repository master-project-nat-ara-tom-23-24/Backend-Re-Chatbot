# Use the official Gradle image as the base image
FROM gradle:7.6-jdk17 as builder

# Set the working directory in the image
WORKDIR /app

# Copy the gradle configuration files into the Docker image
COPY build.gradle.kts ./

# Copy the source code into the Docker image
COPY src ./src

COPY ./my-library-1.0.0.jar ./libs/

# Build the application
RUN gradle bootJar

# Use OpenJDK for runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the image
WORKDIR /app

# Copy the built jar file from the builder image
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Expose the application on port 8080
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "/app/app.jar"]