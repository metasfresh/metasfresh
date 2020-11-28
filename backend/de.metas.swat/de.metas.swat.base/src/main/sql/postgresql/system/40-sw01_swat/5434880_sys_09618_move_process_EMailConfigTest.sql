
-- 07.12.2015 15:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.notification.process.EMailConfigTest', Name='Teste EMail', Value='EMailConfigTest',Updated=TO_TIMESTAMP('2015-12-07 15:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540192
;

-- 07.12.2015 15:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540192
;
