-- Backfill M_Delivery_Planning_Alloc from the legacy 1:1 header link
-- M_ShipperTransportation.M_Delivery_Planning_ID, before that column is dropped. This script
-- touches no column, view or Java.
--
-- M_Delivery_Planning.M_ShipperTransportation_ID is the authoritative side: unlinkDeliveryPlannings()
-- maintains it on void, so it reflects the current link, while the header column is write-once at
-- instruction generation and never cleaned up. The backfill therefore reads the planning-side FK and
-- uses the header column only as a cross-check -- a NON-VOIDED instruction whose header link has no
-- matching planning FK aborts the script; a voided one with a stale header link is expected.
--
-- The allocation's package is resolved through the planning's order line AND the planning's own
-- instruction (sp.C_OrderLine_ID = dp.C_OrderLine_ID AND sp.M_ShipperTransportation_ID =
-- st.M_ShipperTransportation_ID). Joining the package to the instruction alone is unsafe: it is
-- well-defined only while every instruction carries exactly one package, which no index guarantees.
-- The order-line correlation is the general form (every planning has an order line, and
-- createShippingPackage() stamps the same order line onto the package); the instruction conjunct makes
-- the pairing correct by construction, so a planning whose only order-line match sits on another
-- instruction aborts on the first guard instead of being mis-paired.
--
-- AD_Org_ID and AD_Client_ID are the INSTRUCTION's, not the planning's, mirroring
-- createAllocation(); the third pre-check aborts if any pair disagrees on client. LineNo mirrors
-- getMaxAllocationLineNo() + ALLOCATION_LINE_NO_STEP, so a single-member instruction gets 10.
--
-- The migration tool applies this once per DB; the NOT EXISTS guard on the INSERT is defence in depth
-- against a manual re-run and matches the Planning_UQ partial index (IsActive='Y').

-- ===========================================================================================
-- Pre-check, abort on anomaly -- BEFORE any write, so no branch can abort
-- halfway through a mutation.
-- ===========================================================================================
DO $$
DECLARE
    v_header_without_planning_backref  integer;
    v_planning_bad_package_join        integer;
    v_package_shared_by_plannings      integer;
    v_planning_client_mismatch         integer;
BEGIN
    -- A NON-VOIDED instruction whose header link has no matching planning-side FK is an anomaly the
    -- backfill would silently lose. A voided one keeping a stale header link is expected.
    SELECT count(*)
    INTO v_header_without_planning_backref
    FROM M_ShipperTransportation st
    JOIN C_DocType dt ON dt.C_DocType_ID = st.C_DocType_ID AND dt.DocSubType = 'DI'
    WHERE st.M_Delivery_Planning_ID > 0
      AND st.DocStatus <> 'VO'
      AND NOT EXISTS (
          SELECT 1 FROM M_Delivery_Planning dp
          WHERE dp.M_ShipperTransportation_ID = st.M_ShipperTransportation_ID
      );

    IF v_header_without_planning_backref > 0 THEN
        RAISE EXCEPTION
            'M_Delivery_Planning_Alloc backfill: % non-voided DI instruction(s) carry a '
            'header link (M_ShipperTransportation.M_Delivery_Planning_ID) with no matching '
            'M_Delivery_Planning.M_ShipperTransportation_ID backref. This disagreement between '
            'the two sources must be resolved by hand before this backfill can run -- '
            'aborting without writing anything.',
            v_header_without_planning_backref;
    END IF;

    -- Every planning reachable via the planning-side FK must resolve to EXACTLY ONE active package.
    -- Zero means the join would drop the row; more than one means the 1:1 assumption the join rests on
    -- does not hold and no package can be picked unambiguously.
    SELECT count(*)
    INTO v_planning_bad_package_join
    FROM (
        SELECT dp.M_Delivery_Planning_ID,
               count(sp.M_ShippingPackage_ID) AS n_packages
        FROM M_Delivery_Planning dp
        JOIN M_ShipperTransportation st ON st.M_ShipperTransportation_ID = dp.M_ShipperTransportation_ID
        JOIN C_DocType dt ON dt.C_DocType_ID = st.C_DocType_ID AND dt.DocSubType = 'DI'
        LEFT JOIN M_ShippingPackage sp
               ON sp.C_OrderLine_ID = dp.C_OrderLine_ID
              AND sp.IsActive = 'Y'
              AND sp.M_ShipperTransportation_ID = st.M_ShipperTransportation_ID
        WHERE dp.M_ShipperTransportation_ID > 0
          AND st.DocStatus <> 'VO'
        GROUP BY dp.M_Delivery_Planning_ID
        HAVING count(sp.M_ShippingPackage_ID) <> 1
    ) bad;

    IF v_planning_bad_package_join > 0 THEN
        RAISE EXCEPTION
            'M_Delivery_Planning_Alloc backfill: % delivery planning(s) linked to a '
            'non-voided DI instruction do not resolve to exactly one active M_ShippingPackage. '
            'The package must match the planning BOTH on C_OrderLine_ID AND on '
            'M_ShipperTransportation_ID; a package that matches the order line but belongs to a '
            'different instruction is deliberately NOT accepted, because this backfill cannot '
            'tell which pairing such data was meant to express. Aborting without writing '
            'anything -- resolve the pairing by hand and re-run.',
            v_planning_bad_package_join;
    END IF;

    -- The reverse direction: two plannings on the same instruction may share one C_OrderLine_ID
    -- (partial shipments) and so resolve to the SAME package, which Package_UQ forbids. Reported here
    -- in this script's language instead of as a raw index violation.
    SELECT count(*)
    INTO v_package_shared_by_plannings
    FROM (
        SELECT sp.M_ShippingPackage_ID
        FROM M_Delivery_Planning dp
        JOIN M_ShipperTransportation st ON st.M_ShipperTransportation_ID = dp.M_ShipperTransportation_ID
        JOIN C_DocType dt ON dt.C_DocType_ID = st.C_DocType_ID AND dt.DocSubType = 'DI'
        JOIN M_ShippingPackage sp
          ON sp.C_OrderLine_ID = dp.C_OrderLine_ID
         AND sp.IsActive = 'Y'
         AND sp.M_ShipperTransportation_ID = st.M_ShipperTransportation_ID
        WHERE dp.M_ShipperTransportation_ID > 0
          AND st.DocStatus <> 'VO'
        GROUP BY sp.M_ShippingPackage_ID
        HAVING count(*) > 1
    ) bad;

    IF v_package_shared_by_plannings > 0 THEN
        RAISE EXCEPTION
            'M_Delivery_Planning_Alloc backfill: % active M_ShippingPackage row(s) would '
            'be claimed by more than one delivery planning on the same instruction through the '
            'order-line join. The package-join assumption this backfill relies on (one active '
            'package per planning, matched on BOTH C_OrderLine_ID and M_ShipperTransportation_ID) '
            'does not hold for these rows -- aborting without writing anything.',
            v_package_shared_by_plannings;
    END IF;

    -- The allocation stamps the INSTRUCTION's client, so a planning and its instruction disagreeing on
    -- client must abort rather than be mirrored wrongly.
    SELECT count(*)
    INTO v_planning_client_mismatch
    FROM M_Delivery_Planning dp
    JOIN M_ShipperTransportation st ON st.M_ShipperTransportation_ID = dp.M_ShipperTransportation_ID
    JOIN C_DocType dt ON dt.C_DocType_ID = st.C_DocType_ID AND dt.DocSubType = 'DI'
    WHERE dp.M_ShipperTransportation_ID > 0
      AND st.DocStatus <> 'VO'
      AND dp.AD_Client_ID <> st.AD_Client_ID;

    IF v_planning_client_mismatch > 0 THEN
        RAISE EXCEPTION
            'M_Delivery_Planning_Alloc backfill: % delivery planning(s) linked to a '
            'non-voided DI instruction have an AD_Client_ID different from that instruction''s. '
            'The allocation mirrors the instruction''s client (same as AD_Org_ID); this '
            'disagreement must be resolved by hand before this backfill can run -- aborting '
            'without writing anything.',
            v_planning_client_mismatch;
    END IF;
END $$;

-- ===========================================================================================
-- Back up the table this script writes into, before the first write.
-- ===========================================================================================
SELECT backup_table('m_delivery_planning_alloc', '_alloc_backfill');

-- ===========================================================================================
-- Make sure the table's native PK sequence exists before nextval() is called on it.
--
-- M_Delivery_Planning_Alloc was created by a raw INSERT INTO AD_Table, which never runs
-- MTable.afterSave(); the only other creator, dba_seq_check_native(), is called by after_migration()
-- AFTER the whole batch. So on a fresh apply the sequence does not exist yet and the INSERT below
-- fails with 'relation "m_delivery_planning_alloc_seq" does not exist'. Calling it explicitly for one
-- table is the established pattern; it is a check-and-create, so a no-op where the sequence exists.
-- ===========================================================================================
SELECT public.dba_seq_check_native('M_Delivery_Planning_Alloc');

-- ===========================================================================================
-- Backfill one allocation per planning-side link, reusing the instruction's
-- existing M_ShippingPackage.
-- ===========================================================================================
INSERT INTO M_Delivery_Planning_Alloc (
    M_Delivery_Planning_Alloc_ID,
    M_Delivery_Planning_ID, M_ShipperTransportation_ID, M_ShippingPackage_ID,
    LineNo,
    AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy)
SELECT nextval('m_delivery_planning_alloc_seq'),
       dp.M_Delivery_Planning_ID, st.M_ShipperTransportation_ID, sp.M_ShippingPackage_ID,
       COALESCE(existing_max.max_lineno, 0)
           + (ROW_NUMBER() OVER (PARTITION BY st.M_ShipperTransportation_ID
                                  ORDER BY dp.M_Delivery_Planning_ID) * 10),
       st.AD_Client_ID, st.AD_Org_ID, 'Y',
       TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99,
       TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99
FROM M_Delivery_Planning dp
JOIN M_ShipperTransportation st ON st.M_ShipperTransportation_ID = dp.M_ShipperTransportation_ID
JOIN C_DocType dt ON dt.C_DocType_ID = st.C_DocType_ID AND dt.DocSubType = 'DI'
JOIN M_ShippingPackage sp
  ON sp.C_OrderLine_ID = dp.C_OrderLine_ID
 AND sp.IsActive = 'Y'
 AND sp.M_ShipperTransportation_ID = st.M_ShipperTransportation_ID
LEFT JOIN LATERAL (
    SELECT max(existing.LineNo) AS max_lineno
    FROM M_Delivery_Planning_Alloc existing
    WHERE existing.M_ShipperTransportation_ID = st.M_ShipperTransportation_ID
) existing_max ON TRUE
WHERE dp.M_ShipperTransportation_ID > 0
  AND st.DocStatus <> 'VO'
  AND NOT EXISTS (
      SELECT 1 FROM M_Delivery_Planning_Alloc existing
      WHERE existing.M_Delivery_Planning_ID = dp.M_Delivery_Planning_ID
        AND existing.IsActive = 'Y'
  )
;
