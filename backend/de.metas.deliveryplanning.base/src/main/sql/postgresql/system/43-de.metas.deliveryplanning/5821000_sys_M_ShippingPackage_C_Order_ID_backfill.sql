-- Delivery Planning aggregation: backfill M_ShippingPackage.C_Order_ID for every shipping package a
-- delivery-planning allocation created, from the M_Delivery_Planning.C_Order_ID behind it.
--
-- Root cause this closes: DeliveryPlanningRepository.createShippingPackage() persisted the package's
-- C_OrderLine_ID but never its C_Order_ID. M_ShipperTransportationDAO.retrieveOrderIds() (which
-- OrderBL.syncDatesFromTransportOrder uses to push the delivery instruction's BLDate/ETA onto the
-- purchase order behind the cargo, and from there into split payment schedules) reads that column, so
-- a delivery-instruction package with no C_Order_ID gave it nothing to walk - a silent no-op. Package
-- creation now sets the column going forward; this script backfills the rows that already exist,
-- mirroring the very field createShippingPackage() now stamps.
--
-- Scope: every M_ShippingPackage reachable through an M_Delivery_Planning_Alloc row, active or
-- retired - a retired allocation's package is historical data (a removed/moved/voided allocation
-- deactivates rather than deletes it), and knowing which order it was for is just as informational
-- for a retired package as for an active one. The transport-order flow's own M_ShippingPackage rows
-- (created by PurchaseOrderToShipperTransportationRepository, no M_Delivery_Planning_Alloc row ever
-- points at them) are structurally excluded - this script only ever reaches a package through that
-- table, so it cannot touch them.
--
-- ===========================================================================================
-- Measured live (deep_tundra_uat_2, port 21632, 2026-08-28)
-- ===========================================================================================
--   M_Delivery_Planning_Alloc rows (active + retired)                                     41
--   M_ShippingPackage rows pointed at by >1 alloc row (ambiguous - see pre-check)           0
--   of the 41, package already carries a C_Order_ID                                         0
--   of the 41, the linked planning itself has no C_Order_ID (left untouched, expected)       0
--   => rows this script updates here                                                       41
-- The brief for this task assumed 8 rows, a count taken earlier in the day before this stack
-- accumulated more delivery instructions from later work in the same plan; re-measured here
-- rather than trusted, and the script is written to be correct for whatever count a target
-- instance actually has, not hardcoded to either number.
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

-- ===========================================================================================
-- Verification - run by hand after applying (expect 0 both times)
-- ===========================================================================================
-- (a) no delivery-planning package with a resolvable order id is left without one:
--     SELECT count(*) FROM M_ShippingPackage sp
--       JOIN M_Delivery_Planning_Alloc a ON a.M_ShippingPackage_ID = sp.M_ShippingPackage_ID
--       JOIN M_Delivery_Planning dp ON dp.M_Delivery_Planning_ID = a.M_Delivery_Planning_ID
--      WHERE coalesce(sp.C_Order_ID,0) <= 0 AND coalesce(dp.C_Order_ID,0) > 0;
-- (b) every package's C_Order_ID matches the planning that created it (through the allocation):
--     SELECT count(*) FROM M_ShippingPackage sp
--       JOIN M_Delivery_Planning_Alloc a ON a.M_ShippingPackage_ID = sp.M_ShippingPackage_ID
--       JOIN M_Delivery_Planning dp ON dp.M_Delivery_Planning_ID = a.M_Delivery_Planning_ID
--      WHERE dp.C_Order_ID > 0 AND sp.C_Order_ID <> dp.C_Order_ID;
