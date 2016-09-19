#!/bin/sh
#
# project specific script to stop the adempire/metasfresh service on a traget system suited for this particular project.
# The "implementation" can be by directly calling the OS infrastructure (like systemctl) from within this script, but it can also be delegating to another more host-specific script.
# The important thing is that our https://github.com/metasfresh/metasfresh-scripts/blob/master/infrastructure/cd/bootstrap_local.sh script can call it.
#

. ./tools.sh
stop_metasfresh

