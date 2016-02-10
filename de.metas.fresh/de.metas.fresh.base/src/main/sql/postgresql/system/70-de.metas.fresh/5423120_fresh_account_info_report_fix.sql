DROP FUNCTION IF EXISTS report.fresh_Account_Info_Report_Sub ( IN Account_ID numeric (10,0), IN StartDate date, IN EndDate date, IN C_Activity_ID numeric(10,0) );

CREATE FUNCTION report.fresh_Account_Info_Report_Sub ( IN Account_ID numeric (10,0), IN StartDate date, IN EndDate date, IN C_Activity_ID numeric(10,0) ) 
	RETURNS TABLE ( 
		DateAcct date, 
		Fact_Acct_ID numeric (10,0), 
		BP_Name text, 
		Description text,
		Account2_ID text,
		A_Value text,
		AmtAcctDr numeric, 
		AmtAcctCr numeric,
		AmtAcctDrEnd numeric,
		AmtAcctCrEnd numeric,
		Saldo numeric,
		CarrySaldo numeric,
		Param_Acct_Value text,
		Param_Acct_Name text,
		Param_End_Date text,
		Param_Start_Date text,
		Param_Activity_Value text,
		Param_Activity_Name text
	) AS 
$$
SELECT
	fa.DateAcct::Date, 
	fa.Fact_Acct_ID,
	COALESCE ( 
		Replace( Substring( jl.description for position('/' in jl.description) ), '/', ''),
		bp.name
	) AS BP_Name,
	COALESCE (
		Replace( Substring( jl.description from position('/' in jl.description) ), '/', ''),
		COALESCE (tbl.name || ' ', '') || fa.Description 
	) AS Description,
	(CASE WHEN 
		(SELECT count(0) FROM Fact_Acct fa2 
		WHERE fa.ad_table_id = fa2.ad_table_id AND fa2.Record_ID =fa.Record_ID 
			AND fa.Fact_Acct_id != fa2.Fact_Acct_id 
			AND (case when fa.amtacctdr != 0 then fa2.amtacctcr != 0 when fa.amtacctcr != 0 then fa2.amtacctdr != 0 else false end )) 
	= 1 THEN (SELECT ev2.value||' '|| ev2.name FROM Fact_Acct fa2 
			inner join C_ElementValue ev2 ON fa2.Account_ID = ev2.C_ElementValue_ID
		  WHERE fa.ad_table_id = fa2.ad_table_id AND fa2.Record_ID =fa.Record_ID 
			AND fa.Fact_Acct_id != fa2.Fact_Acct_id 
			AND (case when fa.amtacctdr != 0 then fa2.amtacctcr != 0 when fa.amtacctcr != 0 then fa2.amtacctdr != 0 else false end ))
	ELSE ''
	END) AS Account2_ID, -- this selects the name and value of one or no element value, that is matching with the current fact_acct (see when you press verbucht on your docu there is more than 1 line). Later shall be changed in some way so it can selece more, but currently we cannot associate more  

	a.value AS A_Value,
	fa.AmtAcctDr, 
	fa.AmtAcctCr, 
	SUM( fa.AmtAcctDr ) OVER () AS AmtAcctDrEnd,
	SUM( fa.AmtAcctCr ) OVER () AS AmtAcctCrEnd, 
	CarryBalance + SUM( Balance ) OVER ( 
		PARTITION BY fa.Account_ID 
		ORDER BY fa.Account_ID, fa.DateAcct, fa.Fact_Acct_ID 
	) AS IterativeBalance,
	CarryBalance,
	
	Param_Acct_Value,
	Param_Acct_Name,
	to_char($3, 'DD.MM.YYYY') AS Param_End_Date,
	to_char($2, 'DD.MM.YYYY') AS Param_Start_Date,
	pa.value AS Param_Activity_Value,
	pa.Name AS Param_Activit_Name
FROM
	(
		SELECT 
			fa.Account_ID, fa.C_Activity_ID, fa.description, DateAcct, Fact_Acct_ID, AD_Table_ID, Line_ID, AmtAcctDr, AmtAcctCr, fa.C_BPartner_ID,
			ev.value AS Param_Acct_Value, ev.name AS Param_Acct_Name,fa.Record_ID,
			COALESCE( CASE WHEN isCalculationSwitched THEN AmtAcctDr - AmtAcctCr ELSE AmtAcctCr - AmtAcctDr END, 0 ) AS Balance,
			COALESCE( CASE WHEN isCalculationSwitched THEN switchedCarryBalance ELSE CarryBalance END, 0 ) AS CarryBalance
		FROM 
			( 
				SELECT ev.C_ElementValue_ID, ev.value, ev.name, Accounttype IN ('A', 'R', 'O') AS isCalculationSwitched 
				FROM C_ElementValue ev WHERE C_ElementValue_ID = $1 
			) ev
			LEFT OUTER JOIN Fact_Acct fa ON fa.Account_ID = ev.C_ElementValue_ID 
				AND DateAcct >= $2 AND DateAcct <= $3 
				AND (CASE WHEN $4 IS NOT NULL THEN COALESCE( C_Activity_ID = $4, false ) ELSE TRUE END) -- this used to be COALESCE( C_Activity_ID = $4, true) and it was showing the empty ones too when activity id was set
			LEFT OUTER JOIN 
			(
				SELECT SUM ( AmtAcctDr - AmtAcctCr ) AS switchedCarryBalance,  SUM ( fa.AmtAcctCr - AmtAcctDr ) AS CarryBalance, fa.Account_ID 
				FROM Fact_Acct fa WHERE fa.DateAcct < $2 GROUP BY Account_ID
			) cb ON cb.Account_ID = C_ElementValue_ID
	) fa
	LEFT OUTER JOIN GL_JournalLine jl ON fa.Line_ID = jl.GL_JournalLine_ID AND fa.AD_Table_ID = (SELECT Get_Table_ID('GL_Journal'))
	LEFT OUTER JOIN AD_Table tbl ON fa.AD_Table_ID = tbl.AD_Table_ID
	LEFT OUTER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN C_Activity a ON fa.C_Activity_ID = a.C_Activity_ID
	LEFT OUTER JOIN C_Activity pa ON COALESCE( pa.C_Activity_ID = $4, false )
ORDER BY 
	fa.Account_ID,
	fa.DateAcct::Date, 
	fa.Fact_Acct_ID
$$ 
LANGUAGE sql STABLE;


DROP FUNCTION IF EXISTS report.fresh_Account_Info_Report ( IN Account_ID numeric (10,0), IN StartDate date, IN EndDate date, IN C_Activity_ID numeric(10,0)  );

CREATE FUNCTION report.fresh_Account_Info_Report ( IN Account_ID numeric (10,0), IN StartDate date, IN EndDate date, IN C_Activity_ID numeric(10,0)  ) 
	RETURNS TABLE ( 
		DateAcct date, 
		Fact_Acct_ID numeric (10,0), 
		BP_Name text, 
		Description text,
		Account2_ID text,
		A_Value text,
		AmtAcctDr numeric, 
		AmtAcctCr numeric, 
		Saldo numeric,
		Param_Acct_Value text,
		Param_Acct_Name text,
		Param_End_Date text,
		Param_Start_Date text,
		Param_Activity_Value text,
		Param_Activity_Name text,
		overallcount bigint,
		UnionOrder integer
	) AS 
$$
	SELECT 	DateAcct, Fact_Acct_ID, BP_Name, Description, Account2_ID, a_Value, AmtAcctDr, AmtAcctCr, Saldo, 
		Param_Acct_Value, Param_Acct_Name, Param_End_Date, Param_Start_Date, Param_Activity_Value, 
		Param_Activity_Name, count(0) OVER () AS overallcount, 2 AS UnionOrder
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3, $4 )
	WHERE	Fact_Acct_ID IS NOT NULL
UNION ALL
	SELECT DISTINCT null::date, null::numeric, null, 'Anfangssaldo', null::text, null::text, null::numeric, null::numeric, CarrySaldo, 
		Param_Acct_Value, Param_Acct_Name, Param_End_Date, Param_Start_Date, Param_Activity_Value, Param_Activity_Name,count(0) OVER () AS overallcount, 1
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3, $4 )
UNION ALL
	SELECT DISTINCT null::date, null::numeric, null, 'Summe', null::text, null::text, AmtAcctDrEnd, AmtAcctCrEnd, CarrySaldo, 
		Param_Acct_Value, Param_Acct_Name, Param_End_Date, Param_Start_Date, Param_Activity_Value, Param_Activity_Name,count(0) OVER () AS overallcount, 3
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3, $4 )
ORDER BY
	UnionOrder, DateAcct, 
	Fact_Acct_ID
$$ 
LANGUAGE sql STABLE;
