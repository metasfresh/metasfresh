#!/bin/sh
if [ $ADEMPIERE_HOME ]; then
	echo "Using environment variable ADEMPIERE_HOME = $ADEMPIERE_HOME"
else
	ADEMPIERE_HOME=$(dirname $0)                     # Get current dir
	echo "Assuming ADEMPIERE_HOME = $ADEMPIERE_HOME"
fi

# Exit on Error of command immediately
set -e

CLASSPATH=$ADEMPIERE_HOME/lib/Adempiere.jar:$ADEMPIERE_HOME/lib/AdempiereCLib.jar:$ADEMPIERE_HOME/lib/CompiereJasperReqs.jar:$CLASSPATH
echo "CLASSPATH = $CLASSPATH"

##	Check Java Home
if [ $JAVA_HOME ]; then
  JAVA=$JAVA_HOME/bin/java
else
  JAVA=java
  echo JAVA_HOME is not set.
  echo   You may not be able to start Adempiere
  echo   Set JAVA_HOME to the directory of your local JDK.
fi

# To switch between multiple installs, copy the created Adempiere.properties file
# Select the configuration by setting the PROP variable
#PROP=-DPropertyFile=test.properties
#Default:
#PROP=

#  To use your own Encryption class (implementing org.compiere.util.SecureInterface),
#  you need to set it here (and in the server start script) - example:
#  SECURE=-DADEMPIERE_SECURE=org.compiere.util.Secure
SECURE=

$JAVA -Xms32m -Xmx512m -DADEMPIERE_HOME=$ADEMPIERE_HOME $PROP $SECURE -classpath $CLASSPATH org.compiere.Adempiere

