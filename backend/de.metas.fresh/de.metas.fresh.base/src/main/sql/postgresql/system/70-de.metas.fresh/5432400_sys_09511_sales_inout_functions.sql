

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root
(
	AD_User_ID numeric(10,0),
	AD_Org_ID numeric(10,0),
	M_InOut_ID numeric(10,0),
	DocStatus Character (2),
	C_BPartner_ID numeric(10,0),
	C_BPartner_Location_ID numeric (10,0),
	PrintName Character Varying(60),
	AD_Language Text,
	IsHidePackingMaterialInShipmentPrint Character (1)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Root AS
$$SELECT
	io.ad_user_id,
	io.ad_org_id,
	io.m_inout_id,
	io.docstatus,
	io.c_bpartner_id,
	io.c_bpartner_location_id,
	CASE WHEN io.DocStatus = 'DR'
		THEN dt.printname
		ELSE COALESCE(dtt.printname,dt.printname)
	END AS printname,
	CASE WHEN io.DocStatus IN ('DR', 'IP') THEN 'de_CH' ELSE $2 END AS AD_Language,
	isHidePackingMaterialInShipmentPrint
FROM
	M_InOut io
	JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2
WHERE
	io.M_InOut_ID = $1
$$
LANGUAGE sql STABLE
;










DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum ( IN Record_ID numeric );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum
(
	TotalEntered numeric,
	Discount numeric,
	TotalDiscountAmt numeric,
	NonHUTotal numeric,
	HUTotal numeric,
	GrandTotal numeric,
	ISOCode Character Varying,
	ShowDiscount Text
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum ( IN Record_ID numeric )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Sum AS
$$
SELECT
	sum.TotalEntered,
	round(sum.MaxDiscount, 0) AS Discount,
	sum.TotalDiscountAmt,
	sum.NonHuTotal,
	sum.HuTotal,
	sum.NonHuTotal + sum.HuTotal AS GrandTotal,
	COALESCE(cur.cursymbol,cur.iso_code) AS iso_code,
	CASE WHEN MaxDiscount = MinDiscount AND MaxDiscount != 0 AND COALESCE( MaxDiscount, MinDiscount ) IS NOT NULL
		THEN 'Y' ELSE 'N' END AS ShowDiscount
FROM
	M_InOut io
	INNER JOIN
	(
		SELECT 	presum.M_InOut_ID,
			SUM( CASE WHEN NOT isHULine THEN PriceEntered * QtyEntered ELSE 0 END ) as TotalEntered,
			MAX( CASE WHEN NOT isHULine THEN Discount ELSE NULL END ) AS MaxDiscount,
			MIN( CASE WHEN NOT isHULine THEN Discount ELSE NULL END ) AS MinDiscount,
			SUM( CASE WHEN NOT isHULine THEN (PriceEntered - PriceActual) * QtyEntered ELSE 0 END ) AS TotalDiscountAmt,
			SUM( CASE WHEN NOT isHULine THEN PriceActual * QtyEntered ELSE 0 END ) as NonHuTotal,
			SUM( CASE WHEN isHULine AND bp.isHidePackingMaterialInShipmentPrint = 'N'
				THEN PriceActual * QtyEntered ELSE 0 END ) as HuTotal,
			presum.C_Currency_ID
		FROM	(
				SELECT 	iol.M_InOut_ID,
					p.M_Product_Category_ID =
					(
						SELECT value::numeric FROM AD_SysConfig
						WHERE name = 'PackingMaterialProductCategoryID'
					) as IsHULine,
					COALESCE(ic.PriceEntered_Override, ic.PriceEntered) AS PriceEntered,
					COALESCE(ic.Discount_Override, ic.Discount) AS Discount,
					COALESCE(ic.PriceActual_Override, ic.PriceActual) AS PriceActual,
					iol.QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
					ic.C_Currency_ID
				FROM 	M_InOutLine iol
					INNER JOIN C_InvoiceCandidate_InOutLine ic_iol ON ic_iol.M_InOutLine_ID=iol.M_InOutLine_ID
					INNER JOIN C_Invoice_Candidate ic ON ic_iol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
					INNER JOIN M_Product p ON iol.M_product_ID = p.M_Product_ID
					LEFT OUTER JOIN C_UOM_Conversion conv ON conv.C_UOM_ID = iol.C_UOM_ID
						AND conv.C_UOM_To_ID = ic.Price_UOM_ID
						AND iol.M_Product_ID = conv.M_Product_ID
						AND conv.isActive = 'Y'
			) presum
			LEFT OUTER JOIN M_InOut io ON presum.M_InOut_ID = io.M_InOut_ID
			LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
		GROUP BY 	presum.M_InOut_ID, presum.C_Currency_ID
	) sum ON io.M_InOut_ID = sum.M_InOut_ID
	LEFT OUTER JOIN C_Currency cur ON sum.C_Currency_ID = cur.C_Currency_ID
WHERE
	io.M_InOut_ID = $1
$$
LANGUAGE sql STABLE
;











DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU
(
	MovementQty numeric,
	Name Character Varying,
	UOMSymbol Character Varying (10)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU AS
$$
SELECT
	SUM(iol.QtyEntered)			AS MovementQty,
	COALESCE(pt.Name, p.name)		AS Name,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol)	AS UOMSymbol
FROM
	M_InOut io
	INNER JOIN M_InOutLine iol 			ON io.M_InOut_ID = iol.M_InOut_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID
	LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
	--ordering gebinde if config exists
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocLine_Sort dls ON dt.DocBaseType = dls.DocBaseType AND dls.isActive = 'Y'
		AND EXISTS (
			SELECT 0 FROM C_BP_DocLine_Sort bpdls 
			WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID AND bpdls.C_BPartner_ID = bp.C_BPartner_ID 
		) 
	LEFT OUTER JOIN C_DocLine_Sort_Item dlsi ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = iol.M_Product_ID
	
WHERE
	io.M_InOut_ID = $1
	AND pc.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See 08293
GROUP BY
	 COALESCE(pt.Name, p.name), COALESCE(uomt.UOMSymbol, uom.UOMSymbol), dlsi.SeqNo
ORDER BY 
	dlsi.SeqNo NULLS LAST
	
$$
LANGUAGE sql STABLE
;










DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details
(
	Line Numeric (10,0),
	Name Character Varying,
	Attributes Text,
	HUQty Numeric,
	HUName Text,
	qtyEntered Numeric,
	PriceEntered Numeric,
	UOMSymbol Character Varying (10),
	StdPrecision Numeric (10,0),
	LineNetAmt Numeric,
	Discount Numeric,
	IsDiscountPrinted Character (1),
	QtyPattern text
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details AS
$$
SELECT
	iol.line,
	COALESCE(pt.Name, p.name) AS Name,
	CASE WHEN Length( att.Attributes ) > 15
		THEN att.Attributes || E'\n'
		ELSE att.Attributes
	END AS Attributes,
	iol.QtyEnteredTU AS HUQty,
	pi.name AS HUName,
	QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
	COALESCE(ic.PriceEntered_Override, ic.PriceEntered) AS PriceEntered,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	uom.stdPrecision,
	COALESCE(ic.PriceActual_Override, ic.PriceActual) * iol.MovementQty * COALESCE (multiplyrate, 1) AS linenetamt,
	COALESCE(ic.Discount_Override, ic.Discount) AS Discount,
	bp.isDiscountPrinted,
	CASE WHEN StdPrecision = 0 THEN '#,##0' ELSE Substring( '#,##0.000' FROM 0 FOR 7+StdPrecision::integer) END AS QtyPattern
FROM
	M_InOutLine iol
	INNER JOIN M_InOut io 			ON iol.M_InOut_ID = io.M_InOut_ID
	LEFT OUTER JOIN C_BPartner bp			ON io.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN (
		SELECT 	AVG(ic.PriceEntered_Override) AS PriceEntered_Override, AVG(ic.PriceEntered) AS PriceEntered,
			AVG(ic.PriceActual_Override) AS PriceActual_Override, AVG(ic.PriceActual) AS PriceActual,
			AVG(ic.Discount_Override) AS Discount_Override, AVG(ic.Discount) AS Discount, Price_UOM_ID, M_InOutLine_ID
		FROM 	C_InvoiceCandidate_InOutLine iciol
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
		GROUP BY 	Price_UOM_ID, M_InOutLine_ID
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
		WHERE	at_value IN ('1000002', '1000001', '1000030', '1000015') -- Label, Herkunft, Aktionen, Marke (ADR)
		GROUP BY	M_AttributeSetInstance_ID
	) att ON iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
WHERE
	iol.M_InOut_ID = $1
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See 08293
ORDER BY
	line
$$
LANGUAGE sql STABLE
;






DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description 
(
	Description Character Varying (255),
	DocumentNo Character Varying (30),
	MovementDate Timestamp Without Time Zone,
	Reference Character Varying (40),
	BP_Value Character Varying (40),
	Cont_Name Character Varying (40),
	Cont_Phone Character Varying (40),
	Cont_Fax Character Varying (40),
	Cont_Email Character Varying (60),
	SR_Name Text,
	SR_Phone Character Varying (40),
	SR_Fax Character Varying (40),
	SR_Email Character Varying (60),
	PrintName Character Varying (60)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description AS
$$
SELECT
	io.description 	as description,
	io.documentno 	as documentno,
	io.movementdate,
	io.poreference	as reference,
	bp.value	as bp_value,
	Coalesce(cogr.name, '')||
	Coalesce(' ' || cont.title, '') ||
	Coalesce(' ' || cont.firstName, '') ||
	Coalesce(' ' || cont.lastName, '') as cont_name,
	cont.phone	as cont_phone,
	cont.fax	as cont_fax,
	cont.email	as cont_email,
	Coalesce(srgr.name, '')||
	Coalesce(' ' || srep.title, '') ||
	Coalesce(' ' || srep.firstName, '') ||
	Coalesce(' ' || srep.lastName, '') as sr_name,
	srep.phone	as sr_phone,
	srep.fax	as sr_fax,
	srep.email	as sr_email,
	COALESCE ( dtt.printname, dt.printname ) AS printname
FROM
	m_inout io
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2
	INNER JOIN c_bpartner bp ON io.c_bpartner_id	= bp.c_bpartner_id
	LEFT OUTER JOIN AD_User srep ON io.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID<>100
	LEFT OUTER JOIN AD_User cont ON io.AD_User_ID = cont.AD_User_ID
	LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID
	LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID
WHERE
	io.m_inout_id = $1
$$
LANGUAGE sql STABLE
;