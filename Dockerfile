FROM maven:3.9-eclipse-temurin-23 AS build
WORKDIR /
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package -DskipTests

FROM eclipse-temurin:23-jdk
WORKDIR /
COPY --from=build /target/*.jar rest-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "rest-api.jar"]
