#!/bin/sh

echo	Adempiere Database Export 	$Revision: 1.5 $

# $Id: DBExport.sh,v 1.5 2005/12/20 07:12:17 jjanke Exp $

echo Saving database $1@$ADEMPIERE_DB_NAME to $ADEMPIERE_HOME/data/ExpDat.dmp

if [ $# -eq 0 ] 
  then
    echo "Usage:		$0 <userAccount>"
    echo "Example:	$0 adempiere adempiere"
    exit 1
fi
if [ "$ADEMPIERE_HOME" = "" -o  "$ADEMPIERE_DB_NAME" = "" ]
  then
    echo "Please make sure that the environment variables are set correctly:"
    echo "	ADEMPIERE_HOME	e.g. /Adempiere"
    echo "	ADEMPIERE_DB_NAME	e.g. adempiere.adempiere.org"
    exit 1
fi

# Cleanup
sqlplus $1/$2@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME @$ADEMPIERE_HOME/utils/$ADEMPIERE_DB_PATH/Daily.sql

# Export
exp $1/$2@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME FILE=$ADEMPIERE_HOME/data/ExpDat.dmp Log=$ADEMPIERE_HOME/data/ExpDat.log CONSISTENT=Y OWNER=$1 

cd $ADEMPIERE_HOME/data
jar cvfM ExpDat.jar ExpDat.dmp ExpDat.log
