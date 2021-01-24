FROM openjdk:8-jdk-alpine
ADD target/TinyUrlWebserver-0.0.1-SNAPSHOT.jar TinyUrlWebserver-0.0.1-SNAPSHOT.jar
EXPOSE 9000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=test","-jar","TinyUrlWebserver-0.0.1-SNAPSHOT.jar"]