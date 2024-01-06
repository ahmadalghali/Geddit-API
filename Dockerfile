FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
#WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
#COPY target/geddit-0.0.1-SNAPSHOT.jar app.jar

FROM openjdk:21-jdk
COPY --from=build /target/geddit-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=prod"]


#ENTRYPOINT ["java","-jar","app.jar"]
#COPY target/geddit-0.0.1-SNAPSHOT.jar geddit-0.0.1-SNAPSHOT.jar
#COPY target/*.jar app.jar

# Copy the JAR file into the container
#
# Package stage
#
#ARG JAR_FILE=target/*.jar
