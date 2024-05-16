-- 2022-02-23T10:20:02.115Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,540309,540425,541079,TO_TIMESTAMP('2022-02-23 12:20:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y',TO_TIMESTAMP('2022-02-23 12:20:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2022-02-23T10:20:16.259Z
UPDATE AD_Table_Process SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2022-02-23 12:20:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541079
;

-- 2022-02-23T10:20:25.773Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540407
;

