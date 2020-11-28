DROP FUNCTION IF EXISTS report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying);

DROP FUNCTION IF EXISTS report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);

DROP TABLE IF EXISTS report.umsatzreport_report;

DROP FUNCTION IF EXISTS report.Umsatzreport_Report_Sub (IN c_period_id numeric, IN issotrx character varying);

DROP FUNCTION IF EXISTS report.Umsatzreport_Report_Sub (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);

DROP TABLE IF EXISTS report.Umsatzreport_Report_Sub;

CREATE TABLE report.Umsatzreport_Report_Sub
(
	name character varying(60),
	periodend date,
	lastyearperiodend date,
	year character varying(10),
	lastyear character varying(10),
	sameperiodsum numeric,
	sameperiodlastyearsum numeric,
	perioddifference numeric,
	perioddiffpercentage numeric,
	sameyearsum numeric,
	lastyearsum numeric,
	yeardifference numeric,
	yeardiffpercentage numeric,
	attributesetinstance character varying(60)
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.Umsatzreport_Report_Sub(IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric ) RETURNS SETOF report.Umsatzreport_Report_Sub AS
$BODY$
SELECT
	CASE WHEN Length(name) <= 45 THEN name ELSE substring(name FOR 43 ) || '...' END AS name,
	PeriodEnd,
	LastYearPeriodEnd,
	Year,
	LastYear,
	SamePeriodSum AS SamePeriodSum,
	SamePeriodLastYearSum,
	SamePeriodSum - SamePeriodLastYearSum AS PeriodDifference,
	CASE WHEN SamePeriodSum - SamePeriodLastYearSum != 0 AND SamePeriodLastYearSum != 0
		THEN (SamePeriodSum - SamePeriodLastYearSum) / SamePeriodLastYearSum * 100 ELSE NULL
	END AS PeriodDiffPercentage,
	SameYearSum AS SameYearSum,
	LastYearSum AS LastYearSum,
	SameYearSum - LastYearSum AS YearDifference,
	CASE WHEN SameYearSum - LastYearSum != 0 AND LastYearSum != 0
		THEN (SameYearSum - LastYearSum) / LastYearSum * 100 ELSE NULL
	END AS YearDiffPercentage,
	Attributes as attributesetinstance

FROM
	(
		SELECT
			bp.name,
			p.EndDate::Date AS PeriodEnd,
			pp.EndDate::Date AS LastYearPeriodEnd,
			y.fiscalYear AS Year,
			py.fiscalYear AS LastYear,
			SUM( CASE WHEN fa.C_Period_ID = p.C_Period_ID THEN AmtAcct ELSE 0 END ) AS SamePeriodSum,
			SUM( CASE WHEN fap.C_Year_ID = p.C_Year_ID AND fap.periodNo <= p.PeriodNo THEN AmtAcct ELSE 0 END ) AS SameYearSum,
			SUM( CASE WHEN fa.C_Period_ID = pp.C_Period_ID THEN AmtAcct ELSE 0 END ) AS SamePeriodLastYearSum,
			SUM( CASE WHEN fap.C_Year_ID = pp.C_Year_ID AND fap.periodNo <= pp.PeriodNo THEN AmtAcct ELSE 0 END ) AS LastYearSum,
			att.Attributes
		FROM
			C_Period p
			INNER JOIN C_Year y ON p.C_Year_ID = y.C_Year_ID
			-- Get same Period from previous year
			LEFT OUTER JOIN C_Period pp ON pp.C_Period_ID = report.Get_Predecessor_Period_Recursive ( p.C_Period_ID,
				( SELECT count(0) FROM C_Period sp WHERE sp.C_Year_ID = p.C_Year_ID and isActive = 'Y' )::int )
			LEFT OUTER JOIN C_Year py ON pp.C_Year_ID = py.C_Year_ID
			
			-- Get data from fact account
			INNER JOIN (	
				SELECT 	
					fa.M_Product_ID, fa.C_Period_ID, fa.C_BPartner_ID,
					CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct,
					il.M_AttributeSetInstance_ID
					 
				FROM 	
					Fact_Acct fa 
					JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
					JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID
				WHERE	
					AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
					AND IsSOtrx = $2
					AND ( 
				-- If the given attribute set instance has values set... 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $3 )
				-- ... then apply following filter:
				THEN ( 
					-- Take lines where the attributes of the current InvoiceLine's asi are in the parameter asi and their Values Match
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a -- a = Attributes from invoice line, pa = Parameter Attributes
							INNER JOIN report.fresh_Attributes pa ON pa.M_AttributeSetInstance_ID = $3 
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
						WHERE	pa.M_AttributeSetInstance_ID = $3
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				-- ... else deactivate the filter 
				ELSE TRUE END
			)
			) fa ON true
			INNER JOIN C_Period fap ON fa.C_Period_ID = fap.C_Period_ID
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product pr ON fa.M_Product_ID = pr.M_Product_ID  
				AND pr.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
			INNER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID

			LEFT OUTER JOIN	(
					SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID FROM Report.fresh_Attributes
					GROUP BY M_AttributeSetInstance_ID
					) att ON $3 = att.M_AttributeSetInstance_ID
		WHERE
			p.C_Period_ID = $1
			
		GROUP BY
			bp.name,
			p.EndDate,
			pp.EndDate,
			y.fiscalYear,
			py.fiscalYear,
			att.Attributes
	) a
ORDER BY
	SameYearSum DESC$BODY$
LANGUAGE sql STABLE;


DROP FUNCTION IF EXISTS report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);

DROP TABLE IF EXISTS report.umsatzreport_report;

CREATE TABLE report.umsatzreport_report
(
	name character varying(60),
	periodend date,
	lastyearperiodend date,
	year character varying(10),
	lastyear character varying(10),
	sameperiodsum numeric,
	sameperiodlastyearsum numeric,
	perioddifference numeric,
	perioddiffpercentage numeric,
	sameyearsum numeric,
	lastyearsum numeric,
	yeardifference numeric,
	yeardiffpercentage numeric,
	attributesetinstance character varying(60),
	unionorder integer
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric) RETURNS SETOF report.umsatzreport_report AS
$BODY$
	SELECT *, 1 AS UnionOrder FROM report.Umsatzreport_Report_Sub ($1, $2, $3)
UNION ALL
	SELECT 
		null as name, 
		PeriodEnd,
		LastYearPeriodEnd,
		Year,
		LastYear,
		SUM( SamePeriodSum ) AS SamePeriodSum,
		SUM( SamePeriodLastYearSum ) AS SamePeriodLastYearSum,
		SUM( SamePeriodSum ) - SUM( SamePeriodLastYearSum ) AS PeriodDifference,
		CASE WHEN SUM( SamePeriodSum ) - SUM( SamePeriodLastYearSum ) != 0 AND SUM( SamePeriodLastYearSum ) != 0
			THEN (SUM( SamePeriodSum ) - SUM( SamePeriodLastYearSum ) ) / SUM( SamePeriodLastYearSum ) * 100 ELSE NULL
		END AS PeriodDiffPercentage,
		SUM( SameYearSum ) AS SameYearSum,
		SUM( LastYearSum ) AS LastYearSum,
		SUM( SameYearSum ) - SUM( LastYearSum ) AS YearDifference,
		CASE WHEN SUM( SameYearSum ) - SUM( LastYearSum ) != 0 AND SUM( LastYearSum ) != 0
			THEN (SUM( SameYearSum ) - SUM( LastYearSum ) ) / SUM( LastYearSum ) * 100 ELSE NULL
		END AS YearDiffPercentage,
		attributesetinstance,
		2 AS UnionOrder
		
	FROM 
		report.Umsatzreport_Report_Sub ($1, $2, $3)
	GROUP BY
		PeriodEnd,
		LastYearPeriodEnd,
		Year,
		LastYear,
		attributesetinstance
ORDER BY
	UnionOrder, SameYearSum DESC
$BODY$
LANGUAGE sql STABLE;


























DROP FUNCTION IF EXISTS report.umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric
	);
DROP FUNCTION IF EXISTS report.umsatzliste_bpartner_report_sub
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric
	);
DROP TABLE IF EXISTS report.umsatzliste_bpartner_report;
DROP TABLE IF EXISTS report.umsatzliste_bpartner_report_sub;




/* ***************************************************************** */


CREATE TABLE report.umsatzliste_bpartner_report_sub
(
	bp_name character varying(60),
	pc_name character varying(60),
	p_name character varying(255),
	sameperiodsum numeric,
	compperiodsum numeric,
	perioddifference numeric,
	perioddiffpercentage numeric,
	Base_Period_Start character varying(10),
	Base_Period_End character varying(10),
	Comp_Period_Start character varying(10),
	Comp_Period_End character varying(10),
	param_IsSOTrx  character varying,
	param_bp character varying(60),
	param_Activity character varying(60),
	param_product character varying(255),
	param_Product_Category character varying(60),
	Param_Attributes character varying(255)
)
WITH (
	OIDS=FALSE
);

CREATE FUNCTION report.umsatzliste_bpartner_report_sub 
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric
	) 
	RETURNS SETOF report.umsatzliste_bpartner_report_sub AS
$BODY$
SELECT
	bp.Name AS bp_name,
	pc.Name AS pc_name, 
	p.Name AS P_name,
	SamePeriodSum,
	CompPeriodSum,
	SamePeriodSum - CompPeriodSum AS PeriodDifference,
	CASE WHEN SamePeriodSum - CompPeriodSum != 0 AND CompPeriodSum != 0
		THEN (SamePeriodSum - CompPeriodSum) / CompPeriodSum * 100 ELSE NULL
	END AS PeriodDiffPercentage,
	to_char($1, 'DD.MM.YYYY') AS Base_Period_Start,
	to_char($2, 'DD.MM.YYYY') AS Base_Period_End,
	COALESCE( to_char($3, 'DD.MM.YYYY'), '') AS Comp_Period_Start,
	COALESCE( to_char($4, 'DD.MM.YYYY'), '') AS Comp_Period_End,
	CASE WHEN $5 = 'N' THEN 'Einkauf' WHEN $5 = 'Y' THEN 'Verkauf' ELSE 'alle' END AS param_IsSOTrx,
	COALESCE ((SELECT name FROM C_BPartner WHERE C_BPartner_ID = $6), 'alle' ) AS param_bp,
	COALESCE ((SELECT name FROM C_Activity WHERE C_Activity_ID = $7), 'alle' ) AS param_Activity,
	COALESCE ((SELECT name FROM M_Product WHERE M_Product_ID = $8), 'alle' ) AS param_product,
	COALESCE ((SELECT name FROM M_Product_Category WHERE M_Product_Category_ID = $9), 'alle' ) AS param_Product_Category,
	COALESCE ((SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $10), 'alle') AS Param_Attributes
FROM
	(
		SELECT
			fa.C_BPartner_ID,
			fa.M_Product_ID,
			SUM( CASE WHEN IsInPeriod THEN AmtAcct ELSE 0 END ) AS SamePeriodSum,
			SUM( CASE WHEN IsInCompPeriod THEN AmtAcct ELSE 0 END  ) AS CompPeriodSum,
			1 AS Line_Order
		FROM
			(
				SELECT 	fa.*, 
					( fa.DateAcct >= $1 AND fa.DateAcct <= $2 ) AS IsInPeriod,
					( fa.DateAcct >= $3 AND fa.DateAcct <= $4 ) AS IsInCompPeriod,
					CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct 
				FROM 	Fact_Acct fa JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
				WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
			) fa
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
			INNER JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product p ON fa.M_Product_ID = p.M_Product_ID
		WHERE
			AD_Table_ID = ( SELECT Get_Table_ID( 'C_Invoice' ) )
			AND ( IsInPeriod OR IsInCompPeriod )
			AND i.IsSOtrx = $5
			AND ( CASE WHEN $6 IS NULL THEN TRUE ELSE fa.C_BPartner_ID = $6 END )
			AND ( CASE WHEN $7 IS NULL THEN TRUE ELSE fa.C_Activity_ID = $7 END )
			AND ( CASE WHEN $8 IS NULL THEN TRUE ELSE p.M_Product_ID = $8 END AND p.M_Product_ID IS NOT NULL )
			AND ( CASE WHEN $9 IS NULL THEN TRUE ELSE p.M_Product_Category_ID = $9 END 
				-- It was a requirement to not have HU Packing material within the sums of this report 
				AND p.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
			)
			AND ( 
				CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $10 )
				THEN ( 
					EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes a
							INNER JOIN report.fresh_Attributes pa ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND pa.M_AttributeSetInstance_ID = $10			
						WHERE	a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
					)
					AND NOT EXISTS (
						SELECT	0
						FROM	report.fresh_Attributes pa
							LEFT OUTER JOIN report.fresh_Attributes a ON a.at_value = pa.at_value AND a.ai_value = pa.ai_value 
								AND a.M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID
						WHERE	pa.M_AttributeSetInstance_ID = $10
							AND a.M_AttributeSetInstance_ID IS null
					)
				)
				ELSE TRUE END
			)
		GROUP BY
			fa.C_BPartner_ID,
			fa.M_Product_ID
	) a
	INNER JOIN C_BPartner bp ON a.C_BPartner_ID = bp.C_BPartner_ID
	INNER JOIN M_Product p ON a.M_Product_ID = p.M_Product_ID
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
$BODY$
LANGUAGE sql STABLE;


/* ***************************************************************** */


CREATE TABLE report.umsatzliste_bpartner_report
(
	bp_name character varying(60),
	pc_name character varying(60),
	p_name character varying(255),
	sameperiodsum numeric,
	compperiodsum numeric,
	perioddifference numeric,
	perioddiffpercentage numeric,
	Base_Period_Start character varying(10),
	Base_Period_End character varying(10),
	Comp_Period_Start character varying(10),
	Comp_Period_End character varying(10),
	param_IsSOTrx  character varying,
	param_bp character varying(60),
	param_Activity character varying(60),
	param_product character varying(255),
	param_Product_Category character varying(60),
	Param_Attributes character varying(255),
	unionorder integer
)
WITH (
	OIDS=FALSE
);

CREATE FUNCTION report.umsatzliste_bpartner_report 
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN Comp_Period_Start date, 
		IN Comp_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN C_Activity_ID numeric,
		IN M_Product_ID numeric,
		IN M_Product_Category_ID numeric,
		IN M_AttributeSetInstance_ID numeric
	) 
	RETURNS SETOF report.umsatzliste_bpartner_report AS
$BODY$
	SELECT 
		*, 1 AS UnionOrder
	FROM 	
		report.umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
UNION ALL
	SELECT 
		bp_name, pc_name, null AS P_name,
		SUM( SamePeriodSum ) AS SamePeriodSum,
		SUM( CompPeriodSum ) AS CompPeriodSum,
		SUM( SamePeriodSum ) - SUM( CompPeriodSum ) AS PeriodDifference,
		CASE WHEN SUM( SamePeriodSum ) - SUM( CompPeriodSum ) != 0 AND SUM( CompPeriodSum ) != 0
			THEN (SUM( SamePeriodSum ) - SUM( CompPeriodSum )) / SUM( CompPeriodSum ) * 100 ELSE NULL
		END AS PeriodDiffPercentage,
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, param_IsSOTrx, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes,
		2 AS UnionOrder
	FROM 	
		report.umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
	GROUP BY
		bp_name, pc_name, 
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, param_IsSOTrx, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes
UNION ALL
	SELECT 
		bp_name, null, null,
		SUM( SamePeriodSum ) AS SamePeriodSum,
		SUM( CompPeriodSum ) AS CompPeriodSum,
		SUM( SamePeriodSum ) - SUM( CompPeriodSum ) AS PeriodDifference,
		CASE WHEN SUM( SamePeriodSum ) - SUM( CompPeriodSum ) != 0 AND SUM( CompPeriodSum ) != 0
			THEN (SUM( SamePeriodSum ) - SUM( CompPeriodSum )) / SUM( CompPeriodSum ) * 100 ELSE NULL
		END AS PeriodDiffPercentage,
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, param_IsSOTrx, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes,
		3 AS UnionOrder
	FROM 	
		report.umsatzliste_bpartner_report_sub ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
	GROUP BY
		bp_name, 
		Base_Period_Start, Base_Period_End, Comp_Period_Start, Comp_Period_End, param_IsSOTrx, 
		param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes
ORDER BY
	bp_name, pc_name NULLS LAST, UnionOrder, p_name
$BODY$
LANGUAGE sql STABLE;
