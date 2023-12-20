#
# Build stage
#
FROM openjdk:21-jdk-oracle AS build
COPY . .
RUN #mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:21-jdk
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar", "--spring.profiles.active=prod"]