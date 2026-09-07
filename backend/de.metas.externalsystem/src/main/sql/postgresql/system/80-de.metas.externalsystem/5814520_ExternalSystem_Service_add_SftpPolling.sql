-- Register the SFTP-polling service for ScriptedImportConversion.
-- The camel scripted-adapter + backend already support SFTP (ScriptedImportConversionSftpRouteBuilder,
-- ScriptedImportConversionCommand.EnableSftpPolling), but no ExternalSystem_Service row existed for
-- it -- so the backend (ExternalServices.getServiceByTypeAndCommand) and the camel reconciler
-- (matches by serviceValue) could not resolve/enable it. This registers it, mirroring the REST
-- service record 540017.
--
-- IDs allocated from idserver.metas.de on 2026-07-19:
--   ExternalSystem_Service 540018 (defaultSftpPollingScriptedImportConversion)

INSERT INTO ExternalSystem_Service
	(AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive,
	 ExternalSystem_Service_ID, ExternalSystem_ID, value, Name, Description, EnableCommand, DisableCommand)
VALUES
	(1000000, 1000000, now(), 100, now(), 100, 'Y',
	 540018 /*From ID Server*/, 540058, 'defaultSftpPollingScriptedImportConversion', 'SFTP Polling',
	 '/scriptedimportconversion', 'enableSftpPolling', 'disableSftpPolling');
