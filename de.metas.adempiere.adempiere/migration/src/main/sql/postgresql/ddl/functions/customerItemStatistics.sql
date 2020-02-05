DROP FUNCTION IF EXISTS customerItemStatistics(date, date, numeric, numeric);

CREATE OR REPLACE FUNCTION customerItemStatistics(
    IN p_dateFrom date, 
	IN p_dateTo date,
	IN p_C_BPartner_ID numeric, -- mandatory
	IN p_M_Product_ID numeric)

  RETURNS TABLE
(

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
 p.value as productValue,
 p.name as description,
 sum(il.qtyInvoiced) as qty,
 (SELECT UOMSymbol from C_UOM WHERE C_UOM_ID = p.C_UOM_ID) as UOM,
 sum(il.LineNetAmt) as Betrag,
 --sum((il.discount/100) * il.LineNetAmt) not needed atm
--avg(cost.CurrentCostPrice) * sum(il.qtyInvoiced) as DB,
 
 getCostPrice( p.M_Product_ID, p.AD_Client_ID) *  sum(il.qtyInvoiced) as DB,
 ( avg(cost.CurrentCostPrice) * 100 ) / COALESCE(sum(il.qtyInvoiced), 1) as DB_Percent
 
 

FROM 
	M_Product p
	JOIN C_InvoiceLine il on p.M_Product_ID = il.M_Product_ID
	JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID 
	LEFT JOIN 
	( M_Cost cost on p.M_Product_ID = cost.M_Product_ID
	) 
WHERE 1=1
    AND i.C_Bpartner_ID = p_C_BPartner_ID 
	AND CASE WHEN p_dateFrom IS NOT NULL THEN i.DateInvoiced >= p_dateFrom ELSE 1=1 END
	AND CASE WHEN p_dateTo IS NOT NULL THEN i.DateInvoiced <= p_dateTo ELSE 1=1 END
	AND CASE WHEN p_M_Product_ID IS NOT NULL THEN p.M_Product_ID = p_M_Product_ID ELSE 1=1 END

	GROUP BY p.M_Product_ID, p.C_UOM_ID

$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;