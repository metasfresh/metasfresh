#!/bin/bash

set -o nounset  #don't allow access to unset variables
set -o errexit  #don't allow any command to exit with an error code !=0

TOOLS=$(dirname $0)/tools.sh
if [ -f $TOOLS ] && [ -r $TOOLS ]; then
	source $TOOLS
else
	echo "Missing file ${TOOLS}"
	exit 1
fi

#Minor rollouts don't include AOP anyways, but the script might check if the variable is set
JBOSS_AOP_VERSION="None"

ANT_HOME=/opt/apache-ant-1.7.0

#Configfile, sets these variables:
#  ADEMPIERE_HOME
#  JAVA_HOME
#  LOGFILE
#  PATH

#set_CONFIGFILE_var
#check_file_readable $CONFIGFILE
#source $CONFIGFILE

LOCAL_DIR=$(dirname $0)

if [ "$LOCAL_DIR" == "." ]; then
	LOCAL_DIR=$(pwd)
fi
#Note: ROLLOUT_DIR can be overridden from cmdline using -d
ROLLOUT_DIR=$LOCAL_DIR/..

SOURCES_DIR=$LOCAL_DIR/../sources

HOSTNAME=$(hostname)

RELEASE_TIME=$(date "+%Y%m%d_%H%M%S")

DEFAULT_LOCAL_SETTINGS_FILE=~/local_settings.properties

trace $(basename $0) "BEGIN"

prepare()
{
	trace prepare BEGIN

	source $LOCAL_SETTINGS_FILE
	
	check_vars_minor
	
	check_var "LOCAL_DIR" $LOCAL_DIR
	
	source_properties
	
	check_vars_server
	check_rollout_user
	
	check_dir_exists $INSTALL_DIR
	check_dir_exists $JAVA_HOME
	
	export JAVA_HOME=$JAVA_HOME
	
	check_file_exists ~/local_settings.properties
	
	trace prepare "checking  if the local settings file has the right permissions"	
	local LOCAL_SETTINGS_PERMS=$(ls -l ~/local_settings.properties | cut -d " " -f 1)
	if [ "$LOCAL_SETTINGS_PERMS" != "-rw-------" ]; then
		trace prepare "file ~/local_settings.properties has permissions '$LOCAL_SETTINGS_PERMS'. It should have '-rw-------'. Use 'chmod 0600 ~/local_settings.properties' to fix "
	fi

	trace prepare END
}

install_adempiere()
{	
	trace install_adempiere BEGIN

	prepare
		
	stop_adempiere
	
	local JBOSS_APP_FOLDER=adempiere

	if [[ -d "${ADEMPIERE_HOME}/jboss/server/metasfresh" ]]; then
	   JBOSS_APP_FOLDER=metasfresh
	fi

	#This is only required until we also modernized the server-rollout
	trace install_adempiere "Deleting the legacy adempiere.ear if it exists"
	if [ -d "${ADEMPIERE_HOME}/jboss/server/$JBOSS_APP_FOLDER/deploy/adempiere.ear" ]; then
		rm -vr ${ADEMPIERE_HOME}/jboss/server/$JBOSS_APP_FOLDER/deploy/adempiere.ear
	fi

	#This is only required until we also modernized the server-rollout
	trace install_adempiere "Deleting the legacy adempiereJasper.war if it exists"
	if [ -f "${ADEMPIERE_HOME}/jboss/server/$JBOSS_APP_FOLDER/deploy/adempiereJasper.war" ]; then
		rm -vr ${ADEMPIERE_HOME}/jboss/server/$JBOSS_APP_FOLDER/deploy/adempiereJasper.war
	fi
	
	trace install_adempiere "Copying our deployable files to the jboss deploy folder" 
	cp -Rv ${ROLLOUT_DIR}/deploy/* ${ADEMPIERE_HOME}/jboss/server/$JBOSS_APP_FOLDER/deploy
	
	trace install_adempiere "Copying the 'client' allInOne jar to ${ADEMPIERE_HOME}/lib" 
	cp -v ${ROLLOUT_DIR}/adempiere_lib/* ${ADEMPIERE_HOME}/lib
	
	trace install_adempiere "Making ${ADEMPIERE_HOME}/jboss/server/$JBOSS_APP_FOLDER/deploy/reports.war writable to allowing everyone to install jasper files"
	chmod -R a+w ${ADEMPIERE_HOME}/jboss/server/$JBOSS_APP_FOLDER/deploy/reports.war

	trace install_adempiere "Calling RUN_Post_Rollout_Processes.sh" 
	cd $ADEMPIERE_HOME/utils
	./RUN_Post_Rollout_Processes.sh
	
        if [[ -d ${ADEMPIERE_HOME}/src ]]; then
          trace install_adempiere "Adding source files to app-dir ( ${ADEMPIERE_HOME}/src/sources.tar.gz )"
          tar czf ${ADEMPIERE_HOME}/sources/sources.tar.gz ${SOURCES_DIR}/*
          trace install_adempiere "Done adding source files to app-dir"
        else
          trace install_adempiere "No source-folder present in app-dir. Skipping adding sourcefiles."
        fi
 
	start_adempiere
	
	trace install_adempiere END
}

# task 06284
invoke_customer_script()
{
	trace invoke_customer_script BEGIN
	
	local script=${CUSTOMER_SCRIPT:-}
	
	if [ ! -z ${script} ]; 
	then
		trace invoke_customer_script "Variable CUSTOMER_SCRIPT was set to ${script}. Invoking it now"
		eval ${script}
		trace invoke_customer_script "Done invoking ${script}."
	else
		trace invoke_customer_script "Variable CUSTOMER_SCRIPT was not set. Set it in ${LOCAL_SETTINGS_FILE} to execute a custom script at the end of the rollout"
	fi
	
	trace invoke_customer_script END
}


LOCAL_SETTINGS_FILE=$DEFAULT_LOCAL_SETTINGS_FILE

#parse the command line args (getopts doesn't work from inside a procedure)
while getopts "d:s:" OPTION; do
	echo "$OPTION = $OPTARG"
	case "$OPTION" in
		d)
			ROLLOUT_DIR="$OPTARG"
		;;		
		s)
			LOCAL_SETTINGS_FILE="$OPTARG"
		;;
	esac
done

install_adempiere 

# task 06284
invoke_customer_script

trace $(basename $0) "END"

