
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
			AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1)
			AND AD_Org_ID = ( SELECT AD_Org_ID FROM M_InOut WHERE M_InOut_ID = $1)
			AND C_BPartner_ID = ( SELECT C_BPartner_ID FROM M_InOut WHERE M_InOut_ID = $1) 
			AND io.isActive = 'Y' 
	) io
	LEFT OUTER JOIN M_InOutLine iol	ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN (
		SELECT 	AVG(ic.PriceEntered_Override) AS PriceEntered_Override, AVG(ic.PriceEntered) AS PriceEntered,
			AVG(ic.PriceActual_Override) AS PriceActual_Override, AVG(ic.PriceActual) AS PriceActual,
			AVG(ic.Discount_Override) AS Discount_Override, AVG(ic.Discount) AS Discount, ic.Price_UOM_ID, iol.M_InOutLine_ID
		FROM 	
			(SELECT * FROM M_InOut io WHERE io.DocStatus = 'CO' 
										AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 )  
										AND AD_Org_ID = ( SELECT AD_Org_ID FROM M_InOut WHERE M_InOut_ID = $1 )
										AND C_BPartner_ID = ( SELECT C_BPartner_ID FROM M_InOut WHERE M_InOut_ID = $1 )
										AND io.isActive = 'Y') io
			LEFT OUTER JOIN M_InOutLine iol	ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
			INNER JOIN C_InvoiceCandidate_InOutLine iciol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iciol.isActive = 'Y'
			INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
				AND ic.AD_Table_ID = (SELECT Get_Table_ID('C_OrderLine')) -- needs to be here for index scan
			LEFT OUTER JOIN C_Orderline ol ON ic.Record_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
			LEFT OUTER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
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
					(SELECT * FROM M_InOut io WHERE io.DocStatus = 'CO' 
													AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1)  
													AND AD_Org_ID = ( SELECT AD_Org_ID FROM M_InOut WHERE M_InOut_ID = $1)  
													AND C_BPartner_ID = ( SELECT C_BPartner_ID FROM M_InOut WHERE M_InOut_ID = $1)  
													AND io.isActive = 'Y') io
					LEFT OUTER JOIN M_InOutLine iol	ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
					-- Get PI directly from InOutLine (1 to 1) 
					LEFT OUTER JOIN M_HU_PI_Item_Product pi ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
					-- Get PI from HU assignments (1 to n)
					LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
						AND asgn.Record_ID = iol.M_InOutLine_ID AND asgn.isActive = 'Y'
					LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
					LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
				WHERE
					COALESCE ( pi.name, pifb.name ) != 'VirtualPI'
			) x
		GROUP BY M_InOutLine_ID
	) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
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
	-- Attributes
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID 
		FROM 	Report.fresh_Attributes
		WHERE	at_value IN ('1000015', '1000001') -- Marke (ADR), Herkunft
		GROUP BY M_AttributeSetInstance_ID
	) att ON iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
WHERE
	pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y')
	AND QtyEntered != 0 -- Don't display lines without a Qty. See fresh_08293
) x
GROUP BY
	CustomerArticleNumber, name, attributes, HUName, UOMSymbol, QtyPattern, transp
ORDER BY
	CustomerArticleNumber, transp, name 
$$
LANGUAGE sql STABLE
;
