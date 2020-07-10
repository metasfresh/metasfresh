
DROP FUNCTION IF EXISTS report.fresh_qty_statistics_week_report
	(
		IN C_Year_ID numeric, 
		IN week integer,
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric
	);

DROP TABLE IF EXISTS report.fresh_qty_statistics_week_report;

CREATE TABLE report.fresh_qty_statistics_week_report
(
	bp_name character varying(60),
	bp_value character varying(40),
	pc_name character varying(60),
	p_name character varying(255),
	p_value character varying(40),
	UOMSymbol character varying(10), 
	col1 integer,
	col2 integer,
	col3 integer,
	col4 integer,
	col5 integer,
	col6 integer,
	col7 integer,
	col8 integer,
	col9 integer,
	col10 integer,
	col11 integer,
	col12 integer,
	Week1Sum numeric,
	Week2Sum numeric,
	Week3Sum numeric,
	Week4Sum numeric,
	Week5Sum numeric,
	Week6Sum numeric,
	Week7Sum numeric,
	Week8Sum numeric,
	Week9Sum numeric,
	Week10Sum numeric,
	Week11Sum numeric,
	Week12Sum numeric,
	TotalSum numeric,
	TotalAmt numeric,
	StartDate Text,
	EndDate Text,
	param_IsSOTrx character varying,
	param_bp character varying(60),
	param_Activity character varying(60),
	param_product character varying(255),
	param_Product_Category character varying(60),
	Param_Attributes character varying(255),
	ad_org_id numeric,
	unionorder integer
	
)
WITH (
	OIDS=FALSE
);


CREATE OR REPLACE FUNCTION report.fresh_qty_statistics_week_report 
	(
		IN C_Year_ID numeric,
		IN week integer,
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric
	) 
	RETURNS SETOF report.fresh_qty_statistics_week_report AS
$$
	SELECT 
		*, 1 AS UnionOrder
	FROM 	
		report.fresh_statistics_week ($1, $2, $3, $4, $5, $6, $7, $8, $9)
UNION ALL
	SELECT 
		bp_name, bp_Value, pc_name, null AS P_name, null AS P_value, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		SUM( Week1Sum ) AS Week1Sum, SUM( Week2Sum ) AS Week2Sum, SUM( Week3Sum ) AS Week3Sum,
		SUM( Week4Sum ) AS Week4Sum, SUM( Week5Sum ) AS Week5Sum, SUM( Week6Sum ) AS Week6Sum,
		SUM( Week7Sum ) AS Week7Sum, SUM( Week8Sum ) AS Week8Sum, SUM( Week9Sum ) AS Week9Sum,
		SUM( Week10Sum ) AS Week10Sum, SUM( Week11Sum ) AS Week11Sum, SUM( Week12Sum ) AS Week12Sum,
		SUM( TotalSum ) AS TotalSum, SUM( TotalAmt ) AS TotalAmt,
		StartDate, EndDate, param_IsSOTrx, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id,
		2 AS UnionOrder
	FROM 	
		report.fresh_statistics_week ($1, $2, $3, $4, $5, $6, $7, $8, $9)
	GROUP BY
		bp_name, bp_Value, pc_name, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		StartDate, EndDate, param_IsSOTrx, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id
UNION ALL
	SELECT 
		bp_name, bp_Value, null, null, null, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		SUM( Week1Sum ) AS Week1Sum, SUM( Week2Sum ) AS Week2Sum, SUM( Week3Sum ) AS Week3Sum,
		SUM( Week4Sum ) AS Week4Sum, SUM( Week5Sum ) AS Week5Sum, SUM( Week6Sum ) AS Week6Sum,
		SUM( Week7Sum ) AS Week7Sum, SUM( Week8Sum ) AS Week8Sum, SUM( Week9Sum ) AS Week9Sum,
		SUM( Week10Sum ) AS Week10Sum, SUM( Week11Sum ) AS Week11Sum, SUM( Week12Sum ) AS Week12Sum,
		SUM( TotalSum ) AS TotalSum, SUM( TotalAmt ) AS TotalAmt,
		StartDate, EndDate, param_IsSOTrx, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id,
		3 AS UnionOrder
	FROM 	
		report.fresh_statistics_week ($1, $2, $3, $4, $5, $6, $7, $8, $9)
	GROUP BY
		bp_name, bp_Value, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		StartDate, EndDate, param_IsSOTrx, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id
UNION ALL
	SELECT 
		bp_name, bp_Value, null, null, null, null,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, SUM( TotalAmt ) AS TotalAmt,
		StartDate, EndDate, param_IsSOTrx, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id,
		4 AS UnionOrder
	FROM 	
		report.fresh_statistics_week ($1, $2, $3, $4, $5, $6, $7, $8, $9)
	GROUP BY
		bp_name, bp_Value,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		StartDate, EndDate, param_IsSOTrx, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id
	HAVING
		count(DISTINCT UOMSymbol) > 1
ORDER BY
	bp_name, pc_name NULLS LAST, UnionOrder, p_name
$$
LANGUAGE sql STABLE;
