-- 2021-11-04T13:45:32.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='de.metas.async.AsyncBatchObserver.WaitTimeOutMS',Updated=TO_TIMESTAMP('2021-11-04 15:45:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541392
;

-- 2021-11-04T13:45:42.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Timeout to wait for a given AsyncBatchId to be completed',Updated=TO_TIMESTAMP('2021-11-04 15:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541392
;

