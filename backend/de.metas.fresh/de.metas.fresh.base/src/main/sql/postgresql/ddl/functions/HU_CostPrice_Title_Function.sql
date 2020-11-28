DROP FUNCTION IF EXISTS report.HU_CostPrice_Title_Function (IN M_Product_ID numeric(10,0), IN M_Warehouse_ID numeric(10,0), IN ad_language character varying(6));

CREATE OR REPLACE FUNCTION report.HU_CostPrice_Title_Function (IN M_Product_ID numeric(10,0), IN M_Warehouse_ID numeric(10,0), IN ad_language character varying(6))

RETURNS TABLE
(
	Product character varying,
	Warehouse character varying
)


AS
$$
SELECT
	
	(SELECT trim(Value || ' ' || COALESCE(pt.Name, p.Name)) 
		FROM M_Product p
		LEFT JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.ad_language = $3
		WHERE p.M_Product_ID = $1 AND p.isActive = 'Y'
	)AS Product,
	
	(SELECT Name FROM M_Warehouse WHERE M_Warehouse_ID = $2 AND isActive = 'Y'
	)AS Warehouse
;
$$
LANGUAGE sql STABLE

;