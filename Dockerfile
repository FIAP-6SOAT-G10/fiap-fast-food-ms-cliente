FROM openjdk:17-slim

MAINTAINER Grupo 10

ENV AWS_ACCESS_KEY_ID=${awsAccessKeyId}
ENV AWS_SECRET_ACCESS_KEY=${awsSecretAccessKey}
ENV AWS_SESSION_TOKEN=${awsSessionToken}

COPY ./target/fiap-fast-food-ms-cliente-1.0.jar /usr/bin

WORKDIR /usr/bin
ENTRYPOINT java -jar fiap-fast-food-ms-cliente-1.0.jar
EXPOSE 8080
