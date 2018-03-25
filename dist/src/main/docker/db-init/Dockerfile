#FROM postgres:9.5
FROM postgres:10.3

LABEL maintainer="dev@metasfresh-com"

# add this repository so we can install openjdk
RUN echo "deb http://http.debian.net/debian jessie-backports main" >> /etc/apt/sources.list

RUN apt update --assume-yes

RUN apt upgrade --assume-yes

RUN apt install curl --assume-yes

# install openjdk so that we can run mg migrate from within the provisioning script
#RUN apt install -t jessie-backports openjdk-8-jdk-headless --assume-yes
RUN apt install libcups2 --assume-yes
RUN apt install -t jessie-backports openjdk-8-jre-headless --assume-yes

#RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
#ENV LANG=en_US.UTF-8
#ENV LANGUAGE=en_US.UTF-8
#ENV LC_MESSAGES=en_US.UTF-8

COPY provision_metasfresh_db.sh /docker-entrypoint-initdb.d/

COPY docker-entrypoint_init_only-10.3.sh /usr/local/bin/
RUN chmod 700 /usr/local/bin/docker-entrypoint_init_only-10.3.sh

RUN ln -s usr/local/bin/docker-entrypoint_init_only-10.3.sh / # backwards compat
ENTRYPOINT ["docker-entrypoint_init_only-10.3.sh"]

CMD ["postgres"]
#don't expose the port; we don't want external access
#EXPOSE 5432
