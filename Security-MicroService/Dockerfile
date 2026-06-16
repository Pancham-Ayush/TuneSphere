FROM eclipse-temurin:25

WORKDIR /app

COPY ./target/*.jar app.jar

EXPOSE 9090

CMD ["java","-jar","app.jar"]