-- Value: C_BPartner_InterimContract_GenerateInterimInvoice
-- Classname: de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice
-- 2023-09-04T11:37:58.059385600Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585312,'Y','de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice','N',TO_TIMESTAMP('2023-09-04 14:37:57.766','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Generate Interim Invoices','json','N','N','xls','Java',TO_TIMESTAMP('2023-09-04 14:37:57.766','YYYY-MM-DD HH24:MI:SS.US'),100,'C_BPartner_InterimContract_GenerateInterimInvoice')
;

-- 2023-09-04T11:37:58.069876300Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585312 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- 2023-09-04T11:42:52.090230600Z
UPDATE AD_Process_Trl SET Name='Rechnungen für Zwischenzahlungen erstellen',Updated=TO_TIMESTAMP('2023-09-04 14:42:52.089','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585312
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- 2023-09-04T11:42:54.661582100Z
UPDATE AD_Process_Trl SET Name='Rechnungen für Zwischenzahlungen erstellen',Updated=TO_TIMESTAMP('2023-09-04 14:42:54.661','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585312
;

-- 2023-09-04T11:42:54.662628400Z
UPDATE AD_Process SET Name='Rechnungen für Zwischenzahlungen erstellen' WHERE AD_Process_ID=585312
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: OnlyApprovedForInvoicing
-- 2023-09-04T12:03:39.360420400Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585312,542690,20,'OnlyApprovedForInvoicing',TO_TIMESTAMP('2023-09-04 15:03:39.232','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,'Wenn ausgewählt, werden nur die zur Fakturierung freigegebenen Rechnungskandidaten fakturiert.','Y','N','Y','N','Y','N','Nur für Fakturierung freigegeben',10,TO_TIMESTAMP('2023-09-04 15:03:39.232','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:03:39.361474900Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542690 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: OnlyApprovedForInvoicing
-- 2023-09-04T12:04:35.860223100Z
UPDATE AD_Process_Para SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2023-09-04 15:04:35.86','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542690
;

-- Value: C_BPartner_InterimContract_GenerateInterimInvoice
-- Classname: de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice
-- 2023-09-04T12:04:46.194891Z
UPDATE AD_Process SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2023-09-04 15:04:46.192','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585312
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: IgnoreInvoiceSchedule
-- 2023-09-04T12:06:05.040107700Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541910,0,585312,542691,20,'IgnoreInvoiceSchedule',TO_TIMESTAMP('2023-09-04 15:06:04.912','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,'Y','N','Y','N','Y','N','Rechnungs-Terminplan ingorieren',20,TO_TIMESTAMP('2023-09-04 15:06:04.912','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:06:05.041148800Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542691 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-04T12:06:05.073203800Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(541910) 
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: IsUpdateLocationAndContactForInvoice
-- 2023-09-04T12:06:49.666023300Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577153,0,585312,542692,20,'IsUpdateLocationAndContactForInvoice',TO_TIMESTAMP('2023-09-04 15:06:49.521','YYYY-MM-DD HH24:MI:SS.US'),100,'@SQL=SELECT get_sysconfig_value_forOrg(''C_Invoice_Send_To_Current_BillTo_Address_And_User'', ''N'', @AD_Org_ID/-1@)',' ','de.metas.invoicecandidate',0,'Wenn dieser Parameter aktiviert ist, erhalten die zu erstellenden Rechnungen die aktuellen Benutzer und Standorte ihrer Geschäftspartner, unabhängig von den Werten in Bill_Location und Bill_User, die in den eingereihten Rechnungskandidaten eingestellt sind. Die Rechnungskandidaten selbst werden nicht verändert. Dennoch werden die Werte von Bill_Location_Override und Bill_User_Override eingehalten, sofern sie in den Rechnungskandidaten gesetzt sind.','Y','N','Y','N','Y','N','Rechnungsadresse und -kontakt aktualisieren',30,TO_TIMESTAMP('2023-09-04 15:06:49.521','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:06:49.666549600Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542692 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-04T12:06:49.667601500Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(577153) 
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: IsCompleteInvoices
-- 2023-09-04T12:07:14.582477800Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581600,0,585312,542693,20,'IsCompleteInvoices',TO_TIMESTAMP('2023-09-04 15:07:14.46','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Legt fest, ob die neu erstellten Rechungen fertig gestellt oder nur vorbereitet werden sollen.','de.metas.invoicecandidate',0,'Y','N','Y','N','Y','N','Rechnungen fertigstellen',40,TO_TIMESTAMP('2023-09-04 15:07:14.46','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:07:14.583540700Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542693 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-04T12:07:14.584092700Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(581600) 
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: DateInvoiced
-- 2023-09-04T12:07:42.078059300Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,267,0,585312,542694,15,'DateInvoiced',TO_TIMESTAMP('2023-09-04 15:07:41.961','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum auf der Rechnung','de.metas.invoicecandidate',0,'"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.','Y','N','Y','N','N','N','Rechnungsdatum',50,TO_TIMESTAMP('2023-09-04 15:07:41.961','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:07:42.079631200Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542694 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-04T12:07:42.080678800Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(267) 
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: DateAcct
-- 2023-09-04T12:08:03.652534200Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,263,0,585312,542695,15,'DateAcct',TO_TIMESTAMP('2023-09-04 15:08:03.51','YYYY-MM-DD HH24:MI:SS.US'),100,'Accounting Date','de.metas.invoicecandidate',0,'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','N','Buchungsdatum',60,TO_TIMESTAMP('2023-09-04 15:08:03.51','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:08:03.653586700Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542695 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-04T12:08:03.654623300Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(263) 
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: POReference
-- 2023-09-04T12:08:31.405939300Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,952,0,585312,542696,10,'POReference',TO_TIMESTAMP('2023-09-04 15:08:31.263','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','de.metas.invoicecandidate',0,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','Referenz',70,TO_TIMESTAMP('2023-09-04 15:08:31.263','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:08:31.406465100Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542696 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-04T12:08:31.407520600Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(952) 
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: Check_NetAmtToInvoice
-- 2023-09-04T12:09:05.548454100Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542769,0,585312,542697,12,'Check_NetAmtToInvoice',TO_TIMESTAMP('2023-09-04 15:09:05.424','YYYY-MM-DD HH24:MI:SS.US'),100,'0','1=0','de.metas.invoicecandidate',0,'Y','N','Y','N','N','N','Rechungsnetto zur Prüfung',80,TO_TIMESTAMP('2023-09-04 15:09:05.424','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:09:05.549500900Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542697 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-04T12:09:05.550545200Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(542769) 
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- ParameterName: OverrideDueDate
-- 2023-09-04T12:09:26.617846Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582059,0,585312,542698,15,'OverrideDueDate',TO_TIMESTAMP('2023-09-04 15:09:26.497','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate',0,'Y','N','Y','N','N','N','Fälligkeitsdatum abw.',90,TO_TIMESTAMP('2023-09-04 15:09:26.497','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-04T12:09:26.618899600Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542698 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-09-04T12:09:26.619943800Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582059) 
;

-- Process: C_BPartner_InterimContract_GenerateInterimInvoice(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_GenerateInterimInvoice)
-- Table: C_BPartner_InterimContract
-- EntityType: de.metas.invoicecandidate
-- 2023-09-04T12:28:46.018529Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585312,542357,541413,TO_TIMESTAMP('2023-09-04 15:28:45.866','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y',TO_TIMESTAMP('2023-09-04 15:28:45.866','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Column: C_BPartner_InterimContract.Harvesting_Year_ID
-- 2023-09-04T13:14:52.625781300Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-04 16:14:52.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587240
;

UPDATE c_bpartner_interimContract ic
SET harvesting_year_id = (SELECT MAX(c_year_id) FROM c_year y WHERE ic.c_harvesting_calendar_id = y.c_calendar_id),
    updated=TO_TIMESTAMP('2023-09-04 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby=100
WHERE harvesting_year_id IS NULL
;

-- 2023-09-04T13:20:53.338322100Z
INSERT INTO t_alter_column values('c_bpartner_interimcontract','Harvesting_Year_ID','NUMERIC(10)',null,null)
;

-- 2023-09-04T13:20:53.340904100Z
INSERT INTO t_alter_column values('c_bpartner_interimcontract','Harvesting_Year_ID',null,'NOT NULL',null)
;

