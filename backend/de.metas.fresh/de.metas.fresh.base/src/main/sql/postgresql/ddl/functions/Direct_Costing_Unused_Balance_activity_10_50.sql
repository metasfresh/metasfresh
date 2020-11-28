

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Unused_Balance_Activity_10_50 (Year Date, showBudget character varying, AD_Org_ID numeric(10,0) );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Unused_Balance_Activity_10_50 (Year Date, showBudget character varying, AD_Org_ID numeric(10,0) ) RETURNS TABLE
	(
		l_value character varying,
		l_name character varying,
		l_10 numeric,
		l_20 numeric,
		l_30 numeric,
		l_40 numeric,
		l_50 numeric,
		l_90 numeric,
		l_other numeric,
		l_all numeric,
		l_budget_all numeric,
		
		
		balance_10 numeric,
		balance_20 numeric,
		balance_30 numeric,
		balance_40 numeric,
		balance_50 numeric,
		balance_90 numeric,
		balance_other numeric,
		balance numeric,
		
		l_multiplicator numeric,
		
		budget_10 numeric,
		budget_20 numeric,
		budget_30 numeric,
		budget_40 numeric,
		budget_50 numeric,
		budget_90 numeric,
		budget numeric,
		
		ad_org_id numeric,
		
		
		gross_10 numeric,
		gross_20 numeric,
		gross_30 numeric,
		gross_40 numeric,
		gross_50 numeric,
		gross_90 numeric,
		gross_all numeric,
		
		startdate date,
		enddate date,
		
		l_percentage_10 numeric,
		l_percentage_20 numeric,
		l_percentage_30 numeric,
		l_percentage_40 numeric,
		l_percentage_50 numeric,
		l_percentage_90 numeric,
		l_percentage_all numeric,
		
		l_budget_percentage_10 numeric,
		l_budget_percentage_20 numeric,
		l_budget_percentage_30 numeric,
		l_budget_percentage_40 numeric,
		l_budget_percentage_50 numeric,
		l_budget_percentage_90 numeric,
		l_budget_percentage_all numeric,
		
		
		isdisplayother boolean
		
	)

AS 
$BODY$

SELECT
	* ,
	ABS( ROUND( Balance_10 / Gross_10 * 100, 2 ) ) AS L_Percentage_10,
	ABS( ROUND( Balance_20 / Gross_20 * 100, 2 ) ) AS L_Percentage_20,
	ABS( ROUND( Balance_30 / Gross_30 * 100, 2 ) ) AS L_Percentage_30,
	ABS( ROUND( Balance_40 / Gross_40 * 100, 2 ) ) AS L_Percentage_40,
	ABS( ROUND( Balance_50 / Gross_50 * 100, 2 ) ) AS L_Percentage_50,
	ABS( ROUND( Balance_90 / Gross_90 * 100, 2 ) ) AS L_Percentage_90,
	ABS( ROUND( Balance / Gross_all * 100, 2 ) ) AS L_percentage_All,
	ABS( ROUND( Balance_10 / NULLIF( Budget_10, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_10,
	ABS( ROUND( Balance_20 / NULLIF( Budget_20, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_20,
	ABS( ROUND( Balance_30 / NULLIF( Budget_30, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_30,
	ABS( ROUND( Balance_40 / NULLIF( Budget_40, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_40,
	ABS( ROUND( Balance_50 / NULLIF( Budget_50, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_50,
	ABS( ROUND( Balance_90 / NULLIF( Budget_90, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_90,
	ABS( ROUND( Balance / NULLIF( Budget, 0 ) * 100, 2 ) ) AS L_Budget_Percentage_All,
	SUM( Balance_Other ) OVER () != 0 AS isDisplayOther
FROM
	(
	SELECT
		*,
		NULLIF( First_agg ( L_10 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_10,
		NULLIF( First_agg ( L_20 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_20,
		NULLIF( First_agg ( L_30 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_30,
		NULLIF( First_agg ( L_40 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_40,
		NULLIF( First_agg ( L_50 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_50,
		NULLIF( First_agg ( L_90 ) OVER ( ORDER BY L_Value ), 0 ) AS Gross_90,
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
			Sum ( Balance_10 ) OVER ( PARTITION BY L_Value ) AS L_10,
			Sum ( Balance_20 ) OVER ( PARTITION BY L_Value ) AS L_20,
			Sum ( Balance_30 ) OVER ( PARTITION BY L_Value ) AS L_30,
			Sum ( Balance_40 ) OVER ( PARTITION BY L_Value ) AS L_40,
			Sum ( Balance_50 ) OVER ( PARTITION BY L_Value ) AS L_50,
			Sum ( Balance_90 ) OVER ( PARTITION BY L_Value ) AS L_90,
			Sum ( Balance_other ) OVER ( PARTITION BY L_Value ) AS L_other,
			Sum ( Balance ) OVER ( PARTITION BY L_Value ) AS L_All,
			Sum ( Budget ) OVER ( PARTITION BY L_Value ) AS L_Budget_All,



			Balance_10, Balance_20, Balance_30, Balance_40, Balance_50, Balance_90, Balance_other, Balance,
			L_Multiplicator,
			--
			-- Budgets

			Budget_10, Budget_20, Budget_30, Budget_40, Budget_50, Budget_90, Budget,
			ad_org_id
		FROM
			(
			SELECT
				L_Value, L_Name,
				-- Preparing an incremantal Sum over all lines, to later extract an incremental sum for margin level
				Balance_10,
				Balance_20,
				Balance_30,
				Balance_40,
				Balance_50,
				Balance_90,
				Balance_other,
				Balance,
				Budget_10,
				Budget_20,
				Budget_30,
				Budget_40,
				Budget_50,
				Budget_90,
				Budget,
				L_Multiplicator,
				ad_org_id
			FROM
				de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Remainings_Activity_10_50($1, $3)
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
			C_Year_ID = (SELECT C_Year_ID FROM C_Period WHERE C_Period_ID = report.Get_Period( 1000000,  $1::Date ) AND isActive = 'Y') AND isActive = 'Y'

	) date ON true
WHERE ad_org_id = $3
$BODY$
LANGUAGE sql STABLE
;