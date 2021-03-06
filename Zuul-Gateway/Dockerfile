FROM openjdk:8-jdk-alpine
EXPOSE 8762
ARG JAR_FILE=target/Zuul-Gateway.jar
ADD ${JAR_FILE} Zuul-Gateway.jar
ENTRYPOINT ["java","-jar","/Zuul-Gateway.jar"]
