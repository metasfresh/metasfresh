-- Value: report_not_invoiced_receipts
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-01-16T16:11:04.521Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585183,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2023-01-16 18:11:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','Y','N','N','N','N','Y','Y',0,'Noch nicht in Rechnung gestellte Wareneingänge','json','N','N','xls','SELECT * FROM de_metas_acct.report_not_invoiced_receipts(@C_ElementValue_ID/null@, @C_AcctSchema_ID/null@, @EndDate/quotedIfNotDefault/NULL@);','Excel',TO_TIMESTAMP('2023-01-16 18:11:04','YYYY-MM-DD HH24:MI:SS'),100,'report_not_invoiced_receipts')
;

-- 2023-01-16T16:11:04.591Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585183 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: report_not_invoiced_receipts(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-16T16:11:51.882Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,198,0,585183,542452,30,182,'C_ElementValue_ID',TO_TIMESTAMP('2023-01-16 18:11:51','YYYY-MM-DD HH24:MI:SS'),100,'Kontenart','de.metas.acct',0,'Account Elements can be natural accounts or user defined values.','Y','N','Y','N','N','N','Kontenart',10,TO_TIMESTAMP('2023-01-16 18:11:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T16:11:51.915Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542452 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_not_invoiced_receipts(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_ElementValue_ID
-- 2023-01-16T16:11:56.194Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-16 18:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542452
;

-- Process: report_not_invoiced_receipts(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_AcctSchema_ID
-- 2023-01-16T16:12:18.373Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585183,542453,18,'C_AcctSchema_ID',TO_TIMESTAMP('2023-01-16 18:12:17','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','de.metas.acct',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','N','Buchführungs-Schema',20,TO_TIMESTAMP('2023-01-16 18:12:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T16:12:18.407Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542453 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: report_not_invoiced_receipts(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: EndDate
-- 2023-01-16T16:12:47.609Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,294,0,585183,542454,15,'EndDate',TO_TIMESTAMP('2023-01-16 18:12:47','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','Last effective date (inclusive)','de.metas.acct',0,'The End Date indicates the last date in this range.','Y','N','Y','N','Y','N','Enddatum','',30,TO_TIMESTAMP('2023-01-16 18:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T16:12:47.641Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542454 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: Noch nicht in Rechnung gestellte Wareneingänge
-- Action Type: P
-- Process: report_not_invoiced_receipts(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-16T16:17:47.844Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,1117,542040,0,585183,TO_TIMESTAMP('2023-01-16 18:17:47','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.acct','report_not_invoiced_receipts','Y','N','N','N','N','Noch nicht in Rechnung gestellte Wareneingänge',TO_TIMESTAMP('2023-01-16 18:17:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T16:17:47.943Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542040 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-16T16:17:47.976Z
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542040, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542040)
;

-- 2023-01-16T16:17:48.016Z
/* DDL */  select update_menu_translation_from_ad_element(1117) 
;

-- 2023-01-16T16:17:58Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542039 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542037 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.063Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541440 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.126Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.189Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.220Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.252Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541439 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.284Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540944 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.347Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.378Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540945 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.411Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540937 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540939 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540940 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540941 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.536Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540946 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.568Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.599Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540942 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540938 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.662Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540948 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.693Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540959 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.725Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542036 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.757Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542038 AND AD_Tree_ID=10
;

-- 2023-01-16T16:17:58.789Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000064, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542040 AND AD_Tree_ID=10
;

-- 2023-01-16T16:38:15.833Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581925,0,TO_TIMESTAMP('2023-01-16 18:38:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Noch nicht fakturierte Wareneingänge','Noch nicht fakturierte Wareneingänge',TO_TIMESTAMP('2023-01-16 18:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T16:38:16.065Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581925 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-01-16T16:38:37.409Z
UPDATE AD_Element_Trl SET Name='Goods received not yet invoiced', PrintName='Goods received not yet invoiced',Updated=TO_TIMESTAMP('2023-01-16 18:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581925 AND AD_Language='en_US'
;

-- 2023-01-16T16:38:37.471Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581925,'en_US')
;

-- Name: Noch nicht fakturierte Wareneingänge
-- Action Type: P
-- Process: report_not_invoiced_receipts(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-01-16T16:40:23.772Z
UPDATE AD_Menu SET AD_Element_ID=581925, Description=NULL, Name='Noch nicht fakturierte Wareneingänge', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2023-01-16 18:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542040
;

-- 2023-01-16T16:40:23.870Z
/* DDL */  select update_menu_translation_from_ad_element(581925)
;
