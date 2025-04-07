FROM eclipse-temurin:22-jdk-alpine

RUN apk add --no-cache ca-certificates

COPY /BackendSocrates/target/BackendSocrates-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

