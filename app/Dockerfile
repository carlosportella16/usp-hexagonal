# Etapa 1: Build do projeto
FROM openjdk:21 AS builder
WORKDIR /app

# Copiar arquivos do projeto para o container
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

# Verificar a existência do Maven Wrapper e conceder permissões de execução
RUN ls -la ./mvnw && chmod +x ./mvnw

# Executar o Maven para construir o projeto
RUN ./mvnw clean package -DskipTests

# Etapa 2: Construir a imagem final
FROM openjdk:21-jdk-slim

# Criar um grupo e um usuário não privilegiado
ARG USER_NAME=appuser
ARG USER_ID=1000
ARG USER_GROUP=appgroup
ARG USER_GROUP_ID=1000

RUN groupadd -g ${USER_GROUP_ID} ${USER_GROUP} \
    && useradd -m -u ${USER_ID} -g ${USER_GROUP} ${USER_NAME}

WORKDIR /app

# Copiar o JAR gerado da etapa anterior (e mudar o dono para o usuário criado)
COPY --from=builder --chown=${USER_NAME}:${USER_GROUP} /app/target/*.jar app.jar

# Mudar para o usuário não privilegiado
USER ${USER_NAME}

# Expor a porta da aplicação
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
