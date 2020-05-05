
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_flatrate_term_procurement_report_description(IN record_id numeric, IN ad_language character varying);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_flatrate_term_procurement_report_description(IN record_id numeric, IN ad_language character varying)
RETURNS TABLE(
flatrate_term_id numeric,
BPartnerName character varying,
BPartnerValue character varying,
UserName character varying,
ProductName character varying,
ProductValue character varying,
uom character varying,
note character varying,
startdate character varying(60), enddate character varying(60)
)
AS
$$
SELECT 
	-- header data
	ft.C_Flatrate_Term_id,
	billBP.name,billbp.value, u.name,
	COALESCE(p_trl.name, p.name), p.value,
	COALESCE(uom_trl.uomsymbol, uom.uomsymbol),
	ft.note,
	p_param_st.name, p_param_end.name

FROM C_Flatrate_Term ft
JOIN C_Flatrate_Conditions fc on ft.C_Flatrate_Conditions_id = fc.C_Flatrate_Conditions_ID
JOIN M_Product p ON ft.M_Product_ID = p.M_Product_ID
LEFT OUTER JOIN m_product_trl p_trl ON p.M_Product_ID = p_trl.M_Product_ID AND p_trl.ad_language = $2
JOIN C_BPartner billBP ON ft.bill_bpartner_id = billBP.C_BPartner_ID
JOIN AD_User u ON ft.ad_user_incharge_ID = u.AD_User_ID
JOIN C_UOM uom ON ft.C_UOM_ID = uom.C_UOM_ID
LEFT OUTER JOIN C_UOM_trl uom_trl ON uom.C_UOM_ID = uom_trl.C_UOM_ID AND uom_trl.ad_language = $2
JOIN c_period prd ON ft.startdate = prd.startdate
JOIN c_period p_param_st ON ft.startdate = p_param_st.startdate
JOIN c_period p_param_end ON ft.enddate = p_param_end.enddate

WHERE 
ft.C_Flatrate_Term_id=$1 AND
fc.type_conditions ='Procuremnt'


$$
LANGUAGE sql STABLE;
