DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Top_Customer_Function (IN dateFrom date, IN dateTo date) ;
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Top_Customer_Function (IN dateFrom date, IN dateTo date) 
RETURNS TABLE 
(
	revenue numeric,
	revenuek character varying,
	ordersNo numeric,
	bpartnerName character varying
)
AS
$$
SELECT  bp.revenue,
	(CASE WHEN 
		bp.revenue>=1000 THEN (bp.revenue/1000)::integer|| 'K' 
	ELSE 
		(bp.revenue::integer)::character varying	
	END) AS revenuek,
	bp.ordersNo::numeric,
	bp.bpartnerName
FROM 
(
SELECT 
	SUM(fa.amtacctdr-fa.amtacctcr) AS revenue, 
	(SELECT COUNT(o.C_ORDER_ID) FROM C_Order o WHERE bp.C_BPartner_ID = o.C_BPartner_ID AND o.issotrx='Y' AND o.dateOrdered>=$1 AND o.dateOrdered<=$2) AS ordersNo,

	bp.Name AS bpartnerName
FROM C_BPartner bp
JOIN C_BP_Customer_Acct bpa ON bpa.C_BPartner_ID = bp.C_BPartner_ID
JOIN C_ValidCombination vc ON vc.C_ValidCombination_ID= bpa.C_Receivable_Acct

JOIN C_Invoice i ON bp.C_BPartner_ID = i.C_BPartner_ID
JOIN Fact_Acct fa ON vc.Account_ID = fa.Account_ID AND i.C_Invoice_ID = fa.Record_ID AND fa.AD_Table_ID = get_Table_ID('C_Invoice') 

WHERE i.issotrx='Y' 
AND i.dateInvoiced>=$1 AND i.dateInvoiced<=$2 AND i.docStatus IN ('CO','CL')

GROUP BY 
	bp.Name,
	bp.C_BPartner_ID
)bp

ORDER BY bp.revenue DESC

$$
LANGUAGE sql STABLE;
