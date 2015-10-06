@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title	Export Adempiere Database - %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)
@Rem 
@Rem $Id: RUN_DBExport.bat,v 1.16 2005/04/27 17:45:02 jjanke Exp $
@Rem 
@Rem Parameter: <adempiereDBuser>/<adempiereDBpassword>

@call %ADEMPIERE_DB_PATH%\DBExport %ADEMPIERE_DB_USER% %ADEMPIERE_DB_PASSWORD%
@Rem call %ADEMPIERE_DB_PATH%\DBExportFull system %ADEMPIERE_DB_SYSTEM%

@Echo If the following statement fails, fix your environment
IF (%ADEMPIERE_HOME%) == () (CALL myDBcopy.bat) else (CALL %ADEMPIERE_HOME%\utils\myDBcopy.bat)

@Rem Sleep 60
@CHOICE /C YN /T 60 /D N > NUL