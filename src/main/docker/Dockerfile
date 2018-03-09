
#Note: I played with openjdk:8-jdk-alpine, but wasn't able to get even the entrypoint shell script to actually run

FROM docker.metasfresh.com:6000/ubuntu:16.04

# note that we put all of this into one command
# because otherwise, the update and full-update might not be performed (still in cache)
# and the installs might fail, because e.g. openjdk might not yet be in the cache, however URLs might have been changed
RUN apt update \
    && apt -y full-upgrade \
    && apt -y install netcat \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y install dos2unix \
    && apt -y autoremove \
    && apt -y autoclean

RUN mkdir -p /opt/metasfresh/metasfresh-webui-api

# COPY configs/logback.xml /opt/metasfresh/metasfresh-webui-api/
COPY configs/metasfresh-webui-api.conf /opt/metasfresh/metasfresh-webui-api/
COPY configs/metasfresh.properties /opt/metasfresh/metasfresh-webui-api/

COPY ./start_metasfresh-webui-api_docker.sh /opt/metasfresh/metasfresh-webui-api/

# I don't understand why I have to do this. I would assume that when I commit this sh file on windows with lf eols and
# then check it out on a linux machine, it has lf eols. But aparently it doesn't.
# At any rate, thx to https://stackoverflow.com/a/41424794/1012103
RUN dos2unix /opt/metasfresh/metasfresh-webui-api/start_metasfresh-webui-api_docker.sh

# make our start command executable
RUN chmod 700 /opt/metasfresh/metasfresh-webui-api/start_metasfresh-webui-api_docker.sh

COPY ./metasfresh-webui-api.jar /opt/metasfresh/metasfresh-webui-api/metasfresh-webui-api.jar
RUN sh -c 'touch /opt/metasfresh/metasfresh-webui-api/metasfresh-webui-api.jar'

ENTRYPOINT ["/opt/metasfresh/metasfresh-webui-api/start_metasfresh-webui-api_docker.sh"]
