#!/bin/sh
#
# $Id: RUN_ImportReference.sh,v 1.11 2005/12/13 00:17:54 jjanke Exp $

if [ $ADEMPIERE_HOME ]; then
  cd $ADEMPIERE_HOME/utils
fi
. ./myEnvironment.sh Server
echo Export Reference - $ADEMPIERE_HOME \($ADEMPIERE_DB_NAME\)
sh $ADEMPIERE_DB_PATH/ExportReference.sh
