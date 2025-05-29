# Etapa 1: Build do JAR
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Executa o Maven para gerar o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final leve
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta (Render usa automaticamente a variável PORT)
EXPOSE 8080

# Comando para rodar sua aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
