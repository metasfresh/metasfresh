/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
-- DROP VIEW de_metas_acct.documents_accounting_details_v;
CREATE OR REPLACE VIEW de_metas_acct.documents_accounting_details_v
AS
SELECT fa.vatcode,
       fa.accountno,
       fa.accountname,
       fa.dateacct,
       fa.documentno,
       fa.taxname,
       fa.taxrate,
       fa.bpName,
       fa.taxamt,
       fa.multiplier,
       (COALESCE(fa.inv_baseamt, fa.sap_gl_baseamt, fa.gl_baseamt, fa.alloc_baseamt) * fa.multiplier) AS baseamt,
       fa.iso_code,
       fa.IsTaxLine,
       fa.C_Tax_ID,
       fa.account_id,
       fa.postingtype,
       fa.IsActive,
       fa.c_currency_id,
       fa.c_period_ID,
       fa.ad_table_id,
       fa.record_id,
       fa.ad_org_id,
       fa.ad_client_id

FROM (SELECT fa.vatcode                        AS vatcode,
             ev.value                          AS accountno,
             ev.name                           AS accountname,
             fa.dateacct,
             fa.documentno,

             tax.name                          AS taxname,
             tax.rate                          AS taxrate,

             bp.name                           AS bpName,

             (fa.AmtAcctDr - fa.AmtAcctCr)     AS taxamt,
             SIGN(fa.AmtAcctDr - fa.AmtAcctCr) AS multiplier,
             i.taxbaseamt                      AS inv_baseamt,
             gl.taxbaseamt                     AS gl_baseamt,
             sap_gl.taxbaseamt                 AS sap_gl_baseamt,
             (CASE
                  WHEN al.c_allocationline_id IS NULL THEN NULL
                  WHEN tax.IsWholeTax = 'Y'           THEN 0
                  WHEN tax.Rate = 0                   THEN 0
                                                      ELSE ROUND((fa.AmtAcctDr - fa.AmtAcctCr) * 100 / tax.Rate, 2)
              END)                             AS alloc_baseamt,

             c.iso_code,

             (CASE
                  WHEN fa.line_id IS NULL AND fa.C_Tax_id IS NOT NULL
                      THEN 'Y'
                      ELSE 'N'
              END)                             AS IsTaxLine,
             fa.C_Tax_ID,
             fa.account_id,
             fa.postingtype,
             fa.IsActive,
             fa.c_currency_id,
             fa.c_period_ID,
             fa.ad_table_id,
             fa.record_id,
             fa.ad_org_id,
             fa.ad_client_id
      FROM fact_acct fa
               LEFT OUTER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
               LEFT OUTER JOIN c_tax tax ON fa.c_tax_id = tax.c_tax_id
               LEFT OUTER JOIN C_Currency c ON fa.c_Currency_ID = c.C_Currency_ID
               LEFT OUTER JOIN c_bpartner bp ON fa.c_bpartner_id = bp.c_bpartner_id
          --
          -- if invoice
               LEFT OUTER JOIN (SELECT inv_tax.taxbaseamt AS taxbaseamt,
                                       i.c_invoice_id,
                                       inv_tax.c_tax_id
                                FROM c_invoice_v i
                                         JOIN C_InvoiceTax inv_tax ON i.c_invoice_id = inv_tax.c_invoice_id) i
                               ON (fa.record_id = i.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND i.c_tax_id = fa.c_tax_id)
          --
          -- if gl journal
               LEFT OUTER JOIN (SELECT (CASE
                                            WHEN gll.dr_autotaxaccount = 'Y'
                                                THEN gll.dr_taxbaseamt
                                            WHEN cr_autotaxaccount = 'Y'
                                                THEN gll.cr_taxbaseamt::numeric
                                        END)                                  AS taxbaseamt,

                                       gl.gl_journal_id,
                                       gll.gl_journalline_id,
                                       COALESCE(gll.dr_tax_id, gll.cr_tax_id) AS tax_id
                                FROM gl_journal gl
                                         JOIN GL_JournalLine gll
                                              ON gl.gl_journal_id = gll.gl_journal_id) gl ON (fa.record_id = gl.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND gl.gl_journalline_id = fa.line_id AND gl.tax_id = fa.c_tax_id)

          --if SAP gl journal
               LEFT OUTER JOIN (SELECT (SELECT amtacct
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
               LEFT OUTER JOIN c_allocationline al ON (al.c_allocationline_id = fa.line_id AND al.c_allocationhdr_id = fa.record_id AND fa.ad_table_id = get_Table_Id('C_AllocationHdr'))) AS fa
;

