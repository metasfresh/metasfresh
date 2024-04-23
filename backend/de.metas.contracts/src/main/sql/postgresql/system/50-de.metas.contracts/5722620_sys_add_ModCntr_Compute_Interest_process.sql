-- Run mode: SWING_CLIENT

-- Value: ModCntr_Compute_Interest
-- Classname: de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest
-- 2024-04-23T17:47:57.111Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585386,'Y','de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest','N',TO_TIMESTAMP('2024-04-23 20:47:56.849','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Zinsberechnung für modulare Verträge','json','N','N','xls','Java',TO_TIMESTAMP('2024-04-23 20:47:56.849','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Compute_Interest')
;

-- 2024-04-23T17:47:57.120Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585386 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: ModCntr_Compute_Interest
-- Classname: de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest
-- 2024-04-23T17:49:21.124Z
UPDATE AD_Process SET Name='Zins berechnen',Updated=TO_TIMESTAMP('2024-04-23 20:49:21.122','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585386
;

-- 2024-04-23T17:49:21.127Z
UPDATE AD_Process_Trl trl SET Name='Zins berechnen' WHERE AD_Process_ID=585386 AND AD_Language='de_DE'
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- 2024-04-23T17:49:25.779Z
UPDATE AD_Process_Trl SET Name='Zins berechnen',Updated=TO_TIMESTAMP('2024-04-23 20:49:25.779','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585386
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- 2024-04-23T17:49:26.762Z
UPDATE AD_Process_Trl SET Name='Zins berechnen',Updated=TO_TIMESTAMP('2024-04-23 20:49:26.762','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585386
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- 2024-04-23T17:49:31.822Z
UPDATE AD_Process_Trl SET Name='Zins berechnen',Updated=TO_TIMESTAMP('2024-04-23 20:49:31.822','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Process_ID=585386
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- 2024-04-23T17:49:34.008Z
UPDATE AD_Process_Trl SET Name='Compute Interest',Updated=TO_TIMESTAMP('2024-04-23 20:49:34.008','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585386
;

-- 2024-04-23T17:51:23.092Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583086,0,'InterimDate',TO_TIMESTAMP('2024-04-23 20:51:22.947','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Datum Vorfinanzierung','Datum Vorfinanzierung',TO_TIMESTAMP('2024-04-23 20:51:22.947','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-23T17:51:23.093Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583086 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InterimDate
-- 2024-04-23T17:52:04.449Z
UPDATE AD_Element_Trl SET Name='Interim Date', PrintName='Interim Date',Updated=TO_TIMESTAMP('2024-04-23 20:52:04.449','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583086 AND AD_Language='en_US'
;

-- 2024-04-23T17:52:04.482Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583086,'en_US')
;

-- 2024-04-23T17:54:52.950Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583087,0,'BillingDate',TO_TIMESTAMP('2024-04-23 20:54:52.819','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Abrechnungsdatum','Abrechnungsdatum',TO_TIMESTAMP('2024-04-23 20:54:52.819','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-23T17:54:52.952Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583087 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: BillingDate
-- 2024-04-23T17:55:04.397Z
UPDATE AD_Element_Trl SET Name='Billing Date', PrintName='Billing Date',Updated=TO_TIMESTAMP('2024-04-23 20:55:04.397','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583087 AND AD_Language='en_US'
;

-- 2024-04-23T17:55:04.399Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583087,'en_US')
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- ParameterName: InterimDate
-- 2024-04-23T18:40:34.924Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583086,0,585386,542816,15,'InterimDate',TO_TIMESTAMP('2024-04-23 21:40:34.775','YYYY-MM-DD HH24:MI:SS.US'),100,'@#Date@','de.metas.contracts',0,'Y','N','Y','N','Y','N','Datum Vorfinanzierung',10,TO_TIMESTAMP('2024-04-23 21:40:34.775','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-23T18:40:34.925Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542816 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-23T18:40:34.928Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583086)
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- ParameterName: BillingDate
-- 2024-04-23T18:40:54.930Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583087,0,585386,542817,15,'BillingDate',TO_TIMESTAMP('2024-04-23 21:40:54.777','YYYY-MM-DD HH24:MI:SS.US'),100,'@#Date@','de.metas.contracts',0,'Y','N','Y','N','N','N','Abrechnungsdatum',20,TO_TIMESTAMP('2024-04-23 21:40:54.777','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-23T18:40:54.931Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542817 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-04-23T18:40:54.932Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583087)
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- ParameterName: BillingDate
-- 2024-04-23T18:40:59.577Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-23 21:40:59.577','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542817
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- Table: C_Flatrate_Term
-- EntityType: de.metas.contracts
-- 2024-04-23T18:43:09.248Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585386,540320,541481,TO_TIMESTAMP('2024-04-23 21:43:09.095','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-04-23 21:43:09.095','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

