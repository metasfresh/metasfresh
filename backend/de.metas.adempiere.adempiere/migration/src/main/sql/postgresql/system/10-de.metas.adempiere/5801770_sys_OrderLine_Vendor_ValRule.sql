-- Restrict C_OrderLine.C_BPartner_Vendor_ID dropdown on dropship-warehouse sales orders
--
-- DECISION: INSERT new AD_Val_Rule (column C_BPartner_Vendor_ID had no val-rule before).
-- APPROACH: Single rule with conditional SQL (Approach A):
--   - On a dropship warehouse (IsDropShipWarehouse='Y'): show only IsVendor='Y' BPs that have
--     a C_BPartner_Product row for the order line's product (when a product is set).
--   - On a non-dropship warehouse: show all IsVendor='Y' BPs (no extra filter; unchanged behaviour).
-- LEVEL: AD_Column (not field-level), because both SO and PO order-line tabs should see the same rule.
--   AD_Column_ID = 577156 (C_OrderLine.C_BPartner_Vendor_ID)
--
-- Context variables available from C_OrderLine:
--   @M_Warehouse_ID@  — confirmed reachable (see AD_Val_Rule 502100)
--   @M_Product_ID@    — current line's product (0 or null when not set; /0 fallback used)
--   @M_Product_ID/0@  — substitutes 0 if null/empty

-- =============================================================================
-- 1. Insert new AD_Val_Rule
-- =============================================================================
INSERT INTO AD_Val_Rule
    (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, Type, Code, EntityType)
VALUES
    (540782 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-05-11 14:05:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-11 14:05:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'C_BPartner Vendor for OrderLine (dropship-aware)',
     'S',
     'C_BPartner.IsVendor=''Y''
AND C_BPartner.IsActive=''Y''
AND (
  -- dropship-warehouse branch: enforce vendor-product link when product is set
  (
    EXISTS (SELECT 1 FROM M_Warehouse wh
            WHERE wh.M_Warehouse_ID = @M_Warehouse_ID@
              AND wh.IsDropShipWarehouse = ''Y''
              AND wh.IsActive = ''Y'')
    AND (
      COALESCE(@M_Product_ID/0@, 0) = 0
      OR EXISTS (SELECT 1 FROM C_BPartner_Product bpp
                 WHERE bpp.C_BPartner_ID = C_BPartner.C_BPartner_ID
                   AND bpp.M_Product_ID = @M_Product_ID/0@
                   AND bpp.IsActive = ''Y'')
    )
  )
  OR
  -- non-dropship branch: no extra restriction (preserve existing behaviour)
  NOT EXISTS (SELECT 1 FROM M_Warehouse wh
              WHERE wh.M_Warehouse_ID = @M_Warehouse_ID@
                AND wh.IsDropShipWarehouse = ''Y''
                AND wh.IsActive = ''Y'')
)',
     'D');

-- =============================================================================
-- 2. Set the new val-rule on the AD_Column
--    AD_Column_ID = 577156 (C_OrderLine.C_BPartner_Vendor_ID)
-- =============================================================================
UPDATE AD_Column
SET    AD_Val_Rule_ID = 540782 /*From ID Server*/,
       Updated        = TO_TIMESTAMP('2026-05-11 14:05:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy      = 100
WHERE  AD_Column_ID = 577156;
