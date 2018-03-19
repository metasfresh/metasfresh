
#Note: I played with openjdk:8-jdk-alpine, but wasn't able to get even the entrypoint shell script to actually run

FROM docker.metasfresh.com:6000/ubuntu:16.04

# Note that we put all of this into one command
# because otherwise, the update and full-update might not be performed (still in cache)
# and the installs might fail, because e.g. openjdk might not yet be in the cache, however URLs might have been changed.
#
# Note that netcat is used to wait for the DMBS to be available on startup,
RUN apt update \
    && apt -y full-upgrade \
    && apt -y install netcat \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y install dos2unix \
    && apt -y autoremove \
    && apt -y autoclean

COPY ./configs/logback-spring.xml /opt/metasfresh/metasfresh-print/
COPY ./configs/metasfresh.properties /opt/metasfresh/metasfresh-print/

COPY ./start_print_docker.sh /opt/metasfresh/metasfresh-print/

# I don't understand why I have to do this. I would assume that when I commit this sh file on windows with lf eols and
# then check it out on a linux machine, it has lf eols. But aparently it doesn't.
# At any rate, thx to https://stackoverflow.com/a/41424794/1012103
RUN dos2unix /opt/metasfresh/metasfresh-print/start_print_docker.sh

# make our start command executable
RUN chmod 700 /opt/metasfresh/metasfresh-print/start_print_docker.sh

COPY ./metasfresh-print-service-standalone.jar /opt/metasfresh/metasfresh-print/metasfresh-print.jar
RUN sh -c 'touch /opt/metasfresh/metasfresh-print/metasfresh-print.jar'


ENTRYPOINT ["/opt/metasfresh/metasfresh-print/start_print_docker.sh"]
