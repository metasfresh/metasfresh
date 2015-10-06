@Echo	ADEMPIERE Database Export 	$Revision: 1.2 $

@Rem $Id: DBExport.bat,v 1.2 2005/01/22 21:59:15 jjanke Exp $

@Echo Saving database %1@%ADEMPIERE_DB_NAME% to %ADEMPIERE_HOME%\data\ExpDat.dmp

@if (%ADEMPIERE_HOME%) == () goto environment
@if (%ADEMPIERE_DB_NAME%) == () goto environment
@if (%ADEMPIERE_DB_SERVER%) == () goto environment
@if (%ADEMPIERE_DB_PORT%) == () goto environment
@Rem Must have parameter: userAccount
@if (%1) == () goto usage

@set PGPASSWORD=%2
pg_dump -h %ADEMPIERE_DB_SERVER% -p %ADEMPIERE_DB_PORT% -U %1 %ADEMPIERE_DB_NAME% > %ADEMPIERE_HOME%\data\ExpDat.dmp
@set PGPASSWORD=

@cd %ADEMPIERE_HOME%\Data
@jar cvfM ExpDat.jar ExpDat.dmp

@goto end

:environment
@Echo Please make sure that the enviroment variables are set correctly:
@Echo		ADEMPIERE_HOME	e.g. D:\ADEMPIERE2
@Echo		ADEMPIERE_DB_NAME 	e.g. adempiere or xe
@Echo		ADEMPIERE_DB_SERVER 	e.g. dbserver.adempiere.org
@Echo		ADEMPIERE_DB_PORT 	e.g. 5432 or 1521

:usage
@echo Usage:		%0 <userAccount>
@echo Examples:	%0 ADEMPIERE/ADEMPIERE

:end
