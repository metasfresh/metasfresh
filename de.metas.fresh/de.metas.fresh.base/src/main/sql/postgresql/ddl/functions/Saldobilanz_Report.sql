

DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN Date, IN defaultAcc character varying);

DROP TABLE IF EXISTS report.saldobilanz_Report;

CREATE TABLE report.saldobilanz_Report
(
	parentname1 character varying(60),
	parentvalue1 character varying(60),
	parentname2 character varying(60),
	parentvalue2 character varying(60),
	parentname3 character varying(60),
	parentvalue3 character varying(60),
	name character varying(60),
	namevalue character varying(60),
	
	sameyearsum numeric,
	lastyearsum numeric,
	L3_sameyearsum numeric,
	L3_lastyearsum numeric,
	L2_sameyearsum numeric,
	L2_lastyearsum numeric,
	L1_sameyearsum numeric,
	L1_lastyearsum numeric
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.saldobilanz_Report(IN Date Date, IN defaultAcc character varying) RETURNS SETOF report.saldobilanz_Report AS
$BODY$
SELECT
	parentname1,
	parentvalue1,
	parentname2,
	parentvalue2,
	parentname3,
	parentvalue3,
	name,
	value,
	
	SameYearSum AS L4_SameYearSum,
	LastYearSum AS L4_LastYearSum,
	SUM(CASE WHEN parentvalue3 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY parentvalue3 ) AS L3_SameYearSum,
	SUM(CASE WHEN parentvalue3 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY parentvalue3 ) AS L3_LastYearSum,
	SUM(CASE WHEN parentvalue2 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY parentvalue2 ) AS L2_SameYearSum,
	SUM(CASE WHEN parentvalue2 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY parentvalue2 ) AS L2_LastYearSum,
	SUM(CASE WHEN parentvalue1 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY parentvalue1 ) AS L1_SameYearSum,
	SUM(CASE WHEN parentvalue1 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY parentvalue1 ) AS L1_LastYearSum
	--EndDate::Date

FROM
	(
		SELECT
			lvl.lvl1_name as parentName1,
			lvl.lvl1_value as parentValue1,
			lvl.lvl2_name as parentName2,
			lvl.lvl2_value as parentValue2,
			lvl.lvl3_name as parentName3,
			lvl.lvl3_value as parentValue3,
			lvl.lvl4_name as name,
			lvl.lvl4_value as value,
			
			SUM( 
				(CASE 
					WHEN fap.C_Year_ID = p.C_Year_ID THEN AmtAcct 
					WHEN fap.C_Year_ID = pp.C_Year_ID THEN AmtAcct
					ELSE 0 
				END) * Multiplicator
			) AS SameYearSum,
			
			-- this is no longer valid. Leaving it here for traceability 
			--SUM( CASE WHEN fap.C_Year_ID = pp.C_Year_ID AND fa.DateAcct <= $1  - Interval '1 year' THEN AmtAcct ELSE 0 END ) AS LastYearSum
			
			SUM( CASE WHEN fap.C_Year_ID = pp.C_Year_ID THEN AmtAcct * Multiplicator ELSE 0 END ) AS LastYearSum -- taking the whole previous year
		FROM
			C_Period p 
			-- Get same Period from previous year
			LEFT OUTER JOIN C_Period pp ON pp.C_Period_ID = report.Get_Predecessor_Period_Recursive ( p.C_Period_ID,
				( SELECT count(0) FROM C_Period sp WHERE sp.C_Year_ID = p.C_Year_ID and isActive = 'Y' )::int )
			INNER JOIN C_Element_Levels lvl ON true
			INNER JOIN ( SELECT C_ElementValue_ID, acctBalance(C_ElementValue_ID, 0, 1) AS Multiplicator FROM C_ElementValue ) ev 
				ON lvl.lvl4_c_elementValue_ID = ev.C_ElementValue_ID
			-- Get data from fact account
			LEFT OUTER JOIN (
				SELECT 	
					fa.C_Period_ID, fa.account_ID, 
					SUM( AmtAcctCr - AmtAcctDr ) AS AmtAcct
				FROM 	
					Fact_Acct fa 
					INNER JOIN C_ElementValue ev on fa.account_id = ev.C_ElementValue_ID
				WHERE
					DateAcct::date <= $1
				GROUP BY 
					fa.C_Period_ID, fa.account_ID
			) fa ON fa.account_ID = lvl.lvl4_c_elementValue_ID
			LEFT OUTER JOIN C_Period fap ON fa.C_Period_ID = fap.C_Period_ID
		WHERE
			p.C_Period_ID = report.Get_Period( 1000000, $1 ) 
			AND CASE WHEN $2='Y' THEN true
				ELSE lvl1_value != 'ZZ'
			END
		GROUP BY
			lvl.lvl1_name,
			lvl.lvl1_value,
			lvl.lvl2_name,
			lvl.lvl2_value,
			lvl.lvl3_name,
			lvl.lvl3_value,
			lvl.lvl4_name,
			lvl.lvl4_value
	) a
ORDER BY
	parentValue1, parentValue2, parentValue3, value 
$BODY$
LANGUAGE sql STABLE;
ALTER FUNCTION report.saldobilanz_Report(IN Date, IN character varying) OWNER TO adempiere;

