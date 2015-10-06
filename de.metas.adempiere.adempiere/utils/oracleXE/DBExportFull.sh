#!/bin/sh

echo	Adempiere Full Database Export 	$Revision: 1.3 $

# $Id: DBExportFull.sh,v 1.3 2005/01/22 21:59:15 jjanke Exp $

echo Saving database $1@$ADEMPIERE_DB_NAME to $ADEMPIERE_HOME/data/ExpDatFull.dmp

if [ $# -eq 0 ] 
  then
    echo "Usage:		$0 <systemAccount>"
    echo "Example:	$0 system/manager"
    exit 1
fi
if [ "$ADEMPIERE_HOME" = "" -o  "$ADEMPIERE_DB_NAME" = "" ]
  then
    echo "Please make sure that the environment variables are set correctly:"
    echo "	ADEMPIERE_HOME	e.g. /Adempiere"
    echo "	ADEMPIERE_DB_NAME	e.g. adempiere.adempiere.org"
    exit 1
fi

exp $1@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME FILE=$ADEMPIERE_HOME/data/ExpDatFull.dmp Log=$ADEMPIERE_HOME/data/ExpDatFull.log CONSISTENT=Y FULL=Y

cd $ADEMPIERE_HOME/data
jar cvfM ExpDatFull.jar ExpDatFull.dmp  ExpDatFull.log

