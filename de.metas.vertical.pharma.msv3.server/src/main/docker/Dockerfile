
FROM docker.metasfresh.com:6000/ubuntu:16.04

# note that we put all of this into one command
# because otherwise, the update and full-update might not be performed (still in cache)
# and the installs might fail, because e.g. openjdk might not yet be in the cache, however URLs might have been changed
RUN apt update \
    && apt -y full-upgrade \
    && apt -y install openjdk-8-jdk-headless \
    && apt install dos2unix \
    && apt -y autoremove \
    && apt -y autoclean


COPY ./start_msv3-server_docker.sh /opt/metasfresh-msv3-server/

# I don't understand why I have to do this. I would assume that when I commit this sh file on windows with lf eols and 
# then check it out on a linux machine, it has lf eols. But aparently it doesn't.
# At any rate, thx to https://stackoverflow.com/a/41424794/1012103
RUN dos2unix /opt/metasfresh-msv3-server/start_metasfresh-msv3-server_docker.sh

# make our start command executable
RUN chmod 700 /opt/metasfresh-msv3-server/start_msv3-server_docker.sh

COPY ./metasfresh-pharma.msv3.server.jar /opt/metasfresh-msv3-server/
RUN sh -c 'touch /opt/metasfresh-msv3-server/metasfresh-pharma.msv3.server.jar'

ENTRYPOINT ["/opt/metasfresh-msv3-server/start_msv3-server_docker.sh"]
