-- 2021-06-18T17:18:53.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541376,'S',TO_TIMESTAMP('2021-06-18 20:18:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.order.CopyOrgFromBPartner',TO_TIMESTAMP('2021-06-18 20:18:53','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-06-18T17:19:13.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2021-06-18 20:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541376
;

