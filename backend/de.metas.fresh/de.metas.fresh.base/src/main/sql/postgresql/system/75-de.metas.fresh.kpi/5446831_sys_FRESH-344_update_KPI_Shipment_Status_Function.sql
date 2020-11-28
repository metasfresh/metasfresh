DROP FUNCTION IF EXISTS KPI_Shipment_Status_Function (IN M_InOut_ID numeric);
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Shipment_Status_Function (IN M_InOut_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Shipment_Status_Function (IN M_InOut_ID numeric) 
RETURNS TABLE 
(
	
	Shipped numeric,
	Invoiced numeric
	
)
AS
$$


SELECT 

(
CASE 
	WHEN io.DocStatus IN ('CO', 'CL') 
	THEN 1
	ELSE 0
	END
) :: numeric AS Shipped,

(
CASE
	WHEN COUNT(iol.M_InOutLine_ID) > 0
	THEN SUM( LEAST(COALESCE( il.QtyEntered, 0)/ GREATEST(iol.QtyEntered,1),1 :: numeric)) / COUNT(iol.M_InOutLine_ID)
	ELSE 0
	END
) :: numeric AS Invoiced


FROM M_InOut io
LEFT JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID AND iol.IsPackagingMaterial = 'N' 
LEFT JOIN M_MatchInv mi ON mi.M_InOutLine_ID = iol.M_InOutLine_ID
LEFT JOIN C_InvoiceLine il ON mi.C_InvoiceLine_ID = il.C_InvoiceLine_ID
LEFT JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.DocStatus IN ('CO','CL')

WHERE io.M_InOut_ID = $1 and io.IsSOTrx = 'Y' 

group by io.M_InOut_ID

$$
LANGUAGE sql STABLE;