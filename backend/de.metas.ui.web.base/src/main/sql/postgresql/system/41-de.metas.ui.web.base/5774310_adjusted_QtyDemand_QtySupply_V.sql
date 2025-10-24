DROP VIEW IF EXISTS QtyDemand_QtySupply_V
;

CREATE OR REPLACE VIEW QtyDemand_QtySupply_V AS

SELECT t.ad_client_id,
       t.ad_org_id,
       p.name                                                                                                                  AS ProductName,
       p.value                                                                                                                 AS ProductValue,
       p.m_product_id,
       p.m_product_category_id,
       p.c_uom_id,
       t.attributesKey,
       t.m_warehouse_id,
       SUM(qtyReserved)                                                                                                        AS qtyReserved,
       SUM(qtyToMove)                                                                                                          AS qtyToMove,
       SUM(qtyToProduce)                                                                                                       AS qtyToProduce,
       SUM(qtyForecasted)                                                                                                      AS qtyForecasted,
       SUM(qtyStock)                                                                                                           AS qtyStock,
       abs((('x' || substr(md5(concat_ws('#',
                                      t.ad_client_id::text,
                                      t.ad_org_id::text,
                                      p.m_product_id::text,
                                      p.c_uom_id::text,
                                      coalesce(t.attributesKey, '')::text,
                                      coalesce(t.m_warehouse_id, 0)::text)), 1, 8))::bit(32)::int)) AS QtyDemand_QtySupply_V_ID
FROM m_product p
         INNER JOIN
     (
         -- Shipment Schedules (Demand)
         SELECT ss.ad_client_id,
                ss.ad_org_id,
                ss.m_warehouse_id,
                ss.m_product_id,
                generateasistorageattributeskey(ss.m_attributesetinstance_id) AS attributesKey,
                SUM(ss.qtyReserved)                                           AS qtyReserved,
                0::numeric                                                    AS qtyToMove,
                0::numeric                                                    AS qtyToProduce,
                0::numeric                                                    AS qtyForecasted,
                0::numeric                                                    AS qtyStock
         FROM m_shipmentschedule ss
                  INNER JOIN m_product p ON ss.m_product_id = p.m_product_id
         WHERE COALESCE(ss.qtyReserved, 0) <> 0
         GROUP BY ss.ad_client_id, ss.ad_org_id, ss.m_warehouse_id, ss.m_product_id, p.c_uom_id, attributesKey

         UNION ALL

         -- Receipt Schedules (Supply to move)
         SELECT rs.ad_client_id,
                rs.ad_org_id,
                rs.m_warehouse_id,
                rs.m_product_id,
                generateasistorageattributeskey(rs.m_attributesetinstance_id)           AS attributesKey,
                0::numeric                                                              AS qtyReserved,
                SUM(uomconvert(rs.m_product_id, rs.c_uom_id, p.c_uom_id, rs.qtyToMove)) AS qtyToMove,
                0::numeric                                                              AS qtyToProduce,
                0::numeric                                                              AS qtyForecasted,
                0::numeric                                                              AS qtyStock
         FROM m_receiptschedule rs
                  INNER JOIN m_product p ON rs.m_product_id = p.m_product_id
         WHERE COALESCE(rs.qtyToMove, 0) <> 0
         GROUP BY rs.ad_client_id, rs.ad_org_id, rs.m_warehouse_id, rs.m_product_id, p.c_uom_id, attributesKey

         UNION ALL

         -- Production Candidates (Qty to Produce)
         SELECT poc.ad_client_id,
                poc.ad_org_id,
                poc.m_warehouse_id,
                poc.m_product_id,
                generateasistorageattributeskey(poc.m_attributesetinstance_id)                AS attributesKey,
                0::numeric                                                                    AS qtyReserved,
                0::numeric                                                                    AS qtyToMove,
                SUM(uomconvert(poc.m_product_id, poc.c_uom_id, p.c_uom_id, poc.qtyToProcess)) AS qtyToProduce,
                0::numeric                                                                    AS qtyForecasted,
                0::numeric                                                                    AS qtyStock
         FROM pp_order_candidate poc
                  INNER JOIN m_product p ON poc.m_product_id = p.m_product_id
         WHERE COALESCE(poc.qtyToProcess, 0) <> 0
         GROUP BY poc.ad_client_id, poc.ad_org_id, poc.m_warehouse_id, poc.m_product_id, p.c_uom_id, attributesKey

         UNION ALL

         -- Forecasts (Qty Forecasted)
         SELECT f.ad_client_id,
                f.ad_org_id,
                fl.m_warehouse_id                                                 AS m_warehouse_id,
                fl.m_product_id,
                generateasistorageattributeskey(fl.m_attributesetinstance_id)     AS attributesKey,
                0::numeric                                                        AS qtyReserved,
                0::numeric                                                        AS qtyToMove,
                0::numeric                                                        AS qtyToProduce,
                SUM(uomconvert(fl.m_product_id, fl.c_uom_id, p.c_uom_id, fl.qty)) AS qtyForecasted,
                0::numeric                                                        AS qtyStock
         FROM m_forecastline fl
                  INNER JOIN m_forecast f ON f.m_forecast_id = fl.m_forecast_id
                  INNER JOIN m_product p ON fl.m_product_id = p.m_product_id
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
                SUM(s.qtyOnHand) AS qtyStock --already in product UOM
         FROM md_stock s
                  INNER JOIN m_product p ON s.m_product_id = p.m_product_id
         WHERE s.IsActive = 'Y'
           AND COALESCE(s.qtyOnHand, 0) <> 0
         GROUP BY s.ad_client_id, s.ad_org_id, s.m_warehouse_id, s.m_product_id, p.c_uom_id, s.attributeskey) AS t
     ON p.m_product_id = t.m_product_id
         LEFT OUTER JOIN m_warehouse w ON w.m_warehouse_id = t.m_warehouse_id
GROUP BY t.ad_client_id, t.ad_org_id, p.m_product_id, p.m_product_category_id, p.name, p.value, p.c_uom_id, t.attributesKey, t.m_warehouse_id
;

-- default grouping by t.ad_client_id, t.ad_org_id, p.m_product_id, p.m_product_category_id, p.name, p.value, t.c_uom_id, t.attributesKey, t.m_warehouse_id
-- grouping can be adjusted as needed
