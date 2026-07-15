-- Add S_Resource.LotNumberCode column (String, length 10, optional)
-- A per-resource code consumed by custom lot-number sequence providers (e.g. to embed a
-- production-line code in a generated lot number). Not unique, not mandatory.

-- ===========================================================
-- 1. AD_Element
-- ===========================================================
INSERT INTO AD_Element
(AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
 ColumnName, Name, PrintName, EntityType)
SELECT
 585043 /*From ID Server*/,
 0, 0, 'Y',
 TO_TIMESTAMP('2026-06-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
 100,
 TO_TIMESTAMP('2026-06-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
 100,
 'LotNumberCode',
 'Lot-Nummer Code',
 'Lot-Nummer Code',
 'D'
WHERE NOT EXISTS (SELECT 1 FROM AD_Element WHERE AD_Element_ID=585043)
;

-- ===========================================================
-- 2a. Seed AD_Element_Trl skeleton rows for all active system languages
-- ===========================================================
INSERT INTO AD_Element_Trl
(AD_Language, AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
 Name, PrintName, IsTranslated)
SELECT
 l.AD_Language, t.AD_Element_ID, t.AD_Client_ID, t.AD_Org_ID, 'Y',
 TO_TIMESTAMP('2026-06-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
 100,
 TO_TIMESTAMP('2026-06-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
 100,
 t.Name, t.PrintName, 'N'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Element_ID=585043
  AND NOT EXISTS (
    SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID
  )
;

-- ===========================================================
-- 2b. Mark de_DE and de_CH as translated (same text as base)
-- ===========================================================
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-22 10:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID=585043 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-22 10:00:13', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID=585043 AND AD_Language='de_CH'
;

-- ===========================================================
-- 2c. Override en_US with English translation
-- ===========================================================
UPDATE AD_Element_Trl
SET Name='Lot Number Code',
    PrintName='Lot Number Code',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-22 10:00:18', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID=585043 AND AD_Language='en_US'
;

-- ===========================================================
-- 3. AD_Column
-- ===========================================================
INSERT INTO AD_Column
(AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
 AD_Table_ID, AD_Element_ID, AD_Reference_ID,
 ColumnName, Name, FieldLength, IsMandatory, IsKey, IsParent, IsTranslated,
 IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
 IsSelectionColumn, IsSyncDatabase,
 PersonalDataCategory, Version, EntityType)
SELECT
 592876 /*From ID Server*/,
 0, 0, 'Y',
 TO_TIMESTAMP('2026-06-22 10:01:00', 'YYYY-MM-DD HH24:MI:SS'),
 100,
 TO_TIMESTAMP('2026-06-22 10:01:00', 'YYYY-MM-DD HH24:MI:SS'),
 100,
 487,          -- AD_Table_ID for S_Resource
 585043,       -- AD_Element_ID
 10,           -- String reference
 'LotNumberCode',
 'Lot-Nummer Code',
 10,           -- FieldLength
 'N',          -- IsMandatory
 'N', 'N', 'N',
 'N', 'N', 'Y', 'N',
 'N', 'N',
 'NP',         -- PersonalDataCategory: Not Personal
 0,            -- Version
 'D'           -- EntityType: core dictionary
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Column_ID=592876)
;

-- ===========================================================
-- 4a. Seed AD_Column_Trl skeleton rows
-- ===========================================================
INSERT INTO AD_Column_Trl
(AD_Language, AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
 Name, IsTranslated)
SELECT
 l.AD_Language, t.AD_Column_ID, t.AD_Client_ID, t.AD_Org_ID, 'Y',
 TO_TIMESTAMP('2026-06-22 10:01:00', 'YYYY-MM-DD HH24:MI:SS'),
 100,
 TO_TIMESTAMP('2026-06-22 10:01:00', 'YYYY-MM-DD HH24:MI:SS'),
 100,
 t.Name, 'N'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Column_ID=592876
  AND NOT EXISTS (
    SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID
  )
;

-- ===========================================================
-- 4b. Propagate element translations into the column
-- ===========================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585043);

-- ===========================================================
-- 5. Add the physical column
-- ===========================================================
ALTER TABLE S_Resource ADD COLUMN IF NOT EXISTS LotNumberCode VARCHAR(10);
