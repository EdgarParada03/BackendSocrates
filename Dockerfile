FROM eclipse-temurin:22-jdk-alpine

# Instala certificados SSL para conectar con PostgreSQL en Render
RUN apk add --no-cache ca-certificates

# Copia el jar
COPY BackendSocrates/target/BackendSocrates-0.0.1-SNAPSHOT.jar app.jar

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]
