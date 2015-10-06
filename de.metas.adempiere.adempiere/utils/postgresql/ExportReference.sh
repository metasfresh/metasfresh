#!/bin/sh

echo	Adempiere PostgreSQL Database Export 	$Revision: 1.3 $

# $Id: DBExport.sh,v 1.3 2005/01/22 21:59:15 jjanke Exp $

echo Saving database reference@$ADEMPIERE_DB_NAME to $ADEMPIERE_HOME/data/Adempiere_pg.dmp

if [ "$ADEMPIERE_HOME" = "" -o  "$ADEMPIERE_DB_NAME" = "" -o "$ADEMPIERE_DB_SERVER" = "" -o "$ADEMPIERE_DB_PORT" = "" ]
  then
    echo "Please make sure that the environment variables are set correctly:"
    echo "	ADEMPIERE_HOME	e.g. /Adempiere"
    echo "	ADEMPIERE_DB_NAME	e.g. adempiere or xe"
    echo "  ADEMPIERE_DB_SERVER e.g. dbserver.adempiere.org"
    echo "  ADEMPIERE_DB_PORT e.g. 5432 or 1521"
    exit 1
fi

PGPASSWORD=reference
export PGPASSWORD
pg_dump -h $ADEMPIERE_DB_SERVER -p $ADEMPIERE_DB_PORT --no-owner -U reference $ADEMPIERE_DB_NAME > $ADEMPIERE_HOME/data/Adempiere_pg.dmp 
PGPASSWORD=
export PGPASSWORD

cd $ADEMPIERE_HOME/data
jar cvfM Adempiere_pg.jar Adempiere_pg.dmp
