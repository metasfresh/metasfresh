-- 2018-05-21T15:17:49.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Rückvergütung', Value='Rückvergütung (Jasper)',Updated=TO_TIMESTAMP('2018-05-21 15:17:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540971
;

-- 2018-05-21T15:17:49.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Rückvergütung',Updated=TO_TIMESTAMP('2018-05-21 15:17:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541094
;

-- 2018-05-21T15:17:57.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-21 15:17:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Rückvergütung' WHERE AD_Process_ID=540971 AND AD_Language='de_CH'
;

-- 2018-05-21T15:18:00.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-21 15:18:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540971 AND AD_Language='en_US'
;

-- 2018-05-21T15:18:55.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-21 15:18:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Rückvergütung' WHERE AD_Menu_ID=541094 AND AD_Language='de_CH'
;

-- 2018-05-21T15:18:59.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-21 15:18:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Menu_ID=541094 AND AD_Language='en_US'
;

-- 2018-05-21T15:21:03.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,541095,0,540971,TO_TIMESTAMP('2018-05-21 15:21:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Rückvergütung (Jasper)','Y','N','N','N','N','Rückvergütung',TO_TIMESTAMP('2018-05-21 15:21:03','YYYY-MM-DD HH24:MI:SS'),100,'Rückvergütung')
;

-- 2018-05-21T15:21:03.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541095 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-05-21T15:21:03.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541095, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541095)
;

-- 2018-05-21T15:21:04.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2018-05-21T15:21:04.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540935 AND AD_Tree_ID=10
;

-- 2018-05-21T15:21:04.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540958 AND AD_Tree_ID=10
;

-- 2018-05-21T15:21:04.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541074 AND AD_Tree_ID=10
;

-- 2018-05-21T15:21:04.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541095 AND AD_Tree_ID=10
;

-- 2018-05-21T15:21:21.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-21 15:21:21','YYYY-MM-DD HH24:MI:SS'),Name='Yearly Bonus' WHERE AD_Menu_ID=541095 AND AD_Language='en_US'
;

-- 2018-05-21T15:21:24.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-21 15:21:24','YYYY-MM-DD HH24:MI:SS'),WEBUI_NameBrowse='Yearly Bonus' WHERE AD_Menu_ID=541095 AND AD_Language='en_US'
;

-- 2018-05-21T15:21:26.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-21 15:21:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Menu_ID=541095 AND AD_Language='en_US'
;




-- 2018-05-21T16:17:19.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='Y', ShowHelp='Y',Updated=TO_TIMESTAMP('2018-05-21 16:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540971
;

