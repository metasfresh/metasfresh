--
-- this view comes from a customer-repo and was meanwhile superseeded in the master-branch
-- see backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/system/10-de.metas.adempiere/5684561_sys_gh14679_gh14602_migrate_RV_DATEV_Export_Fact_Acct_Invoice_views.sql
--

/*
DROP VIEW IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice
;

DROP VIEW IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice_PerTax
;

CREATE OR REPLACE VIEW RV_DATEV_Export_Fact_Acct_Invoice_PerTax
            (DebitOrCreditIndicator, Currency, dr_account, cr_account, amt, GrandTotal, taxamt, activityname, c_activity_id, documentno, dateacct, bpvalue, bpname, duedate, description, c_bpartner_id, c_invoice_id, docbasetype,
             c_tax_rate,
             vatCode,
             c_doctype_name,
             fact_acct_id, rv_datev_export_fact_acct_invoice_id, ad_client_id, ad_org_id)
AS
SELECT DebitOrCreditIndicator,
       currency,
       dr_account,
       cr_account,
       SUM(amt)          AS amt,
       invoicetotal,
       taxamt,
       activityname,
       c_activity_id,
       documentno,
       dateacct,
       bpvalue,
       bpname,
       duedate,
       documentno        AS description,
       c_bpartner_id,
       c_invoice_id,
       docbasetype,
       c_tax_rate,
       vatCode,
       c_doctype_name,
       MIN(fact_acct_id) AS fact_acct_id,
       MIN(fact_acct_id) AS rv_datev_export_fact_acct_invoice_id,
       ad_client_id,
       ad_org_id
FROM (
         SELECT get_sysconfig_value('DATEVExportLines_OneLinePerInvoiceTax', 'N')                AS isOnelInePerInvoiceTax,
                CASE
                    WHEN fa.amtacctdr <> 0::numeric THEN 'S'
                                                    ELSE 'H'
                END                                                                              AS DebitOrCreditIndicator,
                (SELECT cur.iso_code FROM c_currency cur WHERE cur.c_currency_id = fa.c_currency_id)
                                                                                                 AS Currency,
                (CASE
                     WHEN bp.debtorId > 0 THEN bp.debtorid::varchar
                                          ELSE
                                              (CASE
                                                   WHEN fa.AmtAcctDr <> 0 THEN ev.Value
                                                                          ELSE ev2.Value
                                               END)
                 END)                                                                            AS DR_Account
                 ,
                (CASE
                     WHEN bp.creditorid > 0 THEN bp.creditorId::varchar
                                            ELSE
                                                (CASE
                                                     WHEN fa.AmtAcctDr <> 0 THEN ev2.Value
                                                                            ELSE ev.Value
                                                 END)
                 END)                                                                            AS CR_Account,
                CASE
                    WHEN fa.amtacctdr <> 0::numeric THEN fa.amtacctdr
                                                    ELSE fa.amtacctcr
                END                                                                              AS amt,
                i.grandtotal                                                                     AS invoicetotal,
                it.taxamt,
                a.name                                                                           AS activityname,
                a.c_activity_id,
                fa.documentno,
                fa.dateacct,
                bp.value                                                                         AS bpvalue,
                bp.name                                                                          AS bpname,
                paymenttermduedate(i.c_paymentterm_id, i.dateinvoiced::timestamp WITH TIME ZONE) AS duedate,
                fa.description,
                bp.c_bpartner_id,
                fa.record_id                                                                     AS c_invoice_id,
                fa.docbasetype,
                t.rate                                                                           AS c_tax_rate,
                fa.vatCode,
                dt.name                                                                          AS c_doctype_name,
                fa.fact_acct_id,
                fa.fact_acct_id                                                                  AS rv_datev_export_fact_acct_invoice_id,
                fa.ad_client_id,
                fa.ad_org_id
         FROM fact_acct fa
                  JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
             -- We cannot join using Counterpart_Fact_Acct_ID, because fact_accts with a zero amount don't have then set,
             -- see org.compiere.acct.Fact.save(org.compiere.acct.FactTrxLines)
             -- Therefore, the fact_accts for an invoice with price=0 would never be exported
                  JOIN fact_acct fa2 ON fa2.c_tax_id IS NULL AND fa2.ad_table_id = fa.ad_table_id AND fa2.record_id = fa.record_id
                  JOIN c_elementvalue ev2 ON ev2.c_elementvalue_id = fa2.account_id
                  JOIN c_bpartner bp ON bp.c_bpartner_id = fa.c_bpartner_id
                  LEFT JOIN c_activity a ON a.c_activity_id = COALESCE(fa.c_activity_id, fa2.c_activity_id)
                  JOIN c_invoice i ON i.c_invoice_id = fa.record_id
                  JOIN c_invoicetax it ON i.c_invoice_id = it.c_invoice_id AND it.c_tax_id = fa.c_tax_id
                  JOIN c_tax t ON t.c_tax_id = fa.c_tax_id
                  JOIN c_doctype dt ON dt.c_doctype_id = fa.c_doctype_id

         WHERE fa.ad_table_id = get_table_id('C_Invoice'::character varying)
     ) d
WHERE true
  and (amt <> taxamt OR (amt=0 and taxamt=0))
GROUP BY DebitOrCreditIndicator,
         currency,
         dr_account,
         cr_account,
         invoicetotal,
         taxamt,
         activityname,
         c_activity_id,
         documentno,
         dateacct,
         bpvalue,
         bpname,
         duedate,
         c_bpartner_id,
         c_invoice_id,
         docbasetype,
         c_tax_rate,
         vatcode,
         c_doctype_name,
         ad_client_id,
         ad_org_id
;

DROP VIEW IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice_All
;

CREATE OR REPLACE VIEW RV_DATEV_Export_Fact_Acct_Invoice_All
            (DebitOrCreditIndicator, Currency, dr_account, cr_account, amt, GrandTotal, taxamt, activityname, c_activity_id, documentno, dateacct, bpvalue, bpname, duedate, description, c_bpartner_id, c_invoice_id, docbasetype,
             c_tax_rate,
             vatCode,
             c_doctype_name,
             fact_acct_id, rv_datev_export_fact_acct_invoice_id, ad_client_id, ad_org_id)
AS
SELECT CASE
           WHEN fa.amtacctdr <> 0::numeric THEN 'S'
                                           ELSE 'H'
       END                                                                              AS DebitOrCreditIndicator,
       (SELECT cur.iso_code FROM c_currency cur WHERE cur.c_currency_id = fa.c_currency_id)
                                                                                        AS Currency,
       (CASE
            WHEN bp.debtorId > 0 THEN bp.debtorid::varchar
                                 ELSE
                                     (CASE
                                          WHEN fa.AmtAcctDr <> 0 THEN ev.Value
                                                                 ELSE ev2.Value
                                      END)
        END)                                                                            AS DR_Account
        ,
       (CASE
            WHEN bp.creditorid > 0 THEN bp.creditorId::varchar
                                   ELSE
                                       (CASE
                                            WHEN fa.AmtAcctDr <> 0 THEN ev2.Value
                                                                   ELSE ev.Value
                                        END)
        END)                                                                            AS CR_Account,
       CASE
           WHEN fa.amtacctdr <> 0::numeric THEN fa.amtacctdr
                                           ELSE fa.amtacctcr
       END                                                                              AS amt,
       i.grandtotal                                                                     AS invoicetotal,
       it.taxamt,
       a.name                                                                           AS activityname,
       a.c_activity_id,
       fa.documentno,
       fa.dateacct,
       bp.value                                                                         AS bpvalue,
       bp.name                                                                          AS bpname,
       paymenttermduedate(i.c_paymentterm_id, i.dateinvoiced::timestamp WITH TIME ZONE) AS duedate,
       fa.description,
       bp.c_bpartner_id,
       fa.record_id                                                                     AS c_invoice_id,
       fa.docbasetype,
       t.rate                                                                           AS c_tax_rate,
       fa.vatcode,
       dt.name                                                                          AS c_doctype_name,
       fa.fact_acct_id,
       fa.fact_acct_id                                                                  AS rv_datev_export_fact_acct_invoice_id,
       fa.ad_client_id,
       fa.ad_org_id
FROM fact_acct fa
         JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
    -- We cannot join using Counterpart_Fact_Acct_ID, because fact_accts with a zero amount don't have then set,
    -- see org.compiere.acct.Fact.save(org.compiere.acct.FactTrxLines)
    -- Therefore, the fact_accts for an invoice with price=0 would never be exported
         JOIN fact_acct fa2 ON fa2.c_tax_id IS NULL AND fa2.ad_table_id = fa.ad_table_id AND fa2.record_id = fa.record_id
         JOIN c_elementvalue ev2 ON ev2.c_elementvalue_id = fa2.account_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = fa.c_bpartner_id
         LEFT JOIN c_activity a ON a.c_activity_id = COALESCE(fa.c_activity_id, fa2.c_activity_id)
         JOIN c_invoice i ON i.c_invoice_id = fa.record_id
         JOIN c_invoicetax it ON i.c_invoice_id = it.c_invoice_id AND it.c_tax_id = fa.c_tax_id
         JOIN c_tax t ON t.c_tax_id = fa.c_tax_id
         JOIN c_doctype dt ON dt.c_doctype_id = fa.c_doctype_id

WHERE fa.ad_table_id = get_table_id('C_Invoice'::character varying)
;

CREATE OR REPLACE VIEW RV_DATEV_Export_Fact_Acct_Invoice
            (DebitOrCreditIndicator, Currency, dr_account, cr_account, amt, GrandTotal, taxamt, activityname, c_activity_id, documentno, dateacct, bpvalue, bpname, duedate, description, c_bpartner_id, c_invoice_id, docbasetype,
             c_tax_rate,
             vatCode,
             c_doctype_name,
             fact_acct_id, rv_datev_export_fact_acct_invoice_id, ad_client_id, ad_org_id)
AS
SELECT DebitOrCreditIndicator,
       Currency,
       dr_account,
       cr_account,
       amt,
       GrandTotal,
       taxamt,
       activityname,
       c_activity_id,
       documentno,
       dateacct,
       bpvalue,
       bpname,
       duedate,
       description,
       c_bpartner_id,
       c_invoice_id,
       docbasetype,
       c_tax_rate,
       vatCode,
       c_doctype_name,
       fact_acct_id,
       rv_datev_export_fact_acct_invoice_id,
       ad_client_id,
       ad_org_id
FROM (
         SELECT 'Y' AS IsOneLinePerInvoiceTax,
                DebitOrCreditIndicator,
                Currency,
                dr_account,
                cr_account,
                amt,
                GrandTotal,
                taxamt,
                activityname,
                c_activity_id,
                documentno,
                dateacct,
                bpvalue,
                bpname,
                duedate,
                description,
                c_bpartner_id,
                c_invoice_id,
                docbasetype,
                c_tax_rate,
                vatCode,
                c_doctype_name,
                fact_acct_id,
                rv_datev_export_fact_acct_invoice_id,
                ad_client_id,
                ad_org_id
         FROM RV_DATEV_Export_Fact_Acct_Invoice_PerTax
         UNION
         SELECT 'N' AS IsOneLinePerInvoiceTax,
                DebitOrCreditIndicator,
                Currency,
                dr_account,
                cr_account,
                amt,
                GrandTotal,
                taxamt,
                activityname,
                c_activity_id,
                documentno,
                dateacct,
                bpvalue,
                bpname,
                duedate,
                description,
                c_bpartner_id,
                c_invoice_id,
                docbasetype,
                c_tax_rate,
                vatCode,
                c_doctype_name,
                fact_acct_id,
                rv_datev_export_fact_acct_invoice_id,
                ad_client_id,
                ad_org_id
         FROM RV_DATEV_Export_Fact_Acct_Invoice_All
     ) AS invoices
WHERE IsOneLinePerInvoiceTax = get_sysconfig_value('DATEVExportLines_OneLinePerInvoiceTax', 'N')
;
*/
