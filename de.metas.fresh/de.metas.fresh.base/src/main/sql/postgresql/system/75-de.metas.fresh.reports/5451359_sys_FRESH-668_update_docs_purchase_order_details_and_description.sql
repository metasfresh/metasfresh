DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details(IN record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details(IN record_id numeric, IN ad_language Character Varying (6))

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
	LEFT OUTER JOIN C_BPartner bp			ON ol.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
	INNER JOIN C_BP_Group bpg 			ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item_Product ip 		ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ol.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	-- Tax
	LEFT OUTER JOIN C_Tax t			ON ol.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
	-- Get Attributes
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( att.ai_value, ', ' ORDER BY length(att.ai_value)) AS Attributes, att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
		FROM Report.fresh_Attributes att
		JOIN C_OrderLine ol ON att.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
		WHERE	att.at_Value IN ('1000002', '1000001', '1000030', '1000015', 'M_Material_Tracking_ID') AND ol.C_Order_ID = $1
			-- att.at_IsAttrDocumentRelevant = 'Y' currently those flags are set to be correct for purchase invoices. we need something more flexible for all kinds of documents
		GROUP BY	att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
	) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND ol.C_OrderLine_ID = att.C_OrderLine_ID
WHERE
	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y')
ORDER BY
	ol.line

$$
LANGUAGE sql STABLE	
;













DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Description(IN record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Description(IN record_id numeric, IN ad_language Character Varying (6))

RETURNS TABLE
(
	description character varying(1024),
	documentno character varying(30),
	reference text,
	dateordered timestamp without time zone,
	datepromised timestamp with time zone,
	deliverto character varying(360),
	bp_value character varying(40),
	cont_name text,
	cont_phone character varying(40),
	cont_fax character varying(40),
	cont_email character varying(60),
	sr_name text,
	sr_phone character varying(40),
	sr_fax character varying(40),
	sr_email character varying(60),
	printname character varying(60)
)
AS
$$
SELECT
	o.description 	as description,
	o.documentno 	as documentno,
	trim(o.poreference)	as reference,
	o.dateordered	as dateordered,
	o.datepromised	as datepromised,
	o.DeliveryToAddress 	as deliverto,
	bp.value	as bp_value,
	Coalesce(cogr.name, '')||
	Coalesce(' ' || cont.title, '') ||
	Coalesce(' ' || cont.firstName, '') ||
	Coalesce(' ' || cont.lastName, '') as cont_name,
	cont.phone	as cont_phone,
	cont.fax	as cont_fax,
	cont.email	as cont_email,
	trim(
		Coalesce(srgr.name, '') ||
		Coalesce(' ' || srep.title, '') ||
		Coalesce(' ' || srep.firstName , '')||
		Coalesce(' ' || srep.lastName, '')
	) as sr_name,
	srep.phone	as sr_phone,
	srep.fax	as sr_fax,
	srep.email	as sr_email,
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName
FROM
	C_Order o
	INNER JOIN C_BPartner bp 		ON o.C_BPartner_ID	= bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN AD_User srep		ON o.SalesRep_ID	= srep.AD_User_ID AND srep.AD_User_ID<>100 AND srep.isActive = 'Y'
	LEFT OUTER JOIN AD_User cont		ON o.AD_User_ID 	= cont.AD_User_ID AND cont.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting cogr	ON cont.C_Greeting_ID 	= cogr.C_Greeting_ID AND cogr.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting srgr	ON srep.C_Greeting_ID 	= srgr.C_Greeting_ID AND srgr.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'

WHERE
	o.c_order_id = $1 AND o.isActive = 'Y'
$$
LANGUAGE sql STABLE	
;