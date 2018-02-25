-- 2018-02-25T13:37:38.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Window_ID=540382, InternalName='_Kommissionierung_Profil', Name='Kommissionierung Profil', WEBUI_NameBrowse='Kommissionierung Profil',Updated=TO_TIMESTAMP('2018-02-25 13:37:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541046
;

-- 2018-02-25T13:37:58.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 13:37:58','YYYY-MM-DD HH24:MI:SS'),Name='Picking Profile',WEBUI_NameBrowse='Picking Profile' WHERE AD_Menu_ID=541046 AND AD_Language='en_US'
;

-- 2018-02-25T13:38:32.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 13:38:32','YYYY-MM-DD HH24:MI:SS'),Name='Picking Profile' WHERE AD_Window_ID=540382 AND AD_Language='en_US'
;

-- 2018-02-25T13:41:15.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=541046
;

-- 2018-02-25T13:41:15.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=541046
;

-- 2018-02-25T13:41:15.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=541046 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-02-25T13:43:31.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541047,0,540382,TO_TIMESTAMP('2018-02-25 13:43:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','_Kommissionierung_Profil','Y','N','N','N','N','Kommissionierung Profil',TO_TIMESTAMP('2018-02-25 13:43:31','YYYY-MM-DD HH24:MI:SS'),100,'Kommissionierung Profil')
;

-- 2018-02-25T13:43:31.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541047 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-02-25T13:43:31.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541047, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541047)
;

-- 2018-02-25T13:43:32.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000079, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541047 AND AD_Tree_ID=10
;

-- 2018-02-25T13:43:53.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-25 13:43:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Picking Profil',WEBUI_NameBrowse='Picking Profil' WHERE AD_Menu_ID=541047 AND AD_Language='en_US'
;

