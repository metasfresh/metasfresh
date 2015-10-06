@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Adempiere Server Stop - %ADEMPIERE_HOME%

@Rem $Id: RUN_Server2Stop.bat,v 1.12 2005/09/06 02:46:16 jjanke Exp $

@IF '%ADEMPIERE_APPS_TYPE%' == 'jboss' GOTO JBOSS
@GOTO UNSUPPORTED

:JBOSS
@Set NOPAUSE=Yes
@Set JBOSS_LIB=%JBOSS_HOME%\lib
@Set JBOSS_SERVERLIB=%JBOSS_HOME%\server\adempiere\lib
@Set JBOSS_CLASSPATH=%ADEMPIERE_HOME%\lib\jboss.jar;%JBOSS_LIB%\jboss-system.jar

@CD %JBOSS_HOME%\bin
Call shutdown --server=jnp://%ADEMPIERE_APPS_SERVER%:%ADEMPIERE_JNP_PORT% --shutdown

@Echo Done Stopping Adempiere Apps Server %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)
@GOTO END

:UNSUPPORTED
@Echo Apps Server stop of %ADEMPIERE_APPS_TYPE% not supported

:END
@Rem Sleep 30
@CHOICE /C YN /T 30 /D N > NUL

@Exit