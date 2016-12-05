
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings_Activity_10_50 (Year Date, AD_Org_ID numeric(10,0) );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings_Activity_10_50 (Year Date, AD_Org_ID numeric(10,0) ) RETURNS TABLE
	(
	l_Value Character Varying, L_Name Character Varying,
	Balance_10 numeric, Balance_20 numeric, Balance_30 numeric, Balance_40 numeric, Balance_50 numeric, Balance_Other numeric, Balance numeric, 
	Budget_10 numeric, Budget_20 numeric, Budget_30 numeric, Budget_40 numeric, Budget_50 numeric, Budget numeric, 
	L_Multiplicator numeric,
	ad_org_id numeric (10,0)
	)
AS 
$BODY$
SELECT
	
	l_value, l_name,
	Balance_10 AS Balance_10,
	Balance_20 AS Balance_20,
	Balance_30 AS Balance_30,
	Balance_40 AS Balance_40,
	Balance_50 AS Balance_50,
	Balance_Other AS Balance_Other,
	Balance_10 + Balance_20 + Balance_30 + Balance_40 + Balance_50 + Balance_Other AS Balance,
	Budget_10 AS Budget_10,
	Budget_20 AS Budget_20,
	Budget_30 AS Budget_30,
	Budget_40 AS Budget_40,
	Budget_50 AS Budget_50,
	Budget_10 + Budget_20 + Budget_30 + + Budget_40 + Budget_50 + Budget_Other AS Budget,
	acctBalance(l_ElementValue_ID, 0, 1) AS l_Multiplicator,
	fa.ad_org_id

FROM
	de_metas_endcustomer_fresh_reports.Direct_Costing_selection_Remainings s
	LEFT OUTER JOIN (
		SELECT
			Account_ID,
			SUM( CASE WHEN a.Value = '10' THEN Balance ELSE 0 END ) AS Balance_10,
			SUM( CASE WHEN a.Value = '20' THEN Balance ELSE 0 END ) AS Balance_20,
			SUM( CASE WHEN a.Value = '30' THEN Balance ELSE 0 END ) AS Balance_30,
			SUM( CASE WHEN a.Value = '40' THEN Balance ELSE 0 END ) AS Balance_40,
			SUM( CASE WHEN a.Value = '50' THEN Balance ELSE 0 END ) AS Balance_50,
			SUM( CASE WHEN a.Value IS NULL OR (a.Value != '10' AND a.Value != '20' AND a.Value != '30' AND a.Value != '40' AND a.Value != '50') 
				THEN Balance ELSE 0 END ) AS Balance_Other,
			
			SUM( CASE WHEN a.Value = '10' THEN Budget ELSE 0 END ) AS Budget_10,
			SUM( CASE WHEN a.Value = '20' THEN Budget ELSE 0 END ) AS Budget_20,
			SUM( CASE WHEN a.Value = '30' THEN Budget ELSE 0 END ) AS Budget_30,
			SUM( CASE WHEN a.Value = '40' THEN Budget ELSE 0 END ) AS Budget_40,
			SUM( CASE WHEN a.Value = '50' THEN Budget ELSE 0 END ) AS Budget_50,
			SUM( CASE WHEN a.Value IS NULL OR (a.Value != '10' AND a.Value != '20' AND a.Value != '30' AND a.Value != '40' AND a.Value != '50') 
				THEN Budget ELSE 0 END ) AS Budget_Other,

			fa.ad_org_id
		FROM
			(
				SELECT fa.Account_ID
					, COALESCE(ap.C_Activity_ID, a.C_Activity_ID, aev.C_Activity_ID) as C_Activity_ID
					, CASE WHEN postingtype IN ('A','Y') THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Balance
					, CASE WHEN postingtype = 'B' THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Budget
					, fa.ad_org_id
				FROM Fact_Acct fa
				left outer join C_Activity a on (a.C_Activity_ID=fa.C_Activity_ID)
				left outer join C_Activity ap on (ap.C_Activity_ID=a.Parent_Activity_ID)
				-- another left join with c_element on fact_acct account_id 
				-- in case of c_activity in fact_acct is null, to take the default one from c_elementvalue (FRESH-845)
				left outer join C_ElementValue ev ON fa.Account_ID = ev.C_ElementValue_ID
				left outer join C_Activity aev ON ev.C_Activity_ID = aev.C_Activity_ID
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

