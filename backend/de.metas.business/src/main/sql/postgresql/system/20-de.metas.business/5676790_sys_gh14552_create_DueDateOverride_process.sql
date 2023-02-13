-- 2023-02-13T10:55:31.040Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582058,0,TO_TIMESTAMP('2023-02-13 12:55:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Neues Fälligkeitsdatum einstellen','Neues Fälligkeitsdatum einstellen',TO_TIMESTAMP('2023-02-13 12:55:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T10:55:31.045Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582058 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-02-13T10:55:48.414Z
UPDATE AD_Element_Trl SET Name='Set new due date', PrintName='Set new due date',Updated=TO_TIMESTAMP('2023-02-13 12:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582058 AND AD_Language='en_US'
;

-- 2023-02-13T10:55:48.440Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582058,'en_US') 
;

-- Value: Invoices_already_paid
-- 2023-02-13T11:02:06.588Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545236,0,TO_TIMESTAMP('2023-02-13 13:02:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Folgende Rechnungen sind bereits bezahlt worden: {}','E',TO_TIMESTAMP('2023-02-13 13:02:06','YYYY-MM-DD HH24:MI:SS'),100,'Invoices_already_paid')
;

-- 2023-02-13T11:02:06.589Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545236 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: Invoices_already_paid
-- 2023-02-13T11:02:22.642Z
UPDATE AD_Message_Trl SET MsgText='Following invoices have already been paid: {}',Updated=TO_TIMESTAMP('2023-02-13 13:02:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545236
;

-- Value: C_Invoice_OverrideDueDate
-- Classname: de.metas.invoice.process.C_Invoice_OverrideDueDate
-- 2023-02-13T11:03:21.294Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585221,'Y','de.metas.invoice.process.C_Invoice_OverrideDueDate','N',TO_TIMESTAMP('2023-02-13 13:03:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Set new due date','json','N','N','xls','Java',TO_TIMESTAMP('2023-02-13 13:03:21','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_OverrideDueDate')
;

-- 2023-02-13T11:03:21.296Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585221 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_OverrideDueDate(de.metas.invoice.process.C_Invoice_OverrideDueDate)
-- 2023-02-13T11:03:31.581Z
UPDATE AD_Process_Trl SET Name='Neues Fälligkeitsdatum einstellen',Updated=TO_TIMESTAMP('2023-02-13 13:03:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585221
;

-- Process: C_Invoice_OverrideDueDate(de.metas.invoice.process.C_Invoice_OverrideDueDate)
-- 2023-02-13T11:03:32.989Z
UPDATE AD_Process_Trl SET Name='Neues Fälligkeitsdatum einstellen',Updated=TO_TIMESTAMP('2023-02-13 13:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585221
;

-- 2023-02-13T11:03:32.990Z
UPDATE AD_Process SET Name='Neues Fälligkeitsdatum einstellen' WHERE AD_Process_ID=585221
;

-- Process: C_Invoice_OverrideDueDate(de.metas.invoice.process.C_Invoice_OverrideDueDate)
-- 2023-02-13T11:03:34.427Z
UPDATE AD_Process_Trl SET Name='Neues Fälligkeitsdatum einstellen',Updated=TO_TIMESTAMP('2023-02-13 13:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585221
;

-- Process: C_Invoice_OverrideDueDate(de.metas.invoice.process.C_Invoice_OverrideDueDate)
-- 2023-02-13T11:03:36.651Z
UPDATE AD_Process_Trl SET Name='Neues Fälligkeitsdatum einstellen',Updated=TO_TIMESTAMP('2023-02-13 13:03:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585221
;

-- 2023-02-13T11:04:21.303Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582059,0,'OverrideDueDate',TO_TIMESTAMP('2023-02-13 13:04:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fälligkeitsdatum abw.','Fälligkeitsdatum abw.',TO_TIMESTAMP('2023-02-13 13:04:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T11:04:21.304Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582059 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: OverrideDueDate
-- 2023-02-13T11:04:30.245Z
UPDATE AD_Element_Trl SET Name='Override Due Date', PrintName='Override Due Date',Updated=TO_TIMESTAMP('2023-02-13 13:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582059 AND AD_Language='en_US'
;

-- 2023-02-13T11:04:30.247Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582059,'en_US') 
;

-- Process: C_Invoice_OverrideDueDate(de.metas.invoice.process.C_Invoice_OverrideDueDate)
-- ParameterName: OverrideDueDate
-- 2023-02-13T11:05:05.407Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582059,0,585221,542539,15,'OverrideDueDate',TO_TIMESTAMP('2023-02-13 13:05:05','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Fälligkeitsdatum abw.',10,TO_TIMESTAMP('2023-02-13 13:05:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T11:05:05.409Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542539 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

/* DDL */ SELECT update_Process_Para_Translation_From_AD_Element(582059, 'en_US');

