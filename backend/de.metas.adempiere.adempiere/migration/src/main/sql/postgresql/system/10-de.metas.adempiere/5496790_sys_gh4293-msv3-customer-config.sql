-- 2018-06-28T12:47:17.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541119,0,540423,TO_TIMESTAMP('2018-06-28 12:47:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.msv3.server','MSV3_Kunden_Konfiguration','Y','N','N','N','N','MSV3 Kunden Konfiguration',TO_TIMESTAMP('2018-06-28 12:47:17','YYYY-MM-DD HH24:MI:SS'),100,'MSV3 Kunden Konfiguration')
;

-- 2018-06-28T12:47:17.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541119 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-06-28T12:47:17.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541119, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541119)
;

-- 2018-06-28T12:47:18.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541013 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541026 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541027 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541028 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541023 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541024 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541025 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541029 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541031 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541032 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541065 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:18.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541119 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541013 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541026 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541027 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541028 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541023 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541024 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541025 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541029 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541031 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541032 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541119 AND AD_Tree_ID=10
;

-- 2018-06-28T12:47:21.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541022, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541065 AND AD_Tree_ID=10
;

-- 2018-06-28T12:48:07.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-28 12:48:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='MSV3 Customer Configuration',WEBUI_NameBrowse='MSV3 Customer Configuration' WHERE AD_Menu_ID=541119 AND AD_Language='en_US'
;

