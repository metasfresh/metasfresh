DROP VIEW IF EXISTS rv_sales_invoice_report
;

CREATE VIEW rv_sales_invoice_report
    (DocTypeName, ProductName, ProductCategory, BPName, BPGroupName, SalesRep_Name, DateInvoiced, DeliveryDate, InvoicedQty, LineNetAmt)
AS
SELECT DISTINCT dt.name                                                                                  AS DocTypeName,
                mp.name                                                                                  AS ProductName,
                mpc.name                                                                                 AS ProductCategory,
                billbp.name                                                                              AS BPName,
                bpg.name                                                                                 AS BPGroupName,
                vp.name                                                                                  AS SalesRep_Name,
                i.dateinvoiced::date                                                                     AS DateInvoiced,
                ic.deliverydate::date                                                                    AS DeliveryDate,
                il.qtyentered * (CASE WHEN dt.docbasetype IN ('APC', 'ARC') THEN -1 ELSE 1 END)::numeric AS InvoicedQty,
                il.linenetamt * (CASE WHEN dt.docbasetype IN ('APC', 'ARC') THEN -1 ELSE 1 END)::numeric AS LineNetAmt


FROM c_invoice i
         LEFT JOIN c_doctype dt ON i.c_doctype_id = dt.c_doctype_id
         LEFT JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN c_invoice_line_alloc ila ON ila.c_invoiceline_id = il.c_invoiceline_id
         LEFT JOIN c_invoice_candidate ic ON ila.c_invoice_candidate_id = ic.c_invoice_candidate_id
         LEFT JOIN c_bpartner billbp ON i.c_bpartner_id = billbp.c_bpartner_id
         LEFT JOIN c_bpartner vp ON vp.c_bpartner_id = COALESCE(billbp.c_bpartner_salesrep_id, billbp.c_bpartner_id)
         LEFT JOIN c_bp_group bpg ON billbp.c_bp_group_id = bpg.c_bp_group_id
         LEFT JOIN m_product mp ON il.m_product_id = mp.m_product_id
         LEFT JOIN m_product_category mpc ON mp.m_product_category_id = mpc.m_product_category_id


WHERE i.docstatus = 'CO'::bpchar
  AND i.issotrx = 'Y'
;

ALTER TABLE rv_sales_invoice_report
    OWNER TO metasfresh
;
