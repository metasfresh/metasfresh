@Rem $Id: Start.bat,v 1.7 2005/01/22 21:59:15 jjanke Exp $

@Echo Starting Listener ....
lsnrctl start

@Echo Starting Database ....
@sqlplus "system/%ADEMPIERE_DB_SYSTEM%@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% AS SYSDBA" @%ADEMPIERE_HOME%\utils\%ADEMPIERE_DB_PATH%\Start.sql

@Echo Starting optional agent ....
agentctl start

@Echo ------------------------
lsnrctl status
