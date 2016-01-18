@Rem $Id: RUN_ImportReference.bat,v 1.11 2005/01/22 21:59:15 jjanke Exp $

@if (%ADEMPIERE_HOME%) == () (CALL myEnvironment.bat Server) else (CALL %ADEMPIERE_HOME%\utils\myEnvironment.bat Server)
@Title Import Reference - %ADEMPIERE_HOME% (%ADEMPIERE_DB_NAME%)


@echo Re-Create Reference User and import %ADEMPIERE_HOME%\data\Adempiere.dmp - (%ADEMPIERE_DB_NAME%)
@dir %ADEMPIERE_HOME%\data\Adempiere.dmp
@echo == The import will show warnings. This is OK ==
@pause

@Rem Parameter: <systemAccount> <AdempiereID> <AdempierePwd>
@call %ADEMPIERE_DB_PATH%\ImportAdempiere system/%ADEMPIERE_DB_SYSTEM% reference reference

@pause
