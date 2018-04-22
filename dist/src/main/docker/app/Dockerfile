FROM ubuntu:16.04

RUN apt update \
    && apt -y full-upgrade \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y autoremove \
    && apt -y clean

RUN mkdir -p /opt/metasfresh

COPY configs/* /opt/metasfresh/

COPY ./start_app.sh /opt/metasfresh/
RUN chmod 700 /opt/metasfresh/start_app.sh

COPY ./reports/ /opt/metasfresh/reports

# metasfresh_server.jar is just for historical reasons (service configs on some legacy servers etc)
COPY ./metasfresh_server.jar /opt/metasfresh/metasfresh-app.jar
RUN sh -c 'touch /opt/metasfresh/metasfresh-app.jar'

ENTRYPOINT ["/opt/metasfresh/start_app.sh"]
