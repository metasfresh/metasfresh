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
                                                                                                                                          COALESCE(
                                                                                                                                                  COALESCE(
                                                                                                                                                          COALESCE(x.inv_baseamt, x.gl_baseamt),
                                                                                                                                                          x.hdr_baseamt,
                                                                                                                                                          0 :: numeric)) AS taxbaseamt,
                                                                                                                                          COALESCE(
                                                                                                                                                  COALESCE(
                                                                                                                                                          COALESCE(x.inv_taxamt, x.gl_taxamt),
                                                                                                                                                          x.hdr_taxamt,
                                                                                                                                                          0 :: numeric)) AS taxamt,
                                                                                                                                          taxamtperaccount,
                                                                                                                                          IsTaxLine,
                                                                                                                                          x.iso_code,
                                                                                                                                          x.p_dateFrom                   AS param_startdate,
                                                                                                                                          x.p_dateTo                     AS param_endtdate,
                                                                                                                                          x.param_konto,
                                                                                                                                          x.param_vatcode,
                                                                                                                                          x.param_org,
                                                                                                                                          x.C_Tax_ID,
                                                                                                                                          x.account_id,
                                                                                                                                          x.ad_org_id
FROM (
         SELECT ev.value       AS kontono,
                ev.name        AS kontoname,
                fa.dateacct,
                fa.documentno,

                tax.name       AS taxname,
                tax.rate       AS taxrate,

                bp.name        AS bpName,

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

                c.iso_code,

                (CASE
                     WHEN fa.line_id IS NULL AND fa.C_Tax_id IS NOT NULL
                         THEN 'Y'
                         ELSE 'N'
                 END)          AS IsTaxLine,

                fa.vatcode     AS vatcode,
                p_dateFrom,
                p_dateTo,
                (CASE
                     WHEN p_account_id IS NULL
                         THEN NULL
                         ELSE (SELECT value || ' - ' || name
                               FROM C_ElementValue
                               WHERE C_ElementValue_ID = p_account_id
                                 AND isActive = 'Y')
                 END)          AS param_konto,
                (CASE
                     WHEN p_vatcode IS NULL
                         THEN NULL
                         ELSE p_vatcode
                 END)          AS param_vatcode,
                (CASE
                     WHEN p_org_id IS NULL
                         THEN NULL
                         ELSE (SELECT name
                               FROM ad_org
                               WHERE ad_org_id = p_org_id
                                 AND isActive = 'Y')
                 END)          AS param_org,
                fa.C_Tax_ID,
                fa.account_id,
                fa.ad_org_id

         FROM public.fact_acct fa
                  -- gh #489: explicitly select from public.fact_acct, bacause the function de_metas_acct.Fact_Acct_EndingBalance expects it.

                  JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id AND ev.isActive = 'Y'
                  JOIN c_tax tax ON fa.c_tax_id = tax.c_tax_id AND tax.isActive = 'Y'

                  LEFT JOIN C_Currency c ON fa.c_Currency_ID = c.C_Currency_ID
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
              (SELECT (CASE
                           WHEN dt.docbasetype <> 'APC'
                               THEN inv_tax.taxbaseamt
                               ELSE (-1) * inv_tax.taxbaseamt
                       END) AS taxbaseamt,
                      (CASE
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
              ) i ON fa.record_id = i.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND
                     i.c_tax_id = fa.c_tax_id

                  --if gl journal
                  LEFT OUTER JOIN (SELECT (CASE
                                               WHEN gll.dr_autotaxaccount = 'Y'
                                                   THEN gll.dr_taxbaseamt
                                               WHEN cr_autotaxaccount = 'Y'
                                                   THEN gll.cr_taxbaseamt
                                           END)                                  AS taxbaseamt,
                                          (CASE
                                               WHEN gll.dr_autotaxaccount = 'Y'
                                                   THEN gll.dr_taxamt
                                               WHEN cr_autotaxaccount = 'Y'
                                                   THEN gll.cr_taxamt
                                           END)                                  AS taxamt,
                                          gl.gl_journal_id,
                                          gll.gl_journalline_id,
                                          COALESCE(gll.dr_tax_id, gll.cr_tax_id) AS tax_id
                                   FROM gl_journal gl
                                            JOIN GL_JournalLine gll
                                                 ON gl.gl_journal_id = gll.gl_journal_id AND gll.isActive = 'Y'
                                   WHERE gl.isActive = 'Y'
         ) gl ON fa.record_id = gl.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND
                 gl.gl_journalline_id = fa.line_id AND gl.tax_id = fa.c_tax_id

             --if allocationHdr
                  LEFT OUTER JOIN (
             SELECT hdr.C_AllocationHdr_ID,
                    hdrl.C_AllocationLine_ID,
                    (hdrl.discountamt + hdrl.writeoffamt) ::numeric                                                                           AS taxbaseamt,
                    ROUND((hdrl.discountamt + hdrl.writeoffamt) - ((hdrl.discountamt + hdrl.writeoffamt) * 100 / (100 + t.rate)), 2)::numeric AS taxamt
             FROM C_AllocationHdr hdr
                      JOIN C_AllocationLine hdrl
                           ON hdr.C_AllocationHdr_ID = hdrl.C_AllocationHdr_ID AND hdrl.isActive = 'Y'
                      LEFT JOIN Fact_Acct fa ON hdrl.c_invoice_id = fa.record_id AND fa.ad_table_id = 318 AND fa.postingtype = 'A' AND Line_ID IS NULL AND fa.c_tax_id IS NOT NULL
                      LEFT JOIN C_Tax t ON fa.c_tax_id = t.c_tax_id
             WHERE hdr.isActive = 'Y'
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
           AND fa.isActive = 'Y'
     ) x

ORDER BY x.vatcode, x.kontono, x.kontoname, x.dateacct, x.documentno, x.taxname, x.taxrate, x.bpName
$$
    LANGUAGE sql
    STABLE
;