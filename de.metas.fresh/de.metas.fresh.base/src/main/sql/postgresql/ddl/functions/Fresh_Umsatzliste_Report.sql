DROP FUNCTION IF EXISTS report.fresh_Umsatzreport_Report_Sub (IN c_period_id numeric, IN issotrx character varying);

DROP TABLE IF EXISTS report.fresh_Umsatzreport_Report_Sub;

CREATE TABLE report.fresh_Umsatzreport_Report_Sub
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
	yeardiffpercentage numeric
)
WITH (
	OIDS=FALSE
);
ALTER TABLE report.fresh_Umsatzreport_Report_Sub OWNER TO adempiere;

CREATE FUNCTION report.fresh_Umsatzreport_Report_Sub(IN c_period_id numeric, IN issotrx character varying) RETURNS SETOF report.fresh_Umsatzreport_Report_Sub AS
$BODY$SELECT
	name,
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
	END AS YearDiffPercentage

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
			SUM( CASE WHEN fap.C_Year_ID = pp.C_Year_ID AND fap.periodNo <= pp.PeriodNo THEN AmtAcct ELSE 0 END ) AS LastYearSum
		FROM
			C_Period p
			-- Get same Period from previous year
			INNER JOIN (
				SELECT *, (FiscalYear::Numeric-1)::Character Varying AS PreviousFiscalYear FROM C_Year
			) y ON p.C_Year_ID = y.C_Year_ID
			LEFT OUTER JOIN C_Year py ON y.C_Calendar_ID = py.C_Calendar_ID AND py.FiscalYear = y.PreviousFiscalYear
			LEFT OUTER JOIN C_Period pp ON py.C_Year_ID = pp.C_Year_ID AND p.PeriodNo = pp.PeriodNo

			INNER JOIN (	
					SELECT 	fa.*, CASE WHEN isSOTrx = 'Y' THEN AmtAcctCr - AmtAcctDr ELSE AmtAcctDr - AmtAcctCr END AS AmtAcct 
					FROM 	Fact_Acct fa JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
					WHERE	AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
				) fa ON true
			INNER JOIN C_Period fap ON fa.C_Period_ID = fap.C_Period_ID
			INNER JOIN C_Invoice i ON fa.Record_ID = i.C_Invoice_ID
			INNER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID
			/* Please note: This is an important implicit filter. Inner Joining the Product
			 * filters Fact Acct records for e.g. Taxes
			 */  
			INNER JOIN M_Product pr ON fa.M_Product_ID = pr.M_Product_ID  
		WHERE
			p.C_Period_ID = $1
			AND AD_Table_ID = (SELECT Get_Table_ID('C_Invoice'))
			AND i.IsSOtrx = $2
			-- It was a requirement to not have HU Packing material within the sums of this report 
			AND pr.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
		GROUP BY
			bp.name,
			p.EndDate,
			pp.EndDate,
			y.fiscalYear,
			py.fiscalYear
	) a
ORDER BY
	SameYearSum DESC$BODY$
LANGUAGE sql STABLE;
ALTER FUNCTION report.fresh_Umsatzreport_Report_Sub(IN numeric, IN character varying) OWNER TO adempiere;


DROP FUNCTION IF EXISTS report.fresh_umsatzreport_report (IN c_period_id numeric, IN issotrx character varying);

DROP TABLE IF EXISTS report.fresh_umsatzreport_report;

CREATE TABLE report.fresh_umsatzreport_report
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
	unionorder integer
)
WITH (
	OIDS=FALSE
);
ALTER TABLE report.fresh_umsatzreport_report OWNER TO adempiere;

CREATE FUNCTION report.fresh_umsatzreport_report (IN c_period_id numeric, IN issotrx character varying) RETURNS SETOF report.fresh_umsatzreport_report AS
$BODY$
	SELECT *, 1 AS UnionOrder FROM report.fresh_Umsatzreport_Report_Sub ($1, $2)
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
		2 AS UnionOrder
	FROM 
		report.fresh_Umsatzreport_Report_Sub ($1, $2)
	GROUP BY
		PeriodEnd,
		LastYearPeriodEnd,
		Year,
		LastYear
ORDER BY
	UnionOrder, SameYearSum DESC
$BODY$
LANGUAGE sql STABLE;
ALTER FUNCTION report.fresh_umsatzreport_report (IN numeric, IN character varying) OWNER TO adempiere;

