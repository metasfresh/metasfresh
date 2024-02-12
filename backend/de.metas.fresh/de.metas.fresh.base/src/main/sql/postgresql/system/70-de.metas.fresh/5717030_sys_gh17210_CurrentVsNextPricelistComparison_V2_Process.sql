-- Value: CurrentVsNextPricelistComparison_V2
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/pricelist/report_CurrentVsNextPricelistComparison.jasper
-- 2024-02-12T16:47:02.130Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585353,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2024-02-12 17:47:01','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','N','N','Y','Y','N','N','Y','N','N','N','@PREFIX@de/metas/reports/pricelist/report_CurrentVsNextPricelistComparison.jasper','@PREFIX@de/metas/reports/pricelist/report_CurrentVsNextPricelistComparison.jasper',0,'Vergleichsbericht aktuelle mit nächster Preisliste V2','json','N','Y','','JasperReportsSQL',TO_TIMESTAMP('2024-02-12 17:47:01','YYYY-MM-DD HH24:MI:SS'),100,'CurrentVsNextPricelistComparison_V2')
;

-- 2024-02-12T16:47:02.447Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585353 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: CurrentVsNextPricelistComparison_V2(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-02-12T16:47:27.456Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Current Vs Next Pricelist Comparison V2',Updated=TO_TIMESTAMP('2024-02-12 17:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585353
;

-- Process: CurrentVsNextPricelistComparison_V2(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_ID
-- 2024-02-12T16:49:24Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,585353,542775,30,'C_BPartner_ID',TO_TIMESTAMP('2024-02-12 17:49:23','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner','U',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','N','N','Geschäftspartner',10,TO_TIMESTAMP('2024-02-12 17:49:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T16:49:24.054Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542775 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: CurrentVsNextPricelistComparison_V2(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BP_Group_ID
-- 2024-02-12T16:50:01.922Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1383,0,585353,542776,30,540439,'C_BP_Group_ID',TO_TIMESTAMP('2024-02-12 17:50:01','YYYY-MM-DD HH24:MI:SS'),100,'@C_BP_Group_ID@','Geschäftspartnergruppe','U',0,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','Geschäftspartnergruppe',20,TO_TIMESTAMP('2024-02-12 17:50:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T16:50:01.972Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542776 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: CurrentVsNextPricelistComparison_V2(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BP_Group_ID
-- 2024-02-12T16:50:08.483Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2024-02-12 17:50:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542776
;

-- Process: CurrentVsNextPricelistComparison_V2(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: IsSOTrx
-- 2024-02-12T16:50:46.241Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1106,0,585353,542777,20,'IsSOTrx',TO_TIMESTAMP('2024-02-12 17:50:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','Dies ist eine Verkaufstransaktion','D',0,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','Verkaufstransaktion',30,TO_TIMESTAMP('2024-02-12 17:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T16:50:46.291Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542777 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: CurrentVsNextPricelistComparison_V2(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: p_show_product_price_pi_flag
-- 2024-02-12T16:51:27.886Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577666,0,585353,542778,20,'p_show_product_price_pi_flag',TO_TIMESTAMP('2024-02-12 17:51:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','D',0,'Y','N','Y','N','N','N','Preisliste Packvorschriften',40,TO_TIMESTAMP('2024-02-12 17:51:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T16:51:27.936Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542778 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: CurrentVsNextPricelistComparison_V2
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/pricelist/report_CurrentVsNextPricelistComparison_V2.jasper
-- 2024-02-12T16:51:48.813Z
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/reports/pricelist/report_CurrentVsNextPricelistComparison_V2.jasper', JasperReport_Tabular='@PREFIX@de/metas/reports/pricelist/report_CurrentVsNextPricelistComparison_V2.jasper',Updated=TO_TIMESTAMP('2024-02-12 17:51:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585353
;

-- 2024-02-12T17:14:06.290Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582962,0,TO_TIMESTAMP('2024-02-12 18:14:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vergleichsbericht aktuell v. Nächste Preisliste V2','Vergleichsbericht aktuell v. Nächste Preisliste V2',TO_TIMESTAMP('2024-02-12 18:14:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T17:14:06.579Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582962 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-02-12T17:14:36.353Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Current Vs Next Pricelist Comparison Report V2', PrintName='Current Vs Next Pricelist Comparison Report V2',Updated=TO_TIMESTAMP('2024-02-12 18:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582962 AND AD_Language='en_US'
;

-- 2024-02-12T17:14:36.468Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582962,'en_US') 
;

-- Name: Vergleichsbericht aktuell v. Nächste Preisliste V2
-- Action Type: P
-- Process: CurrentVsNextPricelistComparison_V2(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-02-12T17:15:08.390Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,582962,542137,0,585353,TO_TIMESTAMP('2024-02-12 18:15:07','YYYY-MM-DD HH24:MI:SS'),100,'D','CurrentVsNextPricelistComparison_V2','Y','N','N','N','N','Vergleichsbericht aktuell v. Nächste Preisliste V2',TO_TIMESTAMP('2024-02-12 18:15:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T17:15:08.537Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542137 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-02-12T17:15:08.588Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542137, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542137)
;

-- 2024-02-12T17:15:08.642Z
/* DDL */  select update_menu_translation_from_ad_element(582962) 
;

-- Reordering children of `Reports`
-- Node name: `Next vs AfterNext Pricelist Comparison (@PREFIX@de/metas/reports/pricelist/report_NextVsAfterNextPricelistComparison.jasper)`
-- 2024-02-12T17:15:19.613Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541956 AND AD_Tree_ID=10
;

-- Node name: `Current Vs Next Pricelist Comparison Report (@PREFIX@de/metas/reports/pricelist/report_CurrentVsNextPricelistComparison.jasper)`
-- 2024-02-12T17:15:19.662Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541452 AND AD_Tree_ID=10
;

-- Node name: `Product costs per date (@PREFIX@de/metas/reports/productcosts/report.jasper)`
-- 2024-02-12T17:15:19.711Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542123 AND AD_Tree_ID=10
;

-- Node name: `Product costs per date (Excel) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-12T17:15:19.760Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542130 AND AD_Tree_ID=10
;

-- Node name: `Vergleichsbericht aktuell v. Nächste Preisliste V2`
-- 2024-02-12T17:15:19.807Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542137 AND AD_Tree_ID=10
;

-- Name: Vergleichsbericht aktuell v. Nächste Preisliste V2
-- Action Type: R
-- Report: CurrentVsNextPricelistComparison_V2(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-02-12T17:15:28.009Z
UPDATE AD_Menu SET Action='R',Updated=TO_TIMESTAMP('2024-02-12 18:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542137
;

