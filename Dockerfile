FROM openjdk:11

RUN mkdir -p /opt/app

COPY target/gcp-connector-0.0.1-SNAPSHOT.jar /opt/app

CMD ["java", "-cp", "/opt/app/gcp-connector-0.0.1-SNAPSHOT.jar", "com.example.gcpconnector.GcpConnectorApplication"]