-- 2022-06-18T14:05:25.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541463,'S',TO_TIMESTAMP('2022-06-18 17:05:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','FactAcctLogService.useLegacyProcessor',TO_TIMESTAMP('2022-06-18 17:05:25','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

--2022-06-18T14:05:36.746Z
--I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2022-06-18 17:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541463
;

