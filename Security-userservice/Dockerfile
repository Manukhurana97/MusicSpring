FROM openjdk:8-jre-alpine
EXPOSE 8000
ADD target/UserService.jar
ENTRYPOINT ["java", "-jar", "/UserService.jar"]
