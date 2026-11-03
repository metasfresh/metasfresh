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
	o.DatePromised,
	o.email
FROM
	C_Order_MFGWarehouse_Report report
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID) AND o.isActive = 'Y'
	--
	INNER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	-- Get BPartner Location
	LEFT OUTER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID =
	(
		SELECT First_Agg (C_BPartner_Location_ID::Text ORDER BY IsBillToDefault DESC, IsBillTo DESC)
		FROM C_BPartner_Location sub_bpl WHERE sub_bpl.C_BPartner_ID = o.C_BPartner_ID AND sub_bpl.isActive = 'Y'
	)::Numeric AND bpl.isActive = 'Y'
WHERE true
	AND report.IsActive='Y'
;




drop view if exists report.RV_C_Order_MFGWarehouse_Report_Description;
create or replace view report.RV_C_Order_MFGWarehouse_Report_Description
AS
SELECT
	o.POReference as POReference,
	bp.value as bpValue,
    COALESCE(srgr.name, '') ||
    COALESCE(' ' || srep.title, '') ||
    COALESCE(' ' || srep.firstName, '') ||
    COALESCE(' ' || srep.lastName, '')    AS sr_name,
	trim( Coalesce(cogr.name,  '') ||
	Coalesce(' ' || cont.title, '') ||
	Coalesce(' ' || cont.firstName, '') ||
	Coalesce(' ' || cont.lastName, '') ) as cont_name,
	cont.phone	as cont_phone,
	cont.fax	as cont_fax,
	bpl.address as HandOverLocation,
	bpl.Name as HandOverLocationName,
	o.PreparationDate,
	o.DocumentNo as document_no,
	wh.Name as WarehouseName,
	plant.Name as PlantName,
	(select rl.Name from AD_Ref_List rl where rl.AD_Reference_ID=540574 and rl.Value=report.DocumentType AND rl.isActive = 'Y') as ReportDocumentTypeName,
	--
	-- Filtering columns
	report.C_Order_MFGWarehouse_Report_ID,
	report.DocumentType as ReportDocumentType,
	o.C_Order_ID,
	report.M_Warehouse_ID,
	report.PP_Plant_ID,
	o.C_BPartner_ID,
	o.DatePromised,
    o.billtoaddress,
    o.description
FROM
	C_Order_MFGWarehouse_Report report
	INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID)
	--
	LEFT OUTER JOIN C_BPartner bp 		ON o.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN AD_User srep		ON o.SalesRep_ID = srep.AD_User_ID
    LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID
	LEFT OUTER JOIN AD_User cont		ON o.Bill_User_ID = cont.AD_User_ID
	-- HandOverLocation
	LEFT OUTER JOIN C_BPartner_Location bpl	ON o.HandOver_Location_ID = bpl.C_BPartner_Location_ID
	-- Translatables
	LEFT OUTER JOIN C_Greeting cogr	ON cont.C_Greeting_ID = cogr.C_Greeting_ID
	--
	LEFT OUTER JOIN M_Warehouse wh on (wh.M_Warehouse_ID=report.M_Warehouse_ID)
	LEFT OUTER JOIN S_Resource plant on (plant.S_Resource_ID=report.PP_Plant_ID)
WHERE true
;


DROP VIEW IF EXISTS report.RV_C_Order_MFGWarehouse_Report_Details
;

CREATE OR REPLACE VIEW report.RV_C_Order_MFGWarehouse_Report_Details
AS
SELECT ol.line,
       COALESCE(NULLIF(TRIM(bpp.ProductNo), ''), p.value)  AS ProductValue,
       COALESCE(NULLIF(TRIM(bpp.ProductName), ''), p.Name) AS ProductName,
       COALESCE(NULLIF(TRIM(bpp.UPC), ''), p.UPC)          AS EAN,
       -- Rounding these columns is important to have them in one group
       -- Jasper groups by comparing the BigDecimals. In that logic, 1.00 is not the same as 1
       ROUND(ol.pricelist, 3)                              AS PriceList,
       ROUND(ip.qty, 3)                                    AS Capacity,
       ROUND(ol.Priceactual, 3)                            AS PriceActual,
       ol.QtyEnteredTU,
       ol.QtyEntered,
       pm.name                                             AS Container,
       uom.UOMSymbol                                       AS UOMSymbol,
       --
       -- Filtering columns
       report.C_Order_MFGWarehouse_Report_ID,
       report.DocumentType                                 AS ReportDocumentType,
       reportLine.C_Order_MFGWarehouse_ReportLine_ID,
       o.C_Order_ID,
       ol.C_OrderLine_ID,
       report.M_Warehouse_ID,
       report.PP_Plant_ID,
       o.C_BPartner_ID,
       o.DatePromised,
       reportLine.barcode                                  AS barcode,
       TO_CHAR(att.best_before_date :: DATE, 'dd.MM.YYYY') AS best_before_date,
       att.lotno,
       CASE
           WHEN LENGTH(att.Attributes) > 15
               THEN att.Attributes || E'\n'
               ELSE att.Attributes
       END                                                 AS Attributes,
       report.getQtyPattern(uom.StdPrecision)              AS QtyPattern,
       p.description                                       AS p_description
FROM C_Order_MFGWarehouse_Report report
         INNER JOIN C_Order o ON (report.C_Order_ID = o.C_Order_ID)
         INNER JOIN C_Order_MFGWarehouse_ReportLine reportLine ON (reportLine.C_Order_MFGWarehouse_Report_ID = report.C_Order_MFGWarehouse_Report_ID)
         INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = reportLine.C_OrderLine_ID)
    --
         LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN M_HU_PI_Item_Product ip ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item pii ON ip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
         LEFT OUTER JOIN M_HU_PI_Item pmi ON pmi.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID
    AND pmi.ItemType = 'PM'
         LEFT OUTER JOIN M_HU_PackingMaterial pm ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID
    -- Product and its translation
         LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID

         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
    AND p.M_Product_ID = bpp.M_Product_ID
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
    -- Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID
    -- Attributes
         LEFT OUTER JOIN LATERAL (SELECT STRING_AGG(AT.ai_value, ', '
                                         ORDER BY LENGTH(AT.ai_value), AT.ai_value)
                                         FILTER (WHERE AT.at_value NOT IN ('HU_BestBeforeDate', 'Lot-Nummer'))
                                                                                      AS Attributes,

                                         AT.M_AttributeSetInstance_ID,
                                         STRING_AGG(REPLACE(AT.ai_value, 'MHD: ', ''), ', ')
                                         FILTER (WHERE AT.at_value LIKE 'HU_BestBeforeDate')
                                                                                      AS best_before_date,
                                         STRING_AGG(ai_value, ', ')
                                         FILTER (WHERE AT.at_value LIKE 'Lot-Nummer') AS lotno

                                  FROM Report.fresh_Attributes(ol.M_AttributeSetInstance_ID) AT
                                  WHERE AT.IsPrintedInDocument = 'Y'
                                  GROUP BY AT.M_AttributeSetInstance_ID) att ON TRUE


WHERE 1 = 1
  AND report.IsActive = 'Y'
  AND reportLine.IsActive = 'Y'
  AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
  AND o.IsSOTrx != 'N'
  AND o.DocStatus = 'CO'

;


