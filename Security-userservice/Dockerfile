FROM openjdk:8-jdk-alpine
EXPOSE 8000
ARG JAR_FILE=target/UserService.jar
ADD ${JAR_FILE} UserService.jar
ENTRYPOINT ["java","-jar","/UserService.jar"]
