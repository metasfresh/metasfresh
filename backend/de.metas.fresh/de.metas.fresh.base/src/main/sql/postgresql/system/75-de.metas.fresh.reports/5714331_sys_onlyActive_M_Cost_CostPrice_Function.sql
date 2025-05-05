DROP FUNCTION IF EXISTS report.M_Cost_CostPrice_Function(IN p_keydate            timestamp WITH TIME ZONE,
                                                         IN p_M_Product_ID       numeric(10, 0),
                                                         IN p_M_Warehouse_ID     numeric(10, 0),
                                                         IN p_showDetails        character varying,
                                                         IN p_onlyActiveProducts character varying,
                                                         IN p_ad_language        character varying(6),
                                                         IN p_AcctSchema_ID      numeric(10, 0),
                                                         IN p_AD_Client_ID       numeric(10, 0),
                                                         IN p_AD_Org_ID          numeric(10, 0))
;

DROP FUNCTION IF EXISTS report.M_Cost_CostPrice_Function(IN p_keydate          timestamp WITH TIME ZONE,
                                                         IN p_M_Product_ID     numeric(10, 0),
                                                         IN p_M_Warehouse_ID   numeric(10, 0),
                                                         IN p_showDetails      character varying,
                                                         IN p_ad_language      character varying(6),
                                                         IN p_AcctSchema_ID    numeric(10, 0),
                                                         IN p_M_CostElement_ID numeric(10, 0),
                                                         IN p_AD_Client_ID     numeric(10, 0),
                                                         IN p_AD_Org_ID        numeric(10, 0))
;


DROP FUNCTION IF EXISTS report.M_Cost_CostPrice_Function(IN p_keydate        timestamp WITH TIME ZONE,
                                                         IN p_M_Product_ID   numeric(10, 0),
                                                         IN p_M_Warehouse_ID numeric(10, 0),
                                                         IN p_showDetails    character varying,
                                                         IN p_ad_language    character varying(6),
                                                         IN p_AD_Client_ID   numeric(10, 0),
                                                         IN p_AD_Org_ID      numeric(10, 0))
;

CREATE OR REPLACE FUNCTION report.M_Cost_CostPrice_Function(IN p_keydate            timestamp WITH TIME ZONE,
                                                            IN p_M_Product_ID       numeric(10, 0),
                                                            IN p_M_Warehouse_ID     numeric(10, 0),
                                                            IN p_showDetails        character varying,
                                                            IN p_onlyActiveProducts character varying,
                                                            IN p_ad_language        character varying(6),
                                                            IN p_AD_Client_ID       numeric(10, 0),
                                                            IN p_AD_Org_ID          numeric(10, 0))


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
       a.name                                       AS Activity,
       wh.name                                      AS WarehouseName,
       p.value                                      AS ProductValue,
       COALESCE(pt.Name, p.Name)                    AS ProductName,
       qty,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol)      AS UOMSymbol,
       CostPrice,
       ROUND(qty * CostPrice, dat.costingprecision) AS TotalAmt
FROM (SELECT pa.P_Asset_acct                                                          AS C_ValidCombination_ID,
             wh.C_Activity_ID,
             wh.M_Warehouse_ID,
             hus.M_Product_ID,
             hus.C_UOM_ID,
             SUM(uomconvert(hus.m_product_id, hutl.c_uom_id, hus.c_uom_id, hutl.qty)) AS qty,
             COALESCE(
                     getCurrentCost(hus.M_Product_ID,
                                    hus.C_UOM_ID,
                                    p_keydate::date,
                                    schema.c_acctschema_id,
                                    costElement.m_costelement_id,
                                    p_AD_Client_ID,
                                    p_AD_org_ID)
                 , 0::numeric)                                                        AS CostPrice,
             currency.costingprecision
      FROM M_Warehouse wh
               JOIN ad_org org ON wh.ad_org_id = org.ad_org_ID
               JOIN AD_Client client ON wh.ad_client_ID = client.ad_client_id
               JOIN ad_clientinfo clientInfo ON client.ad_client_id = clientInfo.ad_client_id
               JOIN c_acctschema schema ON clientInfo.c_acctschema1_id = schema.c_acctschema_id
               JOIN C_Currency currency ON schema.c_currency_id = currency.c_currency_id
               JOIN m_costelement costElement
                    ON schema.costingmethod = costElement.costingmethod AND costElement.isactive = 'Y' /* There should only be one active M_CostElement
              entry for a certain CostingMethod because of the unique index M_CostElement_CostingMethod_Unique. Please, keep this function in sync with this index */


               LEFT OUTER JOIN M_Locator l
                               ON wh.M_Warehouse_ID = l.M_Warehouse_ID AND l.isActive = 'Y'
               LEFT OUTER JOIN M_HU_Trx_line hutl ON l.M_Locator_ID = hutl.M_locator_ID AND hutl.isActive = 'Y'
               LEFT OUTER JOIN M_HU_Item item ON hutl.VHU_Item_ID = item.M_HU_Item_ID AND item.isActive = 'Y'
               LEFT OUTER JOIN M_HU hu ON item.M_HU_ID = hu.M_HU_ID AND hu.isActive = 'Y'
               LEFT OUTER JOIN M_HU_Storage hus ON hu.M_HU_ID = hus.M_HU_ID AND hus.isActive = 'Y' AND HUS.qty <> 0
               LEFT OUTER JOIN M_Product_acct pa ON hus.M_Product_ID = pa.M_Product_ID AND pa.isActive = 'Y'
      WHERE hutl.DateTrx::date <= p_keydate
        AND hutl.huStatus IN ('A', 'S') -- qonly display transactions if status is stocked, A = Active, S = Picked
        AND org.ad_org_id = p_AD_Org_ID
        AND client.ad_client_id = p_AD_Client_ID
      GROUP BY pa.P_Asset_acct,
               wh.C_Activity_ID,
               wh.M_Warehouse_ID,
               hus.M_Product_ID,
               hus.C_UOM_ID,
               schema.c_acctschema_id,
               currency.costingprecision,
               COALESCE(getCurrentCost(hus.M_Product_ID,
                                       hus.C_UOM_ID,
                                       p_keydate::date,
                                       schema.c_acctschema_id,
                                       costElement.m_costelement_id,
                                       p_AD_Client_ID,
                                       p_AD_org_ID), 0::numeric)
      HAVING first_agg(hutl.huStatus ORDER BY hutl.created DESC) NOT IN ('P', 'D', 'E')
      UNION
      SELECT pa.P_Asset_acct                                   AS C_ValidCombination_ID,
             wh.C_Activity_ID,
             wh.M_Warehouse_ID,
             p.M_Product_ID,
             p.C_UOM_ID,
             SUM(t.Movementqty)                                AS qty,
             COALESCE(getCurrentCost(p.M_Product_ID,
                                     uom.C_UOM_ID,
                                     p_keydate::date,
                                     schema.c_acctschema_id,
                                     costElement.m_costelement_id,
                                     p_AD_Client_ID,
                                     p_AD_org_ID), 0::numeric) AS CostPrice,
             currency.costingprecision                         AS costingPrecision
      FROM M_Warehouse wh
               JOIN ad_org org
                    ON wh.ad_org_id = org.ad_org_ID
               JOIN AD_Client client ON wh.ad_client_ID = client.ad_client_id
               JOIN ad_clientinfo clientInfo ON client.ad_client_id = clientInfo.ad_client_id
               JOIN c_acctschema schema ON clientInfo.c_acctschema1_id = schema.c_acctschema_id
               JOIN C_Currency currency ON schema.c_currency_id = currency.c_currency_id
               JOIN m_costelement costElement
                    ON schema.costingmethod = costElement.costingmethod AND costElement.isactive = 'Y' /* There should only be one active M_CostElement
              entry for a certain CostingMethod because of the unique index M_CostElement_CostingMethod_Unique. Please, keep this function in sync with this index */

               LEFT OUTER JOIN M_Locator l ON wh.M_Warehouse_ID = l.M_Warehouse_ID AND l.isActive = 'Y'
               LEFT OUTER JOIN M_Transaction t ON l.M_Locator_ID = t.M_Locator_ID AND t.isActive = 'Y'
               LEFT OUTER JOIN M_Product p ON t.M_Product_ID = p.M_Product_ID
          AND CASE WHEN p_onlyActiveProducts = 'Y' THEN p.isactive = 'Y' ELSE 1 = 1 END
               LEFT OUTER JOIN C_UOM uom ON p.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
               LEFT OUTER JOIN M_Product_acct pa ON p.M_Product_ID = pa.M_Product_ID AND pa.isActive = 'Y'
      WHERE p.M_Product_Category_ID =
            getSysConfigAsNumeric('PackingMaterialProductCategoryID'
                , wh.AD_Client_ID
                , wh.AD_Org_ID)
        AND t.MovementDate::date <= p_keydate
        AND org.ad_org_id = p_AD_Org_ID
      GROUP BY pa.P_Asset_acct,
               wh.C_Activity_ID,
               wh.M_Warehouse_ID,
               p.M_Product_ID,
               p.C_UOM_ID,
               schema.c_acctschema_id,
               currency.costingprecision,
               COALESCE(getCurrentCost(p.M_Product_ID,
                                       uom.c_uom_ID,
                                       p_keydate::date,
                                       schema.c_acctschema_id,
                                       costElement.M_CostElement_ID,
                                       p_AD_Client_ID,
                                       p_AD_org_ID), 0::numeric)) dat
         LEFT OUTER JOIN M_Product p
                         ON dat.M_Product_ID = p.M_Product_ID AND p.AD_Org_ID IN (p_ad_org_id, 0) -- products on ANY org are also acceptable
                             AND CASE WHEN p_onlyActiveProducts = 'Y' THEN p.isactive = 'Y' ELSE 1 = 1 END
         LEFT OUTER JOIN M_Product_Trl pt
                         ON p.M_Product_ID = pt.M_Product_ID AND pt.ad_language = p_ad_language AND pt.isActive = 'Y' AND
                            pt.AD_Org_ID = p_ad_org_id
         LEFT OUTER JOIN M_Warehouse wh
                         ON dat.M_Warehouse_ID = wh.M_Warehouse_ID AND wh.isActive = 'Y' AND wh.AD_Org_ID = p_ad_org_id
         LEFT OUTER JOIN C_Activity a ON dat.C_Activity_ID = a.C_Activity_ID AND a.isActive = 'Y'
         LEFT OUTER JOIN C_ValidCombination vc
                         ON dat.C_ValidCombination_ID = vc.C_ValidCombination_ID AND vc.isActive = 'Y'
         LEFT OUTER JOIN C_UOM uom ON dat.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
         LEFT OUTER JOIN C_UOM_Trl uomt
                         ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.ad_language = p_ad_language AND uomt.isActive = 'Y'
WHERE p.m_product_id IS NOT NULL
  AND CASE WHEN p_onlyActiveProducts = 'Y' THEN qty != 0 ELSE 1 = 1 END
  AND CASE WHEN p_M_Product_ID IS NOT NULL AND p_M_Product_ID > 0 THEN p.M_Product_ID = p_M_Product_ID ELSE 1 = 1 END
  AND CASE WHEN p_M_Warehouse_ID IS NOT NULL AND p_M_Warehouse_ID > 0 THEN wh.M_Warehouse_ID = p_M_Warehouse_ID ELSE 1 = 1 END
ORDER BY vc.combination,
         vc.description,
         a.name,
         Wh.name,
         p.value
    ;


$$
    LANGUAGE sql STABLE
;


--
-- SELECT *
-- FROM report.M_Cost_CostPrice_Function('2020-09-16',
--                                       NULL,
--                                       NULL,
--                                       'Y',
--                                       'Y',
--                                       'de_DE',
--                                       1000000,
--                                       1000000)
