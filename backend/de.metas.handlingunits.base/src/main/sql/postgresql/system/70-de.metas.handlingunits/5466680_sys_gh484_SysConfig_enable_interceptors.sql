-- 2017-07-03T09:11:12.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541155,'S',TO_TIMESTAMP('2017-07-03 09:11:12','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y, then the model interceptors that add records to the M_HU_Trace table are gegistered on startup','de.metas.handlingunits','Y','de.metas.handlingunits.trace.interceptor.enabled',TO_TIMESTAMP('2017-07-03 09:11:12','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2017-07-14T07:12:30.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If set to Y, then the listeners and model interceptors that add records to the M_HU_Trace table are registered on startup',Updated=TO_TIMESTAMP('2017-07-14 07:12:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541155
;

