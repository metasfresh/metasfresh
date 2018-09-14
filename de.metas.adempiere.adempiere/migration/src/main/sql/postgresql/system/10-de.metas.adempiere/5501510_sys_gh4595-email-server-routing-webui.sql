-- 2018-09-14T17:09:45.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541139,0,540078,TO_TIMESTAMP('2018-09-14 17:09:44','YYYY-MM-DD HH24:MI:SS'),100,'Define which email server should be used in which context','de.metas.swat','_EMail_Server_Routing','Y','N','N','N','N','EMail Server Routing',TO_TIMESTAMP('2018-09-14 17:09:44','YYYY-MM-DD HH24:MI:SS'),100,'EMail Server Routing')
;

-- 2018-09-14T17:09:45.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541139 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-09-14T17:09:45.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541139, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541139)
;

-- 2018-09-14T17:09:45.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540277, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540209 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:45.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540277, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540203 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:45.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540277, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541139 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:57.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541139 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:57.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541132 AND AD_Tree_ID=10
;

-- 2018-09-14T17:09:57.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541133 AND AD_Tree_ID=10
;

