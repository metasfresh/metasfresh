
DROP FUNCTION IF EXISTS report.fresh_product_statistics_report
	(
		IN C_Period_ID numeric, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric
	);
	
DROP FUNCTION IF EXISTS report.fresh_product_statistics_report
	(
		IN C_Period_ID numeric, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric
	);	

DROP TABLE IF EXISTS report.fresh_product_statistics_report;

CREATE TABLE report.fresh_product_statistics_report
(
  bp_name character varying,
  bp_value character varying,
  pc_name character varying,
  p_name character varying,
  p_value character varying,
  UOMSymbol character varying,
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
  period1sum numeric,
  period2sum numeric,
  period3sum numeric,
  period4sum numeric,
  period5sum numeric,
  period6sum numeric,
  period7sum numeric,
  period8sum numeric,
  period9sum numeric,
  period10sum numeric,
  period11sum numeric,
  period12sum numeric,
  totalsum numeric,
  totalamt numeric,
  startdate text,
  enddate text,
  param_issotrx character varying,
  param_bp character varying,
  param_activity character varying,
  param_product character varying,
  param_product_category character varying,
  param_attributes character varying,
  ad_org_id numeric,
  unionorder integer
)
WITH (
  OIDS=FALSE
);


CREATE OR REPLACE FUNCTION report.fresh_product_statistics_report 
	(
		IN C_Period_ID numeric, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Org_ID numeric
	) 
  RETURNS SETOF report.fresh_product_statistics_report AS
$BODY$
	SELECT 
		*, 1 AS UnionOrder
	FROM 	
		report.fresh_statistics ($1, $2, $3, $4, $5, $6, $7, $8)
UNION ALL
	SELECT 
		null, null, pc_name, P_name, P_value, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		SUM( Period1Sum ) AS Period1Sum, SUM( Period2Sum ) AS Period2Sum, SUM( Period3Sum ) AS Period3Sum,
		SUM( Period4Sum ) AS Period4Sum, SUM( Period5Sum ) AS Period5Sum, SUM( Period6Sum ) AS Period6Sum,
		SUM( Period7Sum ) AS Period7Sum, SUM( Period8Sum ) AS Period8Sum, SUM( Period9Sum ) AS Period9Sum,
		SUM( Period10Sum ) AS Period10Sum, SUM( Period11Sum ) AS Period11Sum, SUM( Period12Sum ) AS Period12Sum,
		SUM( TotalSum ) AS TotalSum, SUM( TotalAmt ) AS TotalAmt,
		StartDate, EndDate, param_IsSOTrx, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id,
		2 AS UnionOrder
	FROM 	
		report.fresh_statistics ($1, $2, $3, $4, $5, $6, $7, $8)
	GROUP BY
		pc_name, P_name, P_value, UOMSymbol,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
		StartDate, EndDate, param_IsSOTrx, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id
ORDER BY
	p_name, UnionOrder, TotalSum DESC
$BODY$
LANGUAGE sql VOLATILE;

COMMENT ON FUNCTION report.fresh_product_statistics_report(numeric, character varying, numeric, numeric, numeric, numeric, numeric, numeric) IS 'Making this function volatile is currently our only known way to avoid
http://postgresql.nabble.com/BUG-8393-quot-ERROR-failed-to-locate-grouping-columns-quot-on-grouping-by-varchar-returned-from-funcn-td5768367.html';