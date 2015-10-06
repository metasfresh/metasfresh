#!/bin/sh

# $Id: PostMigration.sh
echo	Postgresql Post Migration Scripts

if [ $# -le 2 ] 
  then
    echo "Usage:		$0 <systemAccount> <AdempiereID> <AdempierePWD> <PostgresPwd>"
    echo "Example:	$0 postgres adempiere adempiere postgresPwd"
    exit 1
fi
if [ "$ADEMPIERE_HOME" = "" -o  "$ADEMPIERE_DB_NAME" = "" -o "$ADEMPIERE_DB_SERVER" = "" -o "$ADEMPIERE_DB_PORT" = "" ]
  then
    echo "Please make sure that the environment variables are set correctly:"
    echo "	ADEMPIERE_HOME	e.g. /Adempiere"
    echo "	ADEMPIERE_DB_NAME	e.g. adempiere or xe"
    echo "  ADEMPIERE_DB_SERVER e.g. dbserver.adempiere.org"
    echo "  ADEMPIERE_DB_PORT e.g. 5432 or 1521"
    exit 1
fi

PGPASSWORD=$3
export PGPASSWORD
echo -------------------------------------
echo Add missing translations
echo -------------------------------------
psql -h $ADEMPIERE_DB_SERVER -p $ADEMPIERE_DB_PORT -d $ADEMPIERE_DB_NAME -U $2 -f 01_add_missing_translations.sql
PGPASSWORD=
export PGPASSWORD
