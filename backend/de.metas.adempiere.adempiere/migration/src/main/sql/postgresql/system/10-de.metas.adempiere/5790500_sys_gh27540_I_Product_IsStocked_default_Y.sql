-- gh#27540: Make I_Product.IsStocked nullable so NULL means "not explicitly provided"
-- The import process will derive IsStocked from ProductType:
--   Item (I) -> Y (stocked), all others (S, E, R) -> N (not stocked)
-- Explicit values (Y/N) from the import file are preserved.
--
-- We keep AD_Reference_ID=20 (YesNo) so the generated setter setIsStocked(boolean)
-- continues to work. Only make the column non-mandatory and nullable.

-- Step 1: Make non-mandatory, remove default (keep YesNo type)
UPDATE AD_Column
SET IsMandatory        = 'N',
    DefaultValue       = NULL,
    Updated            = now(),
    UpdatedBy          = 99
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'I_Product')
  AND ColumnName = 'IsStocked';

-- Step 2: Make the DB column nullable and remove the default
ALTER TABLE I_Product ALTER COLUMN IsStocked DROP NOT NULL;
ALTER TABLE I_Product ALTER COLUMN IsStocked DROP DEFAULT;
