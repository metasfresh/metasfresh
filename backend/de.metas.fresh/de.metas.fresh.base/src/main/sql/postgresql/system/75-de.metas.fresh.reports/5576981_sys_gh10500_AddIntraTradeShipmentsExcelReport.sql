-- 2020-09-24T14:04:29.019Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584748,'Y','de.metas.impexp.excel.process.ExportToExcelProcess','N',TO_TIMESTAMP('2020-09-24 16:04:28','YYYY-MM-DD HH24:MI:SS'),100,'Bericht für Intrahandel DE Excel','D','Y','N','N','N','N','N','Y','N','Y','Y','',0,'Bericht für Intrahandel DE Excel','N','Y','Excel',TO_TIMESTAMP('2020-09-24 16:04:28','YYYY-MM-DD HH24:MI:SS'),100,'Report for Intratrade DE Excel')
;

-- 2020-09-24T14:04:29.043Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584748 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-09-24T14:21:38.278Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,223,0,584748,541865,30,'C_Year_ID',TO_TIMESTAMP('2020-09-24 16:21:38','YYYY-MM-DD HH24:MI:SS'),100,'Kalenderjahr','U',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','Y','N','Jahr',10,TO_TIMESTAMP('2020-09-24 16:21:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-24T14:21:38.315Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541865 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-09-24T14:21:51.846Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,206,0,584748,541866,30,'C_Period_ID',TO_TIMESTAMP('2020-09-24 16:21:51','YYYY-MM-DD HH24:MI:SS'),100,'Periode des Kalenders','U',0,'"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','Y','N','N','N','Periode',20,TO_TIMESTAMP('2020-09-24 16:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-24T14:21:51.847Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541866 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-09-24T14:22:17.932Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=19, AD_Val_Rule_ID=199, IsMandatory='Y',Updated=TO_TIMESTAMP('2020-09-24 16:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541866
;

-- 2020-09-24T14:22:56.439Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select * from de_metas_endcustomer_fresh_reports.IntraTradeShipments(@C_Period_ID@)',Updated=TO_TIMESTAMP('2020-09-24 16:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584748
;

-- 2020-09-24T14:23:47.632Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578121,0,TO_TIMESTAMP('2020-09-24 16:23:47','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Bericht für Intrahandel DE Excel','Bericht für Intrahandel DE Excel',TO_TIMESTAMP('2020-09-24 16:23:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-24T14:23:47.640Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578121 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-24T14:23:59.875Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,578121,541515,0,584748,TO_TIMESTAMP('2020-09-24 16:23:59','YYYY-MM-DD HH24:MI:SS'),100,'U','Report for Intratrade DE Excel','Y','N','N','N','N','Bericht für Intrahandel DE Excel',TO_TIMESTAMP('2020-09-24 16:23:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-24T14:23:59.891Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541515 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2020-09-24T14:23:59.896Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541515, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541515)
;

-- 2020-09-24T14:23:59.935Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(578121) 
;

-- 2020-09-24T14:23:59.984Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541436 AND AD_Tree_ID=10
;

-- 2020-09-24T14:23:59.985Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541438 AND AD_Tree_ID=10
;

-- 2020-09-24T14:23:59.986Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540963 AND AD_Tree_ID=10
;

-- 2020-09-24T14:23:59.989Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541420 AND AD_Tree_ID=10
;

-- 2020-09-24T14:23:59.990Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541044 AND AD_Tree_ID=10
;

-- 2020-09-24T14:23:59.990Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541515 AND AD_Tree_ID=10
;

-- 2020-09-24T14:24:02.685Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541436 AND AD_Tree_ID=10
;

-- 2020-09-24T14:24:02.686Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541438 AND AD_Tree_ID=10
;

-- 2020-09-24T14:24:02.686Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541515 AND AD_Tree_ID=10
;

-- 2020-09-24T14:24:02.686Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540963 AND AD_Tree_ID=10
;

-- 2020-09-24T14:24:02.687Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541420 AND AD_Tree_ID=10
;

-- 2020-09-24T14:24:02.687Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541044 AND AD_Tree_ID=10
;

