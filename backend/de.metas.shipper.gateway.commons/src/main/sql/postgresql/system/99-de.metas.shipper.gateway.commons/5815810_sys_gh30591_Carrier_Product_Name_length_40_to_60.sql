-- Increase Carrier_Product.Name from VARCHAR(40) to VARCHAR(60).
-- Real DHL carrier-product names exceed 40 characters.
-- AD_Column_ID=591351 (table Carrier_Product, columnname Name).
-- AD_Element_ID=469 (shared generic Name element) is NOT modified.

-- 1. Widen the physical column via t_alter_column (handles view dependencies automatically)
INSERT INTO t_alter_column VALUES('Carrier_Product','Name','VARCHAR(60)',null,null);

-- 2. Update AD_Column FieldLength to match physical reality
UPDATE AD_Column
SET    FieldLength  = 60,
       Updated      = TO_TIMESTAMP('2026-07-23 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Column_ID = 591351;
