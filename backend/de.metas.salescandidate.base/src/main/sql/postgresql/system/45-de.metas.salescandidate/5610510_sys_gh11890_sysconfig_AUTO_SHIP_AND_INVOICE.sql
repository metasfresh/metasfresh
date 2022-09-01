
-- 2021-10-22T18:27:24.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541421,'S',TO_TIMESTAMP('2021-10-22 21:27:24','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','AUTO_SHIP_AND_INVOICE',TO_TIMESTAMP('2021-10-22 21:27:24','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2021-10-22T18:27:34.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Feature flag to enable/disable automate shipment and invoice;',Updated=TO_TIMESTAMP('2021-10-22 21:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541421
;

-- 2021-10-22T18:27:51.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2021-10-22 21:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541421
;

-- 2021-10-27T09:14:52.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2021-10-27 12:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541421
;


