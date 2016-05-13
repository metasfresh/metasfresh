


	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3, $4, $5, $6 )
	WHERE	Fact_Acct_ID IS NOT NULL
UNION ALL
	SELECT DISTINCT null::date, null::numeric, null, 'Anfangssaldo', null::text, null::text, null::numeric, null::numeric, CarrySaldo, 
		ad_org_id, ad_client_id
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3,  $4, $5, $6 )
UNION ALL
	SELECT DISTINCT null::date, null::numeric, null, 'Summe', null::text, null::text, AmtAcctDrEnd, AmtAcctCrEnd, CarrySaldo, 
		ad_org_id, ad_client_id
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3,  $4, $5, $6 )
UNION ALL
	(SELECT DISTINCT null::date, null::numeric, null, 'Summe in EUR', null::text, null::text, null::numeric, null::numeric, null::numeric, 
		ad_org_id, ad_client_id
	FROM 	report.fresh_Account_Info_Report_Sub ($1, $2, $3,  $4, $5, $6 )
	WHERE containsEUR = 'Y' )
ORDER BY
	UnionOrder, DateAcct, 
	Fact_Acct_ID
$$ 
LANGUAGE sql STABLE;


