-- Add M_DiscountSchema.ValidTo field (optional date, end of discount validity window)
-- IDs allocated from idserver.metas.de on 2026-09-13:
--   AD_Column  593552 (M_DiscountSchema.ValidTo)
--   AD_Field   784974 (field on window 233 Rabatt-Schema)

-- ============================================================================
-- Step 1: Add physical column to M_DiscountSchema table
-- ============================================================================
SELECT db_alter_table('M_DiscountSchema', 'ALTER TABLE m_discountschema ADD COLUMN IF NOT EXISTS validto TIMESTAMP');

-- ============================================================================
-- Step 2: Insert AD_Column (reusing existing shared ValidTo AD_Element_ID=618 "Gültig bis")
-- ============================================================================
INSERT INTO AD_Column (
  AD_Column_ID, AD_Client_ID, AD_Org_ID, AD_Table_ID, ColumnName, AD_Reference_ID,
  AD_Element_ID, FieldLength, IsKey, IsParent, IsMandatory,
  IsUpdateable, IsIdentifier, SeqNo, IsTranslated, IsSelectionColumn, IsRangeFilter,
  IsActive, Created, Updated, CreatedBy, UpdatedBy, EntityType, Version
) VALUES (
  593552, 0, 0, 475, 'ValidTo', 15,
  618, 7, 'N', 'N', 'N', 'Y', 'N', 0, 'N', 'N', 'N',
  'Y', NOW(), NOW(), 100, 100, 'D', 1
) /*From ID Server*/;

-- ============================================================================
-- Step 3: Insert AD_Field on window 233 (Rabatt-Schema), placed at SeqNo=1 (after ValidFrom at SeqNo=0)
-- ============================================================================
INSERT INTO AD_Field (
  AD_Field_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Column_ID, Name,
  IsDisplayed, DisplayLength, IsReadOnly, SeqNo, IsSameLine, IsHeading, IsFieldOnly,
  EntityType, IsActive, Created, Updated, CreatedBy, UpdatedBy
) VALUES (
  784974, 0, 0, 404, 593552, 'Gültig bis',
  'Y', 0, 'N', 1, 'N', 'N', 'N',
  'D', 'Y', NOW(), NOW(), 100, 100
) /*From ID Server*/;

-- ============================================================================
-- Step 4: Insert skeleton AD_Field_Trl rows for all active system languages
-- ============================================================================
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, f.AD_Field_ID, f.Name, 'N', f.AD_Client_ID, f.AD_Org_ID, NOW(), 100, NOW(), 100, 'Y'
FROM AD_Language l
CROSS JOIN AD_Field f
WHERE l.IsActive='Y'
  AND l.IsSystemLanguage='Y'
  AND f.AD_Field_ID=784974
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl ft WHERE ft.AD_Language=l.AD_Language AND ft.AD_Field_ID=f.AD_Field_ID)
;

-- ============================================================================
-- Step 5: Propagate element translations from AD_Element to AD_Field
-- ============================================================================
SELECT update_FieldTranslation_From_AD_Name_Element(618);

-- ============================================================================
-- Step 6: Rebuild element links
-- ============================================================================
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784974;
SELECT AD_Element_Link_Create_Missing_Field(784974);
