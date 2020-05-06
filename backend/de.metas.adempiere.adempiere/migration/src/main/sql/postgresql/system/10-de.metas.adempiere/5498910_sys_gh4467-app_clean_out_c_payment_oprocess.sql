--
-- PaymentOnline
--
-- 2018-08-07T10:53:18.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=153
;

-- 2018-08-07T10:53:18.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=153
;

--
-- C_Payment.OProcessing
--
DELETE FROM ad_ui_element WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=5356);
DELETE FROM AD_Field WHERE AD_Column_ID=5356;
-- 2018-08-07T11:01:24.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=5356
;

-- 2018-08-07T11:01:24.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=5356
;

DROP VIEW IF EXISTS public.c_bpartner_openamounts_v;
DROP VIEW IF EXISTS public.rv_bpartneropen;
DROP VIEW IF EXISTS public.rv_payment;
DROP VIEW IF EXISTS public.c_payment_v;

SELECT db_alter_table('C_Payment','ALTER TABLE C_Payment DROP COLUMN OProcessing;');

CREATE OR REPLACE VIEW public.rv_payment AS 
 SELECT c_payment.c_payment_id,
    c_payment.ad_client_id,
    c_payment.ad_org_id,
    c_payment.isactive,
    c_payment.created,
    c_payment.createdby,
    c_payment.updated,
    c_payment.updatedby,
    c_payment.documentno,
    c_payment.datetrx,
    c_payment.isreceipt,
    c_payment.c_doctype_id,
    c_payment.trxtype,
    c_payment.c_bankaccount_id,
    c_payment.c_bpartner_id,
    c_payment.c_invoice_id,
    c_payment.c_bp_bankaccount_id,
    c_payment.c_paymentbatch_id,
    c_payment.tendertype,
    c_payment.creditcardtype,
    c_payment.creditcardnumber,
    c_payment.creditcardvv,
    c_payment.creditcardexpmm,
    c_payment.creditcardexpyy,
    c_payment.micr,
    c_payment.routingno,
    c_payment.accountno,
    c_payment.checkno,
    c_payment.a_name,
    c_payment.a_street,
    c_payment.a_city,
    c_payment.a_state,
    c_payment.a_zip,
    c_payment.a_ident_dl,
    c_payment.a_ident_ssn,
    c_payment.a_email,
    c_payment.voiceauthcode,
    c_payment.orig_trxid,
    c_payment.ponum,
    c_payment.c_currency_id,
    c_payment.c_conversiontype_id,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.payamt
            ELSE c_payment.payamt * (- 1::numeric)
        END AS payamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.discountamt
            ELSE c_payment.discountamt * (- 1::numeric)
        END AS discountamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.writeoffamt
            ELSE c_payment.writeoffamt * (- 1::numeric)
        END AS writeoffamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.taxamt
            ELSE c_payment.taxamt * (- 1::numeric)
        END AS taxamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.overunderamt
            ELSE c_payment.overunderamt * (- 1::numeric)
        END AS overunderamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN 1
            ELSE '-1'::integer
        END AS multiplierap,
    paymentallocated(c_payment.c_payment_id, c_payment.c_currency_id) AS allocatedamt,
    paymentavailable(c_payment.c_payment_id) AS availableamt,
    c_payment.isoverunderpayment,
    c_payment.isapproved,
    c_payment.r_pnref,
    c_payment.r_result,
    c_payment.r_respmsg,
    c_payment.r_authcode,
    c_payment.r_avsaddr,
    c_payment.r_avszip,
    c_payment.r_info,
    c_payment.processing,
    c_payment.docstatus,
    c_payment.docaction,
    c_payment.isprepayment,
    c_payment.c_charge_id,
    c_payment.isreconciled,
    c_payment.isallocated,
    c_payment.isonline,
    c_payment.processed,
    c_payment.posted,
    c_payment.c_campaign_id,
    c_payment.c_project_id,
    c_payment.c_activity_id
   FROM c_payment;
   

CREATE OR REPLACE VIEW public.c_payment_v AS 
 SELECT c_payment.c_payment_id,
    c_payment.ad_client_id,
    c_payment.ad_org_id,
    c_payment.isactive,
    c_payment.created,
    c_payment.createdby,
    c_payment.updated,
    c_payment.updatedby,
    c_payment.documentno,
    c_payment.datetrx,
    c_payment.isreceipt,
    c_payment.c_doctype_id,
    c_payment.trxtype,
    c_payment.c_bankaccount_id,
    c_payment.c_bpartner_id,
    c_payment.c_invoice_id,
    c_payment.c_bp_bankaccount_id,
    c_payment.c_paymentbatch_id,
    c_payment.tendertype,
    c_payment.creditcardtype,
    c_payment.creditcardnumber,
    c_payment.creditcardvv,
    c_payment.creditcardexpmm,
    c_payment.creditcardexpyy,
    c_payment.micr,
    c_payment.routingno,
    c_payment.accountno,
    c_payment.checkno,
    c_payment.a_name,
    c_payment.a_street,
    c_payment.a_city,
    c_payment.a_state,
    c_payment.a_zip,
    c_payment.a_ident_dl,
    c_payment.a_ident_ssn,
    c_payment.a_email,
    c_payment.voiceauthcode,
    c_payment.orig_trxid,
    c_payment.ponum,
    c_payment.c_currency_id,
    c_payment.c_conversiontype_id,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.payamt
            ELSE c_payment.payamt * (- 1::numeric)
        END AS payamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.discountamt
            ELSE c_payment.discountamt * (- 1::numeric)
        END AS discountamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.writeoffamt
            ELSE c_payment.writeoffamt * (- 1::numeric)
        END AS writeoffamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.taxamt
            ELSE c_payment.taxamt * (- 1::numeric)
        END AS taxamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN c_payment.overunderamt
            ELSE c_payment.overunderamt * (- 1::numeric)
        END AS overunderamt,
        CASE c_payment.isreceipt
            WHEN 'Y'::bpchar THEN 1
            ELSE '-1'::integer
        END AS multiplierap,
    c_payment.isoverunderpayment,
    c_payment.isapproved,
    c_payment.r_pnref,
    c_payment.r_result,
    c_payment.r_respmsg,
    c_payment.r_authcode,
    c_payment.r_avsaddr,
    c_payment.r_avszip,
    c_payment.r_info,
    c_payment.processing,
    c_payment.docstatus,
    c_payment.docaction,
    c_payment.isprepayment,
    c_payment.c_charge_id,
    c_payment.isreconciled,
    c_payment.isallocated,
    c_payment.isonline,
    c_payment.processed,
    c_payment.posted,
    c_payment.c_campaign_id,
    c_payment.c_project_id,
    c_payment.c_activity_id,
    c_payment.dateacct
   FROM c_payment;


CREATE OR REPLACE VIEW public.rv_bpartneropen AS 
 SELECT i.ad_client_id,
    i.ad_org_id,
    i.isactive,
    i.created,
    i.createdby,
    i.updated,
    i.updatedby,
    i.c_bpartner_id,
    i.c_currency_id,
    i.grandtotal * i.multiplierap AS amt,
    invoiceopen(i.c_invoice_id, i.c_invoicepayschedule_id) * i.multiplierap AS openamt,
    i.dateinvoiced AS datedoc,
    COALESCE(daysbetween(getdate(), ips.duedate::timestamp with time zone), paymenttermduedays(i.c_paymentterm_id, i.dateinvoiced::timestamp with time zone, getdate())) AS daysdue,
    i.c_campaign_id,
    i.c_project_id,
    i.c_activity_id
   FROM c_invoice_v i
     LEFT JOIN c_invoicepayschedule ips ON i.c_invoicepayschedule_id = ips.c_invoicepayschedule_id
  WHERE i.ispaid = 'N'::bpchar AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))
UNION
 SELECT p.ad_client_id,
    p.ad_org_id,
    p.isactive,
    p.created,
    p.createdby,
    p.updated,
    p.updatedby,
    p.c_bpartner_id,
    p.c_currency_id,
    p.payamt * p.multiplierap::numeric * (- 1::numeric) AS amt,
    paymentavailable(p.c_payment_id) * p.multiplierap::numeric * (- 1::numeric) AS openamt,
    p.datetrx AS datedoc,
    NULL::integer AS daysdue,
    p.c_campaign_id,
    p.c_project_id,
    p.c_activity_id
   FROM c_payment_v p
  WHERE p.isallocated = 'N'::bpchar AND p.c_bpartner_id IS NOT NULL AND (p.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]));


   CREATE OR REPLACE VIEW public.c_bpartner_openamounts_v AS 
 SELECT openamt.c_bpartner_id,
    sum(openamt.openorderamt) AS openorderamt,
    sum(openamt.openinvoiceamt) AS openinvoiceamt,
    sum(openamt.unallocatedpaymentamt) AS unallocatedpaymentamt
   FROM ( SELECT ic.bill_bpartner_id AS c_bpartner_id,
            COALESCE(sum(currencybase(ic.linenetamt +
                CASE
                    WHEN COALESCE(ic.istaxincluded_override, ic.istaxincluded) = 'N'::bpchar AND t.iswholetax = 'N'::bpchar THEN round(ic.linenetamt * round(t.rate / 100::numeric, 12), c.stdprecision)
                    ELSE 0::numeric
                END, ic.c_currency_id, ic.dateordered::timestamp with time zone, ic.ad_client_id, ic.ad_org_id)), 0::numeric) AS openorderamt,
            0 AS openinvoiceamt,
            0 AS unallocatedpaymentamt
           FROM c_invoice_candidate ic
             LEFT JOIN c_tax t ON ic.c_tax_id = t.c_tax_id
             LEFT JOIN c_currency c ON c.c_currency_id = ic.c_currency_id
          WHERE ic.processed = 'N'::bpchar AND ic.issotrx = 'Y'::bpchar
          GROUP BY ic.bill_bpartner_id
        UNION ALL
         SELECT i.c_bpartner_id,
            0 AS openorderamt,
            COALESCE(sum(currencybase(invoiceopen(i.c_invoice_id, i.c_invoicepayschedule_id), i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)), 0::numeric) AS openinvoiceamt,
            0 AS unallocatedpaymentamt
           FROM c_invoice_v i
          WHERE i.issotrx = 'Y'::bpchar AND i.ispaid = 'N'::bpchar AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))
          GROUP BY i.c_bpartner_id
        UNION ALL
         SELECT p.c_bpartner_id,
            0 AS openorderamt,
            0 AS openinvoiceamt,
            COALESCE(sum(currencybase(paymentavailable(p.c_payment_id), p.c_currency_id, p.datetrx::timestamp with time zone, p.ad_client_id, p.ad_org_id)), 0::numeric) * '-1'::integer::numeric AS unallocatedpaymentamt
           FROM c_payment_v p
          WHERE p.isallocated = 'N'::bpchar AND p.c_charge_id IS NULL AND (p.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))
          GROUP BY p.c_bpartner_id) openamt
  GROUP BY openamt.c_bpartner_id;
   