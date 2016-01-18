@Title Install Adempiere Server
@Rem  $Header: /cvsroot/adempiere/install/Adempiere/RUN_setup.bat,v 1.19 2005/09/08 21:54:12 jjanke Exp $
@Echo off

@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat)

@Set JAVA=%JAVA_HOME%\bin\java

@Echo =======================================
@Echo Sign Database Build
@Echo =======================================
@SET CP=%ADEMPIERE_HOME%\lib\CInstall.jar;%ADEMPIERE_HOME%\lib\Adempiere.jar;%ADEMPIERE_HOME%\lib\CCTools.jar;%ADEMPIERE_HOME%\lib\oracle.jar;%ADEMPIERE_HOME%\lib\fyracle.jar;%ADEMPIERE_HOME%\lib\derby.jar;%ADEMPIERE_HOME%\lib\jboss.jar;%ADEMPIERE_HOME%\lib\postgresql.jar;

@"%JAVA%" -classpath %CP% -DADEMPIERE_HOME=%ADEMPIERE_HOME% org.adempiere.process.SignDatabaseBuild