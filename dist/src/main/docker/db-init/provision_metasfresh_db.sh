#!/bin/bash

set -e

# These two variables are used when applying the migration scripts
# everything else in this script assumes that the DB runs locally
#DB_HOST=192.168.99.100
#DB_PORT=31973
DB_HOST=localhost
DB_PORT=5432

DB_NAME=metasfresh
DB_USERNAME=metasfresh
DB_PASSWORD=metasfresh # TODO get from a secret
DB_SYSPASS=System

URL_SEED_DUMP="http://www.metasfresh.com/wp-content/releases/db_seeds/metasfresh_latest.pgdump"
URL_MIGRATION_SCRIPTS_PACKAGE="https://repo.metasfresh.com/content/repositories/mvn-PR-3766-releases/de/metas/dist/metasfresh-dist-dist/5.50.2-9164%2BPR3766/metasfresh-dist-dist-5.50.2-9164%2BPR3766-sql-only.tar.gz"


create_role() 
{
	echo "==================="
	echo " Creating role ..."
	echo "==================="
	psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE ROLE $DB_USERNAME LOGIN ENCRYPTED PASSWORD '$DB_PASSWORD' SUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
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
CREATE DATABASE $DB_NAME WITH OWNER = $DB_USERNAME;
GRANT ALL PRIVILEGES ON DATABASE $DB_NAME to $DB_USERNAME;
EOSQL
	echo "==========="
	echo " ... done!"
	echo "==========="
}

import_dump()
{
	echo "==================================="
	echo " Getting initial seed from url ..."
	echo "==================================="
	echo ""
	echo "URL_SEED_DUMP=$URL_SEED_DUMP"
	echo ""

	local OUTPUT_FILE="metasfresh.pgdump"
	curl -o $OUTPUT_FILE $URL_SEED_DUMP
	
	echo "Populating database with initital seed... "
	pg_restore -Fc -U "$DB_USERNAME" -d "$DB_PASSWORD" $OUTPUT_FILE
	echo "=========="
	echo " ...done!"
	echo "=========="	
}

#
# this function is not ready!
# Also, I'm not sure if it makes sense to do the pull, even with depth=1
apply_migration_scripts_from_git_WIP()
{
	sudo apt -y install git

	# thx to https://stackoverflow.com/a/28039894/1012103
	git init metasfresh-sql-only
	cd metasfresh-sql-only
	git remote add origin git@github.com:metasfresh/metasfresh.git
	git config core.sparseCheckout true

	cat >.git/info/sparse-checkout <<EOL
/**/sql/postgresql/system/**/*.sql
EOL

	git pull --depth=1 origin master

	#TODO: 
	# * change the metasfresh repo to have all SQL files in an explicit folder and remove the migration-sql-basedir property
	# * find out how to collect all the files into one folder structure
	# * get and run the mg tool
}

#
# this is nice, but doesn't work because we don't have the sufficient rights to install stuff
install_if_not_installed()
{
	local tool=$1
	local package=$2
	
	#thx to https://stackoverflow.com/a/677212/1012103
	if hash $tool 2>/dev/null; then
        echo "${tool} is available"
    else
        echo "${tool} is not available; attempting to install it."
		sudo apt install -y $tool
    fi
}

apply_migration_scripts_from_artifact()
{
	echo "========================================"
	echo " Getting migration scripts from url ..."
	echo "========================================"
	echo ""
	echo "URL_MIGRATION_SCRIPTS_PACKAGE=$URL_MIGRATION_SCRIPTS_PACKAGE"
	echo ""
	local OUTPUT_FILE="metasfresh-dist-dist-sql-only.tar.gz"
	curl -o $OUTPUT_FILE $URL_MIGRATION_SCRIPTS_PACKAGE
	
	tar -xf $OUTPUT_FILE
	
	cat >dist/settings.properties <<EOL
METASFRESH_DB_SERVER=${DB_HOST}
METASFRESH_DB_PORT=${DB_PORT}
METASFRESH_DB_NAME=${DB_NAME}
METASFRESH_DB_USER=${DB_USERNAME}
METASFRESH_DB_PASSWORD=${DB_PASSWORD}
EOL

	echo "Running mg migrate... "
	cd dist/install
	./sql_remote.sh -s settings.properties
	
	echo "=========="
	echo " ...done!"
	echo "=========="
}

# go to the tmp directory, where we may write files
cd /tmp

if psql -t -c '\du' | cut -d \| -f 1 | grep -qw $DB_USERNAME; then
    echo "Role $DB_USERNAME already exists"
else
	echo "Role $DB_USERNAME does not yet exist"
	create_role
fi	
	
# check if our DB exists; thx to https://stackoverflow.com/a/16783253/1012103
if psql -lqt | cut -d \| -f 1 | grep -qw $DB_NAME; then
    echo "Database $DB_NAME already exists"
else
	echo "Database $DB_NAME does not yet exist"
	create_db
	import_dump
fi

apply_migration_scripts_from_artifact
