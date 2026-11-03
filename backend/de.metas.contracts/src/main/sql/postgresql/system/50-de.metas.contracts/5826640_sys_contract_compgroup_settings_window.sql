-- Contract compensation groups: settings window.
--
-- Creates the "Kompensationsgruppen-Vertragseinstellungen" / "Compensation group contract
-- settings" window over C_CompensationGroup_ContractSettings (AD_Table_ID=542650, from
-- migration 5826630) with a header tab (Name, Schema, IsActive) and a child tab "Belegarten" /
-- "Document types" over C_CompensationGroup_ContractSettings_DocType (AD_Table_ID=542651)
-- restricting C_DocType_ID to DocBaseType IN ('SOO','POO'). Adds a menu entry under
-- Vertragsverwaltung > Typspezifische Einstellungen, and adds the
-- C_CompensationGroup_ContractSettings_ID field to the Contract Conditions window
-- (C_Flatrate_Conditions, AD_Window_ID=540113, tab 540331), displayed only for
-- Type_Conditions=CompensationGroup, with FK zoom to the new window.
--
-- IDs allocated from idserver.metas.de on 2026-09-28:
--   AD_MigrationScript  5826640 (this script)
--   AD_Element   585494 (window/header-tab/menu title "Kompensationsgruppen-Vertragseinstellungen")
--   AD_Element   585495 (doctypes-tab title "Belegarten")
--   AD_Window    542194
--   AD_Tab       549507 (header, TabLevel=0), 549508 (Belegarten, TabLevel=1)
--   AD_UI_Section 548000 (header tab), 548001 (doctypes tab)
--   AD_UI_Column 549768 (header left), 549769 (header right), 549770 (doctypes)
--   AD_UI_ElementGroup 555794 (header left "default"), 555795 (header right "flags"),
--                       555796 (doctypes "default"), 555797 (header right "org" --
--                       master-data cornerstone: bottom-right group
--                       must carry Org then Client)
--   AD_Field     785583 (Name), 785584 (Schema), 785585 (IsActive) -- header tab
--                785586 (C_DocType_ID), 785587 (IsActive) -- doctypes tab
--                785588 (C_CompensationGroup_ContractSettings_ID on C_Flatrate_Conditions tab 540331)
--                785589 (AD_Org_ID), 785590 (AD_Client_ID) -- header tab "org" group
--   AD_UI_Element 654911 (Name), 654912 (Schema), 654913 (IsActive) -- header tab
--                 654914 (C_DocType_ID), 654915 (IsActive) -- doctypes tab
--                 654916 (C_CompensationGroup_ContractSettings_ID on Conditions window)
--                 654917 (AD_Org_ID), 654918 (AD_Client_ID) -- header tab "org" group
--   AD_Menu      542364
--   AD_Val_Rule  540803 ("C_DocType SOO/POO")
-- ============================================================================
-- 0) The settings table's Name column needs IsIdentifier='Y' (lookup display
--    string) to be usable as an FK/search target -- without it the Contract Conditions field
--    added below fails with "no lookup display columns defined".
-- ============================================================================
UPDATE AD_Column
SET IsIdentifier = 'Y', SeqNo = 10, Updated = TO_TIMESTAMP('2026-09-28 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593658 /* C_CompensationGroup_ContractSettings.Name */
;
-- ============================================================================
-- 1) AD_Element: window / header-tab / menu title
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585494 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Kompensationsgruppen-Vertragseinstellungen', 'Kompensationsgruppen-Vertragseinstellungen',
        'Einstellungen für Verträge vom Typ Kompensationsgruppe (Schema und Belegarten).')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585494, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585494
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Compensation group contract settings', PrintName = 'Compensation group contract settings',
    Description = 'Settings for contracts of type compensation group (schema and document types).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585494 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (already copied by the skeleton insert above); de_DE is set to IsTranslated='Y' by 5827210
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585494 AND AD_Language = 'de_CH'
;
-- ============================================================================
-- 2) AD_Element: doctypes-tab title
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585495 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 11:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 11:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Belegarten', 'Belegarten',
        'Verkaufs- oder Einkaufsbelegarten, für die diese Kompensationsgruppen-Vertragseinstellung gilt.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585495, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585495
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Document types', PrintName = 'Document types',
    Description = 'Sales or purchase document types this compensation group contract settings record applies to.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 11:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585495 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (already copied by the skeleton insert above); de_DE is set to IsTranslated='Y' by 5827210
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 11:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585495 AND AD_Language = 'de_CH'
;
-- ============================================================================
-- 3) AD_Window
-- ============================================================================
INSERT INTO AD_Window (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsBetaFunctionality, IsDefault, IsEnableRemoteCacheInvalidation, IsOneInstanceOnly, IsSOTrx, Name, Processing, Updated, UpdatedBy, WindowType, WinHeight, WinWidth)
VALUES (0, 585494, 0, 542194 /*From ID Server*/, TO_TIMESTAMP('2026-09-28 11:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'N', 'N', 'Y', 'Kompensationsgruppen-Vertragseinstellungen', 'N', TO_TIMESTAMP('2026-09-28 11:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 'M', 0, 0)
;
INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Window_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Window t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Window_ID = 542194
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = t.AD_Window_ID)
;
/* DDL */ select update_window_translation_from_ad_element(585494)
;
DELETE FROM AD_Element_Link WHERE AD_Window_ID = 542194
;
/* DDL */ select AD_Element_Link_Create_Missing_Window(542194)
;
-- Wire the settings table's own window so FK/lookup fields (Table Direct / Search) referencing
-- it can zoom into it.
UPDATE AD_Table
SET AD_Window_ID = 542194, Updated = TO_TIMESTAMP('2026-09-28 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542650 /* C_CompensationGroup_ContractSettings */
;
-- ============================================================================
-- 4) AD_Tab: header (Name, Schema, IsActive) -- TabLevel=0
-- ============================================================================
INSERT INTO AD_Tab (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 585494, 0, 549507 /*From ID Server*/, 542650, 542194, 'Y', TO_TIMESTAMP('2026-09-28 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'N', 'N', 'C_CompensationGroup_ContractSettings', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 0, 'Kompensationsgruppen-Vertragseinstellungen', 'N', 10, 0, TO_TIMESTAMP('2026-09-28 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Tab_ID = 549507
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(585494)
;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 549507
;
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549507)
;
-- ============================================================================
-- 5) AD_Tab: Belegarten / Document types -- TabLevel=1, bound to parent via Parent_Column_ID
-- ============================================================================
INSERT INTO AD_Tab (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Parent_Column_ID, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 585495, 0, 549508 /*From ID Server*/, 542651, 542194, 'Y', TO_TIMESTAMP('2026-09-28 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'N', 'N', 'C_CompensationGroup_ContractSettings_DocType', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Belegarten', 593668 /* C_CompensationGroup_ContractSettings_DocType.C_CompensationGroup_ContractSettings_ID */, 'N', 20, 1, TO_TIMESTAMP('2026-09-28 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Tab_ID = 549508
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(585495)
;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 549508
;
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549508)
;
-- ============================================================================
-- 6) AD_Val_Rule: restrict C_DocType_ID to DocBaseType IN ('SOO','POO')
-- ============================================================================
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Type, Code, EntityType)
VALUES (540803 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 11:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 11:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_DocType SOO/POO', 'S', 'C_DocType.DocBaseType IN (''SOO'',''POO'')', 'de.metas.contracts')
;
UPDATE AD_Column
SET AD_Val_Rule_ID = 540803, Updated = TO_TIMESTAMP('2026-09-28 11:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593669 /* C_CompensationGroup_ContractSettings_DocType.C_DocType_ID */
;
-- ============================================================================
-- 7) AD_Field: header tab (Name, Schema, IsActive)
-- ============================================================================
-- Name
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SortNo, Updated, UpdatedBy)
VALUES (0, 593658, 785583 /*From ID Server*/, 0, 549507, TO_TIMESTAMP('2026-09-28 11:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 255, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Name', 1, TO_TIMESTAMP('2026-09-28 11:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785583
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(469 /*Name element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785583
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785583)
;
-- Schema (C_CompensationGroup_Schema_ID)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593659, 785584 /*From ID Server*/, 0, 549507, TO_TIMESTAMP('2026-09-28 11:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Kompensationsgruppe Schema', TO_TIMESTAMP('2026-09-28 11:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785584
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(543889 /*Kompensationsgruppe Schema element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785584
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785584)
;
-- IsActive
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593654, 785585 /*From ID Server*/, 0, 549507, TO_TIMESTAMP('2026-09-28 11:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Aktiv', TO_TIMESTAMP('2026-09-28 11:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785585
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785585
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785585)
;
-- AD_Org_ID / AD_Client_ID (bottom-right "org" group, per master-data cornerstone)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593651, 785589 /*From ID Server*/, 0, 549507, TO_TIMESTAMP('2026-09-28 11:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Sektion', TO_TIMESTAMP('2026-09-28 11:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785589
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(113 /*AD_Org_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785589
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785589)
;
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593650, 785590 /*From ID Server*/, 0, 549507, TO_TIMESTAMP('2026-09-28 11:00:38', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Mandant', TO_TIMESTAMP('2026-09-28 11:00:38', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785590
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(102 /*AD_Client_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785590
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785590)
;
-- ============================================================================
-- 8) AD_Field: doctypes tab (C_DocType_ID, IsActive)
-- ============================================================================
-- C_DocType_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593669, 785586 /*From ID Server*/, 0, 549508, TO_TIMESTAMP('2026-09-28 11:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Belegart', TO_TIMESTAMP('2026-09-28 11:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785586
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(196 /*C_DocType_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785586
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785586)
;
-- IsActive
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593664, 785587 /*From ID Server*/, 0, 549508, TO_TIMESTAMP('2026-09-28 11:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Aktiv', TO_TIMESTAMP('2026-09-28 11:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785587
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785587
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785587)
;
-- ============================================================================
-- 9) UI layout: header tab (2-column: left "default" primary, right "flags")
-- ============================================================================
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 549507, 548000 /*From ID Server*/, TO_TIMESTAMP('2026-09-28 11:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-09-28 11:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language, AD_UI_Section_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_UI_Section_ID = 548000
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID)
;
-- Left column (SeqNo=10)
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549768 /*From ID Server*/, 548000, TO_TIMESTAMP('2026-09-28 11:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-09-28 11:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Right column (SeqNo=20)
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549769 /*From ID Server*/, 548000, TO_TIMESTAMP('2026-09-28 11:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 20, TO_TIMESTAMP('2026-09-28 11:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Left column: primary group (Name, Schema)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549768, 555794 /*From ID Server*/, TO_TIMESTAMP('2026-09-28 11:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'default', 10, 'primary', TO_TIMESTAMP('2026-09-28 11:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Right column: flags group (IsActive)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549769, 555795 /*From ID Server*/, TO_TIMESTAMP('2026-09-28 11:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'flags', 10, NULL, TO_TIMESTAMP('2026-09-28 11:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: Name (SeqNo=10, grid SeqNoGrid=10)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785583, 0, 549507, 555794, 654911 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-09-28 11:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Name', 10, 10, 0, TO_TIMESTAMP('2026-09-28 11:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: Schema (SeqNo=20, grid SeqNoGrid=20)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785584, 0, 549507, 555794, 654912 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-09-28 11:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Kompensationsgruppe Schema', 20, 20, 0, TO_TIMESTAMP('2026-09-28 11:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: IsActive (SeqNo=10 in flags group, grid SeqNoGrid=30)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785585, 0, 549507, 555795, 654913 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-09-28 11:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktiv', 10, 30, 0, TO_TIMESTAMP('2026-09-28 11:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Right column: org group (Org then Client), below the flags group -- master-data cornerstone
-- "bottom-right: Organisation, then Client".
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549769, 555797 /*From ID Server*/, TO_TIMESTAMP('2026-09-28 11:00:39', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'org', 20, NULL, TO_TIMESTAMP('2026-09-28 11:00:39', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: AD_Org_ID (SeqNo=10, grid SeqNoGrid=40)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785589, 0, 549507, 555797, 654917 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-09-28 11:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Sektion', 10, 40, 0, TO_TIMESTAMP('2026-09-28 11:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: AD_Client_ID (SeqNo=20, not shown in grid)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785590, 0, 549507, 555797, 654918 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-09-28 11:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Mandant', 20, 0, 0, TO_TIMESTAMP('2026-09-28 11:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- ============================================================================
-- 10) UI layout: doctypes tab (single column, grid-first, one primary group)
-- ============================================================================
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 549508, 548001 /*From ID Server*/, TO_TIMESTAMP('2026-09-28 11:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-09-28 11:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100, 'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language, AD_UI_Section_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_UI_Section_ID = 548001
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID)
;
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549770 /*From ID Server*/, 548001, TO_TIMESTAMP('2026-09-28 11:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-09-28 11:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549770, 555796 /*From ID Server*/, TO_TIMESTAMP('2026-09-28 11:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'default', 10, 'primary', TO_TIMESTAMP('2026-09-28 11:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: C_DocType_ID (SeqNo=10, grid SeqNoGrid=10)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785586, 0, 549508, 555796, 654914 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-09-28 11:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Belegart', 10, 10, 0, TO_TIMESTAMP('2026-09-28 11:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: IsActive (SeqNo=20, grid SeqNoGrid=20)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785587, 0, 549508, 555796, 654915 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-09-28 11:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktiv', 20, 20, 0, TO_TIMESTAMP('2026-09-28 11:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- ============================================================================
-- 11) AD_Menu + tree placement (under Vertragsverwaltung > Typspezifische Einstellungen)
-- ============================================================================
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Window_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
VALUES ('W', 0, 585494, 542364 /*From ID Server*/, 0, 542194, TO_TIMESTAMP('2026-09-28 11:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'C_CompensationGroup_ContractSettings', 'Y', 'N', 'N', 'N', 'N', 'Kompensationsgruppen-Vertragseinstellungen', TO_TIMESTAMP('2026-09-28 11:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Menu_ID = 542364
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID)
;
-- Place under parent 1000070 "Typspezifische Einstellungen" (Vertragsverwaltung), last position
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', now(), 100, now(), 100, t.AD_Tree_ID, 542364, 1000070, 999
FROM AD_Tree t
WHERE t.AD_Client_ID = 0 AND t.IsActive = 'Y' AND t.IsAllNodes = 'Y' AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = t.AD_Tree_ID AND Node_ID = 542364)
;
/* DDL */ select update_menu_translation_from_ad_element(585494)
;
-- ============================================================================
-- 12) Contract Conditions window: add C_CompensationGroup_ContractSettings_ID field,
--     displayed only for Type_Conditions=CompensationGroup, zooming to the new window.
-- ============================================================================
-- Cross-document navigation: allow this FK to surface as a zoom target.
UPDATE AD_Column
SET IsExcludeFromZoomTargets = 'N', Updated = TO_TIMESTAMP('2026-09-28 11:00:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593670 /* C_Flatrate_Conditions.C_CompensationGroup_ContractSettings_ID */
;
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, DisplayLogic, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593670, 785588 /*From ID Server*/, 0, 540331, TO_TIMESTAMP('2026-09-28 11:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, '@Type_Conditions/''''@=''CompensationGroup''', 'D', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Einstellungen für Kompensationsgruppen-Verträge', TO_TIMESTAMP('2026-09-28 11:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785588
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585492 /*shared C_CompensationGroup_ContractSettings_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785588
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785588)
;
-- Place into the existing left "default" primary group (540760) of tab 540331, after the
-- last existing settings-FK field (C_LicenseFeeSettings_ID, SeqNo=80) -- same group/column as
-- the sibling type-specific settings fields (Hierarchy/Mediated/LicenseFee).
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785588, 0, 540331, 540760, 654916 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-09-28 11:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Einstellungen für Kompensationsgruppen-Verträge', 90, 0, 0, TO_TIMESTAMP('2026-09-28 11:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
