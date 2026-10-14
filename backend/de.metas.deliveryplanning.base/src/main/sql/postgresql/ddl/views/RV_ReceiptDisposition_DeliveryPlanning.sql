--
-- RV_ReceiptDisposition_DeliveryPlanning -- the single inbound list behind the receipt-disposition
-- delivery-planning window: active incoming-or-dropship delivery plannings (branch one, "planned") UNIONed
-- with the receipt schedules no active planning refers to (branch two, "unplanned"). The two branches are exact
-- complements, so a schedule never appears on both; a schedule shared by N plannings (what a SPLIT produces)
-- yields N rows.
--
-- KEY. RV_ReceiptDisposition_DeliveryPlanning_ID = M_Delivery_Planning_ID on branch one,
-- 1000000000 + M_ReceiptSchedule_ID on branch two.
--   * Arithmetic, never ROW_NUMBER(): a window function blocks predicate push-down, so a filtered open would
--     compute the whole view first.
--   * Branch one keys on the PLANNING, not the schedule: a split shares one schedule, so a schedule-derived id
--     would repeat and the window would lose grid identity, selection and zoom.
--   * Both source ids must stay <= 1,147,483,647 for the sum to fit a Java int.
--
-- LAZY-LOADING SOURCE COLUMNS. M_ReceiptSchedule.M_Shipper_ID, IsBLReceived, IsBookingConfirmed and IsWENotice
-- are IsLazyLoading='Y' -- AD-level ColumnSQL with NO physical value on the table, so a plain SQL view cannot
-- select them. The shipper is read off the already-joined C_Order (o.m_shipper_id); the three flags come from the
-- row's own transport order, per the bullet below.
--
-- PER-BRANCH vs SHARED. Identity and context (product, partner, warehouse, order, order line) are read off the
-- SCHEDULE on BOTH branches, one expression, no CASE - the generate command copies them onto the planning at
-- creation, so the two agree by construction. Dates, quantities and C_UOM_ID are read per branch, because they
-- stay planning-editable after creation and could otherwise silently disagree with each other.
--   * THREE discharge quantities, and each branch answers each of them from its OWN entity: QtyToMove (what
--     still needs to move), PlannedDischargeQuantity (what was planned) and ActualDischargeQuantity (what
--     arrived). Branch one reads the PLANNING - never the schedule: a split shares one schedule, so the
--     schedule carries a single figure for the whole order line while each planning plans its own share, and
--     reading the schedule would show the same order-line-wide number on every sibling row.
--     - QtyToMove: 0 once the planning is Processed, else its planned discharge. A planning is exactly ONE
--       receipt - getReceiveRejectionReason refuses a second one - so nothing stays outstanding on it after
--       the receive; a shortfall becomes a NEW planning, because it will be a new transport. Branch two serves
--       M_ReceiptSchedule.QtyToMove AS IT STANDS, never ordered-minus-moved: that stored column is maintained
--       by the schedule, which forces it to 0 when the schedule is CLOSED - measured on this stack, 112 of
--       3370 schedules do not satisfy the arithmetic and every one of them is closed with QtyToMove 0. The two
--       branches therefore agree in MEANING, each entity expressing the same "done implies 0" rule for itself.
--     - PlannedDischargeQuantity: the planning's own figure on branch one, rs.qtyordered on branch two. A bare
--       schedule has no planning to ask, and what was "planned" for it is what was ordered; it used to serve
--       rs.qtytomove here, i.e. a REMAINING figure under a plan caption - which is the mislabelling the new
--       QtyToMove column removes.
--     - ActualDischargeQuantity: the planning's own figure, and rs.qtymoved on branch two.
--   * Only the DISCHARGE end appears. The planning also carries PlannedLoadedQuantity and ActualLoadQty, the
--     vendor's end of the movement; this is a receipt window, so what is discharged here is what belongs on it.
--     For Incoming the actual equals the planned figure until a partial receipt splits the two apart - which is
--     exactly the case the three columns above exist to show.
--   * ContainerNo, IsBLReceived, IsBookingConfirmed and IsWENotice are the ROW's own transport order's: branch
--     one reads them off the planning's M_ShipperTransportation_ID, branch two scopes the shipping-package join
--     to the schedule's ORDER LINE, the way M_ReceiptSchedule.M_ShipperTransportation_ID's ColumnSQL does.
--     Deliberately NOT the order-wide read the four source columns' own ColumnSQL uses: a split puts its siblings
--     on DIFFERENT transport orders, so an order-wide read shows every container of the order, and an OR of every
--     flag on it, on every row of it.
--   * IsReadyForReceipt is the readiness the window filters by, and its two branches are NOT the same
--     expression: branch one reads the planning's own stored flag (ready only once allocated to a COMPLETED
--     instruction), branch two is the literal 'Y' - a row with no planning has nothing being arranged, so it
--     is never held back. Branch two is therefore not "unknown" and must not be NULL: NULL would drop every
--     unplanned row out of a readiness filter that asks for the actionable ones.
--   * M_Warehouse_ID is the schedule's PLAIN column, deliberately not M_Warehouse_Effective_ID: the planning
--     stores the plain one, so the effective one would make the column's two halves disagree.
--   * ATA (branch two) is the EARLIEST movement date over the schedule's receipts, and needs all three
--     conditions - allocation active, receipt completed, aggregate min - or it reports something false.
--   * CalendarWeek is EXTRACT(week from <that branch's ETA expression>), not from a bare source column, so the
--     week cannot disagree with the ETA shown beside it. ISO week, so a year-end date reports its ISO year's week.
--
-- DIRECTIONS: branch one takes Incoming and Dropship - the pair TransportDirection#isIncomingOrDropship()
-- names, and the two the incoming generate command produces, so both carry a receipt schedule. Outgoing
-- carries a shipment schedule instead and has nothing this view could select.
--

DROP VIEW IF EXISTS RV_ReceiptDisposition_DeliveryPlanning$new
;

CREATE OR REPLACE VIEW RV_ReceiptDisposition_DeliveryPlanning$new
AS
-- branch one: planned rows -- one per active incoming-or-dropship planning carrying a receipt schedule
SELECT dp.m_delivery_planning_id                                             AS RV_ReceiptDisposition_DeliveryPlanning_ID,
       rs.m_receiptschedule_id,
       dp.m_delivery_planning_id,
       'Y'::char(1)                                                          AS isplanned,
       dp.isreadyforreceipt                                                  AS isreadyforreceipt,
       rs.m_product_id,
       rs.c_bpartner_id,
       rs.m_warehouse_id,
       rs.c_order_id,
       COALESCE(dp.eta, COALESCE(rs.datepromised_override, rs.movementdate)) AS eta,
       EXTRACT(week from
               COALESCE(dp.eta, COALESCE(rs.datepromised_override, rs.movementdate)))
                                                                              AS calendarweek,
       COALESCE(dp.etd, o.preparationdate)                                   AS etd,
       dp.atd,
       dp.ata,
       COALESCE(rs.datepromised_override, rs.movementdate)                   AS datepromised_effective,
       dp.qtyordered,
       dp.c_uom_id,
       rs.poreference,
       dpst.containerno                                                      AS containerno,
       dp.m_shippertransportation_id                                         AS m_shippertransportation_id,
       o.m_shipper_id,
       CASE WHEN dp.processed = 'Y' THEN 0::numeric
            ELSE dp.planneddischargequantity END                             AS qtytomove,
       dp.planneddischargequantity                                           AS planneddischargequantity,
       dp.actualdischargequantity                                            AS actualdischargequantity,
       dp.batch,
       rs.isconfirmedbysupplier,
       dpst.isblreceived                                                     AS isblreceived,
       dpst.isbookingconfirmed                                               AS isbookingconfirmed,
       dpst.iswenotice                                                       AS iswenotice,
       dp.processed,
       dp.ad_client_id,
       dp.ad_org_id,
       dp.isactive,
       dp.created,
       dp.createdby,
       dp.updated,
       dp.updatedby
FROM m_delivery_planning dp
         JOIN m_receiptschedule rs ON rs.m_receiptschedule_id = dp.m_receiptschedule_id
         LEFT JOIN c_order o ON o.c_order_id = rs.c_order_id
         LEFT JOIN m_shippertransportation dpst ON dpst.m_shippertransportation_id = dp.m_shippertransportation_id
             AND dpst.isactive = 'Y'
WHERE dp.isactive = 'Y'
  AND dp.transportdirection IN ('Incoming', 'Dropship')
  AND dp.m_receiptschedule_id IS NOT NULL

UNION ALL

-- branch two: unplanned rows -- one per receipt schedule no active planning refers to
SELECT 1000000000 + rs.m_receiptschedule_id                AS RV_ReceiptDisposition_DeliveryPlanning_ID,
       rs.m_receiptschedule_id,
       NULL::numeric(10)                                   AS m_delivery_planning_id,
       'N'::char(1)                                         AS isplanned,
       'Y'::char(1)                                        AS isreadyforreceipt,
       rs.m_product_id,
       rs.c_bpartner_id,
       rs.m_warehouse_id,
       rs.c_order_id,
       COALESCE(rs.datepromised_override, rs.movementdate) AS eta,
       EXTRACT(week from COALESCE(rs.datepromised_override, rs.movementdate))
                                                            AS calendarweek,
       o.preparationdate                                   AS etd,
       o.preparationdate                                   AS atd,
       (SELECT min(io.movementdate)
        FROM m_receiptschedule_alloc rsa
                 JOIN m_inoutline iol ON iol.m_inoutline_id = rsa.m_inoutline_id
                 JOIN m_inout io ON io.m_inout_id = iol.m_inout_id
        WHERE rsa.m_receiptschedule_id = rs.m_receiptschedule_id
          AND rsa.isactive = 'Y'
          AND io.docstatus IN ('CO', 'CL'))                AS ata,
       COALESCE(rs.datepromised_override, rs.movementdate) AS datepromised_effective,
       rs.qtyordered,
       rs.c_uom_id,
       rs.poreference,
       own_st.containerno       AS containerno,
       own_st.m_shippertransportation_id       AS m_shippertransportation_id,
       o.m_shipper_id,
       rs.qtytomove                                        AS qtytomove,
       rs.qtyordered                                       AS planneddischargequantity,
       rs.qtymoved                                         AS actualdischargequantity,
       NULL::character varying(250)                        AS batch,
       rs.isconfirmedbysupplier,
       own_st.isblreceived       AS isblreceived,
       own_st.isbookingconfirmed       AS isbookingconfirmed,
       own_st.iswenotice       AS iswenotice,
       rs.processed,
       rs.ad_client_id,
       rs.ad_org_id,
       rs.isactive,
       rs.created,
       rs.createdby,
       rs.updated,
       rs.updatedby
FROM m_receiptschedule rs
         LEFT JOIN c_order o ON o.c_order_id = rs.c_order_id
    -- One pass over the row's own transport order(s) instead of four identical correlated
    -- subselects. They differed only in the aggregated column -- same source, same join, same
    -- predicate -- so each of the four re-scanned the very same rows, once per unplanned row.
    -- Proven output-equivalent on the local stack (4155 schedules compared, 0 differing) and it
    -- takes the whole view's estimate back under jit_above_cost, so the JIT pass that dominated
    -- the page render stops firing. Collapsed 2026-09-10.
         LEFT JOIN LATERAL (SELECT max(st.m_shippertransportation_id) AS m_shippertransportation_id,
                                   max(st.containerno)         AS containerno,
                                   max(st.isblreceived)        AS isblreceived,
                                   max(st.isbookingconfirmed)  AS isbookingconfirmed,
                                   max(st.iswenotice)          AS iswenotice
                            FROM m_shippingpackage sp
                                     JOIN m_shippertransportation st
                                          ON st.m_shippertransportation_id = sp.m_shippertransportation_id
                                              AND st.isactive = 'Y'
                            WHERE sp.c_order_id = rs.c_order_id
                              AND sp.c_orderline_id = rs.c_orderline_id
                              AND sp.isactive = 'Y') own_st ON TRUE
WHERE NOT EXISTS (SELECT 1
                  FROM m_delivery_planning p
                  WHERE p.m_receiptschedule_id = rs.m_receiptschedule_id
                    AND p.isactive = 'Y')
;

SELECT db_alter_view(
               'rv_receiptdisposition_deliveryplanning',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('rv_receiptdisposition_deliveryplanning$new'))
           )
;

DROP VIEW IF EXISTS RV_ReceiptDisposition_DeliveryPlanning$new
;
