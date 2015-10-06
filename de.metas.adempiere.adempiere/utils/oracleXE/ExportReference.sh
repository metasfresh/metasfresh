#!/bin/sh

echo	Adempiere Database Export 	$Revision: 1.5 $

# $Id: DBExport.sh,v 1.5 2005/12/20 07:12:17 jjanke Exp $

echo Saving reference database reference@$ADEMPIERE_DB_NAME to $ADEMPIERE_HOME/data/Adempiere.dmp

if [ "$ADEMPIERE_HOME" = "" -o  "$ADEMPIERE_DB_NAME" = "" ]
  then
    echo "Please make sure that the environment variables are set correctly:"
    echo "	ADEMPIERE_HOME	e.g. /Adempiere"
    echo "	ADEMPIERE_DB_NAME	e.g. adempiere.adempiere.org"
    exit 1
fi

# Export
exp reference/reference@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME FILE=$ADEMPIERE_HOME/data/Adempiere.dmp Log=$ADEMPIERE_HOME/data/Adempiere.log CONSISTENT=Y OWNER=reference 

cd $ADEMPIERE_HOME/data
jar cvfM Adempiere.jar Adempiere.dmp Adempiere.log
