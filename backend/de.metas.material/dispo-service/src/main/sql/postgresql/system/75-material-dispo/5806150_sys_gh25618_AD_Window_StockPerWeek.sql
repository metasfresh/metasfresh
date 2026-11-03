-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_MigrationScript  5806150  (filename prefix)
--   AD_Window           542159   (Bestand pro Woche)
--   AD_Tab              549289   (Bestand pro Woche tab)
--   AD_Element          584945   (StockPerWeek_ATP — custom ATP label override)
--   AD_Element          584946   (MD_Stock_PerWeek_V — window-level element, required for AD_Window.AD_Element_ID NOT NULL)
--   AD_Menu             542335   (menu entry)
--   AD_UI_Section       547809
--   AD_UI_Column        549539   (left column — single column layout)
--   AD_UI_ElementGroup  555423   (main — holds the 6 data fields)
--   AD_Field            780691 (WeekStartDate), 780692 (QtyExpectedShipments),
--                       780693 (QtyExpectedReceipts), 780694 (QtyATP),
--                       780695 (M_Product_ID), 780696 (M_Warehouse_ID)
--   AD_UI_Element       651991-651996
-- NOTE: AD_UI_Column 549540 and AD_UI_ElementGroup 555424 (empty right/flags column)
--       were allocated but are NOT inserted — single-column layout is correct for this read-only view.

-- AD_Element for the window itself (required: AD_Window.AD_Element_ID is NOT NULL)
-- ColumnName matches the view's conceptual name; used for window/tab translation propagation
-- 2026-06-03T13:00:01Z
INSERT INTO AD_Element
    (AD_Client_ID, AD_Org_ID, AD_Element_ID,
     ColumnName, Created, CreatedBy, Updated, UpdatedBy,
     EntityType, IsActive, Name, PrintName)
VALUES
    (0, 0, 584946 /*From ID Server*/,
     'MD_Stock_PerWeek_V',
     TO_TIMESTAMP('2026-06-03 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.material.dispo', 'Y', 'Bestand pro Woche', 'Bestand pro Woche')
;

-- 2026-06-03T13:00:01Z
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID,
     CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName,
     PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
       t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help,
       t.PO_Name, t.PO_PrintName,
       t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584946
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- en_US translation for window element
-- 2026-06-03T13:00:01Z
UPDATE AD_Element_Trl
SET Name = 'Stock per week', PrintName = 'Stock per week', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584946 AND AD_Language = 'en_US'
;

-- Window: Bestand pro Woche
-- Table:  MD_Stock_PerWeek_V (AD_Table_ID=542612)
-- 2026-06-03T13:00:02Z
INSERT INTO AD_Window
    (AD_Client_ID, AD_Org_ID, AD_Window_ID, AD_Element_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     EntityType, IsActive, IsBetaFunctionality, IsDefault,
     IsEnableRemoteCacheInvalidation, IsExcludeFromZoomTargets,
     IsOneInstanceOnly, IsOverrideInMenu, IsSOTrx,
     Name, Processing, WindowType, WinHeight, WinWidth, ZoomIntoPriority)
VALUES
    (0, 0, 542159 /*From ID Server*/, 584946 /*From ID Server*/,
     TO_TIMESTAMP('2026-06-03 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.material.dispo', 'Y', 'N', 'N',
     'N', 'N',
     'N', 'N', 'Y',
     'Bestand pro Woche', 'N', 'M', 0, 0, 100)
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Window_Trl
    (AD_Language, AD_Window_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Window_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Window t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Window_ID = 542159
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = t.AD_Window_ID)
;

-- en_US translation
-- 2026-06-03T13:00:02Z
UPDATE AD_Window_Trl
SET Name = 'Stock per week', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Window_ID = 542159 AND AD_Language = 'en_US'
;

-- 2026-06-03T13:00:02Z
SELECT update_window_translation_from_ad_element(584946)
;

-- 2026-06-03T13:00:02Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID = 542159
;

-- 2026-06-03T13:00:02Z
SELECT AD_Element_Link_Create_Missing_Window(542159)
;

-- Tab: Bestand pro Woche -> Bestand pro Woche
-- Table: MD_Stock_PerWeek_V (AD_Table_ID=542612)
-- 2026-06-03T13:00:03Z
INSERT INTO AD_Tab
    (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Window_ID, AD_Table_ID, AD_Element_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     EntityType, IsActive, IsAdvancedTab, IsAutodetectDefaultDateFilter,
     IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab,
     IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate,
     IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed,
     IsSingleRow, IsSortTab, IsTranslationTab,
     HasTree, ImportFields, IncludedTabNewRecordInputMode, AllowQuickInput,
     InternalName, MaxQueryRecords, Name, Processing, SeqNo, TabLevel)
VALUES
    (0, 0, 549289 /*From ID Server*/, 542159, 542612, 584946 /*From ID Server*/,
     TO_TIMESTAMP('2026-06-03 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.material.dispo', 'Y', 'N', 'Y',
     'Y', 'N', 'N', 'N',
     'N', 'Y', 'Y', 'N',
     'N', 'Y', 'Y',
     'N', 'N', 'N',
     'N', 'N', 'A', 'Y',
     'MD_Stock_PerWeek_V', 0, 'Bestand pro Woche', 'N', 10, 0)
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name,
     QuickInput_CloseButton_Caption, QuickInput_OpenButton_Caption,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       t.QuickInput_CloseButton_Caption, t.QuickInput_OpenButton_Caption,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Tab_ID = 549289
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;

-- en_US translation for tab
-- 2026-06-03T13:00:00Z
UPDATE AD_Tab_Trl
SET Name = 'Stock per week', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549289 AND AD_Language = 'en_US'
;

-- 2026-06-03T13:00:00Z
SELECT AD_Element_Link_Create_Missing_Tab(549289)
;

-- ============================================================
-- New AD_Element for QtyATP label override (DE: "Verfügbar (ATP)", EN: "Available (ATP)")
-- ColumnName: StockPerWeek_ATP
-- 2026-06-03T13:00:00Z
INSERT INTO AD_Element
    (AD_Client_ID, AD_Org_ID, AD_Element_ID,
     ColumnName, Created, CreatedBy, Updated, UpdatedBy,
     EntityType, IsActive, Name, PrintName)
VALUES
    (0, 0, 584945 /*From ID Server*/,
     'StockPerWeek_ATP',
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.material.dispo', 'Y', 'Verfügbar (ATP)', 'Verfügbar (ATP)')
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID,
     CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName,
     PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
       t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help,
       t.PO_Name, t.PO_PrintName,
       t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584945
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- en_US translation for ATP element: "Available (ATP)"
-- 2026-06-03T13:00:00Z
UPDATE AD_Element_Trl
SET Name = 'Available (ATP)', PrintName = 'Available (ATP)', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 584945 AND AD_Language = 'en_US'
;

-- Cascade translation update to all AD_* tables that reference this element
-- 2026-06-03T13:00:00Z
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584945)
;

-- ============================================================
-- Fields (one per column, all IsReadOnly='Y', IsDisplayed='Y')
-- Grid order: M_Product_ID(10), M_Warehouse_ID(20), WeekStartDate(30),
--             QtyExpectedShipments(40), QtyExpectedReceipts(50), QtyATP(60)
-- IsSelectionColumn='Y' on M_Product_ID, M_Warehouse_ID, WeekStartDate
-- IsRangeFilter='Y' on WeekStartDate; SortNo=1 on WeekStartDate (ascending default sort)

-- Field: Bestand pro Woche -> Bestand pro Woche -> Wochenbeginn (KW)
-- Column: MD_Stock_PerWeek_V.WeekStartDate (AD_Column_ID=592708, AD_Element_ID=584938)
-- SeqNo=30 (grid position); SortNo=1 (default ascending sort); IsSelectionColumn+IsRangeFilter
-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_Column_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     DisplayLength, EntityType, IsActive,
     IsDisplayed, IsDisplayedGrid, IsEncrypted,
     IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
     SeqNo, SeqNoGrid, SortNo)
VALUES
    (0, 0, 780691 /*From ID Server*/, 549289, 592708,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     29, 'de.metas.material.dispo', 'Y',
     'Y', 'Y', 'N',
     'N', 'N', 'Y', 'N', 'Wochenbeginn (KW)',
     30, 30, 1)
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 780691
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-06-03T13:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(584938)
;

-- 2026-06-03T13:00:00Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780691
;

-- 2026-06-03T13:00:00Z
SELECT AD_Element_Link_Create_Missing_Field(780691)
;

-- IsSelectionColumn + IsRangeFilter on WeekStartDate column
-- 2026-06-03T13:00:00Z
UPDATE AD_Column
SET IsSelectionColumn = 'Y', IsRangeFilter = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 592708
;

-- Field: Bestand pro Woche -> Bestand pro Woche -> Erwartete Lieferungen
-- Column: MD_Stock_PerWeek_V.QtyExpectedShipments (AD_Column_ID=592709, AD_Element_ID=584939)
-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_Column_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     DisplayLength, EntityType, IsActive,
     IsDisplayed, IsDisplayedGrid, IsEncrypted,
     IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
     SeqNo, SeqNoGrid)
VALUES
    (0, 0, 780692 /*From ID Server*/, 549289, 592709,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10, 'de.metas.material.dispo', 'Y',
     'Y', 'Y', 'N',
     'N', 'N', 'Y', 'N', 'Erwartete Lieferungen',
     40, 40)
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 780692
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-06-03T13:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(584939)
;

-- 2026-06-03T13:00:00Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780692
;

-- 2026-06-03T13:00:00Z
SELECT AD_Element_Link_Create_Missing_Field(780692)
;

-- Field: Bestand pro Woche -> Bestand pro Woche -> Erwartete Wareneingänge
-- Column: MD_Stock_PerWeek_V.QtyExpectedReceipts (AD_Column_ID=592710, AD_Element_ID=584940)
-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_Column_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     DisplayLength, EntityType, IsActive,
     IsDisplayed, IsDisplayedGrid, IsEncrypted,
     IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
     SeqNo, SeqNoGrid)
VALUES
    (0, 0, 780693 /*From ID Server*/, 549289, 592710,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10, 'de.metas.material.dispo', 'Y',
     'Y', 'Y', 'N',
     'N', 'N', 'Y', 'N', 'Erwartete Wareneingänge',
     50, 50)
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 780693
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-06-03T13:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(584940)
;

-- 2026-06-03T13:00:00Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780693
;

-- 2026-06-03T13:00:00Z
SELECT AD_Element_Link_Create_Missing_Field(780693)
;

-- Field: Bestand pro Woche -> Bestand pro Woche -> Verfügbar (ATP)
-- Column: MD_Stock_PerWeek_V.QtyATP (AD_Column_ID=592711, AD_Element_ID=584821)
-- AD_Name_ID = 584945 (StockPerWeek_ATP element — label override)
-- Timestamp 13:00:01 so field_trl.updated differs from element_trl.updated (13:00:00),
-- ensuring update_FieldTranslation_From_AD_Name_Element guard (f_trl.updated <> e_trl.updated) passes.
-- 2026-06-03T13:00:01Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     DisplayLength, EntityType, IsActive,
     IsDisplayed, IsDisplayedGrid, IsEncrypted,
     IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
     SeqNo, SeqNoGrid)
VALUES
    (0, 0, 780694 /*From ID Server*/, 549289, 592711, 584945 /*From ID Server*/,
     TO_TIMESTAMP('2026-06-03 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10, 'de.metas.material.dispo', 'Y',
     'Y', 'Y', 'N',
     'N', 'N', 'Y', 'N', 'Verfügbar (ATP)',
     60, 60)
;

-- AD_Field_Trl en_US for QtyATP: "Available (ATP)", IsTranslated='Y'
-- 2026-06-03T13:00:01Z
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 780694
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- Override en_US translation to "Available (ATP)"
-- 2026-06-03T13:00:01Z
UPDATE AD_Field_Trl
SET Name = 'Available (ATP)', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 780694 AND AD_Language = 'en_US'
;

-- Use the label override element for field translation
-- 2026-06-03T13:00:01Z
SELECT update_FieldTranslation_From_AD_Name_Element(584945)
;

-- 2026-06-03T13:00:00Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780694
;

-- 2026-06-03T13:00:00Z
SELECT AD_Element_Link_Create_Missing_Field(780694)
;

-- Field: Bestand pro Woche -> Bestand pro Woche -> Produkt
-- Column: MD_Stock_PerWeek_V.M_Product_ID (AD_Column_ID=592706, AD_Element_ID=454)
-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_Column_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     DisplayLength, EntityType, IsActive,
     IsDisplayed, IsDisplayedGrid, IsEncrypted,
     IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
     SeqNo, SeqNoGrid)
VALUES
    (0, 0, 780695 /*From ID Server*/, 549289, 592706,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     22, 'de.metas.material.dispo', 'Y',
     'Y', 'Y', 'N',
     'N', 'N', 'Y', 'N', 'Produkt',
     10, 10)
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 780695
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-06-03T13:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-06-03T13:00:00Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780695
;

-- 2026-06-03T13:00:00Z
SELECT AD_Element_Link_Create_Missing_Field(780695)
;

-- IsSelectionColumn on M_Product_ID column
-- 2026-06-03T13:00:00Z
UPDATE AD_Column
SET IsSelectionColumn = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 592706
;

-- Field: Bestand pro Woche -> Bestand pro Woche -> Lager
-- Column: MD_Stock_PerWeek_V.M_Warehouse_ID (AD_Column_ID=592707, AD_Element_ID=459)
-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_Column_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     DisplayLength, EntityType, IsActive,
     IsDisplayed, IsDisplayedGrid, IsEncrypted,
     IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name,
     SeqNo, SeqNoGrid)
VALUES
    (0, 0, 780696 /*From ID Server*/, 549289, 592707,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     22, 'de.metas.material.dispo', 'Y',
     'Y', 'Y', 'N',
     'N', 'N', 'Y', 'N', 'Lager',
     20, 20)
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 780696
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-06-03T13:00:00Z
SELECT update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2026-06-03T13:00:00Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780696
;

-- 2026-06-03T13:00:00Z
SELECT AD_Element_Link_Create_Missing_Field(780696)
;

-- IsSelectionColumn on M_Warehouse_ID column
-- 2026-06-03T13:00:00Z
UPDATE AD_Column
SET IsSelectionColumn = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 592707
;

-- ============================================================
-- UI Layout — single-column layout (no right/flags column)

-- UI Section: main
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Section
    (AD_Client_ID, AD_Org_ID, AD_UI_Section_ID, AD_Tab_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, Name, SeqNo, Value)
VALUES
    (0, 0, 547809 /*From ID Server*/, 549289,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'main', 10, 'main')
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Section_Trl
    (AD_Language, AD_UI_Section_ID, Description, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_UI_Section_ID = 547809
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID)
;

-- Single UI Column (left only — no right/flags column for this read-only view)
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Column
    (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive, SeqNo)
VALUES
    (0, 0, 549539 /*From ID Server*/, 547809,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 10)
;

-- UI ElementGroup: main (left column, primary style — holds the 6 data fields)
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_ElementGroup
    (AD_Client_ID, AD_Org_ID, AD_UI_ElementGroup_ID, AD_UI_Column_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, Name, SeqNo, UIStyle)
VALUES
    (0, 0, 555423 /*From ID Server*/, 549539,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'main', 10, 'primary')
;

-- ============================================================
-- UI Elements
-- Grid order: M_Product_ID(10), M_Warehouse_ID(20), WeekStartDate(30),
--             QtyExpectedShipments(40), QtyExpectedReceipts(50), QtyATP(60)
-- WidgetSize='S' on WeekStartDate (651991) and 3 qty elements (651992-651994)

-- UI Element: Bestand pro Woche -> Bestand pro Woche.Produkt
-- Column: MD_Stock_PerWeek_V.M_Product_ID (AD_Field_ID=780695)
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementType,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid,
     IsMultiLine, MultiLine_LinesCount,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid)
VALUES
    (0, 0, 651995 /*From ID Server*/, 555423,
     549289, 780695, 'F',
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'N',
     'Y', 'N', 'Y',
     'N', 0,
     'Produkt', 10, 0, 10)
;

-- UI Element: Bestand pro Woche -> Bestand pro Woche.Lager
-- Column: MD_Stock_PerWeek_V.M_Warehouse_ID (AD_Field_ID=780696)
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementType,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid,
     IsMultiLine, MultiLine_LinesCount,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid)
VALUES
    (0, 0, 651996 /*From ID Server*/, 555423,
     549289, 780696, 'F',
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'N',
     'Y', 'N', 'Y',
     'N', 0,
     'Lager', 20, 0, 20)
;

-- UI Element: Bestand pro Woche -> Bestand pro Woche.Wochenbeginn (KW)
-- Column: MD_Stock_PerWeek_V.WeekStartDate (AD_Field_ID=780691)
-- WidgetSize='S' — date column
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementType,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid,
     IsMultiLine, MultiLine_LinesCount,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid, WidgetSize)
VALUES
    (0, 0, 651991 /*From ID Server*/, 555423,
     549289, 780691, 'F',
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'N',
     'Y', 'N', 'Y',
     'N', 0,
     'Wochenbeginn (KW)', 30, 0, 30, 'S')
;

-- UI Element: Bestand pro Woche -> Bestand pro Woche.Erwartete Lieferungen
-- Column: MD_Stock_PerWeek_V.QtyExpectedShipments (AD_Field_ID=780692)
-- WidgetSize='S' — quantity column
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementType,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid,
     IsMultiLine, MultiLine_LinesCount,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid, WidgetSize)
VALUES
    (0, 0, 651992 /*From ID Server*/, 555423,
     549289, 780692, 'F',
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'N',
     'Y', 'N', 'Y',
     'N', 0,
     'Erwartete Lieferungen', 40, 0, 40, 'S')
;

-- UI Element: Bestand pro Woche -> Bestand pro Woche.Erwartete Wareneingänge
-- Column: MD_Stock_PerWeek_V.QtyExpectedReceipts (AD_Field_ID=780693)
-- WidgetSize='S' — quantity column
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementType,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid,
     IsMultiLine, MultiLine_LinesCount,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid, WidgetSize)
VALUES
    (0, 0, 651993 /*From ID Server*/, 555423,
     549289, 780693, 'F',
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'N',
     'Y', 'N', 'Y',
     'N', 0,
     'Erwartete Wareneingänge', 50, 0, 50, 'S')
;

-- UI Element: Bestand pro Woche -> Bestand pro Woche.Verfügbar (ATP)
-- Column: MD_Stock_PerWeek_V.QtyATP (AD_Field_ID=780694, AD_Name_ID=584945)
-- AD_Name_ID wired to override element 584945; WidgetSize='S' — quantity column
-- 2026-06-03T13:00:00Z
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementType, AD_Name_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid,
     IsMultiLine, MultiLine_LinesCount,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid, WidgetSize)
VALUES
    (0, 0, 651994 /*From ID Server*/, 555423,
     549289, 780694, 'F', 584945 /*From ID Server*/,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Y', 'N', 'N',
     'Y', 'N', 'Y',
     'N', 0,
     'Verfügbar (ATP)', 60, 0, 60, 'S')
;

-- ============================================================
-- Menu entry under Lagerverwaltung (1000012)

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Menu
    (AD_Client_ID, AD_Org_ID, AD_Menu_ID, AD_Element_ID, AD_Window_ID,
     Created, CreatedBy, Updated, UpdatedBy,
     Action, EntityType, InternalName,
     IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary,
     Name)
VALUES
    (0, 0, 542335 /*From ID Server*/, 584946 /*From ID Server*/, 542159,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'W', 'de.metas.material.dispo', 'Bestand pro Woche',
     'Y', 'N', 'Y', 'N', 'N',
     'Bestand pro Woche')
;

-- 2026-06-03T13:00:00Z
INSERT INTO AD_Menu_Trl
    (AD_Language, AD_Menu_ID, Description, Name,
     WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Menu_ID = 542335
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID)
;

-- en_US translation for menu
-- 2026-06-03T13:00:00Z
UPDATE AD_Menu_Trl
SET Name = 'Stock per week', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Menu_ID = 542335 AND AD_Language = 'en_US'
;

-- Tree node: place under Lagerverwaltung (Parent_ID=1000012), SeqNo=19
-- 2026-06-03T13:00:00Z
INSERT INTO AD_TreeNodeMM
    (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y',
       TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       t.AD_Tree_ID, 542335, 1000012, 19
FROM AD_Tree t
WHERE t.AD_Client_ID = 0 AND t.IsActive = 'Y' AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM e
                  WHERE e.AD_Tree_ID = t.AD_Tree_ID AND e.Node_ID = 542335)
;
