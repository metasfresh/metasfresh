DROP FUNCTION IF EXISTS report.fresh_Direct_Costing_Activity_10_50 (IN Date date, IN showBudget character varying, IN showRemaining character varying, IN ad_org_id numeric(10,0)) ;


CREATE OR REPLACE FUNCTION report.fresh_Direct_Costing_Activity_10_50 (IN Date date, 
IN showBudget character varying,  -- only used in report
IN showRemaining character varying,
IN ad_org_id numeric(10,0)) -- only used in report
	RETURNS TABLE 
	(
		seq text,
		margin text,
		margin_incr_10 numeric,
		margin_incr_20 numeric,
		margin_incr_30 numeric,
		margin_incr_40 numeric,
		margin_incr_50 numeric,
		margin_incr_other numeric,
		margin_incr_all numeric,
		
		l1_value character varying,
		l1_name character varying,
		isdisplayl1sum boolean,
		l1_10 numeric,
		l1_20 numeric,
		l1_30 numeric,
		l1_40 numeric,
		l1_50 numeric,
		l1_other numeric,
		l1_all numeric,
		l1_multiplicator numeric,
		
		l2_value character varying,
		l2_name character varying,
		l2_10 numeric,
		l2_20 numeric,
		l2_30 numeric,
		l2_40 numeric,
		l2_50 numeric,
		l2_other numeric,
		l2_all numeric,
		l2_multiplicator numeric,
		
		l3_value character varying,
		l3_name character varying,
		
		balance_10 numeric,
		balance_20 numeric,
		balance_30 numeric,
		balance_40 numeric,
		balance_50 numeric,
		balance_other numeric,
		balance numeric,
		
		l3_multiplicator numeric,
		
		margin_budget_incr_10 numeric,
		margin_budget_incr_20 numeric,
		margin_budget_incr_30 numeric,
		margin_budget_incr_40 numeric,
		margin_budget_incr_50 numeric,
		margin_budget_incr_all numeric,
		
		l1_budget_10 numeric,
		l1_budget_20 numeric,
		l1_budget_30 numeric,
		l1_budget_40 numeric,
		l1_budget_50 numeric,
		l1_budget_all numeric,
		
		l2_budget_10 numeric,
		l2_budget_20 numeric,
		l2_budget_30 numeric,
		l2_budget_40 numeric,
		l2_budget_50 numeric,
		l2_budget_all numeric,
		
		budget_10 numeric,
		budget_20 numeric,
		budget_30 numeric,
		budget_40 numeric,
		budget_50 numeric,
		budget numeric,
		
		ad_org_id numeric,
		
		gross_10 numeric,
		gross_20 numeric,
		gross_30 numeric,
		gross_40 numeric,
		gross_50 numeric,
		gross_all numeric,
		
		startdate date,
		enddate date,
		
		margin_percentage_10 numeric,
		margin_percentage_20 numeric,
		margin_percentage_30 numeric,
		margin_percentage_40 numeric,
		margin_percentage_50 numeric,
		margin_percentage_all numeric,
		
		margin_budget_percentage_100 numeric,
		margin_budget_percentage_200 numeric,
		margin_budget_percentage_30 numeric,
		margin_budget_percentage_40 numeric,
		margin_budget_percentage_50 numeric,
		margin_budget_percentage_all numeric,
		
		l1_percentage_10 numeric,
		l1_percentage_20 numeric,
		l1_percentage_30 numeric,
		l1_percentage_40 numeric,
		l1_percentage_50 numeric,
		l1_percentage_all numeric,
		
		l1_budget_percentage_10 numeric,
		l1_budget_percentage_20 numeric,
		l1_budget_percentage_30 numeric,
		l1_budget_percentage_40 numeric,
		l1_budget_percentage_50 numeric,
		l1_budget_percentage_all numeric,
		
		l2_percentage_100 numeric,
		l2_percentage_200 numeric,
		l2_percentage_30 numeric,
		l2_percentage_40 numeric,
		l2_percentage_50 numeric,
		l2_percentage_all numeric,
		
		l2_budget_percentage_10 numeric,
		l2_budget_percentage_20 numeric,
		l2_budget_percentage_30 numeric,
		l2_budget_percentage_40 numeric,
		l2_budget_percentage_50 numeric,
		l2_budget_percentage_all numeric,
		
		l3_percentage_10 numeric,
		l3_percentage_20 numeric,
		l3_percentage_30 numeric,
		l3_percentage_40 numeric,
		l3_percentage_50 numeric,
		l3_percentage_all numeric,
		
		l3_budget_percentage_10 numeric,
		l3_budget_percentage_20 numeric,
		l3_budget_percentage_30 numeric,
		l3_budget_percentage_40 numeric,
		l3_budget_percentage_50 numeric,
		l3_budget_percentage_all numeric,
		
		isdisplaymarginsum boolean,
		isdisplayother boolean

		
		
	)
AS 
$$
SELECT	



	*,
	ROUND( Margin_Incr_10 / Gross_10 * 100, 2 ) AS Margin_Percentage_10,
	ROUND( Margin_Incr_20 / Gross_20 * 100, 2 ) AS Margin_Percentage_20,
	ROUND( Margin_Incr_30 / Gross_30 * 100, 2 ) AS Margin_Percentage_30,
	ROUND( Margin_Incr_40 / Gross_40 * 100, 2 ) AS Margin_Percentage_40,
	ROUND( Margin_Incr_50 / Gross_50 * 100, 2 ) AS Margin_Percentage_50,
	ROUND( Margin_Incr_all / Gross_all * 100, 2 ) AS Margin_Percentage_all,
	ROUND( Margin_Incr_10 / NULLIF( Margin_Budget_Incr_10, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_10,
	ROUND( Margin_Incr_20 / NULLIF( Margin_Budget_Incr_20, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_20,
	ROUND( Margin_Incr_30 / NULLIF( Margin_Budget_Incr_30, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_30,
	ROUND( Margin_Incr_40 / NULLIF( Margin_Budget_Incr_40, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_40,
	ROUND( Margin_Incr_50 / NULLIF( Margin_Budget_Incr_50, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_50,
	ROUND( Margin_Incr_all / NULLIF( Margin_Budget_Incr_All, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_all,

	ABS( ROUND( L1_10 / Gross_10 * 100, 2 ) ) AS L1_Percentage_10,
	ABS( ROUND( L1_20 / Gross_20 * 100, 2 ) ) AS L1_Percentage_20,
	ABS( ROUND( L1_30 / Gross_30 * 100, 2 ) ) AS L1_Percentage_30,
	ABS( ROUND( L1_40 / Gross_40 * 100, 2 ) ) AS L1_Percentage_40,
	ABS( ROUND( L1_50 / Gross_50 * 100, 2 ) ) AS L1_Percentage_50,
	ABS( ROUND( L1_All / Gross_All * 100, 2 ) ) AS L1_Percentage_All,
	ABS( ROUND( L1_10 / NULLIF( L1_Budget_10, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_10,
	ABS( ROUND( L1_20 / NULLIF( L1_Budget_20, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_20,
	ABS( ROUND( L1_30 / NULLIF( L1_Budget_30, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_30,
	ABS( ROUND( L1_40 / NULLIF( L1_Budget_40, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_40,
	ABS( ROUND( L1_50 / NULLIF( L1_Budget_50, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_50,
	ABS( ROUND( L1_All / NULLIF( L1_Budget_All, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_All,

	ABS( ROUND( L2_10 / Gross_10 * 100, 2 ) ) AS L2_Percentage_10,
	ABS( ROUND( L2_20 / Gross_20 * 100, 2 ) ) AS L2_Percentage_20,
	ABS( ROUND( L2_30 / Gross_30 * 100, 2 ) ) AS L2_Percentage_30,
	ABS( ROUND( L2_40 / Gross_40 * 100, 2 ) ) AS L2_Percentage_40,
	ABS( ROUND( L2_50 / Gross_50 * 100, 2 ) ) AS L2_Percentage_50,
	ABS( ROUND( L2_All / Gross_All * 100, 2 ) ) AS L2_Percentage_All,
	ABS( ROUND( L2_10 / NULLIF( L2_Budget_10, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_10,
	ABS( ROUND( L2_20 / NULLIF( L2_Budget_20, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_20,
	ABS( ROUND( L2_30 / NULLIF( L2_Budget_30, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_30,
	ABS( ROUND( L2_40 / NULLIF( L2_Budget_40, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_40,
	ABS( ROUND( L2_50 / NULLIF( L2_Budget_50, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_50,
	ABS( ROUND( L2_All / NULLIF( L2_Budget_All, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_All,

	ABS( ROUND( Balance_10 / Gross_10 * 100, 2 ) ) AS L3_Percentage_10,
	ABS( ROUND( Balance_20 / Gross_20 * 100, 2 ) ) AS L3_Percentage_20,
	ABS( ROUND( Balance_30 / Gross_30 * 100, 2 ) ) AS L3_Percentage_30,
	ABS( ROUND( Balance_40 / Gross_40 * 100, 2 ) ) AS L3_Percentage_40,
	ABS( ROUND( Balance_50 / Gross_50 * 100, 2 ) ) AS L3_Percentage_50,
	ABS( ROUND( Balance / Gross_all * 100, 2 ) ) AS L3_percentage_All,
	ABS( ROUND( Balance_10 / NULLIF( Budget_10, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_10,
	ABS( ROUND( Balance_20 / NULLIF( Budget_20, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_20,
	ABS( ROUND( Balance_30 / NULLIF( Budget_30, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_30,
	ABS( ROUND( Balance_40 / NULLIF( Budget_40, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_40,
	ABS( ROUND( Balance_50 / NULLIF( Budget_50, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_50,
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
		NULLIF( First_agg ( L2_10 ) OVER (ORDER BY Seq ), 0 ) AS Gross_10,
		NULLIF( First_agg ( L2_20 ) OVER (ORDER BY Seq ), 0 ) AS Gross_20,
		NULLIF( First_agg ( L2_30 ) OVER (ORDER BY Seq ), 0 ) AS Gross_30,
		NULLIF( First_agg ( L2_40 ) OVER (ORDER BY Seq ), 0 ) AS Gross_40,
		NULLIF( First_agg ( L2_50 ) OVER (ORDER BY Seq ), 0 ) AS Gross_50,
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
			First_Agg( Incr_10 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_10,
			First_Agg( Incr_20 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_20,
			First_Agg( Incr_30 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_30,
			First_Agg( Incr_40 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_40,
			First_Agg( Incr_50 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_50,
			First_Agg( Incr_other ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_other,
			First_Agg( Incr ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Incr_All,
			-- Level 1
			L1_Value, L1_Name, isDisplayL1Sum,
			Sum ( Balance_10 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_10,
			Sum ( Balance_20 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_20,
			Sum ( Balance_30 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_30,
			Sum ( Balance_40 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_40,
			Sum ( Balance_50 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_50,
			Sum ( Balance_other ) OVER ( PARTITION BY Margin, L1_Value) AS L1_other,
			Sum ( Balance ) OVER ( PARTITION BY Margin, L1_Value) AS L1_All,
			L1_Multiplicator,
			-- Level 2
			-- the reason we use partition by seq is to make different sums if there are accounts with same l2_value, in same margin but different places
			L2_Value, L2_Name,
			Sum ( Balance_10 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_10,
			Sum ( Balance_20 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_20,
			Sum ( Balance_30 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_30,			
			Sum ( Balance_40 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_40,
			Sum ( Balance_50 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_50,
			Sum ( Balance_other ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_other,
			Sum ( Balance ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_All,
			L2_Multiplicator,
			-- Level 3
			L3_Value, L3_Name,
			Balance_10, Balance_20, Balance_30, Balance_40, Balance_50, Balance_other, Balance,
			L3_Multiplicator,
			--
			-- Budgets

			-- Margin
			First_Agg( Budget_Incr_10 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_10,
			First_Agg( Budget_Incr_20 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_20,
			First_Agg( Budget_Incr_30 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_30,
			First_Agg( Budget_Incr_40 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_40,
			First_Agg( Budget_Incr_50 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_50,
			First_Agg( Budget_Incr ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_Budget_Incr_All,
			-- Level 1
			Sum ( Budget_10 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_10,
			Sum ( Budget_20 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_20,
			Sum ( Budget_30 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_30,
			Sum ( Budget_40 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_40,
			Sum ( Budget_50 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_50,
			Sum ( Budget ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_All,
			-- Level 2
			-- the reason we use partition by seq is to make different sums if there are accounts with same l2_value, in same margin but different places
			Sum ( Budget_10 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_10,
			Sum ( Budget_20 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_20,
			Sum ( Budget_30 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_30,
			Sum ( Budget_40 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_40,
			Sum ( Budget_50 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_50,
			Sum ( Budget ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_All,
			-- Level 3
			Budget_10, Budget_20, Budget_30, Budget_40, Budget_50, Budget,
			ad_org_id
		FROM
			(
			SELECT
				seq, margin,
				L1_Name IS NOT NULL AND L1_Name != '' AS isDisplayL1Sum,
				L1_Value, L1_Name, L2_Value, L2_Name, L3_Value, L3_Name,
				-- Preparing an incremantal Sum over all lines, to later extract an incremental sum for margin level
				Balance_10, SUM( Balance_10 ) OVER (ORDER BY Seq ) AS Incr_10,
				Balance_20, SUM( Balance_20 ) OVER (ORDER BY Seq ) AS Incr_20,
				Balance_30, SUM( Balance_30 ) OVER (ORDER BY Seq ) AS Incr_30,
				Balance_40, SUM( Balance_40 ) OVER (ORDER BY Seq ) AS Incr_40,
				Balance_50, SUM( Balance_50 ) OVER (ORDER BY Seq ) AS Incr_50,
				Balance_other, SUM( Balance_other ) OVER (ORDER BY Seq ) AS Incr_other,
				Balance, SUM( Balance ) OVER (ORDER BY Seq ) AS Incr,
				Budget_10, SUM( Budget_10 ) OVER (ORDER BY Seq ) AS Budget_Incr_10,
				Budget_20, SUM( Budget_20 ) OVER (ORDER BY Seq ) AS Budget_Incr_20,
				Budget_30, SUM( Budget_30 ) OVER (ORDER BY Seq ) AS Budget_Incr_30,
				Budget_40, SUM( Budget_40 ) OVER (ORDER BY Seq ) AS Budget_Incr_40,
				Budget_50, SUM( Budget_50 ) OVER (ORDER BY Seq ) AS Budget_Incr_50,
				Budget, SUM( Budget ) OVER (ORDER BY Seq ) AS Budget_Incr,
				L3_Multiplicator, L2_Multiplicator, L1_Multiplicator,
				ad_org_id
			FROM
				de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Activity_10_50($1, $4)
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
	
WHERE ad_org_id = $4 :: numeric(10,0)
ORDER BY
	seq
 $$ 
LANGUAGE sql STABLE;