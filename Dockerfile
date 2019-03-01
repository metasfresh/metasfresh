FROM openjdk:8-jdk-alpine

ARG CACHEBUST=1

RUN apk update && apk upgrade && apk add bash && apk add dos2unix --update-cache --repository http://dl-3.alpinelinux.org/alpine/edge/community/ --allow-untrusted

COPY ./target/de-metas-edi-esb-camel.jar /opt/metasfresh-esb-camel/
COPY ./start_esb-camel_docker.sh /opt/metasfresh-esb-camel/

RUN dos2unix /opt/metasfresh-esb-camel/start_esb-camel_docker.sh

RUN chmod 700 /opt/metasfresh-esb-camel/start_esb-camel_docker.sh
RUN sh -c 'touch /opt/metasfresh-esb-camel/de-metas-edi-esb-camel.jar'

ENTRYPOINT ["/opt/metasfresh-esb-camel/start_esb-camel_docker.sh"]
