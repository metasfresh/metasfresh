-- Run mode: SWING_CLIENT

-- Value: Customer_Product_Statistics
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/customer_product_statistics_year_comparison/report.jasper
-- 2026-10-29T14:52:30.236Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,CSVFieldQuote,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsIncludeCSVHeaderRow,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsPdfA3Output,IsPreventConcurrentExecution,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseAutoFilters,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585691,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2026-10-29 14:52:29.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'"','D','Y','N','N','N','Y','Y','N','N','N','N','N','Y','Y','N','Y','Y','@PREFIX@de/metas/reports/customer_product_statistics_year_comparison/report.jasper',0,'Debitor/Artikel Statistik lang','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2026-10-29 14:52:29.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Customer_Product_Statistics')
;

-- 2026-10-29T14:52:30.302Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585691 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Customer_Product_Statistics(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Org_ID
-- 2026-10-29T14:55:47.458Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,113,0,585691,543366,30,'AD_Org_ID',TO_TIMESTAMP('2026-10-29 14:55:47.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@#AD_Org_ID@','Organisatorische Einheit des Mandanten','D',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion',10,'N',TO_TIMESTAMP('2026-10-29 14:55:47.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-29T14:55:47.526Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543366 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-10-29T14:55:47.611Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(113)
;

-- Process: Customer_Product_Statistics(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_Year_ID
-- 2026-10-29T14:56:23.766Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,223,0,585691,543367,19,'C_Year_ID',TO_TIMESTAMP('2026-10-29 14:56:23.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kalenderjahr','D',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','N','N','Jahr',20,'N',TO_TIMESTAMP('2026-10-29 14:56:23.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-29T14:56:23.827Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543367 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-10-29T14:56:23.887Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(223)
;

-- Process: Customer_Product_Statistics(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_ID
-- 2026-10-29T14:56:54.390Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,187,0,585691,543368,30,'C_BPartner_ID',TO_TIMESTAMP('2026-10-29 14:56:53.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',30,'N',TO_TIMESTAMP('2026-10-29 14:56:53.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-29T14:56:54.452Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543368 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-10-29T14:56:54.513Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(187)
;

-- Process: Customer_Product_Statistics(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: M_Product_ID
-- 2026-10-29T14:57:31.972Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,454,0,585691,543369,30,541138,'M_Product_ID',TO_TIMESTAMP('2026-10-29 14:57:30.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','D',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',40,'N',TO_TIMESTAMP('2026-10-29 14:57:30.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-29T14:57:32.033Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543369 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-10-29T14:57:32.094Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(454)
;

-- 2026-10-29T14:59:04.180Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585571,0,TO_TIMESTAMP('2026-10-29 14:59:03.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Debitor/Artikel Statistik lang','Debitor/Artikel Statistik lang',TO_TIMESTAMP('2026-10-29 14:59:03.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-29T14:59:04.240Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585571 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: Debitor/Artikel Statistik lang
-- Action Type: P
-- Process: Customer_Product_Statistics(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-10-29T15:00:15.806Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,585571,542372,0,585691,TO_TIMESTAMP('2026-10-29 15:00:15.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Customer_Product_Statistics','Y','N','N','N','N','Debitor/Artikel Statistik lang',TO_TIMESTAMP('2026-10-29 15:00:15.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-10-29T15:00:15.867Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542372 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-10-29T15:00:15.928Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542372, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542372)
;

-- 2026-10-29T15:00:15.995Z
/* DDL */  select update_menu_translation_from_ad_element(585571)
;

-- Reordering children of `Reports`
-- Node name: `Artikelstatistik`
-- 2026-10-29T15:00:22.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- Node name: `Artikelstatistik (Excel)`
-- 2026-10-29T15:00:22.077Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- Node name: `Product Statistics Year Comparison`
-- 2026-10-29T15:00:22.135Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542371 AND AD_Tree_ID=10
;

-- Node name: `Promotion Code Evaluation`
-- 2026-10-29T15:00:22.195Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542303 AND AD_Tree_ID=10
;

-- Node name: `Reclamations Report`
-- 2026-10-29T15:00:22.255Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- Node name: `ADR Evaluation`
-- 2026-10-29T15:00:22.313Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report by Attributes`
-- 2026-10-29T15:00:22.373Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report Weekly (by Attributes)`
-- 2026-10-29T15:00:22.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report by Partner`
-- 2026-10-29T15:00:22.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report by Partner (Weekly)`
-- 2026-10-29T15:00:22.565Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report by Partner Weekly (with Qty)`
-- 2026-10-29T15:00:22.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report by Partner (with Qty)`
-- 2026-10-29T15:00:22.689Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Quantity`
-- 2026-10-29T15:00:22.748Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Quantities`
-- 2026-10-29T15:00:22.808Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Week Quantity`
-- 2026-10-29T15:00:22.869Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Week Quantities`
-- 2026-10-29T15:00:22.929Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- Node name: `Sales Trace`
-- 2026-10-29T15:00:22.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540957 AND AD_Tree_ID=10
;

-- Node name: `Sales by Product and Customer Report (Jasper)`
-- 2026-10-29T15:00:23.048Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542317 AND AD_Tree_ID=10
;

-- Node name: `Current Sales Price Overview`
-- 2026-10-29T15:00:23.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542319 AND AD_Tree_ID=10
;

-- Node name: `Sales Price List to Date`
-- 2026-10-29T15:00:23.170Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542126 AND AD_Tree_ID=10
;

-- Node name: `Rückverfolgbarkeit`
-- 2026-10-29T15:00:23.229Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542363 AND AD_Tree_ID=10
;

-- Node name: `Debitor/Artikel Statistik lang`
-- 2026-10-29T15:00:23.288Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542372 AND AD_Tree_ID=10
;

-- Name: Debitor/Artikel Statistik lang
-- Action Type: R
-- Report: Customer_Product_Statistics(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-10-29T15:00:31.408Z
UPDATE AD_Menu SET Action='R',Updated=TO_TIMESTAMP('2026-10-29 15:00:31.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542372
;

