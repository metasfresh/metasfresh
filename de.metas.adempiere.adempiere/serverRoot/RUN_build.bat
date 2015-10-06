@Title Build Adempiere Root
@Rem $Header: /cvsroot/adempiere/serverRoot/RUN_build.bat,v 1.11 2005/09/16 00:50:14 jjanke Exp $

@CALL ..\utils_dev\myDevEnv.bat
@IF NOT %ADEMPIERE_ENV%==Y GOTO NOBUILD

@echo Cleanup ...
@"%JAVA_HOME%\bin\java" -Dant.home="." %ANT_PROPERTIES% org.apache.tools.ant.Main clean

@echo Building ...
@"%JAVA_HOME%\bin\java" -Dant.home="." %ANT_PROPERTIES% org.apache.tools.ant.Main main

@Echo Done ...
@Rem Wait 60 second
@PING 1.1.1.1 -n 1 -w 60000 > NUL
@Exit

:NOBUILD
@Echo Check myDevEnv.bat (copy from myDevEnvTemplate.bat)
@Pause
