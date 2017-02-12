

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
JOIN C_Flatrate_Conditions fc on ft.C_Flatrate_Conditions_id = fc.C_Flatrate_Conditions_ID AND fc.isActive = 'Y'
JOIN PMM_Product pmm ON ft.PMM_Product_ID = pmm.PMM_Product_ID AND pmm.isActive = 'Y'
JOIN C_BPartner billBP ON ft.bill_bpartner_id = billBP.C_BPartner_ID AND billBP.isActive = 'Y'
JOIN AD_User u ON ft.ad_user_incharge_ID = u.AD_User_ID AND u.isActive = 'Y'
JOIN C_UOM uom ON ft.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_trl uom_trl ON uom.C_UOM_ID = uom_trl.C_UOM_ID AND uom_trl.ad_language = $2 AND uom_trl.isActive = 'Y'

JOIN c_period p_param_st ON ft.startdate >= p_param_st.startdate AND ft.startdate <= p_param_st.enddate AND p_param_st.isActive = 'Y'
LEFT OUTER JOIN C_Period_trl period_trl_st ON p_param_st.c_period_ID = period_trl_st.c_period_ID AND period_trl_st.ad_language = $2 AND period_trl_st.isActive = 'Y'
JOIN c_period p_param_end ON ft.enddate >= p_param_end.startdate AND ft.enddate <= p_param_end.enddate AND p_param_end.isActive = 'Y'
LEFT OUTER JOIN C_Period_trl period_trl_end ON p_param_end.c_period_ID = period_trl_end.c_period_ID AND period_trl_end.ad_language = $2 AND period_trl_end.isActive = 'Y'

JOIN C_Year y_st ON p_param_st.C_Year_ID = y_st.C_Year_ID AND y_st.isActive = 'Y'
JOIN C_Year y_end ON p_param_end.C_Year_ID = y_end.C_Year_ID AND y_end.isActive = 'Y'

WHERE 
ft.C_Flatrate_Term_id=$1 AND
fc.type_conditions ='Procuremnt'
AND ft.isActive = 'Y'

$$
LANGUAGE sql STABLE;

