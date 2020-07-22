FROM gradle:4.10.3-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

FROM java:8-jdk-alpine
EXPOSE 8080
RUN mkdir /app
COPY ./build/libs/*.jar /app/package-indexer.jar
ENTRYPOINT ["java","-jar","/app/package-indexer.jar"]