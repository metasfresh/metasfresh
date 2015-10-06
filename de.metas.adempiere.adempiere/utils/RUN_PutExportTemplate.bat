@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Export Database ExpDat.jar - %ADEMPIERE_HOME%

@Rem $Id: RUN_PutExportTemplate.bat,v 1.3 2002/10/22 14:56:40 jjanke Exp $

@Echo ........ Export DB
@call %ADEMPIERE_DB_PATH%\DBExport %ADEMPIERE_DB_USER%/%ADEMPIERE_DB_PASSWORD%

@Rem 	Echo ........ Stop DB
@Rem	sqlplus "system/%ADEMPIERE_DB_SYSTEM% AS SYSDBA" @%ADEMPIERE_HOME%\utils\%ADEMPIERE_BP_PATH%\Stop.sql

@Title Transafer Database ExpDat.jar - %ADEMPIERE_HOME%\data
@Echo Transfer Database ExpDat.jar - %ADEMPIERE_HOME%\data

@Echo ........ FTP
@ping @ADEMPIERE_FTP_SERVER@
@cd %ADEMPIERE_HOME%\data
@dir ExpDat.*

@ftp -s:%ADEMPIERE_HOME%\utils\ftpPutExport.txt

@Echo ........ Done
@pause
