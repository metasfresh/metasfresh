-- Source DDL: backend/de.metas.ui.web.base/src/main/sql/postgresql/ddl/views/M_MaterialCockpit_Base_V.sql
--
-- Material Cockpit v2 performance: M_MaterialCockpit_Base_V now selects from a set-returning SQL function.
--
-- Why: the previous view body computed (a) the attributesKey inside a per-M_AttributeInstance subquery and
-- (b) getLastCostPrice() once per output row. On a large instance the planner also JIT-compiled the whole
-- statement, which cost more than the query itself. The function body below computes the attributesKey once per
-- referenced M_AttributeSetInstance (MATERIALIZED CTE + LATERAL) and the last cost price once per product
-- (MATERIALIZED set-based DISTINCT ON CTE), and runs with JIT disabled.
--
-- The output is identical to the previous view: same columns, same types, same rows, same values.
--   * attributesKey: same encoding as GenerateASIStorageAttributesKeyPart aggregated with the delimiter
--     chr(167)||'&'||chr(167), ordered by M_AttributeValue_ID (NULLs last), M_Attribute_ID; ASIs without any
--     storage-relevant key part fall back to '-1002'; MD_Stock keeps its stored attributeskey.
--   * LastCostPrice: same selection as getLastCostPrice() (purchase orders, DocStatus CO, PriceActual > 0,
--     latest DatePromised, then highest C_OrderLine_ID), falling back to M_Product.SeedCost.
--
-- NOTE: this is the FIRST function in the codebase that uses "SET jit = off". Reason: for this query PostgreSQL
-- estimates a high cost and compiles it with JIT, and the compilation time dominates the run time. The SET clause
-- is attached to the function so that only this query is affected, not the session or the database.
--
-- NOTE: the body of an SQL function is NOT dependency-tracked by PostgreSQL. A change of the return type
-- (M_MaterialCockpit_Base_V_Row) therefore needs "DROP FUNCTION M_MaterialCockpit_Base_V_Fn() CASCADE" (which also
-- drops M_MaterialCockpit_Base_V and the views on top of it) followed by re-creating the function, the view and
-- calling after_migration_M_MaterialCockpit_rebuild().
--
-- ASCII only: the section sign is written as chr(167).

-- 1. Composite type with the exact column list and type modifiers of the previous view
DROP FUNCTION IF EXISTS M_MaterialCockpit_Base_V_Fn()
;

DROP TYPE IF EXISTS M_MaterialCockpit_Base_V_Row
;

CREATE TYPE M_MaterialCockpit_Base_V_Row AS
(
    ad_client_id             numeric(10, 0),
    ad_org_id                numeric(10, 0),
    productname              varchar(600),
    productvalue             varchar(255),
    m_product_id             numeric(10, 0),
    m_product_category_id    numeric(10, 0),
    c_uom_id                 numeric(10, 0),
    attributeskey            text,
    m_warehouse_id           numeric(10, 0),
    qtyreserved              numeric,
    qtytomove                numeric,
    qtytoproduce             numeric,
    qtyforecasted            numeric,
    qtystock                 numeric,
    qtyconfirmedbysupplier   numeric,
    qtyunconfirmedbysupplier numeric,
    qtydemand_qtysupply_v_id integer,
    lastcostprice            numeric
)
;

-- 2. The function
CREATE OR REPLACE FUNCTION M_MaterialCockpit_Base_V_Fn()
    RETURNS SETOF M_MaterialCockpit_Base_V_Row
    LANGUAGE sql
    STABLE
    ROWS 5000
    SET jit = off
AS
$$
WITH asi_ids AS MATERIALIZED (
    SELECT ss.m_attributesetinstance_id AS asi_id FROM m_shipmentschedule ss WHERE ss.qtyReserved <> 0
    UNION SELECT rs.m_attributesetinstance_id FROM m_receiptschedule rs WHERE rs.qtyToMove <> 0
    UNION SELECT poc.m_attributesetinstance_id FROM pp_order_candidate poc WHERE poc.qtyToProcess <> 0
    UNION SELECT fl.m_attributesetinstance_id FROM m_forecastline fl WHERE fl.qty <> 0
),
asi_key AS MATERIALIZED (
    SELECT i.asi_id, k.attributeskey
    FROM asi_ids i
             CROSS JOIN LATERAL (
        SELECT STRING_AGG(parts.keypart, chr(167) || '&' || chr(167) ORDER BY parts.av_id NULLS LAST, parts.attr_id) AS attributeskey
        FROM (SELECT GenerateASIStorageAttributesKeyPart(ai.M_Attribute_ID, a.AttributeValueType, ai.Value,
                                                         ai.ValueNumber, ai.ValueDate, ai.M_AttributeValue_ID) AS keypart,
                     av.M_AttributeValue_ID                                                                    AS av_id,
                     a.M_Attribute_ID                                                                          AS attr_id
              FROM M_AttributeInstance ai
                       JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID
                       LEFT JOIN M_AttributeValue av ON av.M_AttributeValue_ID = ai.M_AttributeValue_ID
              WHERE ai.M_AttributeSetInstance_ID = i.asi_id
                AND a.IsActive = 'Y'
                AND a.IsStorageRelevant = 'Y') parts
        WHERE parts.keypart IS NOT NULL
        ) k
    WHERE k.attributeskey IS NOT NULL
),
lcp AS MATERIALIZED (
    SELECT DISTINCT ON (ol.m_product_id) ol.m_product_id, ol.priceactual
    FROM C_OrderLine ol
             INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
    WHERE o.issotrx = 'N'
      AND o.docstatus = 'CO'
      AND ol.priceactual > 0
    ORDER BY ol.m_product_id, o.DatePromised DESC, ol.C_OrderLine_ID DESC
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
       COALESCE(MAX(lcp.priceactual), p.SeedCost)                                                                 AS LastCostPrice
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
         WHERE ss.qtyReserved <> 0
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
         WHERE rs.qtyToMove <> 0
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
         WHERE poc.qtyToProcess <> 0
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
         WHERE fl.qty <> 0
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
           AND s.qtyOnHand <> 0
         GROUP BY s.ad_client_id, s.ad_org_id, s.m_warehouse_id, s.m_product_id, p.c_uom_id, s.attributeskey) AS t
     ON p.m_product_id = t.m_product_id
         LEFT OUTER JOIN m_warehouse w ON w.m_warehouse_id = t.m_warehouse_id
         LEFT OUTER JOIN lcp ON lcp.m_product_id = p.m_product_id
GROUP BY t.ad_client_id, t.ad_org_id, p.m_product_id, p.m_product_category_id, p.name, p.value, p.c_uom_id, t.attributesKey, t.m_warehouse_id
$$
;

-- 3. The base view selects from the function
DROP VIEW IF EXISTS M_MaterialCockpit_Base_V$new
;

CREATE OR REPLACE VIEW M_MaterialCockpit_Base_V$new AS
SELECT *
FROM M_MaterialCockpit_Base_V_Fn()
;

SELECT db_alter_view(
               'M_MaterialCockpit_Base_V',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('m_materialcockpit_base_v$new'))
       )
;

DROP VIEW IF EXISTS M_MaterialCockpit_Base_V$new
;

-- 4. Re-create the API view QtyDemand_QtySupply_V (and honour a customer override view, if any)
SELECT after_migration_M_MaterialCockpit_rebuild()
;
