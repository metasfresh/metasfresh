DROP FUNCTION IF EXISTS de_metas_acct.tax_accounting_report_details_sum(IN p_dateFrom   date,
                                                                        IN p_dateTo     date,
                                                                        IN p_vatcode    varchar,
                                                                        IN p_account_id numeric,
                                                                        IN p_c_tax_id   numeric,
                                                                        IN p_org_id     numeric)
;



CREATE OR REPLACE FUNCTION de_metas_acct.tax_accounting_report_details_sum(p_datefrom   date,
                                                                           p_dateto     date,
                                                                           p_vatcode    character varying,
                                                                           p_account_id numeric,
                                                                           p_c_tax_id   numeric,
                                                                           p_org_id     numeric)
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
                c_tax_id         numeric,
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
                                 (COALESCE(x.inv_baseamt, x.gl_baseamt, 0::numeric) + COALESCE(x.alloc_baseamt, 0::numeric)) AS taxbaseamt,
                                 (COALESCE(x.inv_taxamt, x.gl_taxamt, 0::numeric) + COALESCE(x.alloc_taxamt, 0 :: numeric))  AS taxamt,
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
                                          fa.alloc_baseamt,
                                          fa.inv_taxamt,
                                          fa.gl_taxamt,
                                          fa.alloc_taxamt,
                                          fa.taxamtperaccount,
                                          fa.c_currency_id,
                                          fa.vatcode AS vatcode,
                                          fa.C_Tax_ID,
                                          fa.ad_org_id,
                                          fa.ad_client_id

                                   FROM de_metas_acct.tax_accounting_details_v fa
                                   WHERE fa.DateAcct >= p_dateFrom
                                     AND fa.DateAcct <= p_dateTo
                                     AND fa.postingtype IN ('A', 'Y')
                                     AND fa.ad_org_id = p_org_id
                                     AND (CASE
                                              WHEN p_vatcode IS NULL
                                                  THEN fa.VatCode IS NULL
                                                  ELSE fa.VatCode = p_vatcode
                                          END)
                                     AND (CASE
                                              WHEN p_account_id IS NULL
                                                  THEN TRUE
                                                  ELSE p_account_id = fa.account_id
                                          END)
                                     AND (CASE
                                              WHEN p_c_tax_id IS NULL
                                                  THEN TRUE
                                                  ELSE p_c_tax_id = fa.C_Tax_id
                                          END)
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