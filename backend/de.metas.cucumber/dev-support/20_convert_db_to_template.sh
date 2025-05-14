#!/bin/sh

set -e
set -u

# the winpty is needed to avoid an error when running the script in git bash on windows

winpty docker exec -it infrastructure_db_1  psql -U postgres -c "alter database metasfresh rename to metasfresh_template_master_integration;"
winpty docker exec -it infrastructure_db_1  psql -U postgres -c "alter database metasfresh_template_master_integration is_template true;"

echo "The local database has been converted to a template database."
echo "You can drop this template database by running the following commands:"
echo ""
echo "winpty docker exec -it infrastructure_db_1  psql -U postgres -c \"UPDATE pg_database SET datistemplate='false' WHERE datname='metasfresh_template_master_integration';\""
echo "winpty docker exec -it infrastructure_db_1  psql -U postgres -c \"drop database if exists metasfresh_template_master_integration;\""
echo ""
echo "You can now proceed with creating the actual database from this template"