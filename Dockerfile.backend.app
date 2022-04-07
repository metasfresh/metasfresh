FROM openjdk:8-jre

RUN apt-get update && apt-get install -y unzip && rm -rf /var/lib/apt/lists/*

WORKDIR /opt/metasfresh
COPY --chmod=700 docker/start_app.sh .

COPY --from=mvn-backend:latest /backend/metasfresh-dist/dist/target/docker/app/metasfresh_server.jar ./metasfresh-app.jar
COPY --from=mvn-backend:latest /backend/metasfresh-dist/dist/target/docker/app/configs/* ./
COPY --from=mvn-backend:latest /backend/metasfresh-dist/dist/target/docker/app/reports/* reports/

# explode uber jar

CMD ["/opt/metasfresh/start_app.sh"]
