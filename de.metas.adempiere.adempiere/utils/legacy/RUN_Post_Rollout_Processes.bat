@Title RUNNING POST ROLLOUT PROCESSES
@Rem
@Rem " 02230: Rollout - Automatisch 'role acess update' und 'sequence number check' ausführen (2011101310000042)"
@Rem Based on script RUN_SignDatabaseBuild.sh
@Rem
@Rem !!! IMPORTANT: We don't roll out to windows, therefore this script has not been tested yet !!!
@Rem
@Rem author: t.schoeneberg@metas.de
@Rem

@Echo off

@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat)

@Set JAVA=%JAVA_HOME%\bin\java

@SET CP=%ADEMPIERE_HOME%\lib\CInstall.jar;%ADEMPIERE_HOME%\lib\Adempiere.jar;%ADEMPIERE_HOME%\lib\CCTools.jar;%ADEMPIERE_HOME%\lib\oracle.jar;%ADEMPIERE_HOME%\lib\fyracle.jar;%ADEMPIERE_HOME%\lib\derby.jar;%ADEMPIERE_HOME%\lib\jboss.jar;%ADEMPIERE_HOME%\lib\postgresql.jar;

@Echo =======================================
@Echo Sign Database Build
@Echo =======================================
@"%JAVA%" -classpath %CP% -DADEMPIERE_HOME=%ADEMPIERE_HOME% org.adempiere.process.SignDatabaseBuild

@Echo =======================================
@Echo Update Sequence Numbers
@Echo =======================================
@"%JAVA%" -classpath %CP% -DADEMPIERE_HOME=%ADEMPIERE_HOME% org.compiere.process.SequenceCheck 

@Echo =======================================
@Echo Update Role Access
@Echo =======================================
@"%JAVA%" -classpath %CP% -DADEMPIERE_HOME=%ADEMPIERE_HOME% org.compiere.process.RoleAccessUpdate 

