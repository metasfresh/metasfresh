#!/bin/sh

#
# %L
# metasfresh
# %%
# Copyright (C) 2023 metas GmbH
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 2 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program. If not, see
# <http://www.gnu.org/licenses/gpl-2.0.html>.
# L%
#

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
