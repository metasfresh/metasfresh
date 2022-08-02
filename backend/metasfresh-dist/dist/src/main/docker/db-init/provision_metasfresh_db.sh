#!/bin/bash
set -e

echo "PGDATA=$PGDATA"

echo "Script $0 started at $(date)" >> $PGDATA/provision_metasfresh_db.info
 
# These two variables are used when applying the migration scripts
# everything else in this script assumes that the DB runs locally
db_host=${DB_HOST:-localhost}
db_port=${DB_PORT:-5432}

db_name=${DB_NAME:-metasfresh}
db_user=${DB_USER:-metasfresh}
db_password=${DB_PASSWORD:-$(echo $secret_db_password)}

url_seed_dump=${URL_SEED_DUMP:-https://metasfresh.com/wp-content/releases/db_seeds/metasfresh_latest.pgdump}
url_migration_scripts_package=${URL_MIGRATION_SCRIPTS_PACKAGE:-NOT_SET}
#"https://repo.metasfresh.com/content/repositories/mvn-PR-3766-releases/de/metas/dist/metasfresh-dist-dist/5.50.2-9164%2BPR3766/metasfresh-dist-dist-5.50.2-9164%2BPR3766-sql-only.tar.gz"

debug_print_bash_cmds=${DEBUG_PRINT_BASH_CMDS:-n}
add_pg_stat_statements_extension=${ADD_PG_STAT_STATEMENTS_EXTENSION:-n}

echo "URL_SEED_DUMP=${url_seed_dump}" >> $PGDATA/provision_metasfresh_db.info
echo "URL_MIGRATION_SCRIPTS_PACKAGE=${url_migration_scripts_package}" >> $PGDATA/provision_metasfresh_db.info

echo_variable_values()
{
 echo "Note: all these variables can be set from outside."
 echo ""
 echo "DB_HOST=${db_host}"
 echo "DB_PORT=${db_port}"
 echo "DB_NAME=${db_name}"
 echo "DB_USER=${db_user}"
 echo "DB_PASSWORD=*******"
 echo "URL_SEED_DUMP=${url_seed_dump}"
 echo "URL_MIGRATION_SCRIPTS_PACKAGE=${url_migration_scripts_package}"
 echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}"
 echo "ADD_PG_STAT_STATEMENTS_EXTENSION=${add_pg_stat_statements_extension}"
 echo ""
}

create_role_if_not_exists()
{
	if psql -t -c '\du' | cut -d \| -f 1 | grep -qw $db_user; then
		echo "Role $db_user already exists"
	else
		echo "Role $db_user does not yet exist"
		create_role
	fi
}

create_db_and_import_seed_dump_if_not_exists()
{
	# check if our DB exists; thx to https://stackoverflow.com/a/16783253/1012103
	if psql -lqt | cut -d \| -f 1 | grep -qw $db_name; then
		echo "Database $db_name already exists"
	else
		echo "Database $db_name does not yet exist"
		create_db
		import_dump
	fi
}

create_role() 
{
	echo "==================="
	echo " Creating role ..."
	echo "==================="
	psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE ROLE $db_user LOGIN ENCRYPTED PASSWORD '$db_password' SUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
EOSQL
	echo "==========="
	echo " ... done!"
	echo "==========="
}

create_db()
{
	echo "======================================="
	echo " Creating database and permissions ..."
	echo "======================================="
	psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE DATABASE $db_name WITH OWNER = $db_user;
GRANT ALL PRIVILEGES ON DATABASE $db_name to $db_user;
EOSQL
	echo "==========="
	echo " ... done!"
	echo "==========="
}

import_dump()
{
	cd /tmp # go to the tmp directory, where we may write files

	echo "==================================="
	echo " Getting initial seed from url ..."
	echo "==================================="
	echo ""
	echo "url_seed_dump=$url_seed_dump"
	echo ""

	local OUTPUT_FILE="metasfresh.pgdump"
	curl -o $OUTPUT_FILE $url_seed_dump
	
	echo "Populating database with initital seed... "
	pg_restore -Fc --username "$db_user" --dbname "$db_name" $OUTPUT_FILE
	echo "=========="
	echo " ...done!"
	echo "=========="	
}

apply_migration_scripts_from_artifact()
{
	if [ "${url_migration_scripts_package}" == "NOT_SET" ]; then
		echo "Note: no migration script package URL was provided."
		return
	fi
	cd /tmp # go to the tmp directory, where we may write files

	echo "========================================"
	echo " Getting migration scripts from url ..."
	echo "========================================"
	echo ""
	echo "url_migration_scripts_package=$url_migration_scripts_package"
	echo ""

	local OUTPUT_FILE="metasfresh-dist-dist-sql-only.tar.gz"
	curl -o $OUTPUT_FILE "${url_migration_scripts_package}"
	
	tar -xf $OUTPUT_FILE
	
	cat >dist/install/settings.properties <<EOL
METASFRESH_DB_SERVER=${db_host}
METASFRESH_DB_PORT=${db_port}
METASFRESH_DB_NAME=${db_name}
METASFRESH_DB_USER=${db_user}
METASFRESH_DB_PASSWORD=${db_password}
EOL

	echo "Running mg migrate... "
	cd dist/install
	./sql_remote.sh -s settings.properties
	
	echo "=========="
	echo " ...done!"
	echo "=========="
}

activate_extensions()
{
	if [ "${add_pg_stat_statements_extension}" != "n" ]; then
		# needs shared_preload_libraries = 'pg_stat_statements'	in postgresql.conf
		echo "==========================================="
		echo " activate pg_stat_statements extension ..."
		echo "==========================================="
		psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;
EOSQL
		echo "==========="
		echo " ... done!"
		echo "==========="
	fi
}
# start printing all bash commands from here onwards, if activated
if [ "$debug_print_bash_cmds" != "n" ];
then
	echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}, so from here we will output all bash commands; set to n (just the lowercase letter) to skip this."
	set -x
fi

echo_variable_values
create_role_if_not_exists
create_db_and_import_seed_dump_if_not_exists
apply_migration_scripts_from_artifact
activate_extensions

echo "Script $0 started at $(date)" >> $PGDATA/provision_metasfresh_db.info
