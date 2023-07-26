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

#DEBUG_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=y"
DEBUG_OPTS=""

check_java_version

trace $(basename $0) "BEGIN"

check_std_tool java

trace $(basename $0) "Calling the java tool with params: $*"

java $DEBUG_OPTS -jar $(dirname $0)/lib/de.metas.migration.cli.jar $@

trace $(basename $0) "END"
