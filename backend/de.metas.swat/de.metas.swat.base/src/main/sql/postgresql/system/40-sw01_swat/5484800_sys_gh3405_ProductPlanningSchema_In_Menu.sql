-- 2018-02-07T14:49:18.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,541035,0,540398,TO_TIMESTAMP('2018-02-07 14:49:18','YYYY-MM-DD HH24:MI:SS'),100,'D','M_Product_Planning_Schema','Y','N','N','N','N','Product Planning Schema',TO_TIMESTAMP('2018-02-07 14:49:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-07T14:49:18.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541035 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-02-07T14:49:18.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541035, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541035)
;

-- 2018-02-07T14:49:19.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541021 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:19.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:19.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:19.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:19.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:19.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:19.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:19.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:33.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541021 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:33.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:33.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:33.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:33.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:33.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:33.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- 2018-02-07T14:49:33.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

