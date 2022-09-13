FROM amazoncorretto:11-alpine-jdk
COPY /target/alpha.jar alpha.jar
EXPOSE 8070/tcp
ENTRYPOINT ["java","-jar","/alpha.jar"]
