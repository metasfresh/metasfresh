-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptDisposition_DeliveryPlanning.sql
--
-- Sources RV_ReceiptDisposition_DeliveryPlanning.ContainerNo from the ROW's own transport order instead of an
-- order-wide aggregate over every transport order of the order. Owner ruling 2026-09-09: "simply take
-- containerNo from transportation order for now." The issue's acceptance criterion has ContainerNo "shown
-- read-only, sourced through the planning", which an order-wide string_agg is not.
--
-- Before, on BOTH branches, ContainerNo was
--   (SELECT string_agg(DISTINCT st.containerno, '; ') FROM m_shippertransportation st
--      JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
--    WHERE sp.c_order_id = rs.c_order_id AND st.containerno IS NOT NULL)
-- copied from M_ReceiptSchedule.ContainerNo's ColumnSQL. Every row of an order therefore showed a '; '-joined
-- list of every container on that order rather than its own - and a SPLIT is exactly the case that breaks: the
-- siblings share one receipt schedule but each gets its OWN delivery instruction (generateDeliveryInstructions
-- creates one M_ShipperTransportation per planning), so the two rows differ in container and the aggregate
-- showed both on both.
--
-- After:
--   * Branch one (planned) joins the planning's own M_Delivery_Planning.M_ShipperTransportation_ID - a LEFT JOIN
--     on the transport order's PK, so grain is unchanged and a planning on no instruction reads NULL. Measured on
--     the deep_tundra_release local DB 2026-09-09, before this change's own scenario had run: 587 of 3242
--     plannings carry that FK.
--   * Branch two (unplanned) has no planning, so it keeps a subquery, but scoped to the schedule's ORDER LINE
--     (sp.c_order_id = rs.c_order_id AND sp.c_orderline_id = rs.c_orderline_id) - the same narrowing that
--     M_ReceiptSchedule.M_ShipperTransportation_ID's own ColumnSQL uses, and it is
--     AddOrderLinesToShipperTransportation (behind M_ReceiptSchedule_AddTo_M_ShipperTransportation) that puts a
--     bare schedule on a transport order, one shipping package per order line. max() over that scope, as that
--     ColumnSQL does, so the column stays one value per row on both branches; the IS NOT NULL guard is dropped
--     because max() ignores nulls.
--     NULL was the alternative and was rejected: measured on the same DB and at the same point, every unplanned
--     row that reaches a shipping package at all reaches it order-LINE-scoped (6 of 6, of 545 unplanned rows), so
--     the narrowing loses nothing real, while NULL would have thrown those away and contradicted the ruling.
--
-- DELIBERATELY NOT CHANGED, and therefore INCONSISTENT: IsBLReceived, IsBookingConfirmed and IsWENotice still
-- read the same order-wide max() over every transport order of the order, on both branches. The owner scoped this
-- change to ContainerNo; the three flags have the identical defect and the identical fix, one branch-one join and
-- one order-line-scoped subquery away.
--
-- Grain and key are unchanged: no row is added or removed, branch one's added join is a LEFT JOIN on the
-- transport order's PK. Re-verified on the same DB after applying: 2095 rows = 2095 distinct keys, no key twice,
-- 1488 planned + 607 unplanned - equal to the 1488 and 607 counted straight off the base tables without the view -
-- and 0 of the 1488 planned rows show a ContainerNo other than their own transport order's.
--
-- No model regeneration: the output column list is identical - all 34 columns, same names, same order (diffed
-- information_schema.columns before and after applying). ContainerNo's PHYSICAL type does move, from text (what
-- string_agg returned) to character varying (the source column's own type, UNIONed with max()'s text), and that
-- needs no regeneration either: the AD_Column is untouched (AD_Reference_ID=10 String, FieldLength=255, no
-- ColumnSQL) and it, not the view's type, is what the generated model and the WebUI field read - both types map
-- to the same java.lang.String.

DROP VIEW IF EXISTS RV_ReceiptDisposition_DeliveryPlanning$new
;

CREATE OR REPLACE VIEW RV_ReceiptDisposition_DeliveryPlanning$new
AS
-- branch one: planned rows -- one per active incoming-or-dropship planning carrying a receipt schedule
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
       dpst.containerno                                                      AS containerno,
       o.m_shipper_id,
       dp.planneddischargequantity                                           AS planneddischargequantity,
       dp.actualdischargequantity                                            AS actualdischargequantity,
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
