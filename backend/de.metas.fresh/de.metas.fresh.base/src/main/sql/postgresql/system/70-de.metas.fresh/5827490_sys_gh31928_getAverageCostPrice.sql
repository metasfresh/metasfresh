-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/getAverageCostPrice.sql
-- Average purchase cost per unit, independent of the schema's configured costing method
-- (hard-filters M_CostElement.CostingMethod = 'A'). Used by the MHD Liste report for the "Standard EK" column.

DROP FUNCTION IF EXISTS getAverageCostPrice(numeric, numeric, numeric)
;

CREATE OR REPLACE FUNCTION getAverageCostPrice(IN p_M_Product_ID numeric, IN p_AD_Client_ID numeric, IN p_AD_Org_ID numeric)
    RETURNS numeric
    LANGUAGE sql
    STABLE
AS
$$
SELECT sum(cost.CurrentCostPrice)
FROM M_Cost cost
         INNER JOIN M_CostElement ce ON ce.M_CostElement_ID = cost.M_CostElement_ID
WHERE cost.M_Product_ID = p_M_Product_ID
  AND cost.AD_Client_ID = p_AD_Client_ID
  AND cost.AD_Org_ID = p_AD_Org_ID
  AND cost.M_AttributeSetInstance_ID = 0
  AND cost.C_AcctSchema_ID = (SELECT ci.C_AcctSchema1_ID FROM AD_ClientInfo ci WHERE ci.AD_Client_ID = p_AD_Client_ID)
  AND ce.CostingMethod = 'A'
  AND cost.IsActive = 'Y'
  AND ce.IsActive = 'Y'
$$
;
