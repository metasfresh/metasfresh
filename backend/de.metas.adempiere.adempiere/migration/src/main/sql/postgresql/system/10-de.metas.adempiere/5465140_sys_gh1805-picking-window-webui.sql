-- 2017-06-13T18:08:09.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540868,0,540345,TO_TIMESTAMP('2017-06-13 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Items ready to be picked','de.metas.inoutcandidate','M_Packageable_V','Y','N','N','Y','N','Packageables',TO_TIMESTAMP('2017-06-13 18:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Kommissionier Terminal (prototype)')
;

-- 2017-06-13T18:08:09.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540868 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-13T18:08:09.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540868, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540868)
;

-- 2017-06-13T18:08:10.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=219 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=364 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=116 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=387 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=402 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=401 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=404 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=405 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=410 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=258 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=260 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=398 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=403 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:10.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:14.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:14.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:14.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:14.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:14.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:14.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:14.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- 2017-06-13T18:08:14.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

