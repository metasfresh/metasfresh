CREATE OR REPLACE FUNCTION de_metas_acct.taxaccountsonly_details(
    p_ad_org_id     numeric,
    p_account_id    numeric,
    p_c_vat_code_id numeric,
    p_datefrom      date,
    p_dateto        date
)
    RETURNS TABLE
            (
                balance           numeric,
                balanceyear       numeric,
                taxbaseamt        numeric,
                accountno         varchar,
                accountname       varchar,
                taxname           varchar,
                c_tax_id          numeric,
                vatcode           varchar,
                c_elementvalue_id numeric,
                param_startdate   date,
                param_enddate     date,
                param_konto       varchar,
                param_vatcode     varchar,
                param_org         varchar,
                currency          character
            )
    STABLE
    LANGUAGE sql
AS
$$
WITH ev AS (
    SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
    FROM C_Tax_Acct ta
             INNER JOIN C_ValidCombination vc
                        ON vc.C_ValidCombination_ID IN (ta.T_Due_Acct, ta.T_Credit_Acct)
    WHERE p_Account_ID IS NULL OR vc.Account_ID = p_Account_ID
),
     vcodes AS (
         SELECT v.C_VAT_Code_ID, v.vatcode
         FROM C_VAT_Code v
         WHERE p_c_vat_code_id IS NULL OR v.C_VAT_Code_ID = p_c_vat_code_id

         UNION ALL

         SELECT NULL::numeric AS C_VAT_Code_ID, NULL::varchar AS vatcode
         WHERE p_c_vat_code_id IS NULL  -- include NULL vatcode if no filter is applied
     )
SELECT
    COALESCE(t.balance, 0) AS balance,
    COALESCE(t.balanceyear, 0) AS balanceyear,
    COALESCE(s.taxbaseamt, 0) AS taxbaseamt,
    t.accountno,
    t.accountname,
    t.taxname,
    t.C_Tax_ID,
    v.vatcode,
    ev.C_ElementValue_ID,
    t.param_startdate,
    t.param_enddate,
    CASE
        WHEN p_Account_ID IS NULL THEN NULL
                                  ELSE (
                                      SELECT value || ' - ' || name
                                      FROM C_ElementValue
                                      WHERE C_ElementValue_ID = p_Account_ID
                                  )
    END AS param_konto,
    t.param_vatcode,
    t.param_org,
    c.iso_code AS currency
FROM ev
         CROSS JOIN vcodes v
         LEFT JOIN LATERAL de_metas_acct.taxaccounts_details(
        p_ad_org_id,
        ev.C_ElementValue_ID,
        v.C_VAT_Code_ID,
        p_datefrom,
        p_dateto
                   ) AS t ON t.vatcode IS NOT DISTINCT FROM v.vatcode

         LEFT JOIN LATERAL  de_metas_acct.tax_accounting_report_details_sum(
        p_datefrom,
        p_dateto,
        v.vatcode,
        ev.C_ElementValue_ID,
        t.C_Tax_ID,
        p_ad_org_id
                   ) AS s ON s.vatcode IS NOT DISTINCT FROM v.vatcode
         JOIN c_acctschema a ON a.ad_orgonly_id = p_ad_org_id
         JOIN c_currency c ON c.c_currency_id = a.c_currency_id
ORDER BY v.vatcode, t.accountno;
$$;