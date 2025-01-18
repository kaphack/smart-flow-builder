# Use a lightweight JDK image as the base
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper files
COPY gradlew gradlew.bat settings.gradle build.gradle /app/
COPY gradle /app/gradle

# Copy the application source code
COPY src /app/src

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew bootJar --no-daemon

# Expose the application port
EXPOSE 8080

# Set the entry point to run the application
CMD ["java", "-jar", "build/libs/smart-flow-builder-0.0.1-SNAPSHOT.jar"]
