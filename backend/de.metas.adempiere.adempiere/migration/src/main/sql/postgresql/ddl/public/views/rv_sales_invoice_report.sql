DROP VIEW IF EXISTS rv_sales_invoice_report
;

CREATE VIEW rv_sales_invoice_report
            (DocTypeName, ProductValue, ProductName, Postal, ProductCategory, BPValue, BPName, BPGroupName, SalesRep_Name, DateInvoiced, DeliveryDate,
             InvoiceDocumentNo, InvoicedQty, LineNetAmt, LineGrossAmt)
AS
SELECT DISTINCT dt.name                                                                                                    AS DocTypeName,
                mp.value                                                                                                   AS ProductValue,
                mp.name                                                                                                    AS ProductName,
                loc.postal                                                                                                 AS Postal,
                mpc.name                                                                                                   AS ProductCategory,
                billbp.value                                                                                               AS BPValue,
                billbp.name                                                                                                AS BPName,
                bpg.name                                                                                                   AS BPGroupName,
                vp.name                                                                                                    AS SalesRep_Name,
                i.dateinvoiced::DATE                                                                                       AS DateInvoiced,
                ic.deliverydate::DATE                                                                                      AS DeliveryDate,
                i.documentno                                                                                               AS InvoiceDocumentNo,
                il.qtyentered * (CASE WHEN dt.docbasetype IN ('APC', 'ARC') THEN -1 ELSE 1 END)::NUMERIC                   AS InvoicedQty,
                il.linenetamt * (CASE WHEN dt.docbasetype IN ('APC', 'ARC') THEN -1 ELSE 1 END)::NUMERIC                   AS LineNetAmt,
                (il.taxamtinfo + il.linenetamt) * (CASE WHEN dt.docbasetype IN ('APC', 'ARC') THEN -1 ELSE 1 END)::NUMERIC AS LineGrossAmt


FROM c_invoice i
         LEFT JOIN c_doctype dt
                   ON i.c_doctype_id = dt.c_doctype_id
         LEFT JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN c_invoice_line_alloc ila ON ila.c_invoiceline_id = il.c_invoiceline_id
         LEFT JOIN c_invoice_candidate ic ON ila.c_invoice_candidate_id = ic.c_invoice_candidate_id
         LEFT JOIN C_BPartner_Location bploc ON ic.Bill_Location_ID = bploc.C_BPartner_Location_ID
         LEFT JOIN C_Location loc ON bploc.C_Location_ID = loc.C_Location_ID
         LEFT JOIN c_bpartner billbp ON i.c_bpartner_id = billbp.c_bpartner_id
         LEFT JOIN AD_User vp ON vp.ad_user_id = billbp.salesrep_id
         LEFT JOIN c_bp_group bpg ON billbp.c_bp_group_id = bpg.c_bp_group_id
         LEFT JOIN m_product mp ON il.m_product_id = mp.m_product_id
         LEFT JOIN m_product_category mpc ON mp.m_product_category_id = mpc.m_product_category_id


WHERE i.docstatus = 'CO'::bpchar
  AND i.issotrx = 'Y'
;

ALTER TABLE rv_sales_invoice_report
    OWNER TO metasfresh
;
