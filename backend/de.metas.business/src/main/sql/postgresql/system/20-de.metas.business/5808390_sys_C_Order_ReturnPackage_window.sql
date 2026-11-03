-- Run mode: SWING_CLIENT
--
-- Ruecknahme Gebinde (return-package) feature, core (EntityType 'D').
--
-- Creates the standalone general window "Rücknahme Gebinde" over the existing table
-- C_Order_ReturnPackage (AD_Table 542618), created in migration 5808300. Single header tab
-- (TabLevel 0) — this is a self-contained general window, NOT the C_Order sub-tab (that
-- sub-tab is separate customer-specific work, intentionally NOT in this PR, nor is the vanilla
-- sales-order window).
--
-- Fields shown (sensible order): Auftrag, Palette, geliefert, zurück,
-- plus the standard Aktiv (flags group) and Sektion / Mandant (org group).
-- Grid (list) view + single-record (form) view both provided.
-- Auftrag / Palette are made grid filters via AD_Column.IsSelectionColumn='Y'
-- (these are the table-local AD_Column rows of C_Order_ReturnPackage — not shared global columns).
-- The business partner is NOT a field here — it is derivable from the order (C_Order_ID).
--
-- Menu: placed under the existing "Vertrieb" (Sales) folder, AD_TreeNodeMM Parent_ID 1000010 —
-- the same top-level menu node that the sales-order window lives under, so the entry is visible
-- in the WebUI menu (C_Order_ReturnPackage is a child of the sales order, C_Order).
--
-- Reused AD_Elements (NO new AD_Element rows inserted — their ColumnNames already exist):
--   585006 'Rücknahme Gebinde' (window/tab/menu caption + key column)
--   558 C_Order_ID 'Auftrag',
--   585007 PalletType 'Palette', 585008 QtyDeliveredLU 'Geliefert', 585009 QtyReturnedLU 'Zurück',
--   348 IsActive 'Aktiv', 113 AD_Org_ID 'Sektion', 102 AD_Client_ID 'Mandant'
--
-- Existing AD_Column_IDs on C_Order_ReturnPackage (from migration 5808300):
--   592815 C_Order_ReturnPackage_ID (key), 592816 AD_Client_ID, 592817 AD_Org_ID,
--   592818 IsActive, 592824 C_Order_ID, 592825 PalletType,
--   592826 QtyDeliveredLU, 592827 QtyReturnedLU
--   (592823 / C_BPartner_ID intentionally not created — derived via C_Order_ID)
--
-- IDs from central ID server:
--   AD_Window 542164
--   AD_Tab    549320
--   AD_Field  781160..781166 (7: C_Order_ID, PalletType, QtyDeliveredLU,
--             QtyReturnedLU, IsActive, AD_Org_ID, AD_Client_ID; 781159 / C_BPartner_ID dropped)
--   AD_UI_Section        547829
--   AD_UI_Column         549566 (left), 549567 (right)
--   AD_UI_ElementGroup   555463 (left primary), 555464 (right flags), 555465 (right org)
--   AD_UI_Element        652306..652312 (one per field; 652305 / C_BPartner_ID dropped)
--   AD_Menu   542340
--
-- NOTE: the window-designer render self-check (render-window-layout.sh) was NOT run — no local
-- DB is available in this environment. The human must apply this migration and verify the
-- rendered layout on the dev DB.

-- Window: Rücknahme Gebinde (reuse element 585006 for the caption)
-- 2026-06-17 10:00:00
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,585006,0,542164 /*From ID Server*/,TO_TIMESTAMP('2026-06-17 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Rücknahme-Gebinde-Zeilen je Kundenauftrag (Palettentyp EUR / H1).','D','Y','N','N','N','N','Y','Rücknahme Gebinde','N',TO_TIMESTAMP('2026-06-17 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2026-06-17 10:00:01
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542164 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;
-- 2026-06-17 10:00:02
/* DDL */  select update_window_translation_from_ad_element(585006)
;

-- 2026-06-17 10:00:03
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542164
;
-- 2026-06-17 10:00:04
/* DDL */ select AD_Element_Link_Create_Missing_Window(542164)
;

-- Tab: Rücknahme Gebinde (over C_Order_ReturnPackage, AD_Table 542618)
-- 2026-06-17 10:00:05
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,585006,0,549320 /*From ID Server*/,542618,542164,'N',TO_TIMESTAMP('2026-06-17 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'Rücknahme-Gebinde-Zeilen je Kundenauftrag (Palettentyp EUR / H1).','D','N','N','C_Order_ReturnPackage','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Rücknahme Gebinde','N',10,0,TO_TIMESTAMP('2026-06-17 10:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-17 10:00:06
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549320 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;
-- 2026-06-17 10:00:07
/* DDL */  select update_tab_translation_from_ad_element(585006)
;
-- 2026-06-17 10:00:08
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549320)
;

-- ============================================================================
-- Fields (AD_Field) — one per shown column. Field translations propagate from the
-- column's AD_Element via update_FieldTranslation_From_AD_Name_Element below.
-- ============================================================================

-- Field: C_Order_ID (Auftrag) — column 592824, element 558
-- 2026-06-17 10:01:02
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,592824,781160 /*From ID Server*/,0,549320,TO_TIMESTAMP('2026-06-17 10:01:02','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'D','Eindeutige Kennung eines Auftrags.','Y','Y','Y','N','N','N','N','N','Auftrag',20,20,0,TO_TIMESTAMP('2026-06-17 10:01:02','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:01:03
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: PalletType (Palette) — column 592825, element 585007
-- 2026-06-17 10:01:04
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,592825,781161 /*From ID Server*/,0,549320,TO_TIMESTAMP('2026-06-17 10:01:04','YYYY-MM-DD HH24:MI:SS'),100,'Palettentyp des Rücknahme-Gebindes (EUR oder H1).',10,'D',NULL,'Y','Y','Y','N','N','N','N','N','Palette',30,30,0,TO_TIMESTAMP('2026-06-17 10:01:04','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:01:05
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: QtyDeliveredLU (geliefert) — column 592826, element 585008
-- 2026-06-17 10:01:06
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,592826,781162 /*From ID Server*/,0,549320,TO_TIMESTAMP('2026-06-17 10:01:06','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge des Rücknahme-Gebindes.',0,'D',NULL,'Y','Y','Y','N','N','N','N','N','Geliefert',40,40,0,TO_TIMESTAMP('2026-06-17 10:01:06','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:01:07
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: QtyReturnedLU (zurück) — column 592827, element 585009
-- 2026-06-17 10:01:08
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,592827,781163 /*From ID Server*/,0,549320,TO_TIMESTAMP('2026-06-17 10:01:08','YYYY-MM-DD HH24:MI:SS'),100,'Zurückgegebene Menge des Rücknahme-Gebindes.',0,'D',NULL,'Y','Y','Y','N','N','N','N','N','Zurück',50,50,0,TO_TIMESTAMP('2026-06-17 10:01:08','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:01:09
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: IsActive (Aktiv) — column 592818, element 348 (flags group, not in grid)
-- 2026-06-17 10:01:10
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,592818,781164 /*From ID Server*/,0,549320,TO_TIMESTAMP('2026-06-17 10:01:10','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.','Y','Y','N','N','N','N','N','N','Aktiv',60,0,0,TO_TIMESTAMP('2026-06-17 10:01:10','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:01:11
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: AD_Org_ID (Sektion) — column 592817, element 113 (org group; last in grid)
-- 2026-06-17 10:01:12
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,592817,781165 /*From ID Server*/,0,549320,TO_TIMESTAMP('2026-06-17 10:01:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung.','Y','Y','Y','N','N','N','N','N','Sektion',70,90,0,TO_TIMESTAMP('2026-06-17 10:01:12','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:01:13
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Field: AD_Client_ID (Mandant) — column 592816, element 102 (advanced; NOT in grid)
-- 2026-06-17 10:01:14
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,592816,781166 /*From ID Server*/,0,549320,TO_TIMESTAMP('2026-06-17 10:01:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden.','Y','Y','N','N','N','N','N','N','Mandant',80,0,0,TO_TIMESTAMP('2026-06-17 10:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:01:15
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Propagate field translations (DE base + en_US) from each column's AD_Element.
-- Pass the COLUMN's AD_Element_ID (no AD_Name_ID override is set on any field).
-- 2026-06-17 10:01:31
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(558)
;
-- 2026-06-17 10:01:32
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585007)
;
-- 2026-06-17 10:01:33
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585008)
;
-- 2026-06-17 10:01:34
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585009)
;
-- 2026-06-17 10:01:35
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(348)
;
-- 2026-06-17 10:01:36
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(113)
;
-- 2026-06-17 10:01:37
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(102)
;

-- Rebuild element links for the new fields
-- 2026-06-17 10:01:40
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (781160,781161,781162,781163,781164,781165,781166)
;
-- 2026-06-17 10:01:42
/* DDL */ select AD_Element_Link_Create_Missing_Field(781160)
;
-- 2026-06-17 10:01:43
/* DDL */ select AD_Element_Link_Create_Missing_Field(781161)
;
-- 2026-06-17 10:01:44
/* DDL */ select AD_Element_Link_Create_Missing_Field(781162)
;
-- 2026-06-17 10:01:45
/* DDL */ select AD_Element_Link_Create_Missing_Field(781163)
;
-- 2026-06-17 10:01:46
/* DDL */ select AD_Element_Link_Create_Missing_Field(781164)
;
-- 2026-06-17 10:01:47
/* DDL */ select AD_Element_Link_Create_Missing_Field(781165)
;
-- 2026-06-17 10:01:48
/* DDL */ select AD_Element_Link_Create_Missing_Field(781166)
;

-- ============================================================================
-- WebUI layout: 1 section, 2 columns (left primary + right flags/org).
-- ============================================================================

-- Section
-- 2026-06-17 10:02:00
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Name,Value) VALUES (0,0,549320,547829 /*From ID Server*/,TO_TIMESTAMP('2026-06-17 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-06-17 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'main','main')
;

-- Left column (SeqNo 10)
-- 2026-06-17 10:02:01
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549566 /*From ID Server*/,547829,TO_TIMESTAMP('2026-06-17 10:02:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-06-17 10:02:01','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Right column (SeqNo 20)
-- 2026-06-17 10:02:02
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549567 /*From ID Server*/,547829,TO_TIMESTAMP('2026-06-17 10:02:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2026-06-17 10:02:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Left column: primary element group (main business fields)
-- 2026-06-17 10:02:03
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549566,555463 /*From ID Server*/,TO_TIMESTAMP('2026-06-17 10:02:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2026-06-17 10:02:03','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Right column: flags group (IsActive first)
-- 2026-06-17 10:02:04
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549567,555464 /*From ID Server*/,TO_TIMESTAMP('2026-06-17 10:02:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,NULL,TO_TIMESTAMP('2026-06-17 10:02:04','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Right column: org/client group (Sektion then Mandant)
-- 2026-06-17 10:02:05
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549567,555465 /*From ID Server*/,TO_TIMESTAMP('2026-06-17 10:02:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',20,NULL,TO_TIMESTAMP('2026-06-17 10:02:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI elements (one per field). Left primary group: business fields. Right flags: IsActive.
-- Right org group: Sektion, then Mandant (advanced).
-- 2026-06-17 10:02:11
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,781160,0,549320,652306 /*From ID Server*/,555463,'F',TO_TIMESTAMP('2026-06-17 10:02:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Y','N',0,'Auftrag',20,0,20,TO_TIMESTAMP('2026-06-17 10:02:11','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:02:12
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,781161,0,549320,652307 /*From ID Server*/,555463,'F',TO_TIMESTAMP('2026-06-17 10:02:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Y','N',0,'Palette',30,0,30,TO_TIMESTAMP('2026-06-17 10:02:12','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:02:13
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,781162,0,549320,652308 /*From ID Server*/,555463,'F',TO_TIMESTAMP('2026-06-17 10:02:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N',0,'Geliefert',40,0,40,TO_TIMESTAMP('2026-06-17 10:02:13','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2026-06-17 10:02:14
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,781163,0,549320,652309 /*From ID Server*/,555463,'F',TO_TIMESTAMP('2026-06-17 10:02:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N',0,'Zurück',50,0,50,TO_TIMESTAMP('2026-06-17 10:02:14','YYYY-MM-DD HH24:MI:SS'),100)
;
-- IsActive in the flags group (first element), not in grid
-- 2026-06-17 10:02:15
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,781164,0,549320,652310 /*From ID Server*/,555464,'F',TO_TIMESTAMP('2026-06-17 10:02:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2026-06-17 10:02:15','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Sektion in the org group; last column in grid (SeqNoGrid 90)
-- 2026-06-17 10:02:16
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,781165,0,549320,652311 /*From ID Server*/,555465,'F',TO_TIMESTAMP('2026-06-17 10:02:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N',0,'Sektion',10,0,90,TO_TIMESTAMP('2026-06-17 10:02:16','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Mandant in the org group; advanced, not in grid
-- 2026-06-17 10:02:17
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,781166,0,549320,652312 /*From ID Server*/,555465,'F',TO_TIMESTAMP('2026-06-17 10:02:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2026-06-17 10:02:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================================
-- Grid filters: make Auftrag / Palette selection columns.
-- These are the table-local AD_Column rows of C_Order_ReturnPackage (not shared globals),
-- so flipping IsSelectionColumn affects only this table's windows.
-- ============================================================================
-- 2026-06-17 10:03:01
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20, Updated=TO_TIMESTAMP('2026-06-17 10:03:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=592824
;
-- 2026-06-17 10:03:02
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30, Updated=TO_TIMESTAMP('2026-06-17 10:03:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=592825
;

-- ============================================================================
-- Menu entry under "Vertrieb" (Sales), AD_TreeNodeMM Parent_ID 1000010.
-- ============================================================================
-- 2026-06-17 10:04:00
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,585006,542340 /*From ID Server*/,0,542164,TO_TIMESTAMP('2026-06-17 10:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Rücknahme-Gebinde-Zeilen je Kundenauftrag (Palettentyp EUR / H1).','D','C_Order_ReturnPackage','Y','N','N','N','N','Rücknahme Gebinde',TO_TIMESTAMP('2026-06-17 10:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-17 10:04:01
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542340 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;
-- 2026-06-17 10:04:02
/* DDL */  select update_menu_translation_from_ad_element(585006)
;

-- Tree node: place the menu under "Vertrieb" (Parent_ID 1000010) in the main menu tree (AD_Table_ID 116).
-- 2026-06-17 10:04:03
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', TO_TIMESTAMP('2026-06-17 10:04:03','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-17 10:04:03','YYYY-MM-DD HH24:MI:SS'), 100, t.AD_Tree_ID, 542340, 1000010, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542340)
;
