FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/Eureka-Server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761

CMD ["java","-jar","app.jar"]