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

#Note: ROLLOUT_DIR can be overridden from cmdline using -d
#Thanks to http://stackoverflow.com/questions/6643853/how-to-convert-in-path-names-to-absolute-name-in-a-bash-script for the readlink tip
LOCAL_DIR=$(readlink -m $(dirname $0))
ROLLOUT_DIR=$(readlink -m ${LOCAL_DIR}/..)

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
	
	check_file_exists $LOCAL_SETTINGS_FILE
	
	trace prepare "checking  if the local settings file has the right permissions"	
	local LOCAL_SETTINGS_PERMS=$(ls -l ${LOCAL_SETTINGS_FILE} | cut -d " " -f 1)
	if [ "$LOCAL_SETTINGS_PERMS" != "-rw-------" ]; then
		trace prepare "file ~/local_settings.properties has permissions '$LOCAL_SETTINGS_PERMS'. It should have '-rw-------'. Use 'chmod 0600 ~/local_settings.properties' to fix "
	fi
        
        trace prepare END
        
}

install_metasfresh()
{	
	trace install_metasfresh BEGIN
	
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

prompt_superuser_script()
{
	local service_name="$1"
	local message="$2"
	
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
	echo "!! PLEASE READ THE FOLLOWING !!"
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
	echo "${message}"
	echo "To perform this task and possible others, please run the following shell script as SUPER USER. Then rerun this script."
	echo ""
	echo "${LOCAL_DIR}/prepare_services_superuser.sh -s ${LOCAL_SETTINGS_FILE} -d ${ROLLOUT_DIR}"
	echo ""
}

#
# Installs a spring-boot service. 
# Notes:
#   * installs the service binary from ${ROLLOUT_DIR}/deploy/services
#   * the binaries name have be <service_name>.jar or <service_name>-service.jar. Anawys, it's installed in the target folder as <service_name>.jar. 
#     This is consistent with the service's docker image name.
#   * metasfresh-webui-api and "metasfresh-material-dispo will be restarted. All others (currently just metasfresh-admin) won't. This is a hack and will probably be changed
#   * install_metasfresh() also installs a service (metasfresh-backend), but is in a separated function for historical reasons.
#
install_service()
{
	local service_name=$1
	trace install_${service_name} BEGIN

	local SERVICE_DEPLOY_SOURCE_FOLDER=${ROLLOUT_DIR}/deploy/services
	local SERVICE_DEPLOY_TARGET_FOLDER=${METASFRESH_HOME}/${service_name}

	local SERVICE_DEPLOY_SOURCE_NAME=NOTSET
	
	if [[ -f ${SERVICE_DEPLOY_SOURCE_FOLDER}/${service_name}.jar ]];
	then
		SERVICE_DEPLOY_SOURCE_NAME=${service_name}
	elif [[ -f ${SERVICE_DEPLOY_SOURCE_FOLDER}/${service_name}-service.jar ]];
	then
		SERVICE_DEPLOY_SOURCE_NAME=${service_name}-service
	else
		trace install_${service_name} "Service binary ${SERVICE_DEPLOY_SOURCE_FOLDER}/${service_name}[-service].jar is not present. Nothing to do."
		return;
	fi
	trace install_${service_name} "Going to install ${SERVICE_DEPLOY_SOURCE_FOLDER}/${SERVICE_DEPLOY_SOURCE_NAME}.jar"	
	
	if [[ -d /opt/${service_name} ]]; 
	then
		prompt_superuser_script ${service_name} "The service ${service_name} is currently installed in /opt/${service_name}. It needs to be migrated to $SERVICE_DEPLOY_TARGET_FOLDER"
		exit 1;
	fi

	local SYSTEM_SERVICE_FILE=/etc/systemd/system/${service_name}.service
	if [[ ! -f $SYSTEM_SERVICE_FILE ]]; 
	then
		prompt_superuser_script ${service_name} "The systemd service file $SYSTEM_SERVICE_FILE is not yet installed."
		exit 1;
	fi

	local SYSTEM_SUDOERS_FILE="/etc/sudoers.d/${service_name}"
	if [[ ! -f $SYSTEM_SUDOERS_FILE ]];
	then
		prompt_superuser_script ${service_name} "The sudoers.d file $SYSTEM_SUDOERS_FILE is not yet installed. It is required to allow the metasfresh user to start and stop the ${service_name} service."
		exit 1;
	fi

	# make sure the service's folder actually exists
	mkdir -p $SERVICE_DEPLOY_TARGET_FOLDER
	
	local SERVICE_CONF_FILE="$SERVICE_DEPLOY_TARGET_FOLDER/${service_name}.conf"
	if [[ ! -f $SERVICE_CONF_FILE ]];
	then
		trace install_${service_name} "The service conf file $SERVICE_CONF_FILE is not yet installed. It is required to customize the services runtime paramters"
		trace install_${service_name} "Checking if file $(pwd)/${service_name}-configs/configs/${service_name}.conf was already extracted"
		if [[ ! -d "$(pwd)/${SERVICE_DEPLOY_SOURCE_NAME}-configs/configs" ]];
		then
			# if the configs.zip was not yet extracted per our advise, then do it now
			unzip ${SERVICE_DEPLOY_SOURCE_FOLDER}/${SERVICE_DEPLOY_SOURCE_NAME}-configs.zip -d $(pwd)/${service_name}-configs
		fi
		if [[ -f $(pwd)/${service_name}-configs/configs/${service_name}.conf ]];
		then
			cp -v $(pwd)/${service_name}-configs/configs/${service_name}.conf ${SERVICE_CONF_FILE}
		fi
	fi
	
	# check if service is running. if it is, it's safe to assume stopping/starting during rollout
	#
	local service_isactive=NOTSET
    if [[ $(systemctl status ${service_name}.service | grep "running" | wc -l) -gt "0" ]]; then
        trace install_${service_name} "Stopping service"
        service_isactive=yes
        sudo systemctl stop ${service_name}.service
    fi
	
	mkdir -p ${METASFRESH_HOME}/${service_name}
	
	if [[ -f ${SERVICE_DEPLOY_TARGET_FOLDER}/${service_name}.jar ]]; 
	then
		trace install_${service_name} "Making sure that the existing service jar can be overwritten with our new version"
		chmod 200 ${SERVICE_DEPLOY_TARGET_FOLDER}/${service_name}.jar
	fi

	cp -v ${SERVICE_DEPLOY_SOURCE_FOLDER}/${SERVICE_DEPLOY_SOURCE_NAME}.jar ${SERVICE_DEPLOY_TARGET_FOLDER}/${service_name}.jar
	
	trace install_${service_name} "Making sure that the main jar shall only be accessible for its owner"
	chmod 500 ${SERVICE_DEPLOY_TARGET_FOLDER}/${service_name}.jar
	
    # if system was running before rollout, start it back up
    #
    if [[ ${service_isactive} = "yes" ]]; then
    	trace install_${service_name} "Starting service"
        sudo systemctl start ${service_name}.service
    fi
    
    if [[ ${service_name} = "metasfresh-webui-api" ]] || [[ ${service_name} = "metasfresh-material-dispo" ]]; then
        sudo systemctl restart ${service_name}.service
    fi 
	
	trace install_${service_name} END
}

install_metasfresh-webui-frontend()
{
	trace install_metasfresh-webui-frontend BEGIN
	
	# First, check if there is anything to do at all
	# Thx to http://stackoverflow.com/a/13864829/1012103 on how to check if METASFRESH_WEBUI_FRONTEND_HOME is set
	if [ -z ${METASFRESH_WEBUI_FRONTEND_HOME+x} ]; 
	then
		trace install_metasfresh-webui-frontend "Variable METASFRESH_WEBUI_FRONTEND_HOME is not set. Not installing the webui-frontend"
		return
	fi
	
	local SRC_TAR="${ROLLOUT_DIR}/deploy/metasfresh-webui-frontend.tar.gz"
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

prepare

install_metasfresh 

install_service metasfresh-admin

# we now have the option to run material-dispo as a part of metasfresh(-app), so in order to ease the local admin's life we do just that
# install_service metasfresh-material-dispo 

install_service metasfresh-webui-api

install_metasfresh-webui-frontend

# task 06284
invoke_customer_script

trace $(basename $0) "END"
