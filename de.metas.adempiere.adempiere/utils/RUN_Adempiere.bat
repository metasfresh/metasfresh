@Title	Adempiere Client %ADEMPIERE_HOME%   %1%
@Rem $Id: RUN_Adempiere.bat,v 1.24 2005/08/24 22:50:37 jjanke Exp $
@Echo off

@Rem Set/Overwrite ADEMPIERE_HOME/JAVA_HOME 
@Rem explicitly here for different versions, etc. e.g.
@Rem
@Rem SET ADEMPIERE_HOME=C:\R251\Adempiere
@Rem SET JAVA_HOME=C:\j2sdk1.4.2_06

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
Set CLASSPATH=lib\Adempiere.jar;lib\AdempiereCLib.jar;lib\CompiereJasperReqs.jar;%CLASSPATH%
set ADEMPIERE_HOME=%~dp0..
@Echo ADEMPIERE_HOME is not set.  
@Echo   You may not be able to start Adempiere
@Echo   Set ADEMPIERE_HOME to the directory of Adempiere.
@Echo   You could set it via WinEnv.js e.g.:
@Echo     cscript WinEnv.js C:\Adempiere C:\j2sdk1.4.2_08
@goto MULTI_INSTALL
:ADEMPIERE_HOME_OK
@Set CLASSPATH=%ADEMPIERE_HOME%\lib\Adempiere.jar;%ADEMPIERE_HOME%\lib\AdempiereCLib.jar;%ADEMPIERE_HOME%\lib\CompiereJasperReqs.jar;%CLASSPATH%

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
@"%JAVA%" -Xms32m -Xmx512m -DADEMPIERE_HOME=%ADEMPIERE_HOME% %PROP% %SECURE% -classpath "%CLASSPATH%" org.compiere.Adempiere 

@Rem @sleep 15
@CHOICE /C YN /T 15 /D N > NUL