#!/bin/sh
if [ $ADEMPIERE_HOME ]; then
	echo "Using environment variable ADEMPIERE_HOME = $ADEMPIERE_HOME"
else
	ADEMPIERE_HOME=$(dirname $0)                     # Get current dir
	echo "Assuming ADEMPIERE_HOME = $ADEMPIERE_HOME"
fi

# Exit on Error of command immediately
set -e

#CLASSPATH=$ADEMPIERE_HOME/lib/de.metas.adempiere.adempiere.jbossfacet.jar:$ADEMPIERE_HOME/lib/jbossall-client.jar:$ADEMPIERE_HOME/lib/adempiereJasper-fonts.jar:$ADEMPIERE_HOME/lib/adempiereJasper-OCRA-font.jar:$ADEMPIERE_HOME/lib/adempiereJasper-OCRB-B10-font.jar:$ADEMPIERE_HOME/lib/de.metas.endcustomer.mf15.base-allInOne.jar:$CLASSPATH
CLASSPATH=$ADEMPIERE_HOME/lib/jp.osdn.ocra.jar:$ADEMPIERE_HOME/lib/jp.osdn.ocrb.jar:$ADEMPIERE_HOME/lib/de.metas.endcustomer.mf15.swingui-1.0_IT-SNAPSHOT.jar

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

# NOTE: line is commented out because we want to allow developers to specify their own "REMOTE_DEBUG_OPTS" before running Adempiere... so they can remote debug 
#REMOTE_DEBUG_OPTS=
#REMOTE_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8788"

# To switch between multiple installs, copy the created Adempiere.properties file
# Select the configuration by setting the PROP variable
#PROP=-DPropertyFile=test.properties
#Default:
#PROP=

#  To use your own Encryption class (implementing org.compiere.util.SecureInterface),
#  you need to set it here (and in the server start script) - example:
#  SECURE=-DADEMPIERE_SECURE=org.compiere.util.Secure
SECURE=

#Note that -Djava.util.Arrays.useLegacyMergeSort=true is related to task "07072 Comparison method violates its general contract (100965620270)"
$JAVA -Xms32m -Xmx1024m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError $REMOTE_DEBUG_OPTS -Djava.util.Arrays.useLegacyMergeSort=true -DADEMPIERE_HOME=$ADEMPIERE_HOME $PROP $SECURE -classpath $CLASSPATH org.compiere.Adempiere

