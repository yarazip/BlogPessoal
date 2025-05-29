# Etapa 1 - Build do projeto usando Maven diretamente
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copia o pom.xml e baixa dependências (cache otimizado)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia todo o código fonte
COPY . .

# Executa o Maven para gerar o JAR (sem rodar testes)
RUN mvn clean package -DskipTests

# Etapa 2 - Imagem final leve com apenas o JAR
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta (Render ou outra nuvem pode mapear)
EXPOSE 8080

# Comando para rodar o Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
