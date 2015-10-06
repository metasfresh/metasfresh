#!/bin/sh
#
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
echo Setup Dialog
echo ===================================
CP=lib/CInstall.jar:lib/Adempiere.jar:lib/CCTools.jar:lib/oracle.jar:lib/derby.jar:lib/fyracle.jar:lib/jboss.jar:lib/postgresql.jar:

# Trace Level Parameter, e.g. ARGS=ALL
ARGS=CONFIG

# To test the OCI driver, add -DTestOCI=Y to the command - example:
# $JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME -DTestOCI=Y org.compiere.install.Setup $ARGS

$JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME org.compiere.install.Setup $ARGS

#echo ===================================
#echo Setup Adempiere Server Environment
#echo ===================================
#$JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME -Dant.home="." org.apache.tools.ant.launch.Launcher setup

echo ===================================
echo Make .sh executable and set Env
echo ===================================
chmod -Rv a+x *.sh
find . -name '*.sh' -exec chmod -v a+x '{}' \;

#task 04567: make the wrapper scripts and binary also executable
chmod -v a+x ./jboss/bin/wrapper
chmod -v a+x ./jboss/bin/adempiere_wrapper
chmod -v a+x ./utils/adempiere_server

# Sign database build
cd utils
. ./RUN_SignDatabaseBuild.sh 

. ./RUN_UnixEnv.sh

#echo ================================
#echo	Test local Connection
#echo ================================
#%JAVA% -classpath lib/Adempiere.jar:lib/AdempiereCLib.jar org.compiere.install.ConnectTest localhost

echo .
echo For problems, check log file in base directory
