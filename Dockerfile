#
# Build stage
#
FROM maven:latest AS build
COPY . .
RUN mvn clean package -DskipTests





# Copy the JAR file into the container
#
# Package stage
#
FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
#COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
COPY target/geddit-0.0.1-SNAPSHOT.jar geddit-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","geddit-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]