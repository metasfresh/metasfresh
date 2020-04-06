DROP FUNCTION IF EXISTS getProductCurrentStock(numeric, numeric, numeric);

CREATE OR REPLACE FUNCTION getProductCurrentStock
(
	p_M_Product_ID numeric,
	p_AD_Client_ID numeric,
	p_AD_Org_ID numeric
)
RETURNS numeric

AS

$$

	SELECT sum(s.QtyOnHand)
	
	FROM MD_Stock s		
		
	WHERE	
		p_M_Product_ID = s.M_Product_ID 
		AND s.IsActive = 'Y'
		AND s.AD_Client_ID = p_AD_Client_ID
		AND s.AD_Org_ID = p_AD_Org_ID
$$	
LANGUAGE sql STABLE;