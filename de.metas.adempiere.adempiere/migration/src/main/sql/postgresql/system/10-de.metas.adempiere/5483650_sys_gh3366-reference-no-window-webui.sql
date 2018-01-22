-- 2018-01-22T15:06:49.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541014,0,540355,TO_TIMESTAMP('2018-01-22 15:06:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.commission','Bankauszug_Referenz','Y','N','N','N','N','Bankauszug Referenz',TO_TIMESTAMP('2018-01-22 15:06:48','YYYY-MM-DD HH24:MI:SS'),100,'Referenz Nr.')
;

-- 2018-01-22T15:06:49.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541014 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-01-22T15:06:49.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541014, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541014)
;

-- 2018-01-22T15:06:49.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:49.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541014 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541014 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2018-01-22T15:06:52.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2018-01-22T15:12:04.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=541014
;

-- 2018-01-22T15:12:04.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=541014
;

-- 2018-01-22T15:12:04.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=541014 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-01-22T15:15:17.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541014 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2018-01-22T15:15:17.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541015,0,540157,TO_TIMESTAMP('2018-01-22 15:16:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.refid','Referenz_Nr.','Y','N','N','N','N','Referenz Nr.',TO_TIMESTAMP('2018-01-22 15:16:21','YYYY-MM-DD HH24:MI:SS'),100,'Referenz Nr.')
;

-- 2018-01-22T15:16:21.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541015 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-01-22T15:16:21.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541015, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541015)
;

-- 2018-01-22T15:16:21.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=164 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=280 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=158 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=522 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=118 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=169 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=433 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=352 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=434 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=435 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540671 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540677 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:21.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541014 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2018-01-22T15:16:25.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541016,0,540156,TO_TIMESTAMP('2018-01-22 15:17:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.refid','Referenz_ Nr_Art','Y','N','N','N','N','Referenz Nr. Art',TO_TIMESTAMP('2018-01-22 15:17:03','YYYY-MM-DD HH24:MI:SS'),100,'Referenz Nr. Art')
;

-- 2018-01-22T15:17:03.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541016 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-01-22T15:17:03.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541016, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541016)
;

-- 2018-01-22T15:17:03.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541014 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:03.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541014 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2018-01-22T15:17:05.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2018-01-22T15:18:41.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Referenz Nr.',Updated=TO_TIMESTAMP('2018-01-22 15:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540157
;

-- 2018-01-22T15:18:41.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Referenz Nr.',Updated=TO_TIMESTAMP('2018-01-22 15:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540402
;

-- 2018-01-22T15:22:47.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Referenz Nr. Art',Updated=TO_TIMESTAMP('2018-01-22 15:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540156
;

-- 2018-01-22T15:22:47.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Referenz Nr. Art',Updated=TO_TIMESTAMP('2018-01-22 15:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540401
;

-- 2018-01-22T15:23:02.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540435,540618,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-22T15:23:02.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540618 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-22T15:23:02.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540826,540618,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:02.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540827,540618,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:02.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540826,541404,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:02.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550730,0,540435,541404,550393,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Ihre Kunden- oder Lieferantennummer beim Geschäftspartner','Die "Referenznummer" kann auf Bestellungen und Rechnungen gedruckt werden um Ihre Dokumente beim Geschäftspartner einfacher zuordnen zu können.','Y','N','Y','Y','N','Referenznummer',10,10,0,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:02.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550729,0,540435,541404,550394,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Reference No Type',20,20,0,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:02.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551879,0,540435,541404,550395,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein manueller Vorgang','Das Selektionsfeld "Manuell" zeigt an, ob dieser Vorang manuell durchgeführt wird.','Y','N','Y','Y','N','Manuell',30,30,0,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:02.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540436,540619,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-22T15:23:02.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540619 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-22T15:23:03.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540828,540619,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-22 15:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:03.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540828,541405,TO_TIMESTAMP('2018-01-22 15:23:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-22 15:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:03.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550736,0,540436,541405,550396,TO_TIMESTAMP('2018-01-22 15:23:03','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,10,0,TO_TIMESTAMP('2018-01-22 15:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T15:23:03.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550735,0,540436,541405,550397,TO_TIMESTAMP('2018-01-22 15:23:03','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','Datensatz-ID',0,20,0,TO_TIMESTAMP('2018-01-22 15:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T16:16:49.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2018-01-22 16:16:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540435
;

-- 2018-01-22T16:17:21.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540827,541406,TO_TIMESTAMP('2018-01-22 16:17:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-01-22 16:17:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T16:17:26.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540827,541407,TO_TIMESTAMP('2018-01-22 16:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2018-01-22 16:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T16:18:41.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541406, SeqNo=10,Updated=TO_TIMESTAMP('2018-01-22 16:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550395
;

-- 2018-01-22T16:19:03.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550731,0,540435,541407,550399,'F',TO_TIMESTAMP('2018-01-22 16:19:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2018-01-22 16:19:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T16:19:13.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550727,0,540435,541407,550400,'F',TO_TIMESTAMP('2018-01-22 16:19:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2018-01-22 16:19:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-22T16:19:59.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET UIStyle='primary', WidgetSize='L',Updated=TO_TIMESTAMP('2018-01-22 16:19:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550393
;

-- 2018-01-22T16:20:06.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-01-22 16:20:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550394
;

-- 2018-01-22T16:20:16.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-01-22 16:20:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550399
;

-- 2018-01-22T16:20:19.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2018-01-22 16:20:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550400
;

