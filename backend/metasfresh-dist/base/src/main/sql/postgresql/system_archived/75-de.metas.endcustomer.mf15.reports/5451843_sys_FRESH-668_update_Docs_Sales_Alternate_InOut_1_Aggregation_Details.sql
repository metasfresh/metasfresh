DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details
(
	Line Numeric (10,0), 
	Attributes Text,
	Value Character Varying,
	Name Character Varying,
	EAN Character Varying,
	PriceList Numeric,
	Capacity Numeric,
	PriceEntered Numeric,
	LineAmt Numeric,
	QtyEnteredTU Numeric,
	QtyEntered Numeric,
	Container Character Varying (60),
	UOMSymbol Character Varying (10),
	StdPrecision Numeric (10,0)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details AS
$$
SELECT
	OL_Line AS Line, -- 08964 The report shall display, and be ordered by the order line number
	attributes, value, name, EAN, pricelist, capacity, Priceentered,
	SUM(LineAmt) AS LineAmt, SUM(QtyEnteredTU) AS QtyEnteredTU, SUM(QtyEntered) AS QtyEntered,
	container, UOMSymbol, stdPrecision
FROM
(
SELECT
	ic.line AS OL_Line, -- ic.Line = C_OrderLine.Line
	iol.line AS IOL_Line, 
	ic.documentno AS O_DocNo, --ic.DocumentNo = C_Order.DocumentNo
	-- 08964 moved the attribute sub select here, to have the ASI ID ready. this way an index scan is possible
	(
		SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes --, M_AttributeSetInstance_ID 
		FROM 	Report.fresh_Attributes
		WHERE	M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID
			AND at_value IN ('1000015', '1000001') -- Marke (ADR), Herkunft
		GROUP BY	M_AttributeSetInstance_ID
	) AS Attributes,
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
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	uom.stdPrecision
FROM
	-- Begin from a prefiltered InOut Table so InOutLines can be joined using the Index
	(SELECT * FROM M_InOut io WHERE io.DocStatus = 'CO' AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 AND isActive = 'Y' ) AND io.isActive = 'Y' ) io
	INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	
	-- Invoice Candidate and orderline for prices and for order-by-data
	LEFT OUTER JOIN (
		SELECT 	ic.PriceEntered_Override, ic.PriceEntered, ic.PriceActual_Override, ic.PriceActual,
			ic.Discount_Override, ic.Discount, ol.Pricelist, ic.Price_UOM_ID, iciol.M_InOutLine_ID,
			ol.line, o.documentno
		FROM 	C_InvoiceCandidate_InOutLine iciol
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
				AND ic.AD_Table_ID = (SELECT Get_Table_ID('C_OrderLine')) -- needs to be here for index scan
			LEFT OUTER JOIN C_Orderline ol ON ic.Record_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
			LEFT OUTER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
		WHERE  iciol.isActive = 'Y'
	) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID
	-- Get Packing instruction
	LEFT OUTER JOIN (
		SELECT DISTINCT
			COALESCE ( iol.M_HU_PI_Item_Product_ID, tu.M_HU_PI_Item_Product_ID ) AS M_HU_PI_Item_Product_ID, iol.M_InOutLine_ID
		FROM	
			(SELECT * FROM M_InOut io WHERE io.DocStatus = 'CO' AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1  AND isActive = 'Y')  AND io.isActive = 'Y') io
			INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
			LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
				AND asgn.Record_ID = iol.M_InOutLine_ID AND asgn.isActive = 'Y'
			LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
	) pi 	ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
	LEFT OUTER JOIN M_HU_PI_Item_Product piip 	ON pi.M_HU_PI_Item_Product_ID = piip.M_HU_PI_Item_Product_ID AND piip.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pii 		ON piip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID AND pii.isActive = 'Y'
	-- Get Packing Material 
	LEFT OUTER JOIN M_HU_PI_Item pmi 		ON pii.M_HU_PI_Version_ID = pmi.M_HU_PI_Version_ID AND pmi.ItemType= 'PM' AND pmi.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PackingMaterial pm 	ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID AND pm.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ic.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ic.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Conversion conv		ON conv.C_UOM_ID = iol.C_UOM_ID
		AND conv.C_UOM_To_ID = ic.Price_UOM_ID
		AND iol.M_Product_ID = conv.M_Product_ID
		AND conv.isActive = 'Y'
	-- Moved attributes in the select block, to provide the view with an M_AttributeSetInstance_ID so it can index scan
WHERE
	-- Moved the filter on M_InOut_ID into a subquery to make sure, M_InOut isretrieved first, to prevent a Seq Scan on M_InOutLine_ID
	pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
) x
GROUP BY
	OL_Line, O_DocNo, attributes, value, name, EAN, pricelist, capacity, Priceentered, container, UOMSymbol, stdPrecision
ORDER BY
	OL_Line, O_DocNo, value
$$
LANGUAGE sql STABLE
;
