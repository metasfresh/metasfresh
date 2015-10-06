@Echo	Adempiere Reference Database Import 	$Revision: 1.4 $

@Rem $Id: ImportReference.bat,v 1.4 2005/01/22 21:59:15 jjanke Exp $

@Echo	Importing Reference DB from %ADEMPIERE_HOME%\data\Reference.dmp

@if (%ADEMPIERE_HOME%) == () goto environment
@if (%ADEMPIERE_DB_NAME%) == () goto environment
@if (%ADEMPIERE_DB_SERVER%) == () goto environment
@if (%ADEMPIERE_DB_PORT%) == () goto environment
@Rem Must have parameter: systemAccount
@if (%1) == () goto usage

@echo -------------------------------------
@echo Re-Create new user
@echo -------------------------------------
@sqlplus %1@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% @%ADEMPIERE_HOME%\Utils\%ADEMPIERE_DB_PATH%\CreateUser.sql Reference Adempiere

@echo -------------------------------------
@echo Import Reference
@echo -------------------------------------
imp %1@%ADEMPIERE_DB_NAME% FILE=%ADEMPIERE_HOME%\data\Reference.dmp FROMUSER=(reference) TOUSER=reference

@echo -------------------------------------
@echo Check System
@echo Import may show some warnings. This is OK as long as the following does not show errors
@echo -------------------------------------
@sqlplus reference/adempiere@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% @%ADEMPIERE_HOME%\Utils\%ADEMPIERE_DB_PATH%\AfterImport.sql

@goto end

:environment
@Echo Please make sure that the enviroment variables are set correctly:
@Echo		ADEMPIERE_HOME	e.g. D:\Adempiere
@Echo		ADEMPIERE_DB_NAME e.g. dev1.adempiere.org

:usage
@echo Usage:		%0 <systemAccount>
@echo Examples:	%0 system/manager

:end
