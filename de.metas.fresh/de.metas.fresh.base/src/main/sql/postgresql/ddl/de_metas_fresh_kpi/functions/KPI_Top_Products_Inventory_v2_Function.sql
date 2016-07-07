
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Top_Products_Inventory_v2_Function (IN CurrentDate date, IN ComparisonDate date) ;

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Top_Products_Inventory_v2_Function (IN CurrentDate date, IN ComparisonDate date) 
RETURNS TABLE 
(
	p_value character varying,
	p_name character varying,
	CurrentQty numeric,
	ComparisonQty numeric
)
AS
$$

SELECT 
	current.p_value,
	current.p_name,
	current.sum as CurrentQty, 
	comparison.sum as ComparisonQty
FROM 
	(
		SELECT 
			p_value, 
			p_name,
			SUM(qty) 
		FROM HU_CostPrice_Function($1,null, null, null) as sum
		GROUP BY p_value, p_name
	) current

LEFT JOIN 
	(
		SELECT 
			p_value, 
			p_name,
			SUM(qty) 
		FROM HU_CostPrice_Function($2, null, null, null) 
		GROUP BY p_value, p_name
	) comparison 
	ON comparison.p_value=current.p_value
	
WHERE current.sum != 0

ORDER BY current.sum DESC

LIMIT 10


$$
LANGUAGE sql STABLE;