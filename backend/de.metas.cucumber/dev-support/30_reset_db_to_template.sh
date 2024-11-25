#!/bin/sh

set -e
set -u

# the winpty is needed to avoid an error when running the script in git bash on windows

winpty docker exec -it infrastructure_db_1  psql -U postgres -c "drop database if exists metasfresh;"
winpty docker exec -it infrastructure_db_1  psql -U postgres -c "create database metasfresh template metasfresh_template_master_integration;"

echo "The local database has been recreated from the template database."
echo "You can rerun this script to reset the local database to the template database."
