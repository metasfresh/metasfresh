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