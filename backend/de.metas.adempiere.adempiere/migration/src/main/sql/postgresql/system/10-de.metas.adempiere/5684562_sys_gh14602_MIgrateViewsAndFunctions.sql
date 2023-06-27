------------------- rv_c_invoice --------
DROP VIEW IF EXISTS rv_c_invoice$new
;

CREATE OR REPLACE VIEW rv_c_invoice$new AS
SELECT i.c_invoice_id,
       i.ad_client_id,
       i.ad_org_id,
       i.isactive,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.issotrx,
       i.documentno,
       i.docstatus,
       i.docaction,
       i.isprinted,
       i.isdiscountprinted,
       i.processing,
       i.processed,
       i.istransferred,
       i.ispaid,
       i.c_doctype_id,
       i.c_doctypetarget_id,
       i.c_order_id,
       i.description,
       i.isapproved,
       i.salesrep_id,
       i.dateinvoiced,
       i.dateprinted,
       i.dateacct,
       i.duedate,
       i.c_bpartner_id,
       i.c_bpartner_location_id,
       i.ad_user_id,
       b.c_bp_group_id,
       i.poreference,
       i.dateordered,
       i.c_currency_id,
       i.c_conversiontype_id,
       i.paymentrule,
       i.c_paymentterm_id,
       i.m_pricelist_id,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id,
       i.ispayschedulevalid,
       i.invoicecollectiontype,
       loc.c_country_id,
       loc.c_region_id,
       loc.postal,
       loc.city,
       i.c_charge_id,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.chargeamt * '-1'::integer::numeric
                                                                              ELSE i.chargeamt
       END AS chargeamt,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.totallines * '-1'::integer::numeric
                                                                              ELSE i.totallines
       END AS totallines,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.grandtotal * '-1'::integer::numeric
                                                                              ELSE i.grandtotal
       END AS grandtotal,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN '-1'::integer
                                                                              ELSE 1
       END AS multiplier
FROM c_invoice i
         JOIN c_doctype d ON i.c_doctype_id = d.c_doctype_id
         JOIN c_bpartner b ON i.c_bpartner_id = b.c_bpartner_id
         JOIN c_bpartner_location bpl ON i.c_bpartner_location_id = bpl.c_bpartner_location_id
         JOIN c_location loc ON bpl.c_location_id = loc.c_location_id
;

;

SELECT db_alter_view(
               'rv_c_invoice',
               (SELECT view_definition
                FROM information_schema.views
                WHERE views.table_name = 'rv_c_invoice$new')
           )
;

DROP VIEW IF EXISTS rv_c_invoice$new
;


------------------- RV_OpenItem --------
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



------------------- rv_bpartneropen --------
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



------------rv_openitemtodate

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


DROP FUNCTION paymenttermduedays(paymentterm_id numeric,
                                 docdate        timestamp WITH TIME ZONE,
                                 paydate        timestamp WITH TIME ZONE)
;




DROP FUNCTION paymenttermduedate(paymentterm_id numeric,
                                 docdate        timestamp WITH TIME ZONE)
;
