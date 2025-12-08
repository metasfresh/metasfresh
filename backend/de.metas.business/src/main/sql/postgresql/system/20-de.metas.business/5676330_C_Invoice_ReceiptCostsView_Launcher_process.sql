-- Value: C_Invoice_ReceiptCostsView_Launcher
-- Classname: de.metas.ui.web.invoice.match_receipt_costs.C_Invoice_ReceiptCostsView_Launcher
-- 2023-02-09T08:08:01.267Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585218,'Y','de.metas.ui.web.invoice.match_receipt_costs.C_Invoice_ReceiptCostsView_Launcher','N',TO_TIMESTAMP('2023-02-09 10:08:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Match received costs','json','N','N','xls','Java',TO_TIMESTAMP('2023-02-09 10:08:00','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_ReceiptCostsView_Launcher')
;

-- 2023-02-09T08:08:01.276Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585218 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: C_Invoice_ReceiptCostsView_Launcher
-- Classname: de.metas.ui.web.invoice.match_receipt_costs.C_Invoice_ReceiptCostsView_Launcher
-- 2023-02-09T08:08:05.288Z
UPDATE AD_Process SET AllowProcessReRun='N', IsFormatExcelFile='N', IsUseBPartnerLanguage='N',Updated=TO_TIMESTAMP('2023-02-09 10:08:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585218
;

-- Process: C_Invoice_ReceiptCostsView_Launcher(de.metas.ui.web.invoice.match_receipt_costs.C_Invoice_ReceiptCostsView_Launcher)
-- Table: C_Invoice
-- EntityType: D
-- 2023-02-09T08:08:33.063Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585218,318,541359,TO_TIMESTAMP('2023-02-09 10:08:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-02-09 10:08:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

