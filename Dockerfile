FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

COPY target/BackendSocrates-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]


