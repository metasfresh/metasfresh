@Echo	Adempiere Database Import		$Revision: 1.9 $

@Rem $Id: ImportAdempiere.bat,v 1.9 2005/09/24 01:50:41 jjanke Exp $

@Echo	Importing Adempiere DB from %ADEMPIERE_HOME%\data\Adempiere.dmp (%ADEMPIERE_DB_NAME%)

@if (%ADEMPIERE_HOME%) == () goto environment
@if (%ADEMPIERE_DB_NAME%) == () goto environment
@if (%ADEMPIERE_DB_SERVER%) == () goto environment
@if (%ADEMPIERE_DB_PORT%) == () goto environment
@Rem Must have parameters systemAccount AdempiereID AdempierePwd
@if (%1) == () goto usage
@if (%2) == () goto usage
@if (%3) == () goto usage

@echo -------------------------------------
@echo Re-Create DB user
@echo -------------------------------------
@sqlplus %1@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% @%ADEMPIERE_HOME%\Utils\%ADEMPIERE_DB_PATH%\CreateUser.sql %2 %3

@echo -------------------------------------
@echo Import Adempiere.dmp
@echo -------------------------------------
@imp %1@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% FILE=%ADEMPIERE_HOME%\data\Adempiere.dmp FROMUSER=(reference) TOUSER=%2 STATISTICS=RECALCULATE

REM echo -------------------------------------
REM echo Create SQLJ 
REM echo -------------------------------------
REM call %ADEMPIERE_HOME%\Utils\%ADEMPIERE_DB_PATH%\create %ADEMPIERE_DB_USER%/%ADEMPIERE_DB_PASSWORD%

@echo --------========--------========--------========--------
@echo System Check - The Import phase showed warnings. 
@echo This is OK as long as the following does not show errors
@echo --------========--------========--------========--------
@sqlplus %2/%3@%ADEMPIERE_DB_SERVER%:%ADEMPIERE_DB_PORT%/%ADEMPIERE_DB_NAME% @%ADEMPIERE_HOME%\Utils\%ADEMPIERE_DB_PATH%\AfterImport.sql

@goto end

:environment
@Echo Please make sure that the enviroment variables are set correctly:
@Echo		ADEMPIERE_HOME	e.g. D:\Adempiere
@Echo		ADEMPIERE_DB_NAME	e.g. dev1.adempiere.org

:usage
@echo Usage:		%0 <systemAccount> <AdempiereID> <AdempierePwd>
@echo Example:	%0 system/manager Adempiere Adempiere

:end
