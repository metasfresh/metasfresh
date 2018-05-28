-- 2018-03-26T18:33:21.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,JasperReport,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540946,'Y','org.compiere.report.ReportStarter',TO_TIMESTAMP('2018-03-26 18:33:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','Y','N','Y','N','Y','@PREFIX@de/metas/reports/revenue_po_report/report.xls','Revenue PO Excel','N','Y','','Java',TO_TIMESTAMP('2018-03-26 18:33:21','YYYY-MM-DD HH24:MI:SS'),100,'Revenue PO Excel (Jasper)')
;

-- 2018-03-26T18:33:21.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540946 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-03-26T18:34:32.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,540946,541274,19,'AD_Org_ID',TO_TIMESTAMP('2018-03-26 18:34:32','YYYY-MM-DD HH24:MI:SS'),100,'@AD_Org_ID@','Organisatorische Einheit des Mandanten','de.metas.fresh',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion','1=1',10,TO_TIMESTAMP('2018-03-26 18:34:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-26T18:34:32.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541274 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-03-26T18:35:01.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540946,541275,15,'Base_Period_Start',TO_TIMESTAMP('2018-03-26 18:35:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Zeitperiode von',20,TO_TIMESTAMP('2018-03-26 18:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-26T18:35:01.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541275 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-03-26T18:35:29.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540946,541277,15,'Base_Period_End',TO_TIMESTAMP('2018-03-26 18:35:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','Y','N','Zeitperiode bis',30,TO_TIMESTAMP('2018-03-26 18:35:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-26T18:35:29.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541277 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-03-26T18:35:32.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-03-26 18:35:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541275
;

-- 2018-03-26T18:37:55.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540946,541278,15,'Comp_Period_Start',TO_TIMESTAMP('2018-03-26 18:37:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Vergleichsperiode von',40,TO_TIMESTAMP('2018-03-26 18:37:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-26T18:37:55.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541278 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-03-26T18:38:17.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540946,541279,15,'Comp_Period_End',TO_TIMESTAMP('2018-03-26 18:38:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Vergleichsperiode bis',50,TO_TIMESTAMP('2018-03-26 18:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-26T18:38:17.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541279 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-03-26T18:39:15.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1383,0,540946,541280,19,'C_BP_Group_ID',TO_TIMESTAMP('2018-03-26 18:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','de.metas.fresh',0,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','Geschäftspartnergruppe',60,TO_TIMESTAMP('2018-03-26 18:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-26T18:39:15.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541280 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-03-26T18:43:46.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.revenue_PO_report (@Base_Period_Start@, @Base_Period_End@, @Comp_Period_Start/NULL@, @Comp_Period_End/NULL@, ''N'', NULL, NULL, NULL, NULL, NULL, @AD_Org_ID@, NULL, @C_BP_Group_ID/NULL@);',Updated=TO_TIMESTAMP('2018-03-26 18:43:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540946
;

-- 2018-03-26T18:47:18.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,541064,0,540946,TO_TIMESTAMP('2018-03-26 18:47:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Revenue PO Excel (Jasper)','Y','N','N','N','N','Revenue PO Excel',TO_TIMESTAMP('2018-03-26 18:47:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-26T18:47:18.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541064 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-03-26T18:47:18.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541064, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541064)
;

-- 2018-03-26T18:47:19.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540968 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541059 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541058 AND AD_Tree_ID=10
;

-- 2018-03-26T18:47:19.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541064 AND AD_Tree_ID=10
;

-- 2018-03-26T18:49:50.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.revenue_PO_report (@Base_Period_Start@::date, @Base_Period_End@::date, @Comp_Period_Start/NULL@::date, @Comp_Period_End/NULL@::date, ''N'', NULL, NULL, NULL, NULL, NULL, @AD_Org_ID@, NULL, @C_BP_Group_ID/NULL@);',Updated=TO_TIMESTAMP('2018-03-26 18:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540946
;

-- 2018-03-26T18:51:58.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.revenue_PO_report (''@Base_Period_Start@''::date, ''@Base_Period_End@''::date, ''@Comp_Period_Start/NULL@''::date, ''@Comp_Period_End/NULL@''::date, ''N'', NULL, NULL, NULL, NULL, NULL, @AD_Org_ID@, NULL, @C_BP_Group_ID/NULL@);',Updated=TO_TIMESTAMP('2018-03-26 18:51:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540946
;

