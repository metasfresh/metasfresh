-- 2017-08-24T09:08:57.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,194,533,TO_TIMESTAMP('2017-08-24 09:08:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',TO_TIMESTAMP('2017-08-24 09:08:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2017-08-24T09:20:13.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,196,532,TO_TIMESTAMP('2017-08-24 09:20:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2017-08-24 09:20:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2017-08-24T09:20:23.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2017-08-24 09:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=194 AND AD_Table_ID=533
;

