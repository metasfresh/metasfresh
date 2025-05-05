-- Value: SAP_GLJournal_AcctSimulation_Launcher
-- Classname: de.metas.acct.acct_simulation.SAP_GLJournal_AcctSimulation_Launcher
-- 2023-08-04T04:38:54.948Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585303,'Y','de.metas.acct.acct_simulation.SAP_GLJournal_AcctSimulation_Launcher','N',TO_TIMESTAMP('2023-08-04 07:38:54','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Simulate journal','json','N','N','xls','Java',TO_TIMESTAMP('2023-08-04 07:38:54','YYYY-MM-DD HH24:MI:SS'),100,'SAP_GLJournal_AcctSimulation_Launcher')
;

-- 2023-08-04T04:38:54.951Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585303 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: SAP_GLJournal_AcctSimulation_Launcher
-- Classname: de.metas.acct.acct_simulation.SAP_GLJournal_AcctSimulation_Launcher
-- 2023-08-04T04:39:05.203Z
UPDATE AD_Process SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2023-08-04 07:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585303
;

-- Process: SAP_GLJournal_AcctSimulation_Launcher(de.metas.acct.acct_simulation.SAP_GLJournal_AcctSimulation_Launcher)
-- Table: SAP_GLJournal
-- EntityType: de.metas.acct
-- 2023-08-04T04:57:59.564Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585303,542275,541405,TO_TIMESTAMP('2023-08-04 07:57:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y',TO_TIMESTAMP('2023-08-04 07:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

