-- DDL: prevent more than one ACTIVE MD_Stock row per business key.
-- Runs after the data-fix script (5812890) that de-duplicates existing rows — the index build
-- would fail while duplicates remain. Kept as a pure-DDL script, separate from the data fix.

-- Selective columns lead (M_Product_ID, AttributesKey, M_Warehouse_ID); the low-cardinality tenant
-- columns (AD_Client_ID, AD_Org_ID) come last — they add little to an index scan. Column order does
-- not change the uniqueness enforced, only lookup efficiency.
CREATE UNIQUE INDEX MD_Stock_BusinessKey_uq ON MD_Stock (M_Product_ID, AttributesKey, M_Warehouse_ID, AD_Client_ID, AD_Org_ID) WHERE IsActive='Y';
