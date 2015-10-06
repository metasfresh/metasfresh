#!/bin/sh
#
# myEnvironment defines the variables used for Adempiere
# Do not edit directly - use RUN_setup
#
# $Id: myEnvironmentTemplate.sh,v 1.12 2005/02/21 03:17:21 jjanke Exp $

echo Setting myEnvironment ....
#	Clients only needs
#		ADEMPIERE_HOME
#		JAVA_HOME 
#	Server install needs to change
#		ADEMPIERE_DB_NAME	(for Oracle)
#		passwords
#
#	For a HTML browser, Adempiere will call "netscape <targetURL>"
#	If not in the path, provide a link called netscape to your browser

# 	Homes ...
ADEMPIERE_HOME=@ADEMPIERE_HOME@
export ADEMPIERE_HOME
JAVA_HOME=@JAVA_HOME@
export JAVA_HOME

#	Database ...
ADEMPIERE_DB_SERVER=@ADEMPIERE_DB_SERVER@
export ADEMPIERE_DB_SERVER
ADEMPIERE_DB_USER=@ADEMPIERE_DB_USER@
export ADEMPIERE_DB_USER
ADEMPIERE_DB_PASSWORD=@ADEMPIERE_DB_PASSWORD@
export ADEMPIERE_DB_PASSWORD
ADEMPIERE_DB_URL=@ADEMPIERE_DB_URL@
export ADEMPIERE_DB_URL
ADEMPIERE_DB_PORT=@ADEMPIERE_DB_PORT@
export ADEMPIERE_DB_PORT

#	Oracle Specifics ...
ADEMPIERE_DB_PATH=@ADEMPIERE_DB_PATH@
export ADEMPIERE_DB_PATH
ADEMPIERE_DB_NAME=@ADEMPIERE_DB_NAME@
export ADEMPIERE_DB_NAME
ADEMPIERE_DB_SYSTEM=@ADEMPIERE_DB_SYSTEM@
export ADEMPIERE_DB_SYSTEM

#	Homes(2)
ADEMPIERE_DB_HOME=$ADEMPIERE_HOME/utils/$ADEMPIERE_DB_PATH
export ADEMPIERE_DB_HOME
JBOSS_HOME=$ADEMPIERE_HOME/jboss
export JBOSS_HOME

#	Apps Server
ADEMPIERE_APPS_SERVER=@ADEMPIERE_APPS_SERVER@
export ADEMPIERE_APPS_SERVER
ADEMPIERE_WEB_PORT=@ADEMPIERE_WEB_PORT@
export ADEMPIERE_WEB_PORT
ADEMPIERE_JNP_PORT=@ADEMPIERE_JNP_PORT@
export ADEMPIERE_JNP_PORT
#	SSL Settings - see jboss/server/adempiere/deploy/jbossweb.sar/META-INF/jboss-service.xml
ADEMPIERE_SSL_PORT=@ADEMPIERE_SSL_PORT@
export ADEMPIERE_SSL_PORT
ADEMPIERE_KEYSTORE=@ADEMPIERE_KEYSTORE@
export ADEMPIERE_KEYSTORE
ADEMPIERE_KEYSTOREPASS=@ADEMPIERE_KEYSTOREPASS@
export ADEMPIERE_KEYSTOREPASS

#	etc.
ADEMPIERE_FTP_SERVER=@ADEMPIERE_FTP_SERVER@
export ADEMPIERE_FTP_SERVER

#	Java
ADEMPIERE_JAVA=$JAVA_HOME/bin/java
export ADEMPIERE_JAVA
ADEMPIERE_JAVA_OPTIONS="@ADEMPIERE_JAVA_OPTIONS@ -DADEMPIERE_HOME=$ADEMPIERE_HOME"

# 02820: Enable remote jmx support for adempiere server (2012052910000021)
#Adding further parameters to allow JMX remote connections
ADEMPIERE_JAVA_OPTIONS="$ADEMPIERE_JAVA_OPTIONS -Dcom.sun.management.jmxremote.port=11099"
ADEMPIERE_JAVA_OPTIONS="$ADEMPIERE_JAVA_OPTIONS -Dcom.sun.management.jmxremote.authenticate=false"
ADEMPIERE_JAVA_OPTIONS="$ADEMPIERE_JAVA_OPTIONS -Dcom.sun.management.jmxremote.ssl=false"

export ADEMPIERE_JAVA_OPTIONS
CLASSPATH="$ADEMPIERE_HOME/lib/Adempiere.jar:$ADEMPIERE_HOME/lib/AdempiereCLib.jar"
export CLASSPATH

if [ $DOLLAR$# -eq 0 ] 
  then
    cp myEnvironment.sh myEnvironment.sav
fi
