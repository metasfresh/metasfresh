-- 2017-06-03T15:05:12.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Description='Mein Benutzer Profil', Name='Mein Profil',Updated=TO_TIMESTAMP('2017-06-03 15:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=53100
;

-- 2017-06-03T15:05:12.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET IsTranslated='N' WHERE AD_Window_ID=53100
;

-- 2017-06-03T15:05:12.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Mein Benutzer Profil', IsActive='Y', Name='Mein Profil',Updated=TO_TIMESTAMP('2017-06-03 15:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53246
;

-- 2017-06-03T15:05:12.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=53246
;

-- 2017-06-03T15:08:34.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540849,0,53100,TO_TIMESTAMP('2017-06-03 15:08:34','YYYY-MM-DD HH24:MI:SS'),100,'Mein Benutzer Profil','D','_Mein_Profil','Y','N','N','N','N','Mein Profil',TO_TIMESTAMP('2017-06-03 15:08:34','YYYY-MM-DD HH24:MI:SS'),100,'Mein Profil')
;

-- 2017-06-03T15:08:34.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540849 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-03T15:08:34.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540849, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540849)
;

-- 2017-06-03T15:08:34.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=334 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=498 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=224 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=514 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=336 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=341 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=170 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=465 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=101 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=294 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=395 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=296 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=221 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=233 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=290 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=109 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540375 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=50008 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

