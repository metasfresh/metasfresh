DROP VIEW c_invoice_v1;

CREATE OR REPLACE VIEW c_invoice_v1 AS
SELECT i.c_invoice_id,
       i.ad_client_id,
       i.ad_org_id,
       i.isactive,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.issotrx,
       i.documentno,
       i.docstatus,
       i.docaction,
       i.processing,
       i.processed,
       i.c_doctype_id,
       i.c_doctypetarget_id,
       i.c_order_id,
       i.description,
       i.isapproved,
       i.istransferred,
       i.salesrep_id,
       i.dateinvoiced,
       i.dateprinted,
       i.dateacct,
       i.c_bpartner_id,
       i.c_bpartner_location_id,
       i.ad_user_id,
       i.poreference,
       i.dateordered,
       i.c_currency_id,
       i.c_conversiontype_id,
       i.paymentrule,
       i.c_paymentterm_id,
       i.c_charge_id,
       i.m_pricelist_id,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id,
       i.isprinted,
       i.isdiscountprinted,
       i.ispaid,
       i.isindispute,
       i.ispayschedulevalid,
       NULL::numeric AS c_invoicepayschedule_id,
       i.invoicecollectiontype,
       inv_tax.c_tax_id,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.chargeamt * (-1)::numeric
                                                                              ELSE i.chargeamt
       END           AS chargeamt,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.totallines * (-1)::numeric
                                                                              ELSE i.totallines
       END           AS totallines,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.grandtotal * (-1)::numeric
                                                                              ELSE i.grandtotal
       END           AS grandtotal,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN inv_tax.taxbaseamt * (-1)::numeric
                                                                              ELSE inv_tax.taxbaseamt
       END           AS taxbaseamt,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN inv_tax.taxamt * (-1)::numeric
                                                                              ELSE inv_tax.taxamt
       END           AS taxamt,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN (-1)
                                                                              ELSE 1
       END           AS multiplier,
       CASE
           WHEN charat(d.docbasetype::character varying, 2)::text = 'P'::text THEN (-1)
                                                                              ELSE 1
       END           AS multiplierap,
       d.docbasetype,
       i.Posted
FROM c_invoice i
         JOIN C_InvoiceTax inv_tax ON i.c_invoice_id = inv_tax.c_invoice_id
         JOIN c_doctype d ON i.c_doctype_id = d.c_doctype_id
;

CREATE OR REPLACE VIEW de_metas_acct.tax_accounting_details_v
AS
SELECT ev.value          AS kontono,
       ev.name           AS kontoname,
       fa.dateacct,
       fa.documentno,

       tax.name          AS taxname,
       tax.rate          AS taxrate,

       bp.name           AS bpName,

       i.taxbaseamt      AS inv_baseamt,
       gl.taxbaseamt     AS gl_baseamt,
       sap_gl.taxbaseamt AS sap_gl_baseamt,
       (CASE
            WHEN al.c_allocationline_id IS NULL THEN NULL
            WHEN tax.IsWholeTax = 'Y'           THEN 0
            WHEN tax.Rate = 0                   THEN 0
                                                ELSE ROUND((fa.AmtAcctDr - fa.AmtAcctCr) * 100 / tax.Rate, 2)
        END)             AS alloc_baseamt,

       i.taxamt          AS inv_taxamt,
       gl.taxamt         AS gl_taxamt,
       sap_gl.taxamt     AS sap_gl_taxamt,
       (CASE
            WHEN al.c_allocationline_id IS NOT NULL
                THEN fa.AmtAcctDr - fa.AmtAcctCr
        END)             AS alloc_taxamt,

       (CASE
            WHEN fa.line_id IS NULL AND fa.C_Tax_id IS NOT NULL
                THEN (fa.amtacctdr - fa.amtacctcr)
                ELSE 0
        END)             AS taxamtperaccount,

       c.iso_code,

       (CASE
            WHEN fa.line_id IS NULL AND fa.C_Tax_id IS NOT NULL
                THEN 'Y'
                ELSE 'N'
        END)             AS IsTaxLine,

       fa.vatcode        AS vatcode,
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
         LEFT OUTER JOIN (SELECT i.taxbaseamt * i.multiplierap AS taxbaseamt,
                                 i.taxamt * i.multiplierap     AS taxamt,
                                 i.c_invoice_id
                          FROM c_invoice_v1 i
                                   JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypeTarget_id) i ON (fa.record_id = i.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND i.c_tax_id = fa.c_tax_id)
    --
    -- if gl journal
         LEFT OUTER JOIN (SELECT (CASE
                                      WHEN gll.dr_autotaxaccount = 'Y'
                                          THEN gll.dr_taxbaseamt
                                      WHEN cr_autotaxaccount = 'Y'
                                          THEN (-1) * gll.cr_taxbaseamt::numeric
                                  END)                                  AS taxbaseamt,
                                 (CASE
                                      WHEN gll.dr_autotaxaccount = 'Y'
                                          THEN gll.dr_taxamt
                                      WHEN cr_autotaxaccount = 'Y'
                                          THEN (-1) * gll.cr_taxbaseamt::numeric
                                  END)                                  AS taxamt,
                                 gl.gl_journal_id,
                                 gll.gl_journalline_id,
                                 COALESCE(gll.dr_tax_id, gll.cr_tax_id) AS tax_id
                          FROM gl_journal gl
                                   JOIN GL_JournalLine gll
                                        ON gl.gl_journal_id = gll.gl_journal_id) gl ON (fa.record_id = gl.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND gl.gl_journalline_id = fa.line_id AND gl.tax_id = fa.c_tax_id)

    --if SAP gl journal
         LEFT OUTER JOIN (SELECT (CASE
                                      WHEN sap_gll.postingsign = 'C' THEN (-1) * sap_gll.amtacct::numeric
                                      WHEN sap_gll.postingsign = 'D' THEN sap_gll.amtacct
                                  END)                                            AS taxamt,

                                 (CASE
                                      WHEN sap_gll.postingsign = 'C' THEN (-1)
                                      WHEN sap_gll.postingsign = 'D' THEN 1
                                  END) *
                                 (SELECT amtacct
                                  FROM SAP_GLJournalLine gll
                                  WHERE gll.sap_gljournalline_id = sap_gll.parent_id
                                    AND sap_gll.c_tax_id = gll.c_tax_id)::numeric AS taxbaseamt,
                                 sap_gl.SAP_GLJournal_ID,
                                 sap_gll.SAP_GLJournalLine_ID,
                                 sap_gll.c_tax_id                                 AS tax_id
                          FROM SAP_GLJournal sap_gl
                                   JOIN SAP_GLJournalLine sap_gll
                                        ON sap_gl.SAP_GLJournal_ID = sap_gll.SAP_GLJournal_ID AND sap_gll.isActive = 'Y'
                          WHERE sap_gl.isActive = 'Y'
                            AND sap_gll.c_tax_id IS NOT NULL
                            AND sap_gll.parent_id IS NOT NULL) sap_gl ON fa.record_id = sap_gl.SAP_GLJournal_ID AND fa.ad_table_id = get_Table_Id('SAP_GLJournal') AND
                                                                         sap_gl.SAP_GLJournalLine_ID = fa.line_id AND sap_gl.tax_id = fa.c_tax_id


    --
    -- if allocationHdr
         LEFT OUTER JOIN c_allocationline al ON (al.c_allocationline_id = fa.line_id AND al.c_allocationhdr_id = fa.record_id AND fa.ad_table_id = get_Table_Id('C_AllocationHdr'))
;

