#Note: I played with openjdk:8-jdk-alpine, but wasn't able to get even the entrypoint shell script to actually run
FROM docker.metasfresh.com:6000/ubuntu:16.04

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image 
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

RUN apt update \
    && apt -y full-upgrade \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y install unzip \
    && apt -y autoremove \
    && apt -y clean

# create the metasfresh-folder as well as a folder for optional jars that might be volume-mounted from outside
RUN mkdir -p /opt/metasfresh/external-lib

# metasfresh_server.jar is just for historical reasons (service configs on some legacy servers etc)
COPY ./metasfresh_server.jar /opt/metasfresh/metasfresh-app.jar

# Explode the uber jar to make it easier to work with the docker image; 
# e.g. with the exploded uber jar, we can later copy or overwrite config files into the docker image that change the applications behavior.
# Note that the skript start_materialdispo_docker.sh is adapted to the exploded jar.
# Note2 that we need unzip; jar doesn't work, thx to https://stackoverflow.com/a/39653862/1012103
RUN cd /opt/metasfresh \
 && (unzip -q /opt/metasfresh/metasfresh-app.jar || echo "Ignore exit code $? because spring boot's uber jar starts with a shell script") \
 && rm /opt/metasfresh/metasfresh-app.jar

COPY configs/* /opt/metasfresh/

COPY ./start_app.sh /opt/metasfresh/
RUN chmod 700 /opt/metasfresh/start_app.sh

COPY ./reports/ /opt/metasfresh/reports



ENTRYPOINT ["/opt/metasfresh/start_app.sh"]
