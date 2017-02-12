@Title	metasfresh Client %METASFRESH_HOME%   %1%
@Echo off

@Rem Set/Overwrite METASFRESH_HOME/JAVA_HOME 
@Rem explicitly here for different versions, etc. e.g.
@Rem
@Rem SET METASFRESH_HOME=C:\metasfresh
@Rem SET JAVA_HOME=c:\Program Files\Java\jdk1.8.0_40

@Rem Variables: 
@Rem DBG_PORT                   ->  Start-Port the script will scan (min. port)
@Rem MAX_DBG_PORT               ->  Maximum Port of scanning the script will disable debugging if DBG_PORT=MAX_DBG_PORT
@Rem ERRORLEVEL                 ->  Windows built-in return-code ( returns "0" if OK, "1" if not OK )
@Rem CLIENT_REMOTE_DEBUG_OPTS   ->  Commandline-Options (Arguments) to use, if Debugging is enabled
@Rem JMX_REMOTE_DEBUG_OPTS      ->  Same as CLIENT_REMOTE_DEBUG_OPTS

:METASFRESH_DEBUG_PORTS
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
@Echo   You may not be able to start Metasfresh
@Echo   Set JAVA_HOME to the directory of your local JDK.
@goto CHECK_METASFRESH
:JAVA_HOME_OK
@Set JAVA=%JAVA_HOME%\bin\java

:CHECK_METASFRESH
@if not "%METASFRESH_HOME%" == "" goto METASFRESH_HOME_OK

SET LOG_DIR=%UserProfile%\.metasfresh\log
@Echo Going to write our log files to %LOG_DIR%

SET METASFRESH_HOME=%~dp0

@Echo METASFRESH_HOME is not set.  
@Echo   You may not be able to start Metasfresh
@Echo   Set METASFRESH_HOME to the directory of Metasfresh.
@Echo   For now we will try with METASFRESH_HOME=%METASFRESH_HOME%
REM @goto MULTI_INSTALL

:METASFRESH_HOME_OK
SET MAIN_CLASSNAME=org.springframework.boot.loader.JarLauncher

@REM finding our jar file
@REM thx to http://stackoverflow.com/questions/13876771/find-file-and-return-full-path-using-a-batch-file
for /f "delims=" %%F in ('dir /b /s "%METASFRESH_HOME%\lib\de.metas.endcustomer.*.swingui-*.jar" 2^>nul') do set JAR=%%F

SET CLASSPATH=%JAR%

:MULTI_INSTALL
@REM  To switch between multiple installs, copy the created metasfresh.properties file
@REM  Select the configuration by setting the PROP variable
@SET PROP=
@Rem  SET PROP=-DPropertyFile=C:\test.properties
@REM  Alternatively use parameter
@if "%1" == "" goto ENCRYPTION
@SET PROP=-DPropertyFile=%1

:ENCRYPTION
@Rem  To use your own Encryption class (implementing org.compiere.util.SecureInterface),
@Rem  you need to set it here (and in the server start script) - example:
@Rem  SET SECURE=-DMETASFRESH_SECURE=org.compiere.util.Secure
@SET SECURE=

@Echo JAVA=%JAVA%
@Echo JAVA_OPTS=%JAVA_OPTS%
@Echo PROP=%PROP%
@Echo CLIENT_REMOTE_DEBUG_OPTS=%CLIENT_REMOTE_DEBUG_OPTS%
@Echo JMX_REMOTE_DEBUG_OPTS=%JMX_REMOTE_DEBUG_OPTS%
@Echo SECURE=%SECURE%
@Echo CLASSPATH=%CLASSPATH%
@Echo MAIN_CLASSNAME=%MAIN_CLASSNAME%

:START
SET JAVA_OPTS=-Xms32m -Xmx1024m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError -Djava.util.Arrays.useLegacyMergeSort=true
"%JAVA%" %JAVA_OPTS% -DMETASFRESH_HOME=%METASFRESH_HOME% %PROP% %CLIENT_REMOTE_DEBUG_OPTS% %JMX_REMOTE_DEBUG_OPTS% "-Dlogging.path=%LOG_DIR%" %SECURE% -classpath "%CLASSPATH%" %MAIN_CLASSNAME%

@Rem @sleep 15
@CHOICE /C YN /T 15 /D N > NUL
