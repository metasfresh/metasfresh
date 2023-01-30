DROP VIEW IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice_PerTax
;
DROP VIEW IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice_All
;
DROP VIEW IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice
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