DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Details_Footer(IN record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Details_Footer(IN record_id numeric)
RETURNS TABLE 
	(
	iso_code character(3)
	)
AS
$$	
SELECT
	c.iso_code
FROM M_Inventory i

INNER JOIN M_InventoryLine il ON i.M_Inventory_ID = il.M_Inventory_ID AND il.isActive = 'Y'
INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN C_BPartner_Location bpl ON bp.C_BPartner_ID = bpl.C_BPartner_ID AND bpl.isActive = 'Y'
LEFT JOIN C_Location l ON bpl.C_Location_id = l.C_Location_ID AND l.isActive = 'Y'

LEFT OUTER JOIN M_Pricingsystem ps ON bp.PO_Pricingsystem_ID = ps.M_Pricingsystem_ID AND ps.isActive = 'Y'
LEFT OUTER JOIN M_PriceList pl ON ps.M_Pricingsystem_ID = pl.M_Pricingsystem_ID AND pl.C_Country_ID = l.C_Country_ID AND pl.isActive = 'Y'

LEFT OUTER JOIN AD_ClientInfo ci ON ci.AD_Client_ID=i.ad_client_id AND ci.isActive = 'Y'
LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID AND acs.isActive = 'Y'
LEFT OUTER JOIN C_Currency c ON COALESCE(pl.C_Currency_ID,acs.C_Currency_ID)=c.C_Currency_ID AND c.isActive = 'Y'
WHERE i.M_Inventory_ID = $1 AND i.isActive = 'Y'
limit 1
$$
LANGUAGE sql STABLE;

