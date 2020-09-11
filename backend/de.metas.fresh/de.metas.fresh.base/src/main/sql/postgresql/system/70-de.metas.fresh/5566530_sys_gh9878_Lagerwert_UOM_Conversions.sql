DROP FUNCTION IF EXISTS report.M_Cost_CostPrice_Function(IN p_keydate timestamp with time zone,
    IN p_M_Product_ID numeric(10, 0),
    IN p_M_Warehouse_ID numeric(10, 0),
    IN p_showDetails character varying,
    IN p_ad_language character varying(6),
    IN p_AcctSchema_ID numeric(10, 0),
    IN p_M_CostElement_ID numeric(10, 0),
    IN p_AD_Client_ID numeric(10, 0),
    IN p_AD_Org_ID numeric(10, 0));

CREATE OR REPLACE FUNCTION report.M_Cost_CostPrice_Function(IN p_keydate timestamp with time zone,
                                                            IN p_M_Product_ID numeric(10, 0),
                                                            IN p_M_Warehouse_ID numeric(10, 0),
                                                            IN p_showDetails character varying,
                                                            IN p_ad_language character varying(6),
                                                            IN p_AcctSchema_ID numeric(10, 0),
                                                            IN p_M_CostElement_ID numeric(10, 0),
                                                            IN p_AD_Client_ID numeric(10, 0),
                                                            IN p_AD_Org_ID numeric(10, 0))


    RETURNS TABLE
            (
                combination   character varying,
                description   character varying,
                activity      character varying,
                WarehouseName character varying,
                ProductValue  character varying,
                ProductName   character varying,
                qty           numeric,
                uomsymbol     character varying,
                costprice     numeric,
                TotalAmt      numeric
            )


AS
$$


SELECT vc.combination,
       vc.description,
       a.name                                  AS Activity,
       wh.name                                 AS WarehouseName,
       p.value                                 AS ProductValue,
       COALESCE(pt.Name, p.Name)               AS ProductName,
       qty,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
       CostPrice,
       qty * CostPrice                         AS TotalAmt
FROM (
         SELECT pa.P_Asset_acct                                                          AS C_ValidCombination_ID,
                wh.C_Activity_ID,
                wh.M_Warehouse_ID,
                hus.M_Product_ID,
                hus.C_UOM_ID,
                SUM(uomconvert(hus.m_product_id, hutl.c_uom_id, hus.c_uom_id, hutl.qty)) as qty,
                COALESCE(
                        getCurrentCost(hus.M_Product_ID, p_keydate::date, p_AcctSchema_ID, p_M_CostElement_ID,
                                       p_AD_Client_ID, p_AD_org_ID)
                    , 0::numeric)                                                        AS CostPrice
         FROM M_Warehouse wh
                  LEFT OUTER JOIN M_Locator l ON wh.M_Warehouse_ID = l.M_Warehouse_ID AND l.isActive = 'Y'
                  LEFT OUTER JOIN M_HU_Trx_line hutl ON l.M_Locator_ID = hutl.M_locator_ID AND hutl.isActive = 'Y'
                  LEFT OUTER JOIN M_HU_Item item ON hutl.VHU_Item_ID = item.M_HU_Item_ID AND item.isActive = 'Y'
                  LEFT OUTER JOIN M_HU hu ON item.M_HU_ID = hu.M_HU_ID AND hu.isActive = 'Y'
                  LEFT OUTER JOIN M_HU_Storage hus ON hu.M_HU_ID = hus.M_HU_ID AND hus.isActive = 'Y'
                  LEFT OUTER JOIN M_Product_acct pa ON hus.M_Product_ID = pa.M_Product_ID AND pa.isActive = 'Y'
         WHERE hutl.DateTrx::date <= $1
           AND hutl.huStatus IN ('A', 'S') -- qonly display transactions if status is stocked, A = Active, S = Picked
         GROUP BY pa.P_Asset_acct,
                  wh.C_Activity_ID,
                  wh.M_Warehouse_ID,
                  hus.M_Product_ID,
                  hus.C_UOM_ID,
                  COALESCE(getCurrentCost(hus.M_Product_ID, p_keydate::date, p_AcctSchema_ID, p_M_CostElement_ID,
                                          p_AD_Client_ID, p_AD_org_ID), 0::numeric)
         HAVING first_agg(hutl.huStatus ORDER BY hutl.created DESC) NOT IN ('P', 'D', 'E')
         UNION
         SELECT pa.P_Asset_acct                                                   AS C_ValidCombination_ID,
                wh.C_Activity_ID,
                wh.M_Warehouse_ID,
                p.M_Product_ID,
                p.C_UOM_ID,
                SUM(t.Movementqty)                                                AS qty,
                COALESCE(getCurrentCost(p.M_Product_ID, p_keydate::date, p_AcctSchema_ID, p_M_CostElement_ID,
                                        p_AD_Client_ID, p_AD_org_ID), 0::numeric) AS CostPrice
         FROM M_Warehouse wh
                  LEFT OUTER JOIN M_Locator l ON wh.M_Warehouse_ID = l.M_Warehouse_ID AND l.isActive = 'Y'
                  LEFT OUTER JOIN M_Transaction t ON l.M_Locator_ID = t.M_Locator_ID AND t.isActive = 'Y'
                  LEFT OUTER JOIN M_Product p ON t.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
                  LEFT OUTER JOIN C_UOM uom ON p.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
                  LEFT OUTER JOIN M_Product_acct pa ON p.M_Product_ID = pa.M_Product_ID AND pa.isActive = 'Y'
         WHERE p.M_Product_Category_ID =
               getSysConfigAsNumeric('PackingMaterialProductCategoryID', wh.AD_Client_ID, wh.AD_Org_ID)
           AND t.MovementDate::date <= $1
         GROUP BY pa.P_Asset_acct,
                  wh.C_Activity_ID,
                  wh.M_Warehouse_ID,
                  p.M_Product_ID,
                  p.C_UOM_ID,
                  COALESCE(getCurrentCost(p.M_Product_ID, p_keydate::date, p_AcctSchema_ID, p_M_CostElement_ID,
                                          p_AD_Client_ID, p_AD_org_ID), 0::numeric)
     ) dat
         LEFT OUTER JOIN M_Product p ON dat.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Trl pt
                         ON p.M_Product_ID = pt.M_Product_ID AND pt.ad_language = $5 AND pt.isActive = 'Y'
         LEFT OUTER JOIN M_Warehouse wh ON dat.M_Warehouse_ID = wh.M_Warehouse_ID AND wh.isActive = 'Y'
         LEFT OUTER JOIN C_Activity a ON dat.C_Activity_ID = a.C_Activity_ID AND a.isActive = 'Y'
         LEFT OUTER JOIN C_ValidCombination vc
                         ON dat.C_ValidCombination_ID = vc.C_ValidCombination_ID AND vc.isActive = 'Y'
         LEFT OUTER JOIN C_UOM uom ON dat.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
         LEFT OUTER JOIN C_UOM_Trl uomt
                         ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.ad_language = $5 AND uomt.isActive = 'Y'
WHERE qty != 0
  AND CASE WHEN $2 IS NULL THEN p.M_Product_ID ELSE $2 END = p.M_Product_ID
  AND CASE WHEN $3 IS NULL THEN wh.M_Warehouse_ID ELSE $3 END = wh.M_Warehouse_ID
ORDER BY vc.combination,
         vc.description,
         a.name,
         Wh.name,
         p.value
    ;


$$
    LANGUAGE sql STABLE
;

