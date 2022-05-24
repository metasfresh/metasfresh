-- 2021-09-21T07:27:01.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541410,'S',TO_TIMESTAMP('2021-09-21 09:27:00','YYYY-MM-DD HH24:MI:SS'),100,'Max. mumber of milliseconds the debouncer waits for more events before sending the ones it collected down the websocket','de.metas.ui.web','Y','webui.WebsocketSender.debouncer.delayInMillis',TO_TIMESTAMP('2021-09-21 09:27:00','YYYY-MM-DD HH24:MI:SS'),100,'200')
;

-- 2021-09-21T07:27:10.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Max. mumber of milliseconds the debouncer waits for more events before sending the ones it collected down the websocket.',Updated=TO_TIMESTAMP('2021-09-21 09:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541410
;

-- 2021-09-21T07:28:25.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541411,'S',TO_TIMESTAMP('2021-09-21 09:28:24','YYYY-MM-DD HH24:MI:SS'),100,'Max. mumber of of events while the debouncer waits for more, before sending the ones it collected down the websocket.','de.metas.ui.web','Y','webui.WebsocketSender.debouncer.bufferMaxSize',TO_TIMESTAMP('2021-09-21 09:28:24','YYYY-MM-DD HH24:MI:SS'),100,'500')
;

