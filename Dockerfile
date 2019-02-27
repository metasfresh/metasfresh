FROM openjdk:8-jdk-alpine
RUN mkdir -p /opt/metasfresh
COPY ./target/de-metas-edi-esb-camel.jar /opt/metasfresh/
WORKDIR /opt/metasfresh/

CMD ["java", "-jar", "de-metas-edi-esb-camel.jar"]
