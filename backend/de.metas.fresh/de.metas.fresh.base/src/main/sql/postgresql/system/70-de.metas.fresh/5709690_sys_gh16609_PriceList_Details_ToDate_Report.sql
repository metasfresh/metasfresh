-- Value: RV_Fresh_SalesPriceList_ToDate
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/pricelist_todate/report.jasper
-- 2023-11-06T20:08:56.554Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585334,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-11-06 21:08:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/reports/pricelist_todate/report.jasper','',0,'Bisherige Verkaufspreisliste','json','N','Y','JasperReportsSQL',TO_TIMESTAMP('2023-11-06 21:08:55','YYYY-MM-DD HH24:MI:SS'),100,'RV_Fresh_SalesPriceList_ToDate')
;

-- 2023-11-06T20:08:56.558Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585334 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-11-06T20:09:25.177Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Sales Price List to Date',Updated=TO_TIMESTAMP('2023-11-06 21:09:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585334
;

-- 2023-11-06T20:10:35.914Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582791,0,TO_TIMESTAMP('2023-11-06 21:10:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bisherige Verkaufspreisliste','Bisherige Verkaufspreisliste',TO_TIMESTAMP('2023-11-06 21:10:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-06T20:10:35.917Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582791 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-11-06T20:10:53.646Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sales Price List to Date', PrintName='Sales Price List to Date',Updated=TO_TIMESTAMP('2023-11-06 21:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582791 AND AD_Language='en_US'
;

-- 2023-11-06T20:10:53.665Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582791,'en_US') 
;

-- Name: Bisherige Verkaufspreisliste
-- Action Type: P
-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-11-06T20:11:31.474Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,582791,542126,0,585334,TO_TIMESTAMP('2023-11-06 21:11:31','YYYY-MM-DD HH24:MI:SS'),100,'D','RV_Fresh_SalesPriceList_ToDate','Y','N','N','N','N','Bisherige Verkaufspreisliste',TO_TIMESTAMP('2023-11-06 21:11:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-06T20:11:31.476Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542126 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-11-06T20:11:31.478Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542126, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542126)
;

-- 2023-11-06T20:11:31.486Z
/* DDL */  select update_menu_translation_from_ad_element(582791) 
;

-- Reordering children of `Reports`
-- Node name: `Article Statistics (@PREFIX@de/metas/reports/article_statistics/report.jasper)`
-- 2023-11-06T20:11:32.072Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- Node name: `Artikelstatistik (Excel)`
-- 2023-11-06T20:11:32.073Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- Node name: `ADR Evaluation (@PREFIX@de/metas/reports/umsatzreport_adr_bpartner/report.jasper)`
-- 2023-11-06T20:11:32.074Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- Node name: `Reclamations Report (@PREFIX@de/metas/reports/request_report/report.jasper)`
-- 2023-11-06T20:11:32.075Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- Node name: `Revenue Report (@PREFIX@de/metas/reports/umsatzreport/report.jasper)`
-- 2023-11-06T20:11:32.077Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- Node name: `Revenue Week Report (@PREFIX@de/metas/reports/umsatzreport_week/report.jasper)`
-- 2023-11-06T20:11:32.078Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Revenue (@PREFIX@de/metas/reports/umsatzreport_bpartner/report.jasper)`
-- 2023-11-06T20:11:32.079Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Week Revenue (@PREFIX@de/metas/reports/umsatzreport_bpartner_week/report.jasper)`
-- 2023-11-06T20:11:32.080Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Week Revenue with Quantity (@PREFIX@de/metas/reports/umsatzreport_bpartner_week_with_qty/report.jasper)`
-- 2023-11-06T20:11:32.081Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Revenue with Quantity (@PREFIX@de/metas/reports/umsatzreport_bpartner_with_qty/report.jasper)`
-- 2023-11-06T20:11:32.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Quantity (@PREFIX@de/metas/reports/qty_statistics/report.jasper)`
-- 2023-11-06T20:11:32.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Quantities (@PREFIX@de/metas/reports/qty_statistics_kg/report.jasper)`
-- 2023-11-06T20:11:32.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Week Quantity (@PREFIX@de/metas/reports/qty_statistics_week/report.jasper)`
-- 2023-11-06T20:11:32.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- Node name: `Statistics by Total Week Quantities (@PREFIX@de/metas/reports/qty_statistics_kg_week/report.jasper)`
-- 2023-11-06T20:11:32.085Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- Node name: `Sales Trace (@PREFIX@de/metas/reports/sales_trace/report.xls)`
-- 2023-11-06T20:11:32.086Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540957 AND AD_Tree_ID=10
;

-- Node name: `Bisherige Verkaufspreisliste`
-- 2023-11-06T20:11:32.088Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542126 AND AD_Tree_ID=10
;

-- Name: Bisherige Verkaufspreisliste
-- Action Type: R
-- Report: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-11-06T20:11:38.367Z
UPDATE AD_Menu SET Action='R',Updated=TO_TIMESTAMP('2023-11-06 21:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542126
;

-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: ValidFrom
-- 2023-11-06T20:14:16.563Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,617,0,585334,542733,15,'ValidFrom',TO_TIMESTAMP('2023-11-06 21:14:16','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','D',0,'"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','Y','N','Gültig ab',10,TO_TIMESTAMP('2023-11-06 21:14:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-06T20:14:16.565Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542733 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: p_show_product_price_pi_flag
-- 2023-11-06T20:15:03.602Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577666,0,585334,542734,20,'p_show_product_price_pi_flag',TO_TIMESTAMP('2023-11-06 21:15:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',0,'Y','N','Y','N','N','N','Preisliste Packvorschriften',20,TO_TIMESTAMP('2023-11-06 21:15:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-06T20:15:03.604Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542734 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

