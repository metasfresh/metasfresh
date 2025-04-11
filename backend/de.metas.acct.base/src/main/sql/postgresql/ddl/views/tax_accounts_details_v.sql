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
-- DROP VIEW IF EXISTS de_metas_acct.tax_accounts_details_v
-- ;

CREATE OR REPLACE VIEW de_metas_acct.tax_accounts_details_v
AS

WITH tax_accounts AS (SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
                      FROM C_Tax_Acct ta
                               INNER JOIN C_ValidCombination vc
                                          ON vc.C_ValidCombination_ID IN (ta.T_Due_Acct, ta.T_Credit_Acct))
SELECT fa.vatcode,
       fa.accountno,
       fa.accountname,
       fa.dateacct,
       fa.documentno,
       fa.taxname,
       fa.taxrate,
       fa.bpName,
       fa.taxamt,
       fa.currency,
       (ABS(TaxBaseAmt) * SIGN(TaxAmt)) AS TaxBaseAmt, -- we need the absolut value in order to be able to enforce tax sign
       fa.source_currency,
       fa.C_Tax_ID,
       fa.account_id,
       fa.postingtype,
       fa.accountconceptualname,
       fa.c_currency_id,
       fa.source_currency_id,
       fa.c_period_ID,
       fa.ad_table_id,
       fa.record_id,
       fa.ad_org_id
FROM (SELECT fa.vatcode                        AS vatcode,
             ev.value                          AS accountno,
             ev.name                           AS accountname,
             fa.dateacct,
             fa.documentno,

             tax.name                          AS taxname,
             tax.rate                          AS taxrate,

             bp.name                           AS bpName,

             (fa.AmtAcctDr - fa.AmtAcctCr)     AS taxamt,
             (CASE
                  WHEN fa.ad_table_id = get_Table_Id('C_Invoice')       THEN inv_tax.taxbaseamt
                  WHEN fa.ad_table_id = get_Table_Id('C_AllocationHdr') THEN
                      (CASE
                           WHEN al.c_allocationline_id IS NULL THEN NULL
                           WHEN tax.IsWholeTax = 'Y'           THEN 0
                           WHEN tax.Rate = 0                   THEN 0
                                                               ELSE ROUND((fa.AmtAcctDr - fa.AmtAcctCr) * 100 / tax.Rate, 2)
                       END)
                  WHEN fa.ad_table_id = get_Table_Id('GL_Journal')      THEN
                      (CASE
                           WHEN gll.dr_autotaxaccount = 'Y'
                               THEN gll.dr_taxbaseamt
                           WHEN cr_autotaxaccount = 'Y'
                               THEN gll.cr_taxbaseamt::numeric
                       END)
                  WHEN fa.ad_table_id = get_Table_Id('SAP_GLJournal')   THEN
                      (SELECT amtacct
                       FROM SAP_GLJournalLine gll
                       WHERE gll.sap_gljournalline_id = sap_gll.parent_id
                         AND sap_gll.c_tax_id = gll.c_tax_id)::numeric
              END)                             AS TaxBaseAmt,
             c.iso_code                        AS source_currency,
             cr.iso_code                       AS currency,
             fa.C_Tax_ID,
             fa.account_id,
             fa.postingtype,
             fa.accountconceptualname,
             fa.c_currency_id                  AS source_currency_id,
             cr.c_currency_id,
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
               LEFT OUTER JOIN C_InvoiceTax inv_tax ON (fa.record_id = inv_tax.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND inv_tax.c_tax_id = fa.c_tax_id)
               LEFT OUTER JOIN GL_JournalLine gll ON (fa.record_id = gll.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND gll.gl_journalline_id = fa.line_id AND COALESCE(gll.dr_tax_id, gll.cr_tax_id) = fa.c_tax_id)
               LEFT OUTER JOIN SAP_GLJournalLine sap_gll ON (fa.record_id = sap_gll.SAP_GLJournal_ID AND fa.ad_table_id = get_Table_Id('SAP_GLJournal') AND
                                                             sap_gll.SAP_GLJournalLine_ID = fa.line_id AND sap_gll.C_tax_id = fa.c_tax_id)
               LEFT OUTER JOIN c_allocationline al ON (al.c_allocationline_id = fa.line_id AND al.c_allocationhdr_id = fa.record_id AND fa.ad_table_id = get_Table_Id('C_AllocationHdr'))
               INNER JOIN AD_ClientInfo ci ON ci.AD_Client_ID = fa.ad_client_id
               INNER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
               INNER JOIN C_Currency cr ON acs.C_Currency_ID = cr.C_Currency_ID) AS fa
         LEFT OUTER JOIN tax_accounts ON (tax_accounts.C_ElementValue_ID = fa.account_id AND fa.ad_table_id IN (get_Table_Id('SAP_GLJournal'), get_Table_Id('GL_Journal')))
WHERE (fa.ad_table_id IN (get_Table_Id('SAP_GLJournal'), get_Table_Id('GL_Journal')))
   OR (fa.ad_table_id IN (get_Table_Id('C_Invoice'), get_Table_Id('C_AllocationHdr')) AND fa.accountconceptualname IN ('T_Due_Acct', 'T_Credit_Acct'))
;