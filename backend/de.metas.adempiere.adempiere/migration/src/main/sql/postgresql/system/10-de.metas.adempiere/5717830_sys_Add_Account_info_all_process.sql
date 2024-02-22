-- Value: ExportAccountInfos
-- Classname: de.metas.acct.process.ExportAccountInfos
-- 2024-02-21T15:13:46.397355300Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585357,'Y','de.metas.acct.process.ExportAccountInfos','N',TO_TIMESTAMP('2024-02-21 17:13:46.168','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Kontenauszug für alle Konten','json','N','N','xls','Java',TO_TIMESTAMP('2024-02-21 17:13:46.168','YYYY-MM-DD HH24:MI:SS.US'),100,'ExportAccountInfos')
;

-- 2024-02-21T15:13:46.415799400Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585357 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ExportAccountInfos(de.metas.acct.process.ExportAccountInfos)
-- ParameterName: C_AcctSchema_ID
-- 2024-02-21T15:14:06.368313Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585357,542781,19,'C_AcctSchema_ID',TO_TIMESTAMP('2024-02-21 17:14:06.219','YYYY-MM-DD HH24:MI:SS.US'),100,'Stammdaten für Buchhaltung','U',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','Y','N','Buchführungs-Schema',10,TO_TIMESTAMP('2024-02-21 17:14:06.219','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-21T15:14:06.370937500Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542781 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-02-21T15:14:06.400407700Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(181) 
;

-- Process: ExportAccountInfos(de.metas.acct.process.ExportAccountInfos)
-- ParameterName: C_AcctSchema_ID
-- 2024-02-21T15:14:14.727643700Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2024-02-21 17:14:14.727','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542781
;

-- Process: ExportAccountInfos(de.metas.acct.process.ExportAccountInfos)
-- ParameterName: DateAcctFrom
-- 2024-02-21T15:14:39.508443600Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543873,0,585357,542782,15,'DateAcctFrom',TO_TIMESTAMP('2024-02-21 17:14:39.4','YYYY-MM-DD HH24:MI:SS.US'),100,'D',0,'Y','N','Y','N','Y','N','Buchungsdatum von',20,TO_TIMESTAMP('2024-02-21 17:14:39.4','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-21T15:14:39.509532900Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542782 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-02-21T15:14:39.511097700Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(543873) 
;

-- Process: ExportAccountInfos(de.metas.acct.process.ExportAccountInfos)
-- ParameterName: DateAcctTo
-- 2024-02-21T15:14:57.615796500Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543874,0,585357,542783,15,'DateAcctTo',TO_TIMESTAMP('2024-02-21 17:14:57.441','YYYY-MM-DD HH24:MI:SS.US'),100,'U',0,'Y','N','Y','N','Y','N','Buchungsdatum bis',30,TO_TIMESTAMP('2024-02-21 17:14:57.441','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-21T15:14:57.616836Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542783 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-02-21T15:14:57.618900400Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(543874) 
;

-- 2024-02-21T15:19:36.500499300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582975,0,TO_TIMESTAMP('2024-02-21 17:19:36.359','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Konten Information','Konten Information',TO_TIMESTAMP('2024-02-21 17:19:36.359','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-21T15:19:36.505274400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582975 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-02-21T15:19:45.830745900Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kontenauszug für alle Konten', PrintName='Kontenauszug für alle Konten',Updated=TO_TIMESTAMP('2024-02-21 17:19:45.83','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582975 AND AD_Language='de_CH'
;

-- 2024-02-21T15:19:45.834376Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582975,'de_CH') 
;

-- Element: null
-- 2024-02-21T15:19:48.958699800Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kontenauszug für alle Konten', PrintName='Kontenauszug für alle Konten',Updated=TO_TIMESTAMP('2024-02-21 17:19:48.958','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582975 AND AD_Language='de_DE'
;

-- 2024-02-21T15:19:48.960251300Z
UPDATE AD_Element SET Name='Kontenauszug für alle Konten', PrintName='Kontenauszug für alle Konten' WHERE AD_Element_ID=582975
;

-- 2024-02-21T15:19:49.179286300Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582975,'de_DE') 
;

-- 2024-02-21T15:19:49.180310300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582975,'de_DE') 
;

-- Element: null
-- 2024-02-21T15:20:05.900144300Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Account Info - all accounts', PrintName='Account Info - all accounts',Updated=TO_TIMESTAMP('2024-02-21 17:20:05.9','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582975 AND AD_Language='en_US'
;

-- 2024-02-21T15:20:05.903255100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582975,'en_US') 
;

-- Element: null
-- 2024-02-21T15:20:09.868984800Z
UPDATE AD_Element_Trl SET Name='Account Info - all accounts', PrintName='Account Info - all accounts',Updated=TO_TIMESTAMP('2024-02-21 17:20:09.868','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582975 AND AD_Language='fr_CH'
;

-- 2024-02-21T15:20:09.872095100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582975,'fr_CH') 
;

-- Name: Kontenauszug für alle Konten
-- Action Type: R
-- Report: Konten - Information (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-02-21T15:20:32.162515400Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,582975,542140,0,540564,TO_TIMESTAMP('2024-02-21 17:20:32.047','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Account Info - all accounts','Y','N','N','N','N','Kontenauszug für alle Konten',TO_TIMESTAMP('2024-02-21 17:20:32.047','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-21T15:20:32.166214100Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542140 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-02-21T15:20:32.169395500Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542140, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542140)
;

-- 2024-02-21T15:20:32.181486900Z
/* DDL */  select update_menu_translation_from_ad_element(582975) 
;

-- Reordering children of `Reports`
-- Node name: `Unallocated Payments (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.811334300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542039 AND AD_Tree_ID=10
;

-- Node name: `Inventory Clearing (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.812889800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542037 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.813931800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- Node name: `Summary and Balance Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.814970Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- Node name: `Profit And Loss Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.816007700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- Node name: `Account Sheet Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.817059900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- Node name: `Customer Item Statistics (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.818096400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.819132900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- Node name: `Show top-revenue customers (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.820175Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- Node name: `Master Data Mutation Log (@PREFIX@de/metas/reports/bpartner_changes/report.jasper)`
-- 2024-02-21T15:20:32.821212100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (@PREFIX@de/metas/reports/bank_statement/report.jasper)`
-- 2024-02-21T15:20:32.822770800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- Node name: `Federal Statistical Office (@PREFIX@de/metas/reports/salesgroups/report.jasper)`
-- 2024-02-21T15:20:32.823805500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (Accounting) (@PREFIX@de/metas/reports/fact_acct/report.jasper)`
-- 2024-02-21T15:20:32.824840800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- Node name: `Balance Sheet (@PREFIX@de/metas/reports/balance_sheet/report.jasper)`
-- 2024-02-21T15:20:32.825359800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing (@PREFIX@de/metas/docs/direct_costing/report.jasper)`
-- 2024-02-21T15:20:32.826399300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion/report.jasper)`
-- 2024-02-21T15:20:32.827434800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- Node name: `Marginal Costing Short Version with previous year (@PREFIX@de/metas/reports/deckungsbeitrag_kurzversion_with_last_year/report.jasper)`
-- 2024-02-21T15:20:32.828464300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- Node name: `Tax Accounting report 3 (@PREFIX@de/metas/reports/tax_accounting_v3/report.jasper)`
-- 2024-02-21T15:20:32.829501600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542030 AND AD_Tree_ID=10
;

-- Node name: `Tax Accounting Report (@PREFIX@de/metas/reports/tax_accounting/report.jasper)`
-- 2024-02-21T15:20:32.830538300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- Node name: `Tax accounting report 2 (@PREFIX@de/metas/reports/tax_accounting_new/report.jasper)`
-- 2024-02-21T15:20:32.831582500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- Node name: `Accounts Information (@PREFIX@de/metas/reports/account_info/report.jasper)`
-- 2024-02-21T15:20:32.832632600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- Node name: `Balance (@PREFIX@de/metas/reports/saldobilanz/report.jasper)`
-- 2024-02-21T15:20:32.833152Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- Node name: `Open Items (@PREFIX@de/metas/reports/openitems/report.jasper)`
-- 2024-02-21T15:20:32.834192900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- Node name: `Cost Center Movements (@PREFIX@de/metas/reports/movements/report.jasper)`
-- 2024-02-21T15:20:32.835228100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- Node name: `Deposit in transit (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.836264200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542036 AND AD_Tree_ID=10
;

-- Node name: `Payment selection (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.837296900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542038 AND AD_Tree_ID=10
;

-- Node name: `Goods received not yet invoiced (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-21T15:20:32.838323800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542040 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug für alle Konten`
-- 2024-02-21T15:20:32.838840800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542140 AND AD_Tree_ID=10
;

-- Name: Kontenauszug für alle Konten
-- Action Type: P
-- Process: ExportAccountInfos(de.metas.acct.process.ExportAccountInfos)
-- 2024-02-21T15:20:44.310568900Z
UPDATE AD_Menu SET Action='P', AD_Process_ID=585357, InternalName='ExportAccountInfos',Updated=TO_TIMESTAMP('2024-02-21 17:20:44.31','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Menu_ID=542140
;

-- 2024-02-22T14:24:31.582917700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582976,0,'Function',TO_TIMESTAMP('2024-02-22 16:24:28.065','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Function','Function',TO_TIMESTAMP('2024-02-22 16:24:28.065','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-22T14:24:31.861061300Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582976 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: ExportAccountInfos(de.metas.acct.process.ExportAccountInfos)
-- ParameterName: Function
-- 2024-02-22T14:25:15.795045200Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,582976,0,585357,542784,10,'Function',TO_TIMESTAMP('2024-02-22 16:25:15.671','YYYY-MM-DD HH24:MI:SS.US'),100,'1=2','D',0,'Y','N','Y','N','Y','N','Function','1=1',40,TO_TIMESTAMP('2024-02-22 16:25:15.671','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-22T14:25:15.797126500Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542784 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-02-22T14:25:15.830196900Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582976) 
;

-- Process: ExportAccountInfos(de.metas.acct.process.ExportAccountInfos)
-- ParameterName: Function
-- 2024-02-22T14:25:47.924039100Z
UPDATE AD_Process_Para SET DefaultValue='report.AccountSheet_Report',Updated=TO_TIMESTAMP('2024-02-22 16:25:47.923','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542784
;


-- Process: ExportAccountInfos(de.metas.acct.process.ExportAccountInfos)
-- ParameterName: C_AcctSchema_ID
-- 2024-02-22T14:25:47.924039100Z
UPDATE AD_Process_Para SET DefaultValue='@$C_AcctSchema_ID@',Updated=TO_TIMESTAMP('2023-11-28 15:54:57.639','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542781
;

