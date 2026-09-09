-- Product window (AD_Window_ID=140) — add "Labels" child tab over M_Product_Tag
-- Satisfies AC4: users can manage product–tag assignments from the core Product window.
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript prefix : 5818120
--   AD_Tab_ID                 : 549362
--   AD_UI_Section_ID          : 547867
--   AD_UI_Column_ID           : 549616
--   AD_UI_ElementGroup_ID     : 555537
--   AD_UI_Element_ID (M_Tag_ID)  : 652813
--   AD_UI_Element_ID (IsActive)  : 652814
--   AD_Field_ID (M_Tag_ID)    : 781900
--   AD_Field_ID (IsActive)    : 781901
--
-- Reused IDs (from T2/T3 migrations — do NOT recreate):
--   AD_Table_ID  M_Product_Tag          : 542637
--   AD_Column_ID M_Product_Tag_ID       : 593129
--   AD_Column_ID M_Product_ID (FK/parent): 593130   ← Parent_Column_ID
--   AD_Column_ID M_Tag_ID               : 593121
--   AD_Column_ID IsActive               : 593126
--   AD_Element_ID M_Tag_ID (Produkt-Label): 585159
--   AD_Element_ID IsActive              : 348
--   AD_Element_ID Labels (tab name)     : 577701   (existing, columnname='')
--
-- Parent window context:
--   AD_Window_ID=140 (Produkt), EntityType='D', max existing tab SeqNo=360 → this tab SeqNo=370

-- ===========================================================================
-- AD_Tab
-- ===========================================================================

-- Tab: Labels (child of Product, TabLevel=1, grid-only, insert allowed)
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AD_Column_ID,Parent_Column_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,577701 /*Labels element*/,0,549362 /*From ID Server*/,542637 /*M_Product_Tag*/,140 /*Produkt window*/,NULL /*AD_Column_ID=NULL for child tab*/,593130 /*Parent_Column_ID=M_Product_Tag.M_Product_ID*/,'Y',TO_TIMESTAMP('2026-08-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','M_Product_Tag','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Labels','N',370,1,TO_TIMESTAMP('2026-08-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=549362
AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

/* DDL */ select update_tab_translation_from_ad_element(577701)
;

/* DDL */ select AD_Element_Link_Create_Missing_Tab(549362)
;

-- ===========================================================================
-- AD_Field rows (one per user-facing column shown in this tab)
-- ===========================================================================

-- Field: M_Tag_ID (Produkt-Label)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593121 /*M_Tag_ID col*/,781900 /*From ID Server*/,0,549362,TO_TIMESTAMP('2026-08-11 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','Y','N','N','N','N','N','Produkt-Label',TO_TIMESTAMP('2026-08-11 10:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781900
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585159 /*M_Tag_ID element*/)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781900
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781900)
;

-- Field: IsActive (Aktiv)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593126 /*IsActive col*/,781901 /*From ID Server*/,0,549362,TO_TIMESTAMP('2026-08-11 10:00:11','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-08-11 10:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781901
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781901
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781901)
;

-- ===========================================================================
-- UI Layout: AD_UI_Section → AD_UI_Column → AD_UI_ElementGroup → AD_UI_Element
-- Single main section, single column, single "default" group.
-- Grid ordering: M_Tag_ID(10), IsActive(20).
-- ===========================================================================

-- UI Section: main
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value)
VALUES (0,0,549362,547867 /*From ID Server*/,TO_TIMESTAMP('2026-08-11 10:00:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-08-11 10:00:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=547867
AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Column: 10 (single column)
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549616 /*From ID Server*/,547867,TO_TIMESTAMP('2026-08-11 10:00:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-08-11 10:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI ElementGroup: default (primary style)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549616,555537 /*From ID Server*/,TO_TIMESTAMP('2026-08-11 10:00:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2026-08-11 10:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: M_Tag_ID / Produkt-Label (SeqNo=10, IsDisplayedGrid=Y, SeqNoGrid=10)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781900,0,549362,555537,652813 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-11 10:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Produkt-Label',10,10,0,TO_TIMESTAMP('2026-08-11 10:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: IsActive / Aktiv (SeqNo=20, IsDisplayedGrid=Y, SeqNoGrid=20)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781901,0,549362,555537,652814 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-11 10:00:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Aktiv',20,20,0,TO_TIMESTAMP('2026-08-11 10:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

