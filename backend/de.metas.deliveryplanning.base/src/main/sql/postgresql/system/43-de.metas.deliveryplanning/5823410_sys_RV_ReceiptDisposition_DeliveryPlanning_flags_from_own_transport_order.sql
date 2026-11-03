-- Source DDL: backend/de.metas.deliveryplanning.base/src/main/sql/postgresql/ddl/views/RV_ReceiptDisposition_DeliveryPlanning.sql
--
-- Sources RV_ReceiptDisposition_DeliveryPlanning.IsBLReceived, IsBookingConfirmed and IsWENotice from the ROW's
-- own transport order instead of an order-wide aggregate over every transport order of the order - the same
-- defect and the same fix 5823380 applied to ContainerNo, on the same two branches. 5823380 left the three flags
-- behind and recorded them as deliberately inconsistent; that note is now wrong and is removed from the view
-- header along with this change. Owner ruling 2026-09-09: "simply take containerNo from transportation order for
-- now" - the "for now" defers whether any of these belongs on the planning as its own column, not which columns
-- read the transport order.
--
-- Before, on BOTH branches, each flag was
--   (SELECT max(st.<flag>) FROM m_shippertransportation st
--      JOIN m_shippingpackage sp ON sp.m_shippertransportation_id = st.m_shippertransportation_id
--    WHERE sp.c_order_id = rs.c_order_id)
-- copied from the source columns' own ColumnSQL on M_ReceiptSchedule. max() over 'Y'/'N' is an OR, so every row
-- of an order showed Y as soon as ANY transport order of that order had the flag set. A SPLIT is the case that
-- breaks: the siblings share one receipt schedule but each gets its OWN delivery instruction
-- (generateDeliveryInstructions creates one M_ShipperTransportation per planning), so the two rows can differ in
-- any of these four columns while the aggregate showed the OR on both.
--
-- After:
--   * Branch one (planned) reads dpst.<flag> off the LEFT JOIN on M_Delivery_Planning.M_ShipperTransportation_ID
--     that 5823380 already added for ContainerNo - the transport order's PK, so grain is unchanged and a planning
--     on no instruction reads NULL, which the AD column allows (IsMandatory='N') and the generated model's
--     boolean getter reads as false.
--     Measured on the deep_tundra_release local DB 2026-09-09, before this change's own scenario had run: 117 of
--     the view's 1488 planned rows carry that FK (601 of all 3346 plannings).
--   * Branch two (unplanned) has no planning, so it keeps a subquery, scoped to the schedule's ORDER LINE
--     (sp.c_order_id = rs.c_order_id AND sp.c_orderline_id = rs.c_orderline_id) exactly as 5823380 scoped
--     ContainerNo: that is the narrowing M_ReceiptSchedule.M_ShipperTransportation_ID's own ColumnSQL uses, and
--     it is AddOrderLinesToShipperTransportation (behind M_ReceiptSchedule_AddTo_M_ShipperTransportation) that
--     puts a bare schedule on a transport order, one shipping package per order line. max() is kept so the column
--     stays one value per row on both branches.
--     The narrowing discards nothing: measured on the same DB and at the same point, every unplanned row that
--     reaches a shipping package at all reaches it order-LINE-scoped (13 of 13, of 607 unplanned rows).
--
-- The defect is not observable in this DB's own data: all 672 M_ShipperTransportation rows carry
-- IsBLReceived = IsBookingConfirmed = IsWENotice = 'N' (measured at the same point), so every order-wide OR
-- happens to agree with every row's own transport order. It is proven by S31789_TC15, which puts the two
-- plannings of one split on transport orders with OPPOSED flags. Against the pre-change view that scenario failed
-- with IsBookingConfirmed expected false but was true on the first row; queried directly, the pair read Y/Y/Y on
-- both rows against own values of Y/N/Y and N/Y/N.
--
-- Grain and key are unchanged: no row is added or removed, and branch one adds no join (it reuses 5823380's).
-- Re-verified on the same DB after applying: 2098 rows = 2098 distinct keys, no key twice, 1490 planned + 608
-- unplanned - equal to the 1490 and 608 counted straight off the base tables without the view. Of the planned
-- rows, the 119 that carry a transport order all show its own three flags (0 disagreeing) and the 1371 that carry
-- none all read NULL for all three.
--
-- No model regeneration: the output column list is identical - all 34 columns, same names, same order (diffed
-- information_schema.columns before and after applying). The three flags' PHYSICAL type does not move either:
-- both before and after they are character with NO length, because a UNION of branch one's character(1) with
-- branch two's typmod-less max() result resolves to the unconstrained type (before, both branches were that
-- max()). Contrast 5823380, where ContainerNo did move, text -> character varying. Either way the AD_Column is
-- what the generated model and the WebUI field read - AD_Reference_ID=20 YesNo, FieldLength=1, no ColumnSQL,
-- untouched here.

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
