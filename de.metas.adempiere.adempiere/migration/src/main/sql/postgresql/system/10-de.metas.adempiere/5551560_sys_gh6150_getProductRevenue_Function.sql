DROP FUNCTION IF EXISTS getProductRevenue(numeric, numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getProductRevenue
(
	p_M_Product_ID numeric,
	p_AD_Client_ID numeric, 
	p_AD_Org_ID numeric, 
	p_DateFrom date, 
	p_DateTo date
)
RETURNS numeric

AS
$$

	SELECT  sum
	(
		CASE WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo 
		ELSE il.LineNetAmt
		END
	)	
	
	FROM C_InvoiceLine il
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
		
	WHERE	
		p_M_Product_ID = il.M_Product_ID 
		AND il.IsActive = 'Y' and i.IsActive = 'Y'
		AND i.isSOTrx = 'Y'
		AND i.DocStatus in ('CO', 'CL')
		AND i.AD_Client_ID = p_AD_Client_ID
		AND i.AD_Org_ID = p_AD_Org_ID
		AND (CASE WHEN p_DateFrom IS NOT NULL THEN i.DateInvoiced >= p_DateFrom ELSE 1=1 END)
		AND (CASE WHEN p_DateTo IS NOT NULL THEN i.DateInvoiced <= p_DateTo ELSE 1=1 END)
	
$$	
LANGUAGE sql STABLE;