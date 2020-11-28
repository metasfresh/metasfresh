drop view if exists report.RV_C_Order_MFGWarehouse_Report_Header;
create or replace view report.RV_C_Order_MFGWarehouse_Report_Header
AS
SELECT
	o.AD_Org_ID,
	o.DocStatus,
	dt.PrintName,
	bpl.C_BPartner_Location_ID,
	--
	-- Filtering columns
	report.C_Order_MFGWarehouse_Report_ID,
	report.DocumentType as ReportDocumentType,
	o.C_Order_ID,
	report.M_Warehouse_ID,
	report.PP_Plant_ID,
	o.C_BPartner_ID,
	o.DatePromised
FROM
	C_Order_MFGWarehouse_Report report
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID)
	--
	INNER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID
	-- Get BPartner Location
	LEFT OUTER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID =
	(
		SELECT First_Agg (C_BPartner_Location_ID::Text ORDER BY IsBillToDefault DESC, IsBillTo DESC)
		FROM C_BPartner_Location sub_bpl WHERE sub_bpl.C_BPartner_ID = o.C_BPartner_ID
	)::Numeric
WHERE true
	AND report.IsActive='Y'
;



drop view if exists report.RV_C_Order_MFGWarehouse_Report_Description;
create or replace view report.RV_C_Order_MFGWarehouse_Report_Description
AS
SELECT
	o.POReference as POReference,
	trim( Coalesce(cogr.name,  '') ||
	Coalesce(' ' || cont.title, '') ||
	Coalesce(' ' || cont.firstName, '') ||
	Coalesce(' ' || cont.lastName, '') ) as cont_name,
	cont.phone	as cont_phone,
	cont.fax	as cont_fax,
	bpl.name as HandOverLocation,
	o.PreparationDate,
	o.DocumentNo as document_no,
	wh.Name as WarehouseName,
	plant.Name as PlantName,
	(select rl.Name from AD_Ref_List rl where rl.AD_Reference_ID=540574 and rl.Value=report.DocumentType) as ReportDocumentTypeName,
	--
	-- Filtering columns
	report.C_Order_MFGWarehouse_Report_ID,
	report.DocumentType as ReportDocumentType,
	o.C_Order_ID,
	report.M_Warehouse_ID,
	report.PP_Plant_ID,
	o.C_BPartner_ID,
	o.DatePromised
FROM
	C_Order_MFGWarehouse_Report report
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID)
	--
	LEFT OUTER JOIN C_BPartner bp 		ON o.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN AD_User srep		ON o.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100
	LEFT OUTER JOIN AD_User cont		ON o.Bill_User_ID = cont.AD_User_ID
	-- HandOverLocation
	LEFT OUTER JOIN C_BPartner_Location bpl	ON o.HandOver_Location_ID = bpl.C_BPartner_Location_ID
	-- Translatables
	LEFT OUTER JOIN C_Greeting cogr	ON cont.C_Greeting_ID = cogr.C_Greeting_ID
	--
	LEFT OUTER JOIN M_Warehouse wh on (wh.M_Warehouse_ID=report.M_Warehouse_ID)
	LEFT OUTER JOIN S_Resource plant on (plant.S_Resource_ID=report.PP_Plant_ID)
WHERE true
	AND report.IsActive='Y'
;





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
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID)
	INNER JOIN C_Order_MFGWarehouse_ReportLine reportLine on (reportLine.C_Order_MFGWarehouse_Report_ID=report.C_Order_MFGWarehouse_Report_ID)
	INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = reportLine.C_OrderLine_ID)
	--
	LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID =  bp.C_BPartner_ID
	LEFT OUTER JOIN M_HU_PI_Item_Product ip ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID
	LEFT OUTER JOIN M_HU_PI_Item pii ON ip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
	LEFT OUTER JOIN M_HU_PI_Item pmi ON pmi.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID
		AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID

	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
		AND p.M_Product_ID = bpp.M_Product_ID
	LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID
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
	LEFT OUTER JOIN M_HU_PI_Item_Product ip ON olpm.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID
	LEFT OUTER JOIN M_HU_PI_Item pii ON ip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
	LEFT OUTER JOIN M_HU_PI_Item pmi ON pmi.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID
		AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID

	-- Product and its translation FROM THE ORIGINAL ORDER LINE BECAUSE WE NEED IT IN WHERECLAUSE
	LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID

	LEFT OUTER JOIN M_Product ppm ON olpm.M_Product_ID = ppm.M_Product_ID

	-- JUST IN CASE THERE IS A BPP FOR THE PACKING MATERIAL
	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
		AND ppm.M_Product_ID = bpp.M_Product_ID

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










