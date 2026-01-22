# 1. Java 21 Alpine
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# 2. A Gradle build során keletkező JAR fájl bemásolása
# A csillag (*) segít, hogy a verziószámtól függetlenül megtalálja a fájlt
COPY build/libs/*-SNAPSHOT.jar app.jar

# 3. Adatbázis mappa létrehozása
RUN mkdir -p /app/data

# 4. Port
EXPOSE 8080

# 5. Indítás - hozzáadtunk egy optimalizációt az SQLite-hoz
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]