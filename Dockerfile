FROM openjdk:17-slim

MAINTAINER Grupo 10

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

COPY ./target/customers-1.0.jar /usr/bin

WORKDIR /usr/bin
RUN chown -R appuser:appgroup /usr/bin

USER appuser

ENTRYPOINT ["java", "-jar", "customers-1.0.jar"]
EXPOSE 8080
