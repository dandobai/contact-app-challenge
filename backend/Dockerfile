# 1. fázis: Építés (Build stage)
FROM gradle:8-jdk21-alpine AS build
WORKDIR /app

# Csak a Gradle beállításokat másoljuk át először (így gyorsabb a cache-elés)
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Másoljuk a forráskódot
COPY src ./src

# Lefuttatjuk a buildet a konténeren belül (tesztek nélkül a sebességért)
RUN gradle clean build -x test --no-daemon

# 2. fázis: Futtatás (Run stage)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Kimásoljuk az első fázisban legyártott JAR-t
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

RUN mkdir -p /app/data
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]