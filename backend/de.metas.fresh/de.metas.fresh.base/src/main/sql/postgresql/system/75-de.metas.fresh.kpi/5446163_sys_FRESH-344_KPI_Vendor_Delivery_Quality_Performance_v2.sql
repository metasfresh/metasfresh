DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Vendor_Delivery_Quality_Performance_v2(IN startdate DATE, IN enddate DATE);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Vendor_Delivery_Quality_Performance_v2(IN startdate DATE, IN enddate DATE)
RETURNS TABLE
(
	name character varying
	,c_bpartner_id numeric
	,issuePercentage numeric(10,2)
	
) 
AS
$$
SELECT name,c_bpartner_id,issuePercentage FROM
(
	SELECT
		ic.name,
		ic.c_bpartner_id,
		(SUM(ic.qtywithissues)/SUM(ic.qtydelivered))::numeric(10,2) AS issuePercentage
	FROM
	(
		SELECT 
			ic.C_Invoice_Candidate_ID, bp.name, bp.c_bpartner_id, ic.QtyDelivered, 
			ic.qtywithissues
		FROM C_BPartner bp
		INNER JOIN C_Invoice_Candidate ic ON bp.C_BPartner_ID = ic.Bill_BPartner_ID

		WHERE ic.issotrx='N' AND ic.qtyDelivered != 0 AND ic.isPackagingmaterial = 'N' AND C_OrderLine_ID IS NOT NULL
			AND ic.DateOrdered >= $1 AND ic.DateOrdered <= $2
		ORDER BY bp.name
	)ic
	GROUP BY ic.name, ic.c_bpartner_id
)rez

WHERE issuePercentage>0
ORDER BY issuePercentage DESC
$$
LANGUAGE sql STABLE;
