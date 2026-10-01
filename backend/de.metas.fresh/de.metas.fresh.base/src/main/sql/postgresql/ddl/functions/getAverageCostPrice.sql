DROP FUNCTION IF EXISTS getAverageCostPrice(numeric, numeric, numeric)
;

/*
 * Function: getAverageCostPrice
 * Purpose : Return the AVERAGE purchase cost per unit for a product, independent of the
 *           accounting schema's configured costing method.
 *
 * Background:
 *   M_Cost does not hold a single "cost" per product. Costs are split into rows keyed by
 *   (M_Product_ID, C_AcctSchema_ID, M_CostElement_ID, AD_Org_ID, M_AttributeSetInstance_ID).
 *   The costing METHOD lives on the cost element (M_CostElement.CostingMethod), not on M_Cost.
 *   M_Cost.CurrentCostPrice is the running per-unit value the costing engine maintains for that
 *   element; for a method-'A' (Average / "Durchschnittskosten") element it is the moving average
 *   purchase cost. The value is already an average -- this function reads it, it does not re-average.
 *
 * Why not reuse getCostPrice():
 *   getCostPrice() returns the cost under the schema's OWN method (ce.CostingMethod = acs.CostingMethod),
 *   so it only equals the average when the primary accounting schema is configured as Average.
 *   This function hard-filters ce.CostingMethod = 'A', so it returns the average for EVERY client,
 *   regardless of that client's configured costing method (a general-purpose "average cost" getter).
 *
 * Logic:
 *   - Restrict to the client's PRIMARY accounting schema (AD_ClientInfo.C_AcctSchema1_ID) so the
 *     result is one consistent, single-currency figure and never a sum across multiple schemas.
 *   - Keep only Average (CostingMethod = 'A') cost elements.
 *   - SUM(CurrentCostPrice): in the standard setup exactly one row matches, so the sum is that value;
 *     the SUM also combines multiple average cost COMPONENTS (cost elements) into the total cost when
 *     more than one is configured. (Matches the aggregation semantics of getCostPrice.)
 *   - COALESCE(..., 0): a client not using average costing may have no 'A' row -> returns 0.
 *
 * Parameters:
 *   p_M_Product_ID - product to look up
 *   p_AD_Client_ID - client (selects the primary accounting schema)
 *   p_AD_Org_ID    - org the cost is maintained for
 * Returns: the average cost price as numeric (0 when no average cost exists).
 */
CREATE OR REPLACE FUNCTION getAverageCostPrice(IN p_M_Product_ID numeric, IN p_AD_Client_ID numeric, IN p_AD_Org_ID numeric)
    RETURNS numeric
    LANGUAGE sql
    STABLE
AS
$$
SELECT COALESCE(sum(cost.CurrentCostPrice), 0)
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
