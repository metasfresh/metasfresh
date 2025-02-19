#!/bin/sh

set -e
set -u

COMPOSE_FILE=../../../misc/dev-support/docker/infrastructure/docker-compose.yml

# reset the database
docker-compose --file $COMPOSE_FILE down
docker volume rm metasfresh_postgres
docker volume rm metasfresh_elasticsearch
docker-compose --file $COMPOSE_FILE build --pull
docker-compose --file $COMPOSE_FILE up -d

echo "The local database is now reset to the seed dump."
echo "Now you can use this command:" 
echo ""
echo "docker-compose  --file $COMPOSE_FILE logs -f db"
echo ""
echo "..to see when the DB is actually up."
echo "When the DB is up, apply the local migration scripts and then proceed with converting this DB into a template."
