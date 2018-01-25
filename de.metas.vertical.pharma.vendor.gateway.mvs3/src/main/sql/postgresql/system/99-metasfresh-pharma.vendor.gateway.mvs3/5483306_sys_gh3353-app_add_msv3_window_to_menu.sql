
-- 2018-01-19T17:15:11.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541013,0,540397,TO_TIMESTAMP('2018-01-19 17:15:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.mvs3','MSV3_Vendor_Config','Y','N','N','N','N','MSV3 Lieferanten Konfiguration',TO_TIMESTAMP('2018-01-19 17:15:11','YYYY-MM-DD HH24:MI:SS'),100,'MSV3 Lieferanten Konfiguration')
;

-- 2018-01-19T17:15:11.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541013 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-01-19T17:15:11.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541013, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541013)
;

-- 2018-01-19T17:15:11.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=222 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=223 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=340 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53206 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=185 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=339 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=338 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=376 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=382 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=486 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=425 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=378 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=374 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=423 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=373 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=424 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:11.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=163, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541013 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:22.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540992 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:22.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540993 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:22.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541013 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:22.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540999 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:23.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540992 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:23.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540993 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:23.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540999 AND AD_Tree_ID=10
;

-- 2018-01-19T17:15:23.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541013 AND AD_Tree_ID=10
;

