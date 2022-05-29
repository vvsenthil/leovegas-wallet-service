FROM openjdk:8
EXPOSE 8080
ADD target/leovegas-wallet-service-1.0-SNAPSHOT.jar leovegas-wallet-service-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/leovegas-wallet-service-1.0-SNAPSHOT.jar"]