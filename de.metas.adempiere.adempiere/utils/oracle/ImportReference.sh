#!/bin/sh

# $Id: ImportReference.sh,v 1.5 2005/01/22 21:59:15 jjanke Exp $
echo	Adempiere Reference Database Import 	$Revision: 1.5 $

echo	Importing Reference DB from $ADEMPIERE_HOME/data/Reference.dmp

if [ $# -eq 2 ] 
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


echo -------------------------------------
echo Re-Create new user
echo -------------------------------------
sqlplus $1@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME @$ADEMPIERE_HOME/utils/$ADEMPIERE_DB_PATH/CreateUser.sql Reference Adempiere

echo -------------------------------------
echo Import Reference
echo -------------------------------------
echo imp $1@$ADEMPIERE_DB_NAME FILE=$ADEMPIERE_HOME/data/Reference.dmp FROMUSER=\(reference\) TOUSER=reference
imp $1@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME FILE=$ADEMPIERE_HOME/data/Reference.dmp FROMUSER=\(reference\) TOUSER=reference

echo -------------------------------------
echo Check System
echo Import may show some warnings. This is OK as long as the following does not show errors
echo -------------------------------------
sqlplus reference/adempiere@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME @$ADEMPIERE_HOME/utils/$ADEMPIERE_DB_PATH/AfterImport.sql

