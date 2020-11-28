DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_selection;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.Direct_Costing_selection AS
SELECT
	to_char(mc.SeqNo, '0000') || to_char(l3.lvl1_seqno, '0000') || to_char(l3.lvl2_seqno, '0000') || to_char(l3.lvl3_seqno, '0000') AS SeqNo,
	mc.name AS margin,
	l1.C_ElementValue_ID AS L1_ElementValue_ID, l1.value AS L1_value, l1.name AS L1_Name, 
	l2.C_ElementValue_ID AS L2_ElementValue_ID, l2.value AS L2_value, l2.name AS L2_Name, 
	l3.C_ElementValue_ID AS L3_ElementValue_ID, l3.value AS L3_value, l3.name AS L3_Name, 
	CASE WHEN l3.Use_Activity_1000 THEN 1 ELSE 0 END as Multi_1000, 
	CASE WHEN l3.Use_Activity_2000 THEN 1 ELSE 0 END as Multi_2000, 
	CASE WHEN l3.Use_Activity_100 THEN 1 ELSE 0 END as Multi_100, 
	CASE WHEN l3.Use_Activity_150 THEN 1 ELSE 0 END as Multi_150
FROM
	report.Margin_Conf_Acct l3
	JOIN report.Margin_Conf_Acct l2 ON l3.Parent_Account_ID = l2.Margin_Conf_Acct_ID AND l2.level = 2
	JOIN report.Margin_Conf_Acct l1 ON l2.Parent_Account_ID = l1.Margin_Conf_Acct_ID AND l1.level = 1
	JOIN report.Margin_Conf mc on l3.Margin_Conf_ID = mc.Margin_Conf_ID
WHERE
	l3.level = 3
;











DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data (Year Date) ;
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data (Year Date) RETURNS TABLE
	(
	Margin text, 
	l1_Value Character Varying, L1_Name Character Varying, L2_Value Character Varying, L2_Name Character Varying, L3_Value Character Varying, L3_Name Character Varying, 
	Balance_1000 numeric, Balance_2000 numeric, Balance_100 numeric, Balance_150 numeric, Balance_Other numeric, Balance numeric, 
	Budget_1000 numeric, Budget_2000 numeric, Budget_100 numeric, Budget_150 numeric, Budget numeric, 
	L1_Multiplicator numeric, L2_Multiplicator numeric, L3_Multiplicator numeric, Seq text
	)
AS 
$BODY$
SELECT
	margin, 
	l1_value, l1_name, l2_value, l2_name, l3_value, l3_name,
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
	acctBalance(l3_ElementValue_ID, 0, 1) AS l3_Multiplicator,
	acctBalance(l2_ElementValue_ID, 0, 1) AS l2_Multiplicator,
	acctBalance(l1_ElementValue_ID, 0, 1) AS l1_Multiplicator,
	SeqNo AS Seq
FROM
	de_metas_endcustomer_fresh_reports.Direct_Costing_selection s
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
	) fa ON fa.Account_ID = s.L3_ElementValue_ID
ORDER BY
	SeqNo
$BODY$
LANGUAGE sql STABLE
;

