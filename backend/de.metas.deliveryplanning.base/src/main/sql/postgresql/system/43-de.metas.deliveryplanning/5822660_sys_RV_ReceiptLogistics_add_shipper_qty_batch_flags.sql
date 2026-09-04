-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptLogistics.sql
--
-- Adds seven columns the filter task (W6b) needs before it can configure them: m_shipper_id,
-- qtytomove (the open-quantity figure), batch, isconfirmedbysupplier, isblreceived,
-- isbookingconfirmed, iswenotice. Full per-column sourcing rationale is in the DDL header comment
-- above ("SHIPPER" / "QTYTOMOVE" / "BATCH" / "TRANSPORT CONFIRMATION FLAGS").
--
-- Grain/key are unaffected: every new expression either reads an existing scalar column already on
-- one of the two tables this view already selects from (m_delivery_planning.batch,
-- m_receiptschedule.qtytomove/isconfirmedbysupplier), reads through the C_Order alias this view
-- already LEFT JOINs (o.m_shipper_id), or is a correlated scalar subquery collapsed by max() -- the
-- identical shape the view already uses for containerno two lines above. No new join is added to the
-- FROM clause of either branch, so no fan-out is possible.
-- Verified after applying: still 323 rows (same as before this script), still a unique key, and
-- "GROUP BY RV_ReceiptLogistics_ID HAVING count(*) > 1" returns zero rows.

DROP VIEW IF EXISTS RV_ReceiptLogistics$new
;

CREATE OR REPLACE VIEW RV_ReceiptLogistics$new
AS
-- branch one: planned rows -- one per active Incoming planning carrying a receipt schedule
SELECT dp.m_delivery_planning_id                                             AS RV_ReceiptLogistics_ID,
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
