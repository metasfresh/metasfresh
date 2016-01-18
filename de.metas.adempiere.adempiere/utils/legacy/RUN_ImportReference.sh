#!/bin/sh
#
# $Id: RUN_ImportReference.sh,v 1.11 2005/12/13 00:17:54 jjanke Exp $

if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
. ./myEnvironment.sh Server
echo Import Reference - $ADEMPIERE_HOME \($ADEMPIERE_DB_NAME\)

SUFFIX=""
SYSUSER=system
if [ $ADEMPIERE_DB_PATH = "postgresql" ]
then
   SUFFIX="_pg"
   SYSUSER=postgres
fi

echo Re-Create Reference User and import $ADEMPIERE_HOME/data/Adempiere.dmp - \($ADEMPIERE_DB_NAME\)
echo == The import will show warnings. This is OK ==
ls -lsa $ADEMPIERE_HOME/data/Adempiere${SUFFIX}.dmp
echo Press enter to continue ...
read in

# Parameter: <systemAccount> <AdempiereID> <AdempierePwd>
sh $ADEMPIERE_DB_PATH/ImportAdempiere.sh $SYSUSER/$ADEMPIERE_DB_SYSTEM reference reference $ADEMPIERE_DB_SYSTEM
