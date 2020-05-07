-- Function: de_metas_acct.acctbalanceuntildate(numeric, numeric, date, character)

-- DROP FUNCTION de_metas_acct.acctbalanceuntildate(numeric, numeric, date, character);

CREATE OR REPLACE FUNCTION de_metas_acct.acctbalanceuntildate(p_account_id numeric, p_c_acctschema_id numeric, p_dateacct date, p_includepostingtypestatistical character DEFAULT 'N'::bpchar)
  RETURNS de_metas_acct.balanceamt AS
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
		INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fas.Account_ID)
		WHERE true
			and fas.Account_ID=$1 -- p_Account_ID
			and fas.C_AcctSchema_ID=$2 -- p_C_AcctSchema_ID
			and fas.PostingType='A'
			and fas.PA_ReportCube_ID is null
			and fas.DateAcct < $3 -- p_DateAcct
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
		INNER JOIN C_ElementValue ev on (ev.C_ElementValue_ID=fas.Account_ID)
		WHERE true
			and $4='Y' -- p_IncludePostingTypeStatistical
			and fas.Account_ID=$1 -- p_Account_ID
			and fas.C_AcctSchema_ID=$2 -- p_C_AcctSchema_ID
			and fas.PostingType='S'
			and fas.PA_ReportCube_ID is null
			and fas.DateAcct < $3 -- p_DateAcct
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
  LANGUAGE sql STABLE
  COST 100;
ALTER FUNCTION de_metas_acct.acctbalanceuntildate(numeric, numeric, date, character)
  OWNER TO metasfresh;
