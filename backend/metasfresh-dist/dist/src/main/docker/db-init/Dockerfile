FROM postgres:14.2

LABEL maintainer="dev@metasfresh-com"

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image 
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

RUN apt -y update \
    && apt -y upgrade \
    && apt -y install curl \
# install openjdk so that we can run mg migrate from within the provisioning script
    && apt -y install libcups2 \
    && apt -y install openjdk-11-jre-headless \
    && apt -y install dos2unix \
    && apt -y autoremove \
    && apt -y clean

COPY provision_metasfresh_db.sh /docker-entrypoint-initdb.d/provision_metasfresh_db.sh

# thx to https://stackoverflow.com/a/48311129/1012103
RUN echo en_US.UTF-8 UTF-8 > /etc/locale.gen
RUN locale-gen en_US.UTF-8

ENV LC_ALL="en_US.UTF-8"
ENV LC_CTYPE="en_US.UTF-8"

COPY docker-entrypoint_init_only-14.2.sh /usr/local/bin/docker-entrypoint_init_only.sh
RUN chown postgres:postgres /usr/local/bin/docker-entrypoint_init_only.sh
RUN chmod 700 /usr/local/bin/docker-entrypoint_init_only.sh

# At any rate, thx to https://stackoverflow.com/a/41424794/1012103
RUN dos2unix /usr/local/bin/docker-entrypoint_init_only.sh
RUN dos2unix /docker-entrypoint-initdb.d/provision_metasfresh_db.sh

RUN ln -s /usr/local/bin/docker-entrypoint_init_only.sh / 
ENTRYPOINT ["/docker-entrypoint_init_only.sh"]

CMD ["postgres"]
#don't expose the port; we don't need/want external access
#EXPOSE 5432
