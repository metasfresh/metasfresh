DROP FUNCTION IF EXISTS getCurrentCost(numeric,
                                       date,
                                       numeric,
                                       numeric,
                                       numeric,
                                       numeric)
;



DROP FUNCTION IF EXISTS getCurrentCost(numeric,
                                       numeric,
                                       date,
                                       numeric,
                                       numeric,
                                       numeric)
;

CREATE OR REPLACE FUNCTION getCurrentCost(p_M_Product_ID     numeric,
                                          p_C_UOM_ID         numeric,
                                          p_Date             date,
                                          p_AcctSchema_ID    numeric,
                                          p_M_CostElement_Id numeric,
                                          p_AD_Client_ID     numeric,
                                          p_AD_Org_ID        numeric)
    RETURNS numeric
AS
$BODY$
WITH x AS
         (
             SELECT m_costdetail_ID,
                    cd.prev_currentcostprice,
                    cd.M_Product_ID,
                    COALESCE(mi.dateAcct,
                             mpo.dateAcct,
                             pp.dateAcct,
                             inv.movementDate,
                             m.MovementDate,
                             io.dateAcct,
                             NULL) AS dateAcct

             FROM m_costdetail cd

                      JOIN AD_Org org ON cd.ad_org_ID = org.ad_org_id


                      LEFT JOIN M_MatchInv mi ON mi.m_matchinv_id = cd.m_matchinv_id
                      LEFT JOIN M_MatchPO mpo ON mpo.M_MatchPO_ID = cd.m_MatchPO_ID
                      LEFT JOIN PP_Cost_Collector pp ON pp.PP_Cost_Collector_ID = cd.pp_cost_collector_id
                      LEFT JOIN m_inventoryline il ON il.m_inventoryline_id = cd.m_inventoryline_id
                      LEFT JOIN M_Inventory inv ON inv.m_inventory_id = il.m_inventory_id
                      LEFT JOIN M_MovementLine ml ON ml.m_movementline_id = cd.m_movementline_id
                      LEFT JOIN M_Movement m ON m.m_movement_id = ml.m_movement_ID
                      LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = cd.m_inoutline_id
                      LEFT JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID

             WHERE cd.ischangingcosts = 'Y'
               AND cd.c_acctschema_id = p_acctSchema_ID
               AND cd.m_costelement_id = p_M_CostElement_Id
               AND cd.M_Product_ID = p_M_Product_ID
               AND cd.ad_client_id = p_AD_Client_ID
               AND cd.ad_org_ID = p_AD_Org_ID
         )


SELECT priceuomconvert(p.M_Product_ID,
                       COALESCE((
                                    SELECT prev_currentcostprice
                                    FROM x
                                    WHERE dateAcct > p_Date
                                    ORDER BY dateAcct
                                    LIMIT 1), cost.currentCostPrice),
                       p.c_uom_id,
                       p_c_uom_id,
                       currency.costingprecision:: integer)
           AS currentCostPrice

FROM M_Cost cost
         JOIN M_Product p ON cost.M_Product_ID = p.M_Product_ID
         JOIN C_AcctSchema sch ON cost.c_acctschema_id = sch.c_acctschema_id
         JOIN C_Currency currency ON sch.c_currency_id = currency.c_currency_id


WHERE cost.m_product_id = p_M_Product_ID
  AND cost.c_acctschema_id = p_acctSchema_ID
  AND cost.m_costelement_id = p_M_CostElement_Id
  AND cost.ad_client_id = p_AD_Client_ID
  AND cost.AD_Org_Id = p_AD_Org_ID;


$BODY$
    LANGUAGE SQL
    STABLE
;
