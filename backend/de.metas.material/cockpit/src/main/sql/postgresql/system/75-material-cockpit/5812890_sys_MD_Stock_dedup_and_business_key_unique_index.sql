-- MD_Stock can end up with more than one ACTIVE row for the same business key
-- (AD_Client_ID, AD_Org_ID, M_Product_ID, M_Warehouse_ID, AttributesKey), which breaks any
-- firstOnly()/single-row lookup on that key. This script de-duplicates existing data and then
-- adds a partial unique index to prevent the situation from recurring.

-- Backup before touching business data.
SELECT backup_table('md_stock');

-- De-duplicate: for every active business-key bucket with more than one row, keep the row with
-- the lowest MD_Stock_ID and deactivate the rest.
UPDATE MD_Stock t
SET IsActive = 'N',
    Updated = TO_TIMESTAMP('2026-07-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
WHERE t.IsActive = 'Y'
  AND t.MD_Stock_ID > (
        SELECT MIN(k.MD_Stock_ID)
        FROM MD_Stock k
        WHERE k.IsActive = 'Y'
          AND k.AD_Client_ID = t.AD_Client_ID
          AND k.AD_Org_ID = t.AD_Org_ID
          AND k.M_Product_ID = t.M_Product_ID
          AND k.M_Warehouse_ID = t.M_Warehouse_ID
          AND k.AttributesKey = t.AttributesKey
      );

-- Prevent recurrence: at most one active row per business key.
CREATE UNIQUE INDEX MD_Stock_BusinessKey_uq ON MD_Stock (AD_Client_ID, AD_Org_ID, M_Product_ID, M_Warehouse_ID, AttributesKey) WHERE IsActive='Y';
