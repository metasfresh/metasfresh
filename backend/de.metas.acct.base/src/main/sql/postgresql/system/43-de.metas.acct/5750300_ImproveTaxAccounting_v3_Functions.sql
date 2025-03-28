DROP FUNCTION IF EXISTS de_metas_acct.taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                             p_DateFrom  date,
                                                             p_DateTo    date)
;


CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                                p_DateFrom  date,
                                                                p_DateTo    date)
    RETURNS TABLE
            (
                Balance            numeric,
                BalanceYear        numeric,
                invoice_total_sum  numeric,
                taxbaseamt_vat_sum numeric,
                taxName            varchar,
                vatcode            varchar
            )
AS
$BODY$
SELECT SUM(t.balance)                 AS balance,
       SUM(t.balanceyear)             AS balanceyear,

       SUM(sm.taxbaseamt + sm.taxamt) AS invoice_total_sum,
       SUM(sm.taxbaseamt)             AS taxbaseamt_vat_sum,
       t.taxname,
       t.vatcode
FROM (SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
      FROM C_Tax_Acct ta
               INNER JOIN C_ValidCombination vc ON (vc.C_ValidCombination_ID IN
                                                    (ta.T_Due_Acct, ta.T_Credit_Acct))) AS ev
         INNER JOIN de_metas_acct.taxaccounts_details(p_AD_Org_ID,
                                                      ev.C_ElementValue_ID,
                                                      NULL,
                                                      p_DateFrom,
                                                      p_DateTo) AS t ON TRUE
         INNER JOIN de_metas_acct.tax_accounting_report_details_sum(p_DateFrom,
                                                                    p_DateTo,
                                                                    t.vatcode,
                                                                    ev.C_ElementValue_ID,
                                                                    t.C_Tax_ID,
                                                                    p_AD_Org_ID) sm ON TRUE
WHERE t.taxname IS NOT NULL
GROUP BY t.vatcode, t.taxname
ORDER BY vatcode
$BODY$
    LANGUAGE sql STABLE
;


DROP FUNCTION IF EXISTS de_metas_acct.tax_accounting_report_details_sum(IN p_dateFrom   date,
                                                                        IN p_dateTo     date,
                                                                        IN p_vatcode    varchar,
                                                                        IN p_account_id numeric,
                                                                        IN p_c_tax_id   numeric,
                                                                        IN p_org_id     numeric)
;

CREATE OR REPLACE FUNCTION de_metas_acct.tax_accounting_report_details_sum(IN p_dateFrom   date,
                                                                           IN p_dateTo     date,
                                                                           IN p_vatcode    varchar,
                                                                           IN p_account_id numeric,
                                                                           IN p_c_tax_id   numeric,
                                                                           IN p_org_id     numeric)
    RETURNS TABLE
            (
                vatcode          character varying,
                kontono          character varying,
                kontoname        character varying,
                taxname          character varying,
                taxrate          numeric,
                taxbaseamt       numeric,
                taxamt           numeric,
                taxamtperaccount numeric,
                C_Tax_ID         numeric,
                ad_org_id        numeric
            )
    LANGUAGE plpgsql
AS
$$
BEGIN

    RETURN QUERY SELECT y.vatcode,
                        y.kontono,
                        y.kontoname,
                        y.taxname,
                        y.taxrate,
                        SUM(currencyconvert(y.taxbaseamt, y.c_currency_id, aas.c_currency_id, y.dateacct, NULL, y.ad_client_id, y.ad_org_id))       AS taxbaseamt,
                        SUM(currencyconvert(y.taxamt, y.c_currency_id, aas.c_currency_id, y.dateacct, NULL, y.ad_client_id, y.ad_org_id))           AS taxamt,
                        SUM(currencyconvert(y.taxamtperaccount, y.c_currency_id, aas.c_currency_id, y.dateacct, NULL, y.ad_client_id, y.ad_org_id)) AS taxamtperaccount,
                        y.C_Tax_ID,
                        y.ad_org_id
                 FROM (
                          SELECT x.vatcode,
                                 x.kontono,
                                 x.kontoname,
                                 x.taxname,
                                 x.taxrate,
                                 (COALESCE(x.inv_baseamt, x.gl_baseamt, x.sap_gl_baseamt, 0::numeric) + COALESCE(x.alloc_baseamt, 0::numeric)) AS taxbaseamt,
                                 (COALESCE(x.inv_taxamt, x.gl_taxamt, x.sap_gl_taxamt, 0::numeric) + COALESCE(x.alloc_taxamt, 0 :: numeric))  AS taxamt,
                                 x.taxamtperaccount                                                                          AS taxamtperaccount,
                                 x.dateacct,
                                 x.c_currency_id,
                                 x.C_Tax_ID,
                                 x.ad_org_id,
                                 x.ad_client_id
                          FROM (
                                   SELECT fa.kontono,
                                          fa.kontoname,
                                          fa.taxname,
                                          fa.taxrate,
                                          fa.dateacct,
                                          fa.inv_baseamt,
                                          fa.gl_baseamt,
                                          fa.sap_gl_baseamt,
                                          fa.alloc_baseamt,
                                          fa.inv_taxamt,
                                          fa.gl_taxamt,
                                          fa.sap_gl_taxamt,
                                          fa.alloc_taxamt,
                                          fa.taxamtperaccount,
                                          fa.c_currency_id,

                                          fa.vatcode     AS vatcode,
                                          fa.C_Tax_ID,
                                          fa.ad_org_id,
                                          fa.ad_client_id

                                   FROM de_metas_acct.tax_accounting_details_v fa
                                   WHERE fa.DateAcct >= p_dateFrom
                                     AND fa.DateAcct <= p_dateTo
                                     AND fa.postingtype IN ('A', 'Y')
                                     AND fa.ad_org_id = p_org_id
                                     AND (p_vatcode IS NULL OR fa.VatCode = p_vatcode)
                                     AND (p_account_id IS NULL OR p_account_id = fa.account_id)
                                     AND (p_c_tax_id IS NULL OR p_c_tax_id = fa.C_Tax_id)
                                     AND fa.isActive = 'Y'
                               ) x
                      ) Y
                          JOIN c_acctschema aas ON Y.
                                                       ad_org_id = aas.ad_orgonly_id
                 GROUP BY y.vatcode,
                          y.kontono,
                          y.kontoname,
                          y.taxname,
                          y.taxrate,
                          y.C_Tax_ID,
                          y.ad_org_id
                 ORDER BY y.vatcode, y.kontono, y.kontoname, y.taxname, y.taxrate;

END;
$$
;


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