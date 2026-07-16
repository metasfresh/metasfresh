-- Run mode: SWING_CLIENT


-- 2026-03-26T18:08:11.580Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584702,0,'HU_MHD_Report',TO_TIMESTAMP('2026-03-26 18:08:10.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','MHD Liste','MHD Liste',TO_TIMESTAMP('2026-03-26 18:08:10.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T18:08:11.678Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584702 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Value: HU_MHD_Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-03-26T18:14:57.518Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585603,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2026-03-26 18:14:55.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Exportiert alle aktiven Handling Units mit Typ, Artikel, Charge, Mindesthaltbarkeitsdatum, Lagerplatz und Lieferantenrückverfolgung nach Excel.','D','Exportiert alle aktiven Handling Units mit Typ, Artikel, Charge, Mindesthaltbarkeitsdatum, Lagerplatz und Lieferantenrückverfolgung nach Excel.','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'MHD Liste','json','N','N','xls','SELECT * FROM de_metas_endcustomer_fresh_reports.HU_MHD_V;','Excel',TO_TIMESTAMP('2026-03-26 18:14:55.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HU_MHD_Report')
;

-- 2026-03-26T18:14:57.624Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585603 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: HU_MHD_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-03-26T18:16:21.979Z
UPDATE AD_Process_Trl SET Description='Exports all active handling units with their type, product, lot, best-before date, storage location, and supplier traceability data to Excel.', Help='Exports all active handling units with their type, product, lot, best-before date, storage location, and supplier traceability data to Excel.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-26 18:16:21.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585603
;

-- 2026-03-26T18:16:22.267Z
UPDATE AD_Process base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Name: MHD Liste
-- Action Type: P
-- Process: HU_MHD_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-03-26T18:20:55.887Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,584702,542311,0,585603,TO_TIMESTAMP('2026-03-26 18:20:52.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EE01','HU_MHD_Report','Y','N','N','N','N','MHD Liste',TO_TIMESTAMP('2026-03-26 18:20:52.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T18:20:56.141Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542311 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-03-26T18:20:56.399Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542311, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542311)
;

-- 2026-03-26T18:20:56.782Z
/* DDL */  select update_menu_translation_from_ad_element(584702)
;

-- Reordering children of `Reports`
-- Node name: `Inventory Valuation`
-- 2026-03-26T18:21:24.852Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000061, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540890 AND AD_Tree_ID=10
;

-- Node name: `Inventory Valuation (Excel)`
-- 2026-03-26T18:21:25.447Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000061, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541511 AND AD_Tree_ID=10
;

-- Node name: `MHD Liste`
-- 2026-03-26T18:21:25.545Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000061, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542311 AND AD_Tree_ID=10
;

-- Name: MHD Liste
-- Action Type: R
-- Report: HU_MHD_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-03-26T18:21:38.234Z
UPDATE AD_Menu SET Action='R',Updated=TO_TIMESTAMP('2026-03-26 18:21:38.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542311
;

-- 2026-03-26T18:29:43.814Z
UPDATE AD_Element SET ColumnName='PackingInstruction',Updated=TO_TIMESTAMP('2026-03-26 18:29:43.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=1001546
;

-- 2026-03-26T18:29:43.904Z
UPDATE AD_Column SET ColumnName='PackingInstruction' WHERE AD_Element_ID=1001546
;

-- 2026-03-26T18:29:44.004Z
UPDATE AD_Process_Para SET ColumnName='PackingInstruction' WHERE AD_Element_ID=1001546
;

-- 2026-03-26T18:29:44.362Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001546,'de_DE')
;

-- 2026-03-26T18:38:55.736Z
UPDATE AD_Element SET ColumnName='Vendor',Updated=TO_TIMESTAMP('2026-03-26 18:38:55.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=573565
;

-- 2026-03-26T18:38:55.826Z
UPDATE AD_Column SET ColumnName='Vendor' WHERE AD_Element_ID=573565
;

-- 2026-03-26T18:38:55.922Z
UPDATE AD_Process_Para SET ColumnName='Vendor' WHERE AD_Element_ID=573565
;

-- 2026-03-26T18:38:56.368Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573565,'de_DE')
;

-- 2026-03-26T18:40:26.178Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584703,0,'Origin',TO_TIMESTAMP('2026-03-26 18:40:25.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Herkunft','Herkunft',TO_TIMESTAMP('2026-03-26 18:40:25.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T18:40:26.268Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584703 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Origin
-- 2026-03-26T18:43:17.560Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Origin', PrintName='Origin',Updated=TO_TIMESTAMP('2026-03-26 18:43:17.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584703 AND AD_Language='en_US'
;

-- 2026-03-26T18:43:17.883Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T18:44:26.300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584703,'en_US')
;

-- 2026-03-26T18:46:00.602Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584704,0,'ExternalProductCode',TO_TIMESTAMP('2026-03-26 18:45:48.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Externe Artikelnummer','Externe Artikelnummer',TO_TIMESTAMP('2026-03-26 18:45:48.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T18:46:00.659Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584704 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ExternalProductCode
-- 2026-03-26T18:47:09.234Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='External Article No.', PrintName='External Article No.',Updated=TO_TIMESTAMP('2026-03-26 18:47:09.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584704 AND AD_Language='en_US'
;

-- 2026-03-26T18:47:09.289Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T18:47:14.015Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584704,'en_US')
;

-- 2026-03-26T18:50:39.433Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584705,0,'LU_Quantity',TO_TIMESTAMP('2026-03-26 18:50:38.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','LU-Menge','LU-Menge',TO_TIMESTAMP('2026-03-26 18:50:38.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T18:50:39.502Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584705 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LU_Quantity
-- 2026-03-26T18:52:08.211Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='LU Quantity', PrintName='LU Quantity',Updated=TO_TIMESTAMP('2026-03-26 18:52:08.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584705 AND AD_Language='en_US'
;

-- 2026-03-26T18:52:08.268Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T18:52:11.824Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584705,'en_US')
;

-- 2026-03-26T18:52:40.028Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584706,0,'TU_CU_Quantity',TO_TIMESTAMP('2026-03-26 18:52:39.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','TU/VE-Menge','TU/VE-Menge',TO_TIMESTAMP('2026-03-26 18:52:39.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T18:52:40.094Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584706 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TU_CU_Quantity
-- 2026-03-26T18:53:06.093Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='TU / CU Quantity', PrintName='TU / CU Quantity',Updated=TO_TIMESTAMP('2026-03-26 18:53:06.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584706 AND AD_Language='en_US'
;

-- 2026-03-26T18:53:06.151Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T18:53:09.578Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584706,'en_US')
;

-- 2026-03-26T18:54:24.493Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584707,0,'StorageLocation',TO_TIMESTAMP('2026-03-26 18:54:24.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Lagerplatz','Lagerplatz',TO_TIMESTAMP('2026-03-26 18:54:24.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T18:54:24.563Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584707 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: StorageLocation
-- 2026-03-26T18:54:50.413Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Storage Location', PrintName='Storage Location',Updated=TO_TIMESTAMP('2026-03-26 18:54:50.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584707 AND AD_Language='en_US'
;

-- 2026-03-26T18:54:50.493Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T18:54:54.467Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584707,'en_US')
;

INSERT INTO m_attribute (m_attribute_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, ismandatory, isinstanceattribute, m_attributesearch_id, attributevaluetype, ad_javaclass_id, ispricingrelevant, value, c_uom_id, valuemax, valuemin, isstoragerelevant, ad_val_rule_id, isattrdocumentrelevant, isreadonlyvalues, istransferwhennull, ishighvolume,
                         descriptionpattern, isalwaysupdateable, isprintedindocument)
VALUES (1000029, 1000000, 0, 'Y', '2022-01-27 19:45:36.000000 +01:00', 100, '2022-01-27 19:45:36.000000 +01:00', 100, 'Lieferanten Lot-Nummer', NULL, 'N', 'Y', NULL, 'S', NULL, 'N', 'Lieferanten Lot-Nummer', NULL, 0, 0, 'N', NULL, 'N', 'N', 'N', 'N', NULL, 'N', 'Y')
ON CONFLICT (m_attribute_id) DO NOTHING
;
-- 2026-03-26T19:04:31.660Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584708,0,'SupplierLotNumber',TO_TIMESTAMP('2026-03-26 19:04:31.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Lieferanten Lot-Nummer','Lieferanten Lot-Nummer',TO_TIMESTAMP('2026-03-26 19:04:31.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T19:04:31.718Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584708 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SupplierLotNumber
-- 2026-03-26T19:05:05.236Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Supplier Lot Number', PrintName='Supplier Lot Number',Updated=TO_TIMESTAMP('2026-03-26 19:05:05.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584708 AND AD_Language='en_US'
;

-- 2026-03-26T19:05:05.310Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T19:05:10.684Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584708,'en_US')
;

-- 2026-03-26T19:15:30.708Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584709,0,'SourceReleaseStatus',TO_TIMESTAMP('2026-03-26 19:15:30.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Quell-Freigabestatus','Quell-Freigabestatus',TO_TIMESTAMP('2026-03-26 19:15:30.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T19:15:30.766Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584709 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SourceReleaseStatus
-- 2026-03-26T19:16:13.484Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Source Release Status', PrintName='Source Release Status',Updated=TO_TIMESTAMP('2026-03-26 19:16:13.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584709 AND AD_Language='en_US'
;

-- 2026-03-26T19:16:13.541Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T19:16:22.516Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584709,'en_US')
;

