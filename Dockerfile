################### BUILD ###################
FROM gradle:7.4.2-jdk17-alpine AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle bootJar

RUN ls -la /home/gradle/src/build/libs

################### RUN ###################
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/backend.jar

EXPOSE 8081

CMD ["java", "-jar", "/app/backend.jar"]