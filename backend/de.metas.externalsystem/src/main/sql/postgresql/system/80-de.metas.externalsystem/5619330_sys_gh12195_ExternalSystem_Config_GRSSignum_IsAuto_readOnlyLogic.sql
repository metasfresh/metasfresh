-- 2021-12-17T10:34:41.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2021-12-17 12:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578923
;

-- 2021-12-17T10:37:15.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsSyncBPartnersToRestEndpoint@=N',Updated=TO_TIMESTAMP('2021-12-17 12:37:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578922
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsSyncBPartnersToRestEndpoint@=N',Updated=TO_TIMESTAMP('2021-12-17 13:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578923
;

UPDATE ExternalSystem_Config_GRSSignum SET IsAutoSendCustomers = 'N', Updated=TO_TIMESTAMP('2021-12-17 13:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
;