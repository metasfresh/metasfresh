-- Value: Mehrwertsteuer-Verprobung(Jasper) 3
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/tax_accounting_v3/report.jasper
-- 2022-12-12T09:44:26.987Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585167,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2022-12-12 11:44:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/reports/tax_accounting_v3/report.jasper',0,'Mehrwertsteuer-Verprobung 3','json','N','Y','JasperReportsSQL',TO_TIMESTAMP('2022-12-12 11:44:25','YYYY-MM-DD HH24:MI:SS'),100,'Mehrwertsteuer-Verprobung(Jasper) 3')
;

-- 2022-12-12T09:44:26.989Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585167 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-12-12T11:34:27.254Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581864,0,TO_TIMESTAMP('2022-12-12 13:34:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Mehrwertsteuer-Verprobung 3','Mehrwertsteuer-Verprobung 3',TO_TIMESTAMP('2022-12-12 13:34:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-12T11:34:27.257Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581864 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-12-12T11:34:31.698Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-12 13:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581864 AND AD_Language='de_CH'
;

-- 2022-12-12T11:34:31.725Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581864,'de_CH') 
;

-- Element: null
-- 2022-12-12T11:34:34.084Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-12 13:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581864 AND AD_Language='de_DE'
;

-- 2022-12-12T11:34:34.086Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581864,'de_DE') 
;

-- 2022-12-12T11:34:34.090Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581864,'de_DE') 
;

-- Element: null
-- 2022-12-12T11:34:51.272Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Taxx Accounting report 3', PrintName='Taxx Accounting report 3',Updated=TO_TIMESTAMP('2022-12-12 13:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581864 AND AD_Language='en_US'
;

-- 2022-12-12T11:34:51.274Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581864,'en_US') 
;

-- Element: null
-- 2022-12-12T11:35:09.499Z
UPDATE AD_Element_Trl SET Name='Tax accounting report 2', PrintName='Tax accounting report 2',Updated=TO_TIMESTAMP('2022-12-12 13:35:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675 AND AD_Language='nl_NL'
;

-- 2022-12-12T11:35:09.504Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577675,'nl_NL') 
;

-- Element: null
-- 2022-12-12T11:35:13.215Z
UPDATE AD_Element_Trl SET Name='Tax accounting report 2', PrintName='Tax accounting report 2',Updated=TO_TIMESTAMP('2022-12-12 13:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675 AND AD_Language='en_US'
;

-- 2022-12-12T11:35:13.217Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577675,'en_US') 
;

-- Name: Mehrwertsteuer-Verprobung 3
-- Action Type: R
-- Report: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2022-12-12T11:35:34.328Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,581864,542030,0,585167,TO_TIMESTAMP('2022-12-12 13:35:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Mehrwertsteuer-Verprobung(Jasper) 3','Y','N','N','N','N','Mehrwertsteuer-Verprobung 3',TO_TIMESTAMP('2022-12-12 13:35:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-12T11:35:34.330Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542030 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-12-12T11:35:34.331Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542030, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542030)
;

-- 2022-12-12T11:35:34.341Z
/* DDL */  select update_menu_translation_from_ad_element(581864) 
;

-- Reordering children of `Reports`
-- Node name: `Business Partner Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:34.890Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- Node name: `Summary and Balance Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:34.891Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- Node name: `Profit And Loss Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:34.892Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- Node name: `Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:34.893Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- Node name: `Customer Item Statistics (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:34.893Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:34.894Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue customers (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:34.895Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- Node name: `Master Data Mutation Log (@PREFIX@de/metas/reports/bpartner_changes/report.jasper)`
-- 2022-12-12T11:35:34.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (@PREFIX@de/metas/reports/bank_statement/report.jasper)`
-- 2022-12-12T11:35:34.900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- Node name: `Federal Statistical Office (@PREFIX@de/metas/reports/salesgroups/report.jasper)`
-- 2022-12-12T11:35:34.901Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (Accounting) (@PREFIX@de/metas/reports/fact_acct/report.jasper)`
-- 2022-12-12T11:35:34.902Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- Node name: `Balance Sheet (@PREFIX@de/metas/reports/balance_sheet/report.jasper)`
-- 2022-12-12T11:35:34.903Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing (@PREFIX@de/metas/docs/direct_costing/report.jasper)`
-- 2022-12-12T11:35:34.903Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion/report.jasper)`
-- 2022-12-12T11:35:34.904Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version with previous year (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion_with_last_year/report.jasper)`
-- 2022-12-12T11:35:34.905Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- Node name: `Tax Accounting Report (@PREFIX@de/metas/reports/tax_accounting/report.jasper)`
-- 2022-12-12T11:35:34.906Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- Node name: `Tax accounting report (@PREFIX@de/metas/reports/tax_accounting_new/report.jasper)`
-- 2022-12-12T11:35:34.906Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- Node name: `Accounts Information (@PREFIX@de/metas/reports/account_info/report.jasper)`
-- 2022-12-12T11:35:34.907Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- Node name: `Balance (@PREFIX@de/metas/reports/saldobilanz/report.jasper)`
-- 2022-12-12T11:35:34.908Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- Node name: `Open Items (@PREFIX@de/metas/reports/openitems/report.jasper)`
-- 2022-12-12T11:35:34.909Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- Node name: `Cost Center Movements (@PREFIX@de/metas/reports/movements/report.jasper)`
-- 2022-12-12T11:35:34.909Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- Node name: `Mehrwertsteuer-Verprobung 3`
-- 2022-12-12T11:35:34.911Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542030 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Business Partner Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:38.430Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- Node name: `Summary and Balance Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:38.430Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- Node name: `Profit And Loss Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:38.431Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- Node name: `Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:38.433Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- Node name: `Customer Item Statistics (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:38.433Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:38.434Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue customers (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-12T11:35:38.435Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- Node name: `Master Data Mutation Log (@PREFIX@de/metas/reports/bpartner_changes/report.jasper)`
-- 2022-12-12T11:35:38.436Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (@PREFIX@de/metas/reports/bank_statement/report.jasper)`
-- 2022-12-12T11:35:38.436Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- Node name: `Federal Statistical Office (@PREFIX@de/metas/reports/salesgroups/report.jasper)`
-- 2022-12-12T11:35:38.437Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (Accounting) (@PREFIX@de/metas/reports/fact_acct/report.jasper)`
-- 2022-12-12T11:35:38.438Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- Node name: `Balance Sheet (@PREFIX@de/metas/reports/balance_sheet/report.jasper)`
-- 2022-12-12T11:35:38.438Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing (@PREFIX@de/metas/docs/direct_costing/report.jasper)`
-- 2022-12-12T11:35:38.439Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion/report.jasper)`
-- 2022-12-12T11:35:38.440Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version with previous year (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion_with_last_year/report.jasper)`
-- 2022-12-12T11:35:38.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- Node name: `Tax Accounting Report (@PREFIX@de/metas/reports/tax_accounting/report.jasper)`
-- 2022-12-12T11:35:38.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- Node name: `Tax accounting report (@PREFIX@de/metas/reports/tax_accounting_new/report.jasper)`
-- 2022-12-12T11:35:38.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- Node name: `Mehrwertsteuer-Verprobung 3`
-- 2022-12-12T11:35:38.443Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542030 AND AD_Tree_ID=10
;

-- Node name: `Accounts Information (@PREFIX@de/metas/reports/account_info/report.jasper)`
-- 2022-12-12T11:35:38.444Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- Node name: `Balance (@PREFIX@de/metas/reports/saldobilanz/report.jasper)`
-- 2022-12-12T11:35:38.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- Node name: `Open Items (@PREFIX@de/metas/reports/openitems/report.jasper)`
-- 2022-12-12T11:35:38.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- Node name: `Cost Center Movements (@PREFIX@de/metas/reports/movements/report.jasper)`
-- 2022-12-12T11:35:38.446Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

