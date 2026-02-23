DROP VIEW IF EXISTS M_MaterialCockpit_Base_V;

CREATE OR REPLACE VIEW M_MaterialCockpit_Base_V AS

--
-- On-hand stock from md_stock (pre-computed from HU data)
--
SELECT s.ad_client_id,
       s.ad_org_id,
       s.m_product_id,
       p.m_product_category_id,
       p.value                                                                           AS ProductValue,
       p.name                                                                            AS ProductName,
       p.c_uom_id,
       s.m_warehouse_id,
       s.attributeskey                                                                   AS AttributesKey,
       'OH'::varchar(2)                                                                  AS SupplyType,
       NULL::numeric(10, 0)                                                              AS C_BPartner_Vendor_ID,
       now()::timestamp without time zone                                                AS DatePromised,
       s.qtyonhand                                                                       AS QtyOnHand,
       0::numeric                                                                        AS QtyTU,
       0::numeric                                                                        AS QtyLU,
       COALESCE(s.qtyonhand * NULLIF(p.weight, 0), 0)                                   AS WeightNet,
       getLastCostPrice(p.m_product_id)                                                  AS LastCostPrice,
       ABS((('x' || SUBSTR(MD5(CONCAT_WS('#',
                                          'OH',
                                          s.ad_client_id::text,
                                          s.ad_org_id::text,
                                          s.m_product_id::text,
                                          COALESCE(s.attributeskey, '')::text,
                                          COALESCE(s.m_warehouse_id, 0)::text)), 1, 10))::bit(32)::int)) AS QtyDemand_QtySupply_V_ID
FROM md_stock s
         INNER JOIN m_product p ON s.m_product_id = p.m_product_id
WHERE s.isactive = 'Y'
  AND COALESCE(s.qtyonhand, 0) <> 0

UNION ALL

--
-- Planned supply from M_ReceiptSchedule (open receipts)
--
SELECT rs.ad_client_id,
       rs.ad_org_id,
       rs.m_product_id,
       p.m_product_category_id,
       p.value                                                                            AS ProductValue,
       p.name                                                                             AS ProductName,
       p.c_uom_id,
       rs.m_warehouse_id,
       generateasistorageattributeskey(rs.m_attributesetinstance_id)                      AS AttributesKey,
       'PS'::varchar(2)                                                                   AS SupplyType,
       rs.c_bpartner_id                                                                   AS C_BPartner_Vendor_ID,
       rs.datepromised::timestamp without time zone                                       AS DatePromised,
       SUM(uomconvert(rs.m_product_id, rs.c_uom_id, p.c_uom_id, rs.qtytomove))          AS QtyOnHand,
       0::numeric                                                                         AS QtyTU,
       0::numeric                                                                         AS QtyLU,
       COALESCE(SUM(uomconvert(rs.m_product_id, rs.c_uom_id, p.c_uom_id, rs.qtytomove) * NULLIF(p.weight, 0)), 0) AS WeightNet,
       getLastCostPrice(p.m_product_id)                                                   AS LastCostPrice,
       ABS((('x' || SUBSTR(MD5(CONCAT_WS('#',
                                          'PS',
                                          rs.ad_client_id::text,
                                          rs.ad_org_id::text,
                                          rs.m_product_id::text,
                                          COALESCE(generateasistorageattributeskey(rs.m_attributesetinstance_id), '')::text,
                                          COALESCE(rs.m_warehouse_id, 0)::text,
                                          COALESCE(rs.c_bpartner_id, 0)::text,
                                          COALESCE(rs.datepromised::text, ''))), 1, 10))::bit(32)::int)) AS QtyDemand_QtySupply_V_ID
FROM m_receiptschedule rs
         INNER JOIN m_product p ON rs.m_product_id = p.m_product_id
WHERE rs.processed = 'N'
  AND rs.isactive = 'Y'
  AND COALESCE(rs.qtytomove, 0) <> 0
GROUP BY rs.ad_client_id, rs.ad_org_id, rs.m_product_id, p.m_product_category_id,
         p.value, p.name, p.c_uom_id, rs.m_warehouse_id,
         generateasistorageattributeskey(rs.m_attributesetinstance_id),
         rs.c_bpartner_id, rs.datepromised, p.weight, p.m_product_id
;
