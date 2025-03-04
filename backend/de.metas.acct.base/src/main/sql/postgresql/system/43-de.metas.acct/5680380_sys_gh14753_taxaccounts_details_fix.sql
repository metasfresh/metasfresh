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
     balanceRecords_dateTo AS
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
                                                       (p_DateFrom - INTERVAL '1 day')::date,
                                                       p_C_Vat_Code_ID) AS b ON 1 = 1
         ),
     balanceRecords_yearBegining AS
         (
             SELECT b.*, a.C_ElementValue_ID
             FROM accounts a
                      JOIN de_metas_acct.balanceToDate(p_AD_Org_ID,
                                                       C_ElementValue_ID,
                                                       (DATE_TRUNC('year', p_DateTo::date) - INTERVAL '1 day')::date,
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
             FROM balanceRecords_dateTo b2
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