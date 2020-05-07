#!/bin/bash
#
# project specific script to start the adempire/metasfresh service on a target system suited for this particular project.
# The "implementation" can be by directly calling the OS infrastructure (like systemctl) from within this script, but it can also be delegating to another more host-specific script.
# The important thing is that our https://github.com/metasfresh/metasfresh-scripts/blob/master/infrastructure/cd/bootstrap_local.sh script can call it.
#

# IMPORTANT: tools.sh requires bash, so if you source it here, make sure that this script also uses bash
TOOLS=$(dirname $0)/tools.sh
if [ -f $TOOLS ] && [ -r $TOOLS ]; then
	source $TOOLS
else
	echo "Missing file ${TOOLS}"
	exit 1
fi

start_metasfresh
