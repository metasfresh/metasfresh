FROM openjdk:8-jdk-oraclelinux7

WORKDIR /opt/metasfresh

COPY --chmod=700 docker-builds/app/start.sh .

ARG REFNAME=local

COPY --from=mazorn/metas-mvn-backend:$REFNAME /backend/metasfresh-dist/dist/target/docker/app/metasfresh_server.jar ./metasfresh-app.jar
COPY --from=mazorn/metas-mvn-backend:$REFNAME /backend/metasfresh-dist/dist/target/docker/app/configs/* ./
COPY --from=mazorn/metas-mvn-backend:$REFNAME /backend/metasfresh-dist/dist/target/docker/app/reports/ reports/

CMD ["/opt/metasfresh/start.sh"]