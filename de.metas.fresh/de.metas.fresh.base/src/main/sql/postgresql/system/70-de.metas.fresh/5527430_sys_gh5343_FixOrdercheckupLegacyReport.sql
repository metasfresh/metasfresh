UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/sales/ordercheckup/legacy/report.jasper' WHERE AD_Process_ID=540532;

COMMIT;



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root( IN record_id numeric, IN bPartnerId numeric, IN datePromised date );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(IN record_id    numeric,
                                                                                           IN bPartnerId   numeric,
                                                                                           IN p_datePromised date)
  RETURNS TABLE
  (
    ad_org_id  numeric,
    docstatus  character(2),
    printname  character varying(60),
    c_order_id integer,
	C_Order_MFGWarehouse_Report_ID integer
  )
AS
$$
SELECT
  r.AD_Org_ID,
  r.DocStatus,
  r.PrintName,
  r.C_Order_ID :: int,
  r.C_Order_MFGWarehouse_Report_ID :: int
FROM report.RV_C_Order_MFGWarehouse_Report_Header r
WHERE
  CASE
  WHEN record_id IS NOT NULL
    THEN r.C_Order_MFGWarehouse_Report_ID = record_id
  WHEN bPartnerId IS NOT NULL AND DatePromised :: date IS NOT NULL
    THEN r.C_BPartner_ID = bPartnerId AND r.DatePromised :: date = p_datePromised :: date
  ELSE false -- shall never nappen
  END
LIMIT 1

$$
LANGUAGE sql
STABLE;


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Details(IN record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Details(IN record_id numeric)
RETURNS TABLE 
	(
	line numeric,
	attributes text,
	prodValue character varying,
	value character varying,
	name character varying(255),
	ean character varying,
	pricelist numeric,
	capacity numeric,
	priceactual numeric,
	qtyenteredtu numeric,
	qtyentered numeric,
	container character varying(60),
	uomsymbol character varying(10),
	c_order_mfgwarehouse_report_id numeric,
	reportdocumenttype character varying(2),
	C_Order_MFGWarehouse_ReportLine_ID numeric,
	c_order_id numeric,
	c_orderline_id numeric,
	m_warehouse_id numeric,
	pp_plant_id numeric,
	c_bpartner_id numeric,
	datepromised timestamp with time zone,
	barcode character varying(255)
	)
AS
$$	

SELECT
	ol.line,
	att.Attributes,
	p.value AS prodValue,
	COALESCE(bpp.ProductNo, p.value) AS Value,
	p.Name AS Name,
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
	AND report.C_Order_MFGWarehouse_Report_ID =  $1
	
ORDER BY ol.line

$$
LANGUAGE sql STABLE;


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN record_id numeric)
RETURNS TABLE 
	(
	bpValue character varying(50),
	order_no character varying(30),
	reference character varying(40),
	preparationdate timestamp with time zone,
	datepromised timestamp with time zone,
	ReportDocumentTypeName character varying(40),
	handoverlocation character varying(140),
    warehousename character varying(60),
    plantname character varying(60)
	)
AS
$$	
SELECT
	r.bpValue,
	r.document_no as order_no,
	r.poreference as reference,
	r.preparationdate as PreparationDate,
	r.datepromised as DatePromised,
	r.ReportDocumentTypeName,
	r.handoverlocation ,
    r.warehousename,
    r.plantname
	
FROM report.RV_C_Order_MFGWarehouse_Report_Description r
WHERE
	r.C_Order_MFGWarehouse_Report_ID = $1
LIMIT 1

$$
LANGUAGE sql STABLE;