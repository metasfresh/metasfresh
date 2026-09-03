--
-- RV_ReceiptLogistics -- the single inbound list behind the receipt-logistics window.
--
-- The dispatcher planning inbound receipts today has to read two lists: the delivery plannings
-- (what somebody already planned) and the receipt schedules nobody has planned yet. This view is
-- those two lists, unioned, so the window can show one.
--
-- ONE ROW =
--   branch one ("planned")   -- one active Incoming delivery planning that carries a receipt schedule;
--   branch two ("unplanned") -- one receipt schedule that no active delivery planning refers to.
-- The two branches are exact complements over the receipt schedules: branch one requires an active
-- planning, branch two requires that none exists. Hence a schedule never appears on both branches,
-- and a schedule shared by N plannings (the shape a SPLIT produces -- every new planning copies
-- M_ReceiptSchedule_ID) legitimately yields N rows, one per planning.
--
-- KEY. RV_ReceiptLogistics_ID is arithmetic and deterministic, never ROW_NUMBER() (a window function
-- blocks predicate push-down, so a filtered open would compute the whole view first):
--   branch one   = M_Delivery_Planning_ID
--   branch two   = 1000000000 + M_ReceiptSchedule_ID
-- Branch one keys on the PLANNING, not on the schedule, precisely because of the split: N plannings
-- share one schedule, so a schedule-derived id would repeat and the window would lose grid identity,
-- selection and zoom. Ceiling: both source ids must stay <= 1,147,483,647 for the sum to fit a Java int.
--
-- WHICH SIDE EACH COLUMN COMES FROM.
--   dates and quantities  -- the planning on branch one (it is the plan of record, the thing the
--                            operator edits and the split divides); the schedule/order on branch two.
--   identity and context  -- the SCHEDULE on BOTH branches, one expression, no CASE. The generate
--                            command copies product, partner, warehouse, order and order line from the
--                            schedule onto the planning at creation, so the two agree by construction;
--                            writing them once removes the class of bug where the two halves of a
--                            column silently drift apart.
--
-- UOM. C_UOM_ID follows the SAME rule as QtyOrdered -- the unit that actually goes with the quantity
-- shown on that branch, not a single expression like identity/context above: the planning's unit on
-- branch one, the schedule's on branch two. Unlike product/partner/warehouse, QtyOrdered is
-- planning-editable after creation, so its unit has to be read from wherever the quantity itself is
-- read, or the two could silently disagree. There is no DB constraint tying M_Delivery_Planning.C_UOM_ID
-- to M_ReceiptSchedule.C_UOM_ID -- on the data behind this view today the two agree on every one of the
-- 67 planned rows (0 disagreements, verified by direct query), because the generate command copies the
-- schedule's unit onto the planning at creation like it does product/partner/warehouse -- but nothing
-- stops a later edit to the planning's unit from breaking that agreement, which is exactly why this
-- column is read per-branch instead of picked once.
--   M_Warehouse_ID is the schedule's plain column, deliberately NOT M_Warehouse_Effective_ID -- the
--   planning stores the plain one, so using the effective one on branch two only would make the
--   column's two halves disagree.
--
-- DATES. Every date column follows one rule -- the planning's value when it has one, the
-- schedule-derived value as the fallback:
--   ETA = COALESCE(planning ETA, schedule DatePromised_Effective).  M_Delivery_Planning.ETA is
--         nullable, so without the fallback a planning created without a date would show an empty
--         arrival on a row whose order promise is perfectly well known.
--   ETD = COALESCE(planning ETD, order PreparationDate) -- Bereitstellungsdatum, the earliest the
--         goods can leave. The receipt schedule has no departure-side date of its own, which is why
--         this fallback comes from the order.
--   ATD = the order's PreparationDate on branch two, which is the very value the generate command
--         prefills into the planning's ATD at creation -- so both branches agree by construction.
--   ATA = read from the receipts themselves on branch two: the EARLIEST movement date over the
--         schedule's completed receipts. That is "when goods first arrived"; the latest would answer
--         a different question and would be a second column, not a redefinition of this one.
--         Three conditions, or it reports something false: the allocation must be active (a reversal
--         deactivates it, and a reversed receipt is not an arrival), the receipt must be completed
--         (a drafted or voided one is not an arrival either), and the aggregate must be min.
--   DatePromised_Effective is shown in its own right on both branches -- the ORDER's promise next to
--         the PLAN. Seeing that a planning says one date while the order promised another is the point.
--
-- COLUMN TYPES. The two sides genuinely differ in the source schema -- M_Delivery_Planning's four
-- date columns are naked "timestamp", while M_ReceiptSchedule.MovementDate/DatePromised_Override and
-- C_Order.PreparationDate are "timestamptz". Postgres therefore resolves ETA/ETD/ATD and
-- DatePromised_Effective to timestamptz and ATA (both sides naked) to timestamp. Both kinds already
-- coexist in delivery-planning windows today and round-trip identically under one session timezone.
--
-- CALENDAR WEEK. CalendarWeek = EXTRACT(week from <the same expression that computes ETA on that
-- branch>) -- not from either bare source column (M_ReceiptSchedule.CalendarWeek does that already,
-- on window 541954, and stays untouched), because ETA itself is a COALESCE and reading a source column
-- directly would let the week disagree with the ETA shown right next to it. EXTRACT(week from ...) is
-- the ISO week (1-53), so a date whose ISO week crosses a year end reports the week of the ISO year
-- it actually belongs to, not the calendar year -- e.g. 2023-01-01 (a Sunday) is week 52 of ISO year
-- 2022, not week 1 of 2023. No separate ISO-year column is added: the grid sorts by ETA (see below),
-- it does not group by week, so a bare week number is unambiguous here.
--
-- DROPSHIP AND OUTGOING are out of scope for this view: branch one is strictly Incoming. Note the
-- consequence, so it is not read as a bug: branch two excludes a schedule that has ANY active
-- planning, so a schedule whose only active planning is a Dropship one appears on NEITHER branch.
--

DROP VIEW IF EXISTS RV_ReceiptLogistics$new
;

CREATE OR REPLACE VIEW RV_ReceiptLogistics$new
AS
-- branch one: planned rows -- one per active Incoming planning carrying a receipt schedule
SELECT dp.m_delivery_planning_id                                             AS RV_ReceiptLogistics_ID,
       rs.m_receiptschedule_id,
       dp.m_delivery_planning_id,
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
       (SELECT string_agg(DISTINCT st.containerno, '; ')
        FROM m_shippertransportation st
                 JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id
          AND st.containerno IS NOT NULL)                                    AS containerno,
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
WHERE dp.isactive = 'Y'
  AND dp.transportdirection = 'Incoming'
  AND dp.m_receiptschedule_id IS NOT NULL

UNION ALL

-- branch two: unplanned rows -- one per receipt schedule no active planning refers to
SELECT 1000000000 + rs.m_receiptschedule_id                AS RV_ReceiptLogistics_ID,
       rs.m_receiptschedule_id,
       NULL::numeric(10)                                   AS m_delivery_planning_id,
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
       (SELECT string_agg(DISTINCT st.containerno, '; ')
        FROM m_shippertransportation st
                 JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id
          AND st.containerno IS NOT NULL)                  AS containerno,
       rs.ad_client_id,
       rs.ad_org_id,
       rs.isactive,
       rs.created,
       rs.createdby,
       rs.updated,
       rs.updatedby
FROM m_receiptschedule rs
         LEFT JOIN c_order o ON o.c_order_id = rs.c_order_id
WHERE NOT EXISTS (SELECT 1
                  FROM m_delivery_planning p
                  WHERE p.m_receiptschedule_id = rs.m_receiptschedule_id
                    AND p.isactive = 'Y')
;

SELECT db_alter_view(
               'rv_receiptlogistics',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('rv_receiptlogistics$new'))
           )
;

DROP VIEW IF EXISTS RV_ReceiptLogistics$new
;
