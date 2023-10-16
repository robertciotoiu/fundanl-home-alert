FROM openjdk:21-jdk
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} fundanl-home-alert.jar
ENTRYPOINT ["java","-jar","/fundanl-home-alert.jar"]