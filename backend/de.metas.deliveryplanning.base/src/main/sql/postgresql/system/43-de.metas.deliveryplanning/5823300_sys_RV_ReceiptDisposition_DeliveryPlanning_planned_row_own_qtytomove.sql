-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptDisposition_DeliveryPlanning.sql
--
-- Fixes QtyToMove on a PLANNED row of RV_ReceiptDisposition_DeliveryPlanning: it now serves the PLANNING's own
-- planned discharge (dp.planneddischargequantity) instead of the receipt SCHEDULE's QtyToMove. Owner ruling
-- (2026-09-09): "this is simply wrong to see full schedule qty."
--
-- Why the schedule's figure is wrong on a planned row: a split copies M_ReceiptSchedule_ID onto every sibling
-- planning, so the schedule carries ONE figure for the whole order line while each planning plans its own share.
-- Reading the schedule therefore shows the same order-line-wide number on every sibling row - and after a partial
-- receipt it shows a number nobody plans to receive at all. Observed on the local stack before this script:
-- schedules 1000018 and 1000019 each carry two active Incoming plannings planning 5 to discharge each while the
-- schedule's own QtyToMove is 0, so the grid showed 0 on rows that plan to receive 5; schedule 1000070 carries
-- three plannings planning 4 / 3 / 3 while the schedule says 10, so all three rows showed 10.
--
-- Branch two keeps rs.qtytomove unchanged: a bare schedule has no planning to ask. QtyOrdered is untouched on
-- both branches (it already reads dp.qtyordered / rs.qtyordered per branch). For Incoming, actual equals planned,
-- so the planned discharge is the meaningful figure on this purchase-only window.
--
-- No AD change: the column, its AD_Column (593501) and its field already exist from 5822660 / 5822670, and the
-- AD_Element it reuses ("Menge zu bewegen" / 542204) still names the quantity the row is about to move. This
-- script is the view body only.
--
-- Grain/key are unaffected: dp.planneddischargequantity is an existing scalar column on m_delivery_planning,
-- a table branch one already selects from - no new join, no new table, no fan-out possible. Verified after
-- applying: 1054 rows, 744 planned + 310 unplanned - identical to the counts measured immediately before this
-- script, and equal to the view's definition (active Incoming plannings carrying a receipt schedule, plus
-- schedules no active planning refers to). Still a unique key, and
-- "GROUP BY RV_ReceiptDisposition_DeliveryPlanning_ID HAVING count(*) > 1" returns zero rows.

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
       dp.planneddischargequantity                                           AS qtytomove,
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
