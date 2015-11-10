#!/bin/bash

#
# NOTE: this script does not to any parameter parsing anymore. It is driven by environment variables.
#

#Don't do anything by default
if [ "$DATABASE" == "" ]; then
	DATABASE="false"
fi
if [ "$MINOR" == "" ]; then
	MINOR="false"
fi

#Note: BUILD_URL is mandatory
#if [ "$BUILD_URL" == "" ]; then
#	BUILD_URL="NOT_SET"
#fi

if [ "$TARGET_USER" == "" ]; then
	TARGET_USER="metas"
fi

if [ "$TARGET_HOST" == "" ]; then
	TARGET_HOST="NOT_SET"
fi

if [ "$ROLLOUT_DIR" == "" ]; then
	ROLLOUT_DIR="NOT_SET"
fi

if [ "$CLEAN_ROLLOUT_APPSERVER" == "" ]; then
	CLEAN_ROLLOUT_APPSERVER="true"
fi

if [ "$WORKSPACE" == "" ]; then
	#fallback
	WORKSPACE=$(pwd) 
fi

set -o nounset  #don't allow access to unset variables
set -o errexit  #don't allow any command to exit with an error code !=0

## Port used for ssh and scp. Can be altered from cmdline parameter -p
SSH_PORT=22

RELEASE_TIME=$(date "+%Y%m%d_%H%M%S")
REMOTE_DIR="/home/${TARGET_USER}/${RELEASE_TIME}"
REMOTE_EXEC_DIR="${REMOTE_DIR}/dist/install"
WRAPPER_APP="metasfresh_server"	
####
# Seting local and rollout dir
####
LOCAL_DIR=$(dirname $0)
if [ "$LOCAL_DIR" == "." ]; then
	LOCAL_DIR=$(pwd)
fi

SOURCES=${LOCAL_DIR}/../sources

TOOLS=${LOCAL_DIR}/tools.sh
if [ -f $TOOLS ] && [ -r $TOOLS ]; then
	source $TOOLS
else
	echo "Missing file ${TOOLS}"
	exit 1
fi

prepare()
{
	trace prepare BEGIN
	
	check_std_tool ssh
	check_std_tool scp
	check_std_tool wget
	
	check_var DATABASE $DATABASE
	check_var MINOR $MINOR
	
	check_var BUILD_URL $BUILD_URL
	check_var ROLLOUT_DIR $ROLLOUT_DIR
	check_var TARGET_HOST $TARGET_HOST
	check_var SSH_PORT $SSH_PORT
	
	check_var BUILD_URL $BUILD_URL
	check_var DIST_ARCHIVE $DIST_ARCHIVE
	
	
	if [ "$DATABASE" = "false" ] && [ "$MINOR" = "false" ]; 
	then
		trace prepare "============================================================================================"
		trace prepare "= IMPORTANT: we will only copy the data, but do not actual rollout!                        ="
		trace prepare "= Set at least one of DATABASE or MINOR environment variables to do more than just copying ="
		trace prepare "============================================================================================"
	fi
		
	if [ "$ROLLOUT_DIR" = "NOT_SET" ]  && [ "$BUILD_URL" = "NOT_SET" ]; 
	then
		trace prepare "At least one of -d or the environment variable 'BUILD_URL' must be set"
		exit 1
	fi
	
	if [ "$ROLLOUT_DIR" != "NOT_SET" ] && [ "$BUILD_URL" != "NOT_SET" ]; 
	then
		trace prepare "ignoring BUILD_URL because a rollout dir is set"
		exit 1
	fi

	DIST_FILE=$(basename ${DIST_ARCHIVE})
	check_var DIST_FILE $DIST_FILE
	
	#getting build number from build URL
	#example for a build URL:
	#http://debuild901:8080/job/us1017_ma01_ad_build/29/
	local build_no=$(echo $BUILD_URL | cut -d '/' -f 6 )
	check_var build_no $build_no
		
	trace prepare "Setting DOWNLOAD_FILE from BUILD_URL"
				
	DOWNLOAD_FILE=${BUILD_URL}/${DIST_ARCHIVE}
	check_var DOWNLOAD_FILE "${DOWNLOAD_FILE}"
	
	trace prepare "Downloading rollout file"
							
	wget --no-verbose ${DOWNLOAD_FILE}
		
	ROLLOUT_DIR=${WORKSPACE}
		
	check_var ROLLOUT_DIR "${ROLLOUT_DIR}"

	check_var TARGET_HOST "${TARGET_HOST}"
	
	rollout_common $TARGET_HOST
	
	trace prepare END
}

rollout_common()
{
	trace rollout_common BEGIN

	local TARGET_HOST=$1
	check_var TARGET_HOST $TARGET_HOST
	check_ssh $TARGET_HOST $SSH_PORT $TARGET_USER
	
	
	trace rollout_common "Creating remote directory ${REMOTE_DIR}"
	ssh -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "mkdir -p ${REMOTE_DIR}"
		
	trace rollout_common "Copying file ${DIST_FILE} to remote directory ${TARGET_HOST}:${REMOTE_DIR}"
	scp -P ${SSH_PORT} ${ROLLOUT_DIR}/${DIST_FILE} ${TARGET_USER}@${TARGET_HOST}:${REMOTE_DIR}
	
	trace rollout_common "Extracting the file ${DIST_FILE} on ${TARGET_HOST}"
	ssh -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "cd ${REMOTE_DIR} && tar -xf ${REMOTE_DIR}/${DIST_FILE}"
	
	ssh -p ${SSH_PORT} -q ${TARGET_USER}@${TARGET_HOST} [[ -f /etc/init.d/adempiere_server ]] && WRAPPER_APP="adempiere_server"

	trace rollout_common "Stopping $WRAPPER_APP on ${TARGET_HOST}"
	ssh -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "sudo /etc/init.d/${WRAPPER_APP} stop"
		
	trace rollout_common END
}

rollout_minor()
{
	trace rollout_minor BEGIN

	local MINOR_SH=${LOCAL_DIR}/minor_remote.sh
	check_file_exists $MINOR_SH

	#invoking script
	trace rollout_minor "Invoking remote script minor_remote.sh"
	trace rollout_minor "=========================================================="
	# Note: 
	#   the -tt is necessary because we might call sudo on the remote site and sudo requries a tty 
	#   Multiple -t options force tty allocation, even if ssh has no local tty.
	ssh -tt -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "${REMOTE_EXEC_DIR}/minor_remote.sh" 
	trace rollout_minor "=========================================================="
	trace rollout_minor "Done with remote script minor_remote.sh"
	
	trace rollout_minor END
}

rollout_database()
{
	trace rollout_database BEGIN
	
	trace rollout_database "Making remote script sql_remote.sh executable"
	ssh -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "chmod a+x ${REMOTE_EXEC_DIR}/sql_remote.sh" 
	
	trace rollout_database "Invoking remote script sql_remote.sh"
	trace rollout_database "=========================================================="
#	# Note: 
#	#   the -tt is necessary because we might call sudo on the remote site and sudo requires a tty 
#	#   Multiple -t options force tty allocation, even if ssh has no local tty.
#	ssh -tt -p ${SSH_PORT} adempiere@${TARGET_HOST} "${REMOTE_EXEC_DIR}/sql_remote.sh" 
	ssh -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "${REMOTE_EXEC_DIR}/sql_remote.sh -d ${REMOTE_EXEC_DIR}/.." 
	trace rollout_database "=========================================================="
	trace rollout_database "Done with remote script sql_remote.sh"

	trace rollout_database END
}

rollout_sources()
{
 trace rollout_sources BEGIN
 if ssh -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "test -d '/opt/metasfresh/src'"; then
   trace rollout_sources "Adding source-files to app folder (/opt/metasfresh/src/metasfresh_src.tar.gz)"
   ssh -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "tar czf /opt/metasfresh/src/metasfresh_src.tar.gz ${SOURCES}/*"
 else
   trace rollout_sources "Skipping adding source-files to app folder."
 fi
 trace rollout_sources END
}

clean_rollout_appserver()
{
	trace clean_rollout_appserver BEGIN

	if [ "$CLEAN_ROLLOUT_APPSERVER" != "true" ]; then
		trace clean_rollout_appserver "SKIPPING deletion of dir ${REMOTE_DIR} on host ${TARGET_HOST} (CLEAN_ROLLOUT_APPSERVER=${CLEAN_ROLLOUT_APPSERVER})"
	else
		trace clean_rollout_appserver "Making remote script cleanrollout_remote.sh executable"
		ssh -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "chmod a+x ${REMOTE_EXEC_DIR}/cleanrollout_remote.sh"
	
		trace clean_rollout_appserver "Invoking remote script cleanrollout_remote.sh"
		trace clean_rollout_appserver "=========================================================="
		# Note: 
		#   the -tt is necessary because we might call sudo on the remote site and sudo requries a tty 
		#   Multiple -t options force tty allocation, even if ssh has no local tty.
		ssh -tt -p ${SSH_PORT} ${TARGET_USER}@${TARGET_HOST} "${REMOTE_EXEC_DIR}/cleanrollout_remote.sh" 
		trace clean_rollout_appserver "=========================================================="
		trace clean_rollout_appserver "Done with remote script cleanrollout_remote.sh"
	fi
	
	trace clean_rollout_appserver END
}


check_ssh()
{
	trace check_ssh BEGIN

	local host=$1
	local port=$2
	local user=$3
	
	trace check_ssh "Trying to log into TARGET_HOST ${host} port ${port} as user ${user}"
	
	#check if we can connect to the remote host as user adempiere	
	ssh -p ${port} ${user}@${host} 'echo ssh connect to TARGET_HOST ${host} port ${port} successfull'
	if [ "$?" != "0" ]  ; then
		trace check_ssh "Can't log into TARGET_HOST ${host} port ${port} as user ${user}, trying metas"
		ssh -p ${port} metas@${host} 'echo ssh connect to TARGET_HOST ${host} port ${port} successfull'
		if [ "$?" != "0" ]  ; then
		  trace check_ssh "Can't log into TARGET_HOST ${host} port ${port} as user metas either"
		  exit 2
		fi
	fi
	
	trace check_ssh END
}

prepare

if [ "$DATABASE" == "true" ]; then
	rollout_database
fi
if [ "$MINOR" == "true" ]; then
	rollout_minor
	rollout_sources
fi

clean_rollout_appserver

trace $(basename $0) "END"
