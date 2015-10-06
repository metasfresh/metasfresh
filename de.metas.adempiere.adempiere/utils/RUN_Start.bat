@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Start Adempiere  - %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)

@Rem $Id: RUN_Start.bat,v 1.11 2005/01/22 21:59:15 jjanke Exp $

@Echo Starting Database
@CALL %ADEMPIERE_DB_PATH%\Start.bat

@START %ADEMPIERE_HOME%\utils\RUN_Server2.bat 
