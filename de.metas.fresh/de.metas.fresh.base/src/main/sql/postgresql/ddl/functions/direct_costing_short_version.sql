DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Short_Version(Date Date);

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Direct_Costing_Short_Version(Date Date, ad_org_id numeric(10,0));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Direct_Costing_Short_Version(Date Date, ad_org_id numeric(10,0))
RETURNS TABLE
	(
	seq text, 
	margin text,
	Margin_Incr_1000 numeric, Margin_Incr_2000 numeric,	Margin_Incr_100 numeric, Margin_Incr_150 numeric, Margin_Incr_other numeric, Margin_Incr_All numeric,		
	L1_Value character varying, L1_Name character varying, 
	isDisplayL1Sum boolean,
	L1_1000 numeric, L1_2000 numeric, L1_100 numeric, L1_150 numeric, L1_other numeric,	L1_All numeric,
	L1_Multiplicator numeric, L2_Value character varying, L2_Name character varying,
	L2_1000 numeric, L2_2000 numeric, L2_100 numeric, L2_150 numeric, L2_other numeric,	L2_All numeric,
	L2_Multiplicator numeric,
	L3_Value character varying, L3_Name character varying,
	Balance_1000 numeric, Balance_2000 numeric, Balance_100 numeric, Balance_150 numeric, Balance_other numeric, Balance numeric,
	L3_Multiplicator numeric,
	Margin_Budget_Incr_1000 numeric, Margin_Budget_Incr_2000 numeric, Margin_Budget_Incr_100 numeric, Margin_Budget_Incr_150 numeric, Margin_Budget_Incr_All numeric,
	L1_Budget_1000 numeric,	L1_Budget_2000 numeric,	L1_Budget_100 numeric, L1_Budget_150 numeric, L1_Budget_All numeric,	
	L2_Budget_1000 numeric,	L2_Budget_2000 numeric,	L2_Budget_100 numeric, L2_Budget_150 numeric, L2_Budget_All numeric,
	Budget_1000 numeric, Budget_2000 numeric, Budget_100 numeric, Budget_150 numeric, Budget numeric,
	Margin_IncrLY_1000 numeric,	Margin_IncrLY_2000 numeric,	Margin_IncrLY_100 numeric,	Margin_IncrLY_150 numeric,	Margin_IncrLY_All numeric,
	L1_BalanceLY_1000 numeric, L1_BalanceLY_2000 numeric, L1_BalanceLY_100 numeric, L1_BalanceLY_150 numeric, L1_BalanceLY_All numeric,
	L2_BalanceLY_1000 numeric, L2_BalanceLY_2000 numeric, L2_BalanceLY_100 numeric,	L2_BalanceLY_150 numeric, L2_BalanceLY_All numeric,
	BalanceLY_1000 numeric, BalanceLY_2000 numeric, BalanceLY_100 numeric, BalanceLY_150 numeric, BalanceLY numeric,
	
	ad_org_id numeric,
	
	Gross_1000 numeric,	Gross_2000 numeric,	Gross_100 numeric, Gross_150 numeric, Gross_All numeric,
	startdate date,	enddate date,
	Margin_Percentage_all numeric,	Margin_Budget_Percentage_all numeric, Margin_BalanceLY_Percentage_all numeric,
	L1_Percentage_All numeric, L1_Budget_Percentage_All numeric, L1_BalanceLY_Percentage_All numeric,
    L2_Percentage_All numeric, L2_Budget_Percentage_All numeric, L2_BalanceLY_Percentage_All numeric,
	L3_percentage_All numeric, L3_Budget_Percentage_All numeric, L3_BalanceLY_Percentage_All numeric,
	isDisplayMarginSum boolean, isDisplayOther boolean
	

	)
AS 
$BODY$
SELECT
	*,
	ROUND( Margin_Incr_all / Gross_all * 100, 2 ) AS Margin_Percentage_all,
	ROUND( Margin_Incr_all / NULLIF( Margin_Budget_Incr_All, 0 ) * 100, 2 ) AS Margin_Budget_Percentage_all,
	ROUND( Margin_IncrLY_all / NULLIF( Margin_IncrLY_All, 0 ) * 100, 2 ) AS Margin_BalanceLY_Percentage_all,

	ABS( ROUND( L1_All / Gross_All * 100, 2 ) ) AS L1_Percentage_All,
	ABS( ROUND( L1_All / NULLIF( L1_Budget_All, 0 ) * 100, 2 ) ) AS L1_Budget_Percentage_All,
	ABS( ROUND( L1_All / NULLIF( L1_BalanceLY_All, 0 ) * 100, 2 ) ) AS L1_BalanceLY_Percentage_All,

	ABS( ROUND( L2_All / Gross_All * 100, 2 ) ) AS L2_Percentage_All,
	ABS( ROUND( L2_All / NULLIF( L2_Budget_All, 0 ) * 100, 2 ) ) AS L2_Budget_Percentage_All,
	ABS( ROUND( L2_All / NULLIF( L2_BalanceLY_All, 0 ) * 100, 2 ) ) AS L2_BalanceLY_Percentage_All,

	ABS( ROUND( Balance / Gross_all * 100, 2 ) ) AS L3_percentage_All,
	ABS( ROUND( Balance / NULLIF( Budget, 0 ) * 100, 2 ) ) AS L3_Budget_Percentage_All,
	ABS( ROUND( Balance / NULLIF( BalanceLY, 0 ) * 100, 2 ) ) AS L3_BalanceLY_Percentage_All,

	-- If current Margin is the Last Margin, display the final sum group footer
	First_Agg( Margin ) OVER ( ORDER BY Seq Desc ) != Margin AS isDisplayMarginSum,

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
			-- the reason we use partition by seq is to make different sums if there are accounts with same l2_value, in same margin but different places
			L2_Value, L2_Name,
			Sum ( Balance_1000 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_1000,
			Sum ( Balance_2000 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_2000,
			Sum ( Balance_100 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_100,
			Sum ( Balance_150 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_150,
			Sum ( Balance_other ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_other,
			Sum ( Balance ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_All,
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
			Sum ( Budget_2000 ) OVER ( PARTITION BY Margin, L1_Value  ) AS L1_Budget_2000,
			Sum ( Budget_100 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_Budget_100,
			Sum ( Budget_150 ) OVER ( PARTITION BY Margin, L1_Value) AS L1_Budget_150,
			Sum ( Budget ) OVER ( PARTITION BY Margin, L1_Value) AS L1_Budget_All,
			-- Level 2
			-- the reason we use partition by seq is to make different sums if there are accounts with same l2_value, in same margin but different places
			Sum ( Budget_1000 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_1000,
			Sum ( Budget_2000 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_2000,
			Sum ( Budget_100 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_100,
			Sum ( Budget_150 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_150,
			Sum ( Budget ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_Budget_All,
			-- Level 3
			Budget_1000, Budget_2000, Budget_100, Budget_150, Budget,

			-- balance last year

			-- Margin
			First_Agg( IncrLY_1000 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_IncrLY_1000,
			First_Agg( IncrLY_2000 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_IncrLY_2000,
			First_Agg( IncrLY_100 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_IncrLY_100,
			First_Agg( IncrLY_150 ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_IncrLY_150,
			First_Agg( IncrLY ) OVER ( PARTITION BY Margin ORDER BY Seq DESC ) AS Margin_IncrLY_All,
			-- Level 1
			Sum ( BalanceLY_1000 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_BalanceLY_1000,
			Sum ( BalanceLY_2000 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_BalanceLY_2000,
			Sum ( BalanceLY_100 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_BalanceLY_100,
			Sum ( BalanceLY_150 ) OVER ( PARTITION BY Margin, L1_Value ) AS L1_BalanceLY_150,
			Sum ( BalanceLY ) OVER ( PARTITION BY Margin, L1_Value) AS L1_BalanceLY_All,
			-- Level 2
			-- the reason we use partition by seq is to make different sums if there are accounts with same l2_value, in same margin but different places
			Sum ( BalanceLY_1000 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_BalanceLY_1000,
			Sum ( BalanceLY_2000 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_BalanceLY_2000,
			Sum ( BalanceLY_100 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_BalanceLY_100,
			Sum ( BalanceLY_150 ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_BalanceLY_150,
			Sum ( BalanceLY ) OVER ( PARTITION BY Margin, L2_Value, substring(seq from 1 for 15)) AS L2_BalanceLY_All,
			-- Level 3
			BalanceLY_1000, BalanceLY_2000, BalanceLY_100, BalanceLY_150, BalanceLY,
			
			x.ad_org_id

		FROM
			(
			SELECT
				seq, margin,
				L1_Name IS NOT NULL AND L1_Name != '' AS isDisplayL1Sum,
				L1_Value, L1_Name, L2_Value, L2_Name, L3_Value, L3_Name,
				-- Preparing an incremantal Sum over all lines, to later extract an incremental sum for margin level
				Balance_1000, SUM( Balance_1000 ) OVER ( ORDER BY Seq ) AS Incr_1000,
				Balance_2000, SUM( Balance_2000 ) OVER ( ORDER BY Seq ) AS Incr_2000,
				Balance_100, SUM( Balance_100 ) OVER (ORDER BY Seq ) AS Incr_100,
				Balance_150, SUM( Balance_150 ) OVER ( ORDER BY Seq ) AS Incr_150,
				Balance_other, SUM( Balance_other ) OVER (ORDER BY Seq ) AS Incr_other,
				Balance, SUM( Balance ) OVER ( ORDER BY Seq ) AS Incr,
				Budget_1000, SUM( Budget_1000 ) OVER ( ORDER BY Seq ) AS Budget_Incr_1000,
				Budget_2000, SUM( Budget_2000 ) OVER ( ORDER BY Seq ) AS Budget_Incr_2000,
				Budget_100, SUM( Budget_100 ) OVER ( ORDER BY Seq ) AS Budget_Incr_100,
				Budget_150, SUM( Budget_150 ) OVER (ORDER BY Seq ) AS Budget_Incr_150,
				Budget, SUM( Budget ) OVER ( ORDER BY Seq ) AS Budget_Incr,
				BalanceLY_1000, SUM( BalanceLY_1000 ) OVER (ORDER BY Seq ) AS IncrLY_1000,
				BalanceLY_2000, SUM( BalanceLY_2000 ) OVER (ORDER BY Seq ) AS IncrLY_2000,
				BalanceLY_100, SUM( BalanceLY_100 ) OVER ( ORDER BY Seq ) AS IncrLY_100,
				BalanceLY_150, SUM( BalanceLY_150 ) OVER (ORDER BY Seq ) AS IncrLY_150,
				BalanceLY_other, SUM( BalanceLY_other ) OVER (ORDER BY Seq ) AS IncrLY_other,
				BalanceLY, SUM( BalanceLY ) OVER ( ORDER BY Seq ) AS IncrLY,
				L3_Multiplicator, L2_Multiplicator, L1_Multiplicator,
				
				ad_org_id
			FROM
				de_metas_endcustomer_fresh_reports.Direct_Costing_Raw_Data_Include_LastYear($1, $2)
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
WHERE ad_org_id = $2
ORDER BY
	seq
;
$BODY$
LANGUAGE sql STABLE
;	
