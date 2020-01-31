-- View: edi_cctop_140_v

-- DROP VIEW IF EXISTS edi_cctop_140_v;

CREATE OR REPLACE VIEW edi_cctop_140_v AS 
 SELECT 
    i.c_invoice_id AS edi_cctop_140_v_id, 
    i.c_invoice_id, 
    i.c_invoice_id AS edi_cctop_invoic_v_id, 
    p.discount, 
    p.discountdays, 
    i.dateinvoiced::timestamp with time zone + p.discountdays AS discountdate, 
    t.rate, 
    i.ad_client_id, 
    i.ad_org_id, 
    i.created, 
    i.createdby, 
    i.updated, 
    i.updatedby, 
    i.isactive
 FROM c_invoice i
    LEFT JOIN c_paymentterm p ON p.c_paymentterm_id = i.c_paymentterm_id
    LEFT JOIN c_invoicetax it ON it.c_invoice_id = i.c_invoice_id
    LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id;
