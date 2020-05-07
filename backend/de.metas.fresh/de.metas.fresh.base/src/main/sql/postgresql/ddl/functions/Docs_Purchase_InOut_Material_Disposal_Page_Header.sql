-- Function: de_metas_endcustomer_fresh_reports.docs_purchase_inout_page_header(numeric, character varying)

-- DROP FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_InOut_Material_Disposal_page_header(numeric, character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_InOut_Material_Disposal_page_header(IN record_id numeric, IN ad_language character varying)
  RETURNS TABLE(
				documentno text, 
				inventorydate timestamp without time zone, 
				bp_value character varying, 
				printname character varying
				) 
  AS
$$

SELECT 
	i.DocumentNo AS documentNo,
	i.movementDate as inventorydate,
	COALESCE(dtt.printName, dt.printName) as printname,
	bp.Value as BP_Value
	

FROM M_Inventory i

-- data from inventory
INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.isActive = 'Y' AND dtt.AD_Language = $2

--data from inout
INNER JOIN M_InventoryLine il ON i.M_Inventory_ID = il.M_Inventory_ID AND il.isActive = 'Y'
INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'

INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'

WHERE i.M_Inventory_ID = $1 AND i.isActive = 'Y'

ORDER BY bp_value	
	
$$
  LANGUAGE sql STABLE;
