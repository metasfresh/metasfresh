#!/bin/bash

set -o nounset  #don't allow access to unset variables
set -o errexit  #don't allow any command to exit with an error code !=0

#
# This script does things that the user metasfresh is not allowed to do.
# The "normal" minor_remote.sh script might ask the user to run this script as super user if it encounters a problem 
#

prepare_service_superuser()
{
	local service_name=$1
	
	echo "Checking if /opt/${service_name} needs to be migrated"
	if [[ -d /opt/${service_name} ]]; 
	then
		echo "!!! Copying /opt/${service_name} to $SYSTEM_DEPLOY_TARGET_FOLDER !!! "
		stop_metasfresh-webui-api
		cp -v /opt/${service_name} $SYSTEM_DEPLOY_TARGET_FOLDER
		
		# needs to be replaced with a new version. details for the user will follow when this script proceeds" >> ${service_name}_copy_to_correct_folder.sh
		rm /opt/metasfresh/metasfresh-webui-api/metasfresh-webui-api.conf
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
	
prepare_service_superuser metasfresh-admin
prepare_service_superuser metasfresh-material-dispo
prepare_service_superuser metasfresh-webui-api
