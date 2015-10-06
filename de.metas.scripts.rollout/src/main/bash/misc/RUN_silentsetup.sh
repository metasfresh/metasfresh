#!/bin/bash
#

set -o errexit  #don't allow any command to exit with an error code !=0

echo Install Adempiere Server
# $Header: /cvsroot/adempiere/install/Adempiere/RUN_setup.sh,v 1.19 2005/09/08 21:54:12 jjanke Exp $

if [ $JAVA_HOME ]; then
  JAVA=$JAVA_HOME/bin/java
  KEYTOOL=$JAVA_HOME/bin/keytool
else
  JAVA=java
  KEYTOOL=keytool
  echo JAVA_HOME is not set.
  echo You may not be able to start the Setup
  echo Set JAVA_HOME to the directory of your local JDK.
fi


echo ===================================
echo Starting Setup ...
echo ===================================
CP=lib/CInstall.jar:lib/Adempiere.jar:lib/CCTools.jar:lib/oracle.jar:lib/derby.jar:lib/fyracle.jar:lib/jboss.jar:lib/postgresql.jar:

# Trace Level Parameter, e.g. ARGS=ALL
ARGS=CONFIG

$JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME org.compiere.install.SilentSetup $ARGS

echo ===================================
echo Make .sh executable and set Env
echo ===================================
chmod -Rv a+x *.sh
find . -name '*.sh' -exec chmod -v a+x '{}' \;

#task 04567: make the wrapper scripts and binary also executable
chmod -v a+x ./jboss/bin/wrapper
chmod -v a+x ./jboss/bin/adempiere_wrapper
chmod -v a+x ./utils/adempiere_server

cd utils

#  02230: Rollout - Automatisch 'role acess update' und 'sequence number check' ausfuehren (2011101310000042)
. ./RUN_Post_Rollout_Processes.sh 

. ./RUN_UnixEnv.sh

echo .
echo For problems, check log file in base directory
