-- Required view for desadv line objects
CREATE OR REPLACE VIEW "de.metas.edi".edi_desadv_line_object_v AS
SELECT edl.edi_desadvline_id,
       JSONB_BUILD_OBJECT(
               'Product', JSONB_BUILD_OBJECT(
                       'Value', p.value,
                       'Name', p.name,
                       'UPC', p.upc,
                       'Description', p.Description,
                       'ProductNo', edl.ProductNo,
                       'GTIN_CU', edl.GTIN_CU,
                       'GTIN_TU', edl.GTIN_TU,
                       'NetWeight', p.weight,
                       'GrossWeight', p.grossweight,
                       'GrossWeightUOM', COALESCE(grossweightUom.uom_json, '{}'::jsonb)),
               'QtyOrderedInDesadvLineUOM', edl.qtyentered,
               'QtyDeliveredInDesadvLineUOM', edl.QtyDeliveredInUOM,
               'DesadvLineUOM', COALESCE(edl_uom.uom_json, '{}'::jsonb),
               'InvoicingUOM', COALESCE(invoiceUom.uom_json, '{}'::jsonb),
               'OrderLine', ol.line,
               'ShipmentLine', iol.line,
               'OrderPOReference', o.poreference,
               'OrderDocumentNo', o.documentno,
               'DesadvLine', edl.line
       ) AS desadv_line_object_json
FROM edi_desadvline edl
         JOIN m_product p ON p.m_product_id = edl.m_product_id
         JOIN "de.metas.edi".edi_uom_object_v edl_uom ON edl_uom.c_uom_id = edl.c_uom_id
         LEFT JOIN "de.metas.edi".edi_uom_object_v invoiceUom ON invoiceUom.c_uom_id = edl.c_uom_invoice_id
         LEFT JOIN "de.metas.edi".edi_uom_object_v grossweightUom ON grossweightUom.c_uom_id = p.grossweight_uom_id
         LEFT JOIN c_orderline ol ON ol.edi_desadvline_id = edl.edi_desadvline_id
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN m_inoutline iol ON iol.edi_desadvline_id = edl.edi_desadvline_id
;
