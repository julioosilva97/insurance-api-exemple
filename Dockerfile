FROM openjdk:17

WORKDIR /app

COPY build/libs/insurances-api.jar /app/

ENTRYPOINT java -jar /app/insurances-api.jar
