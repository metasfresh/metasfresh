-- 2018-05-04T18:31:32.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,UpdatedBy,JasperReport,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,AD_Org_ID,Name,Classname,Value,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-05-04 18:31:32','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-05-04 18:31:32','YYYY-MM-DD HH24:MI:SS'),'N','Y','3','S','Y','N',100,'@PREFIX@de/metas/reports/personal_data/report.jasper',540958,'N','Y','N','Java','N','N',0,'Personal Data','org.compiere.report.ReportStarter','Personal Data (Jasper)','de.metas.fresh')
;

-- 2018-05-04T18:31:32.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540958 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-05-04T18:33:09.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,541088,0,540958,TO_TIMESTAMP('2018-05-04 18:33:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Personal Data (Jasper)','Y','N','N','N','N','Personal Data',TO_TIMESTAMP('2018-05-04 18:33:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-04T18:33:09.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541088 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-05-04T18:33:09.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541088, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541088)
;

-- 2018-05-04T18:33:09.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540968 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541064 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541075 AND AD_Tree_ID=10
;

-- 2018-05-04T18:33:09.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541088 AND AD_Tree_ID=10
;

