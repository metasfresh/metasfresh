DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details_HU ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details_HU;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details_HU
(
	line numeric(10,0),
	Value Character Varying,
	Name Character Varying,
	EAN Character Varying,
	MovementQty numeric,
	UOMSymbol Character Varying(10)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details_HU ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details_HU AS
$$
SELECT
	iol.line,
	COALESCE(bpp.ProductNo, p.value) AS Value,
	COALESCE(bpp.ProductName, p.Name) AS Name,
	COALESCE(bpp.UPC, p.UPC) AS EAN,
	iol.QtyEntered				AS MovementQty,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol)		AS UOMSymbol
FROM
	M_InOut io
	INNER JOIN M_InOutLine iol			ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp			ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
WHERE
	io.M_InOut_ID = $1
	AND pc.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
ORDER BY 
	 iol.line, COALESCE(bpp.ProductNo, p.value) -- same order as non HU lines. See fresh_09065
$$
LANGUAGE sql STABLE
;