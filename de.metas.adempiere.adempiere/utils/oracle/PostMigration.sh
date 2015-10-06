#!/bin/sh

# $Id: PostMigration.sh
echo	Oracle Post Migration Scripts

if [ $# -le 2 ] 
  then
    echo "Usage:		$0 <systemAccount> <AdempiereID> <AdempierePWD>"
    echo "Example:	$0 system/manager adempiere adempiere"
    exit 1
fi
if [ "$ADEMPIERE_HOME" = "" -o  "$ADEMPIERE_DB_NAME" = "" ]
  then
    echo "Please make sure that the environment variables are set correctly:"
    echo "	ADEMPIERE_HOME	e.g. /Adempiere"
    echo "	ADEMPIERE_DB_NAME	e.g. adempiere.adempiere.org"
    exit 1
fi

echo -------------------------------------
echo Add missing translations
echo -------------------------------------
echo sqlplus $2/$3@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME @$ADEMPIERE_HOME/utils/$ADEMPIERE_DB_PATH/01_add_missing_Translations.sql
