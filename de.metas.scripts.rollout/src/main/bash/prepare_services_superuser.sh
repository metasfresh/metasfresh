#!/bin/bash

set -o nounset  #don't allow access to unset variables
set -o errexit  #don't allow any command to exit with an error code !=0

#
# This script does things that the user metasfresh is not allowed to do.
# The "normal" minor_remote.sh script might ask the user to run this script as super user if it encounters a problem 
#

#Thanks to http://stackoverflow.com/questions/6643853/how-to-convert-in-path-names-to-absolute-name-in-a-bash-script for the readlink tip
LOCAL_DIR=$(readlink -m $(dirname $0))
#Note: ROLLOUT_DIR can be overridden from cmdline using -d
ROLLOUT_DIR=$(readlink -m LOCAL_DIR/..)

DEFAULT_LOCAL_SETTINGS_FILE=~/local_settings.properties

prepare_service_superuser()
{
	local service_name=$1

	local SYSTEM_DEPLOY_SOURCE_FOLDER=${ROLLOUT_DIR}/deploy/services
	local SYSTEM_DEPLOY_TARGET_FOLDER=${METASFRESH_HOME}/${service_name}
	
	echo "Checking if /opt/${service_name} needs to be migrated"
	if [[ -d /opt/${service_name} ]]; 
	then

		local INIT_D_FILE=/etc/init.d/${service_name}
		if [[ -x "${INIT_D_FILE}" ]]; then
			echo "Found executable file ${INIT_D_FILE}; Going to try and stop ${service_name}"
			${INIT_D_FILE} stop
			rm -v ${INIT_D_FILE}
		fi

		echo "!!! Copying /opt/${service_name} to $SYSTEM_DEPLOY_TARGET_FOLDER (excluding /opt/${service_name}/log) !!! "
		rsync -av --exclude='log' /opt/${service_name}/ ${SYSTEM_DEPLOY_TARGET_FOLDER}
		
		if [[ -f ${SYSTEM_DEPLOY_TARGET_FOLDER}/metasfresh-webui-api.conf ]];
		then
			# will be replaced with a new version.
			mv -v ${SYSTEM_DEPLOY_TARGET_FOLDER}/metasfresh-webui-api.conf ${SYSTEM_DEPLOY_TARGET_FOLDER}/metasfresh-webui-api.conf_BKP
		fi
		mv /opt/${service_name} /opt/${service_name}_BKP
		echo "!!!  Done !!!"
	else
		echo "OK"
	fi
	
	local SYSTEM_SERVICE_FILE=/etc/systemd/system/${service_name}.service
	echo "Checking if $SYSTEM_SERVICE_FILE exists"
	if [[ ! -f $SYSTEM_SERVICE_FILE ]]; 
	then
		echo "!!! Installing service unit file !!!"
		cd $(pwd)
		unzip ${SYSTEM_DEPLOY_SOURCE_FOLDER}/${service_name}-configs.zip -d ./${service_name}-configs
		cp -v ./${service_name}-configs/configs/${service_name}.service ${SYSTEM_SERVICE_FILE}
		chmod 0644 ${SYSTEM_SERVICE_FILE}
		systemctl daemon-reload
		echo "!!!  Done !!!"
	else
		echo "OK"
	fi
	
	local SYSTEM_SUDOERS_FILE="/etc/sudoers.d/${service_name}"
	echo "Checking if $SYSTEM_SUDOERS_FILE exists"
	if [[ ! -f $SYSTEM_SUDOERS_FILE ]];
	then
		echo "!!! Installing sudoers file !!! "
		echo "metasfresh ALL=(root)NOPASSWD: /bin/systemctl stop ${service_name}.service" > ${SYSTEM_SUDOERS_FILE}
		echo "metasfresh ALL=(root)NOPASSWD: /bin/systemctl start ${service_name}.service" >> ${SYSTEM_SUDOERS_FILE}
		echo "metasfresh ALL=(root)NOPASSWD: /bin/systemctl status ${service_name}.service" >> ${SYSTEM_SUDOERS_FILE}
		echo "metasfresh ALL=(root)NOPASSWD: /bin/systemctl restart ${service_name}.service" >> ${SYSTEM_SUDOERS_FILE}
		chown root:root ${SYSTEM_SUDOERS_FILE}
		chmod 0440 ${SYSTEM_SUDOERS_FILE}
		echo "!!! Done !!!"
	else
		echo "OK"
	fi
}

LOCAL_SETTINGS_FILE=$DEFAULT_LOCAL_SETTINGS_FILE

#parse the command line args (getopts doesn't work from inside a procedure)
while getopts "d:s:" OPTION; do
	case "$OPTION" in
		d)
			echo "$OPTION = $OPTARG"
			ROLLOUT_DIR="$OPTARG"
		;;		
		s)
			echo "$OPTION = $OPTARG"
			LOCAL_SETTINGS_FILE="$OPTARG"
	esac
done

source $LOCAL_SETTINGS_FILE

prepare_service_superuser metasfresh-admin
prepare_service_superuser metasfresh-material-dispo
prepare_service_superuser metasfresh-webui-api
