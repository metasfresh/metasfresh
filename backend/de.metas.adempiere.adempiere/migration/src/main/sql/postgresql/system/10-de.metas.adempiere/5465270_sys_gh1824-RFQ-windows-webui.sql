-- 2017-06-15T20:08:50.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540877,0,315,TO_TIMESTAMP('2017-06-15 20:08:49','YYYY-MM-DD HH24:MI:SS'),100,'Ausschreibung verwalten','de.metas.rfq','_C_RfQ','Y','N','N','N','N','Ausschreibung',TO_TIMESTAMP('2017-06-15 20:08:49','YYYY-MM-DD HH24:MI:SS'),100,'Ausschreibung')
;

-- 2017-06-15T20:08:50.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540877 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-15T20:08:50.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540877, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540877)
;

-- 2017-06-15T20:08:50.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540817 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:50.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540817 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- 2017-06-15T20:08:53.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- 2017-06-15T20:09:37.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540878,0,314,TO_TIMESTAMP('2017-06-15 20:09:37','YYYY-MM-DD HH24:MI:SS'),100,'Ausschreibungs-Themen und -Teilnehmer verwalten','de.metas.rfq','_C_RfQ_Topic','Y','N','N','N','N','Ausschreibung Thema',TO_TIMESTAMP('2017-06-15 20:09:37','YYYY-MM-DD HH24:MI:SS'),100,'Ausschreibung Thema')
;

-- 2017-06-15T20:09:37.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540878 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-15T20:09:37.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540878, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540878)
;

-- 2017-06-15T20:10:21.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000051, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540878 AND AD_Tree_ID=10
;

-- 2017-06-15T20:10:21.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000051, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540876 AND AD_Tree_ID=10
;

-- 2017-06-15T20:10:21.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000051, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540873 AND AD_Tree_ID=10
;

-- 2017-06-15T20:10:21.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000051, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540874 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:18.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540879,0,324,TO_TIMESTAMP('2017-06-15 20:11:18','YYYY-MM-DD HH24:MI:SS'),100,'Ausschreibungs-Antworten verwalten','de.metas.rfq','_C_RfQResponse','Y','N','N','N','N','Ausschreibung Antwort',TO_TIMESTAMP('2017-06-15 20:11:18','YYYY-MM-DD HH24:MI:SS'),100,'Ausschreibung Antwort')
;

-- 2017-06-15T20:11:18.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540879 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-15T20:11:18.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540879, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540879)
;

-- 2017-06-15T20:11:19.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540817 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:19.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540879 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540817 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540879 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- 2017-06-15T20:11:22.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540880,0,540295,TO_TIMESTAMP('2017-06-15 20:12:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.rfq','_C_RFQ_ReponseLine','Y','N','N','N','N','Ausschreibung Antwort Zeile',TO_TIMESTAMP('2017-06-15 20:12:21','YYYY-MM-DD HH24:MI:SS'),100,'Ausschreibung Antwort Zeile')
;

-- 2017-06-15T20:12:21.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540880 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-15T20:12:21.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540880, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540880)
;

-- 2017-06-15T20:12:21.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540817 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540879 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:21.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540880 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540817 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540879 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540880 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- 2017-06-15T20:12:24.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- 2017-06-15T20:17:26.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,613,540301,TO_TIMESTAMP('2017-06-15 20:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-15 20:17:26','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-15T20:17:26.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540301 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-15T20:17:26.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540411,540301,TO_TIMESTAMP('2017-06-15 20:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-15 20:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:26.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540412,540301,TO_TIMESTAMP('2017-06-15 20:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-15 20:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540411,540702,TO_TIMESTAMP('2017-06-15 20:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-15 20:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9322,0,613,540702,545845,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9348,0,613,540702,545846,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10317,0,613,540702,545847,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Beleg Nr.',30,30,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9343,0,613,540702,545848,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9324,0,613,540702,545849,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9330,0,613,540702,545850,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Topic for Request for Quotations','Ein Ausschreibungs-Thema erlaubt Ihnen, eine Liste von potentiellen Lieferanten zur Beantwortung einer Ausschreibung zusammenzustellen.','Y','N','Y','Y','N','Ausschreibungs-Thema',60,60,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556959,0,613,540702,545851,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ausschreibung Art',70,70,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556999,0,613,540702,545852,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Vertragsbedingungen',80,80,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9320,0,613,540702,545853,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','Y','N','Währung',90,90,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10045,0,613,540702,545854,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Vertriebsbeauftragter','The Sales Representative indicates the Sales Rep for this Region.  Any Sales Rep must be a valid internal user.','Y','N','Y','Y','N','Vertriebsbeauftragter',100,100,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557048,0,613,540702,545855,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bid start date',110,110,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557049,0,613,540702,545856,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bid end date',120,120,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9340,0,613,540702,545857,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Datum der Antwort','Datum der Antwort','Y','N','Y','Y','N','Antwort-datum',130,130,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9344,0,613,540702,545858,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Arbeit (geplant) begonnen wird.','Y','N','Y','Y','N','Arbeitsbeginn',140,140,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9323,0,613,540702,545859,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Arbeit (geplant) fertiggestellt ist.','Y','N','Y','Y','N','Arbeit fertiggestellt',150,150,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9321,0,613,540702,545860,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Anzehl der (geplanten) Tage bis zur Auslieferung','Y','N','Y','Y','N','Auslieferungstage',160,160,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9346,0,613,540702,545861,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Are Resonses to the Request for Quotation accepted','If selected, responses for the RfQ are accepted','Y','N','Y','Y','N','Responses Accepted',170,170,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557025,0,613,540702,545862,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','Y','N','Belegstatus',180,180,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9329,0,613,540702,545863,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',190,190,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,615,540302,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-15T20:17:27.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540302 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-15T20:17:27.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540413,540302,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540413,540703,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9375,0,615,540703,545864,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','Y','N','Zeile Nr.',0,10,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556958,0,615,540703,545865,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Lieferprodukt',0,20,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556950,0,615,540703,545866,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','Maßeinheit',0,30,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556949,0,615,540703,545867,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','Menge',0,40,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9381,0,615,540703,545868,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,50,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557029,0,615,540703,545869,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Selected winners count',0,60,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:17:27.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557030,0,615,540703,545870,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Selected winners Qty',0,70,0,TO_TIMESTAMP('2017-06-15 20:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:19:19.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,613,540303,TO_TIMESTAMP('2017-06-15 20:19:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-15 20:19:19','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-06-15T20:19:19.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540303 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-15T20:19:35.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540411,540704,TO_TIMESTAMP('2017-06-15 20:19:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-15 20:19:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:19:48.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=11, UIStyle='',Updated=TO_TIMESTAMP('2017-06-15 20:19:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540702
;

-- 2017-06-15T20:20:02.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9343,0,613,540704,545871,TO_TIMESTAMP('2017-06-15 20:20:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-06-15 20:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:20:12.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557048,0,613,540704,545872,TO_TIMESTAMP('2017-06-15 20:20:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beginn',20,0,0,TO_TIMESTAMP('2017-06-15 20:20:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:20:20.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557049,0,613,540704,545873,TO_TIMESTAMP('2017-06-15 20:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ende',30,0,0,TO_TIMESTAMP('2017-06-15 20:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:20:36.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9340,0,613,540704,545874,TO_TIMESTAMP('2017-06-15 20:20:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Antwort Datum',40,0,0,TO_TIMESTAMP('2017-06-15 20:20:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:20:59.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540411,540705,TO_TIMESTAMP('2017-06-15 20:20:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-06-15 20:20:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:21:03.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=100,Updated=TO_TIMESTAMP('2017-06-15 20:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540702
;

-- 2017-06-15T20:21:17.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9324,0,613,540705,545875,TO_TIMESTAMP('2017-06-15 20:21:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-06-15 20:21:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:21:40.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556959,0,613,540705,545876,TO_TIMESTAMP('2017-06-15 20:21:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ausschreibung Art',20,0,0,TO_TIMESTAMP('2017-06-15 20:21:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:21:50.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9320,0,613,540705,545877,TO_TIMESTAMP('2017-06-15 20:21:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Währung',30,0,0,TO_TIMESTAMP('2017-06-15 20:21:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:22:11.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540412,540706,TO_TIMESTAMP('2017-06-15 20:22:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','docno',10,TO_TIMESTAMP('2017-06-15 20:22:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:22:24.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10317,0,613,540706,545878,TO_TIMESTAMP('2017-06-15 20:22:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beleg Nr.',10,0,0,TO_TIMESTAMP('2017-06-15 20:22:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:22:42.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9344,0,613,540706,545879,TO_TIMESTAMP('2017-06-15 20:22:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beginn Bearbeitung',20,0,0,TO_TIMESTAMP('2017-06-15 20:22:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:22:54.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9323,0,613,540706,545880,TO_TIMESTAMP('2017-06-15 20:22:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ende Bearbeitung',30,0,0,TO_TIMESTAMP('2017-06-15 20:22:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:23:11.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540412,540707,TO_TIMESTAMP('2017-06-15 20:23:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',20,TO_TIMESTAMP('2017-06-15 20:23:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:23:23.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9329,0,613,540707,545881,TO_TIMESTAMP('2017-06-15 20:23:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2017-06-15 20:23:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:23:29.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540412,540708,TO_TIMESTAMP('2017-06-15 20:23:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-06-15 20:23:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:23:37.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9348,0,613,540708,545882,TO_TIMESTAMP('2017-06-15 20:23:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-06-15 20:23:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:23:45.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9322,0,613,540708,545883,TO_TIMESTAMP('2017-06-15 20:23:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-06-15 20:23:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:24:17.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545845
;

-- 2017-06-15T20:24:17.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545846
;

-- 2017-06-15T20:24:17.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545847
;

-- 2017-06-15T20:24:17.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545848
;

-- 2017-06-15T20:24:17.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545849
;

-- 2017-06-15T20:24:17.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545851
;

-- 2017-06-15T20:24:31.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545853
;

-- 2017-06-15T20:24:31.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545855
;

-- 2017-06-15T20:24:31.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545856
;

-- 2017-06-15T20:24:31.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545857
;

-- 2017-06-15T20:24:31.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545858
;

-- 2017-06-15T20:24:40.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545859
;

-- 2017-06-15T20:24:40.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545863
;

-- 2017-06-15T20:25:11.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9330,0,613,540705,545884,TO_TIMESTAMP('2017-06-15 20:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ausschreibung Thema',40,0,0,TO_TIMESTAMP('2017-06-15 20:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:25:15.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2017-06-15 20:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545884
;

-- 2017-06-15T20:25:52.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545884
;

-- 2017-06-15T20:26:27.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9330,0,613,540704,545885,TO_TIMESTAMP('2017-06-15 20:26:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ausschreibung Thema',5,0,0,TO_TIMESTAMP('2017-06-15 20:26:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:27:18.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10045,0,613,540707,545886,TO_TIMESTAMP('2017-06-15 20:27:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bearbeiter',20,0,0,TO_TIMESTAMP('2017-06-15 20:27:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:27:35.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545850
;

-- 2017-06-15T20:27:40.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545854
;

-- 2017-06-15T20:28:00.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540414,540303,TO_TIMESTAMP('2017-06-15 20:28:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-15 20:28:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-15T20:28:16.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2017-06-15 20:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540702
;

-- 2017-06-15T20:28:24.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540414, SeqNo=10,Updated=TO_TIMESTAMP('2017-06-15 20:28:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540702
;

-- 2017-06-15T20:28:34.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-15 20:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545852
;

-- 2017-06-15T20:28:34.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-15 20:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545860
;

-- 2017-06-15T20:28:35.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-15 20:28:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545861
;

-- 2017-06-15T20:28:36.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-15 20:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545862
;

-- 2017-06-15T20:29:15.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-06-15 20:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540702
;

-- 2017-06-15T20:33:09.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545860
;

-- 2017-06-15T20:33:09.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545861
;

-- 2017-06-15T20:33:09.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545852
;

-- 2017-06-15T20:33:09.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545885
;

-- 2017-06-15T20:33:09.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545871
;

-- 2017-06-15T20:33:09.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545878
;

-- 2017-06-15T20:33:09.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545886
;

-- 2017-06-15T20:33:09.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545872
;

-- 2017-06-15T20:33:09.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545873
;

-- 2017-06-15T20:33:09.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545874
;

-- 2017-06-15T20:33:09.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545875
;

-- 2017-06-15T20:33:09.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545876
;

-- 2017-06-15T20:33:09.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545877
;

-- 2017-06-15T20:33:09.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545881
;

-- 2017-06-15T20:33:09.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545862
;

-- 2017-06-15T20:33:09.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545882
;

-- 2017-06-15T20:33:09.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2017-06-15 20:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545883
;

-- 2017-06-15T20:33:29.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-06-15 20:33:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545885
;

-- 2017-06-15T20:33:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-06-15 20:33:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545871
;

-- 2017-06-15T20:33:30.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-06-15 20:33:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545874
;

-- 2017-06-15T20:33:30.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-06-15 20:33:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545878
;

-- 2017-06-15T20:36:56.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-06-15 20:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545867
;

-- 2017-06-15T20:36:56.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-06-15 20:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545866
;

-- 2017-06-15T20:37:09.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-06-15 20:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545864
;

-- 2017-06-15T20:37:09.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-06-15 20:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545865
;

-- 2017-06-15T20:37:09.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-06-15 20:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545867
;

-- 2017-06-15T20:37:10.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-06-15 20:37:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545866
;

