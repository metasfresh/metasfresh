
-- 19.01.2016 16:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540930,'S',TO_TIMESTAMP('2016-01-19 16:31:36','YYYY-MM-DD HH24:MI:SS'),100,'AD_User_ID of the user to notify if an error occurs with the asyncronous creation of a counter document. Empty value or missing AD_SysConfig record means "notify noone".
100=SuperUser','de.metas.async','Y','de.metas.async.api.CreateCounterDocWP.AD_User_InCharge_ID',TO_TIMESTAMP('2016-01-19 16:31:36','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 19.01.2016 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.async.api.CreateCounterDocPP.AD_User_InCharge_ID',Updated=TO_TIMESTAMP('2016-01-19 17:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540930
;
