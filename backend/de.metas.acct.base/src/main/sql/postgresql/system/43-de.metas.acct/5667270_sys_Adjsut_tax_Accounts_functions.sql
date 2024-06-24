DROP FUNCTION IF EXISTS de_metas_acct.taxaccounts_details(p_AD_Org_ID     numeric(10, 0),
                                                          p_Account_ID    numeric,
                                                          p_C_Vat_Code_ID numeric,
                                                          p_DateFrom      date,
                                                          p_DateTo        date)
;

CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_details(p_AD_Org_ID     numeric(10, 0),
                                                             p_Account_ID    numeric,
                                                             p_C_Vat_Code_ID numeric,
                                                             p_DateFrom      date,
                                                             p_DateTo        date)
    RETURNS TABLE
            (
                Balance           numeric,
                BalanceYear       numeric,
                accountNo         varchar,
                accountName       varchar,
                taxName           varchar,
                C_Tax_ID          numeric,
                vatcode           varchar,
                C_ElementValue_ID numeric,
                param_startdate   date,
                param_enddate     date,
                param_konto       varchar,
                param_vatcode     varchar,
                param_org         varchar
            )
AS
$BODY$
WITH accounts AS
         (
             SELECT C_ElementValue_ID
             FROM c_elementvalue
             WHERE p_Account_ID IS NULL
                OR C_ElementValue_ID = p_Account_ID
         ),
     balanceRecords AS
         (
             SELECT b.*, a.C_ElementValue_ID
             FROM accounts a
                      JOIN de_metas_acct.balanceToDate(p_AD_Org_ID,
                                                       C_ElementValue_ID,
                                                       p_DateTo,
                                                       p_C_Vat_Code_ID) AS b ON 1 = 1
         ),
     balanceRecords_dateFrom AS
         (
             SELECT b.*, a.C_ElementValue_ID
             FROM accounts a
                      JOIN de_metas_acct.balanceToDate(p_AD_Org_ID,
                                                       C_ElementValue_ID,
                                                       p_DateFrom,
                                                       p_C_Vat_Code_ID) AS b ON 1 = 1
         ),
     balanceRecords_yearBegining AS
         (
             SELECT b.*, a.C_ElementValue_ID
             FROM accounts a
                      JOIN de_metas_acct.balanceToDate(p_AD_Org_ID,
                                                       C_ElementValue_ID,
                                                       DATE_TRUNC('year', p_DateTo::date)::date,
                                                       p_C_Vat_Code_ID) AS b ON 1 = 1
         ),
     balance AS
         (
             SELECT (COALESCE((b2.Balance).Balance, 0) - COALESCE((b1.Balance).Balance, 0)) AS Balance,
                    (COALESCE((b2.Balance).Balance, 0) - COALESCE((by.Balance).Balance, 0)) AS YearBalance,
                    b2.accountno,
                    b2.accountname,
                    b2.C_Tax_ID,
                    b2.vatcode,
                    b2.C_ElementValue_ID
             FROM balanceRecords b2
                      LEFT JOIN balanceRecords_dateFrom b1 ON b1.c_elementvalue_id = b2.c_elementvalue_id
                 AND b1.accountno = b2.accountno
                 AND b1.accountname = b2.accountname
                 AND COALESCE(b1.vatcode, '') = COALESCE(b2.vatcode, '')
                 AND COALESCE(b1.c_tax_id, 0) = COALESCE(b2.c_tax_id, 0)

                      LEFT JOIN balanceRecords_yearBegining by ON by.c_elementvalue_id = b2.c_elementvalue_id
                 AND by.accountno = b2.accountno
                 AND by.accountname = b2.accountname
                 AND COALESCE(by.vatcode, '') = COALESCE(b2.vatcode, '')
                 AND COALESCE(by.c_tax_id, 0) = COALESCE(b2.c_tax_id, 0)
             WHERE (b2.Balance).Debit <> 0
                OR (b2.Balance).Credit <> 0
         )
SELECT Balance,
       YearBalance,
       b.accountno,
       b.accountname,
       t.Name     AS taxName,
       b.C_Tax_ID,
       b.vatcode,
       b.C_ElementValue_ID,
       p_DateFrom AS param_startdate,
       p_DateTo   AS param_enddate,
       (CASE
            WHEN p_Account_ID IS NULL
                THEN NULL
                ELSE (SELECT value || ' - ' || name
                      FROM C_ElementValue
                      WHERE C_ElementValue_ID = p_Account_ID
                )
        END)      AS param_konto,
       (CASE
            WHEN p_C_Vat_Code_ID IS NULL
                THEN NULL
                ELSE (SELECT vatcode
                      FROM C_Vat_Code
                      WHERE C_Vat_Code_ID = p_C_Vat_Code_ID
                )
        END)      AS param_vatcode,
       (CASE
            WHEN p_AD_Org_ID IS NULL
                THEN NULL
                ELSE (SELECT name
                      FROM ad_org
                      WHERE ad_org_id = p_AD_Org_ID
                )
        END)      AS param_org

FROM balance AS b
         LEFT OUTER JOIN C_Tax t ON t.C_tax_ID = b.C_tax_ID
ORDER BY vatcode, accountno
$BODY$
    LANGUAGE sql STABLE
;



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




CREATE OR REPLACE FUNCTION de_metas_acct.taxaccountsonly_details(p_AD_Org_ID     numeric(10, 0),
                                                                 p_Account_ID    numeric,
                                                                 p_C_Vat_Code_ID numeric,
                                                                 p_DateFrom      date,
                                                                 p_DateTo        date)
    RETURNS TABLE
            (
                Balance           numeric,
                BalanceYear       numeric,
                accountNo         varchar,
                accountName       varchar,
                taxName           varchar,
                C_Tax_ID          numeric,
                vatcode           varchar,
                C_ElementValue_ID numeric,
                param_startdate   date,
                param_enddate     date,
                param_konto       varchar,
                param_vatcode     varchar,
                param_org         varchar
            )
AS
$BODY$

SELECT t.Balance,
       t.BalanceYear,
       t.accountno,
       t.accountname,
       t.taxName,
       t.C_Tax_ID,
       t.vatcode,
       t.C_ElementValue_ID,
       t.param_startdate,
       t.param_enddate,
       (CASE
            WHEN p_Account_ID IS NULL
                THEN NULL
                ELSE (SELECT value || ' - ' || name
                      FROM C_ElementValue
                      WHERE C_ElementValue_ID = p_Account_ID)
        END) AS param_konto,
       t.param_vatcode,
       t.param_org

FROM (
         SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
         FROM C_Tax_Acct ta
                  INNER JOIN C_ValidCombination vc ON (vc.C_ValidCombination_ID IN
                                                       (ta.T_Due_Acct, ta.T_Credit_Acct))
         WHERE p_Account_ID IS NULL
            OR vc.Account_ID = p_Account_ID
     ) AS ev
         INNER JOIN de_metas_acct.taxaccounts_details(p_AD_Org_ID, ev.C_ElementValue_ID, p_C_Vat_Code_ID, p_DateFrom, p_DateTo) AS t ON TRUE
WHERE t.taxname IS NOT NULL
ORDER BY vatcode, accountno
    ;
$BODY$
    LANGUAGE sql STABLE
;
