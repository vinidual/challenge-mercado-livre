FROM openjdk:8-jdk-alpine
COPY target/mercado-livre.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]