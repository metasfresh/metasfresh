-- DROP FUNCTION getCostPrice(numeric, numeric);

CREATE OR REPLACE FUNCTION getCostPrice(p_M_Product_ID numeric, p_AD_Client_ID numeric) 

RETURNS numeric AS

$BODY$
DECLARE
	v_result CostPrice;
BEGIN
	v_result := 
	
	(
		SELECT sum(cost.CurrentCostPrice)
		FROM M_Cost cost
		JOIN M_Product p pn cost.M_Product_ID = p.M_Product_ID
		JOIN M_CostElement ce on cost.M_CostElement_ID = ce.M_Cost_Element_ID
		
		WHERE p.M_Product_ID = p_M_Product_ID
		and ce.CostingMethod = (select 
								from AD_ClientInfo i 
								join C_AcctSchema s on )
		and 
	)
	end if;
	
	return v_result CostPrice;
END;

$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
