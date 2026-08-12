-- Restrict AD_OrgInfo.DropShip_Warehouse_ID dropdown to warehouses flagged
-- IsDropShipWarehouse='Y' (and active, and matching the current AD_Org_ID — preserves the
-- existing scoping from val-rule 189 "M_Warehouse Org" that this column previously used).
--
-- DECISION: INSERT a new AD_Val_Rule rather than modify val-rule 189, because val-rule 189
-- is shared with 10 other columns (AD_OrgInfo.M_Warehouse_ID, M_InOut.M_Warehouse_ID,
-- M_Inventory.M_Warehouse_ID, ...) that must NOT be restricted to dropship warehouses.
--
-- LEVEL: AD_Column (not field-level), because AD_OrgInfo.DropShip_Warehouse_ID has only one
-- field instance.
--   AD_Column_ID = 55321 (AD_OrgInfo.DropShip_Warehouse_ID)
--
-- Pairs with: forward-guard MI AD_OrgInfo_DropShipWarehouse (Java side, rejects on save) and
-- backfill 5802700 (existing data).
-- Created: 2026-05-14 22:01

-- =============================================================================
-- 1. Insert new AD_Val_Rule
-- =============================================================================
INSERT INTO AD_Val_Rule
    (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, Type, Code, EntityType)
VALUES
    (540784 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-05-14 22:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-05-14 22:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'M_Warehouse Org (dropship only)',
     'S',
     'M_Warehouse.AD_Org_ID=@AD_Org_ID@ AND M_Warehouse.IsDropShipWarehouse=''Y''',
     'D')
;

-- =============================================================================
-- 2. Link the new val-rule to AD_OrgInfo.DropShip_Warehouse_ID
--    AD_Column_ID = 55321
-- =============================================================================
UPDATE AD_Column
SET    AD_Val_Rule_ID = 540784 /*From ID Server*/,
       Updated        = TO_TIMESTAMP('2026-05-14 22:01:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy      = 100
WHERE  AD_Column_ID = 55321
;
