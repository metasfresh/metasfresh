
# Notes:
# the idea to select openjdk:8-jdk-alpine as base image comes from "spring cloud in action"
# the rest partially comes from https://spring.io/guides/gs/spring-boot-docker/ and partially from our own metasfresh-docker repo

FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade && apk add netcat-openbsd

RUN mkdir -p /opt/metasfresh/material-dispo

# copy the
COPY ../configs/metasfresh-material-dispo.conf /opt/metasfresh/material-dispo/
COPY ../configs/metasfresh-material-dispo.properties /opt/metasfresh/material-dispo/
COPY start_app.sh /opt/metasfresh/material-dispo/
RUN chmod 700 /opt/metasfresh/material-dispo/start_app.sh

ADD metasfresh-material-dispo.jar /opt/metasfresh/material-dispo/metasfresh-material-dispo.jar

RUN sh -c 'touch /opt/metasfresh/material-dispo/metasfresh-material-dispo.jar'

ENV JAVA_OPTS=""
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /metasfresh-material-dispo.jar" ]

ENTRYPOINT ["/opt/metasfresh/material-dispo/start_app.sh"]