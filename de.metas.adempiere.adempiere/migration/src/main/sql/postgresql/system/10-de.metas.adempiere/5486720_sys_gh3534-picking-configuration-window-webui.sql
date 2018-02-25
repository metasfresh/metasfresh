-- 2018-02-25T12:40:57.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541046,0,540418,TO_TIMESTAMP('2018-02-25 12:40:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','_Kommissionierung_Konfiguration','Y','N','N','N','N','Kommissionierung Konfiguration',TO_TIMESTAMP('2018-02-25 12:40:57','YYYY-MM-DD HH24:MI:SS'),100,'Kommissionierung Konfiguration')
;

-- 2018-02-25T12:40:57.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541046 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-02-25T12:40:57.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541046, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541046)
;

-- 2018-02-25T12:40:57.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=114 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=108 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=115 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53225 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53226 AND AD_Tree_ID=10
;

-- 2018-02-25T12:40:57.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=159, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541046 AND AD_Tree_ID=10
;

-- 2018-02-25T12:41:03.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541046 AND AD_Tree_ID=10
;

-- 2018-02-25T12:41:03.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540991 AND AD_Tree_ID=10
;

-- 2018-02-25T12:41:22.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 12:41:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Picking Config',WEBUI_NameBrowse='Picking Config' WHERE AD_Menu_ID=541046 AND AD_Language='en_US'
;

-- 2018-02-25T12:41:34.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540991
;

-- 2018-02-25T12:41:34.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=540991
;

-- 2018-02-25T12:41:34.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540991 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-02-25T12:43:25.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540382,Updated=TO_TIMESTAMP('2018-02-25 12:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540873
;

-- 2018-02-25T12:44:04.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kommissionierung Konfiguration',Updated=TO_TIMESTAMP('2018-02-25 12:44:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541049
;

