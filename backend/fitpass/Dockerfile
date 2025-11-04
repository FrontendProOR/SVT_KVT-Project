# Use a stable OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven/Gradle build files (if applicable)
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn

# Copy the project files into the container
COPY src ./src

# Copy the images from the backend fitpass-bucket folder
COPY fitpass-bucket /app/fitpass-bucket

# Correct the path to the images folder (shared by frontend and backend)
# COPY ../../images /app/images

# Copy the init.sql file for MySQL
COPY src/main/resources/static/sql/init.sql ./src/main/resources/static/sql/init.sql

# Install dependencies and package the application
RUN ./mvnw clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "target/fitpass-0.0.1-SNAPSHOT.jar"]
