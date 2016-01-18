@Rem $Id: RUN_TrlExport.bat,v 1.4 2005/09/16 00:49:37 jjanke Exp $

@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Export Translation - %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)

@SET AD_LANGUAGE=de_DE
@SET DIRECTORY=%ADEMPIERE_HOME%\data\%AD_LANGUAGE%

@echo This Procedure exports language %AD_LANGUAGE% into directory %DIRECTORY%
@pause

@"%JAVA_HOME%\bin\java" -cp %CLASSPATH% org.compiere.install.Translation %DIRECTORY% %AD_LANGUAGE% export

@pause
