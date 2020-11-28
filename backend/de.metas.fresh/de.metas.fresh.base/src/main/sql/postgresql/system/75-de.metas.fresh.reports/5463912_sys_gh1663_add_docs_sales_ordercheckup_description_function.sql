DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Description(IN record_id numeric)
RETURNS TABLE 
	(
	order_no character varying(30),
	reference character varying(40),
	preparationdate timestamp with time zone,
	datepromised timestamp with time zone,
	ReportDocumentTypeName character varying(40)
	)
AS
$$	
SELECT
	r.document_no as order_no,
	r.poreference as reference,
	r.preparationdate as PreparationDate,
	r.datepromised as DatePromised,
	r.ReportDocumentTypeName
	
FROM report.RV_C_Order_MFGWarehouse_Report_Description r
WHERE
	r.C_Order_MFGWarehouse_Report_ID = $1
LIMIT 1

$$
LANGUAGE sql STABLE;