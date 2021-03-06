FROM openjdk:8-jdk-alpine
EXPOSE 8003
ARG JAR_FILE=target/Playlist-0.0.1.jar
ADD ${JAR_FILE} Playlist-0.0.1.jar
ENTRYPOINT ["java","-jar","/Playlist-0.0.1.jar"]
