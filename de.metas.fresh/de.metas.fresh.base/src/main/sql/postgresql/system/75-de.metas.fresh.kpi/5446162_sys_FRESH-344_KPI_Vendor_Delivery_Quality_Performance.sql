DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Vendor_Delivery_Quality_Performance(IN startdate DATE, IN enddate DATE);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Vendor_Delivery_Quality_Performance(IN startdate DATE, IN enddate DATE)
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
		(SUM(ic.qtyWithIssues)/(SUM(qtyMoved)+SUM(ic.qtyWithIssues)))::numeric(10,2) AS issuePercentage
	FROM
	(
		SELECT 
			ic.C_Invoice_Candidate_ID, bp.name, bp.c_bpartner_id, 
			case when iol.isindispute='Y' THEN iol.movementqty ELSE 0 END AS qtyWithIssues,
			case when iol.isindispute='N' THEN iol.movementqty ELSE 0 END AS qtyMoved
			
		FROM C_BPartner bp
		INNER JOIN C_Invoice_Candidate ic ON bp.C_BPartner_ID = ic.Bill_BPartner_ID

		INNER JOIN C_InvoiceCandidate_InOutLine iciol ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		INNER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID 
		INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID 
 		WHERE ic.issotrx='N' AND ic.isPackagingmaterial = 'N' AND ic.C_OrderLine_ID IS NOT NULL
			AND ic.DateOrdered >= $1 AND ic.DateOrdered <= $2
			AND io.docstatus in ('CO','CL')
		ORDER BY bp.name
	)ic
	GROUP BY ic.name, ic.c_bpartner_id
)rez

WHERE issuePercentage>0
ORDER BY issuePercentage DESC
$$
LANGUAGE sql STABLE;

