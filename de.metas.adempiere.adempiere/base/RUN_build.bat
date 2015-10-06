@Title Build Base
@Rem   $Header: /cvsroot/adempiere/base/RUN_build.bat,v 1.10 2005/09/16 00:48:27 jjanke Exp $
@Echo off

@CALL ..\utils_dev\myDevEnv.bat
@IF NOT %ADEMPIERE_ENV%==Y GOTO NOBUILD

@echo Cleanup ...
@"%JAVA_HOME%\bin\java" -Dant.home="." %ANT_PROPERTIES% org.apache.tools.ant.Main clean

@echo Building ...
@"%JAVA_HOME%\bin\java" -Dant.home="." %ANT_PROPERTIES% org.apache.tools.ant.Main dist

@Echo Done ...
@sleep 60
@exit

:NOBUILD
@Echo Check myDevEnv.bat (copy from myDevEnvTemplate.bat)
@Pause
