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
                vatcode          character varying(10),
                kontono          character varying(40),
                kontoname        character varying(60),
                taxname          character varying(60),
                taxrate          numeric,
                taxbaseamt       numeric,
                taxamt           numeric,
                taxamtperaccount numeric,
                C_Tax_ID         numeric,
                ad_org_id        numeric
            )
AS
$$
BEGIN

    RAISE INFO '-----------------------------------------';
    RAISE INFO 'New call.......';
    RAISE INFO 'param p_vatcode %', p_vatcode;
    RAISE INFO 'param p_account_id %', p_account_id;
    RAISE INFO 'param p_c_tax_id %', p_c_tax_id;

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
                                 (COALESCE(x.inv_baseamt, x.gl_baseamt, 0::numeric) + COALESCE(x.hdr_baseamt, 0::numeric)) AS taxbaseamt,
                                 (COALESCE(x.inv_taxamt, x.gl_taxamt) + COALESCE(x.hdr_taxamt, 0 :: numeric))              AS taxamt,
                                 x.taxamtperaccount                                                                        AS taxamtperaccount,
                                 x.dateacct,
                                 x.c_currency_id,
                                 x.C_Tax_ID,
                                 x.ad_org_id,
                                 x.ad_client_id
                          FROM (
                                   SELECT ev.value       AS kontono,
                                          ev.name        AS kontoname,
                                          tax.name       AS taxname,
                                          tax.rate       AS taxrate,
                                          fa.dateacct,
                                          i.taxbaseamt   AS inv_baseamt,
                                          gl.taxbaseamt  AS gl_baseamt,
                                          hdr.taxbaseamt AS hdr_baseamt,
                                          i.taxamt       AS inv_taxamt,
                                          gl.taxamt      AS gl_taxamt,
                                          hdr.taxamt     AS hdr_taxamt,

                                          (CASE
                                               WHEN fa.line_id IS NULL AND fa.C_Tax_id IS NOT NULL
                                                   THEN (fa.amtacctdr - fa.amtacctcr)
                                                   ELSE 0
                                           END)          AS taxamtperaccount,

                                          fa.c_currency_id,

                                          fa.vatcode     AS vatcode,
                                          fa.C_Tax_ID,
                                          fa.ad_org_id,
                                          fa.ad_client_id

                                   FROM public.fact_acct fa
                                            -- gh #489: explicitly select from public.fact_acct, bacause the function de_metas_acct.Fact_Acct_EndingBalance expects it.

                                            JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id AND ev.isActive = 'Y'
                                            JOIN c_tax tax ON fa.c_tax_id = tax.c_tax_id AND tax.isActive = 'Y'

                                            LEFT OUTER JOIN c_bpartner bp ON fa.c_bpartner_id = bp.c_bpartner_id

                                       --Show all accounts, not only tax accounts
                                            LEFT OUTER JOIN (SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
                                                             FROM C_Tax_Acct ta
                                                                      INNER JOIN C_ValidCombination vc ON (vc.C_ValidCombination_ID IN
                                                                                                           (ta.T_Liability_Acct,
                                                                                                            ta.T_Receivables_Acct, ta.T_Due_Acct,
                                                                                                            ta.T_Credit_Acct, ta.T_Expense_Acct))
                                                                 AND vc.isActive = 'Y'
                                                             WHERE ta.isActive = 'Y'
                                   ) ta ON ta.C_ElementValue_ID = ev.C_ElementValue_ID

                                       --if invoice
                                            LEFT OUTER JOIN
                                        (SELECT SUM(CASE
                                                        WHEN dt.docbasetype <> 'APC'
                                                            THEN inv_tax.taxbaseamt
                                                            ELSE (-1) * inv_tax.taxbaseamt
                                                    END) AS taxbaseamt,
                                                SUM(CASE
                                                        WHEN dt.docbasetype <> 'APC'
                                                            THEN inv_tax.taxamt
                                                            ELSE (-1) * inv_tax.taxamt
                                                    END) AS taxamt,
                                                i.c_invoice_id,
                                                inv_tax.c_tax_id
                                         FROM c_invoice i
                                                  JOIN C_InvoiceTax inv_tax ON i.c_invoice_id = inv_tax.c_invoice_id AND inv_tax.isActive = 'Y'
                                                  JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypeTarget_id
                                         WHERE i.isActive = 'Y'
                                         GROUP BY i.c_invoice_id,
                                                  inv_tax.c_tax_id
                                        ) i ON fa.record_id = i.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND
                                               i.c_tax_id = fa.c_tax_id

                                            --if gl journal
                                            LEFT OUTER JOIN (SELECT SUM(CASE
                                                                            WHEN gll.dr_autotaxaccount = 'Y'
                                                                                THEN gll.dr_taxbaseamt
                                                                            WHEN cr_autotaxaccount = 'Y'
                                                                                THEN gll.cr_taxbaseamt
                                                                        END)                               AS taxbaseamt,
                                                                    SUM(CASE
                                                                            WHEN gll.dr_autotaxaccount = 'Y'
                                                                                THEN gll.dr_taxamt
                                                                            WHEN cr_autotaxaccount = 'Y'
                                                                                THEN gll.cr_taxamt
                                                                        END)                               AS taxamt,
                                                                    gl.gl_journal_id,
                                                                    gll.gl_journalline_id,
                                                                    COALESCE(gll.dr_tax_id, gll.cr_tax_id) AS tax_id
                                                             FROM gl_journal gl
                                                                      JOIN GL_JournalLine gll
                                                                           ON gl.gl_journal_id = gll.gl_journal_id AND gll.isActive = 'Y'
                                                             WHERE gl.isActive = 'Y'
                                                             GROUP BY gl.gl_journal_id,
                                                                      gll.gl_journalline_id,
                                                                      COALESCE(gll.dr_tax_id, gll.cr_tax_id)
                                   ) gl ON fa.record_id = gl.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND
                                           gl.gl_journalline_id = fa.line_id AND gl.tax_id = fa.c_tax_id

                                       --if allocationHdr
                                            LEFT OUTER JOIN (
                                       SELECT hdr.C_AllocationHdr_ID,
                                              hdrl.C_AllocationLine_ID,
                                              SUM(hdrl.discountamt + hdrl.writeoffamt) ::numeric                                                                             AS taxbaseamt,
                                              SUM(ROUND((hdrl.discountamt + hdrl.writeoffamt) - ((hdrl.discountamt + hdrl.writeoffamt) * 100 / (100 + t.rate)), 2)::numeric) AS taxamt
                                       FROM C_AllocationHdr hdr
                                                JOIN C_AllocationLine hdrl
                                                     ON hdr.C_AllocationHdr_ID = hdrl.C_AllocationHdr_ID AND hdrl.isActive = 'Y'
                                                LEFT JOIN Fact_Acct fa ON hdrl.c_invoice_id = fa.record_id AND fa.ad_table_id = 318 AND fa.postingtype = 'A' AND Line_ID IS NULL AND fa.c_tax_id IS NOT NULL
                                                LEFT JOIN C_Tax t ON fa.c_tax_id = t.c_tax_id
                                       WHERE hdr.isActive = 'Y'
                                       GROUP BY hdr.C_AllocationHdr_ID,
                                                hdrl.C_AllocationLine_ID
                                   ) hdr
                                                            ON hdr.C_AllocationHdr_ID = fa.record_id AND
                                                               fa.ad_table_id = get_Table_Id('C_AllocationHdr') AND
                                                               fa.line_id = hdr.C_AllocationLine_ID

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
    LANGUAGE plpgsql
;



DROP FUNCTION IF EXISTS de_metas_acct.taxaccountsonly_details(p_ad_org_id     numeric,
                                                              p_account_id    numeric,
                                                              p_c_vat_code_id numeric,
                                                              p_datefrom      date,
                                                              p_dateto        date)
;



CREATE FUNCTION de_metas_acct.taxaccountsonly_details(p_ad_org_id     numeric,
                                                      p_account_id    numeric,
                                                      p_c_vat_code_id numeric,
                                                      p_datefrom      date,
                                                      p_dateto        date)
    RETURNS TABLE
            (
                balance           numeric,
                balanceyear       numeric,
                taxbaseamt        numeric,
                accountno         character varying,
                accountname       character varying,
                taxname           character varying,
                c_tax_id          numeric,
                vatcode           character varying,
                c_elementvalue_id numeric,
                param_startdate   date,
                param_enddate     date,
                param_konto       character varying,
                param_vatcode     character varying,
                param_org         character varying,
                currency          character
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT t.Balance,
       t.BalanceYear,
       s.taxbaseamt,
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
        END)      AS param_konto,
       t.param_vatcode,
       t.param_org,
       c.iso_code AS currency

FROM (
         SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
         FROM C_Tax_Acct ta
                  INNER JOIN C_ValidCombination vc ON (vc.C_ValidCombination_ID IN
                                                       (ta.T_Due_Acct, ta.T_Credit_Acct))
         WHERE p_Account_ID IS NULL
            OR vc.Account_ID = p_Account_ID
     ) AS ev
         INNER JOIN de_metas_acct.taxaccounts_details(p_AD_Org_ID, ev.C_ElementValue_ID, p_C_Vat_Code_ID, p_DateFrom, p_DateTo) AS t ON TRUE
         INNER JOIN c_acctschema aas
                    ON aas.ad_orgonly_id = p_AD_Org_ID
         INNER JOIN c_currency C ON C.c_currency_id = aas.c_currency_id
         INNER JOIN de_metas_acct.tax_accounting_report_details_sum(p_DateFrom, p_DateTo, t.vatcode,
                                                                    ev.C_ElementValue_ID,
                                                                    t.c_tax_id,
                                                                    p_AD_Org_ID) AS S ON TRUE

WHERE t.taxname IS NOT NULL
ORDER BY vatcode, accountno
    ;
$$
;
