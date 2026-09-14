-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptDisposition_DeliveryPlanning.sql
--
-- Restores a QtyToMove column to RV_ReceiptDisposition_DeliveryPlanning - "what still needs to move", zero
-- once the row is done - and repoints the unplanned branch's PlannedDischargeQuantity at rs.qtyordered.
-- Owner rulings (2026-09-09).
--
-- The domain rule behind both: a planning is exactly ONE receipt. DeliveryPlanningService's
-- getReceiveRejectionReason rejects a second one, and the receive marks the planning Processed, so a
-- shortfall does NOT stay open on the planning - the remainder needs a NEW planning, because it will be a new
-- transport. Owner: "still to move on planning itself is 0 as 2nd receipt isn't allowed, new planning needs to
-- be created for remainder as it will be new transport".
--
-- Ruling A - QtyToMove, per branch from its own entity, agreeing in MEANING rather than in formula:
--   * planned branch: 0 when the planning is Processed, otherwise dp.planneddischargequantity.
--   * unplanned branch: M_ReceiptSchedule.QtyToMove AS IT STANDS. Owner: "schedule has this column already
--     afair don't compute there". It is a STORED column the schedule maintains, and it is NOT
--     ordered-minus-moved: measured on this stack, 112 of 3370 schedules violate that arithmetic and every one
--     of them is IsClosed='Y' with QtyToMove 0 - the schedule forces the remainder to zero when closed, which
--     the arithmetic does not know. Computing it here would show a non-zero remainder on 3.3% of rows.
--   That is the same "done implies 0" rule on both sides, each entity expressing it for itself - which is why
--   neither branch imposes a formula on the other.
--
-- Ruling B - the unplanned branch's PlannedDischargeQuantity becomes rs.qtyordered; ActualDischargeQuantity
-- stays rs.qtymoved. It served rs.qtytomove, i.e. a REMAINING figure under a plan caption - the mislabelling
-- Ruling A's new column removes. A bare schedule has no planning to ask, and what was planned for it is what
-- was ordered.
--
-- Grain/key are unaffected: qtytomove, qtyordered and processed are existing scalar columns on
-- m_delivery_planning and m_receiptschedule, tables both branches already select from - no new join, no new
-- table, no fan-out possible. 5823380 and 5823410 established this two-branch-per-column pattern.
--
-- The AD metadata for the new column - AD_Column / AD_Field / AD_UI_Element, reusing the existing AD_Element
-- 542204 - is the next script, 5823600.

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
       (SELECT max(st.containerno)
        FROM m_shippingpackage sp
                 JOIN m_shippertransportation st ON st.m_shippertransportation_id = sp.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id
          AND sp.c_orderline_id = rs.c_orderline_id)       AS containerno,
       o.m_shipper_id,
       rs.qtytomove                                        AS qtytomove,
       rs.qtyordered                                       AS planneddischargequantity,
       rs.qtymoved                                         AS actualdischargequantity,
       NULL::character varying(250)                        AS batch,
       rs.isconfirmedbysupplier,
       (SELECT max(st.isblreceived)
        FROM m_shippingpackage sp
                 JOIN m_shippertransportation st ON st.m_shippertransportation_id = sp.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id
          AND sp.c_orderline_id = rs.c_orderline_id)       AS isblreceived,
       (SELECT max(st.isbookingconfirmed)
        FROM m_shippingpackage sp
                 JOIN m_shippertransportation st ON st.m_shippertransportation_id = sp.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id
          AND sp.c_orderline_id = rs.c_orderline_id)       AS isbookingconfirmed,
       (SELECT max(st.iswenotice)
        FROM m_shippingpackage sp
                 JOIN m_shippertransportation st ON st.m_shippertransportation_id = sp.m_shippertransportation_id
        WHERE sp.c_order_id = rs.c_order_id
          AND sp.c_orderline_id = rs.c_orderline_id)       AS iswenotice,
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
