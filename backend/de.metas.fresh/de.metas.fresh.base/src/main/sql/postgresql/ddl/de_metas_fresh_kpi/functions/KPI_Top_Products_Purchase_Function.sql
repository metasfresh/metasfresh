--DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Top_Products_Purchase_Function (IN dateFrom date, IN dateTo date) ;
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Top_Products_Purchase_Function (IN dateFrom date, IN dateTo date) 
RETURNS TABLE 
(
	productName character varying,
	purchasedProductsNo numeric,
	purchasedProductsNoK character varying
)
AS
$$
SELECT  p.productName,
	p.purchasedProductsNo,
	(CASE WHEN 
		p.purchasedProductsNo>=1000 THEN (p.purchasedProductsNo/1000)::integer|| 'K' 
	ELSE 
		(p.purchasedProductsNo::integer)::character varying	
	END) AS purchasedProductsNoK
FROM 
(
SELECT 
	p.Name as productName,
	SUM(iol.qtyEntered) AS purchasedProductsNo

FROM M_Product p
INNER JOIN M_InoutLine iol ON p.M_Product_ID = iol.M_Product_ID AND iol.isPackagingMaterial = 'N'
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID

INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID 

WHERE io.issotrx='N' 
	AND io.MovementDate>=$1 AND io.MovementDate<=$2 
	AND io.docStatus IN ('CO','CL')
	--if we don't want returnables
	AND dt.docbasetype='MMR' AND dt.docsubtype='MR'

GROUP BY 
	p.Name
	, p.M_Product_ID
)p

ORDER BY p.purchasedProductsNo DESC

$$
LANGUAGE sql STABLE;
