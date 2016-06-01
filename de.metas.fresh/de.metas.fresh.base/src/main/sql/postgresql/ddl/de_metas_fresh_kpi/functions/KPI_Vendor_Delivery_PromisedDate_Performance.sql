--DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Vendor_Delivery_PromisedDate_Performance(IN startdate DATE, IN enddate DATE);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Vendor_Delivery_PromisedDate_Performance(IN startdate DATE, IN enddate DATE)
RETURNS TABLE
(
	c_bpartner_id numeric,
	name character varying,
	deliveredPastPromised numeric
) 
AS
$$
SELECT c_bpartner_id, name, deliveredPastPromisedPercent FROM
(
SELECT c_bpartner_id, name, 
	((SUM(deliveredPastPromised) / SUM(deliveredPastPromised + deliveredOnPromised) )*100) ::numeric(10,1) AS deliveredPastPromisedPercent
FROM
(
	SELECT 
		bp.c_bpartner_id, bp.Name
		, CASE WHEN io.MovementDate > ol.datePromised THEN iol.movementqty ELSE 0 END AS deliveredPastPromised
		, CASE WHEN io.MovementDate <= ol.datePromised THEN iol.movementqty ELSE 0 END AS deliveredOnPromised
	FROM C_BPartner bp

	INNER JOIN C_Order o ON bp.C_BPartner_ID = o.C_BPartner_ID
	INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
	INNER JOIN M_InOutLine iol ON ol.C_OrderLine_ID = iol.C_OrderLine_ID
	INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
	
	WHERE 
		o.isSOTrx='N'
		AND ol.isPackagingMaterial = 'N'
		AND ol.datePromised >=$1 AND ol.datePromised <= $2
		AND o.docstatus IN ('CO', 'CL') AND io.docstatus IN ('CO', 'CL')
		
	GROUP BY bp.c_bpartner_id, bp.Name, io.MovementDate, ol.datePromised, iol.movementqty
	
	ORDER BY bp.Name
)d

GROUP BY c_bpartner_id, name

)rez 
WHERE deliveredPastPromisedPercent > 0
ORDER BY deliveredPastPromisedPercent DESC
$$
LANGUAGE sql STABLE;
