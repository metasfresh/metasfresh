#!/bin/sh
# Transfer Adempiere Database

# $Id: RUN_PutExportTemplate.sh,v 1.3 2005/01/22 21:59:15 jjanke Exp $
if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
. ./myEnvironment.sh Server
echo Transfer Adempiere Database - $ADEMPIERE_HOME \($ADEMPIERE_DB_NAME\)


Echo ........ Export DB
sh $ADEMPIERE_DB_PATH/DBExport.sh $ADEMPIERE_DB_USER/$ADEMPIERE_DB_PASSWORD

Echo ........ Stop DB
sqlplus "system/$ADEMPIERE_DB_SYSTEM AS SYSDBA" @$ADEMPIERE_HOME/utils/$ADEMPIERE_DB_PATH/Stop.sql

echo ........ FTP
ping @ADEMPIERE_FTP_SERVER@
cd $ADEMPIERE_HOME/data
ls ExpDat.*

ftp -s:$ADEMPIERE_HOME/utils/ftpPutExport.txt

echo ........ Done
