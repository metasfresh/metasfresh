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
	isPrintTax character(1),
	description character varying,
	documentnote character varying,
	productdescription character varying, 
	bp_product_no character varying(30),
	bp_product_name character varying(100),
	cursymbol character varying(10),
	p_value character varying(40),
	p_description character varying(255),
	order_description character varying(1024),
	c_order_compensationgroup_id numeric,
	isgroupcompensationline character(1),
	groupname  character varying(255),
	iso_code character(3),
	iscampaignprice   character(1)	
)
AS
$$
SELECT
	ol.line,
	COALESCE(pt.Name, p.name)		AS Name,
	CASE WHEN Length( att.Attributes ) > 15
		THEN att.Attributes || E'\n'
		ELSE att.Attributes
	END AS Attributes,
	ol.QtyEnteredTU			AS HUQty,
	CASE WHEN piit.M_HU_PI_Version_ID = 101 OR ol.QtyEnteredTU IS NULL
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
	isPrintTax,
	ol.description,
	ol.M_Product_DocumentNote as documentnote,
	ol.productdescription,
	-- in case there is no C_BPartner_Product, fallback to the default ones
	COALESCE(NULLIF(bpp.ProductNo, ''), p.value) as bp_product_no,
	COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,
	c.cursymbol, 
	p.value AS p_value,
	p.description AS p_description,
	o.description AS order_description,
	ol.c_order_compensationgroup_id,
	ol.isgroupcompensationline,
	cg.name,
	c.iso_code,
	ol.iscampaignprice
FROM
	C_OrderLine ol
	INNER JOIN C_Order o 			ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
	INNER JOIN C_BPartner bp			ON o.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_BP_Group bpg 		ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item_Product ip 		ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item piit ON ip.M_HU_PI_Item_ID = piit.M_HU_PI_Item_ID AND piit.isActive = 'Y'
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

	LEFT OUTER JOIN 
			de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(p.M_Product_ID, bp.C_BPartner_ID, att.M_AttributeSetInstance_ID) as bpp on 1=1
			
	-- compensation group
	LEFT JOIN c_order_compensationgroup cg ON ol.c_order_compensationgroup_id = cg.c_order_compensationgroup_id 
	
	LEFT JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID and c.isActive = 'Y'

WHERE
	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	AND (COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID))
ORDER BY
	ol.line

$$
LANGUAGE sql STABLE	
;