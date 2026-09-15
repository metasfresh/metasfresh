-- Tax Declaration: drop the legacy DateFrom / DateTo / DateTrx columns from
-- C_TaxDeclaration. They were superseded by C_Period_ID + DateAcct in the same
-- iteration; previously kept around hidden and nullable for backward-compat — now
-- gone entirely. Also makes C_Period_ID physically NOT NULL so cucumber + any direct
-- SQL insert cannot bypass the framework's mandatory check.

-- 1. Drop AD_UI_Element rows for the 3 columns
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID IN (651167, 651168, 651169);

-- 2. Drop AD_Field rows (and their translations)
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (779183, 779184, 779185);
DELETE FROM AD_Field     WHERE AD_Field_ID IN (779183, 779184, 779185);

-- 3. Drop AD_Column rows (and their translations)
DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (14461, 14462, 14463);
DELETE FROM AD_Column     WHERE AD_Column_ID IN (14461, 14462, 14463);

-- 4. Drop physical columns
ALTER TABLE C_TaxDeclaration DROP COLUMN IF EXISTS DateFrom;
ALTER TABLE C_TaxDeclaration DROP COLUMN IF EXISTS DateTo;
ALTER TABLE C_TaxDeclaration DROP COLUMN IF EXISTS DateTrx;

-- 5. C_Period_ID is mandatory (already IsMandatory='Y' in AD_Column); enforce in PG too.
-- Any row predating the change must already have a Period set; if not, the SET NOT NULL fails
-- here — that's the right time to notice and fix bad data.
ALTER TABLE C_TaxDeclaration ALTER COLUMN C_Period_ID SET NOT NULL;
