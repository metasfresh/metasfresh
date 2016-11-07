
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings (Year Date) ;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings (Year Date, AD_Org_ID numeric(10,0) );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings (Year Date, AD_Org_ID numeric(10,0) ) RETURNS TABLE
	(
	l_Value Character Varying, L_Name Character Varying,
	Balance_1000 numeric, Balance_2000 numeric, Balance_100 numeric, Balance_150 numeric, Balance_Other numeric, Balance numeric, 
	Budget_1000 numeric, Budget_2000 numeric, Budget_100 numeric, Budget_150 numeric, Budget numeric, 
	L_Multiplicator numeric,
	ad_org_id numeric (10,0)
	)
AS 
$BODY$
SELECT
	
	l_value, l_name,
	Balance_1000 * Multi_1000 AS Balance_1000,
	Balance_2000 * Multi_2000 AS Balance_2000,
	Balance_100 * Multi_100 AS Balance_100,
	Balance_150 * Multi_150 AS Balance_150,
	Balance_Other * Multi_150 AS Balance_Other,
	Balance_1000 * Multi_1000 + Balance_2000 * Multi_2000 + Balance_100 * Multi_100 + Balance_150 * Multi_150 + Balance_Other * Multi_150 AS Balance,
	Budget_1000 * Multi_1000 AS Budget_1000,
	Budget_2000 * Multi_2000 AS Budget_2000,
	Budget_100 * Multi_100 AS Budget_100,
	Budget_150 * Multi_150 AS Budget_150,
	Budget_1000 * Multi_1000 + Budget_2000 * Multi_2000 + Budget_100 * Multi_100 + Budget_150 * Multi_150 + Budget_Other * Multi_150 AS Budget,
	acctBalance(l_ElementValue_ID, 0, 1) AS l_Multiplicator,
	fa.ad_org_id

FROM
	de_metas_endcustomer_fresh_reports.Direct_Costing_selection_Remainings s
	LEFT OUTER JOIN (
		SELECT
			Account_ID,
			SUM( CASE WHEN a.Value = '1000' THEN Balance ELSE 0 END ) AS Balance_1000,
			SUM( CASE WHEN a.Value = '2000' THEN Balance ELSE 0 END ) AS Balance_2000,
			SUM( CASE WHEN a.Value = '100' THEN Balance ELSE 0 END ) AS Balance_100,
			SUM( CASE WHEN a.Value = '150' THEN Balance ELSE 0 END ) AS Balance_150,
			SUM( CASE WHEN a.Value IS NULL OR (a.Value != '1000' AND a.Value != '2000' AND a.Value != '100' AND a.Value != '150') 
				THEN Balance ELSE 0 END ) AS Balance_Other,
			
			SUM( CASE WHEN a.Value = '1000' THEN Budget ELSE 0 END ) AS Budget_1000,
			SUM( CASE WHEN a.Value = '2000' THEN Budget ELSE 0 END ) AS Budget_2000,
			SUM( CASE WHEN a.Value = '100' THEN Budget ELSE 0 END ) AS Budget_100,
			SUM( CASE WHEN a.Value = '150' THEN Budget  ELSE 0 END ) AS Budget_150,
			SUM( CASE WHEN a.Value IS NULL OR (a.Value != '1000' AND a.Value != '2000' AND a.Value != '100' AND a.Value != '150') 
				THEN Budget ELSE 0 END ) AS Budget_Other,

			fa.ad_org_id
		FROM
			(
				SELECT fa.Account_ID
					, COALESCE(ap.C_Activity_ID, a.C_Activity_ID) as C_Activity_ID
					, CASE WHEN postingtype IN ('A','Y') THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Balance
					, CASE WHEN postingtype = 'B' THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Budget
					, fa.ad_org_id
				FROM Fact_Acct fa
				left outer join C_Activity a on (a.C_Activity_ID=fa.C_Activity_ID)
				left outer join C_Activity ap on (ap.C_Activity_ID=a.Parent_Activity_ID)
				WHERE	CASE WHEN postingtype = 'B' THEN
					dateacct::date <= (select enddate from c_period where c_period_id=report.Get_Period( 1000000, $1 ))
				ELSE 
					(dateacct::date <= $1) END
					AND dateacct::Date >= (
						SELECT MIN( StartDate )::Date FROM C_Period 
						WHERE C_Year_ID = (SELECT C_Year_ID FROM C_Period WHERE C_Period_ID = report.Get_Period( 1000000, $1 ))
					)
				AND fa.ad_org_id = $2
			) fa
			LEFT OUTER JOIN C_Activity a ON fa.C_Activity_ID = a.C_Activity_ID 
		GROUP BY Account_ID, fa.ad_org_id
	) fa ON fa.Account_ID = s.L_ElementValue_ID
WHERE fa.ad_org_id = $2
$BODY$
LANGUAGE sql STABLE
;

