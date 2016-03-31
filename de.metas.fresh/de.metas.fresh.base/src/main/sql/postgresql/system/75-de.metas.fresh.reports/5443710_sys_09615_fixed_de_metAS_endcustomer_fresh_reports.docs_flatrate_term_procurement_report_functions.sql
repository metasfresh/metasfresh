
DROP FUNCTION IF EXISTS de_metAS_endcustomer_fresh_reports.docs_flatrate_term_procurement_report_details(IN record_id numeric);
CREATE OR REPLACE FUNCTION de_metAS_endcustomer_fresh_reports.docs_flatrate_term_procurement_report_details(IN record_id numeric)
RETURNS TABLE(
flatrate_term_id numeric,
BPartnerName character varying,
BPartnerValue character varying,
UserName character varying,
ProductName character varying,
ProductValue character varying,
uom character varying,
note character varying,
startdate timestamp without time zone, enddate timestamp without time zone,
month1 timestamp without time zone, month2 timestamp without time zone, month3 timestamp without time zone, month4 timestamp without time zone, month5 timestamp without time zone, month6 timestamp without time zone,
month7 timestamp without time zone, month8 timestamp without time zone, month9 timestamp without time zone, month10 timestamp without time zone,  month11 timestamp without time zone, month12 timestamp without time zone,
qty_planned1 numeric, qty_planned2 numeric, qty_planned3 numeric, qty_planned4 numeric, qty_planned5 numeric, qty_planned6 numeric, 
qty_planned7 numeric, qty_planned8 numeric, qty_planned9 numeric, qty_planned10 numeric, qty_planned11 numeric, qty_planned12 numeric, 
flatrateamtperuom1 numeric, flatrateamtperuom2 numeric, flatrateamtperuom3 numeric, flatrateamtperuom4 numeric, flatrateamtperuom5 numeric, flatrateamtperuom6 numeric, 
flatrateamtperuom7 numeric, flatrateamtperuom8 numeric, flatrateamtperuom9 numeric, flatrateamtperuom10 numeric, flatrateamtperuom11 numeric, flatrateamtperuom12 numeric,
total numeric
)
AS
$$
SELECT *,
	 COALESCE(ft.qty_planned1, 0) + COALESCE(ft.qty_planned2, 0) + COALESCE(ft.qty_planned3, 0) + COALESCE(ft.qty_planned4, 0) + COALESCE(ft.qty_planned5, 0) + COALESCE(ft.qty_planned6, 0) + 
	 COALESCE(ft.qty_planned7, 0) + COALESCE(ft.qty_planned8, 0) + COALESCE(ft.qty_planned9, 0) + COALESCE(ft.qty_planned10, 0) + COALESCE(ft.qty_planned11, 0) + COALESCE(ft.qty_planned12, 0)
	  AS total
FROM
(	 
SELECT 
	ft.C_Flatrate_Term_id,
	billBP.name,billbp.value, u.name,
	p.name, p.value, 
	uom.uomsymbol,
	ft.note,
	ft.startdate, ft.enddate,
	
	(SELECT startdate FROM c_period WHERE c_period_id = prd.c_period_id) AS month1,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 1)) AS month2,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 2)) AS month3,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 3)) AS month4,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 4)) AS month5,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 5)) AS month6,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 6)) AS month7,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 7)) AS month8,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 8)) AS month9,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 9)) AS month10,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 10)) AS month11,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 11)) AS month12,
	
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =prd.c_period_id AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned1,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 1) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned2,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 2) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned3,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 3) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned4,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 4) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned5,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 5) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned6,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 6) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned7,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 7) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned8,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 8) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned9,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 9) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned10,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 10) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned11,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 11) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS qty_planned12,
	
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =prd.c_period_id AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom1,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 1) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom2,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 2) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom3,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 3) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom4,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 4) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom5,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 5) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom6,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 6) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom7,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 7) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom8,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 8) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom9,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 9) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom10,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 10) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom11,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 11) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC') AS flatrateamtperuom12

FROM C_Flatrate_Term ft
JOIN C_Flatrate_Conditions fc ON ft.C_Flatrate_Conditions_id = fc.C_Flatrate_Conditions_ID

JOIN M_Product p ON ft.M_Product_ID = p.M_Product_ID
JOIN C_BPartner billBP ON ft.bill_bpartner_id = billBP.C_BPartner_ID
JOIN AD_User u ON ft.ad_user_incharge_ID = u.AD_User_ID
JOIN C_UOM uom ON ft.C_UOM_ID = uom.C_UOM_ID
JOIN c_period prd ON ft.startdate >= prd.startdate AND ft.startdate <= prd.enddate

WHERE 
ft.C_Flatrate_Term_id=$1 AND
fc.type_conditions ='Procuremnt'


GROUP BY billBP.name,billbp.value, u.name,
	p.value, p.name,
	uom.uomsymbol,
	ft.note
	,ft.C_Flatrate_Term_id,
	prd.c_period_id
)ft
$$
LANGUAGE sql STABLE;








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

JOIN c_period p_param_st ON ft.startdate >= p_param_st.startdate AND ft.startdate <= p_param_st.enddate
JOIN c_period p_param_end ON ft.enddate >= p_param_end.startdate AND ft.enddate <= p_param_end.enddate

WHERE 
ft.C_Flatrate_Term_id=$1 AND
fc.type_conditions ='Procuremnt'


$$
LANGUAGE sql STABLE;
