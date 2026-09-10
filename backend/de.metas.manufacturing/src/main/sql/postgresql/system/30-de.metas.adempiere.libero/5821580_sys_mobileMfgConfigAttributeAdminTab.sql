-- Admin child tab for the editable-attributes list, on window 541788 (MobileUI Manufacturing
-- Configuration), TabLevel=1 under root tab 547483.
-- Modeled EXACTLY on the shipped HU-Manager precedent AD_Tab 547558 ("Merkmale") on window 541806:
--   backend/de.metas.handlingunits.base/src/main/sql/postgresql/system/70-de.metas.handlingunits/5728210_sys_gh18417_MobileUI_HU_Manager.sql
--   (AD_Tab section starts at line 684 of that file).
--
-- Link-column binding: mirrors the precedent by setting AD_Tab.AD_Column_ID to the child's own FK
-- column (MobileUI_MFG_Config_ID, 593455) and leaving Parent_Column_ID unset. Verified this is the
-- actual, correctly-functioning production mechanism (not just an untested precedent) by reading
-- GridTabVOBasedDocumentEntityDescriptorFactory.extractChildParentLinkColumnNames(...)
-- (backend/de.metas.ui.web.base/.../GridTabVOBasedDocumentEntityDescriptorFactory.java:1008): when
-- childTabVO.getParent_Column_ID() doesn't resolve, the framework falls back to
-- parentTabVO.getKeyColumnName() (the parent's PK column name) and pairs it with the child's single
-- link column (childTabVO.getLinkColumnNames()). This works correctly here because our child's FK
-- column is named IDENTICALLY to the parent's PK column (both 'MobileUI_MFG_Config_ID' -- the child
-- column reuses the parent table's own PK element, 583019), exactly as the precedent's
-- MobileUI_HUManager_ID FK column matches its parent's PK column name. Confirmed live on the local
-- DB: AD_Tab 547558 has AD_Column_ID=588709 (child's own FK column), Parent_Column_ID NULL, and the
-- HU-Manager admin tab is a shipped, working feature.
--
-- IDs allocated from idserver.metas.de on 2026-09-01:
--   AD_Tab            549417
--   AD_Field          783052 (AD_Client_ID), 783053 (AD_Org_ID), 783054 (IsActive),
--                     783055 (MobileUI_MFG_Config_Attribute_ID, PK), 783056 (MobileUI_MFG_Config_ID, link),
--                     783057 (M_Attribute_ID), 783058 (SeqNo)
--   AD_UI_Section     547922
--   AD_UI_Column      549678
--   AD_UI_ElementGroup 555642
--   AD_UI_Element     653695 (SeqNo), 653696 (M_Attribute_ID), 653697 (IsActive), 653698 (AD_Org_ID)
-- Reused existing AD_Elements (same as the table-creation migration 5821570):
--   102 (AD_Client_ID), 113 (AD_Org_ID), 348 (IsActive), 566 (SeqNo), 2015 (M_Attribute_ID),
--   583019 (MobileUI_MFG_Config_ID -- parent's own PK element), 585399 (MobileUI_MFG_Config_Attribute_ID
--   -- our own PK element, Name='Merkmale', en_US already overridden to 'Attributes'/IsTranslated='Y'
--   by 5821570 -- so update_tab_translation_from_ad_element(585399) below also carries 'Attributes'
--   into the tab's en_US caption for free).

-- ============================================================================
-- AD_Tab: Merkmale (child, TabLevel=1, parent tab 547483)
-- ============================================================================

-- 2026-09-01 11:00:00
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,593455 /*From ID Server -- reused table column, link*/,585399 /*From ID Server -- reused table element*/,0,549417 /*From ID Server*/,542643,541788,'Y',TO_TIMESTAMP('2026-09-01 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','MobileUI_MFG_Config_Attribute','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Merkmale','N',20,1,TO_TIMESTAMP('2026-09-01 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01 11:00:01
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=549417 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-09-01 11:00:02 (sync tab caption from the reused PK element -- carries en_US 'Attributes'/Y)
/* DDL */  select update_tab_translation_from_ad_element(585399)
;

-- 2026-09-01 11:00:03
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549417)
;

-- ============================================================================
-- AD_Field rows (IsDisplayed='N' at field level -- this tab has a persisted AD_UI_Section,
-- so visibility is driven entirely by AD_UI_Element, not AD_Field.IsDisplayed; mirrors precedent).
-- ============================================================================

-- Field: MobileUI Manufacturing Configuration(541788,D) -> Merkmale(549417,D) -> Mandant
-- Column: MobileUI_MFG_Config_Attribute.AD_Client_ID
-- 2026-09-01 11:01:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593447,783052 /*From ID Server*/,0,549417,TO_TIMESTAMP('2026-09-01 11:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-09-01 11:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=783052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783052
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783052)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> Merkmale(549417,D) -> Sektion
-- Column: MobileUI_MFG_Config_Attribute.AD_Org_ID
-- 2026-09-01 11:01:10
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593448,783053 /*From ID Server*/,0,549417,TO_TIMESTAMP('2026-09-01 11:01:10','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-09-01 11:01:10','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=783053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783053
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783053)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> Merkmale(549417,D) -> Aktiv
-- Column: MobileUI_MFG_Config_Attribute.IsActive
-- 2026-09-01 11:01:20
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593451,783054 /*From ID Server*/,0,549417,TO_TIMESTAMP('2026-09-01 11:01:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-09-01 11:01:20','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=783054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783054
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783054)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> Merkmale(549417,D) -> Merkmale (PK)
-- Column: MobileUI_MFG_Config_Attribute.MobileUI_MFG_Config_Attribute_ID
-- 2026-09-01 11:01:30
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593454,783055 /*From ID Server*/,0,549417,TO_TIMESTAMP('2026-09-01 11:01:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2026-09-01 11:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=783055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585399)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783055
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783055)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> Merkmale(549417,D) -> MobileUI Manufacturing Configuration (link)
-- Column: MobileUI_MFG_Config_Attribute.MobileUI_MFG_Config_ID
-- 2026-09-01 11:01:40
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593455,783056 /*From ID Server*/,0,549417,TO_TIMESTAMP('2026-09-01 11:01:40','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','MobileUI Manufacturing Configuration',TO_TIMESTAMP('2026-09-01 11:01:40','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=783056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583019)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783056
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783056)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> Merkmale(549417,D) -> Merkmal
-- Column: MobileUI_MFG_Config_Attribute.M_Attribute_ID
-- 2026-09-01 11:01:50
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593456,783057 /*From ID Server*/,0,549417,TO_TIMESTAMP('2026-09-01 11:01:50','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Merkmal',22,'D','Product Attribute like Color, Size','Y','N','N','N','N','N','N','N','Merkmal',TO_TIMESTAMP('2026-09-01 11:01:50','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=783057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2015)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783057
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783057)
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> Merkmale(549417,D) -> Reihenfolge
-- Column: MobileUI_MFG_Config_Attribute.SeqNo
-- 2026-09-01 11:02:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593457,783058 /*From ID Server*/,0,549417,TO_TIMESTAMP('2026-09-01 11:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2026-09-01 11:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=783058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783058
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(783058)
;

-- ============================================================================
-- AD_UI_Section -> AD_UI_Column -> AD_UI_ElementGroup ("default") -> 4 AD_UI_Element (F-type, IsDisplayed='Y')
-- ============================================================================

-- 2026-09-01 11:03:00
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549417,547922 /*From ID Server*/,TO_TIMESTAMP('2026-09-01 11:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-09-01 11:03:00','YYYY-MM-DD HH24:MI:SS'),100,'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=547922 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-09-01 11:03:10
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549678 /*From ID Server*/,547922,TO_TIMESTAMP('2026-09-01 11:03:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-09-01 11:03:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01 11:03:20
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549678,555642 /*From ID Server*/,TO_TIMESTAMP('2026-09-01 11:03:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2026-09-01 11:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Reihenfolge (SeqNo)
-- 2026-09-01 11:03:30
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,783058,0,549417,653695 /*From ID Server*/,555642,'F',TO_TIMESTAMP('2026-09-01 11:03:30','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',10,0,0,TO_TIMESTAMP('2026-09-01 11:03:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Merkmal (M_Attribute_ID)
-- 2026-09-01 11:03:40
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,783057,0,549417,653696 /*From ID Server*/,555642,'F',TO_TIMESTAMP('2026-09-01 11:03:40','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Merkmal','Product Attribute like Color, Size','Y','N','N','Y','N','N','N',0,'Merkmal',20,0,0,TO_TIMESTAMP('2026-09-01 11:03:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Aktiv (IsActive)
-- 2026-09-01 11:03:50
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,783054,0,549417,653697 /*From ID Server*/,555642,'F',TO_TIMESTAMP('2026-09-01 11:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',30,0,0,TO_TIMESTAMP('2026-09-01 11:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sektion (AD_Org_ID)
-- 2026-09-01 11:04:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,783053,0,549417,653698 /*From ID Server*/,555642,'F',TO_TIMESTAMP('2026-09-01 11:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',40,0,0,TO_TIMESTAMP('2026-09-01 11:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;
