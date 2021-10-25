-- Display window in menu tree
-- 2021-08-16T10:10:03.990Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,544186,541735,0,540729,TO_TIMESTAMP('2021-08-16 12:10:03','YYYY-MM-DD HH24:MI:SS'),100,'D','M_Ingredients','Y','N','N','N','N','Zutaten',TO_TIMESTAMP('2021-08-16 12:10:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-16T10:10:03.993Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541735 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-08-16T10:10:04.011Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541735, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541735)
;

-- 2021-08-16T10:10:04.046Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(544186) 
;

-- 2021-08-16T10:10:04.730Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=268 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.746Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=125 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.746Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=422 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.759Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=107 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.760Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=130 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.760Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=188 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.760Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=227 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.760Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=381 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.761Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=126 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.761Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=421 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.761Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=267 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.761Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=534 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.761Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=490 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.762Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=132 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.762Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=310 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.762Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53432 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.762Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=128 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.763Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=585 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.763Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53210 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.763Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=187 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.763Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53211 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.764Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540646 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:04.764Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=167, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541735 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.437Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541735 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.438Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.454Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.455Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.455Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.456Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.456Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.457Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.457Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.458Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.458Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.459Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.460Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.460Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.461Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.461Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.462Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.462Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2021-08-16T10:10:39.463Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- hide Mandat column from Ingredients window the grid view
-- 2021-08-16T10:18:45.575Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2021-08-16 12:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589485
;


-- hide Mandat column from Product window Ingredients tab
-- 2021-08-16T10:20:38.179Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-08-16 12:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563191
;

-- 2021-08-16T10:20:38.221Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-08-16 12:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563192
;

-- Display Qty
-- 2021-08-17T07:43:22.262Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-08-17 09:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563193
;

-- 2021-08-17T07:43:22.327Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-08-17 09:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563192
;

-- Add m_product_id column
-- 2021-08-17T07:55:58.536Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575307,454,0,19,541413,'M_Product_ID',TO_TIMESTAMP('2021-08-17 09:55:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2021-08-17 09:55:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-17T07:55:58.559Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575307 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-17T07:55:58.594Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Create field for the new column
-- 2021-08-17T07:57:44.847Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575307,651236,0,542057,0,TO_TIMESTAMP('2021-08-17 09:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',0,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',50,50,0,1,1,TO_TIMESTAMP('2021-08-17 09:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-17T07:57:44.875Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=651236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-17T07:57:44.889Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2021-08-17T07:57:46.561Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=651236
;

-- 2021-08-17T07:57:46.658Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(651236)
;

-- display product field in ingredients window
-- 2021-08-17T07:59:36.079Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2021-08-17 09:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575307
;

-- 2021-08-17T07:59:38.654Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_ingredients','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2021-08-17T08:01:36.209Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=15, SeqNoGrid=15,Updated=TO_TIMESTAMP('2021-08-17 10:01:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=651236
;


-- Filter for Product field in ingredients window
-- 2021-08-17T08:03:51.766Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-08-17 10:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575307
;


