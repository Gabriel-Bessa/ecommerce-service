FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=builder target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]