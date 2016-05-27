
--DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Purchase_Order_Aging_Report (IN dateFrom timestamp with time zone, IN dateTo timestamp with time zone) ;
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Purchase_Order_Aging_Report (IN dateFrom timestamp with time zone, IN dateTo timestamp with time zone) 
RETURNS TABLE 
(	
	intervalH character varying,
	ordersNo character varying,
	grandTotal character varying,
	ordersNoTotal character varying
)
AS
$$
SELECT 
	intervalH,
	(CASE WHEN 
		ordersNo>=1000 THEN (ordersNo/1000)::numeric(10,1)|| 'K' 
	ELSE 
		(ordersNo::integer)::character varying	
	END) AS ordersNo,
	(CASE WHEN 
		grandTotal>=1000 THEN (grandTotal/1000)::numeric(10,1)|| 'K' 
	ELSE 
		(grandTotal::integer)::character varying	
	END) AS grandTotal,
	(CASE WHEN 
		SUM(ordersNo) OVER ()>=1000 THEN (SUM(ordersNo) OVER ()/1000)::numeric(10,1)|| 'K' 
	ELSE 
		(SUM(ordersNo) OVER ()::integer)::character varying	
	END) AS ordersNoTotal

FROM (
	SELECT 
		'2h' AS intervalH,
		COUNT(o.C_Order_ID) AS ordersNo,
		SUM(o.grandTotal) AS grandTotal 

	FROM de_metas_fresh_kpi.kpi_purchase_order_aging_v o
	WHERE ($2::timestamp with time zone - o.completedH::timestamp with time zone) <='02:00:00'
	AND $1 <= o.completedH AND $2 >= o.completedH

	UNION ALL
	(
	SELECT 
		'5h' AS intervalH,
		COUNT(o.C_Order_ID) AS ordersNo,
		SUM(o.grandTotal) AS grandTotal 

	FROM de_metas_fresh_kpi.kpi_purchase_order_aging_v o
	WHERE ($2::timestamp with time zone - o.completedH::timestamp with time zone) <='05:00:00'
		AND ($2::timestamp with time zone - o.completedH::timestamp with time zone) >'02:00:00'
		AND $1 <= o.completedH AND $2 >= o.completedH
	)
	UNION ALL
	(
	SELECT 
		'12h' AS intervalH,
		COUNT(o.C_Order_ID) AS ordersNo,
		SUM(o.grandTotal) AS grandTotal 

	FROM de_metas_fresh_kpi.kpi_purchase_order_aging_v o
	WHERE ($2::timestamp with time zone - o.completedH::timestamp with time zone) <='12:00:00'
		AND ($2::timestamp with time zone - o.completedH::timestamp with time zone) >'05:00:00'
			AND $1 <= o.completedH AND $2 >= o.completedH
	)
	UNION ALL
	(
	SELECT 
		'2d' AS intervalH,
		COUNT(o.C_Order_ID) AS ordersNo,
		SUM(o.grandTotal) AS grandTotal 

	FROM de_metas_fresh_kpi.kpi_purchase_order_aging_v o
	WHERE ($2::timestamp with time zone - o.completedH::timestamp with time zone) <='2 days'
		AND ($2::timestamp with time zone - o.completedH::timestamp with time zone) >'12:00:00'
			AND $1 <= o.completedH AND $2 >= o.completedH
	)
	UNION ALL
	(
	SELECT 
		'+2d' AS intervalH,
		COUNT(o.C_Order_ID) AS ordersNo,
		SUM(o.grandTotal) AS grandTotal 

	FROM de_metas_fresh_kpi.kpi_purchase_order_aging_v o
	WHERE ($2::timestamp with time zone - o.completedH::timestamp with time zone) >'2 days'
			AND $1 <= o.completedH AND $2 >= o.completedH
	)
)i
$$
LANGUAGE sql STABLE;