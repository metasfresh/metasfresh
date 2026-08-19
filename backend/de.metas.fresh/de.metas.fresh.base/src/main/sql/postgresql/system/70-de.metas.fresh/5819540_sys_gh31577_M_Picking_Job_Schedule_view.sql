DROP VIEW IF EXISTS M_Picking_OrderBoard_v
;

DROP VIEW IF EXISTS M_Picking_OrderBoard_Overview_v
;

DROP VIEW IF EXISTS M_Picking_Job_Schedule_view
;

CREATE OR REPLACE VIEW M_Picking_Job_Schedule_view AS
WITH base_schedule AS (SELECT s.m_shipmentschedule_id,

                              s.c_bpartner_customer_id,
                              s.c_bpartner_location_id,
                              s.bpartnerlocationname,
                              s.bpartneraddress_override,
                              s.c_orderso_id,
                              s.poreference,
                              s.handover_partner_id,
                              s.handover_location_id,
                              s.setup_place_no,
                              s.dateordered,
                              s.c_orderlineso_id,
                              s.linenetamt,
                              s.c_currency_id,
                              s.m_warehouse_id,
                              s.preparationdate,
                              s.m_product_id,
                              s.c_uom_id,
                              s.m_attributesetinstance_id,
                              s.qtyordered,
                              s.qtytodeliver,
                              s.qtydelivered,
                              s.qtypickedanddelivered,
                              s.qtypickednotdelivered,
                              s.qtypickedplanned,
                              s.qtypickedordelivered,
                              s.qtyonhand,
                              s.qtyscheduledforpicking,
                              s.qtytodeliver - COALESCE(s.qtyscheduledforpicking, 0) AS QtyToScheduleForPicking,
                              s.qtyscheduledforpickingofprocessed,

                              s.deliveryviarule,
                              s.deliverydate,
                              s.priorityrule,
                              s.iscatchweight,
                              s.catch_uom_id,
                              s.m_shipper_id,
                              s.packto_hu_pi_item_product_id,
                              s.datepromised,
                              s.isfixeddatepromised,
                              s.isfixedpreparationdate,

                              s.ad_client_id,
                              s.ad_org_id,
                              s.created,
                              s.createdby,
                              s.updated,
                              s.updatedby,
                              s.isactive,

                              s.carrier_advising_status,
                              s.carrier_product_id,
                              s.carrier_goods_type_id,
                              (SELECT c_doctype_id FROM c_order WHERE c_order_id = s.c_orderso_id) AS c_doctype_id,
                              s.c_country_id,
                              s.weight
                       FROM m_packageable_v s
                       WHERE s.carrier_product_id > 0
                          OR get_sysconfig_value('de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet') = 'N')

-- Real picking job rows
SELECT b.*,
       j.m_picking_job_schedule_id, -- Composed Key together with m_shipmentschedule_id
       j.qtytopick,
       (SELECT COALESCE(SUM(sqp.qtypicked), 0)
        FROM m_shipmentschedule_qtypicked sqp
        WHERE sqp.m_picking_job_schedule_id = j.m_picking_job_schedule_id
          AND sqp.processed = 'Y') AS qtypicked,
       j.c_workplace_id,
       j.processed,
       'N'                         AS isreschedule,
       'Y'                         AS isassigned
FROM base_schedule b
         JOIN M_Picking_Job_Schedule j ON j.m_shipmentschedule_id = b.m_shipmentschedule_id

UNION ALL

-- Synthetic "to be scheduled" row
SELECT b.*,
       0    AS m_picking_job_schedule_id, -- Composed Key together with m_shipmentschedule_id
       NULL AS qtytopick,
       NULL AS qtypicked,
       NULL AS c_workplace_id,
       'N'  AS processed,
       CASE
           WHEN (b.qtydelivered <> b.qtyscheduledforpickingofprocessed) THEN 'Y'
                                                                        ELSE 'N'
       END  AS isreschedule,
       'N'  AS isassigned
FROM base_schedule b
WHERE b.qtytoscheduleforpicking > 0
;

-----

CREATE OR REPLACE VIEW M_Picking_OrderBoard_v AS
SELECT
    (('x' || substr(md5(
                            b.m_product_id::text || '_' ||
                            b.c_uom_id::text || '_' ||
                            COALESCE(b.isassigned, '') || '_' ||
                            COALESCE(b.processed, '') || '_' ||
                            COALESCE(CAST(b.deliverydate AS date)::text, '') || '_' ||
                            loc.c_country_id::text || '_' ||
                            b.ad_client_id::text || '_' ||
                            b.ad_org_id::text
                    ), 1, 8))::bit(32) & x'7fffffff'::bit(32))::int AS M_Picking_OrderBoard_v_ID,
    (('x' || substr(md5(
                            b.m_product_id::text || '_' ||
                            b.c_uom_id::text || '_' ||
                            COALESCE(CAST(b.deliverydate AS date)::text, '') || '_' ||
                            loc.c_country_id::text || '_' ||
                            b.ad_client_id::text || '_' ||
                            b.ad_org_id::text
                    ), 1, 8))::bit(32) & x'7fffffff'::bit(32))::int AS M_Picking_OrderBoard_Overview_v_ID,
    b.m_product_id,
    prod.value                                      AS ProductValue,
    prod.name                                       AS ProductName,
    b.c_uom_id,
    CASE
        WHEN b.isassigned = 'N'                        THEN 'W'
        WHEN b.isassigned = 'Y' AND b.processed = 'N' THEN 'K'
        WHEN b.isassigned = 'Y' AND b.processed = 'Y' THEN 'P'
                                                       ELSE NULL
    END                                             AS OrderBoardStatus,
    CAST(b.deliverydate AS date)                    AS DeliveryDate,
    loc.c_country_id,
    ctry.name                                       AS CountryName,
    SUM(COALESCE(b.qtytopick, b.qtytoscheduleforpicking, 0)) AS QtyTotal,
    COUNT(DISTINCT b.m_shipmentschedule_id)         AS OrderLineCount,
    b.ad_client_id,
    b.ad_org_id,
    MAX(b.updated)                                  AS updated,
    MAX(b.updatedby)                                AS updatedby,
    MAX(b.created)                                  AS created,
    MAX(b.createdby)                                AS createdby,
    'Y'::bpchar                                     AS isactive
FROM m_picking_job_schedule_view b
         JOIN m_product prod ON prod.m_product_id = b.m_product_id
         JOIN c_bpartner_location bl ON bl.c_bpartner_location_id = b.c_bpartner_location_id
         JOIN c_location loc ON loc.c_location_id = bl.c_location_id
         JOIN c_country ctry ON ctry.c_country_id = loc.c_country_id
WHERE
    b.isassigned = 'Y'
   OR b.qtyonhand > 0
GROUP BY
    b.m_product_id,
    prod.value,
    prod.name,
    b.c_uom_id,
    b.isassigned,
    b.processed,
    CAST(b.deliverydate AS date),
    loc.c_country_id,
    ctry.name,
    b.ad_client_id,
    b.ad_org_id
;

-----------


-- Overview aggregation of the Order Board.
-- Groups by product/UOM/delivery-date/country (no status dimension).
-- PK hash excludes isassigned/processed so it matches the FK column in M_Picking_OrderBoard_v.
-- QtyWaiting / QtyPicking / QtyPacking break out the three status buckets.
CREATE OR REPLACE VIEW M_Picking_OrderBoard_Overview_v AS
SELECT
    (('x' || substr(md5(
                            b.m_product_id::text || '_' ||
                            b.c_uom_id::text || '_' ||
                            COALESCE(CAST(b.deliverydate AS date)::text, '') || '_' ||
                            loc.c_country_id::text || '_' ||
                            b.ad_client_id::text || '_' ||
                            b.ad_org_id::text
                    ), 1, 8))::bit(32) & x'7fffffff'::bit(32))::int     AS M_Picking_OrderBoard_Overview_v_ID,
    b.m_product_id,
    prod.value                                            AS ProductValue,
    prod.name                                             AS ProductName,
    b.c_uom_id,
    CAST(b.deliverydate AS date)                          AS DeliveryDate,
    loc.c_country_id,
    ctry.name                                             AS CountryName,
    SUM(CASE WHEN b.isassigned = 'N'
                 THEN COALESCE(b.qtytopick, b.qtytoscheduleforpicking, 0) ELSE 0 END) AS QtyWaiting,
    SUM(CASE WHEN b.isassigned = 'Y' AND b.processed = 'N'
                 THEN COALESCE(b.qtytopick, b.qtytoscheduleforpicking, 0) ELSE 0 END) AS QtyPicking,
    SUM(CASE WHEN b.isassigned = 'Y' AND b.processed = 'Y'
                 THEN COALESCE(b.qtytopick, b.qtytoscheduleforpicking, 0) ELSE 0 END) AS QtyPacking,
    SUM(COALESCE(b.qtytopick, b.qtytoscheduleforpicking, 0)) AS QtyTotal,
    COUNT(DISTINCT b.m_shipmentschedule_id)               AS OrderLineCount,
    b.ad_client_id,
    b.ad_org_id,
    MAX(b.updated)                                        AS updated,
    MAX(b.updatedby)                                      AS updatedby,
    MAX(b.created)                                        AS created,
    MAX(b.createdby)                                      AS createdby,
    'Y'::bpchar                                           AS isactive
FROM m_picking_job_schedule_view b
         JOIN m_product prod ON prod.m_product_id = b.m_product_id
         JOIN c_bpartner_location bl ON bl.c_bpartner_location_id = b.c_bpartner_location_id
         JOIN c_location loc ON loc.c_location_id = bl.c_location_id
         JOIN c_country ctry ON ctry.c_country_id = loc.c_country_id
WHERE
    b.isassigned = 'Y'
   OR b.qtyonhand > 0
GROUP BY
    b.m_product_id,
    prod.value,
    prod.name,
    b.c_uom_id,
    CAST(b.deliverydate AS date),
    loc.c_country_id,
    ctry.name,
    b.ad_client_id,
    b.ad_org_id
;
