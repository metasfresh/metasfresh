-- 2017-09-12T18:54:41.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541159,'S',TO_TIMESTAMP('2017-09-12 18:54:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.letters','Y','de.metas.letters.enabled',TO_TIMESTAMP('2017-09-12 18:54:41','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2017-09-12T18:54:47.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2017-09-12 18:54:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541159
;

