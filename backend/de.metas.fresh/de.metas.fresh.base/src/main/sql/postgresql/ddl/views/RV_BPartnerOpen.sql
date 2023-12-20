DROP VIEW IF EXISTS rv_bpartneropen$new
;

CREATE VIEW rv_bpartneropen$new AS
SELECT i.ad_client_id,
       i.ad_org_id,
       i.isactive,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.c_bpartner_id,
       i.c_currency_id,
       i.grandtotal * i.multiplierap                                                      AS amt,
       invoiceopen(i.c_invoice_id, i.c_invoicepayschedule_id) * i.multiplierap            AS openamt,
       i.dateinvoiced                                                                     AS datedoc,
       DaysBetween(getdate(), COALESCE(ips.DueDate, i.DueDate)::timestamp WITH TIME ZONE) AS DaysDue,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id
FROM c_invoice_v i
         LEFT JOIN c_invoicepayschedule ips ON i.c_invoicepayschedule_id = ips.c_invoicepayschedule_id
WHERE i.ispaid = 'N'::bpchar
  AND (i.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
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
       p.payamt * p.multiplierap::numeric * (- 1::numeric)                         AS amt,
       paymentavailable(p.c_payment_id) * p.multiplierap::numeric * (- 1::numeric) AS openamt,
       p.datetrx                                                                   AS datedoc,
       NULL::integer                                                               AS daysdue,
       p.c_campaign_id,
       p.c_project_id,
       p.c_activity_id
FROM c_payment_v p
WHERE p.isallocated = 'N'::bpchar
  AND p.c_bpartner_id IS NOT NULL
  AND (p.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
;


SELECT db_alter_view(
               'rv_bpartneropen',
               (SELECT view_definition
                FROM information_schema.views
                WHERE views.table_name = 'rv_bpartneropen$new')
           )
;

DROP VIEW IF EXISTS rv_bpartneropen$new
;
