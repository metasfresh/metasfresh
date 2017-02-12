-- DROP FUNCTION de_metas_endcustomer_fresh_reports.docs_rfq_response_report_details(numeric, character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_rfq_response_report_details(IN record_id numeric, IN ad_language character varying)
  RETURNS TABLE(productname character varying, packdescription character varying, qtyrequiered numeric, uomsymbol character varying, currency character, price numeric) AS
$$
SELECT	COALESCE(pt.name,pmm.ProductName) AS productname, 
	pmm.packdescription, 
	rl.qtyrequiered, 
	COALESCE(uomt.uomsymbol,uom.uomsymbol) AS uomsymbol,
	c.iso_code AS currency,
	rl.price as price
	
FROM C_RfQResponseLine rl
INNER JOIN C_RfQResponse rr ON rl.C_RfQResponse_ID = rr.C_RfQResponse_ID
INNER JOIN PMM_Product pmm ON rl.PMM_Product_ID = pmm.PMM_Product_ID
LEFT OUTER JOIN M_Product p ON pmm.M_Product_ID = p.M_Product_ID
LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
INNER JOIN C_UOM uom ON rl.C_UOM_ID = uom.C_UOM_ID
LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
INNER JOIN C_Currency c ON rl.C_Currency_ID = c.C_Currency_ID

WHERE rr.C_RfQResponse_ID = $1

$$
  LANGUAGE sql STABLE
 ;

