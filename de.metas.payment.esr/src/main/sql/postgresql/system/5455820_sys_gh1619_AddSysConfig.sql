-- 23.05.2017 18:41:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541147,'S',TO_TIMESTAMP('2017-05-23 18:41:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','de.metas.payment.esr.MatchOrg',TO_TIMESTAMP('2017-05-23 18:41:47','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 23.05.2017 18:41:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2017-05-23 18:41:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541147
;

