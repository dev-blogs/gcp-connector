FROM openjdk:11

RUN mkdir -p /opt/app

COPY target/gcp-connector-0.0.1-SNAPSHOT-jar-with-dependencies.jar /opt/app
COPY data.txt /opt/app

CMD ["java", "-cp", "/opt/app/gcp-connector-0.0.1-SNAPSHOT-jar-with-dependencies.jar", "com.example.gcpconnector.GcpConnectorApplication"]