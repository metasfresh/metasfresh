ARG REFNAME=local
FROM metasfresh/metas-mvn-backend:$REFNAME as backend

FROM openjdk:8-jdk-oraclelinux7

WORKDIR /opt/metasfresh/metasfresh-webui-api

COPY --from=backend /backend/metasfresh-webui-api/target/docker/metasfresh-webui-api.jar .
COPY --from=backend /backend/metasfresh-webui-api/target/docker/configs/metasfresh.properties .

CMD ["java", "-jar", "/opt/metasfresh/metasfresh-webui-api/metasfresh-webui-api.jar"]