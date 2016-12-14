drop function if exists de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date,   p_IncludePostingTypeStatistical char);

drop function if exists de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, ad_org_id numeric,  p_IncludePostingTypeStatistical char(1));
drop function if exists de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, ad_org_id numeric,  p_IncludePostingTypeStatistical char(1), p_ExcludePostingTypeYearEnd char(1));

/* 

-- This type shall be already in the database. Do not create it again

CREATE TYPE de_metas_acct.BalanceAmt AS
(
	Balance numeric
    , Debit numeric
    , Credit numeric
);

 */
create or replace function de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, p_AD_Org_ID numeric(10,0),  p_IncludePostingTypeStatistical char(1) = 'N',  p_ExcludePostingTypeYearEnd char(1) = 'N')
RETURNS de_metas_acct.BalanceAmt AS
$BODY$
-- NOTE: we use COALESCE(SUM(..)) just to make sure we are not returning null
SELECT ROW(SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
FROM (
	(
		SELECT
			(case
				-- When the account is Expense/Revenue => we shall consider only the Year to Date amount
				when ev.AccountType in ('E', 'R') and fas.DateAcct>=date_trunc('year', $3) then ROW(fas.AmtAcctDr_YTD - fas.AmtAcctCr_YTD, fas.AmtAcctDr_YTD, fas.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
				when ev.AccountType in ('E', 'R') then ROW(0, 0, 0)::de_metas_acct.BalanceAmt
				-- For any other account => we consider from the beginning to Date amount
				else ROW(fas.AmtAcctDr - fas.AmtAcctCr, fas.AmtAcctDr, fas.AmtAcctCr)::de_metas_acct.BalanceAmt
			end) as Balance
		FROM Fact_Acct_Summary fas
		INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fas.Account_ID) AND ev.isActive = 'Y'
		WHERE true
			and fas.Account_ID=$1 -- p_Account_ID
			and fas.C_AcctSchema_ID=$2 -- p_C_AcctSchema_ID
			and fas.PostingType='A'
			and fas.PA_ReportCube_ID is null
			and fas.DateAcct <= $3 -- p_DateAcct
			and fas.ad_org_id = $4 -- p_AD_Org_ID
			and fas.isActive = 'Y'
		ORDER BY fas.DateAcct DESC
		LIMIT 1
	)
	-- Include posting type year end 
	UNION ALL(
	SELECT
			(case
				-- When the account is Expense/Revenue => we shall consider only the Year to Date amount
				when ev.AccountType in ('E', 'R') and fas.DateAcct>=date_trunc('year', $3) then ROW(fas.AmtAcctDr_YTD - fas.AmtAcctCr_YTD, fas.AmtAcctDr_YTD, fas.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
				when ev.AccountType in ('E', 'R') then ROW(0, 0, 0)::de_metas_acct.BalanceAmt
				-- For any other account => we consider from the beginning to Date amount
				else ROW(fas.AmtAcctDr - fas.AmtAcctCr, fas.AmtAcctDr, fas.AmtAcctCr)::de_metas_acct.BalanceAmt
			end) as Balance
		FROM Fact_Acct_Summary fas
		INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fas.Account_ID)
		WHERE true
			and fas.Account_ID=$1 -- p_Account_ID
			and fas.C_AcctSchema_ID=$2 -- p_C_AcctSchema_ID
			and fas.PostingType='Y'
			and fas.PA_ReportCube_ID is null
			and fas.DateAcct <= $3 -- p_DateAcct
			and fas.ad_org_id = $4 -- p_AD_Org_ID
			and $6 = 'N'
		ORDER BY fas.DateAcct DESC
		LIMIT 1
	)
	-- For PostingType=Statistical
	UNION ALL
	(
		SELECT
			(case
				-- When the account is Expense/Revenue => we shall consider only the Year to Date amount
				when ev.AccountType in ('E', 'R') and fas.DateAcct>=date_trunc('year', $3) then ROW(fas.AmtAcctDr_YTD - fas.AmtAcctCr_YTD, fas.AmtAcctDr_YTD, fas.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
				when ev.AccountType in ('E', 'R') then ROW(0, 0, 0)::de_metas_acct.BalanceAmt
				-- For any other account => we consider from the beginning to Date amount
				else ROW(fas.AmtAcctDr - fas.AmtAcctCr, fas.AmtAcctDr, fas.AmtAcctCr)::de_metas_acct.BalanceAmt
			end) as Balance
		FROM Fact_Acct_Summary fas
		INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fas.Account_ID) AND ev.isActive = 'Y'
		WHERE true
			and $5='Y' -- p_IncludePostingTypeStatistical
			and fas.Account_ID=$1 -- p_Account_ID
			and fas.C_AcctSchema_ID=$2 -- p_C_AcctSchema_ID
			and fas.PostingType='S'
			and fas.PA_ReportCube_ID is null
			and fas.DateAcct <= $3 -- p_DateAcct
			and fas.ad_org_id = $4 -- p_AD_Org_ID
			and fas.isActive = 'Y'
		ORDER BY fas.DateAcct DESC
		LIMIT 1
	)
	-- Default value:
	UNION ALL
	(
		SELECT ROW(0, 0, 0)::de_metas_acct.BalanceAmt
	)
) t
$BODY$
LANGUAGE sql STABLE;

