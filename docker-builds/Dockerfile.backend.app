ARG REFNAME=local
FROM metasfresh/metas-mvn-backend:$REFNAME as backend

FROM openjdk:8-jre-bullseye

RUN apt-get -y update && apt-get -y install locales zip && rm -rf /var/lib/apt/lists/*
RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
ENV LANG=de_DE.UTF-8 LANGUAGE=de_DE.UTF-8 LC_MESSAGES=de_DE.UTF-8
ENV TZ=Europe/Berlin

WORKDIR /opt/metasfresh

COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/metasfresh_server.jar .
COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/configs/* ./
COPY --from=backend /backend/metasfresh-dist/dist/target/docker/app/reports/ reports/

COPY docker-builds/metadata/build-info.properties META-INF/
COPY docker-builds/metadata/git.properties BOOT-INF/classes/
RUN zip -g metasfresh_server.jar META-INF/build-info.properties BOOT-INF/classes/git.properties

CMD ["java", "-cp", "/opt/metasfresh/metasfresh_server.jar", "-Dloader.path=/opt/metasfresh/external-lib", "org.springframework.boot.loader.PropertiesLauncher"]