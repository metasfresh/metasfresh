#!/bin/sh

if [ $ADEMPIERE_HOME ]; then
	echo "Using environment variable ADEMPIERE_HOME = $ADEMPIERE_HOME"
else
	ADEMPIERE_HOME=$(dirname $0)                     # Get current dir
	echo "Assuming ADEMPIERE_HOME = $ADEMPIERE_HOME"
fi

if [ $LOG_DIR ]; then
	echo "Going to write our log files to LOG_DIR=$LOG_DIR"
else
	LOG_DIR=$(dirname $0)
	echo "Assuming LOG_DIR = $LOG_DIR"
fi

# Exit on Error of command immediately
set -e

JAR_FILE="$ADEMPIERE_HOME/lib/de.metas.endcustomer.mf15.swingui-1.0_IT-SNAPSHOT.jar"

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
#  SECURE=-DADEMPIERE_SECURE=org.compiere.util.Secure
SECURE=

# MaxPermSize is required when we run with java-7
MEMORY_OPTS="-Xms32m -Xmx1024m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError"

#Note that -Djava.util.Arrays.useLegacyMergeSort=true is related to task "07072 Comparison method violates its general contract (100965620270)"
JAVA_OPTS="${JAVA_OPTS} -Djava.util.Arrays.useLegacyMergeSort=true -DADEMPIERE_HOME=$ADEMPIERE_HOME $PROP $SECURE -classpath $CLASSPATH org.compiere.Adempiere"

echo "JAVA_OPTS = $JAVA_OPTS"
echo "JAR_FILE = $JAR_FILE"
echo "REMOTE_DEBUG_OPTS = $REMOTE_DEBUG_OPTS"
echo "PROP=$PROP"
echo "SECURE=$SECURE"

echo "================================"
echo "about to execute"
echo "$JAVA $JAVA_OPTS $REMOTE_DEBUG_OPTS -DADEMPIERE_HOME=$ADEMPIERE_HOME $PROP $SECURE -jar $JAR_FILE"
echo "================================"

$JAVA $MEMORY_OPTS $JAVA_OPTS $REMOTE_DEBUG_OPTS -DADEMPIERE_HOME=$ADEMPIERE_HOME -Dlogging.path=$LOG_DIR $PROP $SECURE -jar $JAR_FILE

