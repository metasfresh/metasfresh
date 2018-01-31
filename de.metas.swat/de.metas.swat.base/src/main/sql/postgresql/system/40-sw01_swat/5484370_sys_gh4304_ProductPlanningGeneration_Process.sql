-- 2018-01-31T17:33:09.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540918,'Y','de.metas.ui.web.product.process.WEBUI_Create_Default_ProductPlanningData','N',TO_TIMESTAMP('2018-01-31 17:33:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'Create Default Product Planning Data','N','Y','Java',TO_TIMESTAMP('2018-01-31 17:33:09','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Create_Default_ProductPlanningData')
;

-- 2018-01-31T17:33:09.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540918 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-01-31T17:33:20.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2018-01-31 17:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540918
;

-- 2018-01-31T17:34:41.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,541030,0,540918,TO_TIMESTAMP('2018-01-31 17:34:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','WEBUI_Create_Default_ProductPlanningData','Y','N','N','N','N','Create Default Product Planning Data',TO_TIMESTAMP('2018-01-31 17:34:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-31T17:34:41.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541030 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-01-31T17:34:41.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541030, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541030)
;

-- 2018-01-31T17:34:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541021 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:45.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541021 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:45.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:45.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:45.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:45.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:45.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- 2018-01-31T17:34:45.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

