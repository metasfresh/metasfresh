-- Contract compensation groups: settings window additions.
--
-- 1) Add an optional Description column + field to C_CompensationGroup_ContractSettings, reusing
--    the existing shared "Description" AD_Element (275, Text/2000, the module's own convention --
--    see C_Flatrate_Conditions.Description).
-- 2) C_CompensationGroup_ContractSettings_DocType.C_DocType_ID: switch from Table Direct to
--    Search (large reference set) -- keep the existing AD_Val_Rule 540803 ("C_DocType SOO/POO")
--    restricting the lookup to order document types; a val rule applies the same way to a Search
--    column as to a Table Direct one, so no further change is needed.
--
-- IDs allocated from idserver.metas.de on 2026-09-28:
--   AD_Column     593671  (C_CompensationGroup_ContractSettings.Description)
--   AD_Field      785591  (settings header tab 549507)
--   AD_UI_Element 654919  (settings header tab 549507, default/primary group 555794)
-- ============================================================================
-- 1) C_CompensationGroup_ContractSettings.Description (AD_Table_ID=542650)
--    Text, optional, reuses existing shared element 275 (Description)
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593671 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Description', 542650, 275, 14,
        2000, 'Beschreibung', 'Optionale Beschreibung',
        'N', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593671
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
-- Physical column (existing table -> ALTER TABLE ADD COLUMN via db_alter_table, per 5826630 precedent)
SELECT public.db_alter_table('C_CompensationGroup_ContractSettings', 'ALTER TABLE public.C_CompensationGroup_ContractSettings ADD COLUMN Description VARCHAR(2000)')
;
-- Sync the column's Name/translations from the shared "Description" element (standard wording --
-- this field carries no field-specific meaning beyond the generic element, unlike the schema line's applies-to category field, see 5826750)
SELECT update_Column_Translation_From_AD_Element(275)
;
-- AD_Field: Description on the settings header tab (549507), default/primary group (555794),
-- right after the schema field (Name=10, Schema=20)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, EntityType,
                      IsDisplayed, IsDisplayedGrid, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, IsReadOnly,
                      SeqNo, SeqNoGrid)
VALUES (785591 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        549507, 593671, 'Beschreibung', 'Optionale Beschreibung',
        'de.metas.contracts',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N',
        NULL, 0)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 785591, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 785591
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
SELECT update_FieldTranslation_From_AD_Name_Element(275)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785591;
SELECT AD_Element_Link_Create_Missing_Field(785591)
;
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_Tab_ID, SeqNo, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name)
VALUES (654919 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        555794, 785591, 549507, 30, 'Y', 'N', 'N', 'Beschreibung')
;
-- ============================================================================
-- 2) C_CompensationGroup_ContractSettings_DocType.C_DocType_ID: Table Direct -> Search
--    (AD_Val_Rule_ID=540803 already restricts it to order doc types; unchanged)
-- ============================================================================
UPDATE AD_Column
SET AD_Reference_ID = 30, Updated = TO_TIMESTAMP('2026-09-28 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593669
;
