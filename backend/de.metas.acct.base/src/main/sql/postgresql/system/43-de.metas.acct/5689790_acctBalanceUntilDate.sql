DROP FUNCTION IF EXISTS de_metas_acct.acctbalanceuntildate(numeric,
                                                           numeric,
                                                           date,
                                                           character)
;

DROP FUNCTION IF EXISTS de_metas_acct.acctbalanceuntildate(p_account_id                    numeric,
                                                           p_c_acctschema_id               numeric,
                                                           p_dateacct                      date,
                                                           ad_org_id                       numeric,
                                                           p_includepostingtypestatistical character)
;

DROP FUNCTION IF EXISTS de_metas_acct.acctbalanceuntildate(p_account_id                    numeric,
                                                           p_c_acctschema_id               numeric,
                                                           p_dateacct                      date,
                                                           ad_org_id                       numeric,
                                                           p_includepostingtypestatistical character,
                                                           IN p_ExcludePostingTypeYearEnd  character)
;

CREATE OR REPLACE FUNCTION de_metas_acct.acctbalanceuntildate(p_account_id                    numeric,
                                                              p_c_acctschema_id               numeric,
                                                              p_dateacct                      date,
                                                              ad_org_id                       numeric,
                                                              p_includepostingtypestatistical character DEFAULT 'N'::bpchar,
                                                              p_ExcludePostingTypeYearEnd     character DEFAULT 'N'::bpchar)
    RETURNS de_metas_acct.BalanceAmt
AS
$BODY$
    -- NOTE: we use COALESCE(SUM(..)) just to make sure we are not returning null
SELECT ROW (SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
FROM ((SELECT (CASE
    -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                   WHEN ev.AccountType IN ('E', 'R') AND fas.DateAcct >= DATE_TRUNC('year', $3) THEN ROW (fas.AmtAcctDr_YTD - fas.AmtAcctCr_YTD, fas.AmtAcctDr_YTD, fas.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                   WHEN ev.AccountType IN ('E', 'R')                                            THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
    -- For any other account => we consider from the beginning to Date amount
                                                                                                ELSE ROW (fas.AmtAcctDr - fas.AmtAcctCr, fas.AmtAcctDr, fas.AmtAcctCr)::de_metas_acct.BalanceAmt
               END) AS Balance
       FROM Fact_Acct_Summary fas
                INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fas.Account_ID)
       WHERE TRUE
         AND fas.Account_ID = $1      -- p_Account_ID
         AND fas.C_AcctSchema_ID = $2 -- p_C_AcctSchema_ID
         AND fas.PostingType = 'A'
         AND fas.PA_ReportCube_ID IS NULL
         AND fas.DateAcct < $3        -- p_DateAcct
         AND fas.ad_org_id = $4
       ORDER BY fas.DateAcct DESC
       LIMIT 1)
      -- Include posting type year end
      UNION ALL
      (SELECT (CASE
          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                   WHEN ev.AccountType IN ('E', 'R') AND fas.DateAcct >= DATE_TRUNC('year', $3) THEN ROW (fas.AmtAcctDr_YTD - fas.AmtAcctCr_YTD, fas.AmtAcctDr_YTD, fas.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                   WHEN ev.AccountType IN ('E', 'R')                                            THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
          -- For any other account => we consider from the beginning to Date amount
                                                                                                ELSE ROW (fas.AmtAcctDr - fas.AmtAcctCr, fas.AmtAcctDr, fas.AmtAcctCr)::de_metas_acct.BalanceAmt
               END) AS Balance
       FROM Fact_Acct_Summary fas
                INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fas.Account_ID)
       WHERE TRUE
         AND fas.Account_ID = $1      -- p_Account_ID
         AND fas.C_AcctSchema_ID = $2 -- p_C_AcctSchema_ID
         AND fas.PostingType = 'Y'
         AND fas.PA_ReportCube_ID IS NULL
         AND fas.DateAcct <= $3       -- p_DateAcct
         AND fas.ad_org_id = $4       -- p_AD_Org_ID
         AND $6 = 'N'
       ORDER BY fas.DateAcct DESC
       LIMIT 1)
      -- For PostingType=Statistical
      UNION ALL
      (SELECT (CASE
          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                   WHEN ev.AccountType IN ('E', 'R') AND fas.DateAcct >= DATE_TRUNC('year', $3) THEN ROW (fas.AmtAcctDr_YTD - fas.AmtAcctCr_YTD, fas.AmtAcctDr_YTD, fas.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                   WHEN ev.AccountType IN ('E', 'R')                                            THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
          -- For any other account => we consider from the beginning to Date amount
                                                                                                ELSE ROW (fas.AmtAcctDr - fas.AmtAcctCr, fas.AmtAcctDr, fas.AmtAcctCr)::de_metas_acct.BalanceAmt
               END) AS Balance
       FROM Fact_Acct_Summary fas
                INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fas.Account_ID)
       WHERE TRUE
         AND $5 = 'Y'                 -- p_IncludePostingTypeStatistical
         AND fas.Account_ID = $1      -- p_Account_ID
         AND fas.C_AcctSchema_ID = $2 -- p_C_AcctSchema_ID
         AND fas.PostingType = 'S'
         AND fas.PA_ReportCube_ID IS NULL
         AND fas.DateAcct < $3        -- p_DateAcct
         AND fas.ad_org_id = $4
       ORDER BY fas.DateAcct DESC
       LIMIT 1)
      -- Default value:
      UNION ALL
      (SELECT ROW (0, 0, 0)::de_metas_acct.BalanceAmt)) t
$BODY$
    LANGUAGE sql STABLE
                 COST 100
;
