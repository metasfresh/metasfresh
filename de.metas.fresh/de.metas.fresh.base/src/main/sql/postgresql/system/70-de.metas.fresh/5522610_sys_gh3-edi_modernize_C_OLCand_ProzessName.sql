
--
-- modernize process class name
-- 2019-05-27T10:11:57.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='org.adempiere.server.rpl.trx.process.C_OLCand_Update_IsImportedWithIssues', RefreshAllAfterExecution='Y', Value='C_OLCand_Update_IsImportedWithIssues',Updated=TO_TIMESTAMP('2019-05-27 10:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540429
;
