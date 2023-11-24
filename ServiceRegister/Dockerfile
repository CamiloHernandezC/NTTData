FROM maven:3.6.3-openjdk-11-slim AS build 
COPY src /home/eureka/src
copy pom.xml /home/eureka
RUN mvn -f /home/eureka/pom.xml clean package

FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY --from=build "home/eureka/target/ServiceRegister-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]