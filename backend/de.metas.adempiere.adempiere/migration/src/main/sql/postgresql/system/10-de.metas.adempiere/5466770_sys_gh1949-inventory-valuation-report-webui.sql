-- 2017-07-04T17:39:16.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,540890,0,540499,TO_TIMESTAMP('2017-07-04 17:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Lagerwertliste','de.metas.fresh','_Lagerwert (Jasper)1','Y','N','N','N','N','Lagerwert',TO_TIMESTAMP('2017-07-04 17:39:16','YYYY-MM-DD HH24:MI:SS'),100,'Lagerwert')
;

-- 2017-07-04T17:39:16.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540890 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-07-04T17:39:16.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540890, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540890)
;

-- 2017-07-04T17:39:16.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540890 AND AD_Tree_ID=10
;

-- 2017-07-04T17:41:42.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000061, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540890 AND AD_Tree_ID=10
;

-- 2017-07-04T17:55:48.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-04 17:55:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Inventory Valuation',Description='Inventory Valuation Report',WEBUI_NameBrowse='Inventory Valuation' WHERE AD_Menu_ID=540890 AND AD_Language='en_US'
;

-- 2017-07-04T17:56:12.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-04 17:56:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Inventory Valuation',Description='Inventory Valuation' WHERE AD_Process_ID=540499 AND AD_Language='en_US'
;

-- 2017-07-04T17:56:48.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-04 17:56:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product',Description='Product, Service, Material',Help='' WHERE AD_Process_Para_ID=540594 AND AD_Language='en_US'
;

-- 2017-07-04T17:57:20.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-04 17:57:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Warehouse',Description='Warehouse or Location of Service',Help='' WHERE AD_Process_Para_ID=540595 AND AD_Language='en_US'
;

-- 2017-07-04T17:58:11.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-04 17:58:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reporting Date' WHERE AD_Process_Para_ID=540611 AND AD_Language='en_US'
;

