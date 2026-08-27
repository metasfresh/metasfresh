-- Delivery Planning aggregation: backfill M_Delivery_Planning_Alloc from the legacy 1:1
-- header link M_ShipperTransportation.M_Delivery_Planning_ID, BEFORE that column is dropped
-- (dropping it is a later, separate task -- this script does not touch the column, any view,
-- or any Java).
--
-- Scope: AGGREGATION-PROPOSAL.md §3b.3 steps 2 (pre-check) and 3 (backfill) only. Step 1
-- (create M_Delivery_Planning_Alloc) already shipped in 5820400/5820410. Steps 4-6 (recreate
-- the two views + docs_deliveryinstructions_description, drop the AD field then the column)
-- are the next task.
--
-- ===========================================================================================
-- Pre-migration counts (measured live, deep_tundra_uat_2, port 21632, 2026-08-27)
-- ===========================================================================================
--   DI instructions (C_DocType.DocSubType='DI')                          8
--   header link set (st.M_Delivery_Planning_ID > 0)                     8
--   planning FK set (dp.M_ShipperTransportation_ID > 0, on a DI)        8
--   existing M_Delivery_Planning_Alloc rows                              0
--   non-voided header link with no planning backref                     0
--   max M_ShippingPackage rows per DI instruction                       1
--   max M_Delivery_Planning rows per DI instruction (planning side)     1
-- So this script inserts exactly 8 allocations here and has nothing to reconcile. The
-- pre-check below is what makes the script correct on the customer instance too, where the
-- two sources may disagree and a second package per instruction is structurally possible.
--
-- ===========================================================================================
-- Which side is authoritative -- §3b.2
-- ===========================================================================================
-- M_Delivery_Planning.M_ShipperTransportation_ID is authoritative: it is the FK that
-- unlinkDeliveryPlannings() (DeliveryPlanningRepository, @DocValidate(TIMING_AFTER_VOID))
-- actively maintains on void, so it reflects the *current* link. The header column
-- M_ShipperTransportation.M_Delivery_Planning_ID is write-once at instruction generation and
-- is never cleaned up by that void path -- a voided instruction is expected to keep a stale
-- header link with no planning pointing back. So the backfill reads the planning-side FK, and
-- the header column is used only as a cross-check: a NON-VOIDED instruction whose header link
-- has no matching planning FK is a genuine anomaly (the two sources disagree in a way the
-- backfill would silently lose) and aborts the script. A voided instruction with a stale
-- header link is expected and does not abort.
--
-- ===========================================================================================
-- The package-join decision -- the trap in the design doc's (stale) draft insert
-- ===========================================================================================
-- The allocation's M_ShippingPackage_ID is mandatory AND uniquely indexed while IsActive='Y'
-- (M_Delivery_Planning_Alloc_Package_UQ, 5820410). An earlier draft of this backfill joined
-- the package to the *instruction* (LEFT JOIN M_ShippingPackage sp ON sp.M_ShipperTransportation_ID
-- = st.M_ShipperTransportation_ID), which is safe only while each instruction has exactly one
-- package -- true here (max 1, measured above) but not guaranteed on the customer instance,
-- where a second package on the same instruction would either multiply allocation rows or
-- collide on Package_UQ.
--
-- DECISION: join the package to the planning through the order line instead
-- (sp.C_OrderLine_ID = dp.C_OrderLine_ID), which is the correct, general form -- every
-- planning has an order line (DeliveryPlanningRepository:337) and createShippingPackage()
-- stamps the same order line onto the package it creates (:616), so this join reproduces the
-- planning<->package pairing regardless of how many packages the instruction carries. The
-- pre-check below proves this join is well-defined (exactly one active package per planning's
-- order line) BEFORE the insert runs, rather than assuming it.
--
-- ===========================================================================================
-- Field-by-field semantics -- mirrored from DeliveryPlanningRepository.createAllocation()
-- (backend/de.metas.deliveryplanning.base/.../DeliveryPlanningRepository.java:567-586)
-- ===========================================================================================
--   M_ShippingPackage_ID  the instruction's existing package, resolved via the order-line join
--                         above (not created fresh -- this is a backfill of an existing link,
--                         not a new allocation)
--   LineNo                getMaxAllocationLineNo(instruction) + 10 per planning (:828,:96
--                         ALLOCATION_LINE_NO_STEP=10); reproduced here as
--                         COALESCE(MAX existing LineNo on the instruction, 0)
--                         + ROW_NUMBER() OVER (PARTITION BY instruction ORDER BY planning id) * 10
--                         -- so a single-member instruction gets 10, matching the Java. The
--                         window function is safe against a partial re-run here because the
--                         whole backfill is one INSERT statement (atomic) applied at most once
--                         (tracked by AD_MigrationScript); it cannot leave some sibling rows of
--                         one instruction inserted and others not.
--   DocStatus, Processed  mirrored from the INSTRUCTION (st), exactly like createAllocation()
--   AD_Org_ID              the INSTRUCTION's org, not the planning's (createAllocation() sets
--                         it from deliveryInstructionRecord, not the planning)
--   AD_Client_ID           mirrored from the instruction for the same reason; verified equal to
--                         the planning's client on every row in this dataset
--
-- ===========================================================================================
-- Idempotence
-- ===========================================================================================
-- The migration tool runs this script at most once per DB (AD_MigrationScript). The
-- NOT EXISTS guard on the INSERT is defense-in-depth only, in case of a manual re-run
-- (e.g. after a restore that predates this script's AD_MigrationScript row): it re-checks
-- against an ACTIVE allocation for the same M_Delivery_Planning_ID, matching the
-- Planning_UQ partial index (IsActive='Y'), so a second execution inserts nothing.
--
-- No column drop, no view change, no Java change in this script -- that is the next task.

-- ===========================================================================================
-- Step 2 (§3b.3): pre-check, abort on anomaly -- BEFORE any write, so no branch can abort
-- halfway through a mutation.
-- ===========================================================================================
DO $$
DECLARE
    v_header_without_planning_backref  integer;
    v_planning_bad_package_join        integer;
BEGIN
    -- Cross-check (§3b.2): a NON-VOIDED DI instruction whose header link has no matching
    -- planning-side FK is a genuine anomaly -- the two sources disagree in a way the backfill
    -- (which reads the planning side) would silently lose. A voided instruction keeping a
    -- stale header link is expected and must NOT abort.
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
            'gh31608 M_Delivery_Planning_Alloc backfill: % non-voided DI instruction(s) carry a '
            'header link (M_ShipperTransportation.M_Delivery_Planning_ID) with no matching '
            'M_Delivery_Planning.M_ShipperTransportation_ID backref. This disagreement between '
            'the two sources must be resolved by hand before this backfill can run -- '
            'aborting without writing anything.',
            v_header_without_planning_backref;
    END IF;

    -- Proves the order-line join is well-defined before the insert relies on it: every
    -- planning reachable via the planning-side FK on a non-voided DI instruction must resolve
    -- to EXACTLY ONE active M_ShippingPackage through its order line. Zero means the join
    -- would drop the row (and the header cross-check above would then also fail, since no
    -- allocation ever gets created for it -- caught independently here for a precise message).
    -- More than one means the 1:1 assumption the whole join rests on does not hold and the
    -- backfill cannot pick a package unambiguously.
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
        WHERE dp.M_ShipperTransportation_ID > 0
          AND st.DocStatus <> 'VO'
        GROUP BY dp.M_Delivery_Planning_ID
        HAVING count(sp.M_ShippingPackage_ID) <> 1
    ) bad;

    IF v_planning_bad_package_join > 0 THEN
        RAISE EXCEPTION
            'gh31608 M_Delivery_Planning_Alloc backfill: % delivery planning(s) linked to a '
            'non-voided DI instruction do not resolve to exactly one active M_ShippingPackage '
            'via C_OrderLine_ID. The package-join assumption this backfill relies on '
            '(one active package per planning, matched through the order line) does not hold '
            'for these rows -- aborting without writing anything.',
            v_planning_bad_package_join;
    END IF;
END $$;

-- ===========================================================================================
-- Back up the table this script writes into, before the first write.
-- ===========================================================================================
SELECT backup_table('m_delivery_planning_alloc', '_gh31608_backfill');

-- ===========================================================================================
-- Step 3 (§3b.3): backfill one allocation per planning-side link, reusing the instruction's
-- existing M_ShippingPackage (resolved via the order line, see decision above).
-- ===========================================================================================
INSERT INTO M_Delivery_Planning_Alloc (
    M_Delivery_Planning_Alloc_ID,
    M_Delivery_Planning_ID, M_ShipperTransportation_ID, M_ShippingPackage_ID,
    LineNo, DocStatus, Processed,
    AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy)
SELECT nextval('m_delivery_planning_alloc_seq'),
       dp.M_Delivery_Planning_ID, st.M_ShipperTransportation_ID, sp.M_ShippingPackage_ID,
       COALESCE(existing_max.max_lineno, 0)
           + (ROW_NUMBER() OVER (PARTITION BY st.M_ShipperTransportation_ID
                                  ORDER BY dp.M_Delivery_Planning_ID) * 10),
       st.DocStatus, st.Processed,
       st.AD_Client_ID, st.AD_Org_ID, 'Y',
       TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99,
       TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 99
FROM M_Delivery_Planning dp
JOIN M_ShipperTransportation st ON st.M_ShipperTransportation_ID = dp.M_ShipperTransportation_ID
JOIN C_DocType dt ON dt.C_DocType_ID = st.C_DocType_ID AND dt.DocSubType = 'DI'
JOIN M_ShippingPackage sp
  ON sp.C_OrderLine_ID = dp.C_OrderLine_ID
 AND sp.IsActive = 'Y'
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

-- ===========================================================================================
-- Step 7 (§3b.3): verification -- run by hand after applying.
-- ===========================================================================================
-- (a) allocation count equals the pre-migration planning-side link count (expect 8):
--     SELECT count(*) FROM M_Delivery_Planning_Alloc;
-- (b) no M_Delivery_Planning with a non-zero M_ShipperTransportation_ID lacks an active
--     allocation (on a non-voided instruction; expect 0):
--     SELECT count(*)
--       FROM M_Delivery_Planning dp
--       JOIN M_ShipperTransportation st ON st.M_ShipperTransportation_ID = dp.M_ShipperTransportation_ID
--      WHERE dp.M_ShipperTransportation_ID > 0
--        AND st.DocStatus <> 'VO'
--        AND NOT EXISTS (SELECT 1 FROM M_Delivery_Planning_Alloc a
--                          WHERE a.M_Delivery_Planning_ID = dp.M_Delivery_Planning_ID AND a.IsActive='Y');
-- (c) no duplicate active M_Delivery_Planning_ID / M_ShippingPackage_ID among allocations
--     (both partial unique indexes satisfied by construction; expect 0 rows each):
--     SELECT M_Delivery_Planning_ID, count(*) FROM M_Delivery_Planning_Alloc WHERE IsActive='Y'
--       GROUP BY M_Delivery_Planning_ID HAVING count(*) > 1;
--     SELECT M_ShippingPackage_ID, count(*) FROM M_Delivery_Planning_Alloc WHERE IsActive='Y'
--       GROUP BY M_ShippingPackage_ID HAVING count(*) > 1;
-- (d) every allocation's DocStatus/Processed/AD_Org_ID equals its instruction's (expect 0):
--     SELECT count(*) FROM M_Delivery_Planning_Alloc a
--       JOIN M_ShipperTransportation st ON st.M_ShipperTransportation_ID = a.M_ShipperTransportation_ID
--      WHERE a.DocStatus <> st.DocStatus OR a.Processed <> st.Processed OR a.AD_Org_ID <> st.AD_Org_ID;
-- (e) LineNo is non-zero and matches the Java's numbering for a single-member instruction
--     (expect 8, all =10, and 0 for the "<>10" count):
--     SELECT count(*), count(*) FILTER (WHERE LineNo = 0) FROM M_Delivery_Planning_Alloc;
--     SELECT count(*) FROM M_Delivery_Planning_Alloc WHERE LineNo <> 10;
