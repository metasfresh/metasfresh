-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptDisposition_DeliveryPlanning.sql
--
-- Adds the missing IsActive guards on the three transport-order joins, as a safety net:
--
--   branch one   LEFT JOIN m_shippertransportation dpst ...  AND dpst.isactive = 'Y'
--   branch two   JOIN m_shippertransportation st ...         AND st.isactive   = 'Y'
--   branch two   FROM m_shippingpackage sp WHERE ...         AND sp.isactive   = 'Y'
--
-- The receipt-schedule-alloc join in branch two already carried `rsa.isactive = 'Y'`, so this makes the
-- view internally consistent rather than introducing a new convention. The sibling view
-- M_Delivery_Planning_Delivery_Instructions_V guards the same way (`AND dpa.isactive = 'Y'`).
--
-- What it prevents: removing a delivery planning from an instruction deactivates the allocation AND its
-- M_ShippingPackage (DeliveryInstructionService -> afterDeactivation -> deactivateShippingPackages) while
-- LEAVING M_ShipperTransportation_ID on the package intact. An unplanned row would then still read that
-- instruction's ContainerNo / IsBLReceived / IsBookingConfirmed / IsWENotice through the branch-two LATERAL.
-- (The sibling `unlinkDeliveryPlannings` path additionally clears the link, so only the plain deactivation
-- path can produce it.)
--
-- Deliberately NO behaviour test: measured on the local stack 2026-09-10 there are 0 unplanned receipt
-- schedules joining an inactive package (231 inactive packages exist, but all on sales-side order lines with
-- no receipt schedule), so nothing relevant currently rides on unplanned rows. Shipped as a defensive guard
-- on the owner's explicit instruction, not as a bug fix with a reproduction.
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
