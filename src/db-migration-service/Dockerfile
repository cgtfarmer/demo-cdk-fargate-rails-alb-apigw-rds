FROM maven:3.9.6-amazoncorretto-17 AS db-migration-service

ENV LANG=C.UTF-8 \
  APP_HOME=/usr/src/app \
  MAVEN_CONFIG=/root/.m2 \
  MAVEN_BUILD_REPO=/usr/share/maven/ref/repository

WORKDIR $APP_HOME

COPY . .

CMD mvn exec:java
