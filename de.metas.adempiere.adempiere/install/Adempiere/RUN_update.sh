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

#echo ===================================
#echo Update Adempiere Server Environment
#echo ===================================
CP=lib/CInstall.jar:lib/Adempiere.jar:lib/CCTools.jar:lib/oracle.jar:lib/derby.jar:lib/fyracle.jar:lib/jboss.jar:lib/postgresql.jar:
$JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME -Dant.home="." org.apache.tools.ant.launch.Launcher update
