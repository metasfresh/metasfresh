--
-- RV_ReceiptDisposition_DeliveryPlanning -- the single inbound list behind the receipt-disposition
-- delivery-planning window: active Incoming delivery plannings (branch one, "planned") UNIONed with the
-- receipt schedules no active planning refers to (branch two, "unplanned"). The two branches are exact
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
-- select them. The shipper is read off the already-joined C_Order (o.m_shipper_id) and the three flags replicate
-- the source ColumnSQL subquery, correlated on rs.c_order_id, on both branches.
--
-- PER-BRANCH vs SHARED. Identity and context (product, partner, warehouse, order, order line) are read off the
-- SCHEDULE on BOTH branches, one expression, no CASE - the generate command copies them onto the planning at
-- creation, so the two agree by construction. Dates, quantities and C_UOM_ID are read per branch, because they
-- stay planning-editable after creation and could otherwise silently disagree with each other.
--   * M_Warehouse_ID is the schedule's PLAIN column, deliberately not M_Warehouse_Effective_ID: the planning
--     stores the plain one, so the effective one would make the column's two halves disagree.
--   * ATA (branch two) is the EARLIEST movement date over the schedule's receipts, and needs all three
--     conditions - allocation active, receipt completed, aggregate min - or it reports something false.
--   * CalendarWeek is EXTRACT(week from <that branch's ETA expression>), not from a bare source column, so the
--     week cannot disagree with the ETA shown beside it. ISO week, so a year-end date reports its ISO year's week.
--
-- OUT OF SCOPE: branch one is strictly Incoming. Consequence, so it is not read as a bug: branch two excludes a
-- schedule that has ANY active planning, so a schedule whose only active planning is a Dropship one appears on
-- NEITHER branch.
--

DROP VIEW IF EXISTS RV_ReceiptDisposition_DeliveryPlanning$new
;

CREATE OR REPLACE VIEW RV_ReceiptDisposition_DeliveryPlanning$new
AS
-- branch one: planned rows -- one per active Incoming planning carrying a receipt schedule
SELECT dp.m_delivery_planning_id                                             AS RV_ReceiptDisposition_DeliveryPlanning_ID,
       rs.m_receiptschedule_id,
       dp.m_delivery_planning_id,
       'Y'::char(1)                                                          AS isplanned,
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
       o.m_shipper_id,
       rs.qtytomove,
       dp.batch,
       rs.isconfirmedbysupplier,
       (SELECT max(st.isblreceived)
        FROM m_shippertransportation st
                 JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id)                                 AS isblreceived,
       (SELECT max(st.isbookingconfirmed)
        FROM m_shippertransportation st
                 JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id)                                 AS isbookingconfirmed,
       (SELECT max(st.iswenotice)
        FROM m_shippertransportation st
                 JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id)                                 AS iswenotice,
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
WHERE dp.isactive = 'Y'
  AND dp.transportdirection = 'Incoming'
  AND dp.m_receiptschedule_id IS NOT NULL

UNION ALL

-- branch two: unplanned rows -- one per receipt schedule no active planning refers to
SELECT 1000000000 + rs.m_receiptschedule_id                AS RV_ReceiptDisposition_DeliveryPlanning_ID,
       rs.m_receiptschedule_id,
       NULL::numeric(10)                                   AS m_delivery_planning_id,
       'N'::char(1)                                         AS isplanned,
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
       o.m_shipper_id,
       rs.qtytomove,
       NULL::character varying(250)                        AS batch,
       rs.isconfirmedbysupplier,
       (SELECT max(st.isblreceived)
        FROM m_shippertransportation st
                 JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id)                AS isblreceived,
       (SELECT max(st.isbookingconfirmed)
        FROM m_shippertransportation st
                 JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id)                AS isbookingconfirmed,
       (SELECT max(st.iswenotice)
        FROM m_shippertransportation st
                 JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id)                AS iswenotice,
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
