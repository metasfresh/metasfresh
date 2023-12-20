
--
-- this view comes from a customer-repo and was meanwhile superseeded in the master-branch
-- see backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/system/10-de.metas.adempiere/5684561_sys_gh14679_gh14602_migrate_RV_DATEV_Export_Fact_Acct_Invoice_views.sql
--

/*
DROP VIEW IF EXISTS rv_datev_export_fact_acct_invoice
;

DROP VIEW IF EXISTS rv_datev_export_fact_acct_invoice_pertax
;

CREATE VIEW rv_datev_export_fact_acct_invoice_pertax (debitorcreditindicator, currency, dr_account, cr_account, amt, grandtotal, taxamt, activityname, c_activity_id, documentno, dateacct, bpvalue, bpname, duedate, description, c_bpartner_id, c_invoice_id, docbasetype, c_tax_rate, vatcode, c_doctype_name, fact_acct_id, rv_datev_export_fact_acct_invoice_id, ad_client_id, ad_org_id) AS
SELECT d.debitorcreditindicator,
       d.currency,
       d.dr_account,
       d.cr_account,
       SUM(d.amt)          AS amt,
       d.invoicetotal      AS grandtotal,
       d.taxamt,
       d.activityname,
       d.c_activity_id,
       d.documentno,
       d.dateacct,
       d.bpvalue,
       d.bpname,
       d.duedate,
       d.documentno        AS description,
       d.c_bpartner_id,
       d.c_invoice_id,
       d.docbasetype,
       d.c_tax_rate,
       d.vatcode,
       d.c_doctype_name,
       MIN(d.fact_acct_id) AS fact_acct_id,
       MIN(d.fact_acct_id) AS rv_datev_export_fact_acct_invoice_id,
       d.ad_client_id,
       d.ad_org_id
FROM (SELECT get_sysconfig_value('DATEVExportLines_OneLinePerInvoiceTax'::character varying, 'N'::character varying) AS isonelineperinvoicetax,
             CASE
                 WHEN fa.amtacctdr <> 0::numeric THEN 'S'::text
                                                 ELSE 'H'::text
             END                                                                                                     AS debitorcreditindicator,
             (SELECT cur.iso_code
              FROM c_currency cur
              WHERE cur.c_currency_id = fa.c_currency_id)                                                            AS currency,
             CASE
                 WHEN bp.debtorid > 0::numeric THEN bp.debtorid::character varying
                                               ELSE
                                                   CASE
                                                       WHEN fa.amtacctdr <> 0::numeric THEN ev.value
                                                                                       ELSE ev2.value
                                                   END
             END                                                                                                     AS dr_account,
             CASE
                 WHEN bp.creditorid > 0::numeric THEN bp.creditorid::character varying
                                                 ELSE
                                                     CASE
                                                         WHEN fa.amtacctdr <> 0::numeric THEN ev2.value
                                                                                         ELSE ev.value
                                                     END
             END                                                                                                     AS cr_account,
             CASE
                 WHEN fa.amtacctdr <> 0::numeric THEN fa.amtacctdr
                                                 ELSE fa.amtacctcr
             END                                                                                                     AS amt,
             i.grandtotal                                                                                            AS invoicetotal,
             it.taxamt,
             a.name                                                                                                  AS activityname,
             a.c_activity_id,
             fa.documentno,
             fa.dateacct,
             bp.value                                                                                                AS bpvalue,
             bp.name                                                                                                 AS bpname,
             paymenttermduedate(i.c_paymentterm_id, i.dateinvoiced::timestamp WITH TIME ZONE)                        AS duedate,
             fa.description,
             bp.c_bpartner_id,
             fa.record_id                                                                                            AS c_invoice_id,
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
             dt.name                                                                                                 AS c_doctype_name,
             fa.fact_acct_id,
             fa.fact_acct_id                                                                                         AS rv_datev_export_fact_acct_invoice_id,
             fa.ad_client_id,
             fa.ad_org_id
      FROM fact_acct fa
               JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
               JOIN fact_acct fa2 ON fa2.c_tax_id IS NULL AND fa2.ad_table_id = fa.ad_table_id AND fa2.record_id = fa.record_id
               JOIN c_elementvalue ev2 ON ev2.c_elementvalue_id = fa2.account_id
               JOIN c_bpartner bp ON bp.c_bpartner_id = fa.c_bpartner_id
               LEFT JOIN c_activity a ON a.c_activity_id = COALESCE(fa.c_activity_id, fa2.c_activity_id)
               JOIN c_invoice i ON i.c_invoice_id = fa.record_id
               JOIN c_invoicetax it ON i.c_invoice_id = it.c_invoice_id AND it.c_tax_id = fa.c_tax_id
               JOIN c_tax t ON t.c_tax_id = fa.c_tax_id
               JOIN c_doctype dt ON dt.c_doctype_id = fa.c_doctype_id
      WHERE fa.ad_table_id = get_table_id('C_Invoice'::character varying)) d
WHERE TRUE
  AND (d.amt <> d.taxamt OR d.amt = 0::numeric AND d.taxamt = 0::numeric)
GROUP BY d.debitorcreditindicator, d.currency, d.dr_account, d.cr_account, d.invoicetotal, d.taxamt, d.activityname, d.c_activity_id, d.documentno, d.dateacct, d.bpvalue, d.bpname, d.duedate, d.c_bpartner_id, d.c_invoice_id, d.docbasetype, d.c_tax_rate, d.vatcode, d.c_doctype_name, d.ad_client_id, d.ad_org_id
;

ALTER TABLE rv_datev_export_fact_acct_invoice_pertax
    OWNER TO metasfresh
;
---------------------------------------------------------

DROP VIEW IF EXISTS rv_datev_export_fact_acct_invoice_all
;

CREATE VIEW rv_datev_export_fact_acct_invoice_all (debitorcreditindicator, currency, dr_account, cr_account, amt, grandtotal, taxamt, activityname, c_activity_id, documentno, dateacct, bpvalue, bpname, duedate, description, c_bpartner_id, c_invoice_id, docbasetype, c_tax_rate, vatcode, c_doctype_name, fact_acct_id, rv_datev_export_fact_acct_invoice_id, ad_client_id, ad_org_id) AS
SELECT CASE
           WHEN fa.amtacctdr <> 0::numeric THEN 'S'::text
                                           ELSE 'H'::text
       END                                                                              AS debitorcreditindicator,
       (SELECT cur.iso_code
        FROM c_currency cur
        WHERE cur.c_currency_id = fa.c_currency_id)                                     AS currency,
       CASE
           WHEN bp.debtorid > 0::numeric THEN bp.debtorid::character varying
                                         ELSE
                                             CASE
                                                 WHEN fa.amtacctdr <> 0::numeric THEN ev.value
                                                                                 ELSE ev2.value
                                             END
       END                                                                              AS dr_account,
       CASE
           WHEN bp.creditorid > 0::numeric THEN bp.creditorid::character varying
                                           ELSE
                                               CASE
                                                   WHEN fa.amtacctdr <> 0::numeric THEN ev2.value
                                                                                   ELSE ev.value
                                               END
       END                                                                              AS cr_account,
       CASE
           WHEN fa.amtacctdr <> 0::numeric THEN fa.amtacctdr
                                           ELSE fa.amtacctcr
       END                                                                              AS amt,
       i.grandtotal,
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

ALTER TABLE rv_datev_export_fact_acct_invoice_all
    OWNER TO metasfresh
;


----------------------------------------------------------

CREATE VIEW rv_datev_export_fact_acct_invoice (debitorcreditindicator, currency, dr_account, cr_account, amt, grandtotal, taxamt, activityname, c_activity_id, documentno, dateacct, bpvalue, bpname, duedate, description, c_bpartner_id, c_invoice_id, docbasetype, c_tax_rate, vatcode, c_doctype_name, fact_acct_id, rv_datev_export_fact_acct_invoice_id, ad_client_id, ad_org_id) AS
SELECT invoices.debitorcreditindicator,
       invoices.currency,
       invoices.dr_account,
       invoices.cr_account,
       invoices.amt,
       invoices.grandtotal,
       invoices.taxamt,
       invoices.activityname,
       invoices.c_activity_id,
       invoices.documentno,
       invoices.dateacct,
       invoices.bpvalue,
       invoices.bpname,
       invoices.duedate,
       invoices.description,
       invoices.c_bpartner_id,
       invoices.c_invoice_id,
       invoices.docbasetype,
       invoices.c_tax_rate,
       invoices.vatcode,
       invoices.c_doctype_name,
       invoices.fact_acct_id,
       invoices.rv_datev_export_fact_acct_invoice_id,
       invoices.ad_client_id,
       invoices.ad_org_id
FROM (SELECT 'Y'::text AS isonelineperinvoicetax,
             rv_datev_export_fact_acct_invoice_pertax.debitorcreditindicator,
             rv_datev_export_fact_acct_invoice_pertax.currency,
             rv_datev_export_fact_acct_invoice_pertax.dr_account,
             rv_datev_export_fact_acct_invoice_pertax.cr_account,
             rv_datev_export_fact_acct_invoice_pertax.amt,
             rv_datev_export_fact_acct_invoice_pertax.grandtotal,
             rv_datev_export_fact_acct_invoice_pertax.taxamt,
             rv_datev_export_fact_acct_invoice_pertax.activityname,
             rv_datev_export_fact_acct_invoice_pertax.c_activity_id,
             rv_datev_export_fact_acct_invoice_pertax.documentno,
             rv_datev_export_fact_acct_invoice_pertax.dateacct,
             rv_datev_export_fact_acct_invoice_pertax.bpvalue,
             rv_datev_export_fact_acct_invoice_pertax.bpname,
             rv_datev_export_fact_acct_invoice_pertax.duedate,
             rv_datev_export_fact_acct_invoice_pertax.description,
             rv_datev_export_fact_acct_invoice_pertax.c_bpartner_id,
             rv_datev_export_fact_acct_invoice_pertax.c_invoice_id,
             rv_datev_export_fact_acct_invoice_pertax.docbasetype,
             rv_datev_export_fact_acct_invoice_pertax.c_tax_rate,
             rv_datev_export_fact_acct_invoice_pertax.vatcode,
             rv_datev_export_fact_acct_invoice_pertax.c_doctype_name,
             rv_datev_export_fact_acct_invoice_pertax.fact_acct_id,
             rv_datev_export_fact_acct_invoice_pertax.rv_datev_export_fact_acct_invoice_id,
             rv_datev_export_fact_acct_invoice_pertax.ad_client_id,
             rv_datev_export_fact_acct_invoice_pertax.ad_org_id
      FROM rv_datev_export_fact_acct_invoice_pertax
      UNION
      SELECT 'N'::text AS isonelineperinvoicetax,
             rv_datev_export_fact_acct_invoice_all.debitorcreditindicator,
             rv_datev_export_fact_acct_invoice_all.currency,
             rv_datev_export_fact_acct_invoice_all.dr_account,
             rv_datev_export_fact_acct_invoice_all.cr_account,
             rv_datev_export_fact_acct_invoice_all.amt,
             rv_datev_export_fact_acct_invoice_all.grandtotal,
             rv_datev_export_fact_acct_invoice_all.taxamt,
             rv_datev_export_fact_acct_invoice_all.activityname,
             rv_datev_export_fact_acct_invoice_all.c_activity_id,
             rv_datev_export_fact_acct_invoice_all.documentno,
             rv_datev_export_fact_acct_invoice_all.dateacct,
             rv_datev_export_fact_acct_invoice_all.bpvalue,
             rv_datev_export_fact_acct_invoice_all.bpname,
             rv_datev_export_fact_acct_invoice_all.duedate,
             rv_datev_export_fact_acct_invoice_all.description,
             rv_datev_export_fact_acct_invoice_all.c_bpartner_id,
             rv_datev_export_fact_acct_invoice_all.c_invoice_id,
             rv_datev_export_fact_acct_invoice_all.docbasetype,
             rv_datev_export_fact_acct_invoice_all.c_tax_rate,
             rv_datev_export_fact_acct_invoice_all.vatcode,
             rv_datev_export_fact_acct_invoice_all.c_doctype_name,
             rv_datev_export_fact_acct_invoice_all.fact_acct_id,
             rv_datev_export_fact_acct_invoice_all.rv_datev_export_fact_acct_invoice_id,
             rv_datev_export_fact_acct_invoice_all.ad_client_id,
             rv_datev_export_fact_acct_invoice_all.ad_org_id
      FROM rv_datev_export_fact_acct_invoice_all) invoices
WHERE invoices.isonelineperinvoicetax = get_sysconfig_value('DATEVExportLines_OneLinePerInvoiceTax'::character varying, 'N'::character varying)::text
;

ALTER TABLE rv_datev_export_fact_acct_invoice
    OWNER TO metasfresh
;
*/
