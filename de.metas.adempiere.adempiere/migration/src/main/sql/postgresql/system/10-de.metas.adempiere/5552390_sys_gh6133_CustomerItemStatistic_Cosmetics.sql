DROP FUNCTION IF EXISTS customerItemStatistics(numeric, numeric, date, date, numeric, numeric);

CREATE OR REPLACE FUNCTION customerItemStatistics(
     p_AD_Client_ID numeric,
     p_AD_Org_ID numeric,
     p_dateFrom date, 
	 p_dateTo date,
	 p_C_BPartner_ID numeric, 
	 p_M_Product_ID numeric)

  RETURNS TABLE
(

	 BPValue character varying,
	 productValue character varying,
	 Name character varying,
	 qtyInvoiced numeric,
	 UOM  character varying,
	 Revenue numeric,
	 ProductCosts numeric,
	 ProductCostsPercent numeric
 
) AS


$$


SELECT 
	t.BPValue,
	t.productValue,
	t.Name,
	t.qtyInvoiced,
	t.UOM,
	t.Revenue as Revenue,
	t.costPrice * t.qtyInvoiced as ProductCosts,
	round((CASE WHEN t.Revenue > 0 THEN (t.costPrice * t.qtyInvoiced * 100 )/ t.Revenue else 0 end ),2) as  ProductCostsPercent
	
FROM
	(
		SELECT 
			bp.value as BPValue,
			p.value as productValue,
			p.name as Name,
			sum(il.qtyInvoiced) as qtyInvoiced,
			(SELECT UOMSymbol from C_UOM WHERE C_UOM_ID = p.C_UOM_ID) as UOM,
			sum
				(
					CASE WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo 
					ELSE il.LineNetAmt
					END 
				)	AS Revenue, 
			getCostPrice( p.M_Product_ID, p_AD_Client_ID,  p_AD_Client_ID) as costPrice
		 
		FROM 
			M_Product p
			JOIN C_InvoiceLine il on p.M_Product_ID = il.M_Product_ID
			JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID 
			JOIN C_BPartner bp on i.C_BPartner_ID = bp.C_BPartner_ID
			
		WHERE i.isActive = 'Y' and il.IsActive = 'Y'
			AND i.AD_Client_ID = p_AD_Client_ID and i.AD_Org_ID = p_AD_Org_ID
			AND i.DocStatus in ('CO', 'CL')
			AND (p_C_BPartner_ID IS NULL OR p_C_BPartner_ID <= 0 OR i.C_BPartner_ID = p_C_BPartner_ID)
			AND (p_M_Product_ID IS NULL OR p_M_Product_ID <= 0 OR p.M_Product_ID = p_M_Product_ID )
			AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
			AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo)
			
			GROUP BY bp.value, p.M_Product_ID, p.C_UOM_ID
) t
	ORDER BY t.BPValue, t.productValue
$$
LANGUAGE sql STABLE;

