-- Backfills one DD_OrderLine_PickingJobSchedule row per still-open replenishment DD_OrderLine that
-- carries a single-schedule M_Picking_Job_Schedule_ID, so pre-existing orders are visible to the
-- "is this assignment already served?" predicate, which resolves served-ness only through this table.
-- Must run BEFORE the single-schedule FK columns are dropped: it reads them, and an open order left
-- without an alloc row makes the drift watchdog issue a SECOND order for demand the first covers,
-- at the full outstanding quantity with no netting — a duplicated physical stock movement.
-- Data only: no order is re-planned, re-consolidated or re-quantified.

-- The alloc table's native PK sequence is created by dba_seq_check_native(), which only runs in
-- after_migration() — i.e. not yet when this script applies in the same batch as the CREATE TABLE.
SELECT dba_seq_check_native('DD_OrderLine_PickingJobSchedule');

-- Preview before applying on a live instance (uncomment, run, inspect):
-- SELECT ol.DD_OrderLine_ID,
--        ol.DD_Order_ID,
--        COALESCE(ol.M_Picking_Job_Schedule_ID, o.M_Picking_Job_Schedule_ID) AS M_Picking_Job_Schedule_ID,
--        ol.QtyEntered AS Qty,
--        ol.C_UOM_ID,
--        o.DocumentNo,
--        o.IsPickingDisconnected
-- FROM DD_OrderLine ol
--          JOIN DD_Order o ON o.DD_Order_ID = ol.DD_Order_ID
--          JOIN M_Picking_Job_Schedule pjs
--               ON pjs.M_Picking_Job_Schedule_ID = COALESCE(ol.M_Picking_Job_Schedule_ID, o.M_Picking_Job_Schedule_ID)
-- WHERE o.DocStatus = 'CO'
--   AND o.IsActive = 'Y'
--   AND ol.IsActive = 'Y'
--   AND pjs.IsActive = 'Y'
--   AND pjs.Processed = 'N'
--   AND NOT EXISTS (SELECT 1
--                   FROM DD_OrderLine_PickingJobSchedule x
--                   WHERE x.DD_OrderLine_ID = ol.DD_OrderLine_ID
--                     AND x.M_Picking_Job_Schedule_ID = pjs.M_Picking_Job_Schedule_ID)
-- ORDER BY ol.DD_OrderLine_ID;

INSERT INTO DD_OrderLine_PickingJobSchedule (DD_OrderLine_PickingJobSchedule_ID,
                                             AD_Client_ID, AD_Org_ID, IsActive,
                                             Created, CreatedBy, Updated, UpdatedBy,
                                             DD_OrderLine_ID, M_Picking_Job_Schedule_ID, Qty, C_UOM_ID)
SELECT nextval('dd_orderline_pickingjobschedule_seq'),
       ol.AD_Client_ID,
       ol.AD_Org_ID,
       'Y',
       TO_TIMESTAMP('2026-07-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
       99,
       TO_TIMESTAMP('2026-07-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
       99,
       ol.DD_OrderLine_ID,
       COALESCE(ol.M_Picking_Job_Schedule_ID, o.M_Picking_Job_Schedule_ID),
       ol.QtyEntered,
       ol.C_UOM_ID
FROM DD_OrderLine ol
         JOIN DD_Order o ON o.DD_Order_ID = ol.DD_Order_ID
         JOIN M_Picking_Job_Schedule pjs
              ON pjs.M_Picking_Job_Schedule_ID = COALESCE(ol.M_Picking_Job_Schedule_ID, o.M_Picking_Job_Schedule_ID)
WHERE o.DocStatus = 'CO'
  AND o.IsActive = 'Y'
  AND ol.IsActive = 'Y'
  AND pjs.IsActive = 'Y'
  AND pjs.Processed = 'N'
  AND NOT EXISTS (SELECT 1
                  FROM DD_OrderLine_PickingJobSchedule x
                  WHERE x.DD_OrderLine_ID = ol.DD_OrderLine_ID
                    AND x.M_Picking_Job_Schedule_ID = pjs.M_Picking_Job_Schedule_ID)
;
