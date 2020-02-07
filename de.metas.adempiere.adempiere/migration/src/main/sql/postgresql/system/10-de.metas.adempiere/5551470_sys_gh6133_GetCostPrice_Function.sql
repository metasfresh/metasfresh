-- DROP FUNCTION getCostPrice(numeric, numeric);

CREATE OR REPLACE FUNCTION getCostPrice
(
	p_M_Product_ID numeric,
	p_AD_Client_ID numeric
) 
RETURNS numeric 
AS
$BODY$
	SELECT COALESCE(sum(cost.CurrentCostPrice), 0)
	FROM M_Cost cost
	INNER JOIN C_AcctSchema acs on acs.C_AcctSchema_ID=cost.C_AcctSchema_ID
	INNER JOIN M_CostElement ce on cost.M_CostElement_ID = ce.M_CostElement_ID

	WHERE cost.M_Product_ID = p_M_Product_ID

	AND cost.C_AcctSchema_ID = (select ci.C_AcctSchema1_ID from AD_ClientInfo ci where ci.AD_Client_ID = p_AD_Client_ID)
	AND ce.CostingMethod = acs.CostingMethod
	--
	UNION ALL
	(
		SELECT 0
	)
	LIMIT 1
$BODY$
LANGUAGE sql STABLE
;