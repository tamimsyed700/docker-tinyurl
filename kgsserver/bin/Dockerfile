FROM openjdk:8
ADD KeyGenerationMicrsoservices/target/MyTinyUrl-0.0.1-SNAPSHOT.jar MyTinyUrl-0.0.1-SNAPSHOT.jar
ADD KeyGenerationMicrsoservices/GeoLite2-City.mmdb /
EXPOSE 9001
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=test","-jar","MyTinyUrl-0.0.1-SNAPSHOT.jar"]