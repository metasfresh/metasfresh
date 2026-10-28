-- Delivery Planning aggregation: backfill M_ShippingPackage.C_Order_ID for every shipping package a
-- delivery-planning allocation created, from the M_Delivery_Planning.C_Order_ID behind it.
--
-- The column is what carries a delivery instruction's BLDate/ETA back onto the purchase order behind
-- the cargo (and from there into split payment schedules): the sync walks M_ShippingPackage.C_Order_ID,
-- so a package without one is a silent no-op. Packages created from here on carry it; this script
-- fills the rows that already exist.
--
-- Scope: every M_ShippingPackage reachable through an M_Delivery_Planning_Alloc row, active or retired
-- -- a retired allocation's package is historical data (removing/moving/voiding an allocation
-- deactivates rather than deletes it), and which order it was for stays just as informational. The
-- transport-order flow's own packages have no M_Delivery_Planning_Alloc row, so they are out of reach
-- by construction.
--
-- ===========================================================================================
-- Pre-check: abort rather than guess if the 1:1 package<->allocation assumption does not hold
-- ===========================================================================================
DO $$
DECLARE
    v_package_shared_by_allocations integer;
BEGIN
    SELECT count(*)
    INTO v_package_shared_by_allocations
    FROM (
        SELECT M_ShippingPackage_ID
        FROM M_Delivery_Planning_Alloc
        GROUP BY M_ShippingPackage_ID
        HAVING count(*) > 1
    ) bad;

    IF v_package_shared_by_allocations > 0 THEN
        RAISE EXCEPTION
            'M_ShippingPackage.C_Order_ID backfill: % M_ShippingPackage row(s) are pointed at by '
            'more than one M_Delivery_Planning_Alloc row. Every package a delivery-planning '
            'allocation creates is expected to belong to exactly one allocation (createAllocations() '
            'creates a fresh package per allocation) - this data does not match that assumption, so '
            'the C_Order_ID to backfill would be ambiguous. Aborting without writing anything - '
            'resolve the ambiguity by hand and re-run.',
            v_package_shared_by_allocations;
    END IF;
END $$;

-- ===========================================================================================
-- Backfill: one UPDATE, join through the allocation to the planning that created the package.
-- Idempotent by construction: the "already set" guard means a re-run touches zero rows.
-- ===========================================================================================
SELECT backup_table('M_ShippingPackage', '_C_Order_ID_backfill');

UPDATE M_ShippingPackage sp
SET C_Order_ID = dp.C_Order_ID,
    Updated    = TO_TIMESTAMP('2026-08-28 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
FROM M_Delivery_Planning_Alloc a
JOIN M_Delivery_Planning dp ON dp.M_Delivery_Planning_ID = a.M_Delivery_Planning_ID
WHERE a.M_ShippingPackage_ID = sp.M_ShippingPackage_ID
  AND coalesce(sp.C_Order_ID, 0) <= 0
  AND coalesce(dp.C_Order_ID, 0) > 0
;
