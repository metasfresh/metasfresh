-- 14.07.2016 16:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541001,'C',TO_TIMESTAMP('2016-07-14 16:35:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','MRP_Product_Info_DisplayConferenceFlag',TO_TIMESTAMP('2016-07-14 16:35:13','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 14.07.2016 16:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.fresh.MRPProductInfo.DisplayConferenceFlag',Updated=TO_TIMESTAMP('2016-07-14 16:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541001
;

