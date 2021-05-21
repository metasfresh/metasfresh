-- 2021-05-21T11:55:10.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.bpartner.process.C_BPartner_AddRemoveCreditStopStatus', Value='C_BPartner_AddRemoveCreditStopStatus',Updated=TO_TIMESTAMP('2021-05-21 14:55:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540938
;

-- 2021-05-21T11:55:38.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540621
;

-- 2021-05-21T12:00:52.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,540938,291,540934,TO_TIMESTAMP('2021-05-21 15:00:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-05-21 15:00:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

