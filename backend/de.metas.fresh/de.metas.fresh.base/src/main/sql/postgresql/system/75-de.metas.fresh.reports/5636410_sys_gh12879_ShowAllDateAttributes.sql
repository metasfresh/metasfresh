-- View: Report.fresh_Attributes

DROP VIEW IF EXISTS report.RV_Salesgroups;
drop view if exists report.RV_C_Order_MFGWarehouse_Report_Details;
drop view if exists report.RV_C_Order_MFGWarehouse_Report_Details_HU;

DROP VIEW IF EXISTS  Report.fresh_Attributes
;

CREATE OR REPLACE VIEW  Report.fresh_Attributes AS
SELECT * FROM
(
	SELECT
		CASE
			WHEN a.Value = '1000015' AND av.value = '01' THEN NULL -- ADR & Keine/Leer
			WHEN a.Value = '1000015' AND (av.value IS NOT NULL AND av.value != '') THEN 'AdR' -- ADR
			WHEN a.Value = '1000001' AND (av.value IS NOT NULL AND av.value != '') THEN av.value -- Herkunft
			WHEN a.Value = '1000021' AND (ai.value IS NOT NULL AND ai.value != '') THEN ai.Value -- MHD
			WHEN a.Value = 'HU_BestBeforeDate' AND (ai.valuedate IS NOT NULL) THEN 'MHD: '|| to_char(ai.valuedate, 'DD.MM.YYYY') --Best Before Date
			WHEN a.attributevaluetype = 'D' AND (ai.valuedate IS NOT NULL) THEN to_char(ai.valuedate, 'DD.MM.YYYY')
			WHEN a.attributevaluetype = 'S' AND COALESCE(TRIM(ai.value),'') != '' THEN ai.value
			WHEN a.attributevaluetype = 'N' AND ai.valuenumber IS NOT NULL AND ai.valuenumber > 0 THEN ai.valuenumber::bpchar
			WHEN a.Value = 'M_Material_Tracking_ID' 
				THEN (SELECT mt.lot FROM m_material_tracking mt 
					WHERE mt.m_material_tracking_id = ai.value::numeric  )
			ELSE av.Name -- default 
		END AS ai_Value, 
		M_AttributeSetInstance_ID,
		a.Value as at_Value,
		a.Name as at_Name,
		a.IsAttrDocumentRelevant as at_IsAttrDocumentRelevant -- task
	FROM M_AttributeInstance ai
		LEFT OUTER JOIN M_Attributevalue av ON ai.M_Attributevalue_ID = av.M_Attributevalue_ID AND av.isActive = 'Y'
		LEFT OUTER JOIN M_Attribute a ON ai.M_Attribute_ID = a.M_Attribute_ID AND a.isActive = 'Y'
	WHERE
		/**
		 * 08014 - There are/were orderlines, that had M_AttributeSetInstance_ID = 0 when they were intended to not have
		 * Attributes set. Unfortunately there actually was an attribute set with ID = 0 which also had values set thus
		 * The report displayed attribute values even though it should not display them. The Attribute with the ID = 0
		 * Is invalid and therefore not returned by this view. That way, the Report will display nothing for ASI ID = 0
		 */ 
		ai.M_AttributeSetInstance_ID != 0
		AND ai.isActive = 'Y'
) att
WHERE COALESCE( ai_value, '') != ''
;

COMMENT ON VIEW Report.fresh_Attributes IS 'retrieves Attributes in the way they are needed for the jasper reports';
;



Create VIEW report.RV_Salesgroups AS
select x.DateInvoiced, x.ProductSalesgroup,  x.uom, x.asi_inausland, x.asi_adr, x.asi_country,

Case When 
	x.PartnerSalesGroupValue = '0010' 
	THEN x.qtyInvoiced
	ELSE 0
	END
	AS QtyInvoicedDiscounter,

Case When 
	x.PartnerSalesGroupValue = '0020' 
	THEN x.qtyInvoiced
	ELSE 0
	END
	AS QtyInvoicedGastro,
Case When 
	x.PartnerSalesGroupValue = '0030' 
	THEN x.qtyInvoiced
	ELSE 0
	END
	AS QtyInvoicedDetailhandel,


	
Case When 
	x.PartnerSalesGroupValue = '0010' 
	THEN x.revenue
	ELSE 0
	END
	AS RevenueDiscounter,

Case When 
	x.PartnerSalesGroupValue = '0020' 
	THEN x.revenue
	ELSE 0
	END
	AS RevenueGastro,
Case When 
	x.PartnerSalesGroupValue = '0030' 
	THEN x.revenue
	ELSE 0
	END
	AS RevenueDetailhandel,
	
x.ad_org_id


from
(

SELECT 
	i.DateInvoiced,
	list.name as ProductSalesgroup,
	bp.salesgroup as PartnerSalesGroupValue,
	coalesce(p.Salesgroup_UOM_ID, p.C_UOM_ID) as uom,

	(
		case when p.Salesgroup_UOM_ID > 0 
		then uomconvert(p.M_Product_ID, il.C_UOM_ID, p.Salesgroup_UOM_ID, il.QtyInvoiced)
		else uomconvert(p.M_Product_ID, il.C_UOM_ID, p.C_UOM_ID, il.QtyInvoiced)
		end
	
	)QtyInvoiced,

	case when (i.isTaxIncluded = 'Y')
	then
	
	currencyconvert ( il.linenetamt - il.taxamtinfo :: numeric, i.C_Currency_ID :: numeric, (Select C_Currency_ID from c_Currency where iso_code = 'CHF') :: numeric, i.DateInvoiced ::  timestamp with time zone, 0 :: numeric , i.AD_Client_ID :: numeric, i.AD_Org_ID :: numeric)


	else
	currencyconvert ( il.linenetamt :: numeric, i.C_Currency_ID :: numeric, (Select C_Currency_ID from c_Currency where iso_code = 'CHF') :: numeric, i.DateInvoiced ::  timestamp with time zone, 0 :: numeric , i.AD_Client_ID :: numeric, i.AD_Org_ID :: numeric)

	end
	
	as Revenue
	,(SELECT ai_value FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID AND at_value='1000000') AS asi_inausland-- inausland
	,(SELECT ai_value FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID AND at_value='1000015') AS asi_adr-- adr
	,(SELECT ai_value FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID AND at_value='1000001') AS asi_country-- country
	
	, i.ad_org_id

FROM C_InvoiceLine il
JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
JOIN M_Product p on il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
JOIN AD_Ref_List list on list.AD_Reference_id in (select AD_Reference_id from AD_Reference where name = 'M_Product_Salesgroup' AND isActive = 'Y') and list.value = p.Salesgroup AND list.isActive = 'Y'
join C_BPartner bp on i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
JOIN AD_Ref_List list2 on list2.AD_Reference_id in (select AD_Reference_id from AD_Reference where name = 'C_BPartner_Salesgroup' AND isActive = 'Y') and list2.value = bp.Salesgroup AND list2.isActive = 'Y'
where i.isSotrx = 'Y'and
	p.Salesgroup is not null and
	bp.Salesgroup is not null
	AND il.isActive = 'Y'


) x
--we only want to show the ones from inland (inland, adr or ch) task 09837
where x.asi_inausland='Inland' OR x.asi_adr='AdR' OR asi_country='CH'

;


create or replace view report.RV_C_Order_MFGWarehouse_Report_Details
AS
SELECT
	ol.line,
	att.Attributes,
	COALESCE(NULLIF(trim(bpp.ProductNo),''), p.value) AS ProductValue,
	COALESCE(NULLIF(trim(bpp.ProductName),''), p.Name) AS ProductName,
	COALESCE(NULLIF(trim(bpp.UPC),''), p.UPC) AS EAN,
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
	o.DatePromised,
	reportLine.barcode AS barcode 
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
	LEFT OUTER JOIN	LATERAL(
		SELECT 	String_agg ( ai_value, ', ' ) AS Attributes, M_AttributeSetInstance_ID
		FROM 	Report.fresh_Attributes
		WHERE	at_Value IN ( '1000015', '1000001', '1000002' ) -- Marke (ADR), task 08891: also Herkunft, task 2237: also Label
			AND M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID AND  ol.M_AttributeSetInstance_ID != 0
		GROUP BY	M_AttributeSetInstance_ID
	) att ON TRUE
WHERE
	1=1
	AND report.IsActive='Y' and reportLine.IsActive='Y'
	AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
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
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID) AND o.isActive = 'Y'
	INNER JOIN C_Order_MFGWarehouse_ReportLine reportLine on (reportLine.C_Order_MFGWarehouse_Report_ID=report.C_Order_MFGWarehouse_Report_ID)
	INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = reportLine.C_OrderLine_ID) AND ol.isActive = 'Y'
	--
	LEFT OUTER JOIN C_OrderLine olpm ON ol.C_PackingMaterial_OrderLine_ID = olpm.C_OrderLine_ID AND olpm.isActive = 'Y'

	LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'

	-- TAKE THE M_HU_PI_Item_Product_ID OF THE PACKING MATERIAL LINE
	LEFT OUTER JOIN M_HU_PI_Item_Product ip ON olpm.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pii ON ip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID AND pii.isActive = 'Y'
	LEFT OUTER JOIN M_HU_PI_Item pmi ON pmi.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID AND pmi.isActive = 'Y'
		AND pmi.ItemType= 'PM'
	LEFT OUTER JOIN M_HU_PackingMaterial pm ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID AND pm.isActive = 'Y'

	-- Product and its translation FROM THE ORIGINAL ORDER LINE BECAUSE WE NEED IT IN WHERECLAUSE
	LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

	LEFT OUTER JOIN M_Product ppm ON olpm.M_Product_ID = ppm.M_Product_ID AND ppm.isActive = 'Y'

	-- JUST IN CASE THERE IS A BPP FOR THE PACKING MATERIAL
	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
		AND ppm.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'

	LEFT OUTER JOIN M_Product_Category pc ON ppm.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

	-- Unit of measurement and its translation FROM THE PACKING MATERIAL LINE
	LEFT OUTER JOIN C_UOM uom ON olpm.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'

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
	AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', olpm.AD_Client_ID, olpm.AD_Org_ID)
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


