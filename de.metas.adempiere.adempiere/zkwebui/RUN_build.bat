@Title Build Adempiere Clean
@Rem $Header: /cvsroot/adempiere/utils_dev/RUN_build.bat,v 1.22 2005/09/08 21:56:11 jjanke Exp $

@Rem Check java home
@IF NOT EXIST "%JAVA_HOME%\bin" ECHO "** JAVA_HOME NOT found"
@SET PATH=%JAVA_HOME%\bin;%PATH%

@Rem Check jdk
@IF NOT EXIST "%JAVA_HOME%\lib\tools.jar" ECHO "** Need Full Java SDK **"

@Rem Set ant classpath
@SET ANT_CLASSPATH=%CLASSPATH%;..\..\..\tools\lib\ant.jar;..\..\..\tools\lib\ant-launcher.jar;..\..\..\tools\lib\ant-swing.jar;..\..\..\tools\lib\ant-commons-net.jar;..\..\..\tools\lib\commons-net-1.4.0.jar
@SET ANT_CLASSPATH=%ANT_CLASSPATH%;%JAVA_HOME%\lib\tools.jar

@SET ANT_OPTS=-Xms128m -Xmx512m

@echo Cleanup ...
@"%JAVA_HOME%\bin\java" %ANT_OPTS% -classpath "%ANT_CLASSPATH%" -Dant.home="." org.apache.tools.ant.Main clean

@echo Building ...
@"%JAVA_HOME%\bin\java" %ANT_OPTS% -classpath "%ANT_CLASSPATH%" -Dant.home="." org.apache.tools.ant.Main -logger org.apache.tools.ant.listener.MailLogger war
@IF ERRORLEVEL 1 goto ERROR

@Echo Done ...
@Pause
@exit

:ERROR
@Color fc

@Pause