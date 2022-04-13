#!/bin/bash
set -e

echo ""
echo "==================="
echo " Creating role ..."
echo "==================="
psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE ROLE metasfresh LOGIN ENCRYPTED PASSWORD 'metasfresh' SUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
EOSQL
echo "==========="
echo " ... done!"
echo "==========="

echo ""
echo "======================================="
echo " Creating database and permissions ..."
echo "======================================="
psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE DATABASE metasfresh WITH OWNER = metasfresh;
GRANT ALL PRIVILEGES ON DATABASE metasfresh to metasfresh;
EOSQL
# psql -v ON_ERROR_STOP=1 --username=metasfresh <<- EOSQL
# DROP SCHEMA public;
# EOSQL
echo "==========="
echo " ... done!"
echo "==========="

echo ""
echo "==================="
echo "Restoring pgdump"
echo "==================="
pg_restore -Fc --exit-on-error --username metasfresh --dbname metasfresh /metasfresh_latest.pgdump
echo "=========="
echo " ...done!"
echo "=========="
