-- 2019-11-13T12:34:00.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569558,591366,0,53053,TO_TIMESTAMP('2019-11-13 14:34:00','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Picking candidate',TO_TIMESTAMP('2019-11-13 14:34:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-13T12:34:00.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591366 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-13T12:34:00.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543375) 
;

-- 2019-11-13T12:34:00.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591366
;

-- 2019-11-13T12:34:00.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591366)
;

-- 2019-11-13T12:34:21.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2019-11-13 14:34:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591366
;

-- 2019-11-13T12:35:00.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-11-13 14:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591366
;

-- 2019-11-13T12:37:13.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,573917,541397,0,53014,TO_TIMESTAMP('2019-11-13 14:37:13','YYYY-MM-DD HH24:MI:SS'),100,'The cost collector is a repository of all the MO transactions.','EE01','ppCostCollector','Y','N','Y','N','N','Cost Collector',TO_TIMESTAMP('2019-11-13 14:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-13T12:37:13.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541397 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-11-13T12:37:13.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541397, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541397)
;

-- 2019-11-13T12:37:13.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(573917) 
;

-- 2019-11-13T12:37:14.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53054 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:14.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53055 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:14.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53057 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:14.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53058 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:14.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53060 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:14.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=194 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:14.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:28.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000081 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:28.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:28.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000055 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:28.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000063 AND AD_Tree_ID=10
;

-- 2019-11-13T12:37:28.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000071 AND AD_Tree_ID=10
;

