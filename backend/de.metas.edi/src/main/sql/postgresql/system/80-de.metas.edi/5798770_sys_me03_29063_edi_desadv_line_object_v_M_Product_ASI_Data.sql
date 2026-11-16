-- me03#29063: Update edi_desadv_line_object_v to use M_Product_ASI_Data instead of C_BPartner_Product
-- for GTIN/ProductNo resolution. Uses content-based ASI subset matching via IsASIAttributesKeySubset().

DROP VIEW IF EXISTS "de.metas.edi".edi_desadv_line_object_v$new;

CREATE OR REPLACE VIEW "de.metas.edi".edi_desadv_line_object_v$new AS
SELECT dl.edi_desadvline_id,
       JSONB_BUILD_OBJECT(
               'Product', JSONB_BUILD_OBJECT(
                   'SupplierProductNo', p.value,
                   'Name', p.name,
                   'Description', p.Description,
                   'BuyerProductNo', COALESCE(dl.ProductNo, asi_data.productno),
                   'GTIN_CU', COALESCE(dl.GTIN_CU, asi_data.gtin, p.gtin),
                   'GTIN_TU', COALESCE(dl.GTIN_TU, pip.gtin),
                   'NetWeight', p.weight,
                   'GrossWeight', p.grossweight,
                   'GrossWeightUOM', COALESCE(grossweightUom.uom_json, '{}'::jsonb)
               ),
               'QtyOrderedInDesadvLineUOM', dl.qtyentered,
               'QtyDeliveredInDesadvLineUOM', dl.QtyDeliveredInUOM,
               'DesadvLineUOM', COALESCE(dl_uom.uom_json, '{}'::jsonb),
               'QtyDeliveredInInvoicingUOM', dl.qtydeliveredininvoiceuom,
               'InvoicingUOM', COALESCE(invoiceUom.uom_json, '{}'::jsonb),
               'OrderLine', ol.line,
               'ShipmentLine', iol.line,
               'OrderPOReference', o.poreference,
               'OrderDocumentNo', o.documentno,
               'DesadvLine', dl.line
       ) AS desadv_line_object_json
FROM edi_desadvline dl
         JOIN m_product p ON p.m_product_id = dl.m_product_id
         JOIN "de.metas.edi".edi_uom_object_v dl_uom ON dl_uom.c_uom_id = dl.c_uom_id
         LEFT JOIN "de.metas.edi".edi_uom_object_v invoiceUom ON invoiceUom.c_uom_id = dl.c_uom_invoice_id
         LEFT JOIN "de.metas.edi".edi_uom_object_v grossweightUom ON grossweightUom.c_uom_id = p.grossweight_uom_id
         LEFT JOIN c_orderline ol ON ol.edi_desadvline_id = dl.edi_desadvline_id
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN m_inoutline iol ON iol.edi_desadvline_id = dl.edi_desadvline_id

 -- Joins for the packing-instruction with the desadv's bpartner (preferred) or an empty bpartner
         JOIN edi_desadv d ON d.edi_desadv_id = dl.edi_desadv_id
         LEFT JOIN LATERAL (
    SELECT gtin
    FROM m_hu_pi_item_product
    WHERE isactive = 'Y'
      AND m_product_id = p.m_product_id
      AND COALESCE(c_bpartner_id, d.c_bpartner_id) = d.c_bpartner_id
    ORDER BY c_bpartner_id NULLS LAST
    LIMIT 1 ) pip ON TRUE

    -- ASI-aware product data lookup (M_Product_ASI_Data with content-based ASI subset matching)
    -- Note: when `iol` has no matching row (unshipped desadv line), iol.m_attributesetinstance_id is NULL
    -- and only wildcard records (m_attributesetinstance_id IS NULL in M_Product_ASI_Data) will match.
    -- This is acceptable: unshipped lines are rare and always use the buyer-level wildcard GTIN.
         LEFT JOIN LATERAL (
    SELECT gtin, productno
    FROM m_product_asi_data
    WHERE isactive = 'Y'
      AND m_product_id = p.m_product_id
      AND (c_bpartner_id IS NULL OR c_bpartner_id = d.c_bpartner_id)
      AND IsASIAttributesKeySubset(m_attributesetinstance_id, iol.m_attributesetinstance_id)
    ORDER BY seqno
    LIMIT 1 ) asi_data ON TRUE
;

SELECT db_alter_view(
    '"de.metas.edi".edi_desadv_line_object_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('edi_desadv_line_object_v$new')
       AND views.table_schema = 'de.metas.edi')
);

DROP VIEW IF EXISTS "de.metas.edi".edi_desadv_line_object_v$new;
