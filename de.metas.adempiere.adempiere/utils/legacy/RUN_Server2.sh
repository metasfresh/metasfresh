#!/bin/sh
# ADempiere Server Start

if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi

. ./myEnvironment.sh Server

# To use your own Encryption class (implementing org.compiere.util.SecureInterface),
# you need to set it here (and in the client start script) - example:
# SECURE=-DADEMPIERE_SECURE=org.compiere.util.Secure
SECURE=

# headless option if you don't have X installed on the server
JAVA_OPTS="-server $ADEMPIERE_JAVA_OPTIONS $SECURE -Djava.awt.headless=true -Dorg.adempiere.server.embedded=true"

export JAVA_OPTS

$JBOSS_HOME/bin/run.sh -c adempiere -b $ADEMPIERE_APPS_SERVER
