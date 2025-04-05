FROM eclipse-temurin:22-jdk-alpine

# 🛠️ Instala certificados SSL y cliente curl (opcional para pruebas)
RUN apk add --no-cache ca-certificates curl

COPY BackendSocrates/target/BackendSocrates-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]





