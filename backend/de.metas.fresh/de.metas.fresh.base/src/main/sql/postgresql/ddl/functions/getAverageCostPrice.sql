DROP FUNCTION IF EXISTS getAverageCostPrice(numeric, numeric, numeric)
;

/*
 * Function: getAverageCostPrice
 * Purpose : Return the maintained purchase cost per unit for a product, using the costing method
 *           configured on the client's primary accounting schema (Average for an average-costing
 *           client -- hence the name; Standard for a standard-costing client, etc.).
 *
 * Background:
 *   M_Cost does not hold a single "cost" per product. Costs are split into rows keyed by
 *   (M_Product_ID, C_AcctSchema_ID, M_CostElement_ID, AD_Org_ID, M_AttributeSetInstance_ID).
 *   The costing METHOD lives on the cost element (M_CostElement.CostingMethod), not on M_Cost.
 *   M_Cost.CurrentCostPrice is the running per-unit value the costing engine maintains for that
 *   element; for an average-costing element it is the moving average purchase cost. The value is
 *   already maintained -- this function reads it, it does not recompute it.
 *
 * Why a dedicated function (not getCostPrice):
 *   Same selection as getCostPrice (ce.CostingMethod = acs.CostingMethod), but with two
 *   report-specific differences:
 *     - returns NULL when no matching cost row exists (getCostPrice COALESCEs to 0), so the report
 *       leaves the cell blank instead of showing a misleading 0;
 *     - restricts to product-level cost (M_AttributeSetInstance_ID = 0) and active rows only.
 *
 * Logic:
 *   - Resolve the accounting schema per client + org via getC_AcctSchema_ID(client, org): it returns
 *     the schema whose AD_OrgOnly_ID matches this org, else the client's default schema. This covers
 *     clients with multiple accounting schemas (an org-specific schema is used for its own org) and
 *     yields one consistent, single-currency figure per row -- never a sum across schemas.
 *   - Keep only cost elements whose method matches the schema's configured costing method
 *     (ce.CostingMethod = acs.CostingMethod).
 *   - SUM(CurrentCostPrice): in the standard setup exactly one row matches, so the sum is that value;
 *     it also combines multiple cost COMPONENTS (cost elements) into the total cost when more than one
 *     is configured. (Matches the aggregation semantics of getCostPrice.)
 *   - No COALESCE: no matching cost row -> returns NULL, so the report leaves the cell blank (rather
 *     than a misleading 0). A genuine 0 cost still shows.
 *
 * Parameters:
 *   p_M_Product_ID - product to look up
 *   p_AD_Client_ID - client (selects the primary accounting schema)
 *   p_AD_Org_ID    - org the cost is maintained for
 * Returns: the maintained cost price as numeric, or NULL (rendered as a blank cell) when none exists.
 */
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
  AND cost.C_AcctSchema_ID = getC_AcctSchema_ID(p_AD_Client_ID, p_AD_Org_ID)
  AND ce.CostingMethod = acs.CostingMethod
  AND cost.IsActive = 'Y'
  AND ce.IsActive = 'Y'
$$
;
