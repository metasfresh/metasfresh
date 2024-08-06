-- View: edi_cctop_140_v

-- DROP VIEW IF EXISTS edi_cctop_140_v;

CREATE OR REPLACE VIEW edi_cctop_140_v AS
SELECT i.c_invoice_id                                                AS edi_cctop_140_v_id,
       i.c_invoice_id,
       i.c_invoice_id                                                AS edi_cctop_invoic_v_id,
       pterm.discount,
       pterm.discountdays,
       i.dateinvoiced::timestamp WITH TIME ZONE + pterm.discountdays AS discountdate,
       t.rate,
       pterm.name,
       ROUND(SUM(it.TaxBaseAmt), 2)                                  AS DiscountBaseAmt,
       ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2)          AS DiscountAmt,
       i.ad_client_id,
       i.ad_org_id,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.isactive
FROM c_invoice i
         LEFT JOIN c_paymentterm pterm ON pterm.c_paymentterm_id = i.c_paymentterm_id
         LEFT JOIN c_invoicetax it ON it.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id
GROUP BY i.c_invoice_id,
         pterm.discount,
         pterm.discountdays,
         i.dateinvoiced,
         pterm.discountdays,
         t.rate,
         pterm.discount,
         i.ad_client_id,
         i.ad_org_id,
         i.created,
         i.createdby,
         i.updated,
         i.updatedby,
         i.isactive
;
