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
	 description character varying,
	 qtyInvoiced numeric,
	 uomSymbol  character varying,
	 Amount numeric,
	 ProductCosts numeric,
	 ProductCostsPercent numeric
 
) AS


$$


SELECT 
	t.BPValue,
	t.productValue,
	t.productName,
	t.qtyInvoiced,
	t.uomSymbol,
	t.Betrag as Amount,
	t.costPrice * t.qtyInvoiced as ProductCosts,
	CASE WHEN t.betrag > 0 THEN (t.costPrice * t.qtyInvoiced * 100 )/ t.betrag else 0 end as  ProductCostsPercent
	
FROM
	(
		SELECT 
			bp.value as BPValue,
			p.value as productValue,
			p.name as productName,
			sum(il.qtyInvoiced) as qtyInvoiced,
			(SELECT UOMSymbol from C_UOM WHERE C_UOM_ID = p.C_UOM_ID) as uomSymbol,
			sum
				(
					CASE WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo 
					ELSE il.LineNetAmt
					END 
				)	AS Betrag, 
			getCostPrice( p.M_Product_ID, p_AD_Client_ID,  p_AD_Client_ID) as costPrice
		 
		FROM 
			M_Product p
			JOIN C_InvoiceLine il on p.M_Product_ID = il.M_Product_ID
			JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID 
			JOIN C_BPartner bp on i.C_BPartner_ID = bp.C_BPartner_ID
			
		WHERE i.isActive = 'Y' and il.IsActive = 'Y'
			AND i.AD_Client_ID = p_AD_Client_ID and i.AD_Org_ID = p_AD_Org_ID
			
			AND CASE WHEN p_C_BPartner_ID > 0 THEN bp.C_Bpartner_ID = p_C_BPartner_ID  ELSE 1=1 END
			AND CASE WHEN p_dateFrom IS NOT NULL THEN i.DateInvoiced >= p_dateFrom ELSE 1=1 END
			AND CASE WHEN p_dateTo IS NOT NULL THEN i.DateInvoiced <= p_dateTo ELSE 1=1 END
			AND CASE WHEN p_M_Product_ID > 0 THEN p.M_Product_ID = p_M_Product_ID ELSE 1=1 END

			GROUP BY bp.value, p.M_Product_ID, p.C_UOM_ID
) t
	ORDER BY t.BPValue
$$
LANGUAGE sql STABLE;

