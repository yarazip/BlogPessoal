# Usa imagem base com Java 17 Alpine (leve)
FROM openjdk:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR da sua máquina para dentro do container
COPY target/blog-pessoal.jar app.jar

# Expõe a porta 8080 (pode ajustar conforme seu server.port)
EXPOSE 8080

# Comando para rodar sua aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
