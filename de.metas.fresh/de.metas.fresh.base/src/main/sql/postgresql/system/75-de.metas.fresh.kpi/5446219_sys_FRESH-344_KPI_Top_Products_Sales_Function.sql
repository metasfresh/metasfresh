DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Top_Products_Sales_Function (IN dateFrom date, IN dateTo date) ;
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Top_Products_Sales_Function (IN dateFrom date, IN dateTo date) 
RETURNS TABLE 
(
	productName character varying,
	soldProductsNo numeric,
	soldProductsNoK character varying
)
AS
$$
SELECT  p.productName,
	p.soldProductsNo,
	(CASE WHEN 
		p.soldProductsNo>=1000 THEN (p.soldProductsNo/1000)::integer|| 'K' 
	ELSE 
		(p.soldProductsNo::integer)::character varying	
	END) AS soldProductsNoK
FROM 
(
SELECT 
	p.Name as productName,
	SUM(iol.qtyEntered) AS soldProductsNo

FROM M_Product p
INNER JOIN M_InoutLine iol ON p.M_Product_ID = iol.M_Product_ID AND iol.isPackagingMaterial = 'N'
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID

INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID 


WHERE io.issotrx='Y' 
	AND io.MovementDate>=$1 AND io.MovementDate<=$2 
	AND io.docStatus IN ('CO','CL')
	--if we don't want retournables
	AND dt.docbasetype='MMS' AND dt.docsubtype='MS'

GROUP BY 
	p.Name
	, p.M_Product_ID
)p

ORDER BY p.soldProductsNo DESC

$$
LANGUAGE sql STABLE;