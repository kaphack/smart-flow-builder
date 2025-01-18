# Use the official Gradle image as a build stage
FROM gradle:7.3-jdk21 AS build
MAINTAINER balaji
ARG DB_URL
ARG DB_PWD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Set environment variables for Gradle build
ENV DB_URL=$DB_URL
ENV DB_PWD=$DB_PWD

RUN chmod +x ./gradlew

RUN ./gradlew build --no-daemon

# Use a slim version of OpenJDK 21 as the final image
FROM eclipse-temurin:21-jdk-alpine
RUN apk --no-cache update && apk --no-cache add netcat-openbsd
RUN mkdir /app
RUN mkdir /app/logs
# Copy JAR from the build stage
COPY --from=build /home/gradle/src/build/libs/smart-flow-builder-0.0.1-SNAPSHOT.jar /app/smart-flow-builder.jar

WORKDIR /app

CMD ["java", "-jar", "smart-flow-builder.jar"]
EXPOSE 8080
