-- Diagnostic child tab for EDI_EPCIS_Transmitted_SSCC (AD_Table 542624) on the Lieferung /
-- shipment window (AD_Window_ID=169). Lets support/ops see, per shipment, which SSCCs have
-- already been transmitted to which EPCIS receiver config, and when. The ledger data itself
-- (SSCC18, export config, transmission timestamp) is read-only — support cannot edit or add
-- rows — but the tab DOES let support deactivate a row (IsActive='N'): the ledger-exclusion
-- filter in get_epcis_events_json_fn only matches active rows, so deactivating a row makes that
-- physical SSCC eligible for re-transmission again (e.g. after a confirmed-lost delivery to the
-- EPCIS receiver).
--
-- Started from the read-only diagnostic tab pattern of AD_Tab 549295 ("EPCIS-Exportstatus",
-- added by 5806870/5807070 for ExternalSystem_ScriptedExportConversion_Status): TabLevel=1,
-- single-section/single-column/single-element-group layout. Diverges from that sibling on
-- IsReadOnly: this tab sets IsReadOnly='N' at the tab level (required for the IsActive field's
-- own edit to be reachable at all — a tab-level IsReadOnly='Y' would block every field
-- including IsActive), while every DATA field keeps its own AD_Field.IsReadOnly='Y'.
-- IsInsertRecord stays 'N' (no new rows) and the table's own IsDeleteable='N' (set by the
-- 78dc8660bb0 table-creation migration) keeps the hard-delete row action unavailable — the only
-- action available on this tab is deactivate/reactivate a row.
--
-- Key difference from that sibling: ExternalSystem_ScriptedExportConversion_Status is linked
-- polymorphically (AD_Table_ID=319 + Record_ID=@M_InOut_ID@, via AD_Tab.Parent_Column_ID pointing
-- at its Record_ID column plus an explicit WhereClause). EDI_EPCIS_Transmitted_SSCC instead carries
-- a direct M_InOut_ID foreign key, so this tab links the standard way: AD_Tab.Parent_Column_ID
-- points directly at that FK column (592936) and no WhereClause is needed — the same mechanism
-- used by the window's other direct-FK child tabs (e.g. AD_Tab 187 "Auftragsposition" on the Sales
-- Order window, Parent_Column_ID -> C_OrderLine.C_Order_ID).
--
-- Columns shown: IsActive (the one editable/actionable field, placed first), SSCC18,
-- ExternalSystem_Config_ScriptedExportConversion_ID (label override "Exportkonfiguration" /
-- "Export Configuration" via a dedicated forked element — see PART 4.2 below for why a raw
-- AD_Field_Trl.Name override on the shared column element is unsafe), Transmitted (drives the
-- default sort, newest first), and Updated (shown last, mirroring the sibling tab).
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5813400
--   AD_Element         585086 (tab caption: EPCIS-SSCC-Übertragungen)
--   AD_Element         585087 (forked field label: Exportkonfiguration / Export Configuration)
--   AD_Tab             549333
--   AD_UI_Section      547842
--   AD_UI_Column       549583
--   AD_UI_ElementGroup 555487
--   AD_Field           781419 (IsActive)
--   AD_UI_Element      652535 (IsActive)
--   AD_Field           781376 (SSCC18)
--   AD_UI_Element      652492 (SSCC18)
--   AD_Field           781377 (ExternalSystem_Config_ScriptedExportConversion_ID, AD_Name_ID=585087)
--   AD_UI_Element      652493 (ExternalSystem_Config_ScriptedExportConversion_ID)
--   AD_Field           781378 (Transmitted)
--   AD_UI_Element      652494 (Transmitted)
--   AD_Field           781379 (Updated)
--   AD_UI_Element      652495 (Updated)
--
-- Reused existing AD_Column/AD_Element from 5813290:
--   AD_Column 592934 (SSCC18)          -> AD_Element 585084
--   AD_Column 592935 (ExternalSystem_Config_ScriptedExportConversion_ID) -> AD_Element 584101 (column
--     element itself untouched; the field's display label instead comes from AD_Name_ID=585087)
--   AD_Column 592936 (M_InOut_ID, parent link column — not shown as a field)
--   AD_Column 592937 (Transmitted)     -> AD_Element 585085
--   AD_Column 592932 (Updated)         -> AD_Element 607

-- ===========================================================================
-- PART 1: AD_Element for the child tab caption
-- ===========================================================================

-- 2026-07-13T09:00:00Z
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, EntityType, Name, PrintName)
VALUES
    (585086 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-13 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-13 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'EPCIS_SSCC_Transmitted_Tab', 'de.metas.esb.edi',
     'EPCIS-SSCC-Übertragungen', 'EPCIS-SSCC-Übertragungen')
;

-- 2026-07-13T09:00:01Z
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 585086
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- en_US override
-- 2026-07-13T09:00:02Z
UPDATE AD_Element_Trl
SET Name = 'EPCIS SSCC Transmissions', PrintName = 'EPCIS SSCC Transmissions',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-13 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585086
;

-- de_DE / de_CH: mark as translated (name matches the base German text)
-- 2026-07-13T09:00:03Z
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-13 09:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585086
;

-- ===========================================================================
-- PART 2: Diagnostic, deactivate-capable child tab over EDI_EPCIS_Transmitted_SSCC
-- ===========================================================================

-- Parent_Column_ID = 592936 (M_InOut_ID — the direct FK in this table's own row set to the
-- parent shipment). No WhereClause needed: unlike the polymorphic sibling, the link is a plain
-- FK, so the framework filters by Parent_Column_ID alone.
-- 2026-07-13T09:01:00Z
INSERT INTO AD_Tab
    (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID,
     AD_Element_ID,
     Created, CreatedBy, Description, EntityType,
     HasTree, IsActive, IsAdvancedTab, IsCheckParentsChanged,
     IsInfoTab, IsInsertRecord, IsReadOnly, IsRefreshAllOnActivate,
     IsSearchActive, IsSingleRow, IsSortTab, IsTranslationTab,
     Name, OrderByClause, SeqNo, TabLevel,
     Parent_Column_ID,
     Updated, UpdatedBy)
VALUES
    (0, 0, 549333 /*From ID Server*/, 542624 /*EDI_EPCIS_Transmitted_SSCC*/, 169 /*Lieferung window*/,
     585086 /*From ID Server*/,
     TO_TIMESTAMP('2026-07-13 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 'de.metas.esb.edi',
     'N', 'Y', 'N', 'N',
     'N', 'N' /*no insert — rows are system-written*/,
     'N' /*tab-level must be 'N' so the IsActive field's own edit is reachable at all; every
            DATA AD_Field below still carries its own IsReadOnly='Y'*/,
     'N',
     'N', 'N', 'N', 'N',
     'EPCIS-SSCC-Übertragungen', 'Transmitted DESC', 70, 1,
     592936 /*M_InOut_ID — direct FK link to the parent shipment*/,
     TO_TIMESTAMP('2026-07-13 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2026-07-13T09:01:01Z
INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Tab_ID = 549333
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;

-- en_US translation
-- 2026-07-13T09:01:02Z
UPDATE AD_Tab_Trl
SET Name         = 'EPCIS SSCC Transmissions',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-13 09:01:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US'
  AND AD_Tab_ID = 549333
;

-- de_DE / de_CH
-- 2026-07-13T09:01:03Z
UPDATE AD_Tab_Trl
SET Name         = 'EPCIS-SSCC-Übertragungen',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-13 09:01:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Tab_ID = 549333
;

-- ===========================================================================
-- PART 3: UI layout for the tab (included tab — single element group)
-- ===========================================================================

-- 3a) AD_UI_Section (one per tab)
-- 2026-07-13T09:02:00Z
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_Tab_ID, SeqNo, Value)
VALUES
    (547842 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-13 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-13 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549333, 10, 'default')
;

-- 3b) Single UI column (included tabs use a single column)
-- 2026-07-13T09:02:01Z
INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_UI_Section_ID, SeqNo)
VALUES
    (549583 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-13 09:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-13 09:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547842, 10)
;

-- 3c) Single element group (included tabs must have exactly 1 element group, UIStyle=NULL)
-- 2026-07-13T09:02:02Z
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555487 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-13 09:02:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-13 09:02:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549583, 10, NULL, 'default')
;

-- ===========================================================================
-- PART 4: AD_Field + AD_UI_Element rows for each ledger column
-- Grid + form columns: IsActive, SSCC18, Exportkonfiguration, Transmitted, Updated
-- Sort indicator SortNo=-1 on Transmitted (Transmitted DESC per OrderByClause)
-- ===========================================================================

-- 4.0 IsActive (AD_Column 592929, AD_Element 348 — the standard shared "Aktiv"/"Active"
--     element, reused as-is: no fork needed, the standard wording applies unchanged here).
--     The only editable field on this tab (IsReadOnly='N'): deactivating a row here makes it
--     inactive, and get_epcis_events_json_fn's ledger-exclusion only matches active rows
--     (t.isactive='Y'), so that physical SSCC becomes eligible for re-transmission again.
--     Placed first (SeqNo/SeqNoGrid=5) as the tab's one actionable column.
-- 2026-07-13T09:02:10Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592929, 781419 /*From ID Server*/, 0, 549333,
     TO_TIMESTAMP('2026-07-13 09:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Der Eintrag ist im System aktiv', 1, 'de.metas.esb.edi',
     'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.', 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'N' /*editable — the one action support may take on this tab*/,
     'N', 'Aktiv', 5, 5,
     0, 1, 1,
     TO_TIMESTAMP('2026-07-13 09:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781419
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781419;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781419);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-13 09:02:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781419 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781419, 0, 549333, 555487, 652535 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-13 09:02:12','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktiv', 5, 5, 0, TO_TIMESTAMP('2026-07-13 09:02:12','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.1 SSCC18 (AD_Column 592934, AD_Element 585084)
-- 2026-07-13T09:03:00Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592934, 781376 /*From ID Server*/, 0, 549333,
     TO_TIMESTAMP('2026-07-13 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 30, 'de.metas.esb.edi',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y' /*read-only — diagnostic ledger*/,
     'N', 'SSCC18', 10, 10,
     0, 1, 1,
     TO_TIMESTAMP('2026-07-13 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781376
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585084);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781376;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781376);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-13 09:03:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781376 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781376, 0, 549333, 555487, 652492 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-13 09:03:02','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'SSCC18', 10, 10, 0, TO_TIMESTAMP('2026-07-13 09:03:02','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.2 ExternalSystem_Config_ScriptedExportConversion_ID (AD_Column 592935)
--     The shared column element 584101 has a raw technical name ("ExternalSystem_Config_..."),
--     wrong for this tab's label. Per the AD-element rule ("mutate shared vs. fork dedicated"),
--     a shared element must never be overridden via a raw AD_Field_Trl.Name UPDATE — any LATER
--     call to update_FieldTranslation_From_AD_Name_Element(584101) for some other field bound to
--     the same column-element re-syncs ALL fields bound to it, silently reverting a raw override
--     (this bit the sibling field precedent that used that fragile pattern). Instead, fork a
--     dedicated AD_Element (585087) for this field's label and bind it via AD_Field.AD_Name_ID —
--     durable, and scoped only to this field.
-- 2026-07-13T09:03:03Z
INSERT INTO AD_Element
    (AD_Client_ID, AD_Element_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, EntityType, Name, PrintName)
VALUES
    (0, 585087 /*From ID Server*/, 0, 'Y',
     TO_TIMESTAMP('2026-07-13 09:03:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-13 09:03:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'EPCIS_SSCC_Exportkonfiguration_Label', 'de.metas.esb.edi',
     'Exportkonfiguration', 'Exportkonfiguration')
;
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585087
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name = 'Export Configuration', PrintName = 'Export Configuration', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-13 09:03:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585087;
UPDATE AD_Element_Trl SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-13 09:03:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585087;

INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592935, 781377 /*From ID Server*/, 585087 /*dedicated label element*/, 0, 549333,
     TO_TIMESTAMP('2026-07-13 09:03:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 10, 'de.metas.esb.edi',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Exportkonfiguration', 20, 20,
     0, 1, 1,
     TO_TIMESTAMP('2026-07-13 09:03:06', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781377
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
-- Propagate from the DEDICATED element (585087), not from the shared column element (584101) —
-- this only ever touches fields whose AD_Name_ID=585087, i.e. just this one.
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585087);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781377;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781377);
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781377, 0, 549333, 555487, 652493 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-13 09:03:07','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Exportkonfiguration', 20, 20, 0, TO_TIMESTAMP('2026-07-13 09:03:07','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.3 Transmitted (AD_Column 592937, AD_Element 585085)
--     SortNo=-1: grid sort indicator matches OrderByClause='Transmitted DESC' on the tab
-- 2026-07-13T09:03:07Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592937, 781378 /*From ID Server*/, 0, 549333,
     TO_TIMESTAMP('2026-07-13 09:03:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 29, 'de.metas.esb.edi',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Übertragen am', 30, 30,
     -1 /*DESC sort indicator — matches OrderByClause='Transmitted DESC' on the tab*/, 1, 1,
     TO_TIMESTAMP('2026-07-13 09:03:07', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781378
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585085);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781378;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781378);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-13 09:03:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781378 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781378, 0, 549333, 555487, 652494 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-13 09:03:09','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Übertragen am', 30, 30, 0, TO_TIMESTAMP('2026-07-13 09:03:09','YYYY-MM-DD HH24:MI:SS'), 100);

-- 4.4 Updated (AD_Column 592932, AD_Element 607) — shown last, mirroring the sibling tab
-- 2026-07-13T09:03:10Z
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592932, 781379 /*From ID Server*/, 0, 549333,
     TO_TIMESTAMP('2026-07-13 09:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     NULL, 29, 'de.metas.esb.edi',
     NULL, 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'Y',
     'N', 'Aktualisiert', 40, 40,
     0, 1, 1,
     TO_TIMESTAMP('2026-07-13 09:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781379
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(607);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781379;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781379);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-13 09:03:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781379 AND AD_Language IN ('de_DE','de_CH','en_US');
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781379, 0, 549333, 555487, 652495 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-13 09:03:12','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktualisiert', 40, 40, 0, TO_TIMESTAMP('2026-07-13 09:03:12','YYYY-MM-DD HH24:MI:SS'), 100);

-- ===========================================================================
-- PART 5: Backfill any missing translations
-- ===========================================================================
-- 2026-07-13T09:04:00Z
SELECT add_missing_translations();
