@Echo	Adempiere Database Restore 	$Revision: 1.6 $

@Rem $Id: DBRestore.bat,v 1.6 2005/08/27 02:27:10 jjanke Exp $

@Echo	Restoring Adempiere DB from %ADEMPIERE_HOME%\data\ExpDat.dmp

@if (%ADEMPIERE_HOME%) == () goto environment
@if (%ADEMPIERE_DB_NAME%) == () goto environment
@if (%ADEMPIERE_DB_SERVER%) == () goto environment
@if (%ADEMPIERE_DB_PORT%) == () goto environment
@Rem Must have parameter: systemAccount adempiereID AdempierePwd
@if (%1) == () goto usage
@if (%2) == () goto usage
@if (%3) == () goto usage

@echo -------------------------------------
@echo Re-Create DB user
@echo -------------------------------------
@sqlplus %1@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% @%ADEMPIERE_HOME%\utils\%ADEMPIERE_DB_PATH%\CreateUser.sql %2 %3

@echo -------------------------------------
@echo Import ExpDat
@echo -------------------------------------
@imp %1@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% FILE=%ADEMPIERE_HOME%\data\ExpDat.dmp FROMUSER=(%2) TOUSER=%2 STATISTICS=RECALCULATE

@echo -------------------------------------
@echo Create SQLJ 
@echo -------------------------------------
@call %ADEMPIERE_HOME%\Utils\%ADEMPIERE_DB_PATH%\create %ADEMPIERE_DB_USER%/%ADEMPIERE_DB_PASSWORD%

@echo -------------------------------------
@echo Check System
@echo Import may show some warnings. This is OK as long as the following does not show errors
@echo -------------------------------------
@sqlplus %2/%3@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% @%ADEMPIERE_HOME%\utils\%ADEMPIERE_DB_PATH%\AfterImport.sql

@goto end

:environment
@Echo Please make sure that the enviroment variables are set correctly:
@Echo		ADEMPIERE_HOME	e.g. D:\Adempiere
@Echo		ADEMPIERE_DB_NAME e.g. dev1.adempiere.org

:usage
@echo Usage:		%0% <systemAccount> <AdempiereID> <AdempierePwd>
@echo Example:	%0% system/manager adempiere adempiere

:end
