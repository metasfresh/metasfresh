DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_HU(IN record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_HU(IN record_id numeric, IN ad_language Character Varying (6))

RETURNS TABLE
(
	QtyEntered numeric, 
	Name character varying,
	Price numeric, 
	LineNetAmt numeric,  
	UOMSymbol character varying(10)

)
AS
$$
SELECT
	ol.QtyEntered,
	COALESCE(pt.Name, p.name)		AS Name,
	ol.PriceEntered			AS Price,
	ol.linenetamt,
	COALESCE(uom.UOMSymbol, uomt.UOMSymbol)	AS UOMSymbol
FROM
	c_orderline ol
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
WHERE
	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	AND pc.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')

$$
LANGUAGE sql STABLE	
;