FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
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
