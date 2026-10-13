-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptDisposition_DeliveryPlanning.sql
--
-- Adds RV_ReceiptDisposition_DeliveryPlanning.IsReadyForReceipt - the readiness status that tells the rows a
-- logistics worker can act on from the rows a planner is still arranging, so the grid can filter to the
-- actionable ones.
--
-- The two branches are NOT one expression, which is the whole point of the column:
--   * Branch one (planned) reads the planning's own stored M_Delivery_Planning.IsReadyForReceipt (5823480),
--     which is 'Y' only once the planning is allocated to a COMPLETED delivery instruction. Allocation alone
--     is not enough - Add-to and Move-to both refuse a non-draft target, so every allocation begins on a
--     draft.
--   * Branch two (unplanned) is the literal 'Y'::char(1). A receipt schedule no planning refers to has
--     nothing being arranged and nothing to wait on. NULL was rejected here: it would read as "unknown" and
--     drop every unplanned row out of a filter asking for the actionable ones - the opposite of the rule.
--
-- Same two-branch shape as PlannedDischargeQuantity (5823310): a column whose planned half comes off the
-- planning and whose unplanned half is supplied by the branch itself, never one CASE over both.
--
-- Grain is unchanged: the added expression touches no join. Branch one reads a column of the already-joined
-- m_delivery_planning, branch two a constant.
--
-- The AD metadata (AD_Column, AD_Field, AD_UI_Element, and the column's place in the window's selection
-- filters) is 5823500.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_MigrationScript 5823490 (this file)

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
       rs.qtytomove                                        AS planneddischargequantity,
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
