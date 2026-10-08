DROP VIEW IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice
;

CREATE OR REPLACE VIEW RV_DATEV_Export_Fact_Acct_Invoice
            (DebitOrCreditIndicator, Currency, dr_account, cr_account, amt, GrandTotal, taxamt, activityname, c_activity_id, documentno, dateacct, bpvalue, bpname, duedate, description, c_bpartner_id, c_invoice_id, docbasetype,
             c_tax_rate,
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
       dt.name                                                                          AS c_doctype_name,
       fa.fact_acct_id,
       fa.fact_acct_id                                                                  AS rv_datev_export_fact_acct_invoice_id,
       fa.ad_client_id,
       fa.ad_org_id
FROM fact_acct fa
         JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
         JOIN fact_acct fa2 ON fa2.fact_acct_id = fa.counterpart_fact_acct_id
         JOIN c_elementvalue ev2 ON ev2.c_elementvalue_id = fa2.account_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = fa.c_bpartner_id
         LEFT JOIN c_activity a ON a.c_activity_id = COALESCE(fa.c_activity_id, fa2.c_activity_id)
         JOIN c_invoice i ON i.c_invoice_id = fa.record_id
         JOIN c_invoicetax it ON i.c_invoice_id = it.c_invoice_id and it.c_tax_id = fa.c_tax_id
         JOIN c_tax t ON t.c_tax_id = fa.c_tax_id
         JOIN c_doctype dt ON dt.c_doctype_id = fa.c_doctype_id

WHERE fa.ad_table_id = get_table_id('C_Invoice'::character varying)
;