DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Purchase_Price_Range (IN startdate DATE, IN enddate DATE);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Purchase_Price_Range (IN startdate DATE, IN enddate DATE)
RETURNS TABLE
(
	max_product_value character varying,
	max_product_name character varying,
	max_price numeric,
	averagePriceMax numeric,
	min_product_value character varying,
	min_product_name character varying,
	min_price numeric,
	averagePriceMin numeric
	
	
)
AS
$$
SELECT
max(max_product_value) as max_product_value, max(max_product_name) as max_product_name, max(max_price) as max_price, max(averagePriceMax) as averagePriceMax,
max(min_product_value) as min_product_value, max(min_product_name) as min_product_name, max(min_price) as min_price, max(averagePriceMin) as averagePriceMin
FROM
( 
(SELECT 
product_value as max_product_value, null as min_product_value, 
product_name AS max_product_name, null as min_product_name,
max_price as max_price, null as min_price,
averagePrice as averagePriceMax,
null as averagePriceMin

FROM de_metas_fresh_kpi.KPI_Purchase_Price_Range_Sub($1, $2)
ORDER BY max_price DESC LIMIT 1
)
UNION ALL
(
SELECT
null as max_product_value, product_value as min_product_value, 
null as max_product_name, product_name AS min_product_name, 
null as max_price, min_price as min_price,
null as averagePriceMax,
averagePrice as averagePriceMin

from de_metas_fresh_kpi.KPI_Purchase_Price_Range_Sub($1, $2)
ORDER BY min_price ASC LIMIT 1
)
)rez
$$
LANGUAGE sql STABLE;