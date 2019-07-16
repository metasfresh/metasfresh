-- DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN p_C_Order_MFGWarehouse_Report_ID numeric, IN p_C_Order_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN p_C_Order_MFGWarehouse_Report_ID numeric, IN p_C_Order_ID numeric)
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
	r.C_Order_MFGWarehouse_Report_ID = p_C_Order_MFGWarehouse_Report_ID
UNION 

SELECT
    bp.value as bpValue,
	o.DocumentNo as order_no,
	o.poreference as reference,
	o.PreparationDate,
	o.datepromised as DatePromised,
	null as ReportDocumentTypeName,
	bpl.name as HandOverLocation,
	w.Name as warehousename,
	null as plantname
FROM
	C_Order o
	JOIN C_BPartner bp 		ON o.C_BPartner_ID = bp.C_BPartner_ID 
	LEFT JOIN C_BPartner_Location bpl ON o.HandOver_Location_ID = bpl.C_BPartner_Location_ID
	left join M_warehouse w on w.M_warehouse_id = o.M_warehouse_id
WHERE o.C_Order_ID = p_C_Order_ID
LIMIT 1

$$
LANGUAGE sql STABLE;

