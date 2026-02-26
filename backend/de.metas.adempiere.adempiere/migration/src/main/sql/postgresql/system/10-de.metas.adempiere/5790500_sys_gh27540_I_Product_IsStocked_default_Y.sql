-- gh#27540: Make I_Product.IsStocked nullable so NULL means "not explicitly provided"
-- The import process will derive IsStocked from ProductType:
--   Item (I) -> Y (stocked), all others (S, E, R) -> N (not stocked)
-- Explicit values (Y/N) from the import file are preserved.

-- Step 1: Change AD_Column from YesNo (AD_Reference_ID=20) to List (AD_Reference_ID=17)
-- with _YesNo reference (AD_Reference_Value_ID=319), make non-mandatory, remove default
UPDATE AD_Column
SET AD_Reference_ID    = 17,  -- List
    AD_Reference_Value_ID = 319, -- _YesNo
    IsMandatory        = 'N',
    DefaultValue       = NULL,
    Updated            = now(),
    UpdatedBy          = 99
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'I_Product')
  AND ColumnName = 'IsStocked';

-- Step 2: Make the DB column nullable and remove the default
ALTER TABLE I_Product ALTER COLUMN IsStocked DROP NOT NULL;
ALTER TABLE I_Product ALTER COLUMN IsStocked DROP DEFAULT;
