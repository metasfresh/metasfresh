DROP FUNCTION IF EXISTS report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);
DROP FUNCTION IF EXISTS report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric);

DROP TABLE IF EXISTS report.umsatzreport_report;

DROP FUNCTION IF EXISTS report.Umsatzreport_Report_Sub (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);
DROP FUNCTION IF EXISTS report.Umsatzreport_Report_Sub (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric);

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
	attributesetinstance character varying(60),
	ad_org_id numeric
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.Umsatzreport_Report_Sub(IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric) RETURNS SETOF report.Umsatzreport_Report_Sub AS
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
	Attributes as attributesetinstance,
	ad_org_id
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
			att.Attributes,
			fa.ad_org_id
		FROM
			C_Period p
			INNER JOIN C_Year y ON p.C_Year_ID = y.C_Year_ID AND y.isActive = 'Y'
			-- Get same Period from previous year
			LEFT OUTER JOIN C_Period pp ON pp.C_Period_ID = report.Get_Predecessor_Period_Recursive ( p.C_Period_ID,
				( SELECT count(0) FROM C_Period sp WHERE sp.C_Year_ID = p.C_Year_ID and isActive = 'Y' )::int ) AND pp.isActive = 'Y'
			LEFT OUTER JOIN C_Year py ON pp.C_Year_ID = py.C_Year_ID AND py.isActive = 'Y'
			
			-- Get data from fact account
			INNER JOIN (	
				SELECT 	
					fa.M_Product_ID, fa.C_Period_ID, fa.C_BPartner_ID,
					CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct,
					il.M_AttributeSetInstance_ID, fa.ad_org_id
					 
				FROM 	
					Fact_Acct fa 
					JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID AND i.isActive = 'Y'
					JOIN C_InvoiceLine il ON fa.Line_ID = il.C_InvoiceLine_ID AND il.isActive = 'Y'
				WHERE	
					AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
					AND IsSOtrx = $2 AND fa.isActive = 'Y'
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
			INNER JOIN C_Period fap ON fa.C_Period_ID = fap.C_Period_ID AND fap.isActive = 'Y'
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product pr ON fa.M_Product_ID = pr.M_Product_ID  
				AND pr.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y') AND pr.isActive = 'Y'
			INNER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'

			LEFT OUTER JOIN	(
					SELECT 	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID FROM Report.fresh_Attributes
					GROUP BY M_AttributeSetInstance_ID
					) att ON $3 = att.M_AttributeSetInstance_ID
		WHERE

			p.C_Period_ID = $1 AND p.isActive = 'Y'
			AND fa.ad_org_id = $4

		GROUP BY
			bp.name,
			p.EndDate,
			pp.EndDate,
			y.fiscalYear,
			py.fiscalYear,
			att.Attributes,
			fa.ad_org_id
	) a
ORDER BY
	SameYearSum DESC$BODY$
LANGUAGE sql STABLE;



DROP FUNCTION IF EXISTS report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric);
DROP FUNCTION IF EXISTS report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric);

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
	ad_org_id numeric,
	unionorder integer
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.umsatzreport_report (IN c_period_id numeric, IN issotrx character varying, IN M_AttributeSetInstance_ID numeric, IN AD_Org_ID numeric) RETURNS SETOF report.umsatzreport_report AS
$BODY$
	SELECT *, 1 AS UnionOrder FROM report.Umsatzreport_Report_Sub ($1, $2, $3, $4)
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
		ad_org_id,
		2 AS UnionOrder
		
	FROM 
		report.Umsatzreport_Report_Sub ($1, $2, $3, $4)
	GROUP BY
		PeriodEnd,
		LastYearPeriodEnd,
		Year,
		LastYear,
		attributesetinstance,
		ad_org_id
ORDER BY
	UnionOrder, SameYearSum DESC
$BODY$
LANGUAGE sql STABLE;

