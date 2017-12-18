
-- 2017-11-27T15:26:27.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,Help,IsActive,IsBetaFunctionality,IsDefault,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540376,TO_TIMESTAMP('2017-11-27 15:26:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','*** placeholder window ***','Y','N','N','N','Y','Material-Cockpit','N',TO_TIMESTAMP('2017-11-27 15:26:27','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2017-11-27T15:26:27.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540376 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2017-11-28T15:28:19.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:19.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:19.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:19.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:19.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:19.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:19.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:22.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:22.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:22.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:22.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:22.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:22.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:22.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- 2017-11-28T15:28:22.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- 2017-11-28T15:29:01.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Materialcockpit',Updated=TO_TIMESTAMP('2017-11-28 15:29:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540376
;

-- 2017-11-28T15:29:10.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-28 15:29:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Materialcockpit' WHERE AD_Window_ID=540376 AND AD_Language='de_CH'
;

-- 2017-11-28T15:29:18.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-28 15:29:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Materialcockpit' WHERE AD_Window_ID=540376 AND AD_Language='en_US'
;

-- 2017-11-28T15:29:43.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540981,0,540376,TO_TIMESTAMP('2017-11-28 15:29:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Materialcockpit','Y','N','N','N','N','Material Cockpit',TO_TIMESTAMP('2017-11-28 15:29:43','YYYY-MM-DD HH24:MI:SS'),100,'Material Cockpit')
;

-- 2017-11-28T15:29:43.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540981 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-11-28T15:29:43.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540981, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540981)
;

-- 2017-11-28T15:29:44.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53182 AND AD_Tree_ID=10
;

-- 2017-11-28T15:29:44.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=478 AND AD_Tree_ID=10
;

-- 2017-11-28T15:29:44.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53183 AND AD_Tree_ID=10
;

-- 2017-11-28T15:29:44.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53181 AND AD_Tree_ID=10
;

-- 2017-11-28T15:29:44.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53180, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- 2017-11-28T15:30:05.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

