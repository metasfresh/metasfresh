
#Note: I played with openjdk:8-jdk-alpine, but wasn't able to get event the entrypoint shell script to actually run

FROM ubuntu:16.04
RUN apt update && apt -y full-upgrade
RUN apt -y install netcat \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y install postgresql-client-9.5 \
    && apt -y autoremove \
    && apt -y autoclean


COPY ./configs/logback.xml /opt/metasfresh/metasfresh-material-dispo/
COPY ./configs/metasfresh-material-dispo.conf /opt/metasfresh/metasfresh-material-dispo/
COPY ./configs/metasfresh.properties /opt/metasfresh/metasfresh-material-dispo/

COPY ./start_materialdispo_docker.sh /opt/metasfresh/metasfresh-material-dispo/
RUN chmod 700 /opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh

COPY ./metasfresh-material-dispo-service.jar /opt/metasfresh/metasfresh-material-dispo/metasfresh-material-dispo.jar
RUN sh -c 'touch /opt/metasfresh/metasfresh-material-dispo/metasfresh-material-dispo.jar'

ENTRYPOINT ["/opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh"]
