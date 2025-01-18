# Use a JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine AS build
MAINTAINER balaji

# Install Gradle
RUN apk update && apk add --no-cache bash curl && \
    curl -s https://downloads.gradle-dn.com/distributions/gradle-7.3-bin.zip -o gradle.zip && \
    unzip gradle.zip -d /opt && \
    rm gradle.zip && \
    ln -s /opt/gradle-7.3/bin/gradle /usr/bin/gradle

# Set Gradle and source code
ARG DB_URL
ARG DB_PWD
COPY . /home/gradle/src
WORKDIR /home/gradle/src

# Set environment variables for Gradle build
ENV DB_URL=$DB_URL
ENV DB_PWD=$DB_PWD

RUN chmod +x ./gradlew
RUN ./gradlew build --no-daemon

# Use a slim version of OpenJDK 21 as the final image
FROM eclipse-temurin:21-jdk-alpine
RUN apk --no-cache update && apk --no-cache add netcat-openbsd
RUN mkdir /app /app/logs

# Copy JAR from the build stage
COPY --from=build /home/gradle/src/build/libs/smart-flow-builder-0.0.1-SNAPSHOT.jar /app/smart-flow-builder.jar

WORKDIR /app

CMD ["java", "-jar", "smart-flow-builder.jar"]
EXPOSE 8080
