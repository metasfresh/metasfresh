#!/bin/sh
if [ $ADEMPIERE_HOME ]; then
	echo "Using environment variable ADEMPIERE_HOME = $ADEMPIERE_HOME"
else
	ADEMPIERE_HOME=$(dirname $0)                     # Get current dir
	echo "Assuming ADEMPIERE_HOME = $ADEMPIERE_HOME"
fi

# Exit on Error of command immediately
set -e

CLASSPATH=${runner.sh.classpath}

echo "CLASSPATH = $CLASSPATH"

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

JAVA_OPTS=${runner.java.options}
$JAVA $JAVA_OPTS $REMOTE_DEBUG_OPTS -DADEMPIERE_HOME=$ADEMPIERE_HOME $PROP $SECURE -classpath $CLASSPATH ${spring.jarLauncherClass}

