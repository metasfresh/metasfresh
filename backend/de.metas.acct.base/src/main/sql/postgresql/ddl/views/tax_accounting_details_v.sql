CREATE OR REPLACE VIEW de_metas_acct.tax_accounting_details_v
AS
SELECT ev.value      AS kontono,
       ev.name       AS kontoname,
       fa.dateacct,
       fa.documentno,

       tax.name      AS taxname,
       tax.rate      AS taxrate,

       bp.name       AS bpName,

       i.taxbaseamt  AS inv_baseamt,
       gl.taxbaseamt AS gl_baseamt,
       (CASE
            WHEN al.c_allocationline_id IS NULL THEN NULL
            WHEN tax.IsWholeTax = 'Y'           THEN 0
            WHEN tax.Rate = 0                   THEN 0
                                                ELSE ROUND((fa.AmtAcctDr - fa.AmtAcctCr) * 100 / tax.Rate, 2)
        END)         AS alloc_baseamt,

       i.taxamt      AS inv_taxamt,
       gl.taxamt     AS gl_taxamt,
       (CASE
            WHEN al.c_allocationline_id IS NOT NULL
                THEN fa.AmtAcctDr - fa.AmtAcctCr
        END)         AS alloc_taxamt,

       (CASE
            WHEN fa.line_id IS NULL AND fa.C_Tax_id IS NOT NULL
                THEN (fa.amtacctdr - fa.amtacctcr)
                ELSE 0
        END)         AS taxamtperaccount,

       c.iso_code,

       (CASE
            WHEN fa.line_id IS NULL AND fa.C_Tax_id IS NOT NULL
                THEN 'Y'
                ELSE 'N'
        END)         AS IsTaxLine,

       fa.vatcode    AS vatcode,
       fa.C_Tax_ID,
       fa.account_id,
       fa.postingtype,
       fa.IsActive,
       fa.c_currency_id,
       fa.ad_org_id,
       fa.ad_client_id

FROM fact_acct fa

         LEFT OUTER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
         LEFT OUTER JOIN c_tax tax ON fa.c_tax_id = tax.c_tax_id

         LEFT OUTER JOIN C_Currency c ON fa.c_Currency_ID = c.C_Currency_ID
         LEFT OUTER JOIN c_bpartner bp ON fa.c_bpartner_id = bp.c_bpartner_id
    --
    -- if invoice
         LEFT OUTER JOIN (SELECT (CASE
                                      WHEN dt.docbasetype <> 'APC' THEN inv_tax.taxbaseamt
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
                                   JOIN C_InvoiceTax inv_tax ON i.c_invoice_id = inv_tax.c_invoice_id
                                   JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypeTarget_id) i ON (fa.record_id = i.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND i.c_tax_id = fa.c_tax_id)
    --
    -- if gl journal
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
                                        ON gl.gl_journal_id = gll.gl_journal_id) gl ON (fa.record_id = gl.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND gl.gl_journalline_id = fa.line_id AND gl.tax_id = fa.c_tax_id)
    --
    -- if allocationHdr
         LEFT OUTER JOIN c_allocationline al ON (al.c_allocationline_id = fa.line_id AND al.c_allocationhdr_id = fa.record_id AND fa.ad_table_id = get_Table_Id('C_AllocationHdr'))
;

