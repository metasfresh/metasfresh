-- Value: C_Invoice_AcctSimulation_Launcher
-- Classname: de.metas.acct.acct_simulation.C_Invoice_AcctSimulation_Launcher
-- 2023-07-28T06:09:43.516Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585293,'Y','de.metas.acct.acct_simulation.C_Invoice_AcctSimulation_Launcher','N',TO_TIMESTAMP('2023-07-28 09:09:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'GL Journal Simulation','json','N','N','xls','Java',TO_TIMESTAMP('2023-07-28 09:09:43','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_AcctSimulation_Launcher')
;

-- 2023-07-28T06:09:43.520Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585293 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_AcctSimulation_Launcher(de.metas.acct.acct_simulation.C_Invoice_AcctSimulation_Launcher)
-- Table: C_Invoice
-- EntityType: de.metas.acct
-- 2023-07-28T06:10:28.803Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585293,318,541401,TO_TIMESTAMP('2023-07-28 09:10:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y',TO_TIMESTAMP('2023-07-28 09:10:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

