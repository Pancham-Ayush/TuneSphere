FROM eclipse-temurin:25
WORKDIR /app
copy ./target/*.jar app.jar
EXPOSE 8761
cmd ["java","-jar","app.jar"]