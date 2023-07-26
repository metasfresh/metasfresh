DROP FUNCTION IF EXISTS report.fresh_statistics_kg_week ( 
	IN C_Year_ID numeric, IN week integer,IN issotrx character varying,IN C_Activity_ID numeric,IN M_Product_ID numeric, IN M_Product_Category_ID numeric, IN M_AttributeSetInstance_ID numeric, IN convert_to_kg character varying, IN ad_org_id numeric
);

DROP FUNCTION IF EXISTS report.fresh_statistics_kg_week ( 
	IN C_Year_ID numeric, 
	IN week integer,
	IN issotrx character varying,
	IN C_Activity_ID numeric,
	IN M_Product_ID numeric, 
	IN M_Product_Category_ID numeric, 
	IN M_AttributeSetInstance_ID numeric, 
	IN convert_to_kg character varying,
	IN ad_org_id numeric,
	IN AD_Language Character Varying (6)
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
  param_activity character varying(60),
  param_product character varying(255),
  param_product_category character varying(60),
  param_attributes character varying(255),
  ad_org_id numeric,
  iso_code char(3)
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
		IN ad_org_id numeric,
		IN AD_Language Character Varying (6)
	) 
  RETURNS SETOF report.fresh_statistics_kg_week AS
$$
SELECT
	pc_name, 
	P_name,
	P_value,
	uom.UOMSymbol,
	Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
	Week1Sum, Week2Sum, Week3Sum, Week4Sum, Week5Sum, Week6Sum, Week7Sum, Week8Sum, Week9Sum, Week10Sum, Week11Sum, Week12Sum,
	TotalSum, TotalAmt,
	to_char( StartDate, 'DD.MM.YYYY' ) AS StartDate,
	to_char( EndDate, 'DD.MM.YYYY' ) AS EndDate,
	(SELECT name FROM C_Activity WHERE C_Activity_ID = $4) AS param_Activity,
	(SELECT COALESCE(pt.name, p.name) FROM M_Product p 
		LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $10 AND pt.isActive = 'Y'
		WHERE p.M_Product_ID = $5 
	) AS param_product,
	(SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $6) AS param_Product_Category,
	(SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7) AS Param_Attributes,
	a.ad_org_id,
	a.iso_code
FROM
	(
		SELECT
			v.M_Product_ID,
			v.UOMConv_ID,
			(SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0)) AS EndDate,
			(SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11)) AS StartDate,
			
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0)) AS Col1,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -1)) AS Col2,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -2)) AS Col3,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -3)) AS Col4,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -4)) AS Col5,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -5)) AS Col6,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -6)) AS Col7,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -7)) AS Col8,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -8)) AS Col9,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -9)) AS Col10,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -10)) AS Col11,
			(SELECT week_no FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11)) AS Col12,
			
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0))
				THEN v.QtyAcct ELSE 0 END ) AS Week1Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -1))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -1))
				THEN v.QtyAcct ELSE 0 END ) AS Week2Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -2))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -2))
				THEN v.QtyAcct ELSE 0 END ) AS Week3Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -3))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -3))
				THEN v.QtyAcct ELSE 0 END ) AS Week4Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -4))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -4))
				THEN v.QtyAcct ELSE 0 END ) AS Week5Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -5))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -5))
				THEN v.QtyAcct ELSE 0 END ) AS Week6Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -6))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -6))
				THEN v.QtyAcct ELSE 0 END ) AS Week7Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -7))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -7))
				THEN v.QtyAcct ELSE 0 END ) AS Week8Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -8))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -8))
				THEN v.QtyAcct ELSE 0 END ) AS Week9Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -9))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -9))
				THEN v.QtyAcct ELSE 0 END ) AS Week10Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -10))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -10))
				THEN v.QtyAcct ELSE 0 END ) AS Week11Sum,
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11))
				THEN v.QtyAcct ELSE 0 END ) AS Week12Sum,
				
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0))
				THEN v.QtyAcct ELSE 0 END ) AS TotalSum,
				
			SUM( 
				CASE WHEN 
						v.dateacct >= (SELECT startdate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, -11))
						AND v.dateacct <= (SELECT enddate FROM report.Get_Week_Number_and_Limit($2, v.fiscalyear::integer, 0))
				THEN v.AmtAcct ELSE 0 END ) AS TotalAmt,
			v.ad_org_id,
			v.iso_code,
			v.pc_value,
			v.pc_name,
            v.P_name,
            v.P_value

		 FROM (SELECT *,
                      CASE
                          WHEN $8 = 'Y' AND v.C_UOM_ID != uomkg AND convQty IS NOT NULL
                              THEN ABS(convQty) * SIGN(AmtAcct)
                              ELSE ABS(qty) * SIGN(AmtAcct)
                      END
                          AS QtyAcct,   --QtyAcct is calculated in KG where it's possible, only if convert_to_kg = 'Y'
                      CASE
                          WHEN $8 = 'Y' AND v.C_UOM_ID != uomkg AND convQty IS NOT NULL
                              THEN uomkg
                              ELSE v.C_UOM_ID
                      END AS UOMConv_ID --convert uom in KG where it's possible, only if convert_to_kg = 'Y'

               FROM fresh_statistics_kg_week_MV v) AS v
         WHERE v.C_Year_ID = $1
           AND v.IsSOtrx = $3
           AND (v.C_Activity_ID = $4 OR $4 IS NULL)
           AND (v.M_Product_ID = $5  OR $5 IS NULL)
           AND (v.M_Product_Category_ID = $6 OR $6 IS NULL)
           AND (
             -- If the given attribute set instance has values set...
             CASE
                 WHEN EXISTS(SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $7)
                     -- ... then apply following filter:
                     THEN (
                     -- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
                         EXISTS(
                                 SELECT 0
                                 FROM report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
                                          INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $7
                                     AND a.at_value = pa.at_value -- same attribute
                                     AND a.ai_value = pa.ai_value -- same value
                                 WHERE a.M_AttributeSetInstance_ID = v.il_M_AttributeSetInstance_ID
                             )
                         -- Dismiss lines where the Attributes in the Parameter are not in the InvoiceLine's asi
                         AND NOT EXISTS(
                             SELECT 0
                             FROM report.fresh_Attributes pa
                                      LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value
                                 AND a.M_AttributeSetInstance_ID = v.il_M_AttributeSetInstance_ID
                             WHERE pa.M_AttributeSetInstance_ID = $7
                               AND a.M_AttributeSetInstance_ID IS NULL
                         )
                     )
                 -- ... else deactivate the filter
                     ELSE TRUE
             END
             )
           AND v.ad_org_id = $9
         GROUP BY v.M_Product_ID,
                  v.pc_value,
                  v.p_value,
                  v.p_name,
                  v.UOMConv_ID,
                  v.AD_Org_ID,
                  v.fiscalyear,
                  v.iso_code

	) a
	INNER JOIN C_UOM uom ON a.UOMConv_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	
ORDER BY 
	pc_value, P_name
	
$$
LANGUAGE sql STABLE;

