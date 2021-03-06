FROM openjdk:8-jdk-alpine
EXPOSE 8001
ARG JAR_FILE=target/Artist-0.0.1.jar
ADD ${JAR_FILE} Artist-0.0.1.jar
ENTRYPOINT ["java","-jar","/Artist-0.0.1.jar"]
