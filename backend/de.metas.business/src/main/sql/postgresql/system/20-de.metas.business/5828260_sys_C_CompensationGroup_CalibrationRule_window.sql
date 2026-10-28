-- Calibration rules: window "Kalibrierungsregeln" / "Calibration rules".
--
-- Master-data window over C_CompensationGroup_CalibrationRule (AD_Table_ID=542655, from
-- migration 5828210): one header tab, 2-column layout (left primary group with the rule
-- criteria and the calibration factor; right "flags" group with IsActive, then an "org" group
-- with Org and Client). The grid sorts by SeqNo ascending (the rule order is its meaning).
-- BPartner, BP group, product, product category and schema are quick filters (IsSelectionColumn
-- was set on the columns by 5828210). Menu entry next to "Kompensationsgruppe Schema".
-- The table's own window pointer is set so the rule FK on the order line can zoom into it.
--
-- IDs allocated from idserver.metas.de on 2026-10-06:
--   AD_MigrationScript 5828260 (this script)
--   AD_Element 585524 (window / tab / menu title)
--   AD_Window 542195, AD_Tab 549513, AD_UI_Section 548005, AD_UI_Column 549774 (left), 549775 (right)
--   AD_UI_ElementGroup 555801 (left primary), 555802 (right flags), 555803 (right org)
--   AD_Field 785613..785623, AD_UI_Element 654940..654950, AD_Menu 542365
-- Reused: the shared AD_Elements of the table columns (incl. 585522 Kalibrierfaktor from 5828210).

-- ============================================================================
-- 1) AD_Element: window / tab / menu title
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585524 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.order', 'Kalibrierungsregeln', 'Kalibrierungsregeln',
        'Regeln, die die Komponentenmengen von Kompensationsgruppen für Kunden oder Geschäftspartnergruppen skalieren.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585524, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585524
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Calibration rules', PrintName = 'Calibration rules',
    Description = 'Rules that scale the component quantities of compensation groups for customers or business partner groups.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585524 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (copied by the skeleton insert above)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585524 AND AD_Language IN ('de_DE', 'de_CH')
;
-- ============================================================================
-- 2) AD_Window (+ own-window pointer on the table)
-- ============================================================================
INSERT INTO AD_Window (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsBetaFunctionality, IsDefault, IsEnableRemoteCacheInvalidation, IsOneInstanceOnly, IsSOTrx, Name, Processing, Updated, UpdatedBy, WindowType, WinHeight, WinWidth)
VALUES (0, 585524, 0, 542195 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.order', 'Y', 'N', 'N', 'N', 'N', 'Y', 'Kalibrierungsregeln', 'N', TO_TIMESTAMP('2026-10-06 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'M', 0, 0)
;
INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Window_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Window t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Window_ID = 542195
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = t.AD_Window_ID)
;
/* DDL */ select update_window_translation_from_ad_element(585524)
;
DELETE FROM AD_Element_Link WHERE AD_Window_ID = 542195
;
/* DDL */ select AD_Element_Link_Create_Missing_Window(542195)
;
UPDATE AD_Table
SET AD_Window_ID = 542195, Updated = TO_TIMESTAMP('2026-10-06 12:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542655 /* C_CompensationGroup_CalibrationRule */
;
-- ============================================================================
-- 3) AD_Tab: header tab, TabLevel=0
-- ============================================================================
INSERT INTO AD_Tab (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 585524, 0, 549513 /*From ID Server*/, 542655, 542195, 'Y', TO_TIMESTAMP('2026-10-06 12:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.order', 'N', 'N', 'C_CompensationGroup_CalibrationRule', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 0, 'Kalibrierungsregeln', 'N', 10, 0, TO_TIMESTAMP('2026-10-06 12:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Tab_ID = 549513
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(585524)
;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 549513
;
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549513)
;
-- ============================================================================
-- 4) AD_Field (SortNo=1 on SeqNo = default grid sort ascending)
-- ============================================================================
-- Reihenfolge
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SortNo, Updated, UpdatedBy)
VALUES (0, 593722, 785613 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Reihenfolge', 1, TO_TIMESTAMP('2026-10-06 12:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785613
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(566)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785613
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785613)
;
-- Geschäftspartner
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593723, 785614 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Geschäftspartner', TO_TIMESTAMP('2026-10-06 12:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785614
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(187)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785614
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785614)
;
-- Geschäftspartnergruppe
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593724, 785615 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Geschäftspartnergruppe', TO_TIMESTAMP('2026-10-06 12:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785615
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(1383)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785615
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785615)
;
-- Produkt
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593725, 785616 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Produkt', TO_TIMESTAMP('2026-10-06 12:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785616
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(454)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785616
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785616)
;
-- Produkt Kategorie
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593726, 785617 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Produkt Kategorie', TO_TIMESTAMP('2026-10-06 12:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785617
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(453)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785617
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785617)
;
-- Kompensationsgruppe Schema
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593727, 785618 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Kompensationsgruppe Schema', TO_TIMESTAMP('2026-10-06 12:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785618
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(543889)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785618
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785618)
;
-- Kalibrierfaktor
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593728, 785619 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Kalibrierfaktor', TO_TIMESTAMP('2026-10-06 12:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785619
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585522)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785619
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785619)
;
-- Beschreibung
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593729, 785620 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 255, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Beschreibung', TO_TIMESTAMP('2026-10-06 12:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785620
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(275)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785620
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785620)
;
-- Aktiv
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593717, 785621 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Aktiv', TO_TIMESTAMP('2026-10-06 12:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785621
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(348)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785621
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785621)
;
-- Sektion
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593716, 785622 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Sektion', TO_TIMESTAMP('2026-10-06 12:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785622
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(113)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785622
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785622)
;
-- Mandant
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593715, 785623 /*From ID Server*/, 0, 549513, TO_TIMESTAMP('2026-10-06 12:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.order', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Mandant', TO_TIMESTAMP('2026-10-06 12:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785623
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(102)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785623
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785623)
;
-- ============================================================================
-- 5) UI layout: 2-column header tab (left primary group; right flags group, then org group)
-- ============================================================================
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 549513, 548005 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 12:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-10-06 12:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100, 'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language, AD_UI_Section_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_UI_Section_ID = 548005
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID)
;
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549774 /*From ID Server*/, 548005, TO_TIMESTAMP('2026-10-06 12:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-10-06 12:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549775 /*From ID Server*/, 548005, TO_TIMESTAMP('2026-10-06 12:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 20, TO_TIMESTAMP('2026-10-06 12:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549774, 555801 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 12:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'default', 10, 'primary', TO_TIMESTAMP('2026-10-06 12:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549775, 555802 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 12:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'flags', 10, NULL, TO_TIMESTAMP('2026-10-06 12:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549775, 555803 /*From ID Server*/, TO_TIMESTAMP('2026-10-06 12:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'org', 20, NULL, TO_TIMESTAMP('2026-10-06 12:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785613, 0, 549513, 555801, 654940 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Reihenfolge', 10, 10, 0, TO_TIMESTAMP('2026-10-06 12:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785614, 0, 549513, 555801, 654941 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Geschäftspartner', 20, 20, 0, TO_TIMESTAMP('2026-10-06 12:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785615, 0, 549513, 555801, 654942 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Geschäftspartnergruppe', 30, 30, 0, TO_TIMESTAMP('2026-10-06 12:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785616, 0, 549513, 555801, 654943 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produkt', 40, 40, 0, TO_TIMESTAMP('2026-10-06 12:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785617, 0, 549513, 555801, 654944 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produkt Kategorie', 50, 50, 0, TO_TIMESTAMP('2026-10-06 12:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785618, 0, 549513, 555801, 654945 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Kompensationsgruppe Schema', 60, 60, 0, TO_TIMESTAMP('2026-10-06 12:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785619, 0, 549513, 555801, 654946 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Kalibrierfaktor', 70, 70, 0, TO_TIMESTAMP('2026-10-06 12:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785620, 0, 549513, 555801, 654947 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Beschreibung', 80, 80, 0, TO_TIMESTAMP('2026-10-06 12:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785621, 0, 549513, 555802, 654948 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktiv', 10, 90, 0, TO_TIMESTAMP('2026-10-06 12:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785622, 0, 549513, 555803, 654949 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Sektion', 10, 100, 0, TO_TIMESTAMP('2026-10-06 12:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785623, 0, 549513, 555803, 654950 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-06 12:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Mandant', 20, 0, 0, TO_TIMESTAMP('2026-10-06 12:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- ============================================================================
-- 6) AD_Menu + tree placement (next to "Kompensationsgruppe Schema", folder 1000037)
-- ============================================================================
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Window_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
VALUES ('W', 0, 585524, 542365 /*From ID Server*/, 0, 542195, TO_TIMESTAMP('2026-10-06 12:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.order', 'C_CompensationGroup_CalibrationRule', 'Y', 'N', 'N', 'N', 'N', 'Kalibrierungsregeln', TO_TIMESTAMP('2026-10-06 12:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Menu_ID = 542365
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID)
;
-- Place in the folder of the "Kompensationsgruppe Schema" entry (parent 1000037), right after it
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', TO_TIMESTAMP('2026-10-06 12:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 12:00:38', 'YYYY-MM-DD HH24:MI:SS'), 100, t.AD_Tree_ID, 542365, 1000037, 8
FROM AD_Tree t
WHERE t.AD_Client_ID = 0 AND t.IsActive = 'Y' AND t.IsAllNodes = 'Y' AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = t.AD_Tree_ID AND Node_ID = 542365)
;
/* DDL */ select update_menu_translation_from_ad_element(585524)
;
