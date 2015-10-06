@Echo	Adempiere Database Import		$Revision: 1.3 $

@Rem $Id: ImportAdempiere.bat,v 1.3 2005/01/22 21:59:15 jjanke Exp $

@Echo	Importing Adempiere DB from %ADEMPIERE_HOME%\data\Adempiere_pg.dmp (%ADEMPIERE_DB_NAME%)

@if (%ADEMPIERE_HOME%) == () goto environment
@if (%ADEMPIERE_DB_NAME%) == () goto environment
@if (%ADEMPIERE_DB_SERVER%) == () goto environment
@if (%ADEMPIERE_DB_PORT%) == () goto environment
@Rem Must have parameters systemAccount AdempiereID AdempierePwd
@if (%1) == () goto usage
@if (%2) == () goto usage
@if (%3) == () goto usage

@set PGPASSWORD=%4
@echo -------------------------------------
@echo Re-Create user and database
@echo -------------------------------------
@dropdb -h %ADEMPIERE_DB_SERVER% -p %ADEMPIERE_DB_PORT% -U postgres %ADEMPIERE_DB_NAME%
@dropuser -h %ADEMPIERE_DB_SERVER% -p %ADEMPIERE_DB_PORT% -U postgres %2
@set ADEMPIERE_CREATE_ROLE_SQL=CREATE ROLE %2 SUPERUSER LOGIN PASSWORD '%3'
@psql -h %ADEMPIERE_DB_SERVER% -p %ADEMPIERE_DB_PORT% -U postgres -c "%ADEMPIERE_CREATE_ROLE_SQL%"
@set ADEMPIERE_CREATE_ROLE_SQL=

@set PGPASSWORD=%3
@createdb -h %ADEMPIERE_DB_SERVER% -p %ADEMPIERE_DB_PORT% -E UNICODE -O %2 -U %2 %ADEMPIERE_DB_NAME%

@echo -------------------------------------
@echo Import Adempiere_pg.dmp
@echo -------------------------------------
@set ADEMPIERE_ALTER_ROLE_SQL=ALTER ROLE %2 SET search_path TO adempiere, sqlj, pg_catalog
@psql -h %ADEMPIERE_DB_SERVER% -p %ADEMPIERE_DB_PORT% -d %ADEMPIERE_DB_NAME% -U %2 -c "%ADEMPIERE_ALTER_ROLE_SQL%"
@psql -h %ADEMPIERE_DB_SERVER% -p %ADEMPIERE_DB_PORT% -d %ADEMPIERE_DB_NAME% -U %2 -c "drop schema sqlj cascade"
@psql -h %ADEMPIERE_DB_SERVER% -p %ADEMPIERE_DB_PORT% -d %ADEMPIERE_DB_NAME% -U %2 -f %ADEMPIERE_HOME%/data/Adempiere_pg.dmp
@set ADEMPIERE_ALTER_ROLE_SQL=


@set PGPASSWORD=
@goto end

:environment
@Echo Please make sure that the enviroment variables are set correctly:
@Echo		ADEMPIERE_HOME	e.g. D:\ADEMPIERE2
@Echo		ADEMPIERE_DB_NAME 	e.g. adempiere or xe
@Echo		ADEMPIERE_DB_SERVER 	e.g. dbserver.adempiere.org
@Echo		ADEMPIERE_DB_PORT 	e.g. 5432 or 1521

:usage
@echo Usage:		%0 <systemAccount> <AdempiereID> <AdempierePwd> <PostgresPwd>
@echo Example:	%0 postgres Adempiere Adempiere postgresPwd

:end
