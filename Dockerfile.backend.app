FROM openjdk:8-jdk-oraclelinux7

WORKDIR /opt/metasfresh

COPY --chmod=700 docker-builds/app/start.sh .

COPY --from=mvn-backend:latest /backend/metasfresh-dist/dist/target/docker/app/metasfresh_server.jar ./metasfresh-app.jar
COPY --from=mvn-backend:latest /backend/metasfresh-dist/dist/target/docker/app/configs/* ./
COPY --from=mvn-backend:latest /backend/metasfresh-dist/dist/target/docker/app/reports/* reports/

CMD ["/opt/metasfresh/start.sh"]