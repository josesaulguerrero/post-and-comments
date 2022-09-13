FROM amazoncorretto:11-alpine-jdk
COPY /out/artifacts/alpha_jar/alpha.jar alpha.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/alpha.jar"]
