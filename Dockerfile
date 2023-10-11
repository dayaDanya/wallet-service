FROM openjdk:17

COPY target/wallet-service-1.0-SNAPSHOT.jar /app.jar

CMD ["java", "-jar", "/app.jar"]
