@Rem $Id: Stop.bat,v 1.7 2005/01/22 21:59:15 jjanke Exp $

@Echo Stopping database ....
@sqlplus "system/%ADEMPIERE_DB_SYSTEM%@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% AS SYSDBA" @%ADEMPIERE_HOME%\utils\%ADEMPIERE_DB_PATH%\Stop.sql

@Echo Stopping Listener ....
lsnrctl stop

@Echo Stopping (optional) Agent ....
agentctl stop

