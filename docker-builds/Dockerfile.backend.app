ARG REFNAME=local
FROM metasfresh/metas-mvn-backend:$REFNAME as backend

FROM openjdk:8-jdk-oraclelinux7

RUN yum -y update && yum -y install zip && yum -y clean all && rm -rf /var/cache

WORKDIR /opt/metasfresh

COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/metasfresh_server.jar ./metasfresh-app.jar
COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/configs/* ./
COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/reports/ reports/

COPY docker-builds/metadata/build-info.properties META-INF/
COPY docker-builds/metadata/git.properties BOOT-INF/classes/
RUN zip -g metasfresh-app.jar META-INF/build-info.properties BOOT-INF/classes/git.properties

CMD ["java", "-jar", "/opt/metasfresh/metasfresh-app.jar"]