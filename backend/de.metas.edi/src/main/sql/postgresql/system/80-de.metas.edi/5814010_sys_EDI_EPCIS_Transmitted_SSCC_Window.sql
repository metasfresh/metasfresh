-- Dedicated, searchable header window over EDI_EPCIS_Transmitted_SSCC (AD_Table 542624) — the EPCIS
-- transmission ledger. Replaces the per-shipment diagnostic child tab 549333 (added by 5813400 on the
-- Lieferung window 169), which this script DEACTIVATES: a header window lets support/ops search the
-- ledger ACROSS shipments (by transmission date range, SSCC18, and shipment), which the per-shipment
-- tab could not.
--
-- Read-only-except-deactivate, exactly like the tab it replaces: rows are written only by the
-- send-confirmation success listener, so IsInsertRecord='N' and the table's own IsDeleteable='N'
-- (set at table creation, 5813290) keep create + hard-delete unavailable. Every DATA field keeps AD_Field.IsReadOnly='Y';
-- only IsActive is editable (tab-level IsReadOnly='N') — the escape hatch: deactivating a ledger row
-- makes that physical SSCC eligible for re-transmission again (get_epcis_events_json_fn's
-- ledger-exclusion only matches active rows).
--
-- Filters (AD_Column.IsSelectionColumn, + IsRangeFilter for the date): Transmitted (date range),
-- SSCC18 (text), M_InOut_ID (shipment). These columns are used ONLY by this table, so setting the
-- column-level selection flags is scoped in effect to this window.
--
-- Menu: placed under the "Lieferung" menu folder (AD_Menu 1000019, main tree AD_Tree_ID=10), where the
-- other EDI/shipment windows live (e.g. AD_Menu 541406 "EDI Lieferavis SSCC (DESADV)").
--
-- Reuses AD_Element 585086 (caption "EPCIS-SSCC-Übertragungen") and 585087 (field label
-- "Exportkonfiguration"/"Export Configuration") created by 5813400 — identical wording applies here.
--
-- IDs allocated from idserver.metas.de on 2026-07-15:
--   AD_MigrationScript 5814010
--   AD_Window          542174
--   AD_Tab             549351
--   AD_UI_Section      547859
--   AD_UI_Column       549602 (left) · 549603 (right)
--   AD_UI_ElementGroup 555510 (primary, left) · 555511 (flags, right) · 555512 (org, right)
--   AD_Menu            542346
--   AD_Field           781733 (IsActive→flags)  / AD_UI_Element 652659
--   AD_Field           781734 (SSCC18)          / AD_UI_Element 652660
--   AD_Field           781735 (M_InOut_ID)      / AD_UI_Element 652661
--   AD_Field           781736 (ExportCfg, AD_Name_ID=585087) / AD_UI_Element 652662
--   AD_Field           781737 (Transmitted)     / AD_UI_Element 652663
--   AD_Field           781738 (Updated)         / AD_UI_Element 652664
--   AD_Field           781739 (AD_Org_ID→org)   / AD_UI_Element 652665
--   AD_Field           781740 (AD_Client_ID→org)/ AD_UI_Element 652666
-- Layout: 2-column cornerstone — left PRIMARY group (data fields), right FLAGS group (IsActive)
-- then ORG group (AD_Org_ID, AD_Client_ID). UIStyle only 'primary'/NULL (never 'default').
-- Reused AD_Column/AD_Element from 5813290/5813400:
--   592929 IsActive(348) · 592934 SSCC18(585084) · 592935 ExportCfg(584101, label via 585087)
--   592936 M_InOut_ID(1025) · 592937 Transmitted(585085) · 592932 Updated(607)
--   592928 AD_Org_ID(113) · 592927 AD_Client_ID(102)

-- ===========================================================================
-- PART 1: AD_Window
-- ===========================================================================
-- 2026-07-15T10:00:00Z
INSERT INTO AD_Window
    (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Element_ID, EntityType, Name, WindowType, IsSOTrx,
     IsBetaFunctionality, IsDefault, IsEnableRemoteCacheInvalidation, IsExcludeFromZoomTargets,
     IsOneInstanceOnly, IsOverrideInMenu, ZoomInToPriority)
VALUES
    (542174 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-15 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-15 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     585086 /*caption element*/, 'de.metas.esb.edi', 'EPCIS-SSCC-Übertragungen', 'M', 'Y',
     'N', 'N', 'N', 'N',
     'N', 'N', 0)
;

-- 2026-07-15T10:00:01Z
INSERT INTO AD_Window_Trl
    (AD_Language, AD_Window_ID, Name, Description, Help, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Window_ID, t.Name, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Window t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Window_ID=542174
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;
-- 2026-07-15T10:00:02Z
UPDATE AD_Window_Trl SET Name='EPCIS SSCC Transmissions', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-15 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Window_ID=542174;
-- 2026-07-15T10:00:03Z
UPDATE AD_Window_Trl SET Name='EPCIS-SSCC-Übertragungen', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-15 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Window_ID=542174;

-- ===========================================================================
-- PART 2: header tab (TabLevel=0) over the ledger table
-- ===========================================================================
-- 2026-07-15T10:01:00Z
INSERT INTO AD_Tab
    (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AD_Element_ID,
     Created, CreatedBy, Description, EntityType,
     HasTree, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsInfoTab, IsInsertRecord,
     IsReadOnly, IsRefreshAllOnActivate, IsSearchActive, IsSingleRow, IsSortTab, IsTranslationTab,
     Name, OrderByClause, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES
    (0, 0, 549351 /*From ID Server*/, 542624 /*EDI_EPCIS_Transmitted_SSCC*/, 542174 /*new window*/,
     585086 /*caption element*/,
     TO_TIMESTAMP('2026-07-15 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 'de.metas.esb.edi',
     'N', 'Y', 'N', 'N', 'N', 'N' /*no insert — rows are system-written*/,
     'N' /*tab-level 'N' so the IsActive field edit is reachable; every DATA field is IsReadOnly='Y'*/,
     'N', 'N', 'N' /*list-first: open in grid, not single-row*/, 'N', 'N',
     'EPCIS-SSCC-Übertragungen', 'Transmitted DESC', 10, 0,
     TO_TIMESTAMP('2026-07-15 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100)
;
-- 2026-07-15T10:01:01Z
INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Tab_ID=549351
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;
-- 2026-07-15T10:01:02Z
UPDATE AD_Tab_Trl SET Name='EPCIS SSCC Transmissions', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-15 10:01:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Tab_ID=549351;
-- 2026-07-15T10:01:03Z
UPDATE AD_Tab_Trl SET Name='EPCIS-SSCC-Übertragungen', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-15 10:01:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Tab_ID=549351;

-- ===========================================================================
-- PART 3: UI layout (single section / column / element group)
-- ===========================================================================
-- 2026-07-15T10:02:00Z
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES (547859 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-15 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-15 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100, 549351, 10, 'default');
-- Left column (SeqNo=10)
-- 2026-07-15T10:02:01Z
INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES (549602 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-15 10:02:01','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-15 10:02:01','YYYY-MM-DD HH24:MI:SS'), 100, 547859, 10);
-- Right column (SeqNo=20)
-- 2026-07-15T10:02:01Z
INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES (549603 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-15 10:02:01','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-15 10:02:01','YYYY-MM-DD HH24:MI:SS'), 100, 547859, 20);
-- Left column: PRIMARY group (UIStyle='primary' — exactly one per tab) holds the data fields
-- 2026-07-15T10:02:02Z
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555510 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-15 10:02:02','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-15 10:02:02','YYYY-MM-DD HH24:MI:SS'), 100, 549602, 10, 'primary', 'default');
-- Right column: FLAGS group (IsActive first). UIStyle MUST be NULL (never 'default' — it crashes window load).
-- 2026-07-15T10:02:02Z
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555511 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-15 10:02:02','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-15 10:02:02','YYYY-MM-DD HH24:MI:SS'), 100, 549603, 10, NULL, 'flags');
-- Right column: ORG/CLIENT group (last) — AD_Org_ID then AD_Client_ID
-- 2026-07-15T10:02:02Z
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555512 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-15 10:02:02','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-15 10:02:02','YYYY-MM-DD HH24:MI:SS'), 100, 549603, 20, NULL, 'org');

-- ===========================================================================
-- PART 4: fields + UI elements
-- Grid/form order: IsActive, SSCC18, M_InOut (shipment), Exportkonfiguration, Transmitted, Updated
-- ===========================================================================

-- 4.0 IsActive (col 592929, element 348) — the one editable field (deactivate = re-transmit escape hatch)
-- 2026-07-15T10:03:00Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592929, 781733 /*From ID Server*/, 0, 549351, TO_TIMESTAMP('2026-07-15 10:03:00','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 1, 'de.metas.esb.edi', NULL, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N' /*editable — the one action support may take*/, 'N', 'Aktiv', 5, 5, 0, 1, 1, TO_TIMESTAMP('2026-07-15 10:03:00','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781733
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781733;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781733);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781733, 0, 549351, 555511 /*flags group*/, 652659 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-15 10:03:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktiv', 5, 5, 0, TO_TIMESTAMP('2026-07-15 10:03:01','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.1 SSCC18 (col 592934, element 585084) — read-only
-- 2026-07-15T10:03:10Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592934, 781734 /*From ID Server*/, 0, 549351, TO_TIMESTAMP('2026-07-15 10:03:10','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 30, 'de.metas.esb.edi', NULL, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y' /*read-only*/, 'N', 'SSCC18', 10, 10, 0, 1, 1, TO_TIMESTAMP('2026-07-15 10:03:10','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781734
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585084);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781734;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781734);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781734, 0, 549351, 555510, 652660 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-15 10:03:11','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'SSCC18', 10, 10, 0, TO_TIMESTAMP('2026-07-15 10:03:11','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.2 M_InOut_ID (col 592936, element 1025) — shipment, read-only (NEW: not shown on the old tab)
-- 2026-07-15T10:03:20Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592936, 781735 /*From ID Server*/, 0, 549351, TO_TIMESTAMP('2026-07-15 10:03:20','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 10, 'de.metas.esb.edi', NULL, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y' /*read-only*/, 'N', 'Lieferung/Wareneingang', 15, 15, 0, 1, 1, TO_TIMESTAMP('2026-07-15 10:03:20','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781735
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(1025);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781735;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781735);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781735, 0, 549351, 555510, 652661 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-15 10:03:21','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Lieferung/Wareneingang', 15, 15, 0, TO_TIMESTAMP('2026-07-15 10:03:21','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.3 Exportkonfiguration (col 592935, dedicated label element 585087) — read-only
-- 2026-07-15T10:03:30Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592935, 781736 /*From ID Server*/, 585087 /*dedicated label*/, 0, 549351, TO_TIMESTAMP('2026-07-15 10:03:30','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 10, 'de.metas.esb.edi', NULL, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y' /*read-only*/, 'N', 'Exportkonfiguration', 20, 20, 0, 1, 1, TO_TIMESTAMP('2026-07-15 10:03:30','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781736
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585087);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781736;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781736);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781736, 0, 549351, 555510, 652662 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-15 10:03:31','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Exportkonfiguration', 20, 20, 0, TO_TIMESTAMP('2026-07-15 10:03:31','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.4 Transmitted (col 592937, element 585085) — read-only, DESC sort indicator
-- 2026-07-15T10:03:40Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592937, 781737 /*From ID Server*/, 0, 549351, TO_TIMESTAMP('2026-07-15 10:03:40','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 29, 'de.metas.esb.edi', NULL, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y' /*read-only*/, 'N', 'Übertragen am', 30, 30, -1 /*DESC sort indicator matches OrderByClause*/, 1, 1, TO_TIMESTAMP('2026-07-15 10:03:40','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781737
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585085);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781737;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781737);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781737, 0, 549351, 555510, 652663 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-15 10:03:41','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Übertragen am', 30, 30, 0, TO_TIMESTAMP('2026-07-15 10:03:41','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.5 Updated (col 592932, element 607) — read-only, last
-- 2026-07-15T10:03:50Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592932, 781738 /*From ID Server*/, 0, 549351, TO_TIMESTAMP('2026-07-15 10:03:50','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 29, 'de.metas.esb.edi', NULL, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y' /*read-only*/, 'N', 'Aktualisiert', 40, 40, 0, 1, 1, TO_TIMESTAMP('2026-07-15 10:03:50','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781738
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(607);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781738;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781738);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781738, 0, 549351, 555510, 652664 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-15 10:03:51','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktualisiert', 40, 40, 0, TO_TIMESTAMP('2026-07-15 10:03:51','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.6 AD_Org_ID (col 592928, element 113) — org/client group, first; read-only, not in grid
-- 2026-07-15T10:04:00Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592928, 781739 /*From ID Server*/, 0, 549351, TO_TIMESTAMP('2026-07-15 10:04:00','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 10, 'de.metas.esb.edi', NULL, 'Y', 'Y', 'N' /*not in grid*/, 'N', 'N', 'N', 'N', 'Y' /*read-only*/, 'N', 'Organisation', 50, 50, 0, 1, 1, TO_TIMESTAMP('2026-07-15 10:04:00','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781739
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781739;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781739);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781739, 0, 549351, 555512 /*org group*/, 652665 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-15 10:04:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Organisation', 10, 0, 0, TO_TIMESTAMP('2026-07-15 10:04:01','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.7 AD_Client_ID (col 592927, element 102) — org/client group, second; read-only, not in grid
-- 2026-07-15T10:04:02Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592927, 781740 /*From ID Server*/, 0, 549351, TO_TIMESTAMP('2026-07-15 10:04:02','YYYY-MM-DD HH24:MI:SS'), 100, NULL, 10, 'de.metas.esb.edi', NULL, 'Y', 'Y', 'N' /*not in grid*/, 'N', 'N', 'N', 'N', 'Y' /*read-only*/, 'N', 'Mandant', 60, 60, 0, 1, 1, TO_TIMESTAMP('2026-07-15 10:04:02','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781740
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(102);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781740;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781740);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781740, 0, 549351, 555512 /*org group*/, 652666 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-15 10:04:03','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Mandant', 20, 0, 0, TO_TIMESTAMP('2026-07-15 10:04:03','YYYY-MM-DD HH24:MI:SS'), 100);

-- ===========================================================================
-- PART 5: filter columns (selection + date range). These columns are used ONLY by this table.
-- ===========================================================================
-- 2026-07-15T10:04:00Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,
    Updated=TO_TIMESTAMP('2026-07-15 10:04:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=592934 /*SSCC18*/;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,
    Updated=TO_TIMESTAMP('2026-07-15 10:04:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=592936 /*M_InOut_ID*/;
UPDATE AD_Column SET IsSelectionColumn='Y', IsRangeFilter='Y', SelectionColumnSeqNo=30,
    Updated=TO_TIMESTAMP('2026-07-15 10:04:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=592937 /*Transmitted (date range)*/;

-- Reverse cross-document navigation: allow zoom FROM a shipment (M_InOut) TO its ledger rows in this
-- window. The child tab 549333 used to give this in-context access; with it deactivated, the shipment
-- needs the related-documents zoom target instead. This FK column is used only by this table.
-- 2026-07-15T10:04:10Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',
    Updated=TO_TIMESTAMP('2026-07-15 10:04:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=592936 /*M_InOut_ID*/;

-- ===========================================================================
-- PART 6: AD_Menu under the "Lieferung" folder (AD_Menu 1000019, main tree AD_Tree_ID=10)
-- ===========================================================================
-- 2026-07-15T10:05:00Z
INSERT INTO AD_Menu
    (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Window_ID,
     Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary,
     Name, Updated, UpdatedBy)
VALUES
    ('W', 0, 585086 /*caption element*/, 542346 /*From ID Server*/, 0, 542174 /*new window*/,
     TO_TIMESTAMP('2026-07-15 10:05:00','YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.esb.edi',
     'EPCIS_SSCC_Transmissions', 'Y', 'N', 'N', 'N', 'N',
     'EPCIS-SSCC-Übertragungen', TO_TIMESTAMP('2026-07-15 10:05:00','YYYY-MM-DD HH24:MI:SS'), 100)
;
-- 2026-07-15T10:05:01Z
INSERT INTO AD_Menu_Trl
    (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=542346
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;
-- 2026-07-15T10:05:02Z
UPDATE AD_Menu_Trl SET Name='EPCIS SSCC Transmissions', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-15 10:05:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Menu_ID=542346;

-- Tree node under the "Lieferung" folder (Parent_ID=1000019) on the main menu tree (AD_Table_ID=116)
-- 2026-07-15T10:05:03Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', now(), 100, now(), 100, t.AD_Tree_ID, 542346, 1000019, 900
FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116
  AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND e.Node_ID=542346)
;

-- ===========================================================================
-- PART 7: deactivate the replaced per-shipment child tab 549333 (+ its fields/UI)
-- ===========================================================================
-- 2026-07-15T10:06:00Z
UPDATE AD_UI_Element  SET IsActive='N', Updated=TO_TIMESTAMP('2026-07-15 10:06:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Tab_ID=549333;
UPDATE AD_UI_ElementGroup SET IsActive='N', Updated=TO_TIMESTAMP('2026-07-15 10:06:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Column_ID IN (SELECT AD_UI_Column_ID FROM AD_UI_Column WHERE AD_UI_Section_ID IN (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID=549333));
UPDATE AD_UI_Column  SET IsActive='N', Updated=TO_TIMESTAMP('2026-07-15 10:06:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Section_ID IN (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID=549333);
UPDATE AD_UI_Section SET IsActive='N', Updated=TO_TIMESTAMP('2026-07-15 10:06:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Tab_ID=549333;
UPDATE AD_Field      SET IsActive='N', Updated=TO_TIMESTAMP('2026-07-15 10:06:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Tab_ID=549333;
UPDATE AD_Tab        SET IsActive='N', Updated=TO_TIMESTAMP('2026-07-15 10:06:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Tab_ID=549333;

-- ===========================================================================
-- PART 8: backfill translations
-- ===========================================================================
-- 2026-07-15T10:07:00Z
SELECT add_missing_translations();
