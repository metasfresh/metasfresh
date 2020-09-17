DROP FUNCTION if exists getCurrentCost(numeric, date, numeric, numeric, numeric);
CREATE OR REPLACE FUNCTION getCurrentCost(p_M_Product_ID numeric, p_Date date, p_AcctSchema_ID numeric,
                                          p_M_CostElement_Id numeric,
                                          p_AD_Client_ID numeric, p_AD_Org_ID numeric)
    returns numeric
AS
$BODY$
WITH x as
         (
             SELECT m_costdetail_ID,
                    cd.prev_currentcostprice,
                    cd.M_Product_ID,
                    COALESCE(mi.dateAcct, mpo.dateAcct, pp.dateAcct, inv.movementDate, m.MovementDate, io.dateAcct,
                             NULL) as dateAcct

             from m_costdetail cd

                      JOIN AD_Org org on cd.ad_org_ID = org.ad_org_id


                      left join M_MatchInv mi ON mi.m_matchinv_id = cd.m_matchinv_id
                      left JOIN M_MatchPO mpo on mpo.M_MatchPO_ID = cd.m_MatchPO_ID
                      left JOIN PP_Cost_Collector pp ON pp.PP_Cost_Collector_ID = cd.pp_cost_collector_id
                      left JOIN m_inventoryline il on il.m_inventoryline_id = cd.m_inventoryline_id
                      LEFT JOIN M_Inventory inv on inv.m_inventory_id = il.m_inventory_id
                      LEFT JOIN M_MovementLine ml on ml.m_movementline_id = cd.m_movementline_id
                      LEFT JOIN M_Movement m on m.m_movement_id = ml.m_movement_ID
                      LEFT JOIN m_inoutline iol on iol.m_inoutline_id = cd.m_inoutline_id
                      LEFT JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID

             WHERE cd.ischangingcosts = 'Y'
               and cd.c_acctschema_id = p_acctSchema_ID
               and cd.m_costelement_id = p_M_CostElement_Id
               and cd.M_Product_ID = p_M_Product_ID
               and cd.ad_client_id = p_AD_Client_ID
               and cd.ad_org_ID = p_AD_Org_ID
         )


Select COALESCE((SELECT prev_currentcostprice from x where dateAcct > p_Date limit 1), cost.currentCostPrice)
           as currentCostPrice

from M_Cost cost


WHERE cost.m_product_id = p_M_Product_ID
  and cost.c_acctschema_id = p_acctSchema_ID
  and cost.m_costelement_id = p_M_CostElement_Id
  and cost.ad_client_id = p_AD_Client_ID
  and cost.AD_Org_Id = p_AD_Org_ID;


$BODY$
    LANGUAGE SQL
    STABLE
;
