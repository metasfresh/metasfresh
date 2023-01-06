#!/bin/sh

set -e
set -u # this will fail this script if no branch name is given as parameter

# We assume that in the folder misc/dev-support/docker/infrastructure/env-files/ there exists an env file named ${BRANCH_NAME}.env
BRANCH_NAME=$1

COMPOSE_FILE=../docker-compose.yml
ENV_FILE=../env-files/${BRANCH_NAME}.env

# reset the database
docker-compose --file ${COMPOSE_FILE} --env-file ${ENV_FILE} --project-name ${BRANCH_NAME}_infrastructure down

set +e # if the volumes don't exist yet, then it's also fine..so, don't fail the script in that case
docker volume rm ${BRANCH_NAME}_metasfresh_postgres
docker volume rm ${BRANCH_NAME}_metasfresh_elasticsearch
set -e

docker-compose --file ${COMPOSE_FILE} --env-file ${ENV_FILE} build --pull
docker-compose --file ${COMPOSE_FILE} --env-file ${ENV_FILE} --project-name ${BRANCH_NAME}_infrastructure up -d

echo "The local database is now reset to the seed dump."
echo "Now you can use this command:" 
echo ""
echo "docker-compose --file $COMPOSE_FILE --env-file ${ENV_FILE} --project-name ${BRANCH_NAME}_infrastructure logs -f db"
echo ""
echo "..to see when the DB is actually up."
echo "When the DB is up, apply the local migration scripts and then proceed with converting this DB into a template."
