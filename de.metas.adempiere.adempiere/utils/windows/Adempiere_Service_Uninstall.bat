@echo off

REM Adempiere_Service_Uninstall.bat - globalqss - based on http://javaservice.objectweb.org

if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)

NET STOP Adempiere
%ADEMPIERE_HOME%\utils\windows\AdempiereService.exe -uninstall Adempiere
