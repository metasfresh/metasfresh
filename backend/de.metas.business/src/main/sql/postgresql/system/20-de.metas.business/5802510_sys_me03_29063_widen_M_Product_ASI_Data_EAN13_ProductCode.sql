-- me03#29063: widen M_Product_ASI_Data.EAN13_ProductCode from VARCHAR(4) to VARCHAR(50)
--
-- The column was originally introduced in 5795580_sys_M_Product_ASI_Data.sql with
-- FieldLength=4, which can hold only 4 characters. PR #23942 added a COALESCE fallback
-- routing this column into the EDI INVOIC/DESADV JSON's GTIN_CU field — but a 4-char
-- column cannot hold a real EAN-13 number (13 digits), making the fallback functionally
-- useless for the customer who requested it.
--
-- Widening to VARCHAR(50) to match M_Product_ASI_Data.GTIN's width. The wider form
-- accommodates EAN-13 (13 chars), GTIN-14 (14 chars), and any vendor-specific extended
-- barcode formats.
--
-- Caught by the EAN13 fallback cucumber scenario added in this same PR: a real 13-digit
-- value was silently truncated to its first 4 characters, forcing the test to use a
-- 4-char placeholder ('E123') instead of a representative EAN value.

INSERT INTO t_alter_column VALUES ('m_product_asi_data', 'EAN13_ProductCode', 'VARCHAR(50)', NULL, NULL);

-- Keep AD_Column metadata in sync with the physical column
UPDATE AD_Column
SET FieldLength = 50,
    Updated     = TO_TIMESTAMP('2026-05-14 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 100
WHERE AD_Column_ID = 592276;
