DROP FUNCTION IF EXISTS report.tax_accounting_report_details(IN p_dateFrom    date,
                                                             IN p_dateTo      date,
                                                             IN p_vatcode     varchar,
                                                             IN p_account_id  numeric,
                                                             IN p_c_tax_id    numeric,
                                                             IN p_org_id      numeric,
                                                             IN p_ad_language character varying(6))
;

CREATE OR REPLACE FUNCTION report.tax_accounting_report_details(IN p_dateFrom    date,
                                                                IN p_dateTo      date,
                                                                IN p_vatcode     varchar,
                                                                IN p_account_id  numeric,
                                                                IN p_c_tax_id    numeric,
                                                                IN p_org_id      numeric,
                                                                IN p_ad_language character varying(6))
    RETURNS TABLE
            (
                vatcode          character varying(10),
                kontono          character varying(40),
                kontoname        character varying(60),
                dateacct         date,
                documentno       character varying(40),
                taxname          character varying(60),
                taxrate          numeric,
                bpName           character varying,
                taxbaseamt       numeric,
                taxamt           numeric,
                taxamtperaccount numeric,
                IsTaxLine        character(1),
                iso_code         character(3),
                param_startdate  date,
                param_enddate    date,
                param_konto      character varying,
                param_vatcode    character varying,
                param_org        character varying,
                C_Tax_ID         numeric,
                account_id       numeric,
                ad_org_id        numeric
            )
AS
$$

SELECT DISTINCT ON (x.vatcode, x.kontono, x.kontoname, x.dateacct, x.documentno, x.taxname, x.taxrate, x.bpName, x.IsTaxLine, x.iso_code) x.vatcode,
                                                                                                                                          x.kontono,
                                                                                                                                          x.kontoname,
                                                                                                                                          x.dateacct :: date,
                                                                                                                                          x.documentno,
                                                                                                                                          x.taxname,
                                                                                                                                          x.taxrate,
                                                                                                                                          x.bpName,
                                                                                                                                          (COALESCE(x.inv_baseamt, x.gl_baseamt, 0::numeric) + COALESCE(x.alloc_baseamt, 0::numeric)) AS taxbaseamt,
                                                                                                                                          (COALESCE(x.inv_taxamt, x.gl_taxamt, 0::numeric) + COALESCE(x.alloc_taxamt, 0 :: numeric))  AS taxamt,
                                                                                                                                          taxamtperaccount,
                                                                                                                                          IsTaxLine,
                                                                                                                                          x.iso_code,
                                                                                                                                          x.p_dateFrom                                                                                AS param_startdate,
                                                                                                                                          x.p_dateTo                                                                                  AS param_endtdate,
                                                                                                                                          x.param_konto,
                                                                                                                                          x.param_vatcode,
                                                                                                                                          x.param_org,
                                                                                                                                          x.C_Tax_ID,
                                                                                                                                          x.account_id,
                                                                                                                                          x.ad_org_id
FROM (SELECT kontono,
             kontoname,
             fa.dateacct,
             fa.documentno,

             taxname,
             taxrate,

             bpName,

             inv_baseamt,
             gl_baseamt,
             alloc_baseamt,

             inv_taxamt,
             gl_taxamt,
             alloc_taxamt,

             taxamtperaccount,

             iso_code,

             IsTaxLine,

             vatcode,
             p_dateFrom,
             p_dateTo,
             (CASE
                  WHEN p_account_id IS NULL
                      THEN NULL
                      ELSE (SELECT value || ' - ' || name FROM C_ElementValue WHERE C_ElementValue_ID = p_account_id)
              END)     AS param_konto,
             p_vatcode AS param_vatcode,
             (CASE
                  WHEN p_org_id IS NULL
                      THEN NULL
                      ELSE (SELECT name FROM ad_org WHERE ad_org_id = p_org_id)
              END)     AS param_org,
             fa.C_Tax_ID,
             fa.account_id,
             fa.ad_org_id

      FROM de_metas_acct.tax_accounting_details_v fa
      WHERE fa.DateAcct >= p_dateFrom
        AND fa.DateAcct <= p_dateTo
        AND fa.postingtype IN ('A', 'Y')
        AND fa.ad_org_id = p_org_id
        AND (CASE
                 WHEN p_vatcode IS NULL
                     THEN TRUE
                     ELSE fa.VatCode = p_vatcode
             END)
        AND (CASE
                 WHEN p_account_id IS NULL
                     THEN TRUE
                     ELSE p_account_id = fa.account_id
             END)
        AND (CASE
                 WHEN p_c_tax_id IS NULL
                     THEN (fa.C_Tax_id IS NULL)
                     ELSE p_c_tax_id = fa.C_Tax_id
             END)
        AND fa.isActive = 'Y') x

ORDER BY x.vatcode, x.kontono, x.kontoname, x.dateacct, x.documentno, x.taxname, x.taxrate, x.bpName
$$
    LANGUAGE sql
    STABLE
;



--
--
-- Test
/*
SELECT *
FROM report.tax_accounting_report_details(p_dateFrom := '01-08-2022',
                                          p_dateTo := '31-12-2022',
                                          p_vatcode := NULL,
                                          p_account_id := (SELECT ev.c_elementvalue_id FROM c_elementvalue ev WHERE ev.value = '1170' AND c_element_id = 1000001),
                                          p_c_tax_id := 540006,
                                          p_org_id := 1000000,
                                          p_ad_language := 'de_DE')
WHERE documentno IN ('1007899')
;

 */