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


