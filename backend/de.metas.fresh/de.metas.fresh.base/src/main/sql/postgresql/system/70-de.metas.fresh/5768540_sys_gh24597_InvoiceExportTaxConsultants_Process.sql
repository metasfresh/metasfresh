-- Run mode: SWING_CLIENT

-- Value: InvoiceExportTaxConsultants
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-09-11T14:43:18.309Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585500,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2025-09-11 14:43:17.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','Y','Y','N','Y',0,'Rechnungsexport für Steuerberater','json','N','N','xls','SELECT * FROM de_metas_endcustomer_fresh_reports.Docs_Invoice_Export_Tax_Consultants(''@DateFrom/1900-01-01@''::date , ''@DateTo/9999-12-31@''::date,''@#AD_Language@'')','Excel',TO_TIMESTAMP('2025-09-11 14:43:17.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'InvoiceExportTaxConsultants')
;

-- 2025-09-11T14:43:18.362Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585500 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: InvoiceExportTaxConsultants(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-09-11T14:44:03.787Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Invoice export for tax Consultants',Updated=TO_TIMESTAMP('2025-09-11 14:44:03.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585500
;

-- 2025-09-11T14:44:03.840Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: InvoiceExportTaxConsultants(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateFrom
-- 2025-09-11T14:45:38.672Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,585500,542990,15,'DateFrom',TO_TIMESTAMP('2025-09-11 14:45:38.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','N','N','Datum von',10,TO_TIMESTAMP('2025-09-11 14:45:38.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-11T14:45:38.727Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542990 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: InvoiceExportTaxConsultants(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateTo
-- 2025-09-11T14:46:13.144Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585500,542991,15,'DateTo',TO_TIMESTAMP('2025-09-11 14:46:12.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enddatum eines Abschnittes','U',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','Datum bis',20,TO_TIMESTAMP('2025-09-11 14:46:12.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-11T14:46:13.197Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542991 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-09-11T14:50:18.886Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583961,0,TO_TIMESTAMP('2025-09-11 14:50:18.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Rechnungsexport für Steuerberater','Rechnungsexport für Steuerberater',TO_TIMESTAMP('2025-09-11 14:50:18.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-11T14:50:18.940Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583961 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-09-11T14:50:50.168Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoice export for Tax Consultants', PrintName='Invoice export for Tax Consultants',Updated=TO_TIMESTAMP('2025-09-11 14:50:50.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583961 AND AD_Language='en_US'
;

-- 2025-09-11T14:50:50.221Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-11T14:50:53.152Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583961,'en_US')
;

-- Name: Rechnungsexport für Steuerberater
-- Action Type: P
-- Process: InvoiceExportTaxConsultants(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-09-11T14:51:08.558Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,583961,542244,0,585500,TO_TIMESTAMP('2025-09-11 14:51:07.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','InvoiceExportTaxConsultants','Y','N','N','N','N','Rechnungsexport für Steuerberater',TO_TIMESTAMP('2025-09-11 14:51:07.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-11T14:51:08.611Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542244 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-11T14:51:08.660Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542244, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542244)
;

-- 2025-09-11T14:51:08.717Z
/* DDL */  select update_menu_translation_from_ad_element(583961)
;

-- Reordering children of `Reports`
-- Node name: `Next vs AfterNext Pricelist Comparison`
-- 2025-09-11T14:51:12.196Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541956 AND AD_Tree_ID=10
;

-- Node name: `Current vs Previous Pricelist Comparison Report`
-- 2025-09-11T14:51:12.250Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541452 AND AD_Tree_ID=10
;

-- Node name: `Rechnungsexport für Steuerberater`
-- 2025-09-11T14:51:12.300Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542244 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Rechnungsexport für Steuerberater`
-- 2025-09-11T14:51:18.755Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000062, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542244 AND AD_Tree_ID=10
;

