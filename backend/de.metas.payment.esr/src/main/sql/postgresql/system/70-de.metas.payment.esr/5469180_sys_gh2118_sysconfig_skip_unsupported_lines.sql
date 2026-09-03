
-- 2017-08-08T18:38:52.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541157,'O',TO_TIMESTAMP('2017-08-08 18:38:52','YYYY-MM-DD HH24:MI:SS'),100,'If a given ESR_ImportLine has a transaction type that we don''t support and this flag is set to Y, then flag that line as valid and processed so that the whole ESR_Import can be flagged likewise.

See https://github.com/metasfresh/metasfresh/issues/2118','de.metas.payment.esr','Y','de.metas.payment.esr.SkipUnspportedTrxTypes',TO_TIMESTAMP('2017-08-08 18:38:52','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2017-08-08T18:38:55.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If a given ESR_ImportLine has a transaction type that we don''t support and this flag is set to Y, then flag that line as valid and processed so that the whole ESR_Import can be flagged likewise.
See https://github.com/metasfresh/metasfresh/issues/2118',Updated=TO_TIMESTAMP('2017-08-08 18:38:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541157
;

-- 2017-08-08T19:11:17.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.payment.esr.IgnoreUnspportedTrxTypes',Updated=TO_TIMESTAMP('2017-08-08 19:11:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541157
;

-- 2017-08-08T19:11:33.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If a given ESR_ImportLine has a transaction type that we don''t support and this flag is set to Y, then flag that line as both valid and processed so that the whole ESR_Import can be flagged likewise.
See https://github.com/metasfresh/metasfresh/issues/2118',Updated=TO_TIMESTAMP('2017-08-08 19:11:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541157
;

