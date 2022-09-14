FROM amazoncorretto:11-alpine-jdk
COPY /target/posts-comments-alpha.jar alpha.jar
ENTRYPOINT ["java","-jar","/alpha.jar"]
