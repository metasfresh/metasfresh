-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/getAverageCostPrice.sql
-- Maintained purchase cost per unit using the primary accounting schema's costing method
-- (ce.CostingMethod = acs.CostingMethod). Returns NULL when none exists (so the report cell is blank,
-- not 0) and restricts to product-level active cost. Used by the MHD Liste report "Standard EK" column.

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
         INNER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = cost.C_AcctSchema_ID
WHERE cost.M_Product_ID = p_M_Product_ID
  AND cost.AD_Client_ID = p_AD_Client_ID
  AND cost.AD_Org_ID = p_AD_Org_ID
  AND cost.M_AttributeSetInstance_ID = 0
  AND cost.C_AcctSchema_ID = (SELECT ci.C_AcctSchema1_ID FROM AD_ClientInfo ci WHERE ci.AD_Client_ID = p_AD_Client_ID)
  AND ce.CostingMethod = acs.CostingMethod
  AND cost.IsActive = 'Y'
  AND ce.IsActive = 'Y'
$$
;
