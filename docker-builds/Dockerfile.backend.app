ARG REFNAME=local
FROM metasfresh/metas-mvn-backend:$REFNAME as backend

FROM openjdk:8-jdk-oraclelinux7

WORKDIR /opt/metasfresh

COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/metasfresh_server.jar ./metasfresh-app.jar
COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/configs/* ./
COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/reports/ reports/

CMD ["java", "-jar", "/opt/metasfresh/metasfresh-app.jar"]