-- Adds M_HU_PI_Version.PackageDimensionCalcMethod column and exposes it in the
-- Packvorschrift Version windows (540344 current, 540188 legacy).
-- The field only shows when HU_UnitType = TU (DisplayLogic @HU_UnitType@=TU).
--
-- IDs allocated from idserver.metas.de on 2026-07-22:
--   AD_Element    585123 /*From ID Server*/  (PackageDimensionCalcMethod)
--   AD_Column     592977 /*From ID Server*/  (M_HU_PI_Version.PackageDimensionCalcMethod)
--   AD_Field      781766 /*From ID Server*/  (tab 540823, win 540344)
--   AD_Field      781767 /*From ID Server*/  (tab 540505, win 540188)
--   AD_UI_Element 652698 /*From ID Server*/  (tab 540823, win 540344)
-- Migration prefix: 5815420

-- ============================================================
-- 1. AD_Element
-- ============================================================
INSERT INTO AD_Element
  (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   ColumnName, Name, PrintName, Description, EntityType)
VALUES
  (585123 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   'PackageDimensionCalcMethod', 'Berechnungsmethode', 'Berechnungsmethode',
   'Berechnungsmethode für die Verpackungsmaße einer Transporteinheit, wenn sie mehrere Artikel enthält (Bändern / Umverpacken / Verschachteln).', 'D');

-- AD_Element_Trl skeleton for all active system languages
INSERT INTO AD_Element_Trl
  (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
   IsTranslated, AD_Client_ID, AD_Org_ID,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
  'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 585123
  AND NOT EXISTS (
    SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID
  );

-- Mark de_DE and de_CH as translated (German is the base name)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Berechnungsmethode',
    PrintName    = 'Berechnungsmethode',
    Description  = 'Berechnungsmethode für die Verpackungsmaße einer Transporteinheit, wenn sie mehrere Artikel enthält (Bändern / Umverpacken / Verschachteln).',
    Updated      = TO_TIMESTAMP('2026-07-22 14:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585123
  AND AD_Language IN ('de_DE', 'de_CH');

-- Override en_US to English
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Name         = 'Calc Method',
    PrintName    = 'Calc Method',
    Description  = 'Calculation method for a Transport Unit''s package dimensions when it holds multiple items (Strapping / Repacking / Nesting).',
    Updated      = TO_TIMESTAMP('2026-07-22 14:00:18', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585123
  AND AD_Language = 'en_US';

-- ============================================================
-- 2. AD_Column
-- ============================================================
INSERT INTO AD_Column
  (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
   ColumnName, Name, FieldLength, IsKey, IsParent, IsMandatory,
   IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
   IsDimension, IsForceIncludeInGeneratedModel,
   Version, EntityType, PersonalDataCategory)
VALUES
  (592977 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   540510, 585123, 17, 542122,
   'PackageDimensionCalcMethod', 'Berechnungsmethode', 1, 'N', 'N', 'N',
   'N', 'N', 'N', 'Y', 'N',
   'N', 'N',
   0, 'D', 'NP');

-- AD_Column_Trl skeleton for all active system languages
INSERT INTO AD_Column_Trl
  (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Column_ID = 592977
  AND NOT EXISTS (
    SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID
  );

-- ============================================================
-- 3. Physical DDL — add column to M_HU_PI_Version
-- ============================================================
-- New nullable column (no default). Routed through db_alter_table() so the function manages
-- view dependencies (the bare-ALTER exception is only for NOT NULL DEFAULT backfill columns).
SELECT public.db_alter_table('M_HU_PI_Version','ALTER TABLE public.M_HU_PI_Version ADD COLUMN IF NOT EXISTS PackageDimensionCalcMethod CHAR(1)');

-- ============================================================
-- 4. AD_Field — tab 540823 (window 540344, standalone Packvorschrift Version)
-- ============================================================
INSERT INTO AD_Field
  (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_Tab_ID, AD_Column_ID, AD_Name_ID,
   Name, Description, Help,
   IsDisplayed, IsDisplayedGrid, IsSameLine, IsHeading, IsFieldOnly,
   IsReadOnly, IsMandatory, IsEncrypted,
   SeqNo, SeqNoGrid,
   DisplayLogic, EntityType)
VALUES
  (781766 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   540823, 592977, 585123,
   'Berechnungsmethode', NULL, NULL,
   'Y', 'Y', 'N', 'N', 'N',
   'N', 'N', 'N',
   80, 70,
   '@HU_UnitType@=TU', 'D');

-- AD_Field_Trl skeleton
INSERT INTO AD_Field_Trl
  (AD_Language, AD_Field_ID, Name, Description, Help,
   IsTranslated, AD_Client_ID, AD_Org_ID,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help,
  'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 781766
  AND NOT EXISTS (
    SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID
  );

-- Propagate element translations → field (element ID, not field ID)
SELECT update_FieldTranslation_From_AD_Name_Element(585123);

-- Rebuild element links for this field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781766;
SELECT AD_Element_Link_Create_Missing_Field(781766);

-- ============================================================
-- 5. AD_UI_Element — tab 540823, group 540439 (description)
--    placed after Verpackungscode (SeqNo 20) and before/at Beschreibung (SeqNo 30)
--    → SeqNo 25 keeps it between HU Typ area and Beschreibung, grid SeqNoGrid 75
-- ============================================================
INSERT INTO AD_UI_Element
  (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_UI_ElementGroup_ID, AD_Tab_ID, AD_Field_ID, Name,
   AD_UI_ElementType, SeqNo, SeqNoGrid,
   IsDisplayed, IsDisplayedGrid, IsAdvancedField)
VALUES
  (652698 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 14:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 14:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
   540439, 540823, 781766, 'Berechnungsmethode',
   'F', 25, 75,
   'Y', 'Y', 'N');

-- ============================================================
-- 6. AD_Field — tab 540505 (window 540188, legacy Packvorschrift Version)
--    Tab 540505 has no AD_UI_Element rows — field only, no UI_Element needed.
-- ============================================================
INSERT INTO AD_Field
  (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_Tab_ID, AD_Column_ID, AD_Name_ID,
   Name, Description, Help,
   IsDisplayed, IsDisplayedGrid, IsSameLine, IsHeading, IsFieldOnly,
   IsReadOnly, IsMandatory, IsEncrypted,
   SeqNo, SeqNoGrid,
   DisplayLogic, EntityType)
VALUES
  (781767 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-07-22 14:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-07-22 14:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
   540505, 592977, 585123,
   'Berechnungsmethode', NULL, NULL,
   'Y', 'Y', 'N', 'N', 'N',
   'N', 'N', 'N',
   80, 70,
   '@HU_UnitType@=TU', 'D');

-- AD_Field_Trl skeleton for legacy field
INSERT INTO AD_Field_Trl
  (AD_Language, AD_Field_ID, Name, Description, Help,
   IsTranslated, AD_Client_ID, AD_Org_ID,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help,
  'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Field_ID = 781767
  AND NOT EXISTS (
    SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID
  );

-- Propagate element translations → legacy field
SELECT update_FieldTranslation_From_AD_Name_Element(585123);

-- Rebuild element links for the legacy field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781767;
SELECT AD_Element_Link_Create_Missing_Field(781767);

-- ============================================================
-- 7. Final translation cascade from element
-- ============================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585123);
