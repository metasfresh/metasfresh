#!/bin/bash

set -o nounset  #don't allow access to unset variables
set -o errexit  #don't allow any command to exit with an error code !=0

#
# Das hier koennte helfen, wenn "set -o errexit" in subshells nicht funktioniert
#
set -o posix

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


trace $(basename $0) "BEGIN"

delete_rollout

trace $(basename $0) "END"