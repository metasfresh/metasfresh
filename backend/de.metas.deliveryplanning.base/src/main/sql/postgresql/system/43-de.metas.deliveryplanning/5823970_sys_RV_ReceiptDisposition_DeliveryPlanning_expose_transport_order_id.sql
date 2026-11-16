-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptDisposition_DeliveryPlanning.sql
--
-- Exposes M_ShipperTransportation_ID on the view, so the delivery instruction can carry a relation type into
-- this window (migration 5823980). Until now the transport order was reachable only inside join predicates,
-- never as a column, so nothing could filter the view by it.
--
--   branch one (planned)    dp.m_shippertransportation_id     -- the planning's own transport order
--   branch two (unplanned)  own_st.m_shippertransportation_id -- surfaced from the existing LATERAL
--
-- Branch two takes it through the same max() collapse as the four flags it already reads there. That is
-- well-defined rather than arbitrary: a branch-two row reaches exactly ONE transport order (measured: max 1
-- package and 1 distinct transport per unplanned receipt schedule), so the max() is the defensive collapse of a
-- 1:1 relation, consistent with how ContainerNo / IsBLReceived / IsBookingConfirmed / IsWENotice are taken.
--
-- Technical link column only: no AD_Field and no AD_UI_Element, so it does not appear in the window
-- (metasfresh-designing-windows section 2, Axis A -- internal/technical). Reference 30 and no
-- IsSelectionColumn, matching the view's other id columns (C_Order_ID, M_Delivery_Planning_ID,
-- M_ReceiptSchedule_ID). Reuses the existing AD_Element 540089 "Transport Auftrag" -- no new element.
--
-- Verified before committing: the view builds, row count unchanged at 4954, and the column populates on both
-- branches -- 90 of 1616 unplanned rows (exactly those that have a shipping package) and 326 of 3338 planned.
--
-- The AD_Column is CLONED from the view's existing M_Delivery_Planning_ID column (593474) so it cannot miss one
-- of AD_Column's 40-odd NOT NULL flags, then only the differing fields are overridden.

CREATE TEMP TABLE tmp_new_col AS SELECT * FROM AD_Column WHERE AD_Column_ID = 593474;

UPDATE tmp_new_col
   SET AD_Column_ID       = 593550 /*From ID Server*/,
       ColumnName         = 'M_ShipperTransportation_ID',
       AD_Element_ID      = 540089 /*existing: Transport Auftrag*/,
       Name               = 'Transport Auftrag',
       Description        = NULL,
       Help               = NULL,
       IsSelectionColumn  = 'N',
       Created            = TO_TIMESTAMP('2026-09-10 21:30:00','YYYY-MM-DD HH24:MI:SS'),
       Updated            = TO_TIMESTAMP('2026-09-10 21:30:00','YYYY-MM-DD HH24:MI:SS'),
       CreatedBy          = 100,
       UpdatedBy          = 100
;

INSERT INTO AD_Column SELECT * FROM tmp_new_col
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Column_ID = 593550)
;

DROP TABLE tmp_new_col;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Description,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
  FROM AD_Language l, AD_Column t
 WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593550
   AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
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
