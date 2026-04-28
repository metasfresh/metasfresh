DROP VIEW RV_DATEV_Export_Fact_Acct_Invoice
;

CREATE VIEW RV_DATEV_Export_Fact_Acct_Invoice (DebitOrCreditIndicator, Currency, dr_account, cr_account, amt, activityname, c_activity_id, documentno, dateacct, bpvalue, bpname, duedate, description, c_bpartner_id, c_invoice_id, docbasetype, fact_acct_id, rv_datev_export_fact_acct_invoice_id, ad_client_id, ad_org_id) AS
SELECT CASE
           WHEN fa.amtacctdr <> 0::numeric THEN 'S'
                                           ELSE 'H'
       END                                                                              AS DebitOrCreditIndicator,
       (SELECT cur.iso_code FROM c_currency cur WHERE cur.c_currency_id = fa.c_currency_id)
                                                                                        AS Currency,
       CASE
           WHEN fa.amtacctdr <> 0::numeric THEN ev.value
                                           ELSE ev2.value
       END                                                                              AS dr_account,
       CASE
           WHEN fa.amtacctdr <> 0::numeric THEN ev2.value
                                           ELSE ev.value
       END                                                                              AS cr_account,
       CASE
           WHEN fa.amtacctdr <> 0::numeric THEN fa.amtacctdr
                                           ELSE fa.amtacctcr
       END                                                                              AS amt,
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
       fa.fact_acct_id,
       fa.fact_acct_id                                                                  AS rv_datev_export_fact_acct_invoice_id,
       fa.ad_client_id,
       fa.ad_org_id
FROM fact_acct fa
         JOIN fact_acct fa2 ON fa2.fact_acct_id = fa.counterpart_fact_acct_id
         JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
         JOIN c_elementvalue ev2 ON ev2.c_elementvalue_id = fa2.account_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = fa.c_bpartner_id
         LEFT JOIN c_activity a ON a.c_activity_id = COALESCE(fa.c_activity_id, fa2.c_activity_id)
         JOIN c_invoice i ON i.c_invoice_id = fa.record_id
WHERE fa.ad_table_id = get_table_id('C_Invoice'::character varying)
;

ALTER TABLE RV_DATEV_Export_Fact_Acct_Invoice
    OWNER TO metasfresh
;