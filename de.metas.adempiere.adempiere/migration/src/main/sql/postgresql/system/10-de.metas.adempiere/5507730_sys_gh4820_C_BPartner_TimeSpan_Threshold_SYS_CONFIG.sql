-- 2018-12-07T17:41:49.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541251,'S',TO_TIMESTAMP('2018-12-07 17:41:49','YYYY-MM-DD HH24:MI:SS'),100,'* Number of months that have to pass until contract renewal so the customer turns from "new" to "regular" AND
* Number of months without a contract renewaluntil a customer is considered not a customer any more
(issue https://github.com/metasfresh/metasfresh/issues/4820)','D','Y','C_BPartner_TimeSpan_Threshold ',TO_TIMESTAMP('2018-12-07 17:41:49','YYYY-MM-DD HH24:MI:SS'),100,'12')
;

-- 2018-12-07T17:42:27.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='C_BPartner_TimeSpan_Threshold',Updated=TO_TIMESTAMP('2018-12-07 17:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541251
;

