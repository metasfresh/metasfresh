-- Run mode: SWING_CLIENT

-- Name: M_Material_Needs_Planner_V
-- 2025-10-07T09:26:00.916Z
UPDATE AD_Reference SET Description='M_Material_Needs_Planner_V is a view with a composed key, there is no M_Material_Needs_Planner_V_ID. Please use Warehouse_ID or Product_ID as AD_Key',Updated=TO_TIMESTAMP('2025-10-07 09:26:00.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541982
;

-- 2025-10-07T09:26:00.925Z
UPDATE AD_Reference_Trl trl SET Description='M_Material_Needs_Planner_V is a view with a composed key, there is no M_Material_Needs_Planner_V_ID. Please use Warehouse_ID or Product_ID as AD_Key' WHERE AD_Reference_ID=541982 AND AD_Language='de_DE'
;

-- Reference: M_Material_Needs_Planner_V
-- Table: M_Material_Needs_Planner_V
-- Key: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-10-07T09:26:08.101Z
UPDATE AD_Ref_Table SET AD_Key=589737,Updated=TO_TIMESTAMP('2025-10-07 09:26:08.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541982
;

-- Column: M_Material_Needs_Planner_V.M_Material_Needs_Planner_V_ID
-- 2025-10-07T09:26:40.138Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590905
;

-- 2025-10-07T09:26:40.145Z
DELETE FROM AD_Field WHERE AD_Column_ID=590905
;

-- 2025-10-07T09:26:40.145Z
DELETE FROM AD_Column WHERE AD_Column_ID=590905
;

-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-10-07T09:26:51.694Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-10-07 09:26:51.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589737
;

-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-10-07T09:26:56.900Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-10-07 09:26:56.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589739
;


DROP VIEW IF EXISTS M_Material_Needs_Planner_V
;

CREATE OR REPLACE VIEW M_Material_Needs_Planner_V AS
WITH qty_data AS (SELECT candidate.m_product_id,
                         candidate.m_warehouse_id,

                         -- Last full week (Monday - Sunday)
                         COALESCE(SUM(candidate.qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '7 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE)), 0)
                             AS Total_Qty_One_Week_Ago,

                         -- Two weeks ago
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '14 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '7 days'), 0)
                             AS Total_Qty_Two_Weeks_Ago,

                         -- Three weeks ago
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '21 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '14 days'), 0)
                             AS Total_Qty_Three_Weeks_Ago,

                         -- Four weeks ago
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '28 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '21 days'), 0)
                             AS Total_Qty_Four_Weeks_Ago,

                         -- Five weeks ago
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '35 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '28 days'), 0)
                             AS Total_Qty_Five_Weeks_Ago,

                         -- Six weeks ago
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '42 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '35 days'), 0)
                             AS Total_Qty_Six_Weeks_Ago,

                         -- Average over the last 6 full weeks
                         COALESCE(SUM(qty) FILTER (WHERE candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '42 days'
                             AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE)), 0) / 6.0
                             AS Average_Qty_Last_Six_Weeks

                  FROM md_candidate candidate
                  WHERE candidate.md_candidate_type = 'DEMAND'
                    AND candidate.dateprojected >= DATE_TRUNC('week', CURRENT_DATE) - INTERVAL '42 days'
                    AND candidate.dateprojected < DATE_TRUNC('week', CURRENT_DATE)
                    AND candidate.qty IS NOT NULL
                    AND candidate.qty <> 0

                  GROUP BY candidate.m_product_id,
                           candidate.m_warehouse_id),
     stock_data AS (SELECT m_product_id, m_warehouse_id, SUM(qtyonhand) AS QuantityOnHand FROM md_stock GROUP BY m_product_id, m_warehouse_id),
     replenish_data AS (SELECT m_product_id, m_warehouse_id, level_min, level_max
                        FROM m_replenish
                        WHERE isactive = 'Y'),
     product_warehouse_combinations AS (SELECT DISTINCT m_product_id, m_warehouse_id
                                        FROM qty_data
                                        UNION
                                        SELECT DISTINCT m_product_id, m_warehouse_id
                                        FROM stock_data
                                        UNION
                                        SELECT DISTINCT m_product_id, m_warehouse_id
                                        FROM replenish_data),
     product_hupi AS (SELECT DISTINCT ON (m_product_id) m_product_id,
                                                        m_hu_pi_item_product_id
                      FROM m_hu_pi_item_product
                      WHERE isactive = 'Y'
                      ORDER BY m_product_id, validfrom DESC, m_hu_pi_item_product_id)
SELECT COALESCE(demand.Total_Qty_One_Week_Ago, 0)            AS Total_Qty_One_Week_Ago,
       COALESCE(demand.Total_Qty_Two_Weeks_Ago, 0)           AS Total_Qty_Two_Weeks_Ago,
       COALESCE(demand.Total_Qty_Three_Weeks_Ago, 0)         AS Total_Qty_Three_Weeks_Ago,
       COALESCE(demand.Total_Qty_Four_Weeks_Ago, 0)          AS Total_Qty_Four_Weeks_Ago,
       COALESCE(demand.Total_Qty_Five_Weeks_Ago, 0)          AS Total_Qty_Five_Weeks_Ago,
       COALESCE(demand.Total_Qty_Six_Weeks_Ago, 0)           AS Total_Qty_Six_Weeks_Ago,
       COALESCE(demand.Average_Qty_Last_Six_Weeks, 0)        AS Average_Qty_Last_Six_Weeks,
       COALESCE(stock.QuantityOnHand, 0)                     AS QuantityOnHand,
       COALESCE(replenish.level_min, 0)                      AS Level_Min,
       COALESCE(replenish.level_max, replenish.level_min, 0) AS Level_Max,
       product.m_product_category_id                         AS M_Product_Category_ID,
       product.m_product_id                                  AS M_Product_ID,
       wc.m_warehouse_id                                     AS M_Warehouse_ID,
       hupi.m_hu_pi_item_product_id                          AS M_HU_PI_Item_Product_ID,

       -- Standard columns
       product.AD_Client_ID,
       product.AD_Org_ID,
       product.Created,
       product.CreatedBy,
       product.Updated,
       product.UpdatedBy,
       product.IsActive,
       product.isbom

FROM m_product product
         LEFT JOIN product_warehouse_combinations wc ON wc.m_product_id = product.m_product_id
         LEFT JOIN replenish_data replenish ON replenish.m_product_id = wc.m_product_id
    AND replenish.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN qty_data demand ON demand.m_product_id = wc.m_product_id
    AND demand.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN stock_data stock ON stock.m_product_id = wc.m_product_id
    AND stock.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN product_hupi hupi ON hupi.m_product_id = product.m_product_id
WHERE product.isactive = 'Y'
  AND product.isstocked = 'Y'
;
