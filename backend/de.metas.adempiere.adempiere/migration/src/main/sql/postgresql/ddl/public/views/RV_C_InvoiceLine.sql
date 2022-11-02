DROP VIEW RV_C_InvoiceLine
;

CREATE OR REPLACE VIEW RV_C_InvoiceLine AS
SELECT il.ad_client_id,
       il.ad_org_id,
       il.isactive,
       il.created,
       il.createdby,
       il.updated,
       il.updatedby,
       il.c_invoiceline_id,
       i.c_invoice_id,
       i.salesrep_id,
       i.c_bpartner_id,
       i.c_bp_group_id,
       il.m_product_id,
       p.m_product_category_id,
       i.dateinvoiced,
       i.dateacct,
       i.issotrx,
       i.c_doctype_id,
       i.docstatus,
       i.ispaid,
       il.c_campaign_id,
       il.c_project_id,
       il.c_activity_id,
       il.c_projectphase_id,
       il.c_projecttask_id,
       il.qtyinvoiced * i.multiplier::numeric                                          AS qtyinvoiced,
       il.qtyentered * i.multiplier::numeric                                           AS qtyentered,
       il.m_attributesetinstance_id,
       productattribute(il.m_attributesetinstance_id)                                  AS productattribute,
       pasi.m_attributeset_id,
       il.pricelist,
       il.priceactual,
       il.pricelimit,
       il.priceentered,
       CASE
           WHEN il.pricelist = 0::numeric THEN 0::numeric
                                          ELSE ROUND((il.pricelist - il.priceactual) / il.pricelist * 100::numeric, 2)
       END                                                                             AS discount,
       CASE
           WHEN il.pricelimit = 0::numeric THEN 0::numeric
                                           ELSE ROUND((il.priceactual - il.pricelimit) / il.pricelimit * 100::numeric, 2)
       END                                                                             AS margin,
       CASE
           WHEN il.pricelimit = 0::numeric THEN 0::numeric
                                           ELSE (il.priceactual - il.pricelimit) * il.qtyinvoiced
       END                                                                             AS marginamt,
       ROUND(i.multiplier::numeric * il.linenetamt, 2)                                 AS linenetamt,
       ROUND(i.multiplier::numeric * il.pricelist * il.qtyinvoiced, 2)                 AS linelistamt,
       CASE
           WHEN COALESCE(il.pricelimit, 0::numeric) = 0::numeric THEN ROUND(i.multiplier::numeric * il.linenetamt, 2)
                                                                 ELSE ROUND(i.multiplier::numeric * il.pricelimit * il.qtyinvoiced, 2)
       END                                                                             AS linelimitamt,
       ROUND(i.multiplier::numeric * il.pricelist * il.qtyinvoiced - il.linenetamt, 2) AS linediscountamt,
       CASE
           WHEN COALESCE(il.pricelimit, 0::numeric) = 0::numeric THEN 0::numeric
                                                                 ELSE ROUND(i.multiplier::numeric * il.linenetamt - il.pricelimit * il.qtyinvoiced, 2)
       END                                                                             AS lineoverlimitamt
FROM rv_c_invoice i
         JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN m_product p ON il.m_product_id = p.m_product_id
         LEFT JOIN m_attributesetinstance pasi ON il.m_attributesetinstance_id = pasi.m_attributesetinstance_id
;
