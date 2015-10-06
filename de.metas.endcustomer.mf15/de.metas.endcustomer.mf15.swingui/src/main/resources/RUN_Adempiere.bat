@Title	Adempiere Client %ADEMPIERE_HOME%   %1%
@Rem $Id: RUN_Adempiere.bat,v 1.24 2005/08/24 22:50:37 jjanke Exp $
@Echo off

@Rem Set/Overwrite ADEMPIERE_HOME/JAVA_HOME 
@Rem explicitly here for different versions, etc. e.g.
@Rem
@Rem SET ADEMPIERE_HOME=C:\R251\Adempiere
@Rem SET JAVA_HOME=C:\j2sdk1.4.2_06

@Rem Variables: 
@Rem DBG_PORT                   ->  Start-Port the script will scan (min. port)
@Rem MAX_DBG_PORT               ->  Maximum Port of scanning the script will disable debugging if DBG_PORT=MAX_DBG_PORT
@Rem ERRORLEVEL                 ->  Windows built-in return-code ( returns "0" if OK, "1" if not OK )
@Rem CLIENT_REMOTE_DEBUG_OPTS   ->  Commandline-Options (Arguments) to use, if Debugging is enabled
@Rem JMX_REMOTE_DEBUG_OPTS      ->  Same as CLIENT_REMOTE_DEBUG_OPTS

:ADEMPIERE_DEBUG_PORTS
@SET /a DBG_PORT=10000
@SET /a MAX_DBG_PORT=10100
:CHECK_PORTS_CLIENT
@netstat -an|find /i "%DBG_PORT%" 1>NUL 
@IF NOT %ERRORLEVEL% == 1 (
  IF NOT %DBG_PORT% == %MAX_DBG_PORT% (
    SET /a DBG_PORT+=1
    goto :CHECK_PORTS_CLIENT
  )
)
@IF %DBG_PORT% == %MAX_DBG_PORT% (
  SET CLIENT_REMOTE_DEBUG_OPTS=
  echo Not using CLIENT_DEBUGGING_PORTS
  ) ELSE (
  SET CLIENT_REMOTE_DEBUG_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=%DBG_PORT%
  echo Using CLIENT_DEBUGGING_PORT %DBG_PORT%
)
@goto JMX_DEBUG_PORTS

:JMX_DEBUG_PORTS
@SET /a DBG_PORT=10101
@SET /a MAX_DBG_PORT=10200
:CHECK_PORTS_JMX
@netstat -an|find /i "%DBG_PORT%" 1>NUL
@IF NOT %ERRORLEVEL% == 1 (
  IF NOT %DBG_PORT% == %MAX_DBG_PORT% (
    SET /a DBG_PORT+=1
    goto :CHECK_PORTS_JMX
  )
)
@IF %DBG_PORT% == %MAX_DBG_PORT% (
  SET JMX_REMOTE_DEBUG_OPTS=
  echo Not using JMX_DEBUGGING_PORTS
  ) ELSE (
  SET JMX_REMOTE_DEBUG_OPTS=-Dcom.sun.management.jmxremote.port=%DBG_PORT% -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
  echo Using JMX_DEBUGGING_PORT %DBG_PORT%
)

:CHECK_JAVA:
@if not "%JAVA_HOME%" == "" goto JAVA_HOME_OK
@Set JAVA=java
@Echo JAVA_HOME is not set.  
@Echo   You may not be able to start Adempiere
@Echo   Set JAVA_HOME to the directory of your local JDK.
@Echo   You could set it via WinEnv.js e.g.:
@Echo     cscript WinEnv.js C:\Adempiere C:\j2sdk1.4.2_06
@goto CHECK_ADEMPIERE
:JAVA_HOME_OK
@Set JAVA=%JAVA_HOME%\bin\java

:CHECK_ADEMPIERE
@if not "%ADEMPIERE_HOME%" == "" goto ADEMPIERE_HOME_OK

REM set ADEMPIERE_HOME=%~dp0..
set ADEMPIERE_HOME=%~dp0

@Echo ADEMPIERE_HOME is not set.  
@Echo   You may not be able to start Adempiere
@Echo   Set ADEMPIERE_HOME to the directory of Adempiere.
@Echo   You could set it via WinEnv.js e.g.:
@Echo     cscript WinEnv.js C:\Adempiere C:\j2sdk1.4.2_08
@Echo   For now we will try with ADEMPIERE_HOME=%ADEMPIERE_HOME%
REM @goto MULTI_INSTALL

:ADEMPIERE_HOME_OK
@REM @SET CLASSPATH=%ADEMPIERE_HOME%\lib\de.metas.adempiere.adempiere.jbossfacet.jar;%ADEMPIERE_HOME%\lib\jbossall-client.jar;%ADEMPIERE_HOME%\lib\adempiereJasper-fonts.jar;%ADEMPIERE_HOME%\lib\adempiereJasper-OCRA-font.jar;%ADEMPIERE_HOME%\lib\adempiereJasper-OCRB-B10-font.jar;%ADEMPIERE_HOME%\lib\de.metas.endcustomer.mf15.base-allInOne.jar
@SET CLASSPATH=%ADEMPIERE_HOME%\lib\jp.osdn.ocra.jar;%ADEMPIERE_HOME%\lib\jp.osdn.ocrb.jar;%ADEMPIERE_HOME%\lib\de.metas.endcustomer.mf15.swingui-1.0_IT-SNAPSHOT.jar

:MULTI_INSTALL
@REM  To switch between multiple installs, copy the created Adempiere.properties file
@REM  Select the configuration by setting the PROP variable
@SET PROP=
@Rem  SET PROP=-DPropertyFile=C:\test.properties
@REM  Alternatively use parameter
@if "%1" == "" goto ENCRYPTION
@SET PROP=-DPropertyFile=%1

:ENCRYPTION
@Rem  To use your own Encryption class (implementing org.compiere.util.SecureInterface),
@Rem  you need to set it here (and in the server start script) - example:
@Rem  SET SECURE=-DADEMPIERE_SECURE=org.compiere.util.Secure
@SET SECURE=

:START
@REM Note that we don't need the javaagent, because our stuff is already instrumented
@REM Note that -Djava.util.Arrays.useLegacyMergeSort=true is related to task "07072 Comparison method violates its general contract (100965620270)"
"%JAVA%" -Xms32m -Xmx1024m -XX:+HeapDumpOnOutOfMemoryError -Djava.util.Arrays.useLegacyMergeSort=true -Dorg.adempiere.client.lang="Deutsch (Schweiz)" -DADEMPIERE_HOME=%ADEMPIERE_HOME% %PROP% %CLIENT_REMOTE_DEBUG_OPTS% %JMX_REMOTE_DEBUG_OPTS% %SECURE% -classpath "%CLASSPATH%" org.compiere.Adempiere

@Rem @sleep 15
@CHOICE /C YN /T 15 /D N > NUL