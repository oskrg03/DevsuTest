
# Etapa 1: build con Maven
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

COPY init-dbs.sh /docker-entrypoint-initdb.d/init-dbs.sh
RUN chmod +x /docker-entrypoint-initdb.d/init-dbs.sh

# Etapa 2: usar el JAR en un contenedor más liviano
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]