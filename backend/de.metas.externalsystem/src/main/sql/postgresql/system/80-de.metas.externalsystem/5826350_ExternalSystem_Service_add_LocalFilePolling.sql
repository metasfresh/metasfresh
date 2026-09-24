-- Register the local-file-polling service for ScriptedImportConversion.
-- Without this row nothing resolves the local-file enable/disable commands: ExternalServices
-- (getServiceByTypeAndCommand) finds no service for them, so no service instance is created and the
-- camel-side startup reconciler -- which matches a component by ExternalSystem_Service.Value -- has
-- nothing to reconcile. Mirrors the SFTP-polling record 540018 (script
-- 5814520_ExternalSystem_Service_add_SftpPolling.sql) and the REST record 540017.
--
-- Value / EnableCommand / DisableCommand below are literal matches against
--   ScriptedImportConversionLocalFileRouteBuilder.getServiceValue() / getEnableCommand() / getDisableCommand()
-- and against ScriptedImportConversionCommand.EnableLocalFilePolling / DisableLocalFilePolling.
-- A typo in any of the three makes an enabled endpoint silently never poll.
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   ExternalSystem_Service 540019 (defaultLocalFilePollingScriptedImportConversion)
--   AD_MigrationScript 5826350 (this script)

INSERT INTO ExternalSystem_Service
	(AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive,
	 ExternalSystem_Service_ID, ExternalSystem_ID, value, Name, Description, EnableCommand, DisableCommand)
VALUES
	(1000000, 1000000, now(), 100, now(), 100, 'Y',
	 540019 /*From ID Server*/, 540058, 'defaultLocalFilePollingScriptedImportConversion', 'Local File Polling',
	 '/scriptedimportconversion', 'enableLocalFilePolling', 'disableLocalFilePolling');
