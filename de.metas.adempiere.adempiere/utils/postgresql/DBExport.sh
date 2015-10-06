#!/bin/sh

echo	Adempiere PostgreSQL Database Export 	$Revision: 1.3 $

# $Id: DBExport.sh,v 1.3 2005/01/22 21:59:15 jjanke Exp $

echo Saving database $1@$ADEMPIERE_DB_NAME to $ADEMPIERE_HOME/data/ExpDat.dmp

if [ $# -eq 0 ] 
  then
    echo "Usage:		$0 <userAccount>"
    echo "Example:	$0 adempiere adempiere"
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

PGPASSWORD=$2
export PGPASSWORD
pg_dump -h $ADEMPIERE_DB_SERVER -p $ADEMPIERE_DB_PORT --no-owner -U $1 $ADEMPIERE_DB_NAME > $ADEMPIERE_HOME/data/ExpDat.dmp 
PGPASSWORD=
export PGPASSWORD

cd $ADEMPIERE_HOME/data
jar cvfM ExpDat.jar ExpDat.dmp
