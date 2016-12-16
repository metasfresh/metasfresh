

DROP FUNCTION IF EXISTS report.fresh_statistics_kg_week ( 
	IN C_Year_ID numeric, 
	IN week integer,
	IN issotrx character varying,
	IN C_Activity_ID numeric,
	IN M_Product_ID numeric, 
	IN M_Product_Category_ID numeric, 
	IN M_AttributeSetInstance_ID numeric, 
	IN convert_to_kg character varying,
	IN ad_org_id numeric
);

DROP TABLE IF EXISTS report.fresh_statistics_kg_week;

/* ***************************************************************** */

CREATE TABLE report.fresh_statistics_kg_week
(
  pc_name character varying,
  p_name character varying,
  p_value character varying,
  uomsymbol character varying,
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
  week1sum numeric,
  week2sum numeric,
  week3sum numeric,
  week4sum numeric,
  week5sum numeric,
  week6sum numeric,
  week7sum numeric,
  week8sum numeric,
  week9sum numeric,
  week10sum numeric,
  week11sum numeric,
  week12sum numeric,
  totalsum numeric,
  totalamt numeric,
  startdate text,
  enddate text,
  param_issotrx character varying,
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

CREATE FUNCTION report.fresh_statistics_kg_week
	(
		IN C_Year_ID numeric, 
		IN week integer,
		IN issotrx character varying,
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric,
		IN convert_to_kg character varying,
		IN ad_org_id numeric 
	) 
  RETURNS SETOF report.fresh_statistics_kg_week AS
$$
SELECT
	pc.Name AS pc_name, 
	p.Name AS P_name,
	p.value AS P_value,
	uom.UOMSymbol,
	Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
	Week1Sum, Week2Sum, Week3Sum, Week4Sum, Week5Sum, Week6Sum, Week7Sum, Week8Sum, Week9Sum, Week10Sum, Week11Sum, Week12Sum,
	TotalSum, TotalAmt,
	to_char( StartDate, 'DD.MM.YYYY' ) AS StartDate,
	to_char( EndDate, 'DD.MM.YYYY' ) AS EndDate,
	CASE WHEN $3 = 'N' THEN 'Einkauf' WHEN $3 = 'Y' THEN 'Verkauf' ELSE 'alle' END AS param_IsSOTrx,
	COALESCE ((SELECT name FROM C_Activity WHERE C_Activity_ID = $4), 'alle' ) AS param_Activity,
	COALESCE ((SELECT name FROM M_Product WHERE M_Product_ID = $5), 'alle' ) AS param_product,
	COALESCE ((SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $6), 'alle' ) AS param_Product_Category,
	COALESCE ((SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7), 'alle') AS Param_Attributes,
	a.ad_org_id
FROM
	(
		SELECT
			fa.M_Product_ID,
			fa.UOMConv_ID,
			(SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0)) AS EndDate,
			(SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11)) AS StartDate,
			
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0)) AS Col1,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1)) AS Col2,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2)) AS Col3,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3)) AS Col4,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4)) AS Col5,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5)) AS Col6,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6)) AS Col7,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7)) AS Col8,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8)) AS Col9,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9)) AS Col10,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10)) AS Col11,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11)) AS Col12,
			
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.QtyAcct ELSE 0 END ) AS Week1Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -1))
				THEN fa.QtyAcct ELSE 0 END ) AS Week2Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -2))
				THEN fa.QtyAcct ELSE 0 END ) AS Week3Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -3))
				THEN fa.QtyAcct ELSE 0 END ) AS Week4Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -4))
				THEN fa.QtyAcct ELSE 0 END ) AS Week5Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -5))
				THEN fa.QtyAcct ELSE 0 END ) AS Week6Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -6))
				THEN fa.QtyAcct ELSE 0 END ) AS Week7Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -7))
				THEN fa.QtyAcct ELSE 0 END ) AS Week8Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -8))
				THEN fa.QtyAcct ELSE 0 END ) AS Week9Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -9))
				THEN fa.QtyAcct ELSE 0 END ) AS Week10Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -10))
				THEN fa.QtyAcct ELSE 0 END ) AS Week11Sum,
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
				THEN fa.QtyAcct ELSE 0 END ) AS Week12Sum,
				
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.QtyAcct ELSE 0 END ) AS TotalSum,
				
			SUM( 
				CASE WHEN 
						fa.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, -11))
						AND fa.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, y.fiscalyear::integer, 0))
				THEN fa.AmtAcct ELSE 0 END ) AS TotalAmt,
			fa.ad_org_id	

		FROM
			C_Year y
			
			LEFT OUTER JOIN 
			(
				SELECT *,
				CASE WHEN $8 = 'Y' AND fa.C_UOM_ID != uomkg AND convQty IS NOT NULL 
					THEN ABS(convQty) * SIGN(AmtAcct) 
				ELSE ABS(qty) * SIGN(AmtAcct) END
				AS QtyAcct, --QtyAcct is calculated in KG where it's possible, only if convert_to_kg = 'Y'
				CASE WHEN $8 = 'Y' AND fa.C_UOM_ID != uomkg AND convQty IS NOT NULL 
					THEN uomkg
				ELSE fa.C_UOM_ID END AS UOMConv_ID --convert uom in KG where it's possible, only if convert_to_kg = 'Y'
				FROM (	
					SELECT 	fa.*, CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct, uomconvert(fa.M_Product_ID, fa.C_UOM_ID,(SELECT C_UOM_ID as uom_conv FROM C_UOM WHERE x12de355='KGM' and IsActive='Y'),qty ) AS convQty, (SELECT C_UOM_ID as uom_conv FROM C_UOM WHERE x12de355='KGM' and IsActive='Y') AS uomkg
					FROM 	Fact_Acct fa 
					JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
					WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice')) AND fa.isActive = 'Y'	            
				) fa
			) fa ON true
			
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
			INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
		WHERE
			fa.AD_Table_ID = ( SELECT Get_Table_ID( 'C_Invoice' ) )
			-- Akontozahlung invoices are not included. See FRESH_609
			AND i.C_DocType_ID NOT IN (SELECT C_DocType_ID FROM C_DocType WHERE docbasetype='API' AND docsubtype='DP')
			AND y.C_Year_ID = $1
			AND i.IsSOtrx = $3
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
			AND fa.ad_org_id = $9
		GROUP BY
			fa.M_Product_ID,
			fa.UOMConv_ID, 
			fa.AD_Org_ID,
			y.fiscalyear

	) a
	INNER JOIN C_UOM uom ON a.UOMConv_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	
ORDER BY 
	pc.value, p.name
	
$$
LANGUAGE sql STABLE;

