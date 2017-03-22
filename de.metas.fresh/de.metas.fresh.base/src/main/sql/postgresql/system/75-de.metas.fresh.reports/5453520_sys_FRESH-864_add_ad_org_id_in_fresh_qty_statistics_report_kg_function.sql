DROP FUNCTION IF EXISTS report.fresh_qty_statistics_report_kg
	(
		IN C_Period_ID numeric, 
		IN issotrx character varying,
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN convert_to_kg character varying
	);
DROP FUNCTION IF EXISTS report.fresh_qty_statistics_report_kg
	(
		IN C_Period_ID numeric, 
		IN issotrx character varying,
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN convert_to_kg character varying,
		IN AD_Org_ID numeric
	);

DROP TABLE IF EXISTS report.fresh_qty_statistics_report_kg;

CREATE TABLE report.fresh_qty_statistics_report_kg
(
	pc_name character varying(60),
	p_name character varying(255),
	p_value character varying(40),
	UOMSymbol character varying(10), 
	col1 date,
	col2 date,
	col3 date,
	col4 date,
	col5 date,
	col6 date,
	col7 date,
	col8 date,
	col9 date,
	col10 date,
	col11 date,
	col12 date,
	Period1Sum numeric,
	Period2Sum numeric,
	Period3Sum numeric,
	Period4Sum numeric,
	Period5Sum numeric,
	Period6Sum numeric,
	Period7Sum numeric,
	Period8Sum numeric,
	Period9Sum numeric,
	Period10Sum numeric,
	Period11Sum numeric,
	Period12Sum numeric,
	TotalSum numeric,
	TotalAmt numeric,
	StartDate Text,
	EndDate Text,
	param_IsSOTrx character varying,
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


CREATE FUNCTION report.fresh_qty_statistics_report_kg 
	(
		IN C_Period_ID numeric, 
		IN issotrx character varying,
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN convert_to_kg character varying,
		IN AD_Org_ID numeric
	) 
	RETURNS SETOF report.fresh_qty_statistics_report_kg AS
$BODY$
	SELECT 
		*, 1 AS UnionOrder
	FROM 	
		report.fresh_statistics_kg ($1, $2, $3, $4, $5, $6, $7, $8)
UNION ALL
	SELECT 
		pc_name, null AS P_name, null AS P_value, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		SUM( Period1Sum ) AS Period1Sum, SUM( Period2Sum ) AS Period2Sum, SUM( Period3Sum ) AS Period3Sum,
		SUM( Period4Sum ) AS Period4Sum, SUM( Period5Sum ) AS Period5Sum, SUM( Period6Sum ) AS Period6Sum,
		SUM( Period7Sum ) AS Period7Sum, SUM( Period8Sum ) AS Period8Sum, SUM( Period9Sum ) AS Period9Sum,
		SUM( Period10Sum ) AS Period10Sum, SUM( Period11Sum ) AS Period11Sum, SUM( Period12Sum ) AS Period12Sum,
		SUM( TotalSum ) AS TotalSum, SUM( TotalAmt ) AS TotalAmt,
		StartDate, EndDate, param_IsSOTrx, 
		param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id,
		2 AS UnionOrder
	FROM 	
		report.fresh_statistics_kg ($1, $2, $3, $4, $5, $6, $7, $8)
	GROUP BY
		pc_name, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		StartDate, EndDate, param_IsSOTrx,  
		param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id
UNION ALL
	SELECT 
		null, null, null, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		SUM( Period1Sum ) AS Period1Sum, SUM( Period2Sum ) AS Period2Sum, SUM( Period3Sum ) AS Period3Sum,
		SUM( Period4Sum ) AS Period4Sum, SUM( Period5Sum ) AS Period5Sum, SUM( Period6Sum ) AS Period6Sum,
		SUM( Period7Sum ) AS Period7Sum, SUM( Period8Sum ) AS Period8Sum, SUM( Period9Sum ) AS Period9Sum,
		SUM( Period10Sum ) AS Period10Sum, SUM( Period11Sum ) AS Period11Sum, SUM( Period12Sum ) AS Period12Sum,
		SUM( TotalSum ) AS TotalSum, SUM( TotalAmt ) AS TotalAmt,
		StartDate, EndDate, param_IsSOTrx,
		param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id,
		3 AS UnionOrder
	FROM 	
		report.fresh_statistics_kg ($1, $2, $3, $4, $5, $6, $7, $8)
	GROUP BY
		UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		StartDate, EndDate, param_IsSOTrx,  
		param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id
UNION ALL
	SELECT 
		null, null, null, null,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		null, null, null, null, null, null, null, null, null, null, null, null,
		null, SUM( TotalAmt ) AS TotalAmt,
		StartDate, EndDate, param_IsSOTrx,  
		param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id,
		4 AS UnionOrder
	FROM 	
		report.fresh_statistics_kg ($1, $2, $3, $4, $5, $6, $7, $8)
	GROUP BY
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		StartDate, EndDate, param_IsSOTrx,
		param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id
	HAVING
		count(DISTINCT UOMSymbol) > 1
ORDER BY
	pc_name NULLS LAST, UnionOrder, p_name
$BODY$
LANGUAGE sql STABLE;
