-- Value: Commission Calculation (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/purchase/commission/report.jasper
-- 2025-02-28T10:32:43.955Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585455,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2025-02-28 11:32:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/purchase/commission/report.jasper',0,'Commission Calculation (Jasper)','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2025-02-28 11:32:43','YYYY-MM-DD HH24:MI:SS'),100,'Commission Calculation (Jasper)')
;

-- 2025-02-28T10:32:44.016Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585455 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: Commission Calculation (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/purchase/commission/report.jasper
-- 2025-02-28T10:34:30.925Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Provisionsberechnung (Jasper)',Updated=TO_TIMESTAMP('2025-02-28 11:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585455
;

-- Process: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-28T10:34:30.517Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Provisionsberechnung (Jasper)',Updated=TO_TIMESTAMP('2025-02-28 11:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585455
;

-- Value: Commission Calculation (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/purchase/commission/report.jasper
-- 2025-02-28T10:35:01.591Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Provisionsberechnung',Updated=TO_TIMESTAMP('2025-02-28 11:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585455
;

-- Process: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-28T10:35:01.232Z
UPDATE AD_Process_Trl SET Name='Provisionsberechnung',Updated=TO_TIMESTAMP('2025-02-28 11:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585455
;

-- Process: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_SalesRep_ID
-- 2025-02-28T10:37:10.233Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541357,0,585455,542927,30,138,540452,'C_BPartner_SalesRep_ID',TO_TIMESTAMP('2025-02-28 11:37:09','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Vertriebspartner',10,TO_TIMESTAMP('2025-02-28 11:37:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-28T10:37:10.284Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542927 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-02-28T10:39:55.632Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583515,0,'CommissionDate_From',TO_TIMESTAMP('2025-02-28 11:39:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Provisionsdatum von','Provisionsdatum von',TO_TIMESTAMP('2025-02-28 11:39:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-28T10:39:55.687Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583515 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CommissionDate_From
-- 2025-02-28T10:40:34.365Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission date from', PrintName='Commission date from',Updated=TO_TIMESTAMP('2025-02-28 11:40:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583515 AND AD_Language='en_US'
;

-- 2025-02-28T10:40:34.494Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583515,'en_US') 
;

-- 2025-02-28T10:41:15.625Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583516,0,'CommissionDate_To',TO_TIMESTAMP('2025-02-28 11:41:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Provisionsdatum bis','Provisionsdatum bis',TO_TIMESTAMP('2025-02-28 11:41:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-28T10:41:15.676Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583516 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CommissionDate_To
-- 2025-02-28T10:41:45.382Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission date to', PrintName='Commission date to',Updated=TO_TIMESTAMP('2025-02-28 11:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583516 AND AD_Language='en_US'
;

-- 2025-02-28T10:41:45.482Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583516,'en_US') 
;

-- Process: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: CommissionDate_From
-- 2025-02-28T10:42:42.658Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583515,0,585455,542928,16,'CommissionDate_From',TO_TIMESTAMP('2025-02-28 11:42:42','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Provisionsdatum von',20,TO_TIMESTAMP('2025-02-28 11:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-28T10:42:42.717Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542928 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: CommissionDate_To
-- 2025-02-28T10:43:38.948Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583516,0,585455,542929,16,'CommissionDate_To',TO_TIMESTAMP('2025-02-28 11:43:38','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Provisionsdatum bis',30,TO_TIMESTAMP('2025-02-28 11:43:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-28T10:43:39.003Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542929 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Bill_BPartner_ID
-- 2025-02-28T10:45:18.874Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2039,0,585455,542930,30,138,'Bill_BPartner_ID',TO_TIMESTAMP('2025-02-28 11:45:18','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung','U',0,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','N','Rechnungspartner',40,TO_TIMESTAMP('2025-02-28 11:45:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-28T10:45:18.931Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542930 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-28T10:47:11.506Z
UPDATE AD_Process_Trl SET Name='Commission Calculation',Updated=TO_TIMESTAMP('2025-02-28 11:47:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585455
;

-- Process: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-28T10:47:15.527Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-02-28 11:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585455
;

-- 2025-02-28T10:47:50.277Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583517,0,TO_TIMESTAMP('2025-02-28 11:47:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Provisionsberechnung','Provisionsberechnung',TO_TIMESTAMP('2025-02-28 11:47:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-28T10:47:50.340Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583517 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-02-28T10:48:17.222Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission Calculation', PrintName='Commission Calculation',Updated=TO_TIMESTAMP('2025-02-28 11:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583517 AND AD_Language='en_US'
;

-- 2025-02-28T10:48:17.338Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583517,'en_US') 
;

-- Name: Provisionsberechnung
-- Action Type: R
-- Report: Commission Calculation (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-02-28T10:49:04.757Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,572584,542203,0,585455,TO_TIMESTAMP('2025-02-28 11:49:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Commission Calculation (Jasper)','Y','N','N','N','N','Provisionsberechnung',TO_TIMESTAMP('2025-02-28 11:49:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-02-28T10:49:04.807Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542203 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-02-28T10:49:04.864Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542203, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542203)
;

-- 2025-02-28T10:49:04.970Z
/* DDL */  select update_menu_translation_from_ad_element(572584) 
;

-- Reordering children of `Service`
-- Node name: `Time Type`
-- 2025-02-28T10:49:14.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=369 AND AD_Tree_ID=10
;

-- Node name: `Expense Report`
-- 2025-02-28T10:49:14.427Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=318 AND AD_Tree_ID=10
;

-- Node name: `Expenses (to be invoiced)`
-- 2025-02-28T10:49:14.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=327 AND AD_Tree_ID=10
;

-- Node name: `Create Sales Orders from Expense`
-- 2025-02-28T10:49:14.567Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=328 AND AD_Tree_ID=10
;

-- Node name: `Expenses (not reimbursed)`
-- 2025-02-28T10:49:14.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=349 AND AD_Tree_ID=10
;

-- Node name: `Create AP Expense Invoices`
-- 2025-02-28T10:49:14.700Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=329 AND AD_Tree_ID=10
;

-- Node name: `Service Level`
-- 2025-02-28T10:49:14.758Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=284 AND AD_Tree_ID=10
;

-- Node name: `Training`
-- 2025-02-28T10:49:14.809Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=344 AND AD_Tree_ID=10
;

-- Node name: `Provisionsberechnung`
-- 2025-02-28T10:49:14.864Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542203 AND AD_Tree_ID=10
;

-- Reordering children of `Billing`
-- Node name: `Provisionsberechnung`
-- 2025-02-28T10:49:23.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542203 AND AD_Tree_ID=10
;

-- Node name: `Invoice verifcation`
-- 2025-02-28T10:49:23.161Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Candidates`
-- 2025-02-28T10:49:23.214Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice Candidates`
-- 2025-02-28T10:49:23.269Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice`
-- 2025-02-28T10:49:23.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice`
-- 2025-02-28T10:49:23.370Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- Node name: `Customs Invoice`
-- 2025-02-28T10:49:23.420Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- Node name: `Shipment Line to Customs Invoice Line`
-- 2025-02-28T10:49:23.470Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-02-28T10:49:23.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-02-28T10:49:23.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-02-28T10:49:23.623Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- Node name: `Enqueue selection for invoicing and pdf printing`
-- 2025-02-28T10:49:23.674Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541886 AND AD_Tree_ID=10
;

