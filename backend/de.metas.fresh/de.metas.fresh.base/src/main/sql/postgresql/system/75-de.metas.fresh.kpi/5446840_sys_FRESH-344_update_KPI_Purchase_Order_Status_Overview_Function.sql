
DROP FUNCTION IF EXISTS KPI_Purchase_Order_Status_Overview_Function (IN DateFrom date, IN DateTo date) ;
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Purchase_Order_Status_Overview_Function (IN DateFrom date, IN DateTo date) ;

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Purchase_Order_Status_Overview_Function (IN DateFrom date, IN DateTo date) 
RETURNS TABLE 
(
	Confirmed numeric,
	Allocated numeric,
	Issued numeric,
	Received numeric,
	Invoiced numeric
	
)
AS
$$


SELECT  
(
	 SUM(x.Confirmed)/(GREATEST( Count(x.Confirmed), 1))
	 
) AS Confirmed,

(
	 SUM(x.Allocated)/(GREATEST( Count(x.Confirmed), 1))
	 
) AS Allocated,
	
 
(
	 SUM(x.Issued)/(GREATEST( Count(x.Confirmed), 1))
	 
) AS Issued,

(
	 SUM(x.Received)/(GREATEST( Count(x.Confirmed), 1))
	 
) AS Received,
 
(
	 SUM(x.Invoiced)/(GREATEST( Count(x.Confirmed), 1))
	 
) AS Invoiced


FROM

(
SELECT 

(
CASE 
	WHEN o.DocStatus = 'CO' 
	THEN 1
	ELSE 0
	END
) :: numeric AS Confirmed,

(
CASE 
	WHEN COUNT(ol.C_OrderLine_ID) > 0
	THEN  SUM (LEAST(COALESCE(rsa.QtyAllocated, 0)/GREATEST(ol.QtyEntered,1), 1 :: numeric )) / COUNT(ol.C_OrderLine_ID)
	ELSE 0
	END	
) :: numeric AS Allocated,

(
CASE 
	WHEN COUNT(ol.C_OrderLine_ID) > 0
	THEN  SUM (LEAST(COALESCE(rsa.QtyWithIssues, 0)/GREATEST(ol.QtyEntered,1), 1 :: numeric )) / COUNT(ol.C_OrderLine_ID)
	ELSE 0
	END	
) :: numeric AS Issued,

(
CASE
	WHEN COUNT(ol.C_OrderLine_ID) > 0
	THEN SUM( LEAST(COALESCE( iol.QtyEntered,0)/GREATEST(ol.QtyEntered,1),1 :: numeric)) / COUNT(ol.C_OrderLine_ID)
	ELSE 0
	END
) :: numeric AS Received,
(
CASE
	WHEN COUNT(ol.C_OrderLine_ID) > 0
	THEN SUM( LEAST(COALESCE( il.QtyEntered, 0)/ GREATEST(ol.QtyEntered,1),1 :: numeric)) / COUNT(ol.C_OrderLine_ID)
	ELSE 0
	END
) :: numeric AS Invoiced




FROM C_Order o
JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID AND ol.IsPackagingMaterial = 'N' 
LEFT JOIN M_ReceiptSchedule rs ON ol.C_OrderLine_ID = rs.C_OrderLine_ID  
LEFT JOIN M_ReceiptSchedule_Alloc  rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID  AND rsa.isActive = 'Y' 
LEFT JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID
LEFT JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.DocStatus = 'CO'
LEFT JOIN C_InvoiceLine il ON ol.C_OrderLine_ID = il.C_OrderLine_ID
LEFT JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.DocStatus IN ('CO','CL')


WHERE o.DateOrdered >= $1 AND o.DateOrdered <= $2  AND o.IsSoTrx = 'N'

GROUP BY o.C_Order_ID

) x
$$
LANGUAGE sql STABLE;