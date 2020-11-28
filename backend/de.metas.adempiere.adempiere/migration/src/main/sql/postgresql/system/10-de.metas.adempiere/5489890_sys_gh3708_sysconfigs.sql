-- 2018-04-04T10:33:41.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (Value,EntityType,AD_Client_ID,CreatedBy,UpdatedBy,IsActive,ConfigurationLevel,AD_SysConfig_ID,Description,AD_Org_ID,Name,Created,Updated) VALUES ('-','D',0,100,100,'Y','S',541202,'webui''s backend server URL. e.g. https://myWebuiServer',0,'webui.server.url',TO_TIMESTAMP('2018-04-04 10:33:40','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-04 10:33:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-04T10:34:45.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (Value,EntityType,AD_Client_ID,CreatedBy,UpdatedBy,IsActive,ConfigurationLevel,AD_SysConfig_ID,Description,AD_Org_ID,Name,Created,Updated) VALUES ('/window/{windowId}/{recordId}','D',0,100,100,'Y','S',541203,'Document/record path on webui frontend.',0,'webui.server.documentPath',TO_TIMESTAMP('2018-04-04 10:34:45','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-04 10:34:45','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-04T10:35:03.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='webui''s frontend server URL. e.g. https://myWebuiServer',Updated=TO_TIMESTAMP('2018-04-04 10:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541202
;

-- 2018-04-04T10:35:48.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_SysConfig SET Value='http://localhost:3000',Updated=TO_TIMESTAMP('2018-04-04 10:35:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541202
-- ;

-- 2018-04-04T10:46:54.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='webui.frontend.url',Updated=TO_TIMESTAMP('2018-04-04 10:46:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541202
;

-- 2018-04-04T10:47:38.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='webui.frontend.documentPath',Updated=TO_TIMESTAMP('2018-04-04 10:47:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541203
;

