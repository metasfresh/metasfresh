DROP VIEW IF EXISTS de_metas_procurement.qtydelivered_orderline;
CREATE OR REPLACE VIEW de_metas_procurement.qtydelivered_orderline
AS
SELECT SUM(qtydelivered) as qtydelivered,ft.c_Flatrate_term_ID, fde.C_Flatrate_DataEntry_ID from c_orderLine ol
join C_Period p ON p.startdate::date <= ol.datedelivered::date AND p.enddate::date >= ol.datedelivered::date
join C_Flatrate_Term ft ON ft.M_Product_ID = ol.M_Product_ID AND ft.Bill_BPartner_ID = ol.C_BPartner_ID 
JOIN C_Flatrate_DataEntry fde ON fde.c_period_id = p.c_period_ID AND fde.C_Flatrate_Term_ID = ft.C_Flatrate_Term_ID
JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
where o.issotrx='N' and o.docstatus in ('CO','CL')
group by ft.c_Flatrate_term_ID,fde.C_Flatrate_DataEntry_ID
;

DROP FUNCTION IF EXISTS de_metas_procurement.docs_flatrate_term_all_procurements_conditions_report(IN C_BPartner_ID numeric, IN M_Product_ID numeric);
CREATE OR REPLACE FUNCTION de_metas_procurement.docs_flatrate_term_all_procurements_conditions_report(IN C_BPartner_ID numeric, IN M_Product_ID numeric)
RETURNS TABLE
( 	productName text,
	bpartnerName character varying(60),
	startdate character varying(60),
	enddate character varying(60),
	qtyplanned numeric,
	qtyplanned_next_year numeric,
	qtyPlanned_nextyear_permonth numeric,
	qtydelivered numeric,
	year1 integer,
	year2 integer,
	qtyPlanned_jan1 numeric, qtyPlanned_feb1 numeric, qtyPlanned_mar1 numeric, qtyPlanned_apr1 numeric, qtyPlanned_may1 numeric, qtyPlanned_jun1 numeric, 
	qtyPlanned_jul1 numeric, qtyPlanned_aug1 numeric, qtyPlanned_sep1 numeric, qtyPlanned_oct1 numeric, qtyPlanned_nov1 numeric, qtyPlanned_dec1 numeric, 
	qtyPlanned_jan2 numeric, qtyPlanned_feb2 numeric, qtyPlanned_mar2 numeric, qtyPlanned_apr2 numeric, qtyPlanned_may2 numeric, qtyPlanned_jun2 numeric, 
	qtyPlanned_jul2 numeric, qtyPlanned_aug2 numeric, qtyPlanned_sep2 numeric, qtyPlanned_oct2 numeric, qtyPlanned_nov2 numeric, qtyPlanned_dec2 numeric, 
	price_jan1 numeric, price_feb1 numeric, price_mar1 numeric, price_apr1 numeric, price_may1 numeric, price_jun1 numeric, 
	price_jul1 numeric, price_aug1 numeric, price_sep1 numeric, price_oct1 numeric, price_nov1 numeric, price_dec1 numeric,  
	price_jan2 numeric, price_feb2 numeric, price_mar2 numeric, price_apr2 numeric, price_may2 numeric, price_jun2 numeric, 
	price_jul2 numeric, price_aug2 numeric, price_sep2 numeric, price_oct2 numeric, price_nov2 numeric, price_dec2 numeric,  
	qtyDelivered_jan1 numeric, qtyDelivered_feb1 numeric, qtyDelivered_mar1 numeric, qtyDelivered_apr1 numeric, qtyDelivered_may1 numeric, qtyDelivered_jun1 numeric, 
	qtyDelivered_jul1 numeric, qtyDelivered_aug1 numeric, qtyDelivered_sep1 numeric, qtyDelivered_oct1 numeric, qtyDelivered_nov1 numeric, qtyDelivered_dec1 numeric, 
	qtyDelivered_jan2 numeric, qtyDelivered_feb2 numeric, qtyDelivered_mar2 numeric, qtyDelivered_apr2 numeric, qtyDelivered_may2 numeric, qtyDelivered_jun2 numeric, 
	qtyDelivered_jul2 numeric, qtyDelivered_aug2 numeric, qtyDelivered_sep2 numeric, qtyDelivered_oct2 numeric, qtyDelivered_nov2 numeric, qtyDelivered_dec2 numeric, 
	fulfillment numeric
)
AS
$$


SELECT DISTINCT *, qtyDelivered*100/qtyPlanned AS fulfillment
FROM
(
SELECT
	p.Value || ' ' || p.Name AS productName,
	bp.Name AS BPName,
	p_param_st.name AS startdate, p_param_end.name AS enddate,
	(SELECT  SUM(fde.qty_planned)
	FROM C_Flatrate_DataEntry fde
	WHERE fde.C_Flatrate_Term_ID = ft.C_Flatrate_Term_ID
	)AS qtyPlanned,

	ft.QtyPlanned_NextYear AS qtyPlanned_nextyear,
	(CASE WHEN ft.QtyPlanned_NextYear IS NULL THEN 0 ELSE ft.QtyPlanned_NextYear/12 END) AS qtyPlanned_nextyear_permonth,

	(select SUM(q.qtydelivered) FROM (select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := per.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fda.C_Flatrate_DataEntry_ID) as qtydelivered)q
	
	) as QtyDelivered,

	yearStart::integer AS year1, (yearStart +1)::integer AS year2,
	fde_jan1.qty_planned AS qtyPlanned_jan1,
	fde_feb1.qty_planned AS qtyPlanned_feb1,
	fde_mar1.qty_planned AS qtyPlanned_mar1,
	fde_apr1.qty_planned AS qtyPlanned_apr1,
	fde_may1.qty_planned AS qtyPlanned_may1,
	fde_jun1.qty_planned AS qtyPlanned_jun1,
	fde_jul1.qty_planned AS qtyPlanned_jul1,
	fde_aug1.qty_planned AS qtyPlanned_aug1,
	fde_sep1.qty_planned AS qtyPlanned_sep1,
	fde_oct1.qty_planned AS qtyPlanned_oct1,
	fde_nov1.qty_planned AS qtyPlanned_nov1,
	fde_dec1.qty_planned AS qtyPlanned_dec1,
	--
	fde_jan2.qty_planned AS qtyPlanned_jan2,
	fde_feb2.qty_planned AS qtyPlanned_feb2,
	fde_mar2.qty_planned AS qtyPlanned_mar2,
	fde_apr2.qty_planned AS qtyPlanned_apr2,
	fde_may2.qty_planned AS qtyPlanned_may2,
	fde_jun2.qty_planned AS qtyPlanned_jun2,
	fde_jul2.qty_planned AS qtyPlanned_jul2,
	fde_aug2.qty_planned AS qtyPlanned_aug2,
	fde_sep2.qty_planned AS qtyPlanned_sep2,
	fde_oct2.qty_planned AS qtyPlanned_oct2,
	fde_nov2.qty_planned AS qtyPlanned_nov2,
	fde_dec2.qty_planned AS qtyPlanned_dec2,
	--
	fde_jan1.flatrateamtperuom AS price_jan1,
	fde_feb1.flatrateamtperuom AS price_feb1,
	fde_mar1.flatrateamtperuom AS price_mar1,
	fde_apr1.flatrateamtperuom AS price_apr1,
	fde_may1.flatrateamtperuom AS price_may1,
	fde_jun1.flatrateamtperuom AS price_jun1,
	fde_jul1.flatrateamtperuom AS price_jul1,
	fde_aug1.flatrateamtperuom AS price_aug1,
	fde_sep1.flatrateamtperuom AS price_sep1,
	fde_oct1.flatrateamtperuom AS price_oct1,
	fde_nov1.flatrateamtperuom AS price_nov1,
	fde_dec1.flatrateamtperuom AS price_dec1,
	--
	fde_jan2.flatrateamtperuom AS price_jan2,
	fde_feb2.flatrateamtperuom AS price_feb2,
	fde_mar2.flatrateamtperuom AS price_mar2,
	fde_apr2.flatrateamtperuom AS price_apr2,
	fde_may2.flatrateamtperuom AS price_may2,
	fde_jun2.flatrateamtperuom AS price_jun2,
	fde_jul2.flatrateamtperuom AS price_jul2,
	fde_aug2.flatrateamtperuom AS price_aug2,
	fde_sep2.flatrateamtperuom AS price_sep2,
	fde_oct2.flatrateamtperuom AS price_oct2,
	fde_nov2.flatrateamtperuom AS price_nov2,
	fde_dec2.flatrateamtperuom AS price_dec2,

	--

	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_jan1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_jan1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_jan1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_feb1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_feb1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_feb1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_mar1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_mar1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_mar1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_apr1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_apr1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_apr1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_may1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_may1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_may1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_jun1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_jun1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_jun1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_jul1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_jul1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_jul1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_aug1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_aug1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_aug1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_sep1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_sep1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_sep1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_oct1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_oct1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_oct1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_nov1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_nov1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_nov1,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_dec1.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_dec1.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_dec1,
	--

	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_jan2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_jan2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_jan2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_feb2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_feb2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_feb2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_mar2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_mar2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_mar2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_apr2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_apr2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_apr2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_may2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_may2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_may2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_jun2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_jun2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_jun2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_jul2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_jul2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_jul2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_aug2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_aug2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_aug2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_sep2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_sep2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_sep2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_oct2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_oct2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_oct2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_nov2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_nov2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_nov2,
	(select * from de_metas_procurement.getMonthlyQtyDelivered(
		p_MonthDate := p_dec2.Startdate
		, p_C_BPartner_ID := ft.Bill_BPartner_ID
		, p_M_Product_ID := ft.M_Product_ID
		, p_M_HU_PI_Item_Product_ID := null
		, p_C_Flatrate_DataEntry_ID := fde_dec2.C_Flatrate_DataEntry_ID) as qtydelivered)as delivered_dec2

FROM

(
SELECT	C_Flatrate_Term_ID,
	M_Product_ID,
	Bill_BPartner_ID,
	(SELECT (date_part('year',min(ft.startdate) over()))) AS yearStart,
	ft.startdate, ft.enddate,
	QtyPlanned_NextYear

FROM C_Flatrate_Term ft
WHERE ft.type_conditions='Procuremnt' AND ft.isActive='Y'
	AND ft.startdate::date<=now()::date AND ft.enddate::date>=now()::date
	AND M_Product_ID IS NOT NULL AND Bill_BPartner_ID IS NOT NULL
	AND COALESCE(ft.Bill_BPartner_ID = $1, true)
	AND COALESCE(ft.M_Product_ID = $2, true)
	
)ft


JOIN M_Product p ON ft.M_Product_ID = p.M_Product_ID
JOIN C_BPartner bp ON ft.bill_bpartner_id = bp.C_BPartner_ID
JOIN c_period p_param_st ON ft.startdate = p_param_st.startdate
JOIN c_period p_param_end ON ft.enddate = p_param_end.enddate
JOIN C_Flatrate_DataEntry fda ON fda.C_Flatrate_Term_ID = ft.C_Flatrate_Term_ID
JOIN C_Period per ON per.C_Period_ID=fda.C_Period_ID


LEFT OUTER JOIN C_Flatrate_DataEntry fde_jan1 ON (ft.C_Flatrate_Term_ID = fde_jan1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_jan1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-01-01')::date) AND fde_jan1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_feb1 ON (ft.C_Flatrate_Term_ID = fde_feb1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_feb1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-02-01')::date) AND fde_feb1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_mar1 ON (ft.C_Flatrate_Term_ID = fde_mar1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_mar1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-03-01')::date) AND fde_mar1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_apr1 ON (ft.C_Flatrate_Term_ID = fde_apr1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_apr1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-04-01')::date) AND fde_apr1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_may1 ON (ft.C_Flatrate_Term_ID = fde_may1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_may1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-05-01')::date) AND fde_may1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_jun1 ON (ft.C_Flatrate_Term_ID = fde_jun1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_jun1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-06-01')::date) AND fde_jun1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_jul1 ON (ft.C_Flatrate_Term_ID = fde_jul1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_jul1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-07-01')::date) AND fde_jul1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_aug1 ON (ft.C_Flatrate_Term_ID = fde_aug1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_aug1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-08-01')::date) AND fde_aug1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_sep1 ON (ft.C_Flatrate_Term_ID = fde_sep1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_sep1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-09-01')::date) AND fde_sep1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_oct1 ON (ft.C_Flatrate_Term_ID = fde_oct1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_oct1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-10-01')::date) AND fde_oct1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_nov1 ON (ft.C_Flatrate_Term_ID = fde_nov1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_nov1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-11-01')::date) AND fde_nov1.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_dec1 ON (ft.C_Flatrate_Term_ID = fde_dec1.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_dec1.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart||'-12-01')::date) AND fde_dec1.type='PC')

LEFT OUTER JOIN C_Flatrate_DataEntry fde_jan2 ON (ft.C_Flatrate_Term_ID = fde_jan2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_jan2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-01-01')::date) AND fde_jan2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_feb2 ON (ft.C_Flatrate_Term_ID = fde_feb2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_feb2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-02-01')::date) AND fde_feb2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_mar2 ON (ft.C_Flatrate_Term_ID = fde_mar2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_mar2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-03-01')::date) AND fde_mar2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_apr2 ON (ft.C_Flatrate_Term_ID = fde_apr2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_apr2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-04-01')::date) AND fde_apr2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_may2 ON (ft.C_Flatrate_Term_ID = fde_may2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_may2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-05-01')::date) AND fde_may2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_jun2 ON (ft.C_Flatrate_Term_ID = fde_jun2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_jun2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-06-01')::date) AND fde_jun2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_jul2 ON (ft.C_Flatrate_Term_ID = fde_jul2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_jul2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-07-01')::date) AND fde_jul2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_aug2 ON (ft.C_Flatrate_Term_ID = fde_aug2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_aug2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-08-01')::date) AND fde_aug2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_sep2 ON (ft.C_Flatrate_Term_ID = fde_sep2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_sep2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-09-01')::date) AND fde_sep2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_oct2 ON (ft.C_Flatrate_Term_ID = fde_oct2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_oct2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-10-01')::date) AND fde_oct2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_nov2 ON (ft.C_Flatrate_Term_ID = fde_nov2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_nov2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-11-01')::date) AND fde_nov2.type='PC')
LEFT OUTER JOIN C_Flatrate_DataEntry fde_dec2 ON (ft.C_Flatrate_Term_ID = fde_dec2.C_Flatrate_Term_ID AND exists (SELECT 1 FROM C_Period p WHERE fde_dec2.c_period_id=p.C_Period_ID and p.Startdate::date = (yearStart+1||'-12-01')::date) AND fde_dec2.type='PC')

LEFT OUTER JOIN C_Period p_jan1 ON p_jan1.C_Period_ID = fde_jan1.C_Period_ID
LEFT OUTER JOIN C_Period p_feb1 ON p_feb1.C_Period_ID = fde_feb1.C_Period_ID
LEFT OUTER JOIN C_Period p_mar1 ON p_mar1.C_Period_ID = fde_mar1.C_Period_ID
LEFT OUTER JOIN C_Period p_apr1 ON p_apr1.C_Period_ID = fde_apr1.C_Period_ID
LEFT OUTER JOIN C_Period p_may1 ON p_may1.C_Period_ID = fde_may1.C_Period_ID
LEFT OUTER JOIN C_Period p_jun1 ON p_jun1.C_Period_ID = fde_jun1.C_Period_ID
LEFT OUTER JOIN C_Period p_jul1 ON p_jul1.C_Period_ID = fde_jul1.C_Period_ID
LEFT OUTER JOIN C_Period p_aug1 ON p_aug1.C_Period_ID = fde_aug1.C_Period_ID
LEFT OUTER JOIN C_Period p_sep1 ON p_sep1.C_Period_ID = fde_sep1.C_Period_ID
LEFT OUTER JOIN C_Period p_oct1 ON p_oct1.C_Period_ID = fde_oct1.C_Period_ID
LEFT OUTER JOIN C_Period p_nov1 ON p_nov1.C_Period_ID = fde_nov1.C_Period_ID
LEFT OUTER JOIN C_Period p_dec1 ON p_dec1.C_Period_ID = fde_dec1.C_Period_ID

LEFT OUTER JOIN C_Period p_jan2 ON p_jan2.C_Period_ID = fde_jan2.C_Period_ID
LEFT OUTER JOIN C_Period p_feb2 ON p_feb2.C_Period_ID = fde_feb2.C_Period_ID
LEFT OUTER JOIN C_Period p_mar2 ON p_mar2.C_Period_ID = fde_mar2.C_Period_ID
LEFT OUTER JOIN C_Period p_apr2 ON p_apr2.C_Period_ID = fde_apr2.C_Period_ID
LEFT OUTER JOIN C_Period p_may2 ON p_may2.C_Period_ID = fde_may2.C_Period_ID
LEFT OUTER JOIN C_Period p_jun2 ON p_jun2.C_Period_ID = fde_jun2.C_Period_ID
LEFT OUTER JOIN C_Period p_jul2 ON p_jul2.C_Period_ID = fde_jul2.C_Period_ID
LEFT OUTER JOIN C_Period p_aug2 ON p_aug2.C_Period_ID = fde_aug2.C_Period_ID
LEFT OUTER JOIN C_Period p_sep2 ON p_sep2.C_Period_ID = fde_sep2.C_Period_ID
LEFT OUTER JOIN C_Period p_oct2 ON p_oct2.C_Period_ID = fde_oct2.C_Period_ID
LEFT OUTER JOIN C_Period p_nov2 ON p_nov2.C_Period_ID = fde_nov2.C_Period_ID
LEFT OUTER JOIN C_Period p_dec2 ON p_dec2.C_Period_ID = fde_dec2.C_Period_ID

)rez

$$
LANGUAGE sql STABLE;