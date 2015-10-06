@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Stop DataBase Service  - %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)

@Rem $Id: RUN_DBStop.bat,v 1.5 2005/01/22 21:59:15 jjanke Exp $

@CALL %ADEMPIERE_DB_PATH%\Stop.bat
@Echo Done stopping database %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)

@Rem Sleep 60
@CHOICE /C YN /T 60 /D N > NUL