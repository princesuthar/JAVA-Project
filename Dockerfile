# Use OpenJDK 21 to match your pom.xml
FROM openjdk:21-jdk-slim
# Copy the jar file from your target folder
COPY target/prescription-management-0.0.1-SNAPSHOT.jar app.jar
# Expose the port Spring Boot runs on
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]