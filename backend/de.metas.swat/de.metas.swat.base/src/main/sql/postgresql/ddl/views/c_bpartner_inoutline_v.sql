DROP VIEW IF EXISTS  m_material_balance_view;
		
DROP VIEW IF EXISTS report.c_bpartner_inoutline_v;
		
		
CREATE OR REPLACE VIEW report.c_bpartner_inoutline_v AS 
SELECT
	mbd.C_BPartner_ID,
	mbd.MovementDate,

	CASE
		WHEN iol.IsInvoiced = 'Y' THEN true
		ELSE false
	END AS IsInvoiced,
	
	p.M_Product_Category_ID = ((
		SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID'
	)) AS IsPackingMaterial,
	mbd.M_Product_ID,
	mbd.M_InOut_ID,
	CASE 
		WHEN mbd.QtyIncoming > 0 THEN mbd.QtyIncoming
		ELSE mbd.QtyOutgoing
	END AS MovementQty,
	(SELECT UOMSymbol FROM C_UOM WHERE C_UOM_ID = mbd.C_UOM_ID) AS UOMSymbol,
	
	mbd.QtyIncoming AS Incoming,
	mbd.QtyOutgoing AS Outgoing,
	p.name as Name,
	dt.PrintName as PrintName,
	mbd.DocumentNo as DocumentNo
	
FROM

	M_Material_Balance_Detail mbd
	-- We start with BPartner of the Document (See WHERE-Clause)
	
	INNER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = mbd.M_InOutLine_ID
	INNER JOIN M_Product p on p.M_Product_ID = mbd.M_Product_ID
	INNER JOIN C_DocType dt ON mbd.C_DocType_ID = dt.C_DocType_ID
;

	

