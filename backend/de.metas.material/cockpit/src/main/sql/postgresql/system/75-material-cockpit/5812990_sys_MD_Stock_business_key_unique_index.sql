-- DDL: prevent more than one ACTIVE MD_Stock row per business key.
-- Runs after the data-fix script (5812890) that de-duplicates existing rows — the index build
-- would fail while duplicates remain. Kept as a pure-DDL script, separate from the data fix.

CREATE UNIQUE INDEX MD_Stock_BusinessKey_uq ON MD_Stock (AD_Client_ID, AD_Org_ID, M_Product_ID, M_Warehouse_ID, AttributesKey) WHERE IsActive='Y';
