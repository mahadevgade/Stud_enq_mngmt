

FROM openjdk:17

COPY target/stumgmt.jar stumgmt.jar

EXPOSE 9090 

ENTRYPOINT ["java", "-jar", "stumgmt.jar"]  