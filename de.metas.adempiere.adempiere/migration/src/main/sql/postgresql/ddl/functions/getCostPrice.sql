-- DROP FUNCTION getCostPrice(numeric, numeric);

CREATE OR REPLACE FUNCTION getCostPrice(p_M_Product_ID numeric, p_AD_Client_ID numeric) 

RETURNS numeric AS

$BODY$
DECLARE
	v_CostPrice numeric;
BEGIN
	v_CostPrice := 
	
	(
		SELECT sum(cost.CurrentCostPrice)
		FROM M_Cost cost
		JOIN M_Product p on cost.M_Product_ID = p.M_Product_ID
		JOIN M_CostElement ce on cost.M_CostElement_ID = ce.M_CostElement_ID
		
		WHERE p.M_Product_ID = p_M_Product_ID
		and ce.CostingMethod = (select s.CostingMethod
								from AD_ClientInfo i 
								join C_AcctSchema s on  i.C_AcctSchema1_ID = s.C_AcctSchema_ID
								)

	);
	
	
	return v_CostPrice ;
END;

$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
