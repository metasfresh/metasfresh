DROP FUNCTION IF EXISTS de_metas_acct.balanceToDate(p_AD_Org_ID     numeric(10, 0),
                                                    p_Account_ID    numeric,
                                                    p_DateAcct      date,
                                                    p_C_Vat_Code_ID numeric)
;

CREATE OR REPLACE FUNCTION de_metas_acct.balanceToDate(p_AD_Org_ID     numeric(10, 0),
                                                       p_Account_ID    numeric,
                                                       p_DateAcct      date,
                                                       p_C_Vat_Code_ID numeric)
    RETURNS TABLE
            (
                vatcode     varchar,
                C_Tax_ID    numeric,
                accountNo   varchar,
                accountName varchar,
                Balance     de_metas_acct.BalanceAmt
            )
AS
$BODY$
WITH records AS
         (
             WITH filteredRecords AS (
                 SELECT fa.AD_Client_ID
                      , fa.AD_Org_ID
                      , fa.Account_ID
                      , fa.C_Tax_ID
                      , fa.vatcode
                      , fa.C_AcctSchema_ID
                      , fa.PostingType
                      , fa.DateAcct
                      -- Aggregated amounts: (beginning) to Date
                      , SUM(AmtAcctDr) OVER facts_ToDate     AS AmtAcctDr
                      , SUM(AmtAcctCr) OVER facts_ToDate     AS AmtAcctCr
                      , SUM(Qty) OVER facts_ToDate           AS Qty
                      -- Aggregated amounts: Year to Date
                      , SUM(AmtAcctDr) OVER facts_YearToDate AS AmtAcctDr_YTD
                      , SUM(AmtAcctCr) OVER facts_YearToDate AS AmtAcctCr_YTD

                 FROM MV_Fact_Acct_Sum fa
                     WINDOW
                         facts_ToDate AS (PARTITION BY fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Tax_ID, fa.vatcode ORDER BY fa.DateAcct)
                         , facts_YearToDate AS (PARTITION BY fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Tax_ID
                         , fa.vatcode, fa.C_Year_ID ORDER BY fa.DateAcct)
             )
             SELECT --
                    fa.PostingType,
                    ev.AccountType,
                    fa.AmtAcctCr,
                    fa.AmtAcctCr_YTD,
                    fa.AmtAcctDr,
                    fa.AmtAcctDr_YTD,
                    fa.DateAcct,
                    fa.c_tax_id,
                    fa.vatcode,
                    ev.value AS AccountNo,
                    ev.Name  AS AccountName
             FROM filteredRecords fa
                      INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fa.Account_ID)
                      LEFT OUTER JOIN C_Vat_Code vat ON vat.C_Vat_Code_ID = p_C_Vat_Code_ID AND vat.c_tax_id = fa.c_tax_id
             WHERE fa.DateAcct <= p_DateAcct
               AND fa.account_id = p_Account_ID
               AND fa.ad_org_id = p_AD_Org_ID
               AND (CASE WHEN p_C_Vat_Code_ID IS NULL THEN TRUE ELSE vat.vatcode = fa.VatCode END)
         )
SELECT vatcode,
       C_Tax_ID,
       AccountNo,
       AccountName,
       ROW (SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
FROM (
         (
             SELECT *
             FROM (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= DATE_TRUNC('year', p_DateAcct)
                                                                    THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                                                    ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                              END)                        AS Balance,
                             AccountNo,
                             AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct DESC ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        AND fo.PostingType = 'A'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) AS a1
             WHERE RowNo = 1
         )

         -- Include posting type Year End
         UNION ALL
         (
             SELECT *
             FROM (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= DATE_TRUNC('year', p_DateAcct)
                                                                    THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                                                    ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                              END)                        AS Balance,
                             AccountNo,
                             AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct DESC ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        -- AND $6 = 'N' -- p_ExcludePostingTypeYearEnd
                        AND fo.PostingType = 'Y'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) AS a2
             WHERE RowNo = 1
         )

         -- Include posting type Statistical
         UNION ALL
         (
             SELECT *
             FROM (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= DATE_TRUNC('year', p_DateAcct)
                                                                    THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                                                    ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                              END)                        AS Balance,
                             AccountNo,
                             AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct DESC ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        -- AND $5 = 'Y' -- p_IncludePostingTypeStatistical
                        AND fo.PostingType = 'S'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) AS a3
             WHERE RowNo = 1
         )
     ) a
GROUP BY vatcode, C_Tax_ID, AccountNo, AccountName
$BODY$
    LANGUAGE sql STABLE
;