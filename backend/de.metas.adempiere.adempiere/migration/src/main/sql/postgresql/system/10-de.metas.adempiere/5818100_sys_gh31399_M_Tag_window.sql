-- Product Tag registry window (M_Tag)
-- Creates the "Produkt-Label" / "Product Tag" AD_Window over M_Tag (AD_Table_ID=542636).
-- Single tab, grid-first CRUD, 3 user-facing fields: Name, Value, Description.
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript prefix : 5818100
--   AD_Window_ID              : 542180
--   AD_Tab_ID                 : 549361
--   AD_UI_Section_ID          : 547866
--   AD_UI_Column_ID           : 549615
--   AD_UI_ElementGroup_ID     : 555536
--   AD_UI_Element_ID (Name)   : 652810
--   AD_UI_Element_ID (Value)  : 652811
--   AD_UI_Element_ID (Desc)   : 652812
--   AD_Field_ID (Name)        : 781897
--   AD_Field_ID (Value)       : 781898
--   AD_Field_ID (Desc)        : 781899
--   AD_Menu_ID                : 542354
--
-- Reused AD_Column_IDs from T2 migration (5818080_sys_gh31399_M_Tag_table.sql):
--   593118 = Name, 593119 = Value, 593120 = Description
-- Reused AD_Element_IDs:
--   585159 = M_Tag_ID / Produkt-Label (window element)
--   469    = Name, 620 = Value (Suchschlüssel), 275 = Description

-- ===========================================================================
-- AD_Window
-- ===========================================================================

-- Window: Produkt-Label
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth)
VALUES (0,585159 /*M_Tag_ID element*/,0,542180 /*From ID Server*/,TO_TIMESTAMP('2026-08-10 16:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','Produkt-Label','N',TO_TIMESTAMP('2026-08-10 16:00:00','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Window t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=542180
AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

/* DDL */ select update_window_translation_from_ad_element(585159)
;

DELETE FROM AD_Element_Link WHERE AD_Window_ID=542180
;

/* DDL */ select AD_Element_Link_Create_Missing_Window(542180)
;

-- ===========================================================================
-- AD_Tab
-- ===========================================================================

-- Tab: Product Tag (grid-first, TabLevel=0)
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,585159 /*M_Tag_ID element*/,0,549361 /*From ID Server*/,542636 /*M_Tag*/,542180,'Y',TO_TIMESTAMP('2026-08-10 16:00:01','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','M_Tag','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Produkt-Label','N',10,0,TO_TIMESTAMP('2026-08-10 16:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=549361
AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

/* DDL */ select update_tab_translation_from_ad_element(585159)
;

/* DDL */ select AD_Element_Link_Create_Missing_Tab(549361)
;

-- ===========================================================================
-- AD_Field rows (required by convention — one per user-facing column)
-- ===========================================================================

-- Field: Name
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593118 /*Name col*/,781897 /*From ID Server*/,0,549361,TO_TIMESTAMP('2026-08-10 16:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumerischer Identifikator dieses Eintrags',60,'D','Y','Y','Y','N','N','N','N','N','Name',TO_TIMESTAMP('2026-08-10 16:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781897
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(469 /*Name element*/)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781897
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781897)
;

-- Field: Value (Suchschlüssel)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593119 /*Value col*/,781898 /*From ID Server*/,0,549361,TO_TIMESTAMP('2026-08-10 16:00:11','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','Y','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2026-08-10 16:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781898
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(620 /*Value/Suchschlüssel element*/)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781898
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781898)
;

-- Field: Description
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593120 /*Description col*/,781899 /*From ID Server*/,0,549361,TO_TIMESTAMP('2026-08-10 16:00:12','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche kurze Beschreibung',255,'D','Y','Y','Y','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2026-08-10 16:00:12','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781899
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(275 /*Description element*/)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781899
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781899)
;

-- ===========================================================================
-- UI Layout: AD_UI_Section → AD_UI_Column → AD_UI_ElementGroup → AD_UI_Element
-- Single main section, single column, single "default" group.
-- Grid ordering: Name(10), Value(20), Description(30).
-- ===========================================================================

-- UI Section: main
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value)
VALUES (0,0,549361,547866 /*From ID Server*/,TO_TIMESTAMP('2026-08-10 16:00:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-08-10 16:00:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=547866
AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Column: 10 (single column)
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549615 /*From ID Server*/,547866,TO_TIMESTAMP('2026-08-10 16:00:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-08-10 16:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI ElementGroup: default (primary style)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549615,555536 /*From ID Server*/,TO_TIMESTAMP('2026-08-10 16:00:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2026-08-10 16:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Name (SeqNo=10, IsDisplayedGrid=Y, SeqNoGrid=10)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781897,0,549361,555536,652810 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-10 16:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumerischer Identifikator dieses Eintrags','Y','N','Y','Y','N','Name',10,10,0,TO_TIMESTAMP('2026-08-10 16:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Value / Suchschlüssel (SeqNo=20, IsDisplayedGrid=Y, SeqNoGrid=20)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781898,0,549361,555536,652811 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-10 16:00:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Suchschlüssel',20,20,0,TO_TIMESTAMP('2026-08-10 16:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Description (SeqNo=30, IsDisplayedGrid=Y, SeqNoGrid=30)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781899,0,549361,555536,652812 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-10 16:00:32','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche kurze Beschreibung','Y','N','Y','Y','N','Beschreibung',30,30,0,TO_TIMESTAMP('2026-08-10 16:00:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ===========================================================================
-- AD_Menu + tree placement
-- Parent: 167 (Einstellungen Materialwirtschaft)
-- ===========================================================================

INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy)
VALUES ('W',0,585159,542354 /*From ID Server*/,0,542180,TO_TIMESTAMP('2026-08-10 16:00:40','YYYY-MM-DD HH24:MI:SS'),100,'D','M_Tag','Y','N','N','N','N','Produkt-Label',TO_TIMESTAMP('2026-08-10 16:00:40','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=542354
AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Place in menu tree under parent 167 (Einstellungen Materialwirtschaft), last position
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542354, 167, 999
FROM AD_Tree t
WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116
AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542354)
;

/* DDL */ select update_menu_translation_from_ad_element(585159)
;

