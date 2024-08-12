FROM openjdk:8

WORKDIR /tckhcn

COPY target/tapchikhcn-0.0.1-SNAPSHOT.jar tckhcn/tapchikhcn-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "tckhcn/tapchikhcn-0.0.1-SNAPSHOT.jar"]
