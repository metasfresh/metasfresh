DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details
(
	line numeric(10,0),
	Attributes Text,
	Value Character Varying,
	Name Character Varying,
	EAN Character Varying,
	PriceList numeric,
	Capacity numeric,
	PriceEntered numeric,
	LineAmt numeric,
	QtyEnteredTU numeric,
	QtyEntered numeric,
	Container Character Varying(60),
	UOMSymbol Character Varying(10)	
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Details AS
$$
SELECT
	iol.line,
	att.Attributes,
	COALESCE(bpp.ProductNo, p.value) AS Value,
	COALESCE(pt.Name, p.name) AS Name,
	COALESCE(bpp.UPC, p.UPC) AS EAN,
	ic.PriceList,
	piip.qty AS capacity,
	COALESCE(ic.PriceEntered_Override, ic.PriceEntered) AS PriceEntered,
	COALESCE(ic.PriceEntered_Override, ic.PriceEntered) * iol.MovementQty * COALESCE (multiplyrate, 1) AS lineamt,
	iol.QtyEnteredTU,
	QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
	pm.name as container,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol
FROM
	M_InOutLine iol
	INNER JOIN M_InOut io 			ON iol.M_InOut_ID = io.M_InOut_ID
	LEFT OUTER JOIN C_BPartner bp			ON io.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN (
		SELECT 	ic.PriceEntered_Override, ic.PriceEntered, ic.PriceActual_Override, ic.PriceActual,
			ic.Discount_Override, ic.Discount, ol.Pricelist, ic.Price_UOM_ID, iciol.M_InOutLine_ID
		FROM 	C_InvoiceCandidate_InOutLine iciol
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
			LEFT OUTER JOIN C_Orderline ol ON ic.AD_Table_ID = (SELECT Get_Table_ID('C_OrderLine'))
				AND ic.Record_ID = ol.C_OrderLine_ID
	) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID
	-- Get Packing instruction
	LEFT OUTER JOIN (
		SELECT DISTINCT
			COALESCE ( iol.M_HU_PI_Item_Product_ID, tu.M_HU_PI_Item_Product_ID ) AS M_HU_PI_Item_Product_ID, iol.M_InOutLine_ID AS M_InOutLine_ID
		FROM	M_InOutLine iol
			LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
				AND asgn.Record_ID = iol.M_InOutLine_ID
			LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
		WHERE	iol.M_InOut_ID = $1
	) pi 	ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
	LEFT OUTER JOIN M_HU_PI_Item_Product piip 	ON pi.M_HU_PI_Item_Product_ID = piip.M_HU_PI_Item_Product_ID AND piip.name != 'VirtualPI' AND piip.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pii 		ON piip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
	LEFT OUTER JOIN M_HU_PI_Item pmi 		ON pii.M_HU_PI_Version_ID = pmi.M_HU_PI_Version_ID AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm 	ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID and bpp.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ic.Price_UOM_ID = uom.C_UOM_ID
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ic.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
	LEFT OUTER JOIN C_UOM_Conversion conv		ON conv.C_UOM_ID = iol.C_UOM_ID
		AND conv.C_UOM_To_ID = ic.Price_UOM_ID
		AND iol.M_Product_ID = conv.M_Product_ID
		AND conv.isActive = 'Y'
	-- Attributes
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( ai_value, ', ' ) AS Attributes, M_AttributeSetInstance_ID
		FROM 	Report.fresh_Attributes
		WHERE	at_value IN ('1000015', '1000001') -- Marke (ADR), Herkunft
		GROUP BY	M_AttributeSetInstance_ID
	) att ON iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
WHERE
	iol.M_InOut_ID = $1
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
ORDER BY
	 iol.line, COALESCE(bpp.ProductNo, p.value)
$$
LANGUAGE sql STABLE
;