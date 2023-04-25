#!/bin/sh

set -e

# We assume that the stuff was started with 10_reset_db_to_seed_dump.sh
# We assume that in the folder misc/dev-support/docker/infrastructure/env-files/ there exists an env file named ${BRANCH_NAME}.env
if ! [ -z "$1" ]; then
    BRANCH_NAME=$1
else
    echo "!! The first parameter needs do correspond to an env-File !!"
    echo "!! E.g. to use the env-file env-files/release.env, run 30_reset_db_to_template.sh release !!" 
    exit
fi

set -u

# the winpty is needed to avoid an error when running the script in git bash on windows

winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "drop database if exists metasfresh;"
winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "create database metasfresh template metasfresh_template_${BRANCH_NAME};"

echo "The local database has been recreated from the template database."
echo "You can rerun this script to reset the local database to the template database."
