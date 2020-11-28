drop function if exists Fact_Acct_EndingBalance(factLine Fact_Acct);
drop function if exists de_metas_acct.Fact_Acct_EndingBalance(factLine Fact_Acct);
create or replace function de_metas_acct.Fact_Acct_EndingBalance(factLine Fact_Acct)
RETURNS numeric AS
$BODY$
	SELECT acctBalance(($1).Account_ID, COALESCE(SUM(AmtAcctDr), 0), COALESCE(SUM(AmtAcctCr), 0))
	FROM (
		(
			SELECT
				(case when ev.AccountType in ('E', 'R') then fa.AmtAcctDr_YTD else fa.AmtAcctDr end) as AmtAcctDr
				, (case when ev.AccountType in ('E', 'R') then fa.AmtAcctCr_YTD else fa.AmtAcctCr end) as AmtAcctCr
			FROM Fact_Acct_Summary fa
			INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fa.Account_ID)
			WHERE true
				and fa.Account_ID=($1).Account_ID
				and fa.C_AcctSchema_ID=($1).C_AcctSchema_ID
				and fa.PostingType=($1).PostingType
				and fa.DateAcct = ($1).DateAcct - interval '1 day'
				and fa.AD_Org_ID = ($1).AD_Org_ID
			ORDER BY fa.DateAcct DESC
			LIMIT 1
		)
		UNION ALL
		(
			select
				fa.AmtAcctDr_DTD
				, fa.AmtAcctCr_DTD
			from Fact_Acct_EndingBalance fa
			where true
				and fa.Fact_Acct_ID = ($1).Fact_Acct_ID
		)
	) t
	;
$BODY$
LANGUAGE sql STABLE;

