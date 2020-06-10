-- 2020-04-16T13:55:54.036Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2020-04-16 15:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540907
;

-- 2020-04-16T13:55:56.802Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2020-04-16 15:55:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540908
;

-- 2020-04-16T13:56:20.671Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,540662,541803,15,'DateFrom',TO_TIMESTAMP('2020-04-16 15:56:20','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','de.metas.fresh',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',60,TO_TIMESTAMP('2020-04-16 15:56:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T13:56:20.677Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541803 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-16T13:56:46.139Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,540662,541804,15,'DateTo',TO_TIMESTAMP('2020-04-16 15:56:46','YYYY-MM-DD HH24:MI:SS'),100,'Enddatum eines Abschnittes','de.metas.fresh',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','Y','N','Datum bis',7,TO_TIMESTAMP('2020-04-16 15:56:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T13:56:46.142Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541804 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-16T13:56:51.739Z
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=3,Updated=TO_TIMESTAMP('2020-04-16 15:56:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541803
;

-- 2020-04-16T13:58:03.432Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584686,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2020-04-16 15:58:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N','N','N','N','Y','N','Y','Y','@PREFIX@de/metas/reports/tax_accounting_new/report.jasper',0,'Mehrwertsteuer-Verprobung 2','N','Y','JasperReportsSQL',TO_TIMESTAMP('2020-04-16 15:58:03','YYYY-MM-DD HH24:MI:SS'),100,'Mehrwertsteuer-Verprobung(Jasper) 2')
;

-- 2020-04-16T13:58:03.466Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584686 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-04-16T13:58:47.905Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,584686,541805,15,'DateFrom',TO_TIMESTAMP('2020-04-16 15:58:47','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',10,TO_TIMESTAMP('2020-04-16 15:58:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T13:58:47.906Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541805 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-16T13:59:42.995Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,584686,541806,15,'DateTo',TO_TIMESTAMP('2020-04-16 15:59:42','YYYY-MM-DD HH24:MI:SS'),100,'Enddatum eines Abschnittes','U',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','Y','N','Datum bis',20,TO_TIMESTAMP('2020-04-16 15:59:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T13:59:42.997Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541806 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-16T14:00:16.500Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584686,541807,30,'AD_Org_ID',TO_TIMESTAMP('2020-04-16 16:00:16','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','U',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Sektion',30,TO_TIMESTAMP('2020-04-16 16:00:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T14:00:16.502Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541807 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-16T14:01:47.022Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542958,0,584686,541808,30,541071,'C_VAT_Code_ID',TO_TIMESTAMP('2020-04-16 16:01:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct',0,'Y','N','Y','N','N','N','VAT Code',40,TO_TIMESTAMP('2020-04-16 16:01:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T14:01:47.026Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541808 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-16T14:02:38.792Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,148,0,584686,541809,30,540322,'Account_ID',TO_TIMESTAMP('2020-04-16 16:02:38','YYYY-MM-DD HH24:MI:SS'),100,'Verwendetes Konto','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto',50,TO_TIMESTAMP('2020-04-16 16:02:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T14:02:38.794Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541809 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-16T14:03:10.384Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2020-04-16 16:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541809
;

-- 2020-04-16T14:03:28.156Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543019,0,584686,541810,20,'showdetails',TO_TIMESTAMP('2020-04-16 16:03:28','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,'Y','N','Y','N','Y','N','Show Details',60,TO_TIMESTAMP('2020-04-16 16:03:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T14:03:28.157Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541810 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-16T14:04:43.512Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577675,0,TO_TIMESTAMP('2020-04-16 16:04:43','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Fibukonto - Kontoblatt 2','Fibukonto - Kontoblatt 2',TO_TIMESTAMP('2020-04-16 16:04:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T14:04:43.518Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577675 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-04-16T14:04:49.373Z
-- URL zum Konzept
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2020-04-16 16:04:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675
;

-- 2020-04-16T14:04:57.711Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,577675,541461,0,584686,TO_TIMESTAMP('2020-04-16 16:04:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Mehrwertsteuer-Verprobung(Jasper) 2','Y','N','N','N','N','Fibukonto - Kontoblatt 2',TO_TIMESTAMP('2020-04-16 16:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-16T14:04:57.717Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541461 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2020-04-16T14:04:57.719Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541461, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541461)
;

-- 2020-04-16T14:04:57.740Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(577675) 
;

-- 2020-04-16T14:04:57.776Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.778Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.778Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.779Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.779Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.779Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.779Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.780Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.780Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.780Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.781Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.781Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.781Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.781Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.782Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.782Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.783Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.783Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.783Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.784Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.784Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.785Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.785Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.785Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.786Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.786Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.786Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.786Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.787Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.787Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540968 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.787Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541064 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.788Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541075 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.788Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541094 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.789Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541088 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.789Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541099 AND AD_Tree_ID=10
;

-- 2020-04-16T14:04:57.789Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- 2020-04-16T14:05:28.305Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-04-16 16:05:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675 AND AD_Language='de_CH'
;

-- 2020-04-16T14:05:28.311Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577675,'de_CH') 
;

-- 2020-04-16T14:05:53.119Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Mehrwertsteuer-Verprobung 2', PrintName='Mehrwertsteuer-Verprobung 2',Updated=TO_TIMESTAMP('2020-04-16 16:05:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675 AND AD_Language='de_DE'
;

-- 2020-04-16T14:05:53.120Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577675,'de_DE') 
;

-- 2020-04-16T14:05:53.125Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(577675,'de_DE') 
;

-- 2020-04-16T14:05:53.132Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Mehrwertsteuer-Verprobung 2', Description=NULL, Help=NULL WHERE AD_Element_ID=577675
;

-- 2020-04-16T14:05:53.132Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Mehrwertsteuer-Verprobung 2', Description=NULL, Help=NULL WHERE AD_Element_ID=577675 AND IsCentrallyMaintained='Y'
;

-- 2020-04-16T14:05:53.132Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Mehrwertsteuer-Verprobung 2', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577675) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577675)
;

-- 2020-04-16T14:05:53.300Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Mehrwertsteuer-Verprobung 2', Name='Mehrwertsteuer-Verprobung 2' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577675)
;

-- 2020-04-16T14:05:53.302Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Mehrwertsteuer-Verprobung 2', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577675
;

-- 2020-04-16T14:05:53.303Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Mehrwertsteuer-Verprobung 2', Description=NULL, Help=NULL WHERE AD_Element_ID = 577675
;

-- 2020-04-16T14:05:53.304Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Mehrwertsteuer-Verprobung 2', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577675
;

-- 2020-04-16T14:05:59.975Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Mehrwertsteuer-Verprobung 2', PrintName='Mehrwertsteuer-Verprobung 2',Updated=TO_TIMESTAMP('2020-04-16 16:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675 AND AD_Language='de_CH'
;

-- 2020-04-16T14:05:59.976Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577675,'de_CH') 
;

-- 2020-04-16T14:06:01.613Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-04-16 16:06:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675 AND AD_Language='de_DE'
;

-- 2020-04-16T14:06:01.615Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577675,'de_DE') 
;

-- 2020-04-16T14:06:01.633Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(577675,'de_DE') 
;

-- 2020-04-16T14:06:26.919Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tax accounting report', PrintName='Tax accounting report',Updated=TO_TIMESTAMP('2020-04-16 16:06:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675 AND AD_Language='en_US'
;

-- 2020-04-16T14:06:26.922Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577675,'en_US') 
;

-- 2020-04-16T14:06:35.105Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Tax accounting report', PrintName='Tax accounting report',Updated=TO_TIMESTAMP('2020-04-16 16:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577675 AND AD_Language='nl_NL'
;

-- 2020-04-16T14:06:35.108Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577675,'nl_NL') 
;

-- 2020-04-16T14:07:05.958Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.959Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.959Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.960Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.960Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.960Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.961Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.961Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.961Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.962Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.962Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.962Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.963Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.963Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.963Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.964Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.964Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.964Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.965Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.965Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.965Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541461 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.966Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.966Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.967Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.967Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.967Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.968Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.968Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.968Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.969Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.969Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540968 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.969Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541064 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.970Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541075 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.970Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541094 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.971Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541088 AND AD_Tree_ID=10
;

-- 2020-04-16T14:07:05.971Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541099 AND AD_Tree_ID=10
;

