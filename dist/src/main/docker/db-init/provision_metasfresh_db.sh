#!/bin/bash
set -e

# These two variables are used when applying the migration scripts
# everything else in this script assumes that the DB runs locally
db_host=${DB_HOST:-localhost}
db_port=${DB_PORT:-5432}

db_name=${DB_NAME:-metasfresh}
db_user=${DB_USER:-metasfresh}
db_password=${DB_PASSWORD:-$(echo $secret_db_password)}

url_seed_dump=${URL_SEED_DUMP:-http://www.metasfresh.com/wp-content/releases/db_seeds/metasfresh_latest.pgdump}
url_migration_scripts_package=${URL_MIGRATION_SCRIPTS_PACKAGE:-NOT_SET}
#"https://repo.metasfresh.com/content/repositories/mvn-PR-3766-releases/de/metas/dist/metasfresh-dist-dist/5.50.2-9164%2BPR3766/metasfresh-dist-dist-5.50.2-9164%2BPR3766-sql-only.tar.gz"

debug_print_bash_cmds=${DEBUG_PRINT_BASH_CMDS:-n}

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
	pg_restore -Fc -U "$db_user" -d "$db_password" $OUTPUT_FILE
	echo "=========="
	echo " ...done!"
	echo "=========="	
}

apply_migration_scripts_from_artifact()
{
	if [ "{url_migration_scripts_package}" == "NOT_SET" ]; then
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
	curl -o $OUTPUT_FILE $url_migration_scripts_package
	
	tar -xf $OUTPUT_FILE
	
	cat >dist/settings.properties <<EOL
METASFRESH_DB_SERVER=${db_host}
METASFRESH_db_port=${db_port}
METASFRESH_db_name=${db_name}
METASFRESH_DB_USER=${db_user}
METASFRESH_db_password=${db_password}
EOL

	echo "Running mg migrate... "
	cd dist/install
	./sql_remote.sh -s settings.properties
	
	echo "=========="
	echo " ...done!"
	echo "=========="
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

