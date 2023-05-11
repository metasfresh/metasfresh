DROP VIEW IF EXISTS RV_OpenItem$new
;

CREATE OR REPLACE VIEW RV_OpenItem$new AS
SELECT i.ad_org_id,
       i.ad_client_id,
       i.documentno,
       i.c_invoice_id,
       i.c_order_id,
       i.c_bpartner_id,
       i.issotrx,
       i.dateinvoiced,
       i.dateacct,
       p.netdays,
       i.duedate::timestamp WITH TIME ZONE                                AS duedate,
       daysbetween(TRUNC(getdate()), i.DueDate::timestamp WITH TIME ZONE) AS daysdue,
       adddays(i.dateinvoiced::TIMESTAMP WITH TIME ZONE, p.discountdays)  AS discountdate,
       ROUND(i.grandtotal * p.discount / 100::NUMERIC, 2)                 AS discountamt,
       i.grandtotal,
       invoicepaid(i.c_invoice_id, i.c_currency_id, 1::NUMERIC)           AS paidamt,
       invoiceopen(i.c_invoice_id, 0::NUMERIC)                            AS openamt,
       i.c_currency_id,
       i.c_conversiontype_id,
       i.c_paymentterm_id,
       i.ispayschedulevalid,
       NULL::NUMERIC                                                      AS c_invoicepayschedule_id,
       i.invoicecollectiontype,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id
FROM rv_c_invoice i
         JOIN c_paymentterm p ON i.c_paymentterm_id = p.c_paymentterm_id
WHERE invoiceopen(i.c_invoice_id, 0::NUMERIC) <> 0::NUMERIC
  AND i.ispayschedulevalid <> 'Y'::bpchar
  AND (i.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
UNION

SELECT i.ad_org_id,
       i.ad_client_id,
       i.documentno,
       i.c_invoice_id,
       i.c_order_id,
       i.c_bpartner_id,
       i.issotrx,
       i.dateinvoiced,
       i.dateacct,
       daysbetween(COALESCE(ips.duedate, i.duedate)::timestamp WITH TIME ZONE, i.dateinvoiced::timestamp WITH TIME ZONE) AS netdays,
       ips.duedate,
       daysbetween(getdate(), COALESCE(ips.duedate, i.duedate)::timestamp WITH TIME ZONE)                                AS daysdue,
       ips.discountdate,
       ips.discountamt,
       ips.dueamt                                                                                                        AS grandtotal,
       invoicepaid(i.c_invoice_id, i.c_currency_id, 1::numeric)                                                          AS paidamt,
       invoiceopen(i.c_invoice_id, ips.c_invoicepayschedule_id)                                                          AS openamt,
       i.c_currency_id,
       i.c_conversiontype_id,
       i.c_paymentterm_id,
       i.ispayschedulevalid,
       ips.c_invoicepayschedule_id,
       i.invoicecollectiontype,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id
FROM rv_c_invoice i
         JOIN c_invoicepayschedule ips ON i.c_invoice_id = ips.c_invoice_id
WHERE invoiceopen(i.c_invoice_id, ips.c_invoicepayschedule_id) <> 0::numeric
  AND i.ispayschedulevalid = 'Y'::bpchar
  AND (i.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
  AND ips.isvalid = 'Y'::bpchar
;

SELECT db_alter_view(
               'rv_openitem',
               (SELECT view_definition
                FROM information_schema.views
                WHERE views.table_name = 'rv_openitem$new')
           )
;


DROP VIEW IF EXISTS rv_openitem$new
;