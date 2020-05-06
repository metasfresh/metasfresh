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

ROLLOUT_DIR=$LOCAL_DIR/..

DEFAULT_LOCAL_SETTINGS_FILE=~metasfresh/local_settings.properties
LOCAL_SETTINGS_FILE=$DEFAULT_LOCAL_SETTINGS_FILE

#
# do it
#
main()
{
	trace main BEGIN
	
	local CURRENT_USER=$(whoami)
	if [ "$CURRENT_USER" != "root" ]; then
		trace main "this script needs to be run as user root"
		exit 1
	fi
	
	source $LOCAL_SETTINGS_FILE
	
	check_var "ROLLOUT_USER" $ROLLOUT_USER
	check_var_fallback "METASFRESH_HOME" ${METASFRESH_HOME:-NOT_SET} "ADEMPIERE_HOME" ${ADEMPIERE_HOME:-NOT_SET}

	if [[ -f ${METASFRESH_HOME}/metasfresh_server.conf ]]; then
		trace main "The file ${METASFRESH_HOME}/metasfresh_server.conf exists, so aparently the local instalation is already spring-bootified"
		trace main "If this is not the case, please remove or rename that file an run this script again"
		exit 1
	fi
	
	trace main "Stopping the old metasfresh server"
	service metasfresh_server stop

	trace main "Moving the old metasfresh directory out of the way"
	mv -v ${METASFRESH_HOME} ${METASFRESH_HOME}_old
	
	trace main "Creating the new metasfresh home directory and its pid file directory"
	mkdir -vp ${METASFRESH_HOME}/metasfresh_server
	
	trace main "Salvaging the existing metasfresh.properties file from the old directory"
	cp -v ${METASFRESH_HOME}_old/metasfresh.properties ${METASFRESH_HOME}
	
	if [ -f /home/${ROLLOUT_USER}/update_app_client.sh ]; then
		trace main "Installing a new update_app_client.sh file"
		chmod -v 200 /home/${ROLLOUT_USER}/update_app_client.sh
		cp -v ${ROLLOUT_DIR}/misc/update_app_client.sh /home/${ROLLOUT_USER}
		chown -v ${ROLLOUT_USER}:${ROLLOUT_USER} /home/${ROLLOUT_USER}/update_app_client.sh
		chmod -v 500 /home/${ROLLOUT_USER}/update_app_client.sh
	fi

	trace main "Copying the main jar, to be linked as service"
	cp -v ${ROLLOUT_DIR}/deploy/metasfresh_server.jar ${METASFRESH_HOME}
	
	trace main "Making sure that the main jar shall only be accessible for its owner"
	chmod 500 ${METASFRESH_HOME}/metasfresh_server.jar

	trace main "Copying the file with our runtime parameters"
	cp -v ${ROLLOUT_DIR}/misc/metasfresh_server.conf ${METASFRESH_HOME}
	chmod -v 400 ${METASFRESH_HOME}/metasfresh_server.conf

	trace main "Making sure that everything so far is owned by ${ROLLOUT_USER}"
	chown -Rv ${ROLLOUT_USER}:${ROLLOUT_USER} ${METASFRESH_HOME}

	trace main "Linking metasfresh_server.jar as service"
	rm -v /etc/init.d/metasfresh_server
	ln -vs /opt/metasfresh/metasfresh_server.jar /etc/init.d/metasfresh_server
	
	trace main "Done! Please run the script minor_remote.sh as user ${ROLLOUT_USER} to finish the update."
	trace main END
}

trace $(basename $0) BEGIN

#parse the command line args (getopts doesn't work from inside a procedure)
while getopts "s:" OPTION; do
	echo "$OPTION = $OPTARG"
	case "$OPTION" in
		s)
			LOCAL_SETTINGS_FILE="$OPTARG"
		;;
	esac
done

main
	
trace $(basename $0) END