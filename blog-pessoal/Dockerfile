# Etapa 1 - Build do projeto usando Maven
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

COPY pom.xml .  
COPY mvnw .  
COPY .mvn .mvn  

RUN ./mvnw dependency:go-offline  

COPY . .  

RUN ./mvnw clean package -DskipTests  

# Etapa 2 - Imagem final leve com apenas o JAR
FROM openjdk:17-jdk-alpine  

WORKDIR /app  

COPY --from=builder /app/target/*.jar app.jar  

EXPOSE 8080  

ENTRYPOINT ["java", "-jar", "app.jar"]
