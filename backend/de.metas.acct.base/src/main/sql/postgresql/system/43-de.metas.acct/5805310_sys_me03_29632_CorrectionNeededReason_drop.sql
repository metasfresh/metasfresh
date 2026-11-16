-- Tax Declaration — drop CorrectionNeededReason column (descoped in Iter 8)
--
-- Column was added in Iter 7 (migration 5804440) but is not used by the drift-check process.
-- Dropping it now to avoid managing i18n reason text.
--
-- Known IDs from Iter 7 migration 5804440:
--   AD_Element_ID    = 584911
--   AD_Column_ID     = 592619
--   AD_Field_ID      = 780482
--   AD_UI_Element_ID = 651837

-- Backup before destructive change (REVIEW.md rule: backup_table before DROP COLUMN)
SELECT backup_table('c_taxdeclaration', '_iter8_drop_correctionneededreason');

-- 1. Drop physical column (wrapped to handle dependent views automatically)
SELECT public.db_alter_table('C_TaxDeclaration', 'ALTER TABLE C_TaxDeclaration DROP COLUMN IF EXISTS CorrectionNeededReason');

-- 2. Deactivate AD_Column (also clears IsForceIncludeInGeneratedModel)
UPDATE AD_Column
SET IsActive = 'N',
    IsForceIncludeInGeneratedModel = 'N',
    Updated = TIMESTAMP '2026-05-29 10:00:00',
    UpdatedBy = 100
WHERE AD_Column_ID = 592619;

-- 3. Deactivate AD_Field
UPDATE AD_Field
SET IsActive = 'N',
    IsDisplayed = 'N',
    IsDisplayedGrid = 'N',
    Updated = TIMESTAMP '2026-05-29 10:00:01',
    UpdatedBy = 100
WHERE AD_Field_ID = 780482;

-- 4. Deactivate AD_UI_Element
UPDATE AD_UI_Element
SET IsDisplayed = 'N',
    IsDisplayedGrid = 'N',
    Updated = TIMESTAMP '2026-05-29 10:00:02',
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 651837;

-- 5. Deactivate AD_Element + AD_Element_Trl (dedicated element, safe to deactivate)
UPDATE AD_Element
SET IsActive = 'N', Updated = TIMESTAMP '2026-05-29 10:00:03', UpdatedBy = 100
WHERE AD_Element_ID = 584911;

UPDATE AD_Element_Trl
SET IsActive = 'N', Updated = TIMESTAMP '2026-05-29 10:00:04', UpdatedBy = 100
WHERE AD_Element_ID = 584911;
