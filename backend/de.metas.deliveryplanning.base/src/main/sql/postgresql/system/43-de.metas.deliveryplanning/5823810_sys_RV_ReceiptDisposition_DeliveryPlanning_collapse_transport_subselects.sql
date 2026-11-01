-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptDisposition_DeliveryPlanning.sql
--
-- Collapses branch two's four correlated subselects over the row's own transport order into ONE
-- LEFT JOIN LATERAL. They differed only in the aggregated column -- ContainerNo, IsBLReceived,
-- IsBookingConfirmed, IsWENotice -- over the identical source, join and predicate, so each of the four
-- re-scanned the very same rows once per unplanned row. Behaviour is unchanged; this is common-subexpression
-- elimination, not a semantics change.
--
-- Why it matters: the four subselects put essentially the whole view's cost in branch two, and pushed the
-- total estimate past jit_above_cost, so every page render paid a JIT compilation that dominated the
-- response. Measured on the local stack 2026-09-10:
--
--   branch one (planned)   cost   1 307   -- already cheap, untouched
--   branch two (unplanned) cost 132 956   -- SubPlan 1/2 as correlated Aggregates, 1616 loops each
--
--   whole view BEFORE  cost 134 280   exec 156.0 ms   of which JIT 112.2 ms
--   whole view AFTER   cost  57 794   exec  30.0 ms   of which JIT   0.0 ms
--
-- jit_above_cost is 100000, so the rewrite takes the estimate under the threshold and the JIT pass stops
-- firing at all -- that is 112 of the 156 ms. The remaining gain is the three redundant scans going away.
--
-- Output equivalence PROVEN, not assumed, before this script was written: the rewritten body was created as a
-- scratch view beside the live one and compared row-for-row over every column in both directions --
-- 4954 rows each side, `EXCEPT` empty both ways. A narrower check over just the four columns compared all
-- 4155 receipt schedules with 0 differing.
--
-- Only branch two changes. Branch one keeps reading the flags off its existing LEFT JOIN on
-- M_Delivery_Planning.M_ShipperTransportation_ID, which 5823380/5823410 established as the correct grain.

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
       own_st.containerno       AS containerno,
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
         LEFT JOIN LATERAL (SELECT max(st.containerno)         AS containerno,
                                   max(st.isblreceived)        AS isblreceived,
                                   max(st.isbookingconfirmed)  AS isbookingconfirmed,
                                   max(st.iswenotice)          AS iswenotice
                            FROM m_shippingpackage sp
                                     JOIN m_shippertransportation st
                                          ON st.m_shippertransportation_id = sp.m_shippertransportation_id
                            WHERE sp.c_order_id = rs.c_order_id
                              AND sp.c_orderline_id = rs.c_orderline_id) own_st ON TRUE
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
