FROM openjdk:8-jdk-alpine
EXPOSE 8002
ARG JAR_FILE=target/music-0.0.1.jar
ADD ${JAR_FILE} music-0.0.1.jar
ENTRYPOINT ["java","-jar","/music-0.0.1.jar"]
