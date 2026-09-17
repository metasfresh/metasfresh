-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/views/tax_accounts_details_v.sql
-- Reverse-Charge symmetric reporting on tax_accounts_details_v.
--
-- Under §13b UStG + §17(1) UStG, the recipient must declare a RC transaction as both output
-- (UStVA KZ 84/85) and input deduction (KZ 67) with identical signed tax amounts — including
-- after any payment-discount (Skonto) adjustment.
--
-- The view used to back-compute the allocation row's base from signed `taxamt = AmtAcctDr -
-- AmtAcctCr`. For RC the two legs (T_Credit DR + T_Due CR) have mirror signs, so the resulting
-- NetAmt and TaxAmt diverged between input and output rows (976.21 vs 1023.79 on TC-S14).
--
-- Fix: on the T_Due_Acct leg of a RC tax, flip the TaxAmt sign and flip the CMA-row TaxBaseAmt
-- formula to mirror the T_Credit leg. Adds tax.IsReverseCharge to the inner projection.
-- Non-RC paths are byte-identical.

DROP VIEW IF EXISTS de_metas_acct.tax_accounts_details_v$new;

CREATE OR REPLACE VIEW de_metas_acct.tax_accounts_details_v$new AS

WITH tax_accounts AS (SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
                      FROM C_Tax_Acct ta
                               INNER JOIN C_ValidCombination vc
                                          ON vc.C_ValidCombination_ID IN (ta.T_Due_Acct, ta.T_Credit_Acct)
                      UNION

                        SELECT C_ElementValue_ID from C_ElementValue
                        WHERE AccountConceptualName IN ('T_Due_Acct', 'T_Credit_Acct')

                      )
SELECT fa.vatcode,
       fa.accountno,
       fa.accountname,
       fa.dateacct,
       fa.documentno,
       fa.taxname,
       fa.taxrate,
       fa.bpName,
       -- RC symmetric reporting: on reverse-charge taxes, the output (T_Due_Acct) leg mirrors
       -- the input (T_Credit_Acct) leg. §13b UStG + §17(1) UStG require both KZ 84/85 and KZ 67
       -- to show the same (signed) tax amount after any adjustment.
       (CASE
            WHEN fa.accountconceptualname = 'T_Due_Acct' AND fa.isreversecharge = 'Y'
                THEN -fa.taxamt
                ELSE  fa.taxamt
        END) AS taxamt,
       fa.currency,
       (CASE
            WHEN DocBaseType IN ('APC', 'ARI') THEN -TaxBaseAmt
            WHEN DocBaseType IN ('ARC', 'API') THEN  TaxBaseAmt
            -- Allocation (CMA) rows land here. Under the default ledger-sign convention the
            -- back-computed base would diverge between the two RC legs (one positive, one
            -- negative). Flip sign on the RC output leg so the declaration base mirrors the
            -- input leg.
            WHEN fa.accountconceptualname = 'T_Due_Acct' AND fa.isreversecharge = 'Y'
                THEN -SIGN(TaxAmt) * ABS(TaxBaseAmt)
                ELSE  SIGN(TaxAmt) * ABS(TaxBaseAmt) -- we need the absolut value in order to be able to enforce tax sign
        END) AS TaxBaseAmt,
       fa.source_currency,
       fa.C_Tax_ID,
       fa.account_id,
       fa.postingtype,
       fa.accountconceptualname,
       fa.c_currency_id,
       fa.source_currency_id,
       fa.ad_table_id,
       fa.record_id,
       fa.ad_org_id
FROM (SELECT fa.vatcode                    AS vatcode,
             ev.value                      AS accountno,
             ev.name                       AS accountname,
             fa.dateacct,
             fa.documentno,

             tax.name                      AS taxname,
             tax.rate                      AS taxrate,

             bp.name                       AS bpName,

             (fa.AmtAcctDr - fa.AmtAcctCr) AS taxamt,
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
              END)                         AS TaxBaseAmt,
             c.iso_code                    AS source_currency,
             cr.iso_code                   AS currency,
             fa.DocBaseType,
             tax.IsReverseCharge           AS isreversecharge,
             fa.C_Tax_ID,
             fa.account_id,
             fa.postingtype,
             fa.accountconceptualname,
             fa.c_currency_id              AS source_currency_id,
             cr.c_currency_id,
             fa.ad_table_id,
             fa.record_id,
             fa.ad_org_id,
             fa.ad_client_id
      FROM fact_acct fa
               INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
               INNER JOIN c_tax tax ON fa.c_tax_id = tax.c_tax_id
               INNER JOIN C_Currency c ON fa.c_Currency_ID = c.C_Currency_ID
               LEFT OUTER JOIN c_bpartner bp ON fa.c_bpartner_id = bp.c_bpartner_id
               LEFT OUTER JOIN C_InvoiceTax inv_tax ON (fa.record_id = inv_tax.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND inv_tax.c_tax_id = fa.c_tax_id)
               LEFT OUTER JOIN GL_JournalLine gll ON (fa.record_id = gll.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND gll.gl_journalline_id = fa.line_id AND COALESCE(gll.dr_tax_id, gll.cr_tax_id) = fa.c_tax_id)
               LEFT OUTER JOIN SAP_GLJournalLine sap_gll ON (fa.record_id = sap_gll.SAP_GLJournal_ID AND fa.ad_table_id = get_Table_Id('SAP_GLJournal') AND
                                                             sap_gll.SAP_GLJournalLine_ID = fa.line_id AND sap_gll.C_tax_id = fa.c_tax_id)
               LEFT OUTER JOIN c_allocationline al ON (al.c_allocationline_id = fa.line_id AND al.c_allocationhdr_id = fa.record_id AND fa.ad_table_id = get_Table_Id('C_AllocationHdr'))
               INNER JOIN AD_ClientInfo ci ON ci.AD_Client_ID = fa.ad_client_id
               INNER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
               INNER JOIN C_Currency cr ON acs.C_Currency_ID = cr.C_Currency_ID) AS fa
    WHERE (exists (select 1 from tax_accounts where tax_accounts.C_ElementValue_ID = fa.account_id) )
   OR (fa.ad_table_id IN (get_Table_Id('C_Invoice'), get_Table_Id('C_AllocationHdr')) AND fa.accountconceptualname IN ('T_Due_Acct', 'T_Credit_Acct'))
;

SELECT db_alter_view(
    'de_metas_acct.tax_accounts_details_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('tax_accounts_details_v$new')
       AND lower(views.table_schema) = 'de_metas_acct'));

DROP VIEW IF EXISTS de_metas_acct.tax_accounts_details_v$new;
