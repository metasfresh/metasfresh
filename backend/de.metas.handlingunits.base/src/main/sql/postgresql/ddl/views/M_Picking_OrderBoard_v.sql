DROP VIEW IF EXISTS M_Picking_OrderBoard_v;

CREATE OR REPLACE VIEW M_Picking_OrderBoard_v AS
SELECT
    b.m_product_id,
    prod.value                                AS ProductValue,
    prod.name                                 AS ProductName,
    b.c_uom_id,
    CASE
        WHEN b.isassigned = 'N'                        THEN 'W'
        WHEN b.isassigned = 'Y' AND b.processed = 'N' THEN 'K'
        WHEN b.isassigned = 'Y' AND b.processed = 'Y' THEN 'P'
    END                                       AS OrderBoardStatus,
    CAST(b.deliverydate AS date)              AS DeliveryDate,
    loc.c_country_id,
    ctry.name                                 AS CountryName,
    SUM(COALESCE(b.qtytopick, b.qtytoscheduleforpicking, 0)) AS QtyTotal,
    COUNT(DISTINCT b.m_shipmentschedule_id)   AS OrderLineCount,
    b.ad_client_id,
    b.ad_org_id,
    MAX(b.updated)                            AS updated,
    MAX(b.updatedby)                          AS updatedby,
    MAX(b.created)                            AS created,
    MAX(b.createdby)                          AS createdby,
    'Y'::bpchar                               AS isactive
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
    CASE
        WHEN b.isassigned = 'N'                        THEN 'W'
        WHEN b.isassigned = 'Y' AND b.processed = 'N' THEN 'K'
        WHEN b.isassigned = 'Y' AND b.processed = 'Y' THEN 'P'
    END,
    CAST(b.deliverydate AS date),
    loc.c_country_id,
    ctry.name,
    b.ad_client_id,
    b.ad_org_id
;
