/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


CREATE OR REPLACE VIEW "de.metas.edi".edi_uom_object_v AS
SELECT c.c_uom_id,
       JSONB_BUILD_OBJECT(
               'X12DE355', c.X12DE355,
               'Name', c.name
       ) AS uom_json
FROM c_uom c
;

comment ON VIEW "de.metas.edi".edi_uom_object_v IS 'This view is used to create the Unit of Measure JSON objects that are sent to the EDI desadv';

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
