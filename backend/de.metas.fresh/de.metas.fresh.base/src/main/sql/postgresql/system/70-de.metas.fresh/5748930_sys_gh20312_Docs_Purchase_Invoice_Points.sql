DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Points (IN p_record_id   numeric,
                                                                                         IN p_ad_language Character Varying(6))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Points(p_record_id   numeric,
                                                                                p_ad_language character varying)
    RETURNS TABLE
            (
                c_invoice_id          numeric,
                c_invoiceline_id      numeric,
                bpvalue               character varying,
                bpname                character varying,
                c_bpartner_id         numeric,
                line                  numeric,
                qtyinvoicedinpriceuom numeric,
                invoiceproductvalue   character varying,
                invoiceproductname    character varying,
                productvalue          character varying,
                productname           character varying,
                qtyentered            numeric,
                uom                   character varying,
                pointsbase_invoiced   numeric,
                pointssum_settled     numeric,
                percentofbasepoints   numeric,
                documentno            character varying,
                poreference           character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT c.c_invoice_commission_id           AS C_Invoice_id,
       c.c_invoiceline_commission_id       AS C_InvoiceLine_id,
       bp.value                            AS bpValue,
       bp.Name                             AS bpName,
       bp.C_Bpartner_ID,
       il.line,
       il.qtyinvoicedinpriceuom,
       pil.value                           AS invoiceproductValue,
       COALESCE(pilt.name, pil.name)       AS invoiceproductName,
       p.value                             AS productValue,
       COALESCE(pt.name, p.name)           AS productName,
       c.qtyentered,
       COALESCE(ut.uomsymbol, u.uomsymbol) AS uom,
       c.pointsbase_invoiced,
       c.pointssum_settled,
       c.percentofbasepoints,
       o.documentno,
       o.poreference
FROM C_Commission_Overview_V c
         JOIN c_bpartner bp ON bp.c_bpartner_id = c.Bill_Bpartner_ID
         JOIN c_invoiceline il ON il.c_invoiceline_id = c.c_invoiceline_commission_id
         JOIN m_product pil ON pil.m_product_id = il.m_product_id
         JOIN m_product_trl pilt ON pilt.m_product_id = pil.m_product_id AND pilt.ad_language = p_AD_Language
         LEFT JOIN c_order o ON c.c_order_id = o.c_order_id
         LEFT JOIN M_Product p ON c.M_Product_Order_ID = p.m_product_id
         LEFT JOIN m_product_trl pt ON pt.m_product_id = p.m_product_id AND pt.ad_language = p_AD_Language
         LEFT JOIN C_Uom u ON c.C_Uom_id = u.C_Uom_id
         LEFT JOIN C_Uom_trl ut ON ut.C_Uom_id = u.C_Uom_id AND ut.ad_language = p_AD_Language
WHERE c.c_invoice_commission_id = p_record_ID
ORDER BY C_Bpartner_ID, productValue, productName;
$$
;
