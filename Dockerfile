FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /tmp/target/mercado-livre.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]