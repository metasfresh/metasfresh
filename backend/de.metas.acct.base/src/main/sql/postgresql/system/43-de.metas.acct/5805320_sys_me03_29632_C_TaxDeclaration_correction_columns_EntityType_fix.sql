-- Tax Declaration — fix EntityType on correction columns to match table EntityType
--
-- The 3 correction columns (IsCorrection, C_TaxDeclaration_Original_ID, IsCorrectionNeeded)
-- were added in Iter 7 with EntityType='de.metas.acct'. The C_TaxDeclaration table itself
-- has EntityType='D'. GenerateModel's OnlySystemColumns mode only includes columns whose
-- EntityType is in SYSTEM_MAINTAINED_ENTITY_TYPES, which includes 'D' but not 'de.metas.acct'.
-- Migration 5805260 worked around this with IsForceIncludeInGeneratedModel='Y'; this migration
-- fixes the root cause by aligning the column EntityType with the table EntityType.
--
-- Known IDs (from migration 5804440):
--   AD_Column_ID 592616 = IsCorrection
--   AD_Column_ID 592617 = C_TaxDeclaration_Original_ID
--   AD_Column_ID 592618 = IsCorrectionNeeded
-- https://github.com/metasfresh/me03/issues/29632

UPDATE AD_Column
SET EntityType = 'D',
    IsForceIncludeInGeneratedModel = 'N',
    Updated = now(),
    UpdatedBy = 100
WHERE AD_Column_ID IN (592616, 592617, 592618)
  AND IsActive = 'Y';
