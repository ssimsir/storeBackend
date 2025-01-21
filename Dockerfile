FROM openjdk:23

COPY target/store-0.0.1-SNAPSHOT.jar store.jar 

ENTRYPOINT ["java", "-jar", "/store.jar"]