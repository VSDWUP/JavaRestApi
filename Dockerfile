FROM openjdk:17

COPY target/Library.jar Library.jar

ENTRYPOINT ["java", "-jar", "/Library.jar"]