#!/bin/sh

set -e

# We assume that in the folder misc/dev-support/docker/infrastructure/env-files/ there exists an env file named ${BRANCH_NAME}.env
if ! [ -z "$1" ]; then
    BRANCH_NAME=$1
else
    echo "!! The first parameter needs do correspond to an env-File !!"
    echo "!! E.g. to use the env-file env-files/release.env, run 10_reset_db_to_seek_Dump.sh release !!" 
    exit
fi

set -u

COMPOSE_FILE=../docker-compose.yml
ENV_FILE=../env-files/${BRANCH_NAME}.env

# reset the database
docker-compose --file ${COMPOSE_FILE} --env-file ${ENV_FILE} --project-name ${BRANCH_NAME}_infrastructure down
