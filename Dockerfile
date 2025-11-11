# STAGE 1: Build the application using Maven
FROM maven:3.6.3-jdk-8 as builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the source code
COPY ./EmployeeTracker_secure /app/

# Build the project, this will also download dependencies
RUN mvn -f /app/pom.xml clean package

# STAGE 2: Create the final image using Tomcat
FROM tomcat:9.0-jre8-slim

# Remove the default ROOT app
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the generated .war file from the builder stage to the tomcat webapps directory
COPY --from=builder /app/target/EmployeeTracker.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080
