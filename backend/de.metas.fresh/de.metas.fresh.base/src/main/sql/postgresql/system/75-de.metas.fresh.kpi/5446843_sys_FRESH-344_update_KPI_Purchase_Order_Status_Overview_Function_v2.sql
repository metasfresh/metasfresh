DROP FUNCTION IF EXISTS KPI_Purchase_Order_Status_Overview_Function_v2 (IN DateFrom date, IN DateTo date) ;
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Purchase_Order_Status_Overview_Function_v2 (IN DateFrom date, IN DateTo date) ;

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Purchase_Order_Status_Overview_Function_v2 (IN DateFrom date, IN DateTo date) 

RETURNS TABLE 
(
	Confirmed numeric,
	Allocated numeric,
	Picked numeric,
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


WITH result AS (
 SELECT o.*, de_metas_fresh_kpi.KPI_Purchase_Order_Status_Function(o.C_Order_ID) AS OrderStatus
   FROM C_Order o
   WHERE o.DateOrdered >= $1 AND o.DateOrdered <= $2 AND o.IsSoTrx = 'N'

) SELECT *, (result.OrderStatus).* FROM result
) x
$$
LANGUAGE sql STABLE;