FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/library-0.0.1.jar
EXPOSE 8080
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]