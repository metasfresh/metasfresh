@Rem $Id: RUN_DBRestore.bat,v 1.13 2005/01/22 21:59:15 jjanke Exp $

@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Restore Adempiere Database from Export - %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)


@echo Re-Create Adempiere User and import %ADEMPIERE_HOME%\data\ExpDat.dmp
@dir %ADEMPIERE_HOME%\data\ExpDat.dmp
@echo == The import will show warnings. This is OK ==
@pause

@Rem Parameter: <systemAccount> <adempiereID> <adempierePwd>
@Rem globalqss - cruiz - 2007-10-09 - added fourth parameter for postgres (ignored in oracle)
@call %ADEMPIERE_DB_PATH%\DBRestore system/%ADEMPIERE_DB_SYSTEM% %ADEMPIERE_DB_USER% %ADEMPIERE_DB_PASSWORD% %ADEMPIERE_DB_SYSTEM%

@Call %ADEMPIERE_HOME%\utils\RUN_SignDatabaseBuild.bat > NUL 2>&1

@pause
