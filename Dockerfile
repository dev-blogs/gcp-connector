FROM openjdk11:latest

RUN mkdir /opt/app

COPY target/gcp-connector-0.0.1-SNAPSHOT.jar /opt/app

CMD ["java", "-cp", "/opt/app/gcp-connector-0.0.1-SNAPSHOT.jar", "com.example.gcpconnector.GcpConnector"]