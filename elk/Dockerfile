# source: https://www.github.com/spujadas/elk-docker
FROM sebp/elk:740

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image 
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

# update the system
RUN apt update --quiet --assume-yes \
 && apt upgrade --quiet --assume-yes

# install the APM server
#RUN curl -L -O https://artifacts.elastic.co/downloads/apm-server/apm-server-7.4.0-amd64.deb
#RUN dpkg -i apm-server-7.4.0-amd64.deb
#RUN update-rc.d apm-server defaults

COPY start.sh /usr/local/bin/start.sh
RUN chmod +x /usr/local/bin/start.sh
COPY elastic-post-hooks.sh /usr/local/bin/elastic-post-hooks.sh
RUN chmod +x /usr/local/bin/elastic-post-hooks.sh


# copy logstash config files
COPY 01-tcp-input.conf /etc/logstash/conf.d/01-tcp-input.conf
COPY 30-output.conf /etc/logstash/conf.d/30-output.conf

# set file mode to rw-r--r--
RUN chmod 0644 /etc/logstash/conf.d/01-tcp-input.conf \
 && chmod 0644 /etc/logstash/conf.d/30-output.conf

# ports in addition to the ones our basic image exposes
# port 5000 for logstash 
EXPOSE 5000
