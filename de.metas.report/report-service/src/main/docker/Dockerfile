
#Note: I played with openjdk:8-jdk-alpine, but wasn't able to get even the entrypoint shell script to actually run
FROM docker.metasfresh.com:6000/ubuntu:16.04

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image 
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

# Note that we put all of this into one command
# because otherwise, the update and full-update might not be performed (still in cache)
# and the installs might fail, because e.g. openjdk might not yet be in the cache, however URLs might have been changed.
#
# Note that netcat is used to wait for the DMBS to be available on startup,
# and postgresql-client-9.5 is used in performing the eventual migration script rollout
RUN apt update \
    && apt -y full-upgrade \
    && apt -y install netcat \
    && apt -y install postgresql-client-9.5 \
    && apt -y install openjdk-8-jdk-headless \
    && apt -y install dos2unix \
    && apt -y install unzip \
    && apt -y autoremove \
    && apt -y autoclean

COPY ./metasfresh-report-service-standalone.jar /opt/metasfresh/metasfresh-report/metasfresh-report.jar

# Explode the uber jar to make it easier to work with the docker image; 
# e.g. with the exploded uber jar, we can later copy or overwrite config files into the docker image that change the applications behavior.
# Note that the skript start_materialdispo_docker.sh is adapted to the exploded jar.
# Note2 that we need unzip; jar doesn't work, thx to https://stackoverflow.com/a/39653862/1012103
RUN cd /opt/metasfresh/metasfresh-report \
 && (unzip -q /opt/metasfresh/metasfresh-report/metasfresh-report.jar || echo "Ignore exit code $? because spring boot's uber jar starts with a shell script") \
 && rm /opt/metasfresh/metasfresh-report/metasfresh-report.jar

COPY ./configs/logback.xml /opt/metasfresh/metasfresh-report/
COPY ./configs/metasfresh.properties /opt/metasfresh/metasfresh-report/

COPY ./start_report_docker.sh /opt/metasfresh/metasfresh-report/

# I don't understand why I have to do this. I would assume that when I commit this sh file on windows with lf eols and
# then check it out on a linux machine, it has lf eols. But aparently it doesn't.
# At any rate, thx to https://stackoverflow.com/a/41424794/1012103
RUN dos2unix /opt/metasfresh/metasfresh-report/start_report_docker.sh

# make our start command executable
RUN chmod 700 /opt/metasfresh/metasfresh-report/start_report_docker.sh


# prepare the docker image to accomodate external/private jars
# note that for this to work
# * metasfresh-report.jar was made with layout=ZIP, also see https://docs.spring.io/spring-boot/docs/current/maven-plugin/usage.html
# * metasfresh-report.conf contains a line saying "LOADER_PATH=/opt/metasfresh/metasfresh-report/external-libs"
RUN sh -c 'mkdir /opt/metasfresh/metasfresh-report/external-lib'
RUN sh -c 'echo "Jar files in this folder are part of the classpath of metasfresh-report" > /opt/metasfresh/metasfresh-report/external-lib/README.txt'

# by convention, jasper files will go into this folder. It corresponds to AD_OrgInfo.ReportPrefix="file:////opt/metasfresh/reports" in the metasfresh DB
RUN sh -c 'mkdir /opt/metasfresh/reports'

COPY ./dist /opt/metasfresh/dist

# make sure the docker image has a folder for custom SQLs that might come with custom jasper files
RUN sh -c 'mkdir -p /opt/metasfresh/dist/sql'

ENTRYPOINT ["/opt/metasfresh/metasfresh-report/start_report_docker.sh"]
