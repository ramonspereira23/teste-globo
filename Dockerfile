FROM openjdk:20
MAINTAINER arthurvmlucena
COPY /build/libs/costmate-0.0.1-SNAPSHOT.jar costmate-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "costmate-0.0.1-SNAPSHOT.jar"]
#https://www.docker.com/blog/9-tips-for-containerizing-your-spring-boot-code/