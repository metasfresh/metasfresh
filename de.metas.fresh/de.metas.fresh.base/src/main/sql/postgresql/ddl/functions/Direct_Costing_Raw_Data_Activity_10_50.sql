
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Activity_10_50 (Year Date, AD_Org_ID numeric(10,0));
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Activity_10_50 (Year Date, AD_Org_ID numeric(10,0)) RETURNS TABLE
	(
	Margin text, 
	l1_Value Character Varying, 
	L1_Name Character Varying, 
	
	L2_Value Character Varying, 
	L2_Name Character Varying, 
	
	L3_Value Character Varying, 
	L3_Name Character Varying, 
	
	Balance_10 numeric, 
	Balance_20 numeric, 
	Balance_30 numeric, 
	Balance_40 numeric, 
	Balance_50 numeric, 
	Balance_90 numeric, 
	Balance_Other numeric, 
	Balance numeric, 
	
	Budget_10 numeric, 
	Budget_20 numeric, 
	Budget_30 numeric, 
	Budget_40 numeric, 
	Budget_50 numeric, 
	Budget_90 numeric, 
	Budget numeric, 
	
	L1_Multiplicator numeric, 
	L2_Multiplicator numeric, 
	L3_Multiplicator numeric, 
	Seq text,
	
	ad_org_id numeric
	)
AS 
$BODY$
SELECT
	margin, 
	l1_value, l1_name, l2_value, l2_name, l3_value, l3_name,
	Balance_10 * Multi_150 AS Balance_10,
	Balance_20 * Multi_150 AS Balance_20,
	Balance_30 * Multi_150 AS Balance_30,
	Balance_40 * Multi_150 AS Balance_40,
	Balance_50 * Multi_150 AS Balance_50,
	Balance_90 * Multi_150 AS Balance_90,
	Balance_Other* Multi_150  AS Balance_Other,
	Balance_10 * Multi_150 + Balance_20 * Multi_150 + Balance_30 * Multi_150 + Balance_40 * Multi_150 + Balance_50 * Multi_150 + Balance_90 * Multi_150 + Balance_Other * Multi_150 AS Balance,
	Budget_10 * Multi_150 AS Budget_10,
	Budget_20 * Multi_150 AS Budget_20,
	Budget_30 * Multi_150 AS Budget_30,
	Budget_40 * Multi_150 AS Budget_40,	
	Budget_50 * Multi_150 AS Budget_50,
	Budget_90 * Multi_150 AS Budget_90,
	Budget_10 * Multi_150 + Budget_20 * Multi_150 + Budget_30 * Multi_150 + Budget_40 * Multi_150 + Budget_50 * Multi_150 + Budget_90 * Multi_150 + Budget_Other * Multi_150  AS Budget,
	acctBalance(l1_ElementValue_ID, 0, 1) AS l1_Multiplicator,
	acctBalance(l2_ElementValue_ID, 0, 1) AS l2_Multiplicator,
	acctBalance(l3_ElementValue_ID, 0, 1) AS l3_Multiplicator,
	SeqNo AS Seq,
	fa.ad_org_id
FROM
	de_metas_endcustomer_fresh_reports.Direct_Costing_selection s
	LEFT OUTER JOIN (
		SELECT
			Account_ID,
			SUM( CASE WHEN a.Value = '10' THEN Balance ELSE 0 END ) AS Balance_10,
			SUM( CASE WHEN a.Value = '20' THEN Balance ELSE 0 END ) AS Balance_20,
			SUM( CASE WHEN a.Value = '30' THEN Balance ELSE 0 END ) AS Balance_30,
			SUM( CASE WHEN a.Value = '40' THEN Balance ELSE 0 END ) AS Balance_40,
			SUM( CASE WHEN a.Value = '50' THEN Balance ELSE 0 END ) AS Balance_50,
			SUM( CASE WHEN a.Value = '90' THEN Balance ELSE 0 END ) AS Balance_90,
			SUM( CASE WHEN a.Value IS NULL OR (a.Value != '10' AND a.Value != '20' AND a.Value != '30' AND a.Value != '40' AND a.Value != '50' AND a.Value != '90') 
				THEN Balance ELSE 0 END ) AS Balance_Other,
			
			SUM( CASE WHEN a.Value = '10' THEN Budget ELSE 0 END ) AS Budget_10,
			SUM( CASE WHEN a.Value = '20' THEN Budget ELSE 0 END ) AS Budget_20,
			SUM( CASE WHEN a.Value = '30' THEN Budget ELSE 0 END ) AS Budget_30,
			SUM( CASE WHEN a.Value = '40' THEN Budget ELSE 0 END ) AS Budget_40,
			SUM( CASE WHEN a.Value = '50' THEN Budget ELSE 0 END ) AS Budget_50,
			SUM( CASE WHEN a.Value = '90' THEN Budget ELSE 0 END ) AS Budget_90,
			SUM( CASE WHEN a.Value IS NULL OR (a.Value != '10' AND a.Value != '20' AND a.Value != '30' AND a.Value != '40' AND a.Value != '50' AND a.Value != '90') 
				THEN Budget ELSE 0 END ) AS Budget_Other,
			fa.ad_org_id
		FROM
			(
				SELECT fa.Account_ID
					, COALESCE(ap.C_Activity_ID, a.C_Activity_ID, aev.C_Activity_ID) as C_Activity_ID
					, CASE WHEN postingtype in ('A','Y') THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Balance
					, CASE WHEN postingtype = 'B' THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Budget,
					fa.ad_org_id
				FROM Fact_Acct fa
				left outer join C_Activity a on (a.C_Activity_ID=fa.C_Activity_ID) AND a.isActive = 'Y'
				left outer join C_Activity ap on (ap.C_Activity_ID=a.Parent_Activity_ID) AND ap.isActive = 'Y'
				-- another left join with c_element on fact_acct account_id 
				-- in case of c_activity in fact_acct is null, to take the default one from c_elementvalue (FRESH-845)
				left outer join C_ElementValue ev ON fa.Account_ID = ev.C_ElementValue_ID AND ev.isActive = 'Y'
				left outer join C_Activity aev ON ev.C_Activity_ID = aev.C_Activity_ID AND aev.isActive = 'Y'
				WHERE
				CASE WHEN postingtype = 'B' THEN
					dateacct::date <= (select enddate from c_period where c_period_id=report.Get_Period( 1000000, $1 ) AND isActive = 'Y')
				ELSE 
					(dateacct::date <= $1) END
					AND dateacct::Date >= (
						SELECT MIN( StartDate )::Date FROM C_Period 
						WHERE C_Year_ID = (SELECT C_Year_ID FROM C_Period WHERE C_Period_ID = report.Get_Period( 1000000, $1 ) AND isActive = 'Y') AND isActive = 'Y'
					)
				AND fa.isActive = 'Y'	

			) fa
			LEFT OUTER JOIN C_Activity a ON fa.C_Activity_ID = a.C_Activity_ID AND a.isActive = 'Y'
		GROUP BY Account_ID, fa.ad_org_id
	) fa ON fa.Account_ID = s.L3_ElementValue_ID 
WHERE fa.ad_org_id = $2
ORDER BY
	SeqNo
$BODY$
LANGUAGE sql STABLE
;


