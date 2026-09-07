-- C_Workplace — add IsPackingPlace boolean column and WebUI window field.
-- Marks a workplace as a packing place (used for packing operations).
-- Default 'Y' — all existing workplaces become packing places on migration.
--
-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_MigrationScript  5807860  (this script)
--   AD_Element          584993   (IsPackingPlace)
--   AD_Column           592810   (C_Workplace.IsPackingPlace)
--   AD_Field            781119   (Arbeitsplatz tab, window 541744)
--   AD_UI_Element       652265   (flags group 551258)

-- =============================================================================
-- 1. AD_Element — German base text; en_US via Trl
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName)
VALUES (584993 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-15 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-15 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'IsPackingPlace', 'D', 'Packplatz', 'Packplatz');

-- Skeleton Trl rows (all active system languages; copy base DE, IsTranslated='N')
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Element_ID = 584993
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation (en_US — strictly later timestamp than element INSERT)
UPDATE AD_Element_Trl
   SET Name = 'Is Packing Place', PrintName = 'Is Packing Place', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-15 14:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584993 AND AD_Language = 'en_US';

-- de_DE / de_CH — same text as base, mark as translated
UPDATE AD_Element_Trl
   SET IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-15 14:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Element_ID = 584993 AND AD_Language IN ('de_DE', 'de_CH');

-- =============================================================================
-- 2. AD_Column — C_Workplace.IsPackingPlace
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       IsExcludeFromZoomTargets, CloningStrategy,
                       PersonalDataCategory)
VALUES (592810 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-15 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-15 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0,
        (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Workplace'),
        (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'IsPackingPlace'),
        20 /*Yes-No*/,
        'IsPackingPlace', 'Packplatz',
        1, 'Y', 'Y', 'N',
        'Y', 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'Y', 'XX',
        'NP');

-- Skeleton Column Trl rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592810
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 3. Physical DDL — add column with DEFAULT 'Y', backfill, then enforce NOT NULL
-- =============================================================================
SELECT public.db_alter_table('C_Workplace', 'ALTER TABLE public.C_Workplace ADD COLUMN IF NOT EXISTS IsPackingPlace CHAR(1) DEFAULT ''Y'' CHECK (IsPackingPlace IN (''Y'',''N'')) NOT NULL');

-- =============================================================================
-- 4. AD_Field — place on main tab (547260) of C_Workplace window (541744)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, DisplayLength,
                      SeqNo, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly, IsReadOnly,
                      IsMandatory, IsEncrypted, EntityType)
VALUES (781119 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-15 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-15 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Packplatz',
        547260 /*tab: Arbeitsplatz*/,
        (SELECT AD_Column_ID FROM AD_Column WHERE ColumnName = 'IsPackingPlace' AND AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Workplace')),
        'Y', 1,
        0, 55,  -- SeqNo=0 (form governed by AD_UI_Element); SeqNoGrid=55 (before Sektion/AD_Org_ID at 60)
        'N', 'N', 'N', 'N',
        'N', 'N', 'D');

-- Skeleton Field Trl rows
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 781119
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Propagate AD_Element_Trl → AD_Field_Trl (pass ELEMENT ID, not field ID)
SELECT update_FieldTranslation_From_AD_Name_Element(584993 /*IsPackingPlace, From ID Server*/);

-- Rebuild element links for this field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781119;
SELECT AD_Element_Link_Create_Missing_Field(781119);

-- =============================================================================
-- 5. AD_UI_Element — place in flags group (551258) on tab 547260
--    IsActive is at SeqNo=10, IsPackingPlace goes at SeqNo=20
--    Grid: SeqNoGrid=55 (Aktiv=50, this=55, Sektion/AD_Org_ID=60)
-- =============================================================================
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_Field_ID, AD_UI_ElementGroup_ID,
                           AD_UI_ElementType, Name,
                           SeqNo, SeqNoGrid, SeqNo_SideList,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField)
VALUES (652265 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-15 14:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-15 14:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        547260 /*tab: Arbeitsplatz*/,
        781119 /*AD_Field_ID*/,
        551258 /*group: flags*/,
        'F', 'Packplatz',
        20,  -- SeqNo in form: after IsActive (10)
        55,  -- SeqNoGrid: before AD_Org_ID (60)
        0,
        'Y', 'Y', 'N', 'N');

-- =============================================================================
-- 6. Propagate all translations from AD_Element_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584993 /*IsPackingPlace, From ID Server*/);
