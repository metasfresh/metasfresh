

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_flatrate_term_procurement_report_description(IN record_id numeric, IN ad_language character varying);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_flatrate_term_procurement_report_description(IN record_id numeric, IN ad_language character varying)
RETURNS TABLE(
flatrate_term_id numeric,
BPartnerName character varying,
BPartnerValue character varying,
UserName character varying,
ProductName character varying,
uom character varying,
note character varying,
startdate character varying(10), enddate character varying(10)
)
AS
$$
SELECT 
	-- header data
	ft.C_Flatrate_Term_id,
	billBP.name,billbp.value, u.name,
	pmm.ProductName,
	COALESCE(uom_trl.uomsymbol, uom.uomsymbol),
	ft.note,
	y_st.FiscalYear,
	y_end.FiscalYear

FROM C_Flatrate_Term ft
JOIN C_Flatrate_Conditions fc on ft.C_Flatrate_Conditions_id = fc.C_Flatrate_Conditions_ID
JOIN PMM_Product pmm ON ft.PMM_Product_ID = pmm.PMM_Product_ID
JOIN C_BPartner billBP ON ft.bill_bpartner_id = billBP.C_BPartner_ID
JOIN AD_User u ON ft.ad_user_incharge_ID = u.AD_User_ID
JOIN C_UOM uom ON ft.C_UOM_ID = uom.C_UOM_ID
LEFT OUTER JOIN C_UOM_trl uom_trl ON uom.C_UOM_ID = uom_trl.C_UOM_ID AND uom_trl.ad_language = $2

JOIN c_period p_param_st ON ft.startdate >= p_param_st.startdate AND ft.startdate <= p_param_st.enddate
LEFT OUTER JOIN C_Period_trl period_trl_st ON p_param_st.c_period_ID = period_trl_st.c_period_ID AND period_trl_st.ad_language = $2
JOIN c_period p_param_end ON ft.enddate >= p_param_end.startdate AND ft.enddate <= p_param_end.enddate
LEFT OUTER JOIN C_Period_trl period_trl_end ON p_param_end.c_period_ID = period_trl_end.c_period_ID AND period_trl_end.ad_language = $2

JOIN C_Year y_st ON p_param_st.C_Year_ID = y_st.C_Year_ID
JOIN C_Year y_end ON p_param_end.C_Year_ID = y_end.C_Year_ID

WHERE 
ft.C_Flatrate_Term_id=$1 AND
fc.type_conditions ='Procuremnt'


$$
LANGUAGE sql STABLE;

