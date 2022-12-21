FROM maven:3.8.4-jdk-8 AS build
WORKDIR /mock-scale
COPY . /mock-scale
RUN mvn clean package

FROM openjdk:8-jre-bullseye
WORKDIR /app

COPY --from=build /mock-scale/target/*.jar /app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-server","-jar","/app.jar"]