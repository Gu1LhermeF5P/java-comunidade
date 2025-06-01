# Usar uma imagem base oficial do OpenJDK 17 (ou a sua versão Java)
FROM openjdk:17-jdk-slim

# Argumento para o nome do ficheiro JAR (ajuste se o nome/caminho do seu JAR for diferente)
# O seu pom.xml no Canvas tem artifactId "comunidade-java" e version "0.0.1-SNAPSHOT"
ARG JAR_FILE=target/comunidade-java-0.0.1-SNAPSHOT.jar 

WORKDIR /app

# Copiar o ficheiro JAR para dentro do contentor com um nome genérico
COPY ${JAR_FILE} app.jar

# A porta será definida pela variável de ambiente PORT fornecida pelo Render
# Se o seu application.properties tiver server.port=${PORT:10000}, está bom.

# Comando para executar a aplicação quando o contentor iniciar
ENTRYPOINT ["java","-jar","/app.jar"]