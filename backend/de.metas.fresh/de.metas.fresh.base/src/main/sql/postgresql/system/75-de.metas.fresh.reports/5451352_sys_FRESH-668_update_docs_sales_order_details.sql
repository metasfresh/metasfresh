DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details(IN record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details(IN record_id numeric, IN ad_language Character Varying (6))

RETURNS TABLE
(
	line numeric(10,0), 
	Name character varying, 
	Attributes text, 
	HUQty numeric, 
	HUName character varying, 
	QtyEnteredInPriceUOM numeric, 
	PriceEntered numeric, 
	UOMSymbol character varying(10), 
	StdPrecision numeric(10,0), 
	linenetamt numeric,
	discount numeric, 
	isDiscountPrinted character(1), 
	rate character varying, 
	isPrintTax character(1)
)
AS
$$
SELECT
	ol.line,
	COALESCE(pt.Name, p.name)		AS Name,
	CASE WHEN Length( Attributes ) > 15
		THEN Attributes || E'\n'
		ELSE Attributes
	END AS Attributes,
	ol.QtyEnteredTU			AS HUQty,
	CASE WHEN ip.name = 'VirtualPI' OR ol.QtyEnteredTU IS NULL
	THEN NULL ELSE ip.name END 		AS HUName,
	ol.QtyEnteredInPriceUOM		AS QtyEnteredInPriceUOM,
	ol.PriceEntered 			AS PriceEntered,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) 	AS UOMSymbol,
	uom.StdPrecision,
	ol.linenetamt			AS linenetamt,
	CASE
		WHEN round(discount, 0) = discount THEN round(discount, 0)
		WHEN round(discount, 1) = discount THEN round(discount, 1)
		ELSE round(discount, 2)
	END as discount,
	bp.isDiscountPrinted,
	CASE
		WHEN round(rate, 0) = rate THEN round(rate, 0)
		WHEN round(rate, 1) = rate THEN round(rate, 1)
		ELSE round(rate, 2)
	END::character varying AS rate,
	isPrintTax
FROM
	C_OrderLine ol
	INNER JOIN C_Order o 			ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
	INNER JOIN C_BPartner bp			ON o.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_BP_Group bpg 		ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item_Product ip 		ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ol.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y' AND uomt.isActive = 'Y'
	-- Tax
	LEFT OUTER JOIN C_Tax t			ON ol.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
	-- Get Attributes
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( att.ai_value, ', ' ORDER BY length(att.ai_value)) AS Attributes, att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
		FROM 	Report.fresh_Attributes att
		JOIN C_OrderLine ol ON att.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
		WHERE	att.at_Value IN ('1000002', '1000001', '1000030', '1000015') AND ol.C_Order_ID = $1
		GROUP BY	att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
	) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND ol.C_OrderLine_ID = att.C_OrderLine_ID
WHERE
	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
ORDER BY
	ol.line

$$
LANGUAGE sql STABLE	
;