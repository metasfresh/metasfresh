FROM openjdk:8-jdk-alpine

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

RUN apk update && apk upgrade && apk add bash && apk add curl && apk add dos2unix --update-cache --repository http://dl-3.alpinelinux.org/alpine/edge/community/ --allow-untrusted

COPY ./target/de-metas-edi-esb-camel.jar /opt/metasfresh-esb-camel/
COPY ./start_esb-camel_docker.sh /opt/metasfresh-esb-camel/

RUN dos2unix /opt/metasfresh-esb-camel/start_esb-camel_docker.sh

RUN chmod 700 /opt/metasfresh-esb-camel/start_esb-camel_docker.sh
RUN sh -c 'touch /opt/metasfresh-esb-camel/de-metas-edi-esb-camel.jar'

ENTRYPOINT ["/opt/metasfresh-esb-camel/start_esb-camel_docker.sh"]
