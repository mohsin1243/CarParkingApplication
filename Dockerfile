FROM openjdk:8
WORKDIR /app
ADD build/libs/CarParkingSytstem-0.0.1-SNAPSHOT.jar .
EXPOSE 9117
ENTRYPOINT ["java", "-jar", "CarParkingSytstem-0.0.1-SNAPSHOT.jar"]
