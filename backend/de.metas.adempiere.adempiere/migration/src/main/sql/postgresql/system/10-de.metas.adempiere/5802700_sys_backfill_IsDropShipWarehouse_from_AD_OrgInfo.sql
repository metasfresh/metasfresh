-- Backfill M_Warehouse.IsDropShipWarehouse='Y' for every warehouse currently referenced by
-- AD_OrgInfo.DropShip_Warehouse_ID.
--
-- Establishes the invariant: any warehouse used as an org's dropship warehouse must carry the
-- IsDropShipWarehouse flag. The forward-guard model interceptor (AD_OrgInfo_DropShipWarehouse)
-- and the AD_OrgInfo.DropShip_Warehouse_ID val-rule (see 5802710) keep new/changed assignments
-- in sync. This backfill closes the loop for already-assigned values.
--
-- Idempotent: the COALESCE(...) <> 'Y' guard skips warehouses already flagged.
-- Created: 2026-05-14 22:00

UPDATE M_Warehouse
SET    IsDropShipWarehouse = 'Y',
       Updated             = TO_TIMESTAMP('2026-05-14 22:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy           = 99
WHERE  M_Warehouse_ID IN (
           SELECT DropShip_Warehouse_ID
           FROM   AD_OrgInfo
           WHERE  DropShip_Warehouse_ID IS NOT NULL
       )
  AND  COALESCE(IsDropShipWarehouse, 'N') <> 'Y'
;
