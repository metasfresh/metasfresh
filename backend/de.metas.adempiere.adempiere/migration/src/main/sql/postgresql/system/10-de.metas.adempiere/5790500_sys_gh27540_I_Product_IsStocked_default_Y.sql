-- gh#27540: Product Import - change IsStocked default from 'N' to 'Y'
-- So that imported items are stocked by default (which is the expected behavior in nearly all cases)

-- Update the AD_Column default value
UPDATE AD_Column
SET DefaultValue = 'Y',
    Updated      = now(),
    UpdatedBy    = 99
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'I_Product')
  AND ColumnName = 'IsStocked'
  AND DefaultValue = 'N';

-- Change the actual DB column default
ALTER TABLE I_Product ALTER COLUMN IsStocked SET DEFAULT 'Y';
