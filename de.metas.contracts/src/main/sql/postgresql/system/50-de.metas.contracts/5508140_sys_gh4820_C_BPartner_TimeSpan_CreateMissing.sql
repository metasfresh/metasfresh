




-- 2018-12-12T17:49:54.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,541037,'Y','N',TO_TIMESTAMP('2018-12-12 17:49:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N','N','N','N','N','Y',0,'C_BPartner_TimeSpan_CreateMissing','N','Y','Java',TO_TIMESTAMP('2018-12-12 17:49:54','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_TimeSpan_CreateMissing')
;

-- 2018-12-12T17:49:54.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541037 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-12-12T17:50:01.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.contracts.process.C_BPartner_TimeSpan_CreateMissing',Updated=TO_TIMESTAMP('2018-12-12 17:50:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541037
;

-- 2018-12-12T17:50:40.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575921,0,TO_TIMESTAMP('2018-12-12 17:50:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Create Missing Partner Time Span','Create Missing Partner Time Span',TO_TIMESTAMP('2018-12-12 17:50:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T17:50:40.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575921 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-12-12T17:51:09.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,575921,541170,0,541037,TO_TIMESTAMP('2018-12-12 17:51:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','C_BPartner_TimeSpan_CreateMissing','Y','N','N','N','N','C_BPartner_TimeSpan_CreateMissing',TO_TIMESTAMP('2018-12-12 17:51:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T17:51:09.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541170 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-12-12T17:51:09.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541170, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541170)
;

-- 2018-12-12T17:51:09.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(575921) 
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540889 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540954 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540955 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541169 AND AD_Tree_ID=10
;

-- 2018-12-12T17:51:09.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

