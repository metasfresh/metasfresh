-- Value: SAP_GLJournal_OpenItems_Launcher
-- Classname: de.metas.acct.gljournal_sap.select_open_items.SAP_GLJournal_OpenItems_Launcher
-- 2023-07-06T09:48:15.174Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585279,'Y','de.metas.acct.gljournal_sap.select_open_items.SAP_GLJournal_OpenItems_Launcher','N',TO_TIMESTAMP('2023-07-06 12:48:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Clear Open Items','json','N','N','xls','Java',TO_TIMESTAMP('2023-07-06 12:48:13','YYYY-MM-DD HH24:MI:SS'),100,'SAP_GLJournal_OpenItems_Launcher')
;

-- 2023-07-06T09:48:15.182Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585279 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: SAP_GLJournal_OpenItems_Launcher(de.metas.acct.gljournal_sap.select_open_items.SAP_GLJournal_OpenItems_Launcher)
-- Table: SAP_GLJournal
-- EntityType: D
-- 2023-07-06T09:48:41.932Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585279,542275,541392,TO_TIMESTAMP('2023-07-06 12:48:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-07-06 12:48:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

