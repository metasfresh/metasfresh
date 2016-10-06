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
	(select rl.Name from AD_Ref_List rl where rl.AD_Reference_ID=540574 and rl.Value=report.DocumentType AND rl.isActive = 'Y') as ReportDocumentTypeName,
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
	LEFT OUTER JOIN C_BPartner bp 		ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN AD_User srep		ON o.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100 AND srep.isActive = 'Y'
	LEFT OUTER JOIN AD_User cont		ON o.Bill_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
	-- HandOverLocation
	LEFT OUTER JOIN C_BPartner_Location bpl	ON o.HandOver_Location_ID = bpl.C_BPartner_Location_ID AND bpl.isActive = 'Y'
	-- Translatables
	LEFT OUTER JOIN C_Greeting cogr	ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
	--
	LEFT OUTER JOIN M_Warehouse wh on (wh.M_Warehouse_ID=report.M_Warehouse_ID) AND wh.isActive = 'Y'
	LEFT OUTER JOIN S_Resource plant on (plant.S_Resource_ID=report.PP_Plant_ID) AND plant.isActive = 'Y'
WHERE true
	AND report.IsActive='Y'
;


