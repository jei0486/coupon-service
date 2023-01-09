#FROM openjdk:17-ea-11-jdk-slim
FROM openjdk:11.0.11-jre-slim
VOLUME /tmp
COPY build/lib/coupon-api.jar coupon-api.jar
# RUN mkdir /app
# COPY ./build/libs/*-SNAPSHOT.jar ./app/application.jar
ENTRYPOINT ["java", "-jar", "coupon-api.jar"]