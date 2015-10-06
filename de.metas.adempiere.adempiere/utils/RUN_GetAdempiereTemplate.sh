#!/bin/sh
# Download Adempiere
#
# $Id: RUN_GetAdempiereTemplate.sh,v 1.3 2005/01/22 21:59:15 jjanke Exp $

if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
. ./myEnvironment.sh Server
echo Download Adempiere Database - $ADEMPIERE_HOME \($ADEMPIERE_DB_NAME\)

echo Download Adempiere Database as jar into $ADEMPIERE_HOME/data

ping @ADEMPIERE_FTP_SERVER@
cd $ADEMPIERE_HOME/data
rm Adempiere.jar

ftp -s:$ADEMPIERE_HOME/utils/ftpGetAdempiere.txt

echo Unpacking 
jar xvf Adempiere.jar

echo ........ Received

cd $ADEMPIERE_HOME/utils
sh RUN_ImportAdempiere.sh
