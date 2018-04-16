FROM postgres:10.3

LABEL maintainer="dev@metasfresh-com"

# add this repository so we can install openjdk
RUN echo "deb http://http.debian.net/debian jessie-backports main" >> /etc/apt/sources.list

RUN apt -y update \
    && apt -y upgrade \
    && apt -y install curl \
# install openjdk so that we can run mg migrate from within the provisioning script
    && apt -y install libcups2 \
    && apt -y install -t jessie-backports openjdk-8-jre-headless \
    && apt -y autoremove \
    && apt -y clean

COPY provision_metasfresh_db.sh /docker-entrypoint-initdb.d/

COPY docker-entrypoint_init_only-10.3.sh /usr/local/bin/docker-entrypoint_init_only.sh
RUN chown postgres:postgres /usr/local/bin/docker-entrypoint_init_only.sh
RUN chmod 700 /usr/local/bin/docker-entrypoint_init_only.sh

RUN ln -s usr/local/bin/docker-entrypoint_init_only.sh / 
ENTRYPOINT ["/docker-entrypoint_init_only.sh"]

CMD ["postgres"]
#don't expose the port; we don't need/want external access
#EXPOSE 5432
