FROM ubuntu:latest
LABEL authors="mohamedogleh"



# Qaybta 1-aad: Build-ka barnaamijka (Mvn + MS JDK)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Qaybta 2-aad: Runtime-ka rasmiga ah ee Microsoft OpenJDK
FROM mcr.microsoft.com/openjdk/jdk:25-ubuntu
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]