
#Note: I played with openjdk:8-jdk-alpine, but wasn't able to get event the entrypoint shell script to actually run

FROM ubuntu:16.04
RUN apt update && apt -y full-upgrade
RUN apt -y install netcat \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y autoremove \
    && apt -y autoclean

# COPY ./configs/logback.xml /opt/metasfresh/metasfresh-webui-api/
COPY ./configs/metasfresh-webui-api.conf /opt/metasfresh/metasfresh-webui-api/
COPY ./configs/metasfresh.properties /opt/metasfresh/metasfresh-webui-api/

COPY ./start_metasfresh-webui-api_docker.sh /opt/metasfresh/metasfresh-webui-api/
RUN chmod 700 /opt/metasfresh/metasfresh-webui-api/start_metasfresh-webui-api_docker.sh

COPY ./metasfresh-webui-api.jarr /opt/metasfresh/metasfresh-webui-api/metasfresh-webui-api.jar
RUN sh -c 'touch /opt/metasfresh/metasfresh-webui-api/metasfresh-webui-api.jar'

ENTRYPOINT ["/opt/metasfresh/metasfresh-webui-api/start_metasfresh-webui-api_docker.sh"]
