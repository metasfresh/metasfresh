--
-- the classes were moved to de.metas.email
--

UPDATE AD_Process SET ClassName='de.metas.email.process.R_MailText_Test' WHERE ClassName='de.metas.notification.process.R_MailText_Test';
UPDATE AD_Process SET ClassName='de.metas.email.process.EMailConfigTest' WHERE ClassName='de.metas.notification.process.EMailConfigTest';
