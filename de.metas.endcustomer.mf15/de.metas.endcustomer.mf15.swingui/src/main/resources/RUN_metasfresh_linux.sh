#!/bin/sh

# Uncomment this option to disable the client from comparing its own version with the version stored in the AD_System database table
#METASFRESH_CLIENT_CHECK_OPTS="-Dde.metas.clientcheck.Enabled=false"

if [ $METASFRESH_HOME ]; then
	echo "Using environment variable METASFRESH_HOME = $METASFRESH_HOME"
elif [ $ADEMPIERE_HOME ]; then
	METASFRESH_HOME=$ADEMPIERE_HOME
	echo "Using environment variable ADEMPIERE_HOME = $ADEMPIERE_HOME also for METASFRESH_HOME"
else
	METASFRESH_HOME=$(dirname $0)                     # Get current dir
	echo "Assuming METASFRESH_HOME = $METASFRESH_HOME"
fi

if [ $LOG_DIR ]; then
	echo "Going to write our log files to LOG_DIR=$LOG_DIR"
else
	LOG_DIR=$(dirname $0)
	echo "Assuming LOG_DIR = $LOG_DIR"
fi

# Exit on Error of command immediately
set -e

#get the path of our executable jar file. Note the wildcards, they are here mostly because we like this to also work with feature builds.
JAR_FILE=$(ls $METASFRESH_HOME/lib/de.metas.endcustomer.*.swingui-*.jar)

##	Check Java Home
if [ $JAVA_HOME ]; then
  JAVA=$JAVA_HOME/bin/java
else
  JAVA=java
  echo JAVA_HOME is not set.
  echo   You may not be able to start metasfresh
  echo   Set JAVA_HOME to the directory of your local JDK.
fi

# NOTE: line is commented out because we want to allow developers to specify their own "REMOTE_DEBUG_OPTS" before running metasfresh... so they can remote debug 
#REMOTE_DEBUG_OPTS=
#REMOTE_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8788"

# To switch between multiple installs, copy the created metasfresh.properties file
# Select the configuration by setting the PROP variable
#PROP=-DPropertyFile=test.properties
#Default:
#PROP=

#  To use your own Encryption class (implementing org.compiere.util.SecureInterface),
#  you need to set it here (and in the server start script) - example:
#  SECURE=-DMETASFRESH_SECURE=org.compiere.util.Secure
SECURE=

MEMORY_OPTS="-Xms32m -Xmx1024m -XX:+HeapDumpOnOutOfMemoryError"

# -Djava.util.Arrays.useLegacyMergeSort=true is related to task "07072 Comparison method violates its general contract (100965620270)"
# -Dsun.java2d.xrender=false is related to http://stackoverflow.com/questions/34188495/how-can-i-work-around-the-classcastexception-in-java2d-bug-id-7172749 . The problem happens in some X environments
JAVA_OPTS="${JAVA_OPTS} -Djava.util.Arrays.useLegacyMergeSort=true -Dsun.java2d.xrender=false -DMETASFRESH_HOME=$METASFRESH_HOME $PROP $SECURE -classpath $CLASSPATH org.compiere.Adempiere"

echo "JAVA_OPTS = $JAVA_OPTS"
echo "JAR_FILE = $JAR_FILE"
echo "REMOTE_DEBUG_OPTS = $REMOTE_DEBUG_OPTS"
echo "PROP=$PROP"
echo "SECURE=$SECURE"

echo "================================"
echo "about to execute"
echo "$JAVA $JAVA_OPTS $REMOTE_DEBUG_OPTS -DMETASFRESH_HOME=$METASFRESH_HOME $PROP $SECURE -jar $JAR_FILE"
echo "================================"

$JAVA $MEMORY_OPTS $JAVA_OPTS $REMOTE_DEBUG_OPTS -DMETASFRESH_HOME=$METASFRESH_HOME $METASFRESH_CLIENT_CHECK_OPTS -Dlogging.path=$LOG_DIR $PROP $SECURE -jar $JAR_FILE

