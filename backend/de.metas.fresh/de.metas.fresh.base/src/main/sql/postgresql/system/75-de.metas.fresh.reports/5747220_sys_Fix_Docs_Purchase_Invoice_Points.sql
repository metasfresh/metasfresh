DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Points (IN Record_ID numeric, IN AD_Language Character Varying(6));
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Points;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Points
(
    C_Invoice_id          numeric,
    C_InvoiceLine_id      numeric,
    bpValue               varchar,
    bpName                varchar,
    C_Bpartner_ID         numeric,
    line                  numeric,
    qtyinvoicedinpriceuom numeric,
    invoiceproductValue   varchar,
    invoiceproductName    varchar,
    productValue          varchar,
    productName           varchar,
    qtyentered            numeric,
    uom                   varchar,
    pointsbase_invoiced   numeric,
    pointssum_settled     numeric,
    percentofbasepoints   numeric
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Invoice_Points(IN p_record_ID numeric,
                                                                                IN p_AD_Language Character Varying(6))
    RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Points AS
$$
select c.C_Invoice_id as C_Invoice_id,
       c.c_invoiceline_id as C_InvoiceLine_id,
       bp.value                            as bpValue,
       bp.Name                             as bpName,
       bp.C_Bpartner_ID,
       il.line,
       il.qtyinvoicedinpriceuom,
       pil.value                           as invoiceproductValue,
       coalesce(pilt.name, pil.name)       as invoiceproductName,
       p.value                             as productValue,
       coalesce(pt.name, p.name)           as productName,
       c.qtyentered,
       coalesce(ut.uomsymbol, u.uomsymbol) as uom,
       c.pointsbase_invoiced,
       c.pointssum_settled,
       c.percentofbasepoints
from C_Commission_Overview_V c
         join c_bpartner bp on bp.c_bpartner_id = c.Bill_Bpartner_ID
         join c_invoiceline il on il.c_invoiceline_id = c.c_invoiceline_id
         join m_product pil on pil.m_product_id = il.m_product_id
         join m_product_trl pilt on pilt.m_product_id = pil.m_product_id and pilt.ad_language = p_AD_Language
         left join M_Product p on c.M_Product_Order_ID = p.m_product_id
         left join m_product_trl pt on pt.m_product_id = p.m_product_id and pt.ad_language = p_AD_Language
         left join C_Uom u on c.C_Uom_id = u.C_Uom_id
         left join C_Uom_trl ut on ut.C_Uom_id = u.C_Uom_id and ut.ad_language = p_AD_Language
where c.c_invoice_id = p_record_ID
order by C_Bpartner_ID, productValue, productName;
$$
    LANGUAGE sql
    STABLE;
