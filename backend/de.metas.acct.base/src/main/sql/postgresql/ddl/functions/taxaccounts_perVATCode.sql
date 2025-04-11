DROP FUNCTION IF EXISTS de_metas_acct.report_taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                             p_DateFrom  date,
                                                             p_DateTo    date)
;


CREATE OR REPLACE FUNCTION de_metas_acct.report_taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                                p_DateFrom  date,
                                                                p_DateTo    date)
    RETURNS TABLE
            (
                Balance            numeric,
                BalanceYear        numeric,
                currency           varchar,
                source_currency    varchar,
                invoice_total_sum  numeric,
                taxbaseamt_vat_sum numeric,
                taxName            varchar,
                vatcode            varchar
            )
AS
$BODY$
SELECT SUM(CurrentBalance)       AS balance,
       SUM(balance_one_year_ago) AS balanceyear,
       currency,
       source_currency,
       (CASE
            WHEN Currency = source_currency THEN
                SUM(TotalWithoutVAT + CurrentBalance)
                                            ELSE NULL
        END)                     AS invoice_total_sum,
       SUM(TotalWithoutVAT)      AS taxbaseamt_vat_sum,
       Taxname,
       vatcode
FROM de_metas_acct.report_taxaccounts(p_AD_Org_ID,
                                      NULL,
                                      NULL,
                                      p_DateFrom,
                                      p_DateTo,
                                      'N'::bpchar,
                                      '3',
                                      NULL)
GROUP BY vatcode, currency, source_currency, Taxname
ORDER BY vatcode
$BODY$
    LANGUAGE sql STABLE
;