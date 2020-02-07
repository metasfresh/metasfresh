DROP FUNCTION IF EXISTS customerItemStatistics(date, date, numeric, numeric);

CREATE OR REPLACE FUNCTION customerItemStatistics(
    IN p_dateFrom date, 
	IN p_dateTo date,
	IN p_C_BPartner_ID numeric, 
	IN p_M_Product_ID numeric)

  RETURNS TABLE
(

 partnerValue character varying,
 productValue character varying,
 description character varying,
 qty numeric,
 UOM  character varying,
 betrag numeric,
 DB numeric,
 DB_Percent numeric
 

) AS


$BODY$


SELECT 
 bp.value,
 p.value as productValue,
 p.name as description,
 sum(il.qtyInvoiced) as qty,
 (SELECT UOMSymbol from C_UOM WHERE C_UOM_ID = p.C_UOM_ID) as UOM,
 sum(il.LineNetAmt) as Betrag, -- TODO: Decide if the tax should be subtracted in case of IsTaxIncluded = true
 --sum((il.discount/100) * il.LineNetAmt) -- not needed atm 
 getCostPrice( p.M_Product_ID, p.AD_Client_ID) *  sum(il.qtyInvoiced) as DB,
 ((getCostPrice( p.M_Product_ID, p.AD_Client_ID)*  sum(il.qtyInvoiced)) * 100 ) / COALESCE(sum(il.LineNetAmt), 1) as DB_Percent
 
 

FROM 
	M_Product p
	JOIN C_InvoiceLine il on p.M_Product_ID = il.M_Product_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID 
	JOIN C_BPartner bp on i.C_BPartner_ID = bp.C_BPartner_ID
	
WHERE 1=1
    AND CASE WHEN p_C_BPartner_ID > 0 THEN bp.C_Bpartner_ID = p_C_BPartner_ID  ELSE 1=1 END
	AND CASE WHEN p_dateFrom IS NOT NULL THEN i.DateInvoiced >= p_dateFrom ELSE 1=1 END
	AND CASE WHEN p_dateTo IS NOT NULL THEN i.DateInvoiced <= p_dateTo ELSE 1=1 END
	AND CASE WHEN p_M_Product_ID > 0 THEN p.M_Product_ID = p_M_Product_ID ELSE 1=1 END

	GROUP BY bp.value, p.M_Product_ID, p.C_UOM_ID
	ORDER BY bp.value

$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;

