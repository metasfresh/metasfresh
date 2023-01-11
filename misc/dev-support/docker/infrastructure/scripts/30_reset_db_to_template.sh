#!/bin/sh

set -e
set -u # this will fail this script if no branch name is given as parameter

# We assume that the stuff was started with 10_reset_db_to_seed_dump.sh
BRANCH_NAME=$1

# the winpty is needed to avoid an error when running the script in git bash on windows

winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "drop database if exists metasfresh;"
winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "create database metasfresh template metasfresh_template_master_integration;"

echo "The local database has been recreated from the template database."
echo "You can rerun this script to reset the local database to the template database."
