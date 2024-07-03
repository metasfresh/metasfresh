-- Value: Produktionsauftrag zurücksetzen
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2024-07-02T14:52:16.758Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585403,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2024-07-02 17:52:15','YYYY-MM-DD HH24:MI:SS'),100,'Update PP_Order.PlanningStatus=''P''','D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Produktionsauftrag zurücksetzen','json','N','N','xls','UPDATE pp_order SET planningstatus = ''P'' WHERE pp_order_id = @PP_Order_ID/NULL@;','SQL',TO_TIMESTAMP('2024-07-02 17:52:15','YYYY-MM-DD HH24:MI:SS'),100,'Produktionsauftrag zurücksetzen')
;

-- 2024-07-02T14:52:16.784Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585403 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Produktionsauftrag zurücksetzen(de.metas.process.ExecuteUpdateSQL)
-- Table: PP_Order
-- EntityType: D
-- 2024-07-02T14:53:52.881Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585403,53027,541499,TO_TIMESTAMP('2024-07-02 17:53:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2024-07-02 17:53:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- Process: Produktionsauftrag zurücksetzen(de.metas.process.ExecuteUpdateSQL)
-- 2024-07-02T19:36:32.878Z
UPDATE AD_Process_Trl SET Name='Uncomplete Manufacturing Order',Updated=TO_TIMESTAMP('2024-07-02 22:36:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585403
;

