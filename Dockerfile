# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-oracle

# Set the working directory
WORKDIR /app

ARG JAR_FILE=target/*.jar
# Copy the JAR file into the container
COPY target/geddit-0.0.1-SNAPSHOT.jar /app/geddit-0.0.1-SNAPSHOT.jar

# Expose a port (if your application listens on a specific port)
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "geddit-0.0.1-SNAPSHOT.jar"]
