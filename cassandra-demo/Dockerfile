FROM dockerfile/java:oracle-java8

ADD target/cassandra-demo-0.0.1-SNAPSHOT.jar /usr/share/myservice/myservice.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar"]