-- Function: report.fresh_account_info_report_sub(numeric, date, date, numeric, character varying, character varying, character varying, numeric)

-- DROP FUNCTION report.fresh_account_info_report_sub(numeric, date, date, numeric, character varying, character varying, character varying, numeric);

DROP FUNCTION IF EXISTS report.fresh_account_info_report_sub(numeric,date, date, numeric, character varying, character varying, character varying, numeric);

DROP FUNCTION IF EXISTS report.fresh_account_info_report_sub(numeric, numeric,numeric, numeric, numeric, character varying, character varying, character varying, numeric);

CREATE OR REPLACE FUNCTION report.fresh_account_info_report_sub(
    IN account_start_id numeric,
    IN account_end_id numeric,
    IN C_Period_Start_ID numeric, 
    IN C_Period_End_ID numeric, 
    IN c_activity_id numeric,
    IN displayvoiddocuments character varying,
    IN showcurrencyexchange character varying,
    IN showonlyemptyactivity character varying,
    IN ad_org_id numeric)
  RETURNS TABLE
	(dateacct date, 
	fact_acct_id numeric, 
	bp_name text, 
	description text, 
	account2_id text, 
	a_value text, 
	amtacctdr numeric, 
	amtacctcr numeric, 
	amtacctdrend numeric, 
	amtacctcrend numeric, 
	saldo numeric, 
	carrysaldo numeric,
	param_acct_value text,
	param_acct_name text, 
	param_end_date date, 
	param_start_date date, 
	param_activity_value text, 
	param_activity_name text, 
	docstatus text, 
	conversionmultiplyrate numeric, 
	eurosaldo numeric, 
	containseur boolean, 
	ad_org_id numeric) AS
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
	SUM( fa.AmtAcctDr ) OVER (PARTITION BY param_acct_value) AS AmtAcctDrEnd,
	SUM( fa.AmtAcctCr ) OVER (PARTITION BY param_acct_value) AS AmtAcctCrEnd, 
	CarryBalance + SUM( Balance ) OVER ( 
		PARTITION BY fa.Account_ID
		ORDER BY fa.Account_ID, fa.DateAcct, fa.Fact_Acct_ID 
	) AS IterativeBalance,
	CarryBalance,
	param_acct_value,
	param_acct_name,
	(SELECT enddate::date FROM C_Period WHERE C_Period_ID = $4) AS Param_End_Date,
	(SELECT startdate::date FROM C_Period WHERE C_Period_ID = $3) AS Param_Start_Date,
	pa.value AS Param_Activity_Value,
	pa.Name AS Param_Activit_Name,
	fa.docStatus,
	ConversionMultiplyRate,
	CASE WHEN $7 = 'Y' AND ConversionMultiplyRate IS NOT NULL  THEN ConversionMultiplyRate * (CarryBalance + SUM( Balance ) OVER ()) ELSE NULL END AS EuroSaldo,
	containsEUR,
	fa.ad_org_id
FROM
	(
		SELECT 
			fa.Account_ID, fa.C_Activity_ID, fa.description, DateAcct, Fact_Acct_ID, AD_Table_ID, Line_ID, AmtAcctDr, AmtAcctCr, fa.C_BPartner_ID,
			ev.value AS Param_Acct_Value, ev.name AS Param_Acct_Name,fa.Record_ID,
			COALESCE(AmtAcctDr - AmtAcctCr, 0 ) AS Balance,
			(de_metas_acct.acctbalanceuntildate(ev.C_ElementValue_ID, acs.C_AcctSchema_ID,(SELECT startdate::date FROM C_Period WHERE C_Period_ID = $3), $9)).Balance AS CarryBalance,
			fa.DocStatus,
		        --currencyrate returns the multiplyrate of the conversion rate for: currency from , currency to, date, conversion type, client id and org id
			currencyrate(c.c_currency_id, (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR') , (SELECT enddate::date FROM C_Period WHERE C_Period_ID = $4), (SELECT C_ConversionType_ID FROM C_ConversionType where value ='P'), ci.AD_Client_ID, ci.ad_org_id) AS ConversionMultiplyRate,
			CASE WHEN $7='N' -- we don't need to check if the elementValue has a foreign currency
			THEN false
			ELSE -- check if the element value is set to show the Internation currency and if this currency is EURO. Convert to EURO in this case
				(
					exists
					(select 1 from C_ElementValue elv where ev.C_ElementValue_ID = elv.C_ElementValue_ID and elv.ShowIntCurrency = 'Y' and elv.Foreign_Currency_ID = (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR'))
				)		
			END			
			AS containsEUR,
			fa.ad_org_id
		FROM 
			( 
				SELECT ev.C_ElementValue_ID, ev.value, ev.name, ev.ad_client_id
				FROM C_ElementValue ev
				JOIN C_ElementValue ev_from ON ev_from.C_ElementValue_ID = $1 
				JOIN C_ElementValue ev_to ON ev_to.C_ElementValue_ID = $2 
				 WHERE ev.value >= ev_from.value and ev.value<= ev_to.value and char_length(ev.value) >= char_length(ev_from.value) and char_length(ev.value)<= char_length(ev_to.value) --getting elements between the selected values

			) ev
			
			LEFT OUTER JOIN Fact_Acct fa ON fa.Account_ID = ev.C_ElementValue_ID 
				AND DateAcct >= (SELECT startdate::date FROM C_Period WHERE C_Period_ID = $3)
				AND DateAcct <= (SELECT enddate::date FROM C_Period WHERE C_Period_ID = $4) 
				AND (CASE WHEN $5 IS NOT NULL THEN COALESCE( C_Activity_ID = $5, false ) ELSE TRUE END) -- this used to be COALESCE( C_Activity_ID = $5, true) and it was showing the empty ones too when activity id was set
		
			--taking the currency of the client to convert it into euro
			LEFT OUTER JOIN AD_ClientInfo ci ON ci.AD_Client_ID=ev.ad_client_id
			LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID
			LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID=c.C_Currency_ID

			LEFT OUTER JOIN C_Period period_LastYearEnd ON (period_LastYearEnd.C_Period_ID = report.Get_Predecessor_Period_Recursive ($3, (SELECT PeriodNo::int FROM C_Period WHERE C_Period_ID = $3))) 
		WHERE fa.postingtype IN ('A', 'Y') -- task 09804 don't show/sum other than current (A) -- now we have other current Y (year end)
	) fa
	LEFT OUTER JOIN GL_JournalLine jl ON fa.Line_ID = jl.GL_JournalLine_ID AND fa.AD_Table_ID = (SELECT Get_Table_ID('GL_Journal'))
	LEFT OUTER JOIN AD_Table tbl ON fa.AD_Table_ID = tbl.AD_Table_ID
	LEFT OUTER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID
	LEFT OUTER JOIN C_Activity a ON fa.C_Activity_ID = a.C_Activity_ID
	LEFT OUTER JOIN C_Activity pa ON COALESCE( pa.C_Activity_ID = $5, false )
WHERE
			
		CASE WHEN ($6 = 'Y') THEN
 			1=1
 			ELSE
 			(
				fa.DocStatus NOT IN ('CL', 'VO', 'RE')
			)
			END
			
		AND fa.ad_org_id = $9
		
		AND CASE WHEN ($8 = 'N') THEN
			1=1
			ELSE
			(
				fa.C_Activity_ID IS NULL
			)
			END
		
ORDER BY fa.Param_Acct_Value,
	fa.Account_ID,
	fa.DateAcct::Date, 
	fa.Fact_Acct_ID
 $$
  LANGUAGE sql STABLE
;




-- Function: report.fresh_account_info_report(numeric, date, date, numeric, character varying, character varying, character varying, numeric)

DROP FUNCTION IF EXISTS report.fresh_account_info_report(numeric, date, date, numeric, character varying, character varying, character varying, numeric);

DROP FUNCTION IF EXISTS  report.fresh_account_info_report(numeric,numeric, numeric, numeric, numeric, character varying, character varying, character varying, numeric);

CREATE OR REPLACE FUNCTION report.fresh_account_info_report(
    IN account_from_id numeric,
    IN account_to_id numeric,
    IN C_Period_Start_ID numeric,
    IN C_Period_End_ID numeric,
    IN c_activity_id numeric,
    IN displayvoiddocuments character varying,
    IN showcurrencyexchange character varying,
    IN showonlyemptyactivity character varying,
    IN ad_org_id numeric)
  RETURNS TABLE
	(dateacct date, 
	fact_acct_id numeric, 
	bp_name text, 
	description text, 
	account2_id text, 
	a_value text, 
	amtacctdr numeric, 
	amtacctcr numeric, 
	saldo numeric, 
	param_acct_value text,
	param_acct_name text, 
	param_end_date date, 
	param_start_date date, 
	param_activity_value text, 
	param_activity_name text, 
	overallcount bigint, 
	unionorder integer, 
	docstatus text, 
	eurosaldo numeric, 
	containseur boolean, 
	ad_org_id numeric) AS
$$
	SELECT 	DateAcct, Fact_Acct_ID, BP_Name, Description, Account2_ID, a_Value, AmtAcctDr, AmtAcctCr, Saldo, 
		Param_Acct_Value, Param_Acct_Name, Param_End_Date, Param_Start_Date, Param_Activity_Value, 
		Param_Activity_Name, count(0) OVER () AS overallcount, 2 AS UnionOrder, DocStatus, null::numeric, null::boolean,
		ad_org_id
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3, $4, $5, $6, $7, $8, $9)
	WHERE	Fact_Acct_ID IS NOT NULL
UNION ALL
	SELECT DISTINCT null::date, null::numeric, null, 'Anfangssaldo', null::text, null::text, null::numeric, null::numeric, CarrySaldo, 
		Param_Acct_Value, Param_Acct_Name, Param_End_Date, Param_Start_Date, Param_Activity_Value, Param_Activity_Name,count(0) OVER () AS overallcount, 1, null::text, null::numeric, null::boolean,
		ad_org_id
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3, $4, $5, $6, $7, $8, $9)
UNION ALL
	SELECT DISTINCT null::date, null::numeric, null, 'Summe', null::text, null::text, AmtAcctDrEnd, AmtAcctCrEnd, CarrySaldo, 
		Param_Acct_Value, Param_Acct_Name, Param_End_Date, Param_Start_Date, Param_Activity_Value, Param_Activity_Name,count(0) OVER () AS overallcount, 3, null::text, null::numeric, null::boolean,
		ad_org_id
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3, $4, $5, $6, $7, $8, $9)
UNION ALL
	(SELECT DISTINCT null::date, null::numeric, null, 'Summe in EUR', null::text, null::text, null::numeric, null::numeric, null::numeric, 
		Param_Acct_Value, Param_Acct_Name, Param_End_Date, Param_Start_Date, Param_Activity_Value, Param_Activity_Name,count(0) OVER () AS overallcount, 4, null::text, EuroSaldo, containsEUR,
		ad_org_id
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3, $4, $5, $6, $7, $8, $9)
	WHERE containsEUR = 'Y' )
ORDER BY
	Param_Acct_Value,UnionOrder,  DateAcct, 
	Fact_Acct_ID
$$
  LANGUAGE sql STABLE
;
