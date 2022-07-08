ARG REFNAME=local
FROM metasfresh/metas-mvn-camel:$REFNAME as camel

FROM adoptopenjdk:14.0.2_8-jdk-hotspot

WORKDIR /app

COPY --from=camel /camel/de-metas-camel-externalsystems/core/target/uber/de-metas-camel-externalsystems-core.jar .

CMD ["java", "-jar", "/app/de-metas-camel-externalsystems-core.jar"]