/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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
                                        FROM replenish_data)
SELECT COALESCE(demand.Total_Qty_One_Week_Ago, 0)     AS Total_Qty_One_Week_Ago,
       COALESCE(demand.Total_Qty_Two_Weeks_Ago, 0)    AS Total_Qty_Two_Weeks_Ago,
       COALESCE(demand.Total_Qty_Three_Weeks_Ago, 0)  AS Total_Qty_Three_Weeks_Ago,
       COALESCE(demand.Total_Qty_Four_Weeks_Ago, 0)   AS Total_Qty_Four_Weeks_Ago,
       COALESCE(demand.Total_Qty_Five_Weeks_Ago, 0)   AS Total_Qty_Five_Weeks_Ago,
       COALESCE(demand.Total_Qty_Six_Weeks_Ago, 0)    AS Total_Qty_Six_Weeks_Ago,
       COALESCE(demand.Average_Qty_Last_Six_Weeks, 0) AS Average_Qty_Last_Six_Weeks,
       COALESCE(stock.QuantityOnHand, 0)              AS QuantityOnHand,
       COALESCE(replenish.level_min, 0)               AS Level_Min,
       COALESCE(replenish.level_max, 0)               AS Level_Max,
       product.m_product_category_id                  AS M_Product_Category_ID,
       product.m_product_id                           AS M_Product_ID,
       wc.m_warehouse_id                              AS M_Warehouse_ID,

       -- Standard columns
       product.AD_Client_ID,
       product.AD_Org_ID,
       product.Created,
       product.CreatedBy,
       product.Updated,
       product.UpdatedBy,
       product.IsActive

FROM m_product product
         LEFT JOIN product_warehouse_combinations wc ON wc.m_product_id = product.m_product_id
         LEFT JOIN replenish_data replenish ON replenish.m_product_id = wc.m_product_id
    AND replenish.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN qty_data demand ON demand.m_product_id = wc.m_product_id
    AND demand.m_warehouse_id = wc.m_warehouse_id
         LEFT JOIN stock_data stock ON stock.m_product_id = wc.m_product_id
    AND stock.m_warehouse_id = wc.m_warehouse_id
WHERE product.isactive = 'Y'
  AND product.isstocked = 'Y'
;
