
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings (Year Date) ;
DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_selection_Remainings;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.Direct_Costing_selection_Remainings AS
SELECT
	ev.C_ElementValue_ID AS L_ElementValue_ID, ev.value AS L_value, ev.name AS L_Name, 
	1 as Multi_1000, 
	1 as Multi_2000, 
	1 as Multi_100, 
	1 as Multi_150
FROM
	C_ElementValue ev
where
ev.value not in (select value from report.Margin_Conf_Acct)
and 
char_length(ev.value)<=4
and 
ev.accounttype in ('R', 'E')
and ev.isSummary='N'
;




CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings (Year Date) RETURNS TABLE
	(
	l_Value Character Varying, L_Name Character Varying,
	Balance_1000 numeric, Balance_2000 numeric, Balance_100 numeric, Balance_150 numeric, Balance_Other numeric, Balance numeric, 
	Budget_1000 numeric, Budget_2000 numeric, Budget_100 numeric, Budget_150 numeric, Budget numeric, 
	L_Multiplicator numeric
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
	Balance_1000 * Multi_1000 + Balance_2000 * Multi_2000 + Balance_100 * Multi_100 + Balance_150 * Multi_150 AS Balance,
	Budget_1000 * Multi_1000 AS Budget_1000,
	Budget_2000 * Multi_2000 AS Budget_2000,
	Budget_100 * Multi_100 AS Budget_100,
	Budget_150 * Multi_150 AS Budget_150,
	Budget_1000 * Multi_1000 + Budget_2000 * Multi_2000 + Budget_100 * Multi_100 + Budget_150 * Multi_150 AS Budget,
	acctBalance(l_ElementValue_ID, 0, 1) AS l_Multiplicator

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
			SUM( CASE WHEN a.Value = '150' THEN Budget  ELSE 0 END ) AS Budget_150
		FROM
			(
				SELECT Account_ID, C_Activity_ID, 
					CASE WHEN postingtype = 'A' THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Balance,
					CASE WHEN postingtype = 'B' THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Budget
				FROM 	Fact_Acct 
				WHERE	dateacct::date <= $1
					AND dateacct::Date >= (
						SELECT MIN( StartDate )::Date FROM C_Period 
						WHERE C_Year_ID = (SELECT C_Year_ID FROM C_Period WHERE C_Period_ID = report.Get_Period( 1000000, $1 ))
					)
			) fa
			LEFT OUTER JOIN C_Activity a ON fa.C_Activity_ID = a.C_Activity_ID 
		GROUP BY Account_ID
	) fa ON fa.Account_ID = s.L_ElementValue_ID
$BODY$
LANGUAGE sql STABLE
;

