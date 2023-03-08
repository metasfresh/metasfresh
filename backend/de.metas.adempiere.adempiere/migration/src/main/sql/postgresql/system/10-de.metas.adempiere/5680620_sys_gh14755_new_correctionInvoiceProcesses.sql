-- Value: C_Invoice_GenerateCorrectionInvoice
-- Classname: org.adempiere.invoice.process.C_Invoice_GenerateCorrectionInvoice
-- 2023-03-06T17:01:36.898Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585233,'Y','org.adempiere.invoice.process.C_Invoice_GenerateCorrectionInvoice','N',TO_TIMESTAMP('2023-03-06 18:01:36','YYYY-MM-DD HH24:MI:SS'),100,'Erzeugt eine Gutschrift und eine Korrekturrechnung zu einer Rechnung.
In der Gutschrift ist der volle Rechnungsbetrag enthalten.','de.metas.swat','','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Erstelle Korrekturrechnung','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-06 18:01:36','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_GenerateCorrectionInvoice')
;

-- 2023-03-06T17:01:36.901Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585233 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_GenerateCorrectionInvoice(org.adempiere.invoice.process.C_Invoice_GenerateCorrectionInvoice)
-- 2023-03-06T17:01:56.531Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-06 18:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585233
;

-- Process: C_Invoice_GenerateCorrectionInvoice(org.adempiere.invoice.process.C_Invoice_GenerateCorrectionInvoice)
-- 2023-03-06T17:01:59.781Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-06 18:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585233
;

-- Process: C_Invoice_GenerateCorrectionInvoice(org.adempiere.invoice.process.C_Invoice_GenerateCorrectionInvoice)
-- 2023-03-06T17:03:47.885Z
UPDATE AD_Process_Trl SET Description='Generates a Credit Memo and Correction Invoice.
The Credit Memo contains the full amount.', IsTranslated='Y', Name='Generate Correction Invoice',Updated=TO_TIMESTAMP('2023-03-06 18:03:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585233
;

-- Process: C_Invoice_GenerateCorrectionInvoice(org.adempiere.invoice.process.C_Invoice_GenerateCorrectionInvoice)
-- Table: C_Invoice
-- EntityType: D
-- 2023-03-06T17:04:58.601Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585233,318,541369,TO_TIMESTAMP('2023-03-06 18:04:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-03-06 18:04:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Value: C_Invoice_ReissueInvoice
-- Classname: org.adempiere.invoice.process.C_Invoice_ReissueInvoice
-- 2023-03-06T17:07:44.592Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585234,'Y','org.adempiere.invoice.process.C_Invoice_ReissueInvoice','N',TO_TIMESTAMP('2023-03-06 18:07:44','YYYY-MM-DD HH24:MI:SS'),100,'Erzeugt eine Gutschrift und eine neue Rechnung zu einer Rechnung.
In der Gutschrift ist der volle Rechnungsbetrag enthalten.','de.metas.swat','','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Neue Rechnung ausstellen','json','N','N','xls','Java',TO_TIMESTAMP('2023-03-06 18:07:44','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_ReissueInvoice')
;

-- 2023-03-06T17:07:44.593Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585234 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_ReissueInvoice(org.adempiere.invoice.process.C_Invoice_ReissueInvoice)
-- 2023-03-06T17:08:10.695Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-06 18:08:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585234
;

-- Process: C_Invoice_ReissueInvoice(org.adempiere.invoice.process.C_Invoice_ReissueInvoice)
-- 2023-03-06T17:08:11.585Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-06 18:08:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585234
;

-- Process: C_Invoice_ReissueInvoice(org.adempiere.invoice.process.C_Invoice_ReissueInvoice)
-- 2023-03-06T17:10:10.120Z
UPDATE AD_Process_Trl SET Description='Generates a Credit Memo and new Sales Invoice.
The Credit Memo contains the full amount.', IsTranslated='Y', Name='Reissue Invoice',Updated=TO_TIMESTAMP('2023-03-06 18:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585234
;

-- Process: C_Invoice_ReissueInvoice(org.adempiere.invoice.process.C_Invoice_ReissueInvoice)
-- Table: C_Invoice
-- EntityType: D
-- 2023-03-06T17:10:36.790Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585234,318,541370,TO_TIMESTAMP('2023-03-06 18:10:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-03-06 18:10:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

