#!/bin/sh
#
# $Id: RUN_PostMigration.sh

if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
. ./myEnvironment.sh Server

#check java home
if [ $JAVA_HOME ]; then
  export PATH=$JAVA_HOME/bin:$PATH	
else
  echo JAVA_HOME is not set.
  echo You may not be able to build Adempiere
  echo Set JAVA_HOME to the directory of your local JDK.
  exit
fi

# check jdk
if  [ ! -f $JAVA_HOME/lib/tools.jar ] ; then
   echo "** Need full Java SDK **"
   exit
fi

SUFFIX=""
SYSUSER=system
if [ $ADEMPIERE_DB_PATH = "postgresql" ]
then
    SUFFIX="_pg"
    SYSUSER=postgres
fi

#classpath
MYCLASSPATH=../lib/Adempiere.jar:../lib/CCTools.jar:../lib/postgresql.jar:../lib/oracle.jar:../lib/jboss.jar

JAVA_OPTS="-Xms128m -Xmx512m -Djava.awt.headless=true"

ADEMPIERE_OPTS="-DADEMPIERE_HOME=$ADEMPIERE_HOME -DPropertyFile=../Adempiere.properties"

#Run generate model
echo
echo Generate Model
echo

$JAVA_HOME/bin/java $JAVA_OPTS $ADEMPIERE_OPTS -classpath $MYCLASSPATH org.adempiere.util.GenerateModel src/org/compiere/model org.compiere.model 'D' '%' 

#Add missing translations
echo
echo Add missing translations
echo

# Parameter: <systemAccount> <AdempiereID> <AdempierePwd> <SystemPwd>
sh $ADEMPIERE_DB_PATH/PostMigration.sh $SYSUSER/$ADEMPIERE_DB_SYSTEM $ADEMPIERE_DB_USER $ADEMPIERE_DB_PASSWORD $ADEMPIERE_DB_SYSTEM

#Run Synchronize Terminology
echo
echo Synchronize Terminology
echo

$JAVA_HOME/bin/java $JAVA_OPTS $ADEMPIERE_OPTS -classpath $MYCLASSPATH org.compiere.process.SynchronizeTerminology

#Run Role access update
echo
echo Role Access Update
echo

$JAVA_HOME/bin/java $JAVA_OPTS $ADEMPIERE_OPTS -classpath $MYCLASSPATH org.compiere.process.RoleAccessUpdate

#Run Sequence Check
echo
echo Sequence Check
echo

$JAVA_HOME/bin/java $JAVA_OPTS $ADEMPIERE_OPTS -classpath $MYCLASSPATH org.compiere.process.SequenceCheck

echo Done ...

exit 0

