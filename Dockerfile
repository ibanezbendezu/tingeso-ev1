FROM openjdk:17
ARG JAR_FILE=target/mueblesstgo.jar
COPY ${JAR_FILE} mueblesstgo.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/mueblesstgo.jar"]