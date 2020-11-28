-- 14.07.2015 10:12:24
-- URL zum Konzept
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/sales/alternative_inout_1/report.jasper', Name='Alternativ Lieferschein', Value='Alternativ Lieferschein (Jasper)',Updated=TO_TIMESTAMP('2015-07-14 10:12:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540537
;

-- 14.07.2015 10:12:24
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540537
;

-- 14.07.2015 10:12:54
-- URL zum Konzept
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/sales/alternative_inout_2/report.jasper', Name='Alternativ Lieferschein 2', Value='Alternativ Lieferschein 2 (Jasper)',Updated=TO_TIMESTAMP('2015-07-14 10:12:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540581
;

-- 14.07.2015 10:12:54
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540581
;

-- 14.07.2015 10:13:47
-- URL zum Konzept
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/sales/alternate_inout_1_aggregation/report.jasper', Name='Alternativ Lieferschein Zusammenzug', Value='Alternativ Lsch. Zusammenzug (Jasper)',Updated=TO_TIMESTAMP('2015-07-14 10:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540542
;

-- 14.07.2015 10:13:47
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540542
;

-- 14.07.2015 10:15:07
-- URL zum Konzept
UPDATE AD_Process SET Name='Alternativ Lieferschein 2 Zusammenzug', Value='Alternativ Lsch. 2 Zusammenzug (Jasper)',Updated=TO_TIMESTAMP('2015-07-14 10:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540584
;

-- 14.07.2015 10:15:07
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540584
;

-- 14.07.2015 10:16:31
-- URL zum Konzept
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/sales/alternative_inout_2_aggregation/report.jasper',Updated=TO_TIMESTAMP('2015-07-14 10:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540584
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description
(
	DocumentNo Character Varying,
	Count Bigint,
	MovementDate Timestamp without time zone,
	Reference Character Varying,
	PrintName Character Varying,
	bp_value Character Varying
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description AS
$$
SELECT
	First_Agg( io.documentno ORDER BY io.documentno ) as documentno,
	count( io.DocumentNo ) AS count,
	MIN( io.movementdate ) as MovementDate,
	First_Agg( io.poreference )	as reference,
	First_Agg( COALESCE ( dtt.printname, dt.printname ) ) AS printname,
	First_Agg( bp.value ) AS BP_Value
FROM
	m_inout io
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
WHERE
	io.M_InOut_ID IN (
		SELECT 	M_InOut_ID 
		FROM	M_InOut 
		WHERE 	DocStatus = 'CO' 
			AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 )
	)
$$
LANGUAGE sql STABLE
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details_HU ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details_HU;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details_HU
(
	Value Character Varying,
	Name Character Varying,
	EAN Character Varying,
	MovementQty numeric,
	UOMSymbol Character Varying (10)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details_HU ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Details_HU AS
$$
SELECT
	value, name, EAN,
	SUM(MovementQty) AS MovementQty,
	UOMSymbol
FROM
(
SELECT
	COALESCE(bpp.ProductNo, p.value) AS Value,
	COALESCE(pt.Name, p.Name) AS Name,
	COALESCE(bpp.UPC, p.UPC) AS EAN,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	iol.QtyEntered				AS MovementQty,
	dlsi.SeqNo
FROM
	-- Begin from a prefiltered InOut Table so InOutLines can be joined using the Index
	(	
		SELECT * FROM M_InOut io 
		WHERE io.DocStatus = 'CO' AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 ) 
	) io
	INNER JOIN M_InOutLine iol			ON io.M_InOut_ID = iol.M_InOut_ID
	LEFT OUTER JOIN C_BPartner bp			ON io.C_BPartner_ID = bp.C_BPartner_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID
	LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
	-- Get order by expression
	LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocLine_Sort dls ON dt.DocBaseType = dls.DocBaseType AND dls.isActive = 'Y'
		AND EXISTS (
			SELECT 0 FROM C_BP_DocLine_Sort bpdls 
			WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID AND bpdls.C_BPartner_ID = bp.C_BPartner_ID 
		) 
	LEFT OUTER JOIN C_DocLine_Sort_Item dlsi ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = p.M_Product_ID
WHERE
	-- Moved the filter on M_InOut_ID into a subquery to make sure, M_InOut isretrieved first, to prevent a Seq Scan on M_InOutLine_ID
	pc.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
) x
GROUP BY
	value, name, EAN, UOMSymbol, SeqNo
ORDER BY
	--fresh_09065/fresh_09074 order by config in DocLine_Sort if there is any
	SeqNo NULLS LAST 
$$
LANGUAGE sql STABLE
;

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
	(SELECT * FROM M_InOut io WHERE io.DocStatus = 'CO' AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 ) ) io
	INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
	
	-- Invoice Candidate and orderline for prices and for order-by-data
	LEFT OUTER JOIN (
		SELECT 	ic.PriceEntered_Override, ic.PriceEntered, ic.PriceActual_Override, ic.PriceActual,
			ic.Discount_Override, ic.Discount, ol.Pricelist, ic.Price_UOM_ID, iciol.M_InOutLine_ID,
			ol.line, o.documentno
		FROM 	C_InvoiceCandidate_InOutLine iciol
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
				AND ic.AD_Table_ID = (SELECT Get_Table_ID('C_OrderLine')) -- needs to be here for index scan
			LEFT OUTER JOIN C_Orderline ol ON ic.Record_ID = ol.C_OrderLine_ID
			LEFT OUTER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
	) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID
	-- Get Packing instruction
	LEFT OUTER JOIN (
		SELECT DISTINCT
			COALESCE ( iol.M_HU_PI_Item_Product_ID, tu.M_HU_PI_Item_Product_ID ) AS M_HU_PI_Item_Product_ID, iol.M_InOutLine_ID
		FROM	
			(SELECT * FROM M_InOut io WHERE io.DocStatus = 'CO' AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 ) ) io
			INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
			LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
				AND asgn.Record_ID = iol.M_InOutLine_ID
			LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
	) pi 	ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
	LEFT OUTER JOIN M_HU_PI_Item_Product piip 	ON pi.M_HU_PI_Item_Product_ID = piip.M_HU_PI_Item_Product_ID
	LEFT OUTER JOIN M_HU_PI_Item pii 		ON piip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
	-- Get Packing Material 
	LEFT OUTER JOIN M_HU_PI_Item pmi 		ON pii.M_HU_PI_Version_ID = pmi.M_HU_PI_Version_ID AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm 	ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ic.Price_UOM_ID = uom.C_UOM_ID
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ic.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
	LEFT OUTER JOIN C_UOM_Conversion conv		ON conv.C_UOM_ID = iol.C_UOM_ID
		AND conv.C_UOM_To_ID = ic.Price_UOM_ID
		AND iol.M_Product_ID = conv.M_Product_ID
		AND conv.isActive = 'Y'
	-- Moved attributes in the select block, to provide the view with an M_AttributeSetInstance_ID so it can index scan
WHERE
	-- Moved the filter on M_InOut_ID into a subquery to make sure, M_InOut isretrieved first, to prevent a Seq Scan on M_InOutLine_ID
	pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
) x
GROUP BY
	OL_Line, O_DocNo, attributes, value, name, EAN, pricelist, capacity, Priceentered, container, UOMSymbol, stdPrecision
ORDER BY
	OL_Line, O_DocNo, value
$$
LANGUAGE sql STABLE
;


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
	LEFT OUTER JOIN M_HU_PI_Item_Product piip 	ON pi.M_HU_PI_Item_Product_ID = piip.M_HU_PI_Item_Product_ID AND piip.name != 'VirtualPI'
	LEFT OUTER JOIN M_HU_PI_Item pii 		ON piip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
	LEFT OUTER JOIN M_HU_PI_Item pmi 		ON pii.M_HU_PI_Version_ID = pmi.M_HU_PI_Version_ID AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm 	ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID
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
	INNER JOIN M_InOutLine iol			ON io.M_InOut_ID = iol.M_InOut_ID
	LEFT OUTER JOIN C_BPartner bp			ON io.C_BPartner_ID = bp.C_BPartner_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID
	LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
WHERE
	io.M_InOut_ID = $1
	AND pc.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
ORDER BY 
	 iol.line, COALESCE(bpp.ProductNo, p.value) -- same order as non HU lines. See fresh_09065
$$
LANGUAGE sql STABLE
;


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Aggregation_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Aggregation_Details;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Aggregation_Details
(
	CustomerArticleNumber Character Varying,
	Name Character Varying,
	Attributes Text,
	HUQty Numeric,
	HUName Text,
	qtyEntered Numeric,
	UOMSymbol Character Varying (10),
	QtyPattern text,
	Transp bigint
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Aggregation_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Aggregation_Details AS
$$
SELECT
	CustomerArticleNumber, name, attributes, SUM(HUQty), HUName, SUM(QtyEntered), UOMSymbol, QtyPattern, transp
FROM
(
SELECT
	COALESCE( bpp.ProductNo, p.value ) AS CustomerArticleNumber,
	COALESCE(pt.Name, p.name) AS Name,
	att.Attributes,
	iol.QtyEnteredTU AS HUQty,
	pi.name AS HUName,
	QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	CASE WHEN StdPrecision = 0 THEN '#,##0' ELSE Substring( '#,##0.000' FROM 0 FOR 7+StdPrecision::integer) END AS QtyPattern,
	transp
FROM
	-- Begin from a prefiltered InOut Table so InOutLines can be joined using the Index
	-- assign an alias to each inout: 1st inout = 1, 2nd inout = 2, and so on
	(
		SELECT	*, rank() OVER ( PARTITION BY '' ORDER BY MovementDate, M_InOut_ID ) as transp
		FROM	M_InOut io 
		WHERE 	io.DocStatus = 'CO' 
			AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 ) 
	) io
	LEFT OUTER JOIN M_InOutLine iol	ON io.M_InOut_ID = iol.M_InOut_ID
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN (
		SELECT 	AVG(ic.PriceEntered_Override) AS PriceEntered_Override, AVG(ic.PriceEntered) AS PriceEntered,
			AVG(ic.PriceActual_Override) AS PriceActual_Override, AVG(ic.PriceActual) AS PriceActual,
			AVG(ic.Discount_Override) AS Discount_Override, AVG(ic.Discount) AS Discount, ic.Price_UOM_ID, iol.M_InOutLine_ID
		FROM 	
			(SELECT * FROM M_InOut io WHERE io.DocStatus = 'CO' AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 ) ) io
			LEFT OUTER JOIN M_InOutLine iol	ON io.M_InOut_ID = iol.M_InOut_ID
			INNER JOIN C_InvoiceCandidate_InOutLine iciol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
				AND ic.AD_Table_ID = (SELECT Get_Table_ID('C_OrderLine')) -- needs to be here for index scan
			LEFT OUTER JOIN C_Orderline ol ON ic.Record_ID = ol.C_OrderLine_ID
			LEFT OUTER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
		GROUP BY 
			ic.Price_UOM_ID, iol.M_InOutLine_ID, ol.line, o.documentno
	) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID
	-- Get Packing instruction
	LEFT OUTER JOIN
	(
		SELECT String_Agg( DISTINCT name, E'\n' ORDER BY name ) AS Name, M_InOutLine_ID
		FROM
			(
				SELECT DISTINCT
					-- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
					COALESCE ( pifb.name, pi.name ) AS name,
					iol.M_InOutLine_ID
				FROM
					(SELECT * FROM M_InOut io WHERE io.DocStatus = 'CO' AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 ) ) io
					LEFT OUTER JOIN M_InOutLine iol	ON io.M_InOut_ID = iol.M_InOut_ID
					-- Get PI directly from InOutLine (1 to 1) 
					LEFT OUTER JOIN M_HU_PI_Item_Product pi ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID
					-- Get PI from HU assignments (1 to n)
					LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
						AND asgn.Record_ID = iol.M_InOutLine_ID
					LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
					LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID
				WHERE
					COALESCE ( pi.name, pifb.name ) != 'VirtualPI'
			) x
		GROUP BY M_InOutLine_ID
	) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID
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
		SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID 
		FROM 	Report.fresh_Attributes
		WHERE	at_value IN ('1000015', '1000001') -- Marke (ADR), Herkunft
		GROUP BY M_AttributeSetInstance_ID
	) att ON iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
WHERE
	pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
) x
GROUP BY
	CustomerArticleNumber, name, attributes, HUName, UOMSymbol, QtyPattern, transp
ORDER BY
	CustomerArticleNumber, transp, name 
$$
LANGUAGE sql STABLE
;



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Details;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Details
(
	CustomerArticleNumber Character Varying,
	Name Character Varying,
	Attributes Text,
	HUQty Numeric,
	HUName Text,
	qtyEntered Numeric,
	UOMSymbol Character Varying (10),
	QtyPattern text
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_2_Details AS
$$
SELECT
	COALESCE( bpp.ProductNo, p.value ) AS CustomerArticleNumber,
	COALESCE(pt.Name, p.name) AS Name,
	att.Attributes,
	iol.QtyEnteredTU AS HUQty,
	pi.name AS HUName,
	QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	CASE WHEN StdPrecision = 0 THEN '#,##0' ELSE Substring( '#,##0.000' FROM 0 FOR 7+StdPrecision::integer) END AS QtyPattern
FROM
	M_InOutLine iol
	INNER JOIN M_InOut io 				ON iol.M_InOut_ID = io.M_InOut_ID
	LEFT OUTER JOIN C_BPartner bp			ON io.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN (
		SELECT 	AVG(ic.PriceEntered_Override) AS PriceEntered_Override, AVG(ic.PriceEntered) AS PriceEntered,
			AVG(ic.PriceActual_Override) AS PriceActual_Override, AVG(ic.PriceActual) AS PriceActual,
			AVG(ic.Discount_Override) AS Discount_Override, AVG(ic.Discount) AS Discount, Price_UOM_ID, iol.M_InOutLine_ID
		FROM 	M_InOutLine iol
			INNER JOIN C_InvoiceCandidate_InOutLine iciol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		WHERE
			iol.M_InOut_ID = $1
		GROUP BY 	Price_UOM_ID, iol.M_InOutLine_ID
	) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID
	-- Get Packing instruction
	LEFT OUTER JOIN
	(
		SELECT String_Agg( DISTINCT name, E'\n' ORDER BY name ) AS Name, M_InOutLine_ID
		FROM
			(
				SELECT DISTINCT
					-- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
					COALESCE ( pifb.name, pi.name ) AS name,
					iol.M_InOutLine_ID
				FROM
					M_InOutLine iol
					-- Get PI directly from InOutLine (1 to 1) 
					LEFT OUTER JOIN M_HU_PI_Item_Product pi ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID
					-- Get PI from HU assignments (1 to n)
					LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
						AND asgn.Record_ID = iol.M_InOutLine_ID
					LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
					LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID
				WHERE
					COALESCE ( pi.name, pifb.name ) != 'VirtualPI'
					AND iol.M_InOut_ID = $1
			) x
		GROUP BY M_InOutLine_ID
	) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID
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
		SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID FROM Report.fresh_Attributes
		WHERE	at_value IN ('1000015', '1000001') -- Marke (ADR), Herkunft
		GROUP BY	M_AttributeSetInstance_ID
	) att ON iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
WHERE
	iol.M_InOut_ID = $1
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
ORDER BY
	COALESCE( bpp.ProductNo, p.value ) 
$$
LANGUAGE sql STABLE
;