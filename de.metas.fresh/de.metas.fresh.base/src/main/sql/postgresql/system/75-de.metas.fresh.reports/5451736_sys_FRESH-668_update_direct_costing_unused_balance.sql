

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
			C_Year_ID = (SELECT C_Year_ID FROM C_Period WHERE C_Period_ID = report.Get_Period( 1000000,  $1::Date ) AND isActive = 'Y') AND isActive = 'Y'
	) date ON true
WHERE ad_org_id = $3
$BODY$
LANGUAGE sql STABLE
;