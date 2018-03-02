FROM ubuntu:16.04


RUN apt update && apt -y full-upgrade
RUN apt autoclean && apt update

RUN apt -y install netcat \
    && apt -y install curl \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y install postgresql-client-9.5 \
    && apt -y autoremove \
    && apt -y autoclean

RUN mkdir -p /opt/metasfresh

COPY configs/* /opt/metasfresh/
COPY configs/local_settings.properties /root/

COPY ./start_app.sh /opt/metasfresh/
RUN chmod 700 /opt/metasfresh/start_app.sh

COPY ./metasfresh-dist-serverRoot.jar /opt/metasfresh/metasfresh-app.jar
RUN sh -c 'touch /opt/metasfresh/metasfresh-app.jar'

ENTRYPOINT ["/opt/metasfresh/start_app.sh"]

# we currently run an embedded activemq broker in here..this is its port
EXPOSE 61616
