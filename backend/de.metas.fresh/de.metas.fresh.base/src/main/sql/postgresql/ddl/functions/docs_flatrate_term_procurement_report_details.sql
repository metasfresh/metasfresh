

DROP FUNCTION IF EXISTS de_metAS_endcustomer_fresh_reports.docs_flatrate_term_procurement_report_details(IN record_id numeric);
CREATE OR REPLACE FUNCTION de_metAS_endcustomer_fresh_reports.docs_flatrate_term_procurement_report_details(IN record_id numeric)
RETURNS TABLE(
flatrate_term_id numeric,
BPartnerName character varying,
BPartnerValue character varying,
UserName character varying,
ProductName character varying,
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
	pmm.ProductName, 
	uom.uomsymbol,
	ft.note,
	ft.startdate, ft.enddate,
	
	(SELECT startdate FROM c_period WHERE c_period_id = prd.c_period_id AND isActive = 'Y') AS month1,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 1) AND isActive = 'Y') AS month2,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 2) AND isActive = 'Y') AS month3,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 3) AND isActive = 'Y') AS month4,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 4) AND isActive = 'Y') AS month5,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 5) AND isActive = 'Y') AS month6,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 6) AND isActive = 'Y') AS month7,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 7) AND isActive = 'Y') AS month8,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 8) AND isActive = 'Y') AS month9,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 9) AND isActive = 'Y') AS month10,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 10) AND isActive = 'Y') AS month11,
	(SELECT startdate FROM c_period WHERE c_period_id = report.Get_Successor_Period_recursive(prd.c_period_id, 11) AND isActive = 'Y') AS month12,
	
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =prd.c_period_id AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned1,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 1) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned2,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 2) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned3,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 3) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned4,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 4) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned5,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 5) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned6,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 6) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned7,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 7) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned8,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 8) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned9,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 9) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned10,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 10) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned11,
	(SELECT fde1.qty_planned FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 11) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS qty_planned12,
	
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =prd.c_period_id AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom1,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 1) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom2,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 2) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom3,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 3) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom4,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 4) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom5,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 5) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom6,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 6) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom7,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 7) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom8,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 8) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom9,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 9) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom10,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 10) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom11,
	(SELECT fde1.flatrateamtperuom FROM C_Flatrate_DataEntry fde1 WHERE fde1.c_period_id =report.Get_Successor_Period_recursive(prd.c_period_id, 11) AND ft.C_Flatrate_Term_id = fde1.C_Flatrate_Term_id AND fde1.type='PC' AND fde1.isActive = 'Y') AS flatrateamtperuom12

FROM C_Flatrate_Term ft
JOIN C_Flatrate_Conditions fc ON ft.C_Flatrate_Conditions_id = fc.C_Flatrate_Conditions_ID AND fc.isActive = 'Y'
JOIN PMM_Product pmm ON ft.PMM_Product_ID = pmm.PMM_Product_ID AND pmm.isActive = 'Y'
JOIN C_BPartner billBP ON ft.bill_bpartner_id = billBP.C_BPartner_ID AND billBP.isActive = 'Y'
JOIN AD_User u ON ft.ad_user_incharge_ID = u.AD_User_ID AND u.isActive = 'Y'
JOIN C_UOM uom ON ft.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
JOIN c_period prd ON ft.startdate >= prd.startdate AND ft.startdate <= prd.enddate AND prd.isActive = 'Y'

WHERE 
ft.C_Flatrate_Term_id=$1 AND
fc.type_conditions ='Procuremnt'
AND ft.isActive = 'Y'

GROUP BY billBP.name,billbp.value, u.name,
	pmm.ProductName,
	uom.uomsymbol,
	ft.note
	,ft.C_Flatrate_Term_id,
	prd.c_period_id
)ft
$$
LANGUAGE sql STABLE;
