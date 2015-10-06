#!/bin/sh
#
# $Id: RUN_ImportAdempiere.sh,v 1.9 2005/01/22 21:59:15 jjanke Exp $

if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
. ./myEnvironment.sh Server
echo Import Adempiere - $ADEMPIERE_HOME \($ADEMPIERE_DB_NAME\)

SUFFIX=""
SYSUSER=system
if [ $ADEMPIERE_DB_PATH = "postgresql" ]
then
    SUFFIX="_pg"
    SYSUSER=postgres
fi

echo Re-Create Adempiere User and import $ADEMPIERE_HOME/data/Adempiere${SUFFIX}.dmp - \($ADEMPIERE_DB_NAME\)
echo == The import will show warnings. This is OK ==
ls -lsa $ADEMPIERE_HOME/data/Adempiere${SUFFIX}.dmp
echo Press enter to continue ...
read in

# Parameter: <systemAccount> <AdempiereID> <AdempierePwd>
# globalqss - cruiz - 2007-10-09 - added fourth parameter for postgres(ignored in oracle)
$ADEMPIERE_DB_PATH/ImportAdempiere.sh $SYSUSER/$ADEMPIERE_DB_SYSTEM $ADEMPIERE_DB_USER $ADEMPIERE_DB_PASSWORD $ADEMPIERE_DB_SYSTEM

$ADEMPIERE_HOME/utils/RUN_SignDatabaseBuild.sh 
