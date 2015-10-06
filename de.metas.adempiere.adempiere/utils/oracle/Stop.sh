#!/bin/sh

# $Id: Stop.sh,v 1.7 2005/01/22 21:59:15 jjanke Exp $

# In a multi-instance environment set the environment first
# SET ORACLE_SID=
# export ORACLE_SID

sqlplus "system/$ADEMPIERE_DB_SYSTEM@$ADEMPIERE_DB_SERVER:$ADEMPIERE_DB_PORT/$ADEMPIERE_DB_NAME AS SYSDBA" @$ADEMPIERE_HOME/utils/$ADEMPIERE_DB_PATH/Stop.sql

