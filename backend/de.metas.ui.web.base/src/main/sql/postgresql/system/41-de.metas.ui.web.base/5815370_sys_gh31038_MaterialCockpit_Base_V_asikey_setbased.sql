-- M_MaterialCockpit_Base_V: Base view for Material Cockpit V2
-- 5 data sources: Shipment Schedules, Receipt Schedules, Production Candidates, Forecasts, Current Stock
-- Uses db_alter_view pattern for safe dependency handling.
-- NOTE: QtyDemand_QtySupply_V is intentionally NOT dropped at the top here. It — and its
-- customer-side dependents (e.g. RV_PurchaseCockpit) — are dropped + recreated automatically by the
-- db_alter_view call below and by after_migration_M_MaterialCockpit_rebuild(). A naked
-- "DROP VIEW QtyDemand_QtySupply_V" (no CASCADE) fails on any instance where a dependent view exists,
-- and forcing CASCADE would drop that dependent without recreating it. Verified on a throwaway DB with
-- a dependent view present: db_alter_view captures and recreates the whole chain.

DROP VIEW IF EXISTS M_MaterialCockpit_Base_V$new
;

CREATE OR REPLACE VIEW M_MaterialCockpit_Base_V$new AS
    -- IMPORTANT: PLEASE DO NOT CHANGE THIS VIEW, but
    -- * create a new view called CUS123_MaterialCockpit_V
    -- * run
WITH asi_key AS (
    -- Set-based attributesKey computation, once per ASI, instead of a per-row
    -- generateasistorageattributeskey() call in each of the branches below. Same encoding as
    -- GenerateASIStorageAttributesKeyPart (same filters, delimiter and ordering) => identical output.
    -- Restricted to the ASIs actually referenced by the four branches below (the IN-subquery uses the
    -- same predicates as those branches), so the key is computed once per *referenced* ASI rather than
    -- over all of M_AttributeInstance system-wide (which on a large instance means ~1M function calls).
    SELECT asi_id,
           STRING_AGG(keypart, '§&§' ORDER BY av_id NULLS LAST, attr_id) AS attributeskey
    FROM (
             SELECT ai.M_AttributeSetInstance_ID                                                                AS asi_id,
                    GenerateASIStorageAttributesKeyPart(ai.M_Attribute_ID, a.AttributeValueType, ai.Value,
                                                         ai.ValueNumber, ai.ValueDate, ai.M_AttributeValue_ID)   AS keypart,
                    av.M_AttributeValue_ID                                                                      AS av_id,
                    a.M_Attribute_ID                                                                            AS attr_id
             FROM M_AttributeInstance ai
                      JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID
                      LEFT JOIN M_AttributeValue av ON av.M_AttributeValue_ID = ai.M_AttributeValue_ID
             WHERE a.IsActive = 'Y'
               AND a.IsStorageRelevant = 'Y'
               AND ai.M_AttributeSetInstance_ID IN (
                   SELECT ss.m_attributesetinstance_id  FROM m_shipmentschedule ss WHERE COALESCE(ss.qtyReserved, 0)  <> 0
                   UNION SELECT rs.m_attributesetinstance_id  FROM m_receiptschedule rs  WHERE COALESCE(rs.qtyToMove, 0)    <> 0
                   UNION SELECT poc.m_attributesetinstance_id FROM pp_order_candidate poc WHERE COALESCE(poc.qtyToProcess, 0) <> 0
                   UNION SELECT fl.m_attributesetinstance_id  FROM m_forecastline fl    WHERE COALESCE(fl.qty, 0)         <> 0
               )
         ) parts
    WHERE keypart IS NOT NULL
    GROUP BY asi_id
)
SELECT t.ad_client_id,
       t.ad_org_id,
       p.name                                                                                           AS ProductName,
       p.value                                                                                          AS ProductValue,
       p.m_product_id,
       p.m_product_category_id,
       p.c_uom_id,
       t.attributesKey,
       t.m_warehouse_id,
       SUM(qtyReserved)                                                                                 AS qtyReserved,
       SUM(qtyToMove)                                                                                   AS qtyToMove,
       SUM(qtyToProduce)                                                                                AS qtyToProduce,
       SUM(qtyForecasted)                                                                               AS qtyForecasted,
       SUM(qtyStock)                                                                                    AS qtyStock,
       SUM(qtyConfirmedBySupplier)                                                                      AS qtyConfirmedBySupplier,
       SUM(qtyUnconfirmedBySupplier)                                                                    AS qtyUnconfirmedBySupplier,
       ABS((('x' || SUBSTR(MD5(CONCAT_WS('#',
                                         t.ad_client_id::text,
                                         t.ad_org_id::text,
                                         p.m_product_id::text,
                                         p.c_uom_id::text,
                                         COALESCE(t.attributesKey, '')::text,
                                         COALESCE(t.m_warehouse_id, 0)::text)), 1, 10))::bit(32)::int)) AS QtyDemand_QtySupply_V_ID,
       getLastCostPrice(p.M_Product_ID)                                                                 AS LastCostPrice
FROM m_product p
         INNER JOIN
     (
         -- Shipment Schedules (Demand)
         SELECT ss.ad_client_id,
                ss.ad_org_id,
                ss.m_warehouse_id,
                ss.m_product_id,
                COALESCE(k.attributeskey, '-1002')                            AS attributesKey,
                SUM(ss.qtyReserved)                                           AS qtyReserved,
                0::numeric                                                    AS qtyToMove,
                0::numeric                                                    AS qtyToProduce,
                0::numeric                                                    AS qtyForecasted,
                0::numeric                                                    AS qtyStock,
                0::numeric                                                    AS qtyConfirmedBySupplier,
                0::numeric                                                    AS qtyUnconfirmedBySupplier
         FROM m_shipmentschedule ss
                  INNER JOIN m_product p ON ss.m_product_id = p.m_product_id
                  LEFT JOIN asi_key k ON k.asi_id = ss.m_attributesetinstance_id
         WHERE COALESCE(ss.qtyReserved, 0) <> 0
         GROUP BY ss.ad_client_id, ss.ad_org_id, ss.m_warehouse_id, ss.m_product_id, p.c_uom_id, attributesKey

         UNION ALL

         -- Receipt Schedules (Supply to move)
         SELECT rs.ad_client_id,
                rs.ad_org_id,
                rs.m_warehouse_id,
                rs.m_product_id,
                COALESCE(k.attributeskey, '-1002')                                                                                              AS attributesKey,
                0::numeric                                                                                                                      AS qtyReserved,
                SUM(uomconvert(rs.m_product_id, rs.c_uom_id, p.c_uom_id, rs.qtyToMove))                                                         AS qtyToMove,
                0::numeric                                                                                                                      AS qtyToProduce,
                0::numeric                                                                                                                      AS qtyForecasted,
                0::numeric                                                                                                                      AS qtyStock,
                CASE WHEN rs.IsConfirmedBySupplier = 'Y' THEN SUM(uomconvert(rs.m_product_id, rs.c_uom_id, p.c_uom_id, rs.qtyToMove)) ELSE 0 END AS qtyConfirmedBySupplier,
                CASE WHEN rs.IsConfirmedBySupplier = 'N' THEN SUM(uomconvert(rs.m_product_id, rs.c_uom_id, p.c_uom_id, rs.qtyToMove)) ELSE 0 END AS qtyUnconfirmedBySupplier
         FROM m_receiptschedule rs
                  INNER JOIN m_product p ON rs.m_product_id = p.m_product_id
                  LEFT JOIN asi_key k ON k.asi_id = rs.m_attributesetinstance_id
         WHERE COALESCE(rs.qtyToMove, 0) <> 0
         GROUP BY rs.ad_client_id, rs.ad_org_id, rs.m_warehouse_id, rs.m_product_id, p.c_uom_id, attributesKey, rs.IsConfirmedBySupplier

         UNION ALL

         -- Production Candidates (Qty to Produce)
         SELECT poc.ad_client_id,
                poc.ad_org_id,
                poc.m_warehouse_id,
                poc.m_product_id,
                COALESCE(k.attributeskey, '-1002')                                            AS attributesKey,
                0::numeric                                                                    AS qtyReserved,
                0::numeric                                                                    AS qtyToMove,
                SUM(uomconvert(poc.m_product_id, poc.c_uom_id, p.c_uom_id, poc.qtyToProcess)) AS qtyToProduce,
                0::numeric                                                                    AS qtyForecasted,
                0::numeric                                                                    AS qtyStock,
                0::numeric                                                                    AS qtyConfirmedBySupplier,
                0::numeric                                                                    AS qtyUnconfirmedBySupplier
         FROM pp_order_candidate poc
                  INNER JOIN m_product p ON poc.m_product_id = p.m_product_id
                  LEFT JOIN asi_key k ON k.asi_id = poc.m_attributesetinstance_id
         WHERE COALESCE(poc.qtyToProcess, 0) <> 0
         GROUP BY poc.ad_client_id, poc.ad_org_id, poc.m_warehouse_id, poc.m_product_id, p.c_uom_id, attributesKey

         UNION ALL

         -- Forecasts (Qty Forecasted)
         SELECT f.ad_client_id,
                f.ad_org_id,
                fl.m_warehouse_id                                                 AS m_warehouse_id,
                fl.m_product_id,
                COALESCE(k.attributeskey, '-1002')                                AS attributesKey,
                0::numeric                                                        AS qtyReserved,
                0::numeric                                                        AS qtyToMove,
                0::numeric                                                        AS qtyToProduce,
                SUM(uomconvert(fl.m_product_id, fl.c_uom_id, p.c_uom_id, fl.qty)) AS qtyForecasted,
                0::numeric                                                        AS qtyStock,
                0::numeric                                                        AS qtyConfirmedBySupplier,
                0::numeric                                                        AS qtyUnconfirmedBySupplier
         FROM m_forecastline fl
                  INNER JOIN m_forecast f ON f.m_forecast_id = fl.m_forecast_id
                  INNER JOIN m_product p ON fl.m_product_id = p.m_product_id
                  LEFT JOIN asi_key k ON k.asi_id = fl.m_attributesetinstance_id
         WHERE COALESCE(fl.qty, 0) <> 0
         GROUP BY f.ad_client_id, f.ad_org_id, fl.m_warehouse_id, fl.m_product_id, p.c_uom_id, attributesKey

         UNION ALL

         -- Current Stock (Qty On Hand)
         SELECT s.ad_client_id,
                s.ad_org_id,
                s.m_warehouse_id,
                s.m_product_id,
                s.attributeskey  AS attributesKey,
                0::numeric       AS qtyReserved,
                0::numeric       AS qtyToMove,
                0::numeric       AS qtyToProduce,
                0::numeric       AS qtyForecasted,
                SUM(s.qtyOnHand) AS qtyStock, --already in product UOM
                0::numeric       AS qtyConfirmedBySupplier,
                0::numeric       AS qtyUnconfirmedBySupplier
         FROM md_stock s
                  INNER JOIN m_product p ON s.m_product_id = p.m_product_id
         WHERE s.IsActive = 'Y'
           AND COALESCE(s.qtyOnHand, 0) <> 0
         GROUP BY s.ad_client_id, s.ad_org_id, s.m_warehouse_id, s.m_product_id, p.c_uom_id, s.attributeskey) AS t
     ON p.m_product_id = t.m_product_id
         LEFT OUTER JOIN m_warehouse w ON w.m_warehouse_id = t.m_warehouse_id
GROUP BY t.ad_client_id, t.ad_org_id, p.m_product_id, p.m_product_category_id, p.name, p.value, p.c_uom_id, t.attributesKey, t.m_warehouse_id
;

SELECT db_alter_view(
               'M_MaterialCockpit_Base_V',
               (SELECT view_definition
                FROM information_schema.views
                WHERE LOWER(views.table_name) = LOWER('m_materialcockpit_base_v$new'))
       )
;

DROP VIEW IF EXISTS M_MaterialCockpit_Base_V$new
;


SELECT after_migration_M_MaterialCockpit_rebuild()
;
