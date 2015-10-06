@Echo	Adempiere Full Database Export 	$Revision: 1.6 $

@Rem $Id: DBExportFull.bat,v 1.6 2005/04/27 17:45:01 jjanke Exp $

@Echo Saving database %1@%ADEMPIERE_DB_NAME% to %ADEMPIERE_HOME%\data\ExpDatFull.dmp

@if (%ADEMPIERE_HOME%) == () goto environment
@if (%ADEMPIERE_DB_NAME%) == () goto environment
@if (%ADEMPIERE_DB_SERVER%) == () goto environment
@if (%ADEMPIERE_DB_PORT%) == () goto environment
@Rem Must have parameter: systemAccount
@if (%1) == () goto usage


@sqlplus %1/%2@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% @%ADEMPIERE_HOME%\utils\%ADEMPIERE_DB_PATH%\Daily.sql


@exp %1/%2@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% FILE=%ADEMPIERE_HOME%\data\ExpDatFull.dmp Log=%ADEMPIERE_HOME%\data\ExpDatFull.log CONSISTENT=Y STATISTICS=NONE FULL=Y

@cd %ADEMPIERE_HOME%\data
@jar cvfM data\ExpDatFull.jar ExpDatFull.dmp  ExpDatFull.log

@goto end

:environment
@Echo Please make sure that the enviroment variables are set correctly:
@Echo		ADEMPIERE_HOME	e.g. D:\Adempiere
@Echo		ADEMPIERE_DB_NAME	e.g. dev1.adempiere.org

:usage
@echo Usage:	%0 <systemAccount>
@echo Examples:	%0 system/manager

:end
