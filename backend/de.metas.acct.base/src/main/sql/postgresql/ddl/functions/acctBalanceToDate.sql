DROP FUNCTION IF EXISTS de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, p_IncludePostingTypeStatistical char);

DROP FUNCTION IF EXISTS de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, ad_org_id numeric, p_IncludePostingTypeStatistical char(1));
DROP FUNCTION IF EXISTS de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, ad_org_id numeric, p_IncludePostingTypeStatistical char(1), p_ExcludePostingTypeYearEnd char(1));

CREATE OR REPLACE FUNCTION de_metas_acct.acctBalanceToDate(p_Account_ID numeric,
                                                           p_C_AcctSchema_ID numeric,
                                                           p_DateAcct date,
                                                           p_AD_Org_ID numeric(10, 0),
                                                           p_IncludePostingTypeStatistical char(1) = 'N',
                                                           p_ExcludePostingTypeYearEnd char(1) = 'N')
    RETURNS de_metas_acct.BalanceAmt
AS
$BODY$
WITH filteredAndOrdered AS (
    SELECT --
           fas.PostingType,
           ev.AccountType,
           fas.AmtAcctCr,
           fas.AmtAcctCr_YTD,
           fas.AmtAcctDr,
           fas.AmtAcctDr_YTD,
           fas.DateAcct
    FROM Fact_Acct_Summary fas
             INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fas.Account_ID) AND ev.isActive = 'Y'
    WHERE TRUE
      AND fas.Account_ID = $1      -- p_Account_ID
      AND fas.C_AcctSchema_ID = $2 -- p_C_AcctSchema_ID
      AND fas.PA_ReportCube_ID IS NULL
      AND fas.DateAcct <= $3       -- p_DateAcct
      AND fas.ad_org_id = $4       -- p_AD_Org_ID
      AND fas.isActive = 'Y'
)
-- NOTE: we use COALESCE(SUM(..)) just to make sure we are not returning null
SELECT ROW (SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
FROM (
         -- Include posting type Actual
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                         ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                 END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND fo.PostingType = 'A'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Include posting type Year End
         UNION ALL
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                         ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                 END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND $6 = 'N' -- p_ExcludePostingTypeYearEnd
               AND fo.PostingType = 'Y'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Include posting type Statistical
         UNION ALL
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                         ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                 END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND $5 = 'Y' -- p_IncludePostingTypeStatistical
               AND fo.PostingType = 'S'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Default value:
         UNION ALL
         (
             SELECT ROW (0, 0, 0)::de_metas_acct.BalanceAmt
         )
     ) t
$BODY$
    LANGUAGE sql STABLE;

