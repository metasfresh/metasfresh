ARG REFNAME=local
FROM mazorn/metas-mvn-backend:$REFNAME as backend

FROM openjdk:8-jdk-oraclelinux7

WORKDIR /opt/metasfresh

COPY --chmod=700 docker-builds/app/start.sh .

COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/metasfresh_server.jar ./metasfresh-app.jar
COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/configs/* ./
COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/reports/ reports/

CMD ["/opt/metasfresh/start.sh"]