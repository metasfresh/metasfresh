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
               'GrossWeightUOM;', JSONB_BUILD_OBJECT(
                       'X12DE355', grossweightUom.X12DE355,
                       'Name', grossweightUom.name)),
               'QtyEntered', edl.qtyentered,
               'DesadvLineUOM', JSONB_BUILD_OBJECT(
                       'X12DE355', edl_uom.X12DE355,
                       'Name', edl_uom.name),
               'OrderLine', ol.line,
               'ShipmentLine', iol.line,
               'OrderPOReference', o.poreference,
               'OrderDocumentNo', o.documentno,
               'MovementQty', COALESCE(iol.movementqty, 0),
               'DesadvLine', edl.line
       ) AS desadv_line_object_json
FROM edi_desadvline edl
         JOIN m_product p ON p.m_product_id = edl.m_product_id
         JOIN c_uom edl_uom ON edl_uom.c_uom_id = edl.c_uom_id
         LEFT JOIN c_uom grossweightUom ON grossweightUom.c_uom_id = p.grossweight_uom_id
         LEFT JOIN c_orderline ol ON ol.edi_desadvline_id = edl.edi_desadvline_id
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN m_inoutline iol ON iol.edi_desadvline_id = edl.edi_desadvline_id
;
