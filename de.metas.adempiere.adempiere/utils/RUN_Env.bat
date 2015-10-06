@Title Adempiere Environment Check

@Rem $Id: RUN_Env.bat,v 1.16 2005/01/22 21:59:15 jjanke Exp $

@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat)

@Echo General ...
@Echo PATH      = %PATH%
@Echo CLASSPTH  = %CLASSPATH%

@Echo .
@Echo Homes ...
@Echo ADEMPIERE_HOME        = %ADEMPIERE_HOME%
@Echo JAVA_HOME            = %JAVA_HOME%
@Echo ADEMPIERE_DB_URL      = %ADEMPIERE_DB_URL%

@Echo .
@Echo Database ...
@Echo ADEMPIERE_DB_USER     = %ADEMPIERE_DB_USER%
@Echo ADEMPIERE_DB_PASSWORD = %ADEMPIERE_DB_PASSWORD%
@Echo ADEMPIERE_DB_PATH     = %ADEMPIERE_DB_PATH%

@Echo .. Oracle specifics
@Echo ADEMPIERE_DB_NAME      = %ADEMPIERE_DB_NAME%
@Echo ADEMPIERE_DB_SYSTEM   = %ADEMPIERE_DB_SYSTEM%

%JAVA_HOME%\bin\java -version

@Echo .
@Echo Java Version should be "1.4.2"
@Echo ---------------------------------------------------------------
@Pause

@Echo .
@Echo ---------------------------------------------------------------
@Echo Database Connection Test (1) ... %ADEMPIERE_DB_NAME%
@Echo If this fails, verify the ADEMPIERE_DB_NAME setting with Oracle Net Manager
@Echo You should see an OK at the end
@Pause
tnsping %ADEMPIERE_DB_NAME%

@Echo .
@Echo ---------------------------------------------------------------
@Echo Database Connection Test (3) ... system/%ADEMPIERE_DB_SYSTEM% in %ADEMPIERE_DB_HOME%
@Echo If this test fails, verify the system password in ADEMPIERE_DB_SYSTEM
@Pause
sqlplus system/%ADEMPIERE_DB_SYSTEM%@%ADEMPIERE_DB_NAME% @%ADEMPIERE_DB_HOME%\Test.sql

@Echo .
@Echo ---------------------------------------------------------------
@Echo Checking Database Size
@Pause
sqlplus system/%ADEMPIERE_DB_SYSTEM%@%ADEMPIERE_DB_NAME% @%ADEMPIERE_DB_HOME%\CheckDB.sql %ADEMPIERE_DB_USER%

@Echo .
@Echo ---------------------------------------------------------------
@Echo Database Connection Test (4) ... %ADEMPIERE_DB_USER%/%ADEMPIERE_DB_PASSWORD%
@Echo This may fail, if you have not imported the Adempiere database yet - Just enter EXIT and run this script again after the import
@Pause
sqlplus %ADEMPIERE_DB_USER%/%ADEMPIERE_DB_PASSWORD%@%ADEMPIERE_DB_NAME% @%ADEMPIERE_DB_HOME%\Test.sql

@Echo .
@Echo Done
@pause
