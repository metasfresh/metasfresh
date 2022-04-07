docker run -d --name my-app -e DB_HOST=metasfresh-lulz-db -e ES_HOST=metasfresh-lulz-search -e RABBITMQ_HOST=metasfresh-lulz-rabbitmq -e APP_HOST=metasfresh-lulz-app -e secret_db_password=very-secret my-app:latest
docker ps -a
docker logs --tail 100 -f my-app
