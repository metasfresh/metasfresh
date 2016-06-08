DROP FUNCTION IF EXISTS KPI_Sales_Order_Status_Function (IN C_Order_ID numeric) ;
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Sales_Order_Status_Function (IN C_Order_ID numeric) ;

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Sales_Order_Status_Function (IN C_Order_ID numeric) 
RETURNS TABLE 
(
	Confirmed numeric,
	Allocated numeric,
	Picked numeric,
	Shipped numeric,
	Invoiced numeric
	
)
AS
$$


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
	WHEN COUNT(ss.M_ShipmentSchedule_ID) > 0
	THEN  SUM (LEAST(COALESCE(ss.QtyOrdered, 0)/GREATEST(ol.QtyEntered,1), 1 :: numeric )) / COUNT(ss.M_ShipmentSchedule_ID)
	ELSE 0
	END	
) :: numeric AS Allocated,
(
CASE
	WHEN COUNT(ss.M_ShipmentSchedule_ID) > 0
	THEN SUM( LEAST(COALESCE( ssp.QtyPicked,0)/ GREATEST(ol.QtyEntered,1),1 :: numeric)) / COUNT(ss.M_ShipmentSchedule_ID)
	ELSE 0
	END
) :: numeric AS Picked,
(
CASE
	WHEN COUNT(ss.M_ShipmentSchedule_ID) > 0
	THEN SUM( LEAST(COALESCE( iol.QtyEntered,0)/GREATEST(ol.QtyEntered,1),1 :: numeric)) / COUNT(ss.M_ShipmentSchedule_ID)
	ELSE 0
	END
) :: numeric AS Shipped,
(
CASE
	WHEN COUNT(ss.M_ShipmentSchedule_ID) > 0
	THEN SUM( LEAST(COALESCE( il.QtyEntered, 0)/ GREATEST(ol.QtyEntered,1),1 :: numeric)) / COUNT(ss.M_ShipmentSchedule_ID)
	ELSE 0
	END
) :: numeric AS Invoiced


FROM C_Order o
JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
LEFT JOIN M_ShipmentSchedule ss ON ol.C_OrderLine_ID = ss.C_OrderLine_ID
LEFT JOIN M_ShipmentSchedule_QtyPicked ssp ON ss.M_ShipmentSchedule_ID = ssp.M_ShipmentSchedule_ID
LEFT JOIN M_InOutLine iol ON ol.C_OrderLine_ID = iol.C_OrderLine_ID
LEFT JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.DocStatus = 'CO'
LEFT JOIN C_InvoiceLine il ON ol.C_OrderLine_ID = il.C_OrderLine_ID
LEFT JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.DocStatus IN ('CO', 'CL')

WHERE o.C_Order_ID  =$1 AND o.IsSoTrx = 'Y'

GROUP BY o.C_Order_ID
$$
LANGUAGE sql STABLE;
