-- 2018-04-19T10:00:12.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,541074,0,540946,TO_TIMESTAMP('2018-04-19 10:00:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Lieferantenbewertung Excel','Y','N','N','N','N','Lieferantenbewertung Excel',TO_TIMESTAMP('2018-04-19 10:00:12','YYYY-MM-DD HH24:MI:SS'),100,'Lieferantenbewertung Excel')
;

-- 2018-04-19T10:00:12.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541074 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-04-19T10:00:12.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541074, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541074)
;

-- 2018-04-19T10:00:13.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540934 AND AD_Tree_ID=10
;

-- 2018-04-19T10:00:13.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540935 AND AD_Tree_ID=10
;

-- 2018-04-19T10:00:13.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540958 AND AD_Tree_ID=10
;

-- 2018-04-19T10:00:13.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541071 AND AD_Tree_ID=10
;

-- 2018-04-19T10:00:13.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000049, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541074 AND AD_Tree_ID=10
;

-- 2018-04-19T10:00:36.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-19 10:00:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Vendor Evaluation',WEBUI_NameBrowse='Vendor Evaluation' WHERE AD_Menu_ID=541074 AND AD_Language='en_US'
;

-- 2018-04-19T10:03:12.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Lieferantenbewertung Excel', Value='Lieferantenbewertung Excel',Updated=TO_TIMESTAMP('2018-04-19 10:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540946
;

-- 2018-04-19T10:03:12.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Lieferantenbewertung Excel',Updated=TO_TIMESTAMP('2018-04-19 10:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541064
;


