# Usar uma imagem base oficial do OpenJDK 17 (ou a sua versão Java)
FROM openjdk:17-jdk-slim

# Argumento para o nome do ficheiro JAR (ajuste se o nome/caminho for diferente)
ARG JAR_FILE=target/comunidade-java-0.0.1-SNAPSHOT.jar 

WORKDIR /app

# Copiar o ficheiro JAR para dentro do contentor
COPY ${JAR_FILE} app.jar


# A porta será definida pela variável de ambiente PORT fornecida pelo Render
EXPOSE 8080 

# Comando para executar a aplicação
ENTRYPOINT ["java","-jar","/app.jar"]