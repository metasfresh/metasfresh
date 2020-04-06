drop view if exists report.RV_C_Order_MFGWarehouse_Report_Details;
create or replace view report.RV_C_Order_MFGWarehouse_Report_Details
AS
SELECT
	ol.line,
	att.Attributes,
	COALESCE(bpp.ProductNo, p.value) AS ProductValue,
	COALESCE(bpp.ProductName, p.Name) AS ProductName,
	COALESCE(bpp.UPC, p.UPC) AS EAN,
	-- Rounding these columns is important to have them in one group
	-- Jasper groups by comparing the BigDecimals. In that logic, 1.00 is not the same as 1
	round(ol.pricelist, 3) AS PriceList,
	round(ip.qty, 3) AS Capacity,
	round(ol.Priceactual, 3) AS PriceActual,
	ol.QtyEnteredTU,
	ol.QtyEntered,
	pm.name as Container,
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
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID) AND o.isActive = 'Y'
	INNER JOIN C_Order_MFGWarehouse_ReportLine reportLine on (reportLine.C_Order_MFGWarehouse_Report_ID=report.C_Order_MFGWarehouse_Report_ID)
	INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = reportLine.C_OrderLine_ID) AND ol.isActive = 'Y'
	--
	LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item_Product ip ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pii ON ip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID AND pii.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pmi ON pmi.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID  AND pmi.isActive = 'Y'
		AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID AND pm.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND bpp.isActive='Y'
		AND p.M_Product_ID = bpp.M_Product_ID
	LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	-- ADR Attribute
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( ai_value, ', ' ) AS Attributes, M_AttributeSetInstance_ID
		FROM 	Report.fresh_Attributes
		WHERE	at_Value IN ( '1000015', '1000001' ) -- Marke (ADR), task 08891: also Herkunft
		GROUP BY	M_AttributeSetInstance_ID
	) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
		AND ol.M_AttributeSetInstance_ID != 0
WHERE
	1=1
	AND report.IsActive='Y' and reportLine.IsActive='Y'
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND o.IsSOTrx != 'N'
	AND o.DocStatus = 'CO'

/*
ORDER BY
	CASE WHEN $P{c_order_id} IS NOT NULL THEN ol.line ELSE 0 END,
	-- When no order document is given, sort to aggregate
	p.name,
	att.Attributes,
	uom.UOMSymbol,
	ol.Pricelist,
	ol.PriceActual,
	pm.name,
	ip.qty
*/
;


