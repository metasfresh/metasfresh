--DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Top_Performers_Purchase(IN startdate DATE, IN enddate DATE);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Top_Performers_Purchase(IN startdate DATE, IN enddate DATE)
RETURNS TABLE
(
	ad_user_id numeric(10,0),
	userName character varying(60),
	tablename character varying(60),
	LinesNo character varying
)
AS
$$
SELECT 
	ad_user_id
	,Name
	,TableName
	,(CASE WHEN linesno >=1000 THEN (linesno::numeric/1000)::numeric(10,1)|| 'K' 	
	ELSE 
		(linesno::numeric)::character varying	
	END) AS LinesNo

	
FROM (
SELECT 
	u.AD_User_ID
	,u.Name
	,(SELECT name FROM AD_Table WHERE tablename= 'C_OrderLine') AS TableName
	,COUNT(ol.C_OrderLine_ID) AS linesNo
FROM AD_User u
INNER JOIN C_OrderLine ol ON u.AD_User_ID = ol.CreatedBy
INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
WHERE o.issotrx='N' AND o.DocStatus NOT IN ('IP','VO') AND
	ol.created >=$1 AND ol.created <=$2
GROUP BY u.AD_User_ID
	,u.Name
	
UNION ALL
(
SELECT 
	u.AD_User_ID
	,u.Name
	,(SELECT name FROM AD_Table WHERE tablename= 'M_InOutLine') AS TableName
	,COUNT(iol.M_InOutLine_ID) AS linesNo
FROM AD_User u
INNER JOIN M_InOutLine iol ON u.AD_User_ID = iol.CreatedBy
INNER JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID
WHERE io.issotrx='N' AND io.DocStatus NOT IN ('IP','VO') AND
	iol.created >=$1 AND iol.created <=$2
GROUP BY u.AD_User_ID
	,u.Name
)

UNION ALL
(
SELECT 
	u.AD_User_ID
	,u.Name
	,(SELECT name FROM AD_Table WHERE tablename= 'C_InvoiceLine') AS TableName
	,COUNT(il.C_Invoice_ID) AS linesNo
FROM AD_User u
INNER JOIN C_InvoiceLine il ON u.AD_User_ID = il.CreatedBy
INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
WHERE i.issotrx='N' AND i.DocStatus NOT IN ('IP','VO') AND
	il.created >=$1 AND il.created <=$2
GROUP BY u.AD_User_ID
	,u.Name
)
)l
ORDER BY tablename, l.linesno DESC

$$
LANGUAGE sql STABLE;
