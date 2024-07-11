-- 2021-11-22T11:23:24.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,580267,541863,0,541342,TO_TIMESTAMP('2021-11-22 13:23:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','C_LicenseFeeSettings','Y','N','N','N','N','C_LicenseFeeSettings',TO_TIMESTAMP('2021-11-22 13:23:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-22T11:23:24.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541863 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-11-22T11:23:24.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541863, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541863)
;

-- 2021-11-22T11:23:24.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(580267) 
;

-- 2021-11-22T11:23:25.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000070, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541387 AND AD_Tree_ID=10
;

-- 2021-11-22T11:23:25.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000070, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541754 AND AD_Tree_ID=10
;

-- 2021-11-22T11:23:25.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000070, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541412 AND AD_Tree_ID=10
;

-- 2021-11-22T11:23:25.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000070, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540889 AND AD_Tree_ID=10
;

-- 2021-11-22T11:23:25.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000070, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540955 AND AD_Tree_ID=10
;

-- 2021-11-22T11:23:25.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000070, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541863 AND AD_Tree_ID=10
;

