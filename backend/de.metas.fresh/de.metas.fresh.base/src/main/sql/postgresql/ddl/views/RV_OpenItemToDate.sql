DROP VIEW IF EXISTS rv_openitemtodate$new
;


CREATE VIEW rv_openitemtodate$new AS
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
       i.DueDate                                                          AS duedate,
       daysbetween(TRUNC(getdate()), i.DueDate::timestamp WITH TIME ZONE) AS daysdue,
       adddays(i.dateinvoiced::timestamp WITH TIME ZONE, p.discountdays)  AS discountdate,
       ROUND(i.grandtotal * p.discount / 100::numeric, 2)                 AS discountamt,
       i.grandtotal,
       i.c_currency_id,
       i.c_conversiontype_id,
       i.c_paymentterm_id,
       i.ispayschedulevalid,
       NULL::numeric                                                      AS c_invoicepayschedule_id,
       i.invoicecollectiontype,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id
FROM rv_c_invoice i
         JOIN c_paymentterm p ON i.c_paymentterm_id = p.c_paymentterm_id
WHERE i.ispayschedulevalid <> 'Y'::bpchar
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
       daysbetween(ips.duedate::timestamp WITH TIME ZONE, i.dateinvoiced::timestamp WITH TIME ZONE) AS netdays,
       ips.duedate,
       daysbetween(getdate(), ips.duedate::timestamp WITH TIME ZONE)                                AS daysdue,
       ips.discountdate,
       ips.discountamt,
       ips.dueamt                                                                                   AS grandtotal,
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
WHERE i.ispayschedulevalid = 'Y'::bpchar
  AND (i.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
  AND ips.isvalid = 'Y'::bpchar
;


SELECT db_alter_view(
               'rv_openitemtodate',
               (SELECT view_definition
                FROM information_schema.views
                WHERE views.table_name = 'rv_openitemtodate$new')
           )
;


DROP VIEW IF EXISTS rv_openitemtodate$new
;
