@Title Build Adempiere Installer
@Rem  $Header: /cvsroot/adempiere/install/RUN_build.bat,v 1.6 2005/09/13 04:27:12 jjanke Exp $
@Echo off

@CALL ..\utils_dev\myDevEnv.bat
@IF NOT %ADEMPIERE_ENV%==Y GOTO NOBUILD

@echo Cleanup ...
@"%JAVA_HOME%\bin\java" -Dant.home="." %ANT_PROPERTIES% org.apache.tools.ant.Main clean

@echo Building ...
@"%JAVA_HOME%\bin\java" -Dant.home="." %ANT_PROPERTIES% org.apache.tools.ant.Main 

@Echo Done ...
@sleep 60
@exit

:NOBUILD
@Echo Check myDevEnv.bat (copy from myDevEnvTemplate.bat)
@Pause
