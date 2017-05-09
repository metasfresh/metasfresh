
# the base image comes from "spring cloud in action"
# the rest mostly comes from https://spring.io/guides/gs/spring-boot-docker/
FROM openjdk:8-jdk-alpine

RUN apk update && apk upgrade && apk add netcat-openbsd && apk add sed

COPY ../sources/configs/logback.xml /opt/metasfresh/metasfresh-material-dispo/
COPY ../sources/configs/metasfresh-material-dispo.conf /opt/metasfresh/metasfresh-material-dispo/
COPY ../sources/configs/metasfresh.properties /opt/metasfresh/metasfresh-material-dispo/

COPY ../sources/configs/start_materialdispo_docker.sh /opt/metasfresh/metasfresh-material-dispo/
RUN chmod 700 /opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh

COPY metasfresh-material-dispo.jar /opt/metasfresh/metasfresh-material-dispo
RUN sh -c 'touch /opt/metasfresh/metasfresh-material-dispo/metasfresh-material-dispo.jar'

ENTRYPOINT ["/opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh"]
