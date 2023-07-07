FROM tomcat:9-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/OnlineLearningAcademy-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080