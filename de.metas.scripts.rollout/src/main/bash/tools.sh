#!bin/bash
#
# Contains useful methods.
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

check_var()
{
	varName=$1
	var=$2
	
	if [ "$var" = "" ]
	then
		trace "check_vars" "Variable/Param '${varName}' must be set"
		exit 1
	fi
	trace "check_vars" "Variable/Param '${varName}' is set to ${var}"
	
	return 0
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
	
	check_var "INSTALL_DIR" $INSTALL_DIR
	check_var "ADEMPIERE_HOME" $ADEMPIERE_HOME
	check_var "ADEMPIERE_BINARY_ZIP" $ADEMPIERE_BINARY_ZIP
	check_var "ADEMPIERE_APPS_SERVER" $ADEMPIERE_APPS_SERVER
	check_var "ANT_HOME" $ANT_HOME
	check_var "JAVA_HOME" $JAVA_HOME
	
	if [ "$DO_AOP" != "false" ]; then
		check_var "JBOSS_AOP_VERSION" $JBOSS_AOP_VERSION
		check_var "JBOSS_VERSION" $JBOSS_VERSION
	else
		trace check_vars_server "Not checking APO variables (DO_AOP='${DO_AOP}')"
	fi
	
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
	
	check_var "ADEMPIERE_DB_SERVER" $ADEMPIERE_DB_SERVER
	check_var "ADEMPIERE_DB_NAME" $ADEMPIERE_DB_NAME
	
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

	check_var "ADEMPIERE_HOME" $ADEMPIERE_HOME
	check_var "JAVA_HOME" $JAVA_HOME
	check_var "PATH" $PATH
	check_var "ANT_HOME" $ANT_HOME
	
	trace check_vars_minor END
}

start_adempiere()
{
	trace start_adempiere BEGIN
	
	if [ -f /etc/debian_version ]; then
	
		# debian/ubuntu
		trace start_adempiere "Starting server via init script"
		check_std_tool sudo
		if [ -f /etc/init.d/metasfresh_server ]; then
		  sudo /etc/init.d/metasfresh_server start
                else
		  sudo /etc/init.d/adempiere_server start
		fi
	else
		# gentoo
		trace start_adempiere "Starting server via init script"
		check_std_tool sudo
		sudo /etc/init.d/adempiere start
	fi
	
	trace start_adempiere END
}

stop_adempiere()
{
	trace stop_adempiere BEGIN
		
	if [ -f /etc/debian_version ]; then
	
		# debian/ubuntu
		trace stop_adempiere "Stopping server via init script"		
		check_std_tool sudo
		if [ -f /etc/init.d/metasfresh_server ]; then
		  sudo /etc/init.d/metasfresh_server stop
		else
		  sudo /etc/init.d/adempiere_server stop
		fi
	
#		# With our current crappy service startup script, debian and ubuntu tend to hang up the ssh session when those scripts are called via ssh from dejen901 (jenkins)
#		# Therefore we call the stop script directly
#		# Also note that RUN_Server2Stop.sh fails when adempiere is not running. Therefore we temporarily allow exit codes!=0
#		trace stop_adempiere "Stopping server"
#		set +e
#		cd ${ADEMPIERE_HOME}/utils && ./RUN_Server2Stop.sh
#		set -e

	else
		# gentoo
		trace stop_adempiere "Stopping server via init script"		
		check_std_tool sudo
		sudo /etc/init.d/adempiere stop
	fi
	
	trace stop_adempiere END
}

add_service_adempiere()
{
	trace add_service_adempiere BEGIN
		
	if [ -f /etc/debian_version ]; then
		trace add_service_adempiere "ts didn't get this to work reliadbly under Debian/Ubuntu, so pls do 'sudo update-rc.d adempiere defaults' manually"
		#sudo update-rc.d adempiere defaults
	else
		# gentoo
		trace add_service_adempiere "Making sure that adempiere server is started at system startup"
		check_std_tool sudo
		sudo /sbin/rc-update add adempiere default
	fi
	
	trace add_service_adempiere END
}

#
# Tool for update_adempiere_scripts. 
# Check if the file to rename exits
# Renames the file unless the target file exists
#
rename_file()
{
	trace rename_file BEGIN
	
	local renameFileName=$1
	local renameFileNameExt=$2
	local renameFileNameSuffix=$3
	
	check_file_exists ${renameFileName}.${renameFileNameExt}
	
	if [ -f ${renameFileName}_${renameFileNameSuffix}.${renameFileNameExt} ]; then
		trace rename_file "${renameFileName}_${renameFileNameSuffix}.${renameFileNameExt} exists. Doing nothing"
	else
		echo "mv -v ${renameFileName}.${renameFileNameExt} ${renameFileName}_${renameFileNameSuffix}.${renameFileNameExt}"
		mv -v ${renameFileName}.${renameFileNameExt} ${renameFileName}_${renameFileNameSuffix}.${renameFileNameExt}
	fi
	
	trace rename_file END
}

update_adempiere_scripts()
{
	trace update_adempiere_scripts BEGIN
	
	rename_file ${INSTALL_DIR}/Adempiere/build xml orig
	rename_file ${INSTALL_DIR}/Adempiere/RUN_silentsetup sh bkp
	
	cp -v ${ROLLOUT_DIR}/install/misc/build.xml ${INSTALL_DIR}/Adempiere/build.xml
	cp -v ${ROLLOUT_DIR}/install/misc/RUN_silentsetup.sh ${INSTALL_DIR}/Adempiere/RUN_silentsetup.sh
	
	#making script executable
	trace update_adempiere_scripts "Making script RUN_silentsetup executable"
	chmod a+x ${ADEMPIERE_HOME}/RUN_silentsetup.sh
	
	trace update_adempiere_scripts END
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

#set_CONFIGFILE_var()
#{
#	if [ -f /etc/debian_version ]; then
#		CONFIGFILE="/etc/adempiere/adempiere.conf"
#	else
#		# assuming gentoo
#		CONFIGFILE="/etc/conf.d/adempiere"
#	fi
#}
