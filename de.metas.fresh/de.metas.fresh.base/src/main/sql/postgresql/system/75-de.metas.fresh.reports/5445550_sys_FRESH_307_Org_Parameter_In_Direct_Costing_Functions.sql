

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data (Year Date);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data (Year Date, AD_Org_ID numeric(10,0));
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data (Year Date, AD_Org_ID numeric(10,0)) RETURNS TABLE
	(
	Margin text, 
	l1_Value Character Varying, 
	L1_Name Character Varying, 
	
	L2_Value Character Varying, 
	L2_Name Character Varying, 
	
	L3_Value Character Varying, 
	L3_Name Character Varying, 
	
	Balance_1000 numeric, 
	Balance_2000 numeric, 
	Balance_100 numeric, 
	Balance_150 numeric, 
	Balance_Other numeric, 
	Balance numeric, 
	
	Budget_1000 numeric, 
	Budget_2000 numeric, 
	Budget_100 numeric, 
	Budget_150 numeric, 
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
					, CASE WHEN postingtype = 'A' THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Balance
					, CASE WHEN postingtype = 'B' THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Budget,
					fa.ad_org_id
				FROM Fact_Acct fa
				left outer join C_Activity a on (a.C_Activity_ID=fa.C_Activity_ID)
				left outer join C_Activity ap on (ap.C_Activity_ID=a.Parent_Activity_ID)
				WHERE
				CASE WHEN postingtype = 'B' THEN
					dateacct::date <= (select enddate from c_period where c_period_id=report.Get_Period( 1000000, $1 ))
				ELSE 
					(dateacct::date <= $1) END
					AND dateacct::Date >= (
						SELECT MIN( StartDate )::Date FROM C_Period 
						WHERE C_Year_ID = (SELECT C_Year_ID FROM C_Period WHERE C_Period_ID = report.Get_Period( 1000000, $1 ))
					)
			) fa
			LEFT OUTER JOIN C_Activity a ON fa.C_Activity_ID = a.C_Activity_ID 
		GROUP BY Account_ID, fa.ad_org_id
	) fa ON fa.Account_ID = s.L3_ElementValue_ID 
WHERE fa.ad_org_id = $2
ORDER BY
	SeqNo
$BODY$
LANGUAGE sql STABLE
;

DROP FUNCTION IF EXISTS report.fresh_Direct_Costing (IN Date date, IN showBudget character varying, IN showRemaining character varying) ;


CREATE OR REPLACE FUNCTION report.fresh_Direct_Costing (IN Date date, 
IN showBudget character varying,  -- only used in report
IN showRemaining character varying,
IN ad_org_id numeric(10,0)) -- only used in report
	RETURNS TABLE 
	(
		seq text,
		margin text,
		margin_incr_1000 numeric,
		margin_incr_2000 numeric,
		margin_incr_100 numeric,
		margin_incr_150 numeric,
		margin_incr_other numeric,
		margin_incr_all numeric,
		
		l1_value character varying,
		l1_name character varying,
		isdisplayl1sum boolean,
		l1_1000 numeric,
		l1_2000 numeric,
		l1_100 numeric,
		l1_150 numeric,
		l1_other numeric,
		l1_all numeric,
		l1_multiplicator numeric,
		
		l2_value character varying,
		l2_name character varying,
		l2_1000 numeric,
		l2_2000 numeric,
		l2_100 numeric,
		l2_150 numeric,
		l2_other numeric,
		l2_all numeric,
		l2_multiplicator numeric,
		
		l3_value character varying,
		l3_name character varying,
		
		balance_1000 numeric,
		balance_2000 numeric,
		balance_100 numeric,
		balance_150 numeric,
		balance_other numeric,
		balance numeric,
		
		l3_multiplicator numeric,
		
		margin_budget_incr_1000 numeric,
		margin_budget_incr_2000 numeric,
		margin_budget_incr_100 numeric,
		margin_budget_incr_150 numeric,
		margin_budget_incr_all numeric,
		
		l1_budget_1000 numeric,
		l1_budget_2000 numeric,
		l1_budget_100 numeric,
		l1_budget_150 numeric,
		l1_budget_all numeric,
		
		l2_budget_1000 numeric,
		l2_budget_2000 numeric,
		l2_budget_100 numeric,
		l2_budget_150 numeric,
		l2_budget_all numeric,
		
		budget_1000 numeric,
		budget_2000 numeric,
		budget_100 numeric,
		budget_150 numeric,
		budget numeric,
		
		ad_org_id numeric,
		
		gross_1000 numeric,
		gross_2000 numeric,
		gross_100 numeric,
		gross_150 numeric,
		gross_all numeric,
		
		startdate date,
		enddate date,
		
		margin_percentage_1000 numeric,
		margin_percentage_2000 numeric,
		margin_percentage_100 numeric,
		margin_percentage_150 numeric,
		margin_percentage_all numeric,
		
		margin_budget_percentage_1000 numeric,
		margin_budget_percentage_2000 numeric,
		margin_budget_percentage_100 numeric,
		margin_budget_percentage_150 numeric,
		margin_budget_percentage_all numeric,
		
		l1_percentage_1000 numeric,
		l1_percentage_2000 numeric,
		l1_percentage_100 numeric,
		l1_percentage_150 numeric,
		l1_percentage_all numeric,
		
		l1_budget_percentage_1000 numeric,
		l1_budget_percentage_2000 numeric,
		l1_budget_percentage_100 numeric,
		l1_budget_percentage_150 numeric,
		l1_budget_percentage_all numeric,
		
		l2_percentage_1000 numeric,
		l2_percentage_2000 numeric,
		l2_percentage_100 numeric,
		l2_percentage_150 numeric,
		l2_percentage_all numeric,
		
		l2_budget_percentage_1000 numeric,
		l2_budget_percentage_2000 numeric,
		l2_budget_percentage_100 numeric,
		l2_budget_percentage_150 numeric,
		l2_budget_percentage_all numeric,
		
		l3_percentage_1000 numeric,
		l3_percentage_2000 numeric,
		l3_percentage_100 numeric,
		l3_percentage_150 numeric,
		l3_percentage_all numeric,
		
		l3_budget_percentage_1000 numeric,
		l3_budget_percentage_2000 numeric,
		l3_budget_percentage_100 numeric,
		l3_budget_percentage_150 numeric,
		l3_budget_percentage_all numeric,
		
		isdisplaymarginsum boolean,
		isdisplayother boolean

		
		
	)
AS 
$$
SELECT	



	*,
	ROUND( Margin_Incr_1000 / Gross_1000 * 100, 2 ) AS Margin_Percentage_1000,
	ROUND( Margin_Incr_2000 / Gross_2000 * 100, 2 ) AS Margin_Percentage_2000,
	ROUND( Margin_Incr_100 / Gross_100 * 100, 2 ) AS Margin_Percentage_100,
	ROUND( Margin_Incr_150 / Gross_150 * 100, 2 ) AS Margin_Percentage_150,
	ROUND( Margin_Incr_all / Gross_all * 100, 2 ) AS Margin_Percentage_all,
	ROUND( Margin_Incr_1000 / NULLIF( Margin_Budget_Incr_1000, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_1000,
	ROUND( Margin_Incr_2000 / NULLIF( Margin_Budget_Incr_2000, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_2000,
	ROUND( Margin_Incr_100 / NULLIF( Margin_Budget_Incr_100, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_100,
	ROUND( Margin_Incr_150 / NULLIF( Margin_Budget_Incr_150, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_150,
	ROUND( Margin_Incr_all / NULLIF( Margin_Budget_Incr_All, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_all,

	ABS( ROUND( L1_1000 / Gross_1000 * 100, 2 ) ) AS L1_Percentage_1000,
	ABS( ROUND( L1_2000 / Gross_2000 * 100, 2 ) ) AS L1_Percentage_2000,
	ABS( ROUND( L1_100 / Gross_100 * 100, 2 ) ) AS L1_Percentage_100,
	ABS( ROUND( L1_150 / Gross_150 * 100, 2 ) ) AS L1_Percentage_150,
	ABS( ROUND( L1_All / Gross_All * 100, 2 ) ) AS L1_Percentage_All,
	ABS( ROUND( L1_1000 / NULLIF( L1_Budget_1000, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_1000,
	ABS( ROUND( L1_2000 / NULLIF( L1_Budget_2000, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_2000,
	ABS( ROUND( L1_100 / NULLIF( L1_Budget_100, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_100,
	ABS( ROUND( L1_150 / NULLIF( L1_Budget_150, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_150,
	ABS( ROUND( L1_All / NULLIF( L1_Budget_All, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_All,

	ABS( ROUND( L2_1000 / Gross_1000 * 100, 2 ) ) AS L2_Percentage_1000,
	ABS( ROUND( L2_2000 / Gross_2000 * 100, 2 ) ) AS L2_Percentage_2000,
	ABS( ROUND( L2_100 / Gross_100 * 100, 2 ) ) AS L2_Percentage_100,
	ABS( ROUND( L2_150 / Gross_150 * 100, 2 ) ) AS L2_Percentage_150,
	ABS( ROUND( L2_All / Gross_All * 100, 2 ) ) AS L2_Percentage_All,
	ABS( ROUND( L2_1000 / NULLIF( L2_Budget_1000, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_1000,
	ABS( ROUND( L2_2000 / NULLIF( L2_Budget_2000, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_2000,
	ABS( ROUND( L2_100 / NULLIF( L2_Budget_100, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_100,
	ABS( ROUND( L2_150 / NULLIF( L2_Budget_150, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_150,
	ABS( ROUND( L2_All / NULLIF( L2_Budget_All, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_All,

	ABS( ROUND( Balance_1000 / Gross_1000 * 100, 2 ) ) AS L3_Percentage_1000,
	ABS( ROUND( Balance_2000 / Gross_2000 * 100, 2 ) ) AS L3_Percentage_2000,
	ABS( ROUND( Balance_100 / Gross_100 * 100, 2 ) ) AS L3_Percentage_100,
	ABS( ROUND( Balance_150 / Gross_150 * 100, 2 ) ) AS L3_Percentage_150,
	ABS( ROUND( Balance / Gross_all * 100, 2 ) ) AS L3_percentage_All,
	ABS( ROUND( Balance_1000 / NULLIF( Budget_1000, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_1000,
	ABS( ROUND( Balance_2000 / NULLIF( Budget_2000, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_2000,
	ABS( ROUND( Balance_100 / NULLIF( Budget_100, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_100,
	ABS( ROUND( Balance_150 / NULLIF( Budget_150, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_150,
	ABS( ROUND( Balance / NULLIF( Budget, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_All,

	-- If current Margin is the Last Margin, display the final sum group footer
	First_Agg( Margin ) OVER (ORDER BY Seq Desc ) != Margin AS isDisplayMarginSum,

	-- If there are no Sums in the 'Other'-Column, don't display it
	SUM( Balance_Other ) OVER () != 0 AS isDisplayOther
FROM
	(
	SELECT
		*,
		-- Get initial profit to create the percentage of each line and/or sum
		NULLIF( First_agg ( L2_1000 ) OVER (ORDER BY Seq ), 0 ) AS Gross_1000,
		NULLIF( First_agg ( L2_2000 ) OVER (ORDER BY Seq ), 0 ) AS Gross_2000,
		NULLIF( First_agg ( L2_100 ) OVER (ORDER BY Seq ), 0 ) AS Gross_100,
		NULLIF( First_agg ( L2_150 ) OVER (ORDER BY Seq ), 0 ) AS Gross_150,
		NULLIF( First_agg ( L2_all ) OVER (ORDER BY Seq ), 0 ) AS Gross_All
	FROM
		(
		SELECT
			seq,
			-- Margin
			margin,
			--
			-- Balances

			-- Create an Incremental sum for the Margins. This sum will be Displayed in the report
			-- It is a requirement of the report that the sums are incremental over the margins
			-- Note: The window function Last_Value does not get the Last Value, but the maximum
			First_Agg( Incr_1000 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_1000,
			First_Agg( Incr_2000 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_2000,
			First_Agg( Incr_100 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_100,
			First_Agg( Incr_150 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_150,
			First_Agg( Incr_other ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_other,
			First_Agg( Incr ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_All,
			-- Level 1
			L1_Value, L1_Name, isDisplayL1Sum,
			Sum ( Balance_1000 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_1000,
			Sum ( Balance_2000 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_2000,
			Sum ( Balance_100 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_100,
			Sum ( Balance_150 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_150,
			Sum ( Balance_other ) OVER ( PARTITION BY Margin, L1_Value) AS L1_other,
			Sum ( Balance ) OVER ( PARTITION BY Margin, L1_Value) AS L1_All,
			L1_Multiplicator,
			-- Level 2
			L2_Value, L2_Name,
			Sum ( Balance_1000 ) OVER ( PARTITION BY Margin, L2_Value) AS L2_1000,
			Sum ( Balance_2000 ) OVER ( PARTITION BY Margin, L2_Value) AS L2_2000,
			Sum ( Balance_100 ) OVER ( PARTITION BY Margin, L2_Value) AS L2_100,
			Sum ( Balance_150 ) OVER ( PARTITION BY Margin, L2_Value) AS L2_150,
			Sum ( Balance_other ) OVER ( PARTITION BY Margin, L2_Value) AS L2_other,
			Sum ( Balance ) OVER ( PARTITION BY Margin, L2_Value) AS L2_All,
			L2_Multiplicator,
			-- Level 3
			L3_Value, L3_Name,
			Balance_1000, Balance_2000, Balance_100, Balance_150, Balance_other, Balance,
			L3_Multiplicator,
			--
			-- Budgets

			-- Margin
			First_Agg( Budget_Incr_1000 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_1000,
			First_Agg( Budget_Incr_2000 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_2000,
			First_Agg( Budget_Incr_100 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_100,
			First_Agg( Budget_Incr_150 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_150,
			First_Agg( Budget_Incr ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_All,
			-- Level 1
			Sum ( Budget_1000 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_1000,
			Sum ( Budget_2000 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_2000,
			Sum ( Budget_100 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_100,
			Sum ( Budget_150 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_Budget_150,
			Sum ( Budget ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_All,
			-- Level 2
			Sum ( Budget_1000 ) OVER ( PARTITION BY Margin, L2_Value) AS L2_Budget_1000,
			Sum ( Budget_2000 ) OVER ( PARTITION BY Margin, L2_Value) AS L2_Budget_2000,
			Sum ( Budget_100 ) OVER ( PARTITION BY Margin, L2_Value) AS L2_Budget_100,
			Sum ( Budget_150 ) OVER ( PARTITION BY Margin, L2_Value) AS L2_Budget_150,
			Sum ( Budget ) OVER ( PARTITION BY Margin, L2_Value) AS L2_Budget_All,
			-- Level 3
			Budget_1000, Budget_2000, Budget_100, Budget_150, Budget,
			ad_org_id
		FROM
			(
			SELECT
				seq, margin,
				L1_Name IS NOT NULL AND L1_Name != '' AS isDisplayL1Sum,
				L1_Value, L1_Name, L2_Value, L2_Name, L3_Value, L3_Name,
				-- Preparing an incremantal Sum over all lines, to later extract an incremental sum for margin level
				Balance_1000, SUM( Balance_1000 ) OVER (ORDER BY Seq ) AS Incr_1000,
				Balance_2000, SUM( Balance_2000 ) OVER (ORDER BY Seq ) AS Incr_2000,
				Balance_100, SUM( Balance_100 ) OVER (ORDER BY Seq ) AS Incr_100,
				Balance_150, SUM( Balance_150 ) OVER (ORDER BY Seq ) AS Incr_150,
				Balance_other, SUM( Balance_other ) OVER (ORDER BY Seq ) AS Incr_other,
				Balance, SUM( Balance ) OVER (ORDER BY Seq ) AS Incr,
				Budget_1000, SUM( Budget_1000 ) OVER (ORDER BY Seq ) AS Budget_Incr_1000,
				Budget_2000, SUM( Budget_2000 ) OVER (ORDER BY Seq ) AS Budget_Incr_2000,
				Budget_100, SUM( Budget_100 ) OVER (ORDER BY Seq ) AS Budget_Incr_100,
				Budget_150, SUM( Budget_150 ) OVER (ORDER BY Seq ) AS Budget_Incr_150,
				Budget, SUM( Budget ) OVER (ORDER BY Seq ) AS Budget_Incr,
				L3_Multiplicator, L2_Multiplicator, L1_Multiplicator,
				ad_org_id
			FROM
				de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data($1, $4)
			) x
		) y
	) z
	LEFT OUTER JOIN (
		SELECT
			First_Agg ( StartDate::text ORDER BY PeriodNo )::Date AS StartDate,
			$1::date AS EndDate
		FROM
			C_Period
		WHERE
			C_Year_ID = (SELECT C_Year_ID FROM C_Period WHERE C_Period_ID = report.Get_Period( 1000000,  $1::Date ))
	) date ON true
	
WHERE ad_org_id = $4
ORDER BY
	seq
 $$ 
LANGUAGE sql STABLE;






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
					, CASE WHEN postingtype = 'A' THEN AmtAcctCr - AmtAcctDr ELSE 0 END AS Balance
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








DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Unused_Balance (Year Date, showBudget character varying, AD_Org_ID numeric(10,0) );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Unused_Balance (Year Date, showBudget character varying, AD_Org_ID numeric(10,0) ) RETURNS TABLE
	(
		l_value character varying,
		l_name character varying,
		l_1000 numeric,
		l_2000 numeric,
		l_100 numeric,
		l_150 numeric,
		l_other numeric,
		l_all numeric,
		l_budget_all numeric,
		
		
		balance_1000 numeric,
		balance_2000 numeric,
		balance_100 numeric,
		balance_150 numeric,
		balance_other numeric,
		balance numeric,
		
		l_multiplicator numeric,
		
		budget_1000 numeric,
		budget_2000 numeric,
		budget_100 numeric,
		budget_150 numeric,
		budget numeric,
		
		ad_org_id numeric,
		
		
		gross_1000 numeric,
		gross_2000 numeric,
		gross_100 numeric,
		gross_150 numeric,
		gross_all numeric,
		
		startdate date,
		enddate date,
		
		l_percentage_1000 numeric,
		l_percentage_2000 numeric,
		l_percentage_100 numeric,
		l_percentage_150 numeric,
		l_percentage_all numeric,
		
		l_budget_percentage_1000 numeric,
		l_budget_percentage_2000 numeric,
		l_budget_percentage_100 numeric,
		l_budget_percentage_150 numeric,
		l_budget_percentage_all numeric,
		
		
		isdisplayother boolean
		
	)

AS 
$BODY$

SELECT
	* ,
	ABS( ROUND( Balance_1000 / Gross_1000 * 100, 2 ) ) AS L_Percentage_1000,
	ABS( ROUND( Balance_2000 / Gross_2000 * 100, 2 ) ) AS L_Percentage_2000,
	ABS( ROUND( Balance_100 / Gross_100 * 100, 2 ) ) AS L_Percentage_100,
	ABS( ROUND( Balance_150 / Gross_150 * 100, 2 ) ) AS L_Percentage_150,
	ABS( ROUND( Balance / Gross_all * 100, 2 ) ) AS L_percentage_All,
	ABS( ROUND( Balance_1000 / NULLIF( Budget_1000, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_1000,
	ABS( ROUND( Balance_2000 / NULLIF( Budget_2000, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_2000,
	ABS( ROUND( Balance_100 / NULLIF( Budget_100, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_100,
	ABS( ROUND( Balance_150 / NULLIF( Budget_150, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_150,
	ABS( ROUND( Balance / NULLIF( Budget, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_All,
	SUM( Balance_Other ) OVER () != 0 AS isDisplayOther
FROM
	(
	SELECT
		*,
		NULLIF( First_agg ( L_1000 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_1000,
		NULLIF( First_agg ( L_2000 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_2000,
		NULLIF( First_agg ( L_100 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_100,
		NULLIF( First_agg ( L_150 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_150,
		NULLIF( First_agg ( L_all ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_All
	FROM
		(
		SELECT
			--
			-- Balances

			-- Create an Incremental sum for the Margins. This sum will be Displayed in the report
			-- It is a requirement of the report that the sums are incremental over the margins
			-- Note: The window function Last_Value does not get the Last Value, but the maximum
			-- Level
			L_Value, L_Name,
			Sum ( Balance_1000 ) OVER ( PARTITION BY L_Value ) AS L_1000,
			Sum ( Balance_2000 ) OVER ( PARTITION BY L_Value ) AS L_2000,
			Sum ( Balance_100 ) OVER ( PARTITION BY L_Value ) AS L_100,
			Sum ( Balance_150 ) OVER ( PARTITION BY L_Value ) AS L_150,
			Sum ( Balance_other ) OVER ( PARTITION BY L_Value ) AS L_other,
			Sum ( Balance ) OVER ( PARTITION BY L_Value ) AS L_All,
			Sum ( Budget ) OVER ( PARTITION BY L_Value ) AS L_Budget_All,



			Balance_1000, Balance_2000, Balance_100, Balance_150, Balance_other, Balance,
			L_Multiplicator,
			--
			-- Budgets

			Budget_1000, Budget_2000, Budget_100, Budget_150, Budget,
			ad_org_id
		FROM
			(
			SELECT
				L_Value, L_Name,
				-- Preparing an incremantal Sum over all lines, to later extract an incremental sum for margin level
				Balance_1000,
				Balance_2000,
				Balance_100,
				Balance_150,
				Balance_other,
				Balance,
				Budget_1000,
				Budget_2000,
				Budget_100,
				Budget_150,
				Budget,
				L_Multiplicator,
				ad_org_id
			FROM
				de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings($1, $3)
			) x
		) y
	) z
	LEFT OUTER JOIN (
		SELECT
			First_Agg ( StartDate::text ORDER BY PeriodNo )::Date AS StartDate,
			$1 ::date AS EndDate
		FROM
			C_Period
		WHERE
			C_Year_ID = (SELECT C_Year_ID FROM C_Period WHERE C_Period_ID = report.Get_Period( 1000000,  $1::Date ))
	) date ON true
WHERE ad_org_id = $3
$BODY$
LANGUAGE sql STABLE
;
