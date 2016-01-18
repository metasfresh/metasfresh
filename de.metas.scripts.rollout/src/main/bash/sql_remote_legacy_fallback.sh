#!/bin/bash

set -o nounset  #don't allow access to unset variables
set -o errexit  #don't allow any command to exit with an error code !=0


####
# Setting local and rollout dir
####
LOCAL_DIR=`dirname $0`
if [ "$LOCAL_DIR" == "." ]; then
	LOCAL_DIR=`pwd`
fi
#Note: ROLLOUT_DIR can be overridden from cmdline using -d
ROLLOUT_DIR=$LOCAL_DIR/..

TOOLS=`dirname $0`/tools.sh
if [ -f $TOOLS ] && [ -r $TOOLS ]; then
	source $TOOLS
else
	echo "Missing file ${TOOLS}"
	exit 1
fi

DEFAULT_LOCAL_SETTINGS_FILE=~/local_settings.properties

trace $(basename $0) "BEGIN"

ERROR_FILE=$LOCAL_DIR/error.tmp

print_help()
{
	echo "Help"
	echo "sql_remote: Util to apply ADempiere migration scripts to a database"
	echo "Parameters:"
	echo "-d <Rollout-Directory>; Optional; Directory that contains the rollout package"
	echo "-f <File>; Optional; Only process the given file in the rollout directory"
	echo "-h <Hostname>; Reqired; Hostanme of the adempiere application server. Needed to select the right properties file"
	echo "-i; Optional; Ignore database errors. WARNING: Only use if you know what you are doing!"
	echo "-r; Optional; Only record script, but don't actually execute. WARNING: Only use if you know what you are doing!"
	echo ""
	echo "Hint: Tool checks if a script has already been applied"
}

prepare()
{
	trace prepare BEGIN

	source_properties

	check_vars_database

	#creating pg_pass file if required
	if [ -f ~/.pgpass ]; then
		trace prepare "FOUND FILE ~/.pgpass. This script won't alter it, so please makes sure that it is correct!"
	else
		trace prepare "Creating file ~/.pgpass."
		echo "${ADEMPIERE_DB_SERVER}:5432:${ADEMPIERE_DB_NAME}:${ADEMPIERE_DB_USER}:${ADEMPIERE_DB_PASSWORD}" >> ~/.pgpass
		chmod 600 /home/adempiere/.pgpass
	fi	
	
	PSQL_PARAMS="--quiet -t --single-transaction --host $ADEMPIERE_DB_SERVER --dbname $ADEMPIERE_DB_NAME --username $ADEMPIERE_DB_USER"	
	if [ "${IGNORE_ERRORS}" == "NO" ]; then
		PSQL_PARAMS="${PSQL_PARAMS} --variable ON_ERROR_STOP="
	else
		trace prepare "WARNING: DATABASE ERRORS ARE INGORED (parameter -i)"
	fi
	
	SQL_DIR=${ROLLOUT_DIR}/sql
	check_dir_exists ${SQL_DIR}
	
	check_std_tool psql
	check_std_tool readlink
	
	trace prepare END
	
	if [ -f $ERROR_FILE ]; then
		trace prepare "Removing existing error file ${ERROR_FILE}."
		rm $ERROR_FILE
	fi
}

check_already_applied()
{
	local name=$1

	SQL_SELECT_EXISTING="SELECT CASE WHEN count(1)>0 THEN 'YES' ELSE 'NO' END as found FROM AD_MigrationScript WHERE name='${name}'"
	
	local file_found=`echo $SQL_SELECT_EXISTING | psql ${PSQL_PARAMS}`
	echo $file_found
}

run_sql()
{
	trace run_sql BEGIN
	
	trace run_sql "PSQL_PARAMS=${PSQL_PARAMS}"
	
	local error_file="NONE";

	local search_pattern=""
	if [ "$SINGLE_FILE" == "NOT_SET" ]; then
		trace run_sql "Processing files ${SQL_DIR}/*.sql"
		search_pattern="*.sql"
	else
		trace run_sql "Processing only file  ${SQL_DIR}/${SINGLE_FILE}"
		search_pattern="$SINGLE_FILE"
	fi

	trace run_sql "Going to invoke 'find ${SQL_DIR} -name ${search_pattern}'"

	local count=$(find ${SQL_DIR} -name ${search_pattern} | wc -l)
    trace run_sql "Number of files to process: $count"

	#notes: 
	#  *The "read" command handles file names with whitespaces correctly
	#  *Despite "set -o errexit", the script doesn't finish a desired, if an error occurs inside the while block. 
	#   Only the block is finished. that's why we check the running of SQL scripts manually
	set +o errexit
	
	find ${SQL_DIR} -name "${search_pattern}" | sort | while read file; do

#		trace run_sql "Current file=$file"

		local file_name_simple=`basename "$file"`
		local dir=`dirname "$file"`
		local project_name=`basename "$dir"`
		
		local file_found=`check_already_applied "${project_name}->${file_name_simple}"`
		
		if [ "${file_found}" == "YES" ]; then
			trace run_sql "Script already applied: ${project_name}->${file_name_simple}"
		else
		
			local description="by `readlink -f $0`"
			if [ "$RECORD_ONLY" == "YES" ]; then
				trace run_sql "Not executing script (only recording) ${project_name}->${file_name_simple} (${file})"
				description="Script NOT applied, only recorded ${description}"
			else
				local error_script="NONE"
			
				trace run_sql "Now executing ${project_name}->${file_name_simple} (${file})" 
				psql ${PSQL_PARAMS} --file "${file}" || error_script=${file}
				
				if [ "$error_script" != "NONE" ]; then
					trace run_sql "Error with current script"
					echo "${error_script}" > $ERROR_FILE
					set -o errexit	
					return 1;
				fi
				description="Script applied ${description}"
			fi
				
			trace run_sql "Recording SQL script"		
			echo "INSERT INTO AD_MigrationScript(\
					ad_client_id, ad_org_id, created, createdby, updated, updatedby, \
					description, \
					developername, isactive, releaseno, status, isapply, \
					filename, name, projectname\
				  ) VALUES (\
				    0, 0, now(), 100, now(), 100, \
					'${description}',\
					NULL, 'Y' , 1, 'CO', 'Y',\
					'${file}', '${project_name}->${file_name_simple}','${project_name}');" \
				| psql ${PSQL_PARAMS} || error_script=${file}
				
			if [ "$error_script" != "NONE" ]; then
				trace run_sql "Error recording current script"
				echo "${error_script}" > $ERROR_FILE
				set -o errexit	
				return 1;
			fi
		fi
#		trace run_sql "Done with file $file"
		
	done
	set -o errexit	
		
	trace run_sql END
}

IGNORE_ERRORS="NO"
RECORD_ONLY="NO"
SINGLE_FILE="NOT_SET"

LOCAL_SETTINGS_FILE=$DEFAULT_LOCAL_SETTINGS_FILE
SETTINGS_FILE="NOT_SET"

#parse the command line args (getopts doesn't work from inside a procedure)
while getopts "d:f:h:irs:" OPTION; do
	case "$OPTION" in
		d)ROLLOUT_DIR="$OPTARG";;
		f)SINGLE_FILE="$OPTARG";;
		i)IGNORE_ERRORS="YES";;
		r)RECORD_ONLY="YES";;
		s)SETTINGS_FILE="$OPTARG";;
	esac
done

prepare
run_sql

if [ -f $ERROR_FILE ]; then
	trace $(basename $0) "Aborting because of an error with SQL script `cat $ERROR_FILE`"
	exit 1;
else
	trace $(basename $0) "END"
fi
