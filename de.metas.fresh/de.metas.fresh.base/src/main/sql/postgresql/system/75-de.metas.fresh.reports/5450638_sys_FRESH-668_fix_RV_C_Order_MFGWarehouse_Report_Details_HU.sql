drop view if exists report.RV_C_Order_MFGWarehouse_Report_Details_HU;
create or replace view report.RV_C_Order_MFGWarehouse_Report_Details_HU
AS
SELECT DISTINCT
	olpm.line as Line,
	COALESCE(bpp.ProductNo, ppm.value) AS ProductValue,
	COALESCE(bpp.ProductName, ppm.Name) AS ProductName,
	COALESCE(bpp.UPC, ppm.UPC) AS EAN,
	olpm.pricelist,
	olpm.Priceactual AS PriceActual,
	olpm.qtyenteredtu,
	olpm.qtyordered,
	uom.UOMSymbol AS UOMSymbol,
	--
	-- Filtering columns
	report.C_Order_MFGWarehouse_Report_ID,
	report.DocumentType as ReportDocumentType,
	reportLine.C_Order_MFGWarehouse_ReportLine_ID,
	o.C_Order_ID,
	ol.C_OrderLine_ID,
	report.M_Warehouse_ID,
	report.PP_Plant_ID,
	o.C_BPartner_ID,
	o.DatePromised
FROM
	C_Order_MFGWarehouse_Report report
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID)
	INNER JOIN C_Order_MFGWarehouse_ReportLine reportLine on (reportLine.C_Order_MFGWarehouse_Report_ID=report.C_Order_MFGWarehouse_Report_ID)
	INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = reportLine.C_OrderLine_ID)
	--
	LEFT OUTER JOIN C_OrderLine olpm ON ol.C_PackingMaterial_OrderLine_ID = olpm.C_OrderLine_ID

	LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID =  bp.C_BPartner_ID

	-- TAKE THE M_HU_PI_Item_Product_ID OF THE PACKING MATERIAL LINE
	LEFT OUTER JOIN M_HU_PI_Item_Product ip ON olpm.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pii ON ip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
	LEFT OUTER JOIN M_HU_PI_Item pmi ON pmi.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID
		AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID

	-- Product and its translation FROM THE ORIGINAL ORDER LINE BECAUSE WE NEED IT IN WHERECLAUSE
	LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID

	LEFT OUTER JOIN M_Product ppm ON olpm.M_Product_ID = ppm.M_Product_ID

	-- JUST IN CASE THERE IS A BPP FOR THE PACKING MATERIAL
	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
		AND ppm.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'

	LEFT OUTER JOIN M_Product_Category pc ON ppm.M_Product_Category_ID = pc.M_Product_Category_ID

	-- Unit of measurement and its translation FROM THE PACKING MATERIAL LINE
	LEFT OUTER JOIN C_UOM uom ON olpm.C_UOM_ID = uom.C_UOM_ID

	-- ADR Attribute FROM THE PACKING MATERIAL LINE
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( ai_value, ', ' ) AS Attributes, M_AttributeSetInstance_ID
		FROM 	Report.fresh_Attributes
		WHERE	at_Value IN ( '1000015', '1000001' ) -- Marke (ADR), task 08891: also Herkunft
		GROUP BY	M_AttributeSetInstance_ID
	) att ON olpm.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
		AND olpm.M_AttributeSetInstance_ID != 0


WHERE
	1=1
	AND report.IsActive='Y' and reportLine.IsActive='Y'
	AND pc.M_Product_Category_ID = (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND o.IsSOTrx != 'N'
	AND o.DocStatus = 'CO'
/*
ORDER BY
	line,
	-- When no order document is given, sort to aggregate
	COALESCE(bpp.ProductName, ppm.Name),
	uom.UOMSymbol,
	olpm.Pricelist,
	olpm.PriceActual
*/
;


