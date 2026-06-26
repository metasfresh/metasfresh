-- Add the Verpackungsmaterial (packaging material) child tab to the Product window
-- for the M_Product_PackagingMaterial table created in migration 5809760.
-- IDs allocated from idserver.metas.de on 2026-06-26:
--   AD_Tab             549326  (Verpackungsmaterial tab on Product window 140)
--   AD_Field           781254  (PackagingMaterialType field)
--   AD_Field           781255  (WeightInGram field)
--   AD_UI_Section      547832  (main section)
--   AD_UI_Column       549571  (single column)
--   AD_UI_ElementGroup 555469  (main group)
--   AD_UI_Element      652374  (PackagingMaterialType UI element)
--   AD_UI_Element      652375  (WeightInGram UI element)
-- Pre-existing (migration 5809760):
--   AD_Table 542621, AD_Element 585052 (table label),
--   M_Product_ID FK column 592892 (IsParent='Y'),
--   PackagingMaterialType column 592893 (element 585053),
--   WeightInGram column 592894 (element 585054)

-- Run mode: SWING_CLIENT

-- Point the child table at the Product window (AD_Window_ID=140)
-- 2026-06-26 16:00:00
UPDATE AD_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2026-06-26 16:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542621
;

-- AD_Tab: Verpackungsmaterial (child of Product window 140, TabLevel 1)
-- Child->parent link via AD_Column_ID = M_Product_PackagingMaterial.M_Product_ID (592892, the FK in the child table).
-- AD_Column_ID is the primary link-column mechanism (GridTabVO.buildLinkColumnNames: if AD_Column_ID is set
-- it is used directly as the child link column). Verified working on 3todev; the Parent_Column_ID-only variant
-- did not bind the tab there.
-- 2026-06-26 16:00:10
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,AD_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,585052,0,549326 /*From ID Server*/,542621,140,'Y',TO_TIMESTAMP('2026-06-26 16:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Verpackungsmaterialien des Produkts.','D','N','Verpackungsmaterialien des Produkts (Materialtyp und Gewicht).','N','A','M_Product_PackagingMaterial','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Verpackungsmaterial',592892,'N',183,1,TO_TIMESTAMP('2026-06-26 16:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 16:00:11
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549326
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-06-26 16:00:12
/* DDL */ select update_tab_translation_from_ad_element(585052)
;

-- 2026-06-26 16:00:13
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549326)
;

-- AD_Field: PackagingMaterialType (label/translation propagated from column element 585053)
-- 2026-06-26 16:01:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592893,781254 /*From ID Server*/,0,549326,TO_TIMESTAMP('2026-06-26 16:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Verpackungsmaterial-Typ',10,'D','Y','N','N','N','N','N','N','N','Verpackungsmaterial-Typ',TO_TIMESTAMP('2026-06-26 16:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 16:01:01
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781254
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-26 16:01:02
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585053)
;

-- 2026-06-26 16:01:03
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781254
;

-- 2026-06-26 16:01:04
/* DDL */ select AD_Element_Link_Create_Missing_Field(781254)
;

-- AD_Field: WeightInGram (label/translation propagated from column element 585054)
-- 2026-06-26 16:01:10
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592894,781255 /*From ID Server*/,0,549326,TO_TIMESTAMP('2026-06-26 16:01:10','YYYY-MM-DD HH24:MI:SS'),100,'Gewicht (g)',10,'D','Y','N','N','N','N','N','N','N','Gewicht (g)',TO_TIMESTAMP('2026-06-26 16:01:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 16:01:11
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781255
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-26 16:01:12
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585054)
;

-- 2026-06-26 16:01:13
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781255
;

-- 2026-06-26 16:01:14
/* DDL */ select AD_Element_Link_Create_Missing_Field(781255)
;

-- AD_UI_Section: main
-- 2026-06-26 16:02:00
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value)
VALUES (0,0,549326,547832 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 16:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2026-06-26 16:02:00','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2026-06-26 16:02:01
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547832
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- AD_UI_Column
-- 2026-06-26 16:02:10
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549571 /*From ID Server*/,547832,TO_TIMESTAMP('2026-06-26 16:02:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-06-26 16:02:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_UI_ElementGroup
-- 2026-06-26 16:02:20
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549571,555469 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 16:02:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2026-06-26 16:02:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_UI_Element: PackagingMaterialType (shown in single-row and grid)
-- 2026-06-26 16:03:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781254,0,549326,555469,652374 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-26 16:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Verpackungsmaterial-Typ','Y','N','N','Y','Y','N','N',0,'Verpackungsmaterial-Typ',10,10,0,TO_TIMESTAMP('2026-06-26 16:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_UI_Element: WeightInGram (shown in single-row and grid)
-- 2026-06-26 16:03:10
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781255,0,549326,555469,652375 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-26 16:03:10','YYYY-MM-DD HH24:MI:SS'),100,'Gewicht (g)','Y','N','N','Y','Y','N','N',0,'Gewicht (g)',20,20,0,TO_TIMESTAMP('2026-06-26 16:03:10','YYYY-MM-DD HH24:MI:SS'),100)
;

