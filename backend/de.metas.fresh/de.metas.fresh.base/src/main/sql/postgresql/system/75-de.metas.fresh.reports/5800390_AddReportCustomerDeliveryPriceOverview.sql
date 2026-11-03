-- Value: customer_delivery_price_report
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/customerdeliverypriceoverview/report.jasper
-- 2026-05-06T16:43:30.847Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585614,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2026-05-06 16:43:29.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/reports/customerdeliverypriceoverview/report.jasper','',0,'Verkaufspreis-Auskunft','json','N','Y','JasperReportsSQL',TO_TIMESTAMP('2026-05-06 16:43:29.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'customer_delivery_price_report')
;

-- 2026-05-06T16:43:30.861Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585614 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2026-05-06T16:44:19.035Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584834,0,'Customer_delivery_price_report',TO_TIMESTAMP('2026-05-06 16:44:18.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Verkaufspreis-Auskunft','Verkaufspreis-Auskunft',TO_TIMESTAMP('2026-05-06 16:44:18.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-06T16:44:19.043Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584834 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Customer_delivery_price_report
-- 2026-05-06T16:44:22.070Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-06 16:44:22.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584834 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-06T16:44:22.074Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584834,'de_CH')
;


-- 2026-05-06T16:44:22.074Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584834,'de_DE')
;

-- Element: Customer_delivery_price_report
-- 2026-05-06T16:44:40.472Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Current Sales Price Overview', PrintName='Current Sales Price Overview',Updated=TO_TIMESTAMP('2026-05-06 16:44:40.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584834 AND AD_Language='en_US'
;

-- 2026-05-06T16:44:40.473Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-06T16:44:40.683Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584834,'en_US')
;

-- Name: Verkaufspreis-Auskunft
-- Action Type: R
-- Report: customer_delivery_price_report(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-05-06T16:45:39.369Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,584834,542319,0,585614,TO_TIMESTAMP('2026-05-06 16:45:39.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','customer_delivery_price_report','Y','N','N','Y','N','Verkaufspreis-Auskunft',TO_TIMESTAMP('2026-05-06 16:45:39.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-06T16:45:39.374Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542319 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-05-06T16:45:39.376Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542319, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542319)
;

-- 2026-05-06T16:45:39.378Z
/* DDL */  select update_menu_translation_from_ad_element(584834)
;

-- Reordering children of `Reports`
-- Node name: `Invoice export for Tax Consultants (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-06T16:45:39.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000062, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542244 AND AD_Tree_ID=10
;

-- Node name: `Verkaufspreis-Auskunft`
-- 2026-05-06T16:45:39.949Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000062, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542319 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Article Statistics (@PREFIX@de/metas/reports/article_statistics/report.jasper)`
-- 2026-05-06T16:45:45.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- Node name: `Artikelstatistik (Excel)`
-- 2026-05-06T16:45:45.137Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- Node name: `Promotion Code Evaluation (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-05-06T16:45:45.138Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542303 AND AD_Tree_ID=10
;

-- Node name: `Reclamations Report (@PREFIX@de/metas/reports/request_report/report.jasper)`
-- 2026-05-06T16:45:45.139Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- Node name: `ADR Evaluation (@PREFIX@de/metas/reports/umsatzreport_adr_bpartner/report.jasper)`
-- 2026-05-06T16:45:45.140Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report (@PREFIX@de/metas/reports/umsatzreport/report.jasper)`
-- 2026-05-06T16:45:45.140Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- Node name: `Revenue Week Report (@PREFIX@de/metas/reports/umsatzreport_week/report.jasper)`
-- 2026-05-06T16:45:45.141Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Revenue (@PREFIX@de/metas/reports/umsatzreport_bpartner/report.jasper)`
-- 2026-05-06T16:45:45.142Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Week Revenue (@PREFIX@de/metas/reports/umsatzreport_bpartner_week/report.jasper)`
-- 2026-05-06T16:45:45.143Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Week Revenue with Quantity (@PREFIX@de/metas/reports/umsatzreport_bpartner_week_with_qty/report.jasper)`
-- 2026-05-06T16:45:45.144Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Revenue with Quantity (@PREFIX@de/metas/reports/umsatzreport_bpartner_with_qty/report.jasper)`
-- 2026-05-06T16:45:45.145Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Quantity (@PREFIX@de/metas/reports/qty_statistics/report.jasper)`
-- 2026-05-06T16:45:45.146Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Quantities (@PREFIX@de/metas/reports/qty_statistics_kg/report.jasper)`
-- 2026-05-06T16:45:45.146Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Week Quantity (@PREFIX@de/metas/reports/qty_statistics_week/report.jasper)`
-- 2026-05-06T16:45:45.147Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Week Quantities (@PREFIX@de/metas/reports/qty_statistics_kg_week/report.jasper)`
-- 2026-05-06T16:45:45.148Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- Node name: `Sales Trace (@PREFIX@de/metas/reports/sales_trace/report.xls)`
-- 2026-05-06T16:45:45.148Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540957 AND AD_Tree_ID=10
;

-- Node name: `Sales by Product and Customer Report (Jasper) (@PREFIX@de/metas/reports/goodssold/report.jasper)`
-- 2026-05-06T16:45:45.149Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542317 AND AD_Tree_ID=10
;

-- Node name: `Verkaufspreis-Auskunft`
-- 2026-05-06T16:45:45.150Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542319 AND AD_Tree_ID=10
;

-- Node name: `Sales Price List to Date (@PREFIX@de/metas/reports/pricelist_todate/report.jasper)`
-- 2026-05-06T16:45:45.151Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542126 AND AD_Tree_ID=10
;



-- Run mode: SWING_CLIENT

-- 2026-05-07T10:06:39.531Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540779,'C_BPartner.IsActive=''Y'' AND C_BPartner.IsSummary=''N'' AND C_BPartner.IsCustomer=''Y''',TO_TIMESTAMP('2026-05-07 10:06:39.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BPartners which are customers','D','Y','C_BPartner_Customer','S',TO_TIMESTAMP('2026-05-07 10:06:39.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;


-- 2026-05-07T10:03:22.906Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577762,0,585614,543191,15,'Date',TO_TIMESTAMP('2026-05-07 10:03:22.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',0,'Y','N','Y','N','Y','N','Datum',10,TO_TIMESTAMP('2026-05-07 10:03:22.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-07T10:03:22.916Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543191 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-05-07T10:04:02.875Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,585614,543192,18,540779,'C_BPartner_ID',TO_TIMESTAMP('2026-05-07 10:04:02.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',20,TO_TIMESTAMP('2026-05-07 10:04:02.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-07T10:04:02.879Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543192 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


-- 2026-05-07T10:08:51.265Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2026-05-07 10:08:51.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543192
;



-- Run mode: SWING_CLIENT

-- 2026-05-07T12:08:34.355Z
UPDATE AD_Process SET JasperReport_Tabular='@PREFIX@de/metas/reports/customerdeliverypriceoverview/report_TabularView.jasper',Updated=TO_TIMESTAMP('2026-05-07 12:08:34.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585614
;

-- 2026-05-07T12:09:33.075Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577492,0,585614,543193,17,541097,'ReportFormat',TO_TIMESTAMP('2026-05-07 12:09:32.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@SQL=SELECT getDefaultValue_ProcessPara(p_AD_Process_Para_ID => 541681)','U',0,'Y','N','Y','N','Y','N','Report format',30,TO_TIMESTAMP('2026-05-07 12:09:32.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-07T12:09:33.081Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543193 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


-- 2026-05-07T12:38:34.324Z
UPDATE AD_Process_Para SET DefaultValue='PDF',Updated=TO_TIMESTAMP('2026-05-07 12:38:34.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543193
;


-- 2026-05-07T12:41:30.713Z
UPDATE AD_Process_Trl SET Description='This report provides a consolidated view of processed shipment schedules for a specific business partner, enriched with the current raw standard sales price for each product.
It specifically retrieves the base list price from the customer''s assigned pricing system. Please note:
* No Discounts: Prices shown are standard list prices; customer-specific discounts or promotional deductions are not applied.
* No ASI/PI Matching: The lookup ignores Attribute Set Instances (ASI) and Handling Unit/Package Item (PI) specific pricing.
* Standard UOM only: It returns the base standard price for the product''s default unit of measure.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 12:41:30.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585614
;

-- 2026-05-07T12:41:30.715Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T12:42:15.259Z
UPDATE AD_Process_Trl SET Description='Dieser Bericht bietet eine konsolidierte Übersicht der verarbeiteten Lieferpläne (Shipment Schedules) für einen bestimmten Geschäftspartner, angereichert mit dem aktuellen Standard-Listenverkaufspreis für jedes Produkt.
Der Bericht ruft gezielt den Basis-Listenpreis aus dem dem Kunden zugewiesenen Preissystem ab. Bitte beachten Sie:
* Keine Rabatte: Die ausgewiesenen Preise sind Standard-Listenpreise; kundenspezifische Rabatte oder Aktionsabzüge werden nicht berücksichtigt.
* Keine ASI/PI-Prüfung: Die Preisermittlung ignoriert spezifische Preise für Attributsatz-Instanzen (ASI) sowie Packmittel-spezifische Preise (Handling Unit/PI).
* Nur Standard-UOM: Es wird der Basis-Standardpreis für die Standard-Maßeinheit (UOM) des Produkts ausgegeben.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 12:42:15.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language in ('de_CH', 'de_DE') AND AD_Process_ID=585614
;

-- 2026-05-07T12:42:15.259Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

