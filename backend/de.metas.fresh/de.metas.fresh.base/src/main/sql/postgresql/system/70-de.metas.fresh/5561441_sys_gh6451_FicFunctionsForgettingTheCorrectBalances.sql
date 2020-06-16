DROP FUNCTION IF EXISTS de_metas_acct.balanceToDate(p_AD_Org_ID numeric(10, 0), p_Account_ID numeric,
    p_DateAcct date,
    p_C_Vat_Code_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_acct.balanceToDate(p_AD_Org_ID numeric(10, 0),
                                                       p_Account_ID numeric,
                                                       p_DateAcct date,
                                                       p_C_Vat_Code_ID numeric)
    RETURNS TABLE
            (
                vatcode     varchar,
                C_Tax_ID     numeric,
				accountNo   varchar,
				accountName varchar,
                Balance     de_metas_acct.BalanceAmt
            )
AS
$BODY$
WITH records AS
         (
             WITH filteredRecords AS (
                 with fact_records AS
                          (
                              select fa.AD_Client_ID
                                   , fa.AD_Org_ID
                                   , fa.Account_ID
                                   , fa.C_AcctSchema_ID
                                   , fa.PostingType
                                   , fa.DateAcct
                                   , fa.C_Tax_ID
                                   , fa.vatcode
                                   , p.C_Period_ID
                                   , p.C_Year_ID
                                   -- Aggregated amounts
                                   , COALESCE(SUM(AmtAcctDr), 0) as AmtAcctDr
                                   , COALESCE(SUM(AmtAcctCr), 0) as AmtAcctCr
                                   , COALESCE(SUM(Qty), 0)       as Qty
                              FROM Fact_Acct fa
                                       left outer join C_Period p on (p.C_Period_ID = fa.C_Period_ID)
                              GROUP BY fa.AD_Client_ID, fa.AD_Org_ID
                                     , p.C_Period_ID
                                     , fa.DateAcct
                                     , fa.C_AcctSchema_ID
                                     , fa.PostingType
                                     , fa.Account_ID
                                     , fa.C_Tax_ID
                                     , fa.vatcode
                          )
                 select fa.AD_Client_ID
                      , fa.AD_Org_ID
                      , fa.Account_ID
                      , fa.C_Tax_ID
                      , fa.vatcode
                      , fa.C_AcctSchema_ID
                      , fa.PostingType
                      , fa.DateAcct
                      -- Aggregated amounts: (beginning) to Date
                      , SUM(AmtAcctDr) over facts_ToDate     as AmtAcctDr
                      , SUM(AmtAcctCr) over facts_ToDate     as AmtAcctCr
                      , SUM(Qty) over facts_ToDate           as Qty
                      -- Aggregated amounts: Year to Date
                      , SUM(AmtAcctDr) over facts_YearToDate as AmtAcctDr_YTD
                      , SUM(AmtAcctCr) over facts_YearToDate as AmtAcctCr_YTD

                 from MV_Fact_Acct_Sum fa
                     window
                         facts_ToDate as (partition by fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Tax_ID, fa.vatcode order by fa.DateAcct)
                         , facts_YearToDate as (partition by fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Tax_ID
                         , fa.vatcode, fa.C_Year_ID order by fa.DateAcct)
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
					ev.value as AccountNo,
					ev.Name as AccountName
             FROM filteredRecords fa
                      INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fa.Account_ID) AND ev.isActive = 'Y'
					   LEFT OUTER JOIN C_Vat_Code vat on vat.C_Vat_Code_ID = p_C_Vat_Code_ID and vat.isActive = 'Y'
                              WHERE fa.DateAcct <= p_DateAcct
                                AND fa.account_id = p_Account_ID
                                AND fa.ad_org_id = p_AD_Org_ID
                                AND (CASE WHEN vat.vatcode IS NULL THEN TRUE ELSE vat.vatcode = fa.VatCode END)
         )
select vatcode,
       C_Tax_ID,  AccountNo, AccountName,
       ROW (SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
from (
         (
             select *
             from (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= date_trunc('year', p_DateAcct)
                                      THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                  ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                          END)                            AS Balance, AccountNo, AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct desc ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        AND fo.PostingType = 'A'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) as a1
             where RowNo = 1
         )

         -- Include posting type Year End
         UNION ALL
         (
             select *
             from (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= date_trunc('year', p_DateAcct)
                                      THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                  ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                          END)                            AS Balance, AccountNo, AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct desc ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        -- AND $6 = 'N' -- p_ExcludePostingTypeYearEnd
                        AND fo.PostingType = 'Y'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) as a2
             where RowNo = 1
         )

         -- Include posting type Statistical
         UNION ALL
         (
             select *
             from (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= date_trunc('year', p_DateAcct)
                                      THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                  ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                          END)                            AS Balance, AccountNo, AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct desc ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        -- AND $5 = 'Y' -- p_IncludePostingTypeStatistical
                        AND fo.PostingType = 'S'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) as a3
             where RowNo = 1
         )
     ) a
group by vatcode, C_Tax_ID, AccountNo, AccountName
$BODY$
    LANGUAGE sql STABLE;
	
	



DROP FUNCTION IF EXISTS de_metas_acct.taxaccounts_details(p_AD_Org_ID numeric(10, 0),
    p_Account_ID numeric,
    p_C_Vat_Code_ID numeric,
    p_DateFrom date,
    p_DateTo date);

CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_details(p_AD_Org_ID numeric(10, 0),
                                                             p_Account_ID numeric,
                                                             p_C_Vat_Code_ID numeric,
                                                             p_DateFrom date,
                                                             p_DateTo date)
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
with accounts as
         (
             select C_ElementValue_ID
             from c_elementvalue
             where IsActive = 'Y'
               AND CASE WHEN p_Account_ID IS NULL THEN 1 = 1 ELSE C_ElementValue_ID = p_Account_ID END
         ),
     balanceRecords AS
         (
             Select b.*, a.C_ElementValue_ID
             from accounts a
                      join de_metas_acct.balanceToDate(p_AD_Org_ID,
                                                       C_ElementValue_ID,
                                                       p_DateTo,
                                                       p_C_Vat_Code_ID) as b on 1 = 1
         ),
     balanceRecords_dateFrom AS
         (
             Select b.*, a.C_ElementValue_ID
             from accounts a
                      join de_metas_acct.balanceToDate(p_AD_Org_ID,
                                                       C_ElementValue_ID,
                                                       p_DateFrom,
                                                       p_C_Vat_Code_ID) as b on 1 = 1
         ),
     balance AS
         (
             Select (coalesce((b2.Balance).Balance, 0) - coalesce((b1.Balance).Balance, 0)) as Balance,
                    coalesce((b2.Balance).Balance, 0)                                       as YearBalance,
                    b2.accountno,
                    b2.accountname,
                    b2.C_Tax_ID,
                    b2.vatcode,
                    b2.C_ElementValue_ID
             from balanceRecords b2
                      left join balanceRecords_dateFrom b1 on b1.c_elementvalue_id = b2.c_elementvalue_id
                 and b1.accountno = b2.accountno
                 and b1.accountname = b2.accountname
                 and coalesce(b1.vatcode, '') = coalesce(b2.vatcode, '')
                 and coalesce(b1.c_tax_id, 0) = coalesce(b2.c_tax_id, 0)
             where (b2.Balance).Debit <> 0
                or (b2.Balance).Credit <> 0
         )
select Balance,
       YearBalance,
       b.accountno,
       b.accountname,
       t.Name                                as taxName,
       b.C_Tax_ID,
       b.vatcode,
       b.C_ElementValue_ID,
       p_DateFrom                            AS param_startdate,
       p_DateTo                              AS param_enddate,
       (CASE
            WHEN p_Account_ID IS NULL
                THEN NULL
            ELSE (SELECT value || ' - ' || name
                  from C_ElementValue
                  WHERE C_ElementValue_ID = p_Account_ID
                    and isActive = 'Y') END) AS param_konto,
       (CASE
            WHEN p_C_Vat_Code_ID IS NULL
                THEN NULL
            ELSE (SELECT vatcode
                  FROM C_Vat_Code
                  WHERE C_Vat_Code_ID = p_C_Vat_Code_ID
                    and isActive = 'Y') END) AS param_vatcode,
       (CASE
            WHEN p_AD_Org_ID IS NULL
                THEN NULL
            ELSE (SELECT name
                  from ad_org
                  where ad_org_id = p_AD_Org_ID
                    and isActive = 'Y') END) AS param_org

from balance as b
         left outer join C_Tax t on t.C_tax_ID = b.C_tax_ID
order by vatcode, accountno
$BODY$
    LANGUAGE sql STABLE;