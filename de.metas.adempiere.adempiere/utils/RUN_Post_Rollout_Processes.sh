#!/bin/sh
#
# " 02230: Rollout - Automatisch 'role acess update' und 'sequence number check' ausf�hren (2011101310000042)"
# Based on script RUN_SignDatabaseBuild.sh
#
# author: t.schoeneberg@metas.de
#

echo RUNNING POST ROLLOUT PROCESSES


if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
. ./myEnvironment.sh Server

JAVA=$JAVA_HOME/bin/java

CP=$ADEMPIERE_HOME/lib/CInstall.jar:$ADEMPIERE_HOME/lib/Adempiere.jar:$ADEMPIERE_HOME/lib/CCTools.jar:$ADEMPIERE_HOME/lib/oracle.jar:$ADEMPIERE_HOME/lib/derby.jar:$ADEMPIERE_HOME/lib/fyracle.jar:$ADEMPIERE_HOME/lib/jboss.jar:$ADEMPIERE_HOME/lib/postgresql.jar:$ADEMPIERE_HOME/lib/CompiereJasperReqs.jar:

echo ===================================
echo Sign Database Build
echo ===================================
$JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME org.adempiere.process.SignDatabaseBuild

echo ===================================
echo Update Sequence Numbers
echo ===================================
$JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME org.compiere.process.SequenceCheck

echo ===================================
echo Update Role Access
echo ===================================
$JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME org.compiere.process.RoleAccessUpdate

echo ===================================
echo Executing database after migration
echo ===================================
$JAVA -classpath $CP -DADEMPIERE_HOME=$ADEMPIERE_HOME org.adempiere.db.util.AfterMigration
