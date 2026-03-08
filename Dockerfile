FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ..
RUN chmod _x mvnw
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/Bookly-OnlineBookStore-0.0.1-SNAPSHOT.jar"]