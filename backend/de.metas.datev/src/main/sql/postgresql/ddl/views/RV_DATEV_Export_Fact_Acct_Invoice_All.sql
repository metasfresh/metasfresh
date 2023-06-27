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
       i.duedate::timestamp WITH TIME ZONE                                              AS duedate,
       fa.description,
       bp.c_bpartner_id,
       fa.record_id                                                                     AS c_invoice_id,
       fa.docbasetype,
       CASE
           -- When the tax have:
           --  1. The rate equal to zero
           --  2. The Type dest. country = EU-foreign
           --  3. The ReqTaxCert = Y
           WHEN (t.rate = 0 AND t.requirestaxcertificate = 'Y' AND t.typeofdestcountry = 'WITHIN_COUNTRY_AREA')
               -- Find the correspondent domestic tax with:
               --  1. The same Tax Category
               --  2. The same Country of origin
               --  3. The Type dest. country = EU-foreign
               --  4. The ReqTaxCert = N
               THEN (SELECT tx.rate
                     FROM c_tax tx
                     WHERE (tx.requirestaxcertificate = 'N' OR tx.requirestaxcertificate IS NULL)
                       AND tx.typeofdestcountry = 'WITHIN_COUNTRY_AREA'
                       AND tx.c_country_id = t.c_country_id
                       AND tx.c_taxcategory_id = t.c_taxcategory_id
                       AND tx.ad_org_id = t.ad_org_id
                     LIMIT 1)
               ELSE t.rate
       END                                                                              AS c_tax_rate,
       CASE
           -- When the tax have:
           --  1. The rate equal to zero
           --  2. The Type dest. country = EU-foreign
           --  3. The ReqTaxCert = Y
           WHEN (t.rate = 0 AND t.requirestaxcertificate = 'Y' AND t.typeofdestcountry = 'WITHIN_COUNTRY_AREA')
               -- Set the vatcode = 9
               THEN '9'
               ELSE fa.vatcode
       END                                                                              AS vatcode,
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
