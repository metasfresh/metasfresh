-- 2017-12-07T14:44:46.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Lager Gruppe',Updated=TO_TIMESTAMP('2017-12-07 14:44:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540380
;

-- 2017-12-07T14:44:53.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-07 14:44:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Window_ID=540380 AND AD_Language='en_US'
;

-- 2017-12-07T14:46:40.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540996,0,540380,TO_TIMESTAMP('2017-12-07 14:46:40','YYYY-MM-DD HH24:MI:SS'),100,'U','_Lager_Gruppe','Y','N','N','N','N','Lager Gruppe',TO_TIMESTAMP('2017-12-07 14:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Lager Gruppe')
;

-- 2017-12-07T14:46:40.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540996 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-12-07T14:46:40.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540996, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540996)
;

-- 2017-12-07T14:46:41.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000070, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540996 AND AD_Tree_ID=10
;

-- 2017-12-07T14:46:44.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000069, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540996 AND AD_Tree_ID=10
;

-- 2017-12-07T14:48:19.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Lager Gruppe Kommissionierung',Updated=TO_TIMESTAMP('2017-12-07 14:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540380
;

-- 2017-12-07T14:48:19.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Lager Gruppe Kommissionierung',Updated=TO_TIMESTAMP('2017-12-07 14:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540996
;

-- 2017-12-07T14:48:54.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='_Lager_Gruppe_Kommissionierung', WEBUI_NameBrowse='Lager Gruppe Kommissionierung',Updated=TO_TIMESTAMP('2017-12-07 14:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540996
;

-- 2017-12-07T14:49:16.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-07 14:49:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Warehouse Picking Group',WEBUI_NameBrowse='Warehouse Picking Group' WHERE AD_Menu_ID=540996 AND AD_Language='en_US'
;

