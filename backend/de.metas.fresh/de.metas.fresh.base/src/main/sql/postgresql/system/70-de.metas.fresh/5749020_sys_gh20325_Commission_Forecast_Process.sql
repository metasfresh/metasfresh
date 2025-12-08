-- Value: Commission Forecast (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/purchase/commission_forecast/report.jasper
-- 2025-03-12T19:37:48.473Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585456,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2025-03-12 20:37:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/purchase/commission_forecast/report.jasper',0,'Provision Prognose','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2025-03-12 20:37:47','YYYY-MM-DD HH24:MI:SS'),100,'Commission Forecast (Jasper)')
;

-- Value: Commission Forecast (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- 2025-03-13T12:03:21.972Z
UPDATE AD_Process SET JasperReport='', JasperReport_Tabular='',Updated=TO_TIMESTAMP('2025-03-13 13:03:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585456
;

-- Value: Commission Forecast (Jasper)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-03-13T12:05:08.087Z
UPDATE AD_Process SET Classname='de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess', SQLStatement='select * from de_metas_endcustomer_fresh_reports.Docs_Purchase_Commission_Forecast(@C_BPartner_SalesRep_ID@, ''@#AD_Language@'')', Type='Excel',Updated=TO_TIMESTAMP('2025-03-13 13:05:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585456
;

-- 2025-03-12T19:37:48.527Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585456 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Commission Forecast (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_SalesRep_ID
-- 2025-03-12T19:39:14.500Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541357,0,585456,542936,30,138,540452,'C_BPartner_SalesRep_ID',TO_TIMESTAMP('2025-03-12 20:39:13','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','Y','N','Vertriebspartner',10,TO_TIMESTAMP('2025-03-12 20:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-12T19:39:14.554Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542936 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Commission Forecast (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_SalesRep_ID
-- 2025-03-12T19:39:36.370Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-03-12 20:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542936
;

-- 2025-03-12T19:41:50.253Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583528,0,TO_TIMESTAMP('2025-03-12 20:41:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Provision Prognose','Provision Prognose',TO_TIMESTAMP('2025-03-12 20:41:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-12T19:41:50.306Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583528 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-03-12T19:42:29.662Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission Forecast', PrintName='Commission Forecast',Updated=TO_TIMESTAMP('2025-03-12 20:42:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583528 AND AD_Language='en_US'
;

-- 2025-03-12T19:42:29.792Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583528,'en_US') 
;

-- Name: Provision Prognose
-- Action Type: P
-- Process: Commission Forecast (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-03-12T19:43:01.071Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,583528,542214,0,585456,TO_TIMESTAMP('2025-03-12 20:43:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Commission Forecast (Jasper)','Y','N','N','N','N','Provision Prognose',TO_TIMESTAMP('2025-03-12 20:43:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-12T19:43:01.123Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542214 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-03-12T19:43:01.176Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542214, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542214)
;

-- 2025-03-12T19:43:01.234Z
/* DDL */  select update_menu_translation_from_ad_element(583528) 
;

-- Reordering children of `Billing`
-- Node name: `Provisionsberechnung`
-- 2025-03-12T19:43:18.215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542203 AND AD_Tree_ID=10
;

-- Node name: `Invoice verifcation`
-- 2025-03-12T19:43:18.367Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Candidates`
-- 2025-03-12T19:43:18.419Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice Candidates`
-- 2025-03-12T19:43:18.574Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice`
-- 2025-03-12T19:43:18.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice`
-- 2025-03-12T19:43:18.727Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- Node name: `Customs Invoice`
-- 2025-03-12T19:43:18.782Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- Node name: `Shipment Line to Customs Invoice Line`
-- 2025-03-12T19:43:18.844Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-03-12T19:43:18.982Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-03-12T19:43:19.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-03-12T19:43:19.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- Node name: `Enqueue selection for invoicing and pdf printing`
-- 2025-03-12T19:43:19.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541886 AND AD_Tree_ID=10
;

-- Node name: `Provision Prognose`
-- 2025-03-12T19:43:19.189Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542214 AND AD_Tree_ID=10
;

