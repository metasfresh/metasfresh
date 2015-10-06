@echo off

REM Adempiere_Service_Install.bat - globalqss - based on http://javaservice.objectweb.org

if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)

%ADEMPIERE_HOME%\utils\windows\AdempiereService.exe -install ADempiere "%JAVA_HOME%\jre\bin\server\jvm.dll" -Xmx256M -Djava.class.path="%JAVA_HOME%\lib\tools.jar";"%ADEMPIERE_HOME%\jboss\bin\run.jar" -server %ADEMPIERE_JAVA_OPTIONS% -Djetty.port=%ADEMPIERE_WEB_PORT% -Djetty.ssl=%ADEMPIERE_SSL_PORT% -Djetty.keystore=%ADEMPIERE_KEYSTORE% -Djetty.password=%ADEMPIERE_KEYSTORE_PASSWORD% -start org.jboss.Main -params -c adempiere -b %ADEMPIERE_APPS_SERVER% -stop org.jboss.Main -method systemExit -out "%ADEMPIERE_HOME%\jboss\bin\out.txt" -current "%ADEMPIERE_HOME%\jboss\bin" -manual -overwrite -description "ADempiere JBoss server"

rem IT DEPENDS ON OTHER SERVICE?
rem add this option for dependency with postgresql service (replace with proper service name)
rem    -depends pgsql-8.2
rem add this option for dependency with oracle service (replace with proper service name)
rem    -depends oraclexe
rem NOTE: The -depends parameter must be put before -manual or -automatic parameter

rem DO YOU WANT AUTOMATIC STARTUP?
rem replace -manual by -auto to make service run automatically on system startup

rem other usefult options can be:
rem -err err_log_file
rem -shutdown seconds
rem -user user_name
rem -password password
rem -append or -overwrite
rem -description service_desc
