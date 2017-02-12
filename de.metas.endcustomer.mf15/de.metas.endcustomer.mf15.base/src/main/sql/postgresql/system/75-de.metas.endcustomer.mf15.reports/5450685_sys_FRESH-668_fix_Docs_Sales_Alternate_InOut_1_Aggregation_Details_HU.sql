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
	LEFT OUTER JOIN C_BPartner_Product bpp		ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
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