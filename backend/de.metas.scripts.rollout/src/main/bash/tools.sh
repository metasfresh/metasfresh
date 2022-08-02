#!bin/bash
#
# Contains useful methods.
# IMPORTANT: tools.sh requires bash, so if you source it anywhere, make sure that the script which sources it also uses bash
#

check_file_exists()
{
	fileName=$1
	
	if [ -f $fileName ]; then
		trace "check_file_exists" "File ${fileName} exists. Checking if readable"
		check_file_readable $fileName
		return 0
	fi	
	
	trace "check_file_exists" "File ${fileName} not existing"
	exit 1
}

check_dir_exists()
{
	dirName=$1
	if [ -d $dirName ]; then
		trace "check_dir_exists" "Dir ${dirName} exists"
		check_file_readable $dirName
		return 0
	fi	
	
	trace "check_dir_exists" "Dir ${dirName} not existing"
	exit 1
}

#Works for files and directories
check_file_readable(){

	fileName=$1
	
	if [ -r $fileName ]; then
		trace "check_file_readable" "File ${fileName} is readable"
		return 0
	fi
	
	trace "check_file_readable" "File ${fileName} is not readable"
	exit 1
}

#
# If the given variable is not set (value being either "" or "NOT_SET"), then this method fails the script (exit 1)
#
check_var()
{
	local varName=$1
	local var=$2
	
	if [[ "$var" = "" || "$var" = "NOT_SET" ]]
	then
		trace "check_var" "Variable/Param '${varName}' must be set"
		exit 1
	fi
	trace "check_var" "Variable/Param '${varName}' is set to ${var}"
	
	return 0
}

# 
# If the first given variable is not set (value being either "" or "NOT_SET"), then it falls back to the second given variable 
# and sets the first var to the second var's value. If the second variabe is not set either then this method fails the script (exit 1).
#
check_var_fallback()
{
	local varName=$1
	local var=$2
	local fallback_varName=$3
	local fallback_var=$4
	
	if [[ "$var" = "" || "$var" = "NOT_SET" ]]
	then
		trace "check_var_fallback" "Variable '${varName}' is not set. Trying fallback to variable '${fallback_varName}'"
		check_var $fallback_varName $fallback_var
		trace "check_var_fallback" "Setting '${varName}' to ${fallback_var}"
		eval $varName=$fallback_var
	fi
	trace "check_var_fallback" "Variable/Param '${varName}' is set to ${var}"
}

check_std_tool()
{
	local tool=$1
	trace prepare "checking if '${tool}' is available: `which ${tool}`"
	which $tool 1>/dev/null
}

trace()
{
	procedure_name=$1
	msg=$2
	time=$(date "+%Y-%m-%d %H:%M:%S") 
	echo " | ${time} | ${procedure_name} | ${msg}"
}

check_exit_code()
{
	err_status=$1
	proc=$2
	err_msg=$3
	if [ $err_status -ne 0 ]
	then
		trace $proc "EXITCODE=${err_status}; Message: ${err_msg}"
		exit -1
	fi
}

check_vars_server()
{
	trace check_vars_server BEGIN

	check_var_fallback "METASFRESH_HOME" ${METASFRESH_HOME:-NOT_SET} "ADEMPIERE_HOME" ${ADEMPIERE_HOME:-NOT_SET}
			
	trace check_vars_server END
}

check_rollout_user()
{
	trace check_rollout_user BEGIN
	
	check_var "ROLLOUT_USER" $ROLLOUT_USER
	local CURRENT_USER=$(whoami)
	if [ "$CURRENT_USER" != "$ROLLOUT_USER" ]; then
		trace "check_rollout_user" "ROLLOUT_USER from settings is ${ROLLOUT_USER}, but current user is ${CURRENT_USER}"
		exit 1
	fi 
	
	trace check_rollout_user END
}

check_vars_database()
{
	trace check_vars_database BEGIN
	
	check_var_fallback "METASFRESH_DB_SERVER" ${METASFRESH_DB_SERVER:-NOT_SET} "ADEMPIERE_DB_SERVER" ${ADEMPIERE_DB_SERVER:-NOT_SET}
	check_var_fallback "METASFRESH_DB_NAME" ${METASFRESH_DB_NAME:-NOT_SET} "ADEMPIERE_DB_NAME" ${ADEMPIERE_DB_NAME:-NOT_SET}
	
	trace check_vars_database END
}

#
# reads the rollout-properties and the local (host-specific) properties
#
source_properties()
{
	trace source_properties BEGIN
	
	check_var "ROLLOUT_DIR" $ROLLOUT_DIR
	check_var "HOSTNAME" $HOSTNAME

	#reading local settings
	check_file_readable $LOCAL_SETTINGS_FILE
	trace source_properties "sourcing ${LOCAL_SETTINGS_FILE}"
	source $LOCAL_SETTINGS_FILE
		
	trace source_properties END
}

check_vars_minor()
{
	trace check_vars_minor BEGIN

	check_var_fallback "METASFRESH_HOME" ${METASFRESH_HOME:-NOT_SET} "ADEMPIERE_HOME" ${ADEMPIERE_HOME:-NOT_SET}
	check_var "PATH" $PATH

	trace check_vars_minor END
}

start_metasfresh()
{
	trace start_metasfresh BEGIN

	if [ "${SKIP_START_STOP:-false}" == "true" ]; then
		trace start_metasfresh "SKIP_START_STOP = ${SKIP_START_STOP}, so we skip this"
	else
				# jira issue ME-46
                if [ "$(cat /proc/1/comm)" == "systemd" ]; then
                        sudo /bin/systemctl start metasfresh_server.service
                else
                        service metasfresh_server start
                fi

	fi

	trace start_metasfresh END
}

stop_metasfresh()
{
	trace stop_metasfresh BEGIN

	if [ "${SKIP_START_STOP:-false}" == "true" ]; then
		trace start_metasfresh "SKIP_START_STOP = ${SKIP_START_STOP}, so we skip this"
	else
				# jira issue ME-46
                if [ "$(cat /proc/1/comm)" == "systemd" ]; then
                        sudo /bin/systemctl stop metasfresh_server.service
                else
                        service metasfresh_server stop
                fi
	fi
		
	trace stop_metasfresh END
}

start_metasfresh-webui-api()
{
	if [ -e /etc/init.d/metasfresh-webui-api ]; 
	then
		echo Starting metasfresh-webui-api
		/etc/init.d/metasfresh-webui-api start
	else
		echo "/etc/init.d/metasfresh-webui-api does not exist. Please create it. Menawhile, this script does *not* start the service."
		echo "to create /etc/init.d/metasfresh-webui-api , you can do (as root):"
		echo "		ln -s metasfresh-webui-api.jar /etc/init.d/metasfresh-webui-api"
	fi
}

stop_metasfresh-webui-api()
{
	if [ -e /etc/init.d/metasfresh-webui-api ]; 
	then
		echo Stopping metasfresh-webui-api
		/etc/init.d/metasfresh-webui-api stop
	elif [ -e /etc/systemd/system/metasfresh_webui.service]; 
	then
		echo Stopping metasfresh-webui-api
		systemctl stop metasfresh_webui.service
	else
		echo "/etc/init.d/metasfresh-webui-api does not exist. Please create it. Menawhile, this script does *not* stop the service."
	fi
}

delete_rollout()
{
	trace delete_rollout BEGIN

	local rollout_dir=$(readlink -f ${ROLLOUT_DIR}/.. )
	trace clean_previous_rollout "Deleting ${rollout_dir}"
	rm -r $rollout_dir
	
	trace delete_rollout END
}

clean_previous_rollout()
{
	trace clean_previous_rollout BEGIN
	
	local abs_rollout_dir=$(readlink -f ${ROLLOUT_DIR} )
	local abs_rollout_last=$(readlink -f ${ROLLOUT_DIR}/../../rollout_last )
	local abs_rollout_current=$(readlink -f ${ROLLOUT_DIR}/../../rollout_current )
	
	if [ -d ${abs_rollout_last} ]; then
		trace clean_previous_rollout "deleting ${abs_rollout_last}/* dir"
		rm -v ${ROLLOUT_DIR}/../../rollout_last # remoing symlink
		rm -rv ${abs_rollout_last} # removing dir
	fi
	
	if [ -d ${abs_rollout_current} ]; then
		trace clean_previous_rollout "Making rollout_current -> rollout_last"
		mv -v ${ROLLOUT_DIR}/../../rollout_current ${ROLLOUT_DIR}/../../rollout_last
	fi
	
	trace clean_previous_rollout "Making new rollout_current"
	ln -s ${abs_rollout_dir} ${ROLLOUT_DIR}/../../rollout_current
	
	trace clean_previous_rollout END
}
