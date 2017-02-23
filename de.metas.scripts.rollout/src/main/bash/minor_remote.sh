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


LOCAL_DIR=$(dirname $0)

if [ "$LOCAL_DIR" == "." ]; then
	LOCAL_DIR=$(pwd)
fi

#Note: ROLLOUT_DIR can be overridden from cmdline using -d
ROLLOUT_DIR=$LOCAL_DIR/..

SOURCES_DIR=$ROLLOUT_DIR/sources

HOSTNAME=$(hostname)

RELEASE_TIME=$(date "+%Y%m%d_%H%M%S")

DEFAULT_LOCAL_SETTINGS_FILE=~/local_settings.properties

# by default, this script will stop and start metasfrresh
SKIP_START_STOP=false

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

	check_java_version
	
	check_file_exists ~/local_settings.properties
	
	trace prepare "checking  if the local settings file has the right permissions"	
	local LOCAL_SETTINGS_PERMS=$(ls -l ~/local_settings.properties | cut -d " " -f 1)
	if [ "$LOCAL_SETTINGS_PERMS" != "-rw-------" ]; then
		trace prepare "file ~/local_settings.properties has permissions '$LOCAL_SETTINGS_PERMS'. It should have '-rw-------'. Use 'chmod 0600 ~/local_settings.properties' to fix "
	fi
        
        trace prepare END
        
}

install_metasfresh()
{	
	trace install_metasfresh BEGIN

	prepare
	
	if [[ -f ${METASFRESH_HOME}/metasfresh_server.conf ]]; then
		trace install_metasfresh "The local instalation is already spring-bootified"
	else
		trace install_metasfresh "The local instalation is not yet spring-bootified."
		trace install_metasfresh "Please run the script spring_bootify_metasfresh.sh as user root,"
		trace install_metasfresh "then please run this script again"
		exit 1
	fi
	
	stop_metasfresh
	
    if [[ -d ${METASFRESH_HOME}/src ]]; then
          trace install_metasfresh "Adding source files to app-dir ( ${METASFRESH_HOME}/src/sources.tar.gz )"
          tar czf ${METASFRESH_HOME}/src/sources.tar.gz ${SOURCES_DIR}/*
          trace install_metasfresh "Done adding source files to app-dir"
    else
          trace install_metasfresh "No source-folder present in app-dir. Skipping adding sourcefiles."
	fi

	trace main "Making sure that the main jar can be overwritten with our new version"
	chmod 200 ${METASFRESH_HOME}/metasfresh_server.jar
	
	
	trace install_metasfresh "Copying our files to the metasfresh folder" 
	cp -Rv ${ROLLOUT_DIR}/deploy/* ${METASFRESH_HOME}

	trace main "Making sure that the main jar shall only be accessible for its owner"
	chmod 500 ${METASFRESH_HOME}/metasfresh_server.jar
	
	trace install_metasfresh "Making ${METASFRESH_HOME}/reports/ writable to allowing everyone to install jasper files"
	chmod -R a+w ${METASFRESH_HOME}/reports/

	start_metasfresh
	
	trace install_metasfresh END
}

install_metasfresh-webui-api()
{
	trace install_metasfresh-webui-api BEGIN
	
	# First, check if there is anything to do at all
	if [ ! -z ${METASFRESH_WEBUI_API_HOME} ]; 
	then
		trace install_metasfresh-webui-api "Variable METASFRESH_WEBUI_API_HOME is not set. Not installing the webui-api"
		return
	fi
	
	local SRC_JAR="${ROLLOUT_DIR}/deploy/download/metasfresh-webui-api.jar"
	if [ ! -e ${SRC_JAR} ];
	then
		trace install_metasfresh-webui-api "File ${SRC_JAR} is not part of this package. Not installing the webui-api"
		return
	fi

	local TARGET_JAR="${METASFRESH_WEBUI_API_HOME}/metasfresh-webui-api.jar"	
	if [ -e $TARGET_JAR ]; then
		stop_metasfresh-webui-api
	fi
	
	cp -v $SRC_JAR $TARGET_JAR
	chmod -v 200 $TARGET_JAR
	chown -v metasfresh: $TARGET_JAR
		
	start_metasfresh-webui-api
	
	trace install_metasfresh-webui-api END
}

install_metasfresh-webui-frontend()
{
	trace install_metasfresh-webui-frontend BEGIN
	
	# First, check if there is anything to do at all
	if [ ! -z ${METASFRESH_WEBUI_FRONTEND_HOME} ]; 
	then
		trace install_metasfresh-webui-frontend "Variable METASFRESH_WEBUI_FRONTEND_HOME is not set. Not installing the webui-frontend"
		return
	fi
	
	local SRC_TAR="${ROLLOUT_DIR}/deploy/download/metasfresh-webui-frontend.tar.gz"
	if [ ! -e ${SRC_TAR} ];
	then
		trace install_metasfresh-webui-frontend "File ${SRC_TAR} is not part of this package. Not installing the webui-frontend"
		return
	fi

	cd ${METASFRESH_WEBUI_FRONTEND_HOME}
	cp -a ./dist/config.js ./
	rm -r ./dist
	
	cp -v ${SRC_TAR} ${METASFRESH_WEBUI_FRONTEND_HOME}
	tar xvzf ./metasfresh-webui-frontend.tar.gz
	
	cp -a ./config.js ./dist/
	chown metasfresh:metasfresh -R ./dist
	
	trace install_metasfresh-webui-frontend END
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
while getopts "d:s:n" OPTION; do
	case "$OPTION" in
		d)
			echo "$OPTION = $OPTARG"
			ROLLOUT_DIR="$OPTARG"
		;;		
		s)
			echo "$OPTION = $OPTARG"
			LOCAL_SETTINGS_FILE="$OPTARG"
		;;
		n)
			SKIP_START_STOP="true"
	esac
done

install_metasfresh 
install_metasfresh-webui-api
install_metasfresh-webui-frontend

# task 06284
invoke_customer_script

trace $(basename $0) "END"
