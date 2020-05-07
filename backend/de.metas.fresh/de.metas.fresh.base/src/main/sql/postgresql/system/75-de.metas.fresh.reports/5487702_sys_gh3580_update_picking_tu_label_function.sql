DROP FUNCTION IF EXISTS report.picking_tu_label(IN M_HU_ID numeric);

CREATE FUNCTION report.picking_tu_label(IN M_HU_ID numeric) RETURNS TABLE
	(
	org_name Character Varying, 
	bp_gln Character Varying, 
	bp_gln_9_12 text, 
	addr_name Character Varying, 
	address text, 
	barcode text
	)
AS 
$$
SELECT

	org.Name as org_name,
	bp_loc.gln as bp_gln,
	substring(bp_loc.gln from 9 for 4) as bp_gln_9_12,
	bp_loc.name as addr_name,
	COALESCE(bp.CompanyName, bp.Name) || ', ' || loc.address1 || ', ' || loc.Postal || '' || loc.City,
	
	report.get_barcode($1) || '413' || bp_loc.gln as barcode
	
	
FROM M_HU hu
	
JOIN AD_Org org ON hu.AD_Org_ID = org.AD_Org_ID 

-- data from HU
LEFT JOIN (
		SELECT 	sub_tua.M_HU_ID, bp.C_BPartner_ID
		FROM	M_HU_Attribute sub_tua
			INNER JOIN M_Attribute sub_a ON sub_tua.M_Attribute_ID = sub_a.M_Attribute_ID AND sub_a.isActive = 'Y'
			INNER JOIN C_OrderLine ol ON sub_tua.valueNumber = ol.C_OrderLine_ID AND ol.isActive = 'Y' 
			INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
			INNER JOIN C_BPartner bp ON o.C_Bpartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
		WHERE 	sub_a.value = 'HU_PurchaseOrderLine_ID' -- HU_PurchaseOrderLine_ID
			 AND sub_tua.isActive = 'Y'
	) tua_po_bp ON hu.M_HU_ID = tua_po_bp.M_HU_ID
	
	
LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = COALESCE(hu.C_BPartner_ID, tua_po_bp.C_BPartner_ID) AND bp.isActive = 'Y'

LEFT JOIN C_BPartner_Location bp_loc ON bp.C_BPartner_ID = bp_loc.C_BPartner_ID AND bp_loc.isActive = 'Y' AND bp_loc.isBillTo = 'Y' AND bp_loc.isBillToDefault = 'Y'
LEFT JOIN C_Location loc ON bp_loc.C_Location_ID = loc.C_Location_ID AND loc.isActive = 'Y'

WHERE hu.M_HU_ID = $1	
$$
LANGUAGE sql STABLE	
;
