-- 2021-12-09T15:16:48.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=541367,Updated=TO_TIMESTAMP('2021-12-09 16:16:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541967
;

-- 2021-12-09T15:17:21.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,580342,541873,0,541367,TO_TIMESTAMP('2021-12-09 16:17:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Zusatzstoffe Übersetzung','Y','N','N','N','N','Zusatzstoffe Übersetzung',TO_TIMESTAMP('2021-12-09 16:17:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T15:17:22.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541873 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-12-09T15:17:22.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541873, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541873)
;

-- 2021-12-09T15:17:22.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(580342) 
;

-- 2021-12-09T15:17:36.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541735 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:36.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:36.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:36.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:36.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:36.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:36.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:36.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:36.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541487 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:37.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541488 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:38.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:38.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541867 AND AD_Tree_ID=10
;

-- 2021-12-09T15:17:38.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541873 AND AD_Tree_ID=10
;

-- 2021-12-09T15:24:17.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsTranslationTab='Y',Updated=TO_TIMESTAMP('2021-12-09 16:24:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=545198
;

-- 2021-12-09T15:25:01.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsTranslationTab='N',Updated=TO_TIMESTAMP('2021-12-09 16:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=545198
;

-- 2021-12-09T15:28:00.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_additive_trl','M_Additive_ID','NUMERIC(10)',null,null)
;

-- 2021-12-09T15:28:39.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-12-09 16:28:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578767
;

-- 2021-12-09T15:38:38.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-12-09 16:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578899
;

-- 2021-12-09T15:40:31.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19, FieldLength=22, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2021-12-09 16:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578898
;

-- 2021-12-09T15:41:11.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@AD_Client_ID@', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:41:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578898
;

-- 2021-12-09T15:43:37.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=104, DefaultValue='@AD_Org_ID@', FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-12-09 16:43:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578900
;

-- 2021-12-09T15:45:05.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2021-12-09 16:45:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578901
;

-- 2021-12-09T15:46:09.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='Y',Updated=TO_TIMESTAMP('2021-12-09 16:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578901
;

-- 2021-12-09T15:48:14.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='Y', IsExcludeFromZoomTargets='N', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578902
;

-- 2021-12-09T15:49:08.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578901
;

-- 2021-12-09T15:49:35.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:49:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578900
;

-- 2021-12-09T15:50:05.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578899
;

-- 2021-12-09T15:51:53.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578903
;

-- 2021-12-09T15:52:19.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2021-12-09 16:52:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578903
;

-- 2021-12-09T15:53:17.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:53:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578904
;

-- 2021-12-09T15:54:04.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y', IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2021-12-09 16:54:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578904
;

-- 2021-12-09T15:55:11.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:55:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578905
;

-- 2021-12-09T15:56:00.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:56:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578906
;

-- 2021-12-09T15:57:01.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-12-09 16:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578906
;

-- 2021-12-09T15:57:41.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:57:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578907
;

-- 2021-12-09T15:58:18.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsExcludeFromZoomTargets='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-12-09 16:58:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578907
;

-- 2021-12-09T15:59:26.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 16:59:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578908
;

-- 2021-12-09T16:00:36.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2021-12-09 17:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578909
;

-- 2021-12-09T16:00:55.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_additive_trl','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2021-12-09T16:07:59.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2021-12-09 17:07:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541967
;

-- 2021-12-09T16:08:25.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2021-12-09 17:08:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541967
;

-- 2021-12-09T16:15:06.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545092,547722,TO_TIMESTAMP('2021-12-09 17:15:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','Description',20,TO_TIMESTAMP('2021-12-09 17:15:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:15:41.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,675208,0,545198,547722,598420,'F',TO_TIMESTAMP('2021-12-09 17:15:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2021-12-09 17:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;-- 2021-12-09T17:48:11.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Zusatzstoffe (Additives)',Updated=TO_TIMESTAMP('2021-12-09 18:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=597499
;

