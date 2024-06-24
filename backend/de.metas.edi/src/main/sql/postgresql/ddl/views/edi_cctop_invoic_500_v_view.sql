-- View: public.edi_cctop_invoic_500_v

DROP VIEW IF EXISTS public.edi_cctop_invoic_500_v
;

CREATE OR REPLACE VIEW public.edi_cctop_invoic_500_v AS
SELECT SUM(il.qtyEntered)                                                                                                            AS QtyInvoiced,
       CASE
           WHEN u.x12de355 = 'TU' THEN 'PCE'
                                              ELSE u.x12de355
       END                                                                                                                           AS eancom_uom, /* C_InvoiceLine's UOM */
       CASE
           WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                     ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced)
       END                                                                                                                           AS QtyInvoicedInOrderedUOM,
       u_ordered.x12de355                                                                                                            AS eancom_ordered_uom, /* leaving out that CASE-mumbo-jumbo from the other uoms; IDK if it's needed (anymore) */
       MIN(il.c_invoiceline_id)                                                                                                                                                AS edi_cctop_invoic_500_v_id,
       SUM(il.linenetamt)                                                                                                                                                      AS linenetamt,
       MIN(il.line)                                                                                                                                                            AS line,
       il.c_invoice_id,
       il.c_invoice_id                                                                                                                                                         AS edi_cctop_invoic_v_id,
       il.priceactual,
       il.pricelist,
       ol.invoicableqtybasedon,
       REGEXP_REPLACE(pp.UPC, '\s+$', '')                                        AS UPC_CU,
       REGEXP_REPLACE(pp.EAN_CU, '\s+$', '')                                     AS EAN_CU, -- Deprecated: superseded by buyer_ean_cu
       REGEXP_REPLACE(p.value, '\s+$', '')                                       AS Value,
       REGEXP_REPLACE(pp.productno, '\s+$', '')                                  AS CustomerProductNo,
       SUBSTR(p.name, 1, 35)                                                     AS name,
       SUBSTR(p.name, 36, 70)                                                    AS name2,
       t.rate,
       CASE /* be lenient if il.price_uom_id is not set; see https://github.com/metasfresh/metasfresh/issues/6458 */
           WHEN COALESCE(u_price.x12de355, u.x12de355) = 'TU' THEN 'PCE'
                                                                          ELSE COALESCE(u_price.x12de355, u.x12de355)
       END                                                                                                                           AS eancom_price_uom /* C_InvoiceLine's Price-UOM */,
       CASE
           WHEN t.rate = 0 THEN 'Y'
                           ELSE ''
       END                                                                                                                                                                     AS taxfree,
       c.iso_code,
       il.ad_client_id,
       il.ad_org_id,
       MIN(il.created)                                                                                                                                                         AS created,
       MIN(il.createdby)::numeric(10, 0)                                                                                                                                       AS createdby,
       MAX(il.updated)                                                                                                                                                         AS updated,
       MAX(il.updatedby)::numeric(10, 0)                                                                                                                                       AS updatedby,
       il.isactive,
       CASE pc.value
           WHEN 'Leergut' THEN 'P'
                          ELSE ''
       END                                                                                                                                                                     AS leergut,
       COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''),
                p.name)::character varying                                       AS productdescription,
       COALESCE(ol.line, il.line)                                                                                                                                              AS orderline,
       COALESCE(NULLIF(o.poreference, ''), i.poreference)::character varying(40)                                                     AS orderporeference,
       il.c_orderline_id,
       SUM(il.taxamtinfo)                                                                                                                                                      AS taxamtinfo,
       REGEXP_REPLACE(pip.GTIN, '\s+$', '')                                      AS GTIN, -- Deprecated: superseded by buyer_gtin_tu
       REGEXP_REPLACE(pip.EAN_TU, '\s+$', '')                                    AS EAN_TU,
       REGEXP_REPLACE(pip.UPC, '\s+$', '')                                       AS UPC_TU,
       REGEXP_REPLACE(pip.GTIN, '\s+$', '')                                      AS Buyer_GTIN_TU,
       COALESCE( -- if there is no explicit pp.GTIN, then assume that the G from GTIN is respected and thus buyer&supplier work with the same value
               NULLIF(REGEXP_REPLACE(pp.GTIN, '\s+$', ''), ''),
               REGEXP_REPLACE(p.GTIN, '\s+$', '')
       )                                                                         AS Buyer_GTIN_CU,
       REGEXP_REPLACE(pp.EAN_CU, '\s+$', '')                                     AS Buyer_EAN_CU,
       REGEXP_REPLACE(p.GTIN, '\s+$', '')                                        AS Supplier_GTIN_CU,
       il.QtyEnteredInBPartnerUOM                                                AS qtyEnteredInBPartnerUOM,
       il.C_UOM_BPartner_ID                                                      AS C_UOM_BPartner_ID,
       ol.externalseqno                                                          AS externalSeqNo
FROM c_invoiceline il
         LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.isactive = 'Y'
         LEFT JOIN m_hu_pi_item_product pip ON COALESCE(ol.m_hu_pi_item_product_id, il.m_hu_pi_item_product_id) = pip.m_hu_pi_item_product_id
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN c_uom u_ordered ON u_ordered.c_uom_id = COALESCE(ol.c_uom_id, il.c_uom_id)
         LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
         LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
         LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
         LEFT JOIN c_bpartner_product pp
                   ON pp.c_bpartner_id = i.c_bpartner_id AND pp.m_product_id = il.m_product_id AND pp.isactive = 'Y'
         LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
         LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id
         LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
WHERE TRUE
  AND il.m_product_id IS NOT NULL
  AND il.isactive = 'Y'
  AND il.qtyentered <> 0
GROUP BY il.c_invoice_id,
         il.priceactual,
         il.pricelist,
         ol.InvoicableQtyBasedOn,
         pp.UPC,
         pp.EAN_CU,
         p.value,
         pp.productno,
         (SUBSTR(p.name, 1, 35)),
         (SUBSTR(p.name, 36, 70)),
         t.rate,
         (CASE
              WHEN u.x12de355 = 'TU' THEN 'PCE'
                                                    ELSE u.x12de355
             END),
         (CASE /* be lenient if il.price_uom_id is not set; see https://github.com/metasfresh/metasfresh/issues/6458 */
              WHEN COALESCE(u_price.x12de355, u.x12de355) = 'TU' THEN 'PCE'
                                                                                ELSE COALESCE(u_price.x12de355, u.x12de355)
             END),
         (
             CASE
              WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                        ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced)
          END),
         u_ordered.x12de355,
         (CASE
              WHEN t.rate = 0 THEN 'Y'
                              ELSE ''
          END),
         c.iso_code,
         il.ad_client_id,
         il.ad_org_id,
         il.isactive,
         (CASE pc.value
              WHEN 'Leergut' THEN 'P'
                             ELSE ''
          END),
         (COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)),
         (COALESCE(NULLIF(o.poreference, ''), i.poreference)),
         (COALESCE(ol.line, il.line)),
         il.c_orderline_id,
         pip.UPC, pip.GTIN, pip.EAN_TU, pp.GTIN, p.GTIN,
         il.QtyEnteredInBPartnerUOM, il.C_UOM_BPartner_ID, ol.externalseqno
ORDER BY COALESCE(ol.line, il.line);

COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
'
;