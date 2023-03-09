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

-- Value: Event_DocumentGenerated
-- 2023-03-09T07:37:11.721Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545254,0,TO_TIMESTAMP('2023-03-09 08:37:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','{0} {1} wurde erstellt','I',TO_TIMESTAMP('2023-03-09 08:37:11','YYYY-MM-DD HH24:MI:SS'),100,'Event_DocumentGenerated')
;

-- 2023-03-09T07:37:11.723Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545254 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: Event_DocumentGenerated
-- 2023-03-09T07:37:24.586Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-09 08:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545254
;

-- Value: Event_DocumentGenerated
-- 2023-03-09T07:37:25.696Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-09 08:37:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545254
;

-- Value: Event_DocumentGenerated
-- 2023-03-09T07:37:37.432Z
UPDATE AD_Message_Trl SET MsgText='{0} {1} was generated',Updated=TO_TIMESTAMP('2023-03-09 08:37:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545254
;

-- 2023-03-09T09:05:00.570Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582125,0,'IsFixedInvoice',TO_TIMESTAMP('2023-03-09 10:05:00','YYYY-MM-DD HH24:MI:SS'),100,'Invoices with this set to Y will not have the docActions RE, RC and VO available','D','Y','IsFixedInvoice','IsFixedInvoice',TO_TIMESTAMP('2023-03-09 10:05:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-09T09:05:00.574Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582125 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Invoice.IsFixedInvoice
-- 2023-03-09T09:06:04.896Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,586289,582125,0,20,318,'IsFixedInvoice',TO_TIMESTAMP('2023-03-09 10:06:04','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Invoices with this set to Y will not have the docActions RE, RC and VO available','D',1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'IsFixedInvoice',0,0,'Invoices with this set to Y will not have the docActions RE, RC and VO available',TO_TIMESTAMP('2023-03-09 10:06:04','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2023-03-09T09:06:04.901Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586289 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-09T09:06:04.940Z
/* DDL */  select update_Column_Translation_From_AD_Element(582125)
;

-- 2023-03-09T09:06:22.610Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN IsFixedInvoice CHAR(1) DEFAULT ''N'' CHECK (IsFixedInvoice IN (''Y'',''N'')) NOT NULL')
;

