--DROP FUNCTION IF EXISTS report.fresh_Get_Predecessor_Period (IN Source_Period_ID numeric);

DROP FUNCTION IF EXISTS report.fresh_statistics ( 
	IN C_Period_ID numeric, IN issotrx character varying, IN C_BPartner_ID numeric, IN C_Activity_ID numeric,
	IN M_Product_ID numeric, IN M_Product_Category_ID numeric, IN M_AttributeSetInstance_ID numeric
);
DROP FUNCTION IF EXISTS report.fresh_statistics ( 
	IN C_Period_ID numeric, IN issotrx character varying, IN C_BPartner_ID numeric, IN C_Activity_ID numeric,
	IN M_Product_ID numeric, IN M_Product_Category_ID numeric, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric
);


DROP TABLE IF EXISTS report.fresh_statistics;

/* ***************************************************************** */

CREATE TABLE report.fresh_statistics
(
  bp_name character varying,
  bp_value character varying,
  pc_name character varying,
  p_name character varying,
  p_value character varying,
  uomsymbol character varying,
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
  param_bp character varying(60),
  param_activity character varying(60),
  param_product character varying(255),
  param_product_category character varying(60),
  param_attributes character varying(255),
  ad_org_id numeric
)
WITH (
  OIDS=FALSE
);

/* ***************************************************************** */

CREATE FUNCTION report.fresh_statistics
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
  RETURNS SETOF report.fresh_statistics AS
$BODY$
SELECT
	bp.Name AS bp_name,
	bp.value AS bp_Value,
	pc.Name AS pc_name, 
	p.Name AS P_name,
	p.value AS P_value,
	uom.UOMSymbol,
	Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
	Period1Sum, Period2Sum, Period3Sum, Period4Sum, Period5Sum, Period6Sum, Period7Sum, Period8Sum, Period9Sum, Period10Sum, Period11Sum, Period12Sum,
	TotalSum, TotalAmt,
	to_char( COALESCE(Col12, Col11, Col10, Col9, Col8, Col7, Col6, Col5, Col4, Col3, Col2, Col1), 'DD.MM.YYYY' ) AS StartDate,
	to_char( EndDate, 'DD.MM.YYYY' ) AS EndDate,
	CASE WHEN $2 = 'N' THEN 'Einkauf' WHEN $2 = 'Y' THEN 'Verkauf' ELSE 'alle' END AS param_IsSOTrx,
	COALESCE ((SELECT name FROM C_BPartner WHERE C_BPartner_ID = $3), 'alle' ) AS param_bp,
	COALESCE ((SELECT name FROM C_Activity WHERE C_Activity_ID = $4), 'alle' ) AS param_Activity,
	COALESCE ((SELECT name FROM M_Product WHERE M_Product_ID = $5), 'alle' ) AS param_product,
	COALESCE ((SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $6), 'alle' ) AS param_Product_Category,
	COALESCE ((SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7), 'alle') AS Param_Attributes,
	a.ad_org_id
FROM
	(
		SELECT
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			fa.C_UOM_ID, 
			p1.EndDate::Date,
			p1.StartDate::Date AS Col1, p2.StartDate::Date AS Col2, p3.StartDate::Date AS Col3, p4.StartDate::Date AS Col4, 
			p5.StartDate::Date AS Col5, p6.StartDate::Date AS Col6, p7.StartDate::Date AS Col7, p8.StartDate::Date AS Col8, 
			p9.StartDate::Date AS Col9, p10.StartDate::Date AS Col10, p11.StartDate::Date AS Col11, p12.StartDate::Date AS Col12,
			SUM( CASE WHEN fa.C_Period_ID = p1.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period1Sum,
			SUM( CASE WHEN fa.C_Period_ID = p2.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period2Sum,
			SUM( CASE WHEN fa.C_Period_ID = p3.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period3Sum,
			SUM( CASE WHEN fa.C_Period_ID = p4.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period4Sum,
			SUM( CASE WHEN fa.C_Period_ID = p5.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period5Sum,
			SUM( CASE WHEN fa.C_Period_ID = p6.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period6Sum,
			SUM( CASE WHEN fa.C_Period_ID = p7.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period7Sum,
			SUM( CASE WHEN fa.C_Period_ID = p8.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period8Sum,
			SUM( CASE WHEN fa.C_Period_ID = p9.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period9Sum,
			SUM( CASE WHEN fa.C_Period_ID = p10.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period10Sum,
			SUM( CASE WHEN fa.C_Period_ID = p11.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period11Sum,
			SUM( CASE WHEN fa.C_Period_ID = p12.C_Period_ID THEN fa.QtyAcct ELSE 0 END ) AS Period12Sum,
			SUM( CASE WHEN fa.C_Period_ID IN 
					(p1.C_Period_ID, p2.C_Period_ID, p3.C_Period_ID, p4.C_Period_ID, p5.C_Period_ID, p6.C_Period_ID, 
					p7.C_Period_ID, p8.C_Period_ID, p9.C_Period_ID, p10.C_Period_ID, p11.C_Period_ID, p12.C_Period_ID)
				THEN fa.QtyAcct ELSE 0 END
			) AS TotalSum,
			SUM( CASE WHEN fa.C_Period_ID IN 
					(p1.C_Period_ID, p2.C_Period_ID, p3.C_Period_ID, p4.C_Period_ID, p5.C_Period_ID, p6.C_Period_ID, 
					p7.C_Period_ID, p8.C_Period_ID, p9.C_Period_ID, p10.C_Period_ID, p11.C_Period_ID, p12.C_Period_ID)
				THEN fa.AmtAcct ELSE 0 END
			) AS TotalAmt,
			fa.ad_org_id
		FROM
			C_Period p1
			LEFT OUTER JOIN C_Period p2 ON p2.C_Period_ID = report.fresh_Get_Predecessor_Period(p1.C_Period_ID)
			LEFT OUTER JOIN C_Period p3 ON p3.C_Period_ID = report.fresh_Get_Predecessor_Period(p2.C_Period_ID)
			LEFT OUTER JOIN C_Period p4 ON p4.C_Period_ID = report.fresh_Get_Predecessor_Period(p3.C_Period_ID)
			LEFT OUTER JOIN C_Period p5 ON p5.C_Period_ID = report.fresh_Get_Predecessor_Period(p4.C_Period_ID)
			LEFT OUTER JOIN C_Period p6 ON p6.C_Period_ID = report.fresh_Get_Predecessor_Period(p5.C_Period_ID)
			LEFT OUTER JOIN C_Period p7 ON p7.C_Period_ID = report.fresh_Get_Predecessor_Period(p6.C_Period_ID)
			LEFT OUTER JOIN C_Period p8 ON p8.C_Period_ID = report.fresh_Get_Predecessor_Period(p7.C_Period_ID)
			LEFT OUTER JOIN C_Period p9 ON p9.C_Period_ID = report.fresh_Get_Predecessor_Period(p8.C_Period_ID)
			LEFT OUTER JOIN C_Period p10 ON p10.C_Period_ID = report.fresh_Get_Predecessor_Period(p9.C_Period_ID)
			LEFT OUTER JOIN C_Period p11 ON p11.C_Period_ID = report.fresh_Get_Predecessor_Period(p10.C_Period_ID)
			LEFT OUTER JOIN C_Period p12 ON p12.C_Period_ID = report.fresh_Get_Predecessor_Period(p11.C_Period_ID)
			
			LEFT OUTER JOIN 
			(
				SELECT *, ABS(qty) * SIGN(AmtAcct) AS QtyAcct
				FROM (	
					SELECT 	fa.*, CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct 
					FROM 	Fact_Acct fa JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
					WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
				) fa
			) fa ON true
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
			INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
		WHERE
			fa.AD_Table_ID = ( SELECT Get_Table_ID( 'C_Invoice' ) )
			-- Akontozahlung invoices are not included. See FRESH_609
			AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID FROM C_DocType WHERE docbasetype='API' AND docsubtype='DP')
			AND p1.C_Period_ID = $1
			AND i.IsSOtrx = $2
			AND ( CASE WHEN $3 IS NULL THEN TRUE ELSE fa.C_BPartner_ID = $3 END )
			AND ( CASE WHEN $4 IS NULL THEN TRUE ELSE fa.C_Activity_ID = $4 END )
			AND ( CASE WHEN $5 IS NULL THEN TRUE ELSE p.M_Product_ID = $5 END )
			AND ( CASE WHEN $6 IS NULL THEN TRUE ELSE p.M_Product_Category_ID = $6 END 
				-- It was a requirement to not have HU Packing material within the sums of the Statistics reports 
				AND p.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
			)
			AND ( 
				-- If the given attribute set instance has values set... 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7 )
				-- ... then apply following filter:
				THEN ( 
					-- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
							INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $7 
								AND a.at_value = pa.at_value -- same attribute
								AND a.ai_value = pa.ai_value -- same value
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $7
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				-- ... else deactivate the filter 
				ELSE TRUE END
			)
			AND fa.ad_org_id = $8
		GROUP BY
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			fa.C_UOM_ID, 
			p1.EndDate,
			p1.StartDate, p2.StartDate, p3.StartDate, p4.StartDate, p5.StartDate, p6.StartDate, 
			p7.StartDate, p8.StartDate, p9.StartDate, p10.StartDate, p11.StartDate, p12.StartDate,
			fa.ad_org_id
	) a
	INNER JOIN C_UOM uom ON a.C_UOM_ID = uom.C_UOM_ID
	INNER JOIN C_BPartner bp ON a.C_BPartner_ID = bp.C_BPartner_ID
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
ORDER BY
	bp.Name, pc.value, p.name
$BODY$
LANGUAGE sql VOLATILE;

COMMENT ON FUNCTION report.fresh_product_statistics_report(numeric, character varying, numeric, numeric, numeric, numeric, numeric) IS 'Making this function volatile is currently our only known way to avoid
http://postgresql.nabble.com/BUG-8393-quot-ERROR-failed-to-locate-grouping-columns-quot-on-grouping-by-varchar-returned-from-funcn-td5768367.html';