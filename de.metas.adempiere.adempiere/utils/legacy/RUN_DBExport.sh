#!/bin/sh

# Author + Copyright 1999-2005 Jorg Janke
# $Id: RUN_DBExport.sh,v 1.10 2005/05/31 18:45:33 jjanke Exp $
if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
. ./myEnvironment.sh Server
echo 	Export Adempiere Database - $ADEMPIERE_HOME \($ADEMPIERE_DB_NAME\)


# Parameter: <adempiereDBuser>/<adempiereDBpassword>
sh $ADEMPIERE_DB_PATH/DBExport.sh $ADEMPIERE_DB_USER $ADEMPIERE_DB_PASSWORD

# sh $ADEMPIERE_DB_PATH/DBExportFull system $ADEMPIERE_DB_SYSTEM

if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
sh myDBcopy.sh

