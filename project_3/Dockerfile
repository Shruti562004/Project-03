FROM tomcat:9

# Remove default Tomcat apps (optional but recommended)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file
COPY target/project_3.war /usr/local/tomcat/webapps/project_3.war

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]