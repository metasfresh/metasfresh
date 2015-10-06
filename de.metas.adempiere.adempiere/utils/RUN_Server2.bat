@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Adempiere Server Start - %ADEMPIERE_HOME% (%ADEMPIERE_APPS_TYPE%)

@Rem $Id: RUN_Server2.bat,v 1.24 2005/10/26 00:38:18 jjanke Exp $

@Rem  To use your own Encryption class (implementing org.compiere.util.SecureInterface),
@Rem  you need to set it here (and in the client start script) - example:
@Rem  SET SECURE=-DADEMPIERE_SECURE=org.compiere.util.Secure
@SET SECURE=


@IF '%ADEMPIERE_APPS_TYPE%' == 'jboss' GOTO JBOSS
@GOTO UNSUPPORTED

:JBOSS
@Set NOPAUSE=Yes
@Set JAVA_OPTS=-server %ADEMPIERE_JAVA_OPTIONS% %SECURE% -Dorg.adempiere.server.embedded=true

@Echo Start Adempiere Apps Server %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)
@Call %JBOSS_HOME%\bin\run -c adempiere -b %ADEMPIERE_APPS_SERVER%
@Echo Done Adempiere Apps Server %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)
@GOTO END

:UNSUPPORTED
@Echo Apps Server start of %ADEMPIERE_APPS_TYPE% not supported

:END
@Rem Sleep 60
@CHOICE /C YN /T 60 /D N > NUL

@Exit