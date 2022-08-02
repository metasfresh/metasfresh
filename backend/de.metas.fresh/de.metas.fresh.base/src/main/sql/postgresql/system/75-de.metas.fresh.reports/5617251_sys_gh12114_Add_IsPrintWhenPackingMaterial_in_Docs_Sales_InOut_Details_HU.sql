
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU ( IN p_M_InOut_ID numeric, IN p_AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU
(
	MovementQty numeric,
	Name Character Varying,
	UOMSymbol Character Varying (10),
	Description Character Varying,
	IsPrintWhenPackingMaterial char(1)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU ( IN p_M_InOut_ID numeric, IN p_AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU AS
$$
SELECT
	SUM(iol.QtyEntered)			AS MovementQty,
	COALESCE(pt.Name, p.name)		AS Name,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol)	AS UOMSymbol,
	iol.Description,
	p.IsPrintWhenPackingMaterial
FROM
	M_InOut io
	INNER JOIN M_InOutLine iol 			ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'
	--ordering gebinde if config exists
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID and dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocLine_Sort dls ON dt.DocBaseType = dls.DocBaseType AND dls.isActive = 'Y'
		AND EXISTS (
			SELECT 0 FROM C_BP_DocLine_Sort bpdls 
			WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID AND bpdls.C_BPartner_ID = bp.C_BPartner_ID AND bpdls.isActive = 'Y'
		) 
	LEFT OUTER JOIN C_DocLine_Sort_Item dlsi ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = iol.M_Product_ID AND dlsi.isActive = 'Y'
	
WHERE
	io.M_InOut_ID = p_M_InOut_ID AND io.isActive = 'Y'
	AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
	AND QtyEntered != 0 -- Don't display lines without a Qty. See 08293
GROUP BY
	 COALESCE(pt.Name, p.name), COALESCE(uomt.UOMSymbol, uom.UOMSymbol), dlsi.SeqNo, iol.description, p.IsPrintWhenPackingMaterial
ORDER BY 
	dlsi.SeqNo NULLS LAST
	
$$
LANGUAGE sql STABLE
;