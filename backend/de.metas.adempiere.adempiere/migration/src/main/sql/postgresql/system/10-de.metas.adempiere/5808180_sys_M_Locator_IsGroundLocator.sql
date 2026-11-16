-- M_Locator — add IsGroundLocator boolean flag + Locator tab field (AD_Window 139 / AD_Tab 178).
-- Indicates the locator is on the ground floor (Erdgeschoss), relevant for put-away routing
-- and warehouse-operation optimizations.
--
-- IDs allocated from idserver.metas.de on 2026-06-16:
--   AD_MigrationScript  5808180 (this script)
--   AD_Element          585005  (IsGroundLocator)
--   AD_Column           592814  (M_Locator.IsGroundLocator)
--   AD_Field            781157  (Locator tab field, AD_Tab 178)
--   AD_UI_Element       652303  (UI placement in default group 541165)

-- =============================================================================
-- 1. AD_Element — German base text; English via _Trl
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName)
VALUES (585005 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-16 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-16 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'IsGroundLocator', 'D', 'Erdgeschoss-Lagerort', 'Erdgeschoss-Lagerort');

-- Skeleton Trl rows (all active system languages; copy base German text, IsTranslated='N')
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585005
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- de_DE translation (same as base — mark as translated)
UPDATE AD_Element_Trl
   SET Name = 'Erdgeschoss-Lagerort', PrintName = 'Erdgeschoss-Lagerort', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-16 08:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585005 AND AD_Language = 'de_DE';

-- de_CH translation (Swiss: same text, no ß issue here)
UPDATE AD_Element_Trl
   SET Name = 'Erdgeschoss-Lagerort', PrintName = 'Erdgeschoss-Lagerort', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-16 08:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585005 AND AD_Language = 'de_CH';

-- en_US translation
UPDATE AD_Element_Trl
   SET Name = 'Ground Floor Locator', PrintName = 'Ground Floor Locator', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-16 08:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 585005 AND AD_Language = 'en_US';

-- =============================================================================
-- 2. AD_Column (M_Locator — AD_Table_ID 207)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       IsExcludeFromZoomTargets, CloningStrategy,
                       PersonalDataCategory)
VALUES (592814 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-16 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-16 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0,
        207 /*M_Locator*/,
        585005 /*AD_Element IsGroundLocator*/,
        20 /*Yes-No*/,
        'IsGroundLocator', 'Erdgeschoss-Lagerort',
        1, 'Y', 'Y', 'N',
        'N', 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'Y', 'XX',
        'NP');

-- Skeleton AD_Column_Trl rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 592814
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 3. Physical DDL — mandatory boolean, default false, NOT NULL
-- =============================================================================
SELECT public.db_alter_table('M_Locator', 'ALTER TABLE public.M_Locator ADD COLUMN IF NOT EXISTS IsGroundLocator CHAR(1) DEFAULT ''N'' CHECK (IsGroundLocator IN (''Y'',''N'')) NOT NULL');

-- Sync AD_Column.DefaultValue
UPDATE AD_Column
   SET DefaultValue = 'N',
       Updated = TO_TIMESTAMP('2026-06-16 08:01:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Column_ID = 592814;

-- =============================================================================
-- 4. Propagate element translations → AD_Column_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585005 /*IsGroundLocator*/);

-- =============================================================================
-- 5. AD_Field — Locator tab (AD_Tab 178, AD_Window 139)
--    Placed after IsAfterPickingLocator (seqno=120) at seqno=125, seqnogrid=115
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID,
                      Name, EntityType,
                      IsDisplayed, DisplayLength,
                      IsSameLine, IsHeading, IsFieldOnly, IsEncrypted,
                      SeqNo, SeqNoGrid,
                      IsMandatory, IsReadOnly,
                      AD_Reference_ID)
VALUES (781157 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-16 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-16 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        178 /*AD_Tab Lagerort*/,
        592814 /*M_Locator.IsGroundLocator*/,
        'Erdgeschoss-Lagerort', 'D',
        'Y', 1,
        'N', 'N', 'N', 'N',
        125, 115,
        'N', 'N',
        20 /*Yes-No*/);

-- Skeleton AD_Field_Trl rows
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 781157
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Propagate element translations → AD_Field_Trl
-- (pass AD_Element_ID, not AD_Field_ID — the function resolves via AD_Column.AD_Element_ID)
SELECT update_FieldTranslation_From_AD_Name_Element(585005 /*IsGroundLocator element*/);

-- Rebuild element links for the new field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781157;
SELECT AD_Element_Link_Create_Missing_Field(781157);

-- =============================================================================
-- 6. AD_UI_Element — place in default element group (541165) of tab 178
--    After IsAfterPickingLocator (UI seqno=90); use seqno=95, seqnogrid=95
-- =============================================================================
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID,
                           Name, AD_UI_ElementType,
                           SeqNo, SeqNoGrid,
                           IsDisplayed, IsDisplayedGrid,
                           IsAdvancedField)
VALUES (652303 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-16 08:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-16 08:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        178 /*AD_Tab Lagerort — denormalized, must match element-group section tab*/,
        541165 /*default group of tab 178*/,
        781157 /*AD_Field IsGroundLocator*/,
        'Erdgeschoss-Lagerort', 'F',
        95, 95,
        'Y', 'Y',
        'N');
