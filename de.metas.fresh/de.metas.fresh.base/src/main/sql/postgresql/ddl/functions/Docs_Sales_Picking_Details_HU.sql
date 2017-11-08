
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Details_HU ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) );

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Details_HU ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE
(
	MovementQty numeric,
	Name Character Varying,
	UOMSymbol Character Varying (10)
)
AS
$$
SELECT
	SUM(ol.QtyEntered)			AS MovementQty,
	COALESCE(pt.Name, p.name)		AS Name,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol)	AS UOMSymbol
FROM
	C_Order o
	INNER JOIN C_OrderLine ol 			ON o.C_Order_ID = ol.C_Order_ID AND ol.isActive = 'Y'
	-- Product and its translato.
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translato.
	LEFT OUTER JOIN C_UOM uom			ON ol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	--ordering gebinde if config exists
	LEFT OUTER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON o.C_DocType_ID = dt.C_DocType_ID and dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocLine_Sort dls ON dt.DocBaseType = dls.DocBaseType AND dls.isActive = 'Y'
		AND EXISTS (
			SELECT 0 FROM C_BP_DocLine_Sort bpdls 
			WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID AND bpdls.C_BPartner_ID = bp.C_BPartner_ID AND bpdls.isActive = 'Y'
		) 
	LEFT OUTER JOIN C_DocLine_Sort_Item dlsi ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = ol.M_Product_ID AND dlsi.isActive = 'Y'
	
WHERE
	o.C_Order_ID = $1 AND o.isActive = 'Y'
	AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
	AND QtyEntered != 0 -- Don't display lines without a Qty. See 08293
GROUP BY
	 COALESCE(pt.Name, p.name), COALESCE(uomt.UOMSymbol, uom.UOMSymbol), dlsi.SeqNo, ol.description
ORDER BY 
	dlsi.SeqNo NULLS LAST
	
$$
LANGUAGE sql STABLE
;