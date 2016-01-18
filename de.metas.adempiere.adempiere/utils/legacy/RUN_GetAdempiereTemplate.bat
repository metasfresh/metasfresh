@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Download Adempiere.jar Database into %ADEMPIERE_HOME%\data

@Rem $Id: RUN_GetAdempiereTemplate.bat,v 1.1 2002/10/08 04:31:20 jjanke Exp $

@Echo Download Adempiere.jar Database into %ADEMPIERE_HOME%\data

@ping @ADEMPIERE_FTP_SERVER@
@cd %ADEMPIERE_HOME%\data
@del Adempiere.jar

@ftp -s:%ADEMPIERE_HOME%\utils\ftpGetAdempiere.txt

@Echo Unpacking ...
@jar xvf Adempiere.jar

@Echo ........ Received

@cd %ADEMPIERE_HOME%\utils
@START RUN_ImportAdempiere.bat

@pause
