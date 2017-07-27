
#Note: I played with openjdk:8-jdk-alpine, but wasn't able to get event the entrypoint shell script to actually run

FROM ubuntu:16.04

# note that we put all of this into one command
# because otherwise, the update and full-update might not be performed (still in cache)
# and the installs might fail, because e.g. openjdk might not yet be in the cache, however URLs might have been changed
RUN apt update \
    && apt -y full-upgrade \
    && apt -y install netcat \
    && apt -y install openjdk-8-jdk-headless \
    && apt install dos2unix \
    && apt -y autoremove \
    && apt -y autoclean


COPY ./configs/logback.xml /opt/metasfresh/metasfresh-material-dispo/
COPY ./configs/metasfresh-material-dispo.conf /opt/metasfresh/metasfresh-material-dispo/
COPY ./configs/metasfresh.properties /opt/metasfresh/metasfresh-material-dispo/

COPY ./start_materialdispo_docker.sh /opt/metasfresh/metasfresh-material-dispo/

# i don't understand why i have to do this. I would assume that when I check out this sh file on a linux machine, it has lf eols, but aparently it doesn't
# at any rate, thx to https://stackoverflow.com/a/41424794/1012103
RUN dos2unix /opt/metasfresh/metasfresh-webui-api/start_metasfresh-webui-api_docker.sh

# make our start command executable
RUN chmod 700 /opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh

COPY ./metasfresh-material-dispo-service.jar /opt/metasfresh/metasfresh-material-dispo/metasfresh-material-dispo.jar
RUN sh -c 'touch /opt/metasfresh/metasfresh-material-dispo/metasfresh-material-dispo.jar'

ENTRYPOINT ["/opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh"]
