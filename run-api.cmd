docker run -d --name my-api -e DB_HOST=metasfresh-lulz-db -e ES_HOST=metasfresh-lulz-search -e RABBITMQ_HOST=metasfresh-lulz-rabbitmq -e APP_HOST=metasfresh-lulz-app my-api:latest
docker ps -a
docker logs --tail 100 -f my-api
