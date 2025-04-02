DROP VIEW c_invoice_v1;

CREATE OR REPLACE VIEW c_invoice_v1 AS
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
       i.processing,
       i.processed,
       i.c_doctype_id,
       i.c_doctypetarget_id,
       i.c_order_id,
       i.description,
       i.isapproved,
       i.istransferred,
       i.salesrep_id,
       i.dateinvoiced,
       i.dateprinted,
       i.dateacct,
       i.c_bpartner_id,
       i.c_bpartner_location_id,
       i.ad_user_id,
       i.poreference,
       i.dateordered,
       i.c_currency_id,
       i.c_conversiontype_id,
       i.paymentrule,
       i.c_paymentterm_id,
       i.c_charge_id,
       i.m_pricelist_id,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id,
       i.isprinted,
       i.isdiscountprinted,
       i.ispaid,
       i.isindispute,
       i.ispayschedulevalid,
       NULL::numeric AS c_invoicepayschedule_id,
       i.invoicecollectiontype,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.chargeamt * (-1)::numeric
                                                                              ELSE i.chargeamt
       END           AS chargeamt,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.totallines * (-1)::numeric
                                                                              ELSE i.totallines
       END           AS totallines,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.grandtotal * (-1)::numeric
                                                                              ELSE i.grandtotal
       END           AS grandtotal,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN inv_tax.taxbaseamt * (-1)::numeric
                                                                              ELSE inv_tax.taxbaseamt
       END           AS taxbaseamt,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN inv_tax.taxamt * (-1)::numeric
                                                                              ELSE inv_tax.taxamt
       END           AS taxamt,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN (-1)
                                                                              ELSE 1
       END           AS multiplier,
       CASE
           WHEN charat(d.docbasetype::character varying, 2)::text = 'P'::text THEN (-1)
                                                                              ELSE 1
       END           AS multiplierap,
       d.docbasetype,
       i.Posted,
       inv_tax.c_tax_id
FROM c_invoice i
         JOIN C_InvoiceTax inv_tax ON i.c_invoice_id = inv_tax.c_invoice_id
         JOIN c_doctype d ON i.c_doctype_id = d.c_doctype_id
;