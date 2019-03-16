
#Note: I played with openjdk:8-jdk-alpine, but wasn't able to get even the entrypoint shell script to actually run
FROM docker.metasfresh.com:6000/ubuntu:16.04

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image 
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

# note that we put all of this into one command
# because otherwise, the update and full-update might not be performed (still in cache)
# and the installs might fail, because e.g. openjdk might not yet be in the cache, however URLs might have been changed
RUN apt update \
    && apt -y full-upgrade \
    && apt -y install netcat \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y install dos2unix \
    && apt -y install unzip \
    && apt -y autoremove \
    && apt -y autoclean

COPY ./metasfresh-material-dispo-service.jar /opt/metasfresh/metasfresh-material-dispo/metasfresh-material-dispo.jar

# Explode the uber jar to make it easier to work with the docker image; 
# e.g. with the exploded uber jar, we can later copy or overwrite config files into the docker image that change the applications behavior.
# Note that the skript start_materialdispo_docker.sh is adapted to the exploded jar.
# Note2 that we need unzip; jar doesn't work, thx to https://stackoverflow.com/a/39653862/1012103
RUN cd /opt/metasfresh/metasfresh-material-dispo \
 && (unzip -q /opt/metasfresh/metasfresh-material-dispo/metasfresh-material-dispo.jar || echo "Ignore exit code $? because spring boot's uber jar starts with a shell script") \
 && rm /opt/metasfresh/metasfresh-material-dispo/metasfresh-material-dispo.jar

COPY ./configs/logback.xml /opt/metasfresh/metasfresh-material-dispo/
COPY ./configs/metasfresh-material-dispo.conf /opt/metasfresh/metasfresh-material-dispo/
COPY ./configs/metasfresh.properties /opt/metasfresh/metasfresh-material-dispo/

COPY ./start_materialdispo_docker.sh /opt/metasfresh/metasfresh-material-dispo/

# I don't understand why I have to do this. I would assume that when I commit this sh file on windows with lf eols and 
# then check it out on a linux machine, it has lf eols. But aparently it doesn't.
# At any rate, thx to https://stackoverflow.com/a/41424794/1012103
RUN dos2unix /opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh

# make our start command executable
RUN chmod 700 /opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh


ENTRYPOINT ["/opt/metasfresh/metasfresh-material-dispo/start_materialdispo_docker.sh"]
