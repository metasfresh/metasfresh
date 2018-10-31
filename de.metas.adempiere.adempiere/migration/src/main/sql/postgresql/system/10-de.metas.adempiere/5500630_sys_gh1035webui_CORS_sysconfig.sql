-- 2018-09-04T11:57:34.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (Value,EntityType,AD_Client_ID,CreatedBy,UpdatedBy,IsActive,ConfigurationLevel,AD_SysConfig_ID,Description,AD_Org_ID,Name,Created,Updated) VALUES ('Y','D',0,100,100,'Y','S',541231,'',0,'webui.frontend.cors.enabled',TO_TIMESTAMP('2018-09-04 11:57:33','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-09-04 11:57:33','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-09-04T11:58:43.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If enabled, the Access-Control-Allow-Origin respone header will be set as "webui.frontend.url" sysconfig.',Updated=TO_TIMESTAMP('2018-09-04 11:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541231
;

