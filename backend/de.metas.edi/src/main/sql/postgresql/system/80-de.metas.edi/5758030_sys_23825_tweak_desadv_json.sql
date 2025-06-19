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

-- Function for desadv packs
CREATE OR REPLACE FUNCTION "de.metas.edi".get_desadv_packs_json_fn(p_edi_desadv_id NUMERIC)
    RETURNS JSONB
AS
$$
DECLARE
    v_packs_json JSONB;
BEGIN
    SELECT JSONB_AGG(
                   JSONB_BUILD_OBJECT(
                           'SeqNo', p_lat.seqno,
                           'IPA_SSCC18', p_lat.ipa_sscc18,
                           'M_HU_PackagingCode_Text', pc_lu.packagingcode,
                           'LineItems', COALESCE(pack_items_lat.items_data, '[]'::jsonb),
                           'GTIN_PackingMaterial', p_lat.gtin_packingmaterial
                   ) ORDER BY p_lat.seqno
           )
    INTO v_packs_json
    FROM edi_desadv_pack p_lat
             LEFT JOIN m_hu_packagingcode pc_lu ON pc_lu.m_hu_packagingcode_id = p_lat.m_hu_packagingcode_id
             LEFT JOIN LATERAL (
        SELECT JSONB_AGG(
                       JSONB_BUILD_OBJECT(
                               'BestBeforeDate', pi_lat.bestbeforedate,
                               'LotNumber', pi_lat.lotnumber,
                               'QtyCUsPerTU', pi_lat.qtycuspertu,
                               'QtyCUsPerLU', pi_lat.qtycusperlu,
                               'QtyCUsPerLU_InInvoiceUOM', pi_lat.qtycusperlu_ininvoiceuom,
                               'QtyCUsPerTU_InInvoiceUOM', pi_lat.qtycuspertu_ininvoiceuom,
                               'QtyTU', pi_lat.qtytu,
                               'DesadvLine', COALESCE(line_obj.desadv_line_object_json, '{}'::jsonb),
                               'M_HU_PackagingCode_TU_Text', pc_lu.packagingcode,
                               'Line', pi_lat.line,
                               'GTIN_TU_PackingMaterial', pi_lat.gtin_tu_packingmaterial
                       ) ORDER BY pi_lat.line
               ) AS items_data
        FROM edi_desadv_pack_item pi_lat
                 LEFT JOIN "de.metas.edi".edi_desadv_line_object_v line_obj ON line_obj.edi_desadvline_id = pi_lat.edi_desadvline_id
                 LEFT JOIN m_hu_packagingcode pc_tu ON pc_tu.m_hu_packagingcode_id = pi_lat.m_hu_packagingcode_tu_id
        WHERE pi_lat.edi_desadv_pack_id = p_lat.edi_desadv_pack_id
          AND pi_lat.isactive = 'Y'
        ) pack_items_lat ON TRUE
    WHERE p_lat.edi_desadv_id = p_edi_desadv_id
      AND p_lat.isactive = 'Y';

    RETURN COALESCE(v_packs_json, '[]'::jsonb);
END;
$$
    LANGUAGE plpgsql STABLE
;



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
SELECT dl.edi_desadvline_id,
       JSONB_BUILD_OBJECT(
               'Product', JSONB_BUILD_OBJECT(
                   'SupplierProductNo', p.value,
                   'Name', p.name,
                   'Description', p.Description,
                   'BuyerProductNo', COALESCE(dl.ProductNo, bpp.productno),
                   'GTIN_CU', COALESCE(dl.GTIN_CU, bpp.gtin, p.gtin),
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

    -- Joins for the bpartner-product with the desadv's bpartner
         LEFT JOIN LATERAL (
    SELECT gtin, productno
    FROM c_bpartner_product
    WHERE isactive = 'Y'
      AND m_product_id = p.m_product_id
      AND c_bpartner_id = d.c_bpartner_id
    LIMIT 1 ) bpp ON TRUE
;


-- Main view that uses the functions and other views
drop VIEW if exists M_InOut_Export_EDI_DESADV_JSON_V;
CREATE OR REPLACE VIEW M_InOut_Export_EDI_DESADV_JSON_V AS
SELECT io.m_inout_id,
       JSON_BUILD_OBJECT('metasfresh_DESADV', JSONB_BUILD_OBJECT(
               'Version', '0.2',
               'TechnicalRecipientGLN', buyer.edidesadvrecipientgln,
               'Parties', JSONB_BUILD_OBJECT(
                       'Supplier', COALESCE(bp_supplier.bpartner_json, '{}'::jsonb),
                       'Supplier_Location', COALESCE(bpl_supplier.bpartner_location_json, '{}'::jsonb),
                       'Buyer', COALESCE(bp_buyer.bpartner_json, '{}'::jsonb),
                       'Buyer_Location', COALESCE(bpl_buyer.bpartner_location_json, '{}'::jsonb),
                       'Invoicee', COALESCE(bp_bill.bpartner_json, '{}'::jsonb),
                       'Invoicee_Location', COALESCE(bpl_bill.bpartner_location_json, '{}'::jsonb),
                       'DeliveryParty', COALESCE(bp_handover.bpartner_json, '{}'::jsonb),
                       'DeliveryParty_Location', COALESCE(bpl_handover.bpartner_location_json, '{}'::jsonb),
                       'UltimateConsignee', COALESCE(bp_dropship.bpartner_json, '{}'::jsonb),
                       'UltimateConsignee_Location', COALESCE(bpl_dropship.bpartner_location_json, '{}'::jsonb)
                          ),
               'DateOrdered', d.dateordered,
               'ShipmentDocumentNo', io.documentno,
               'EDI_Desadv_ID', d.edi_desadv_id,
               'MovementDate', io.movementdate,
               'POReference', COALESCE(d.poreference, io.poreference),
               'Packings', "de.metas.edi".get_desadv_packs_json_fn(d.edi_desadv_id),
               'Currency', COALESCE(curr.currency_json, '{}'::jsonb),
               'InvoicableQtyBasedOn', (SELECT edl_ib.invoicableqtybasedon
                                        FROM edi_desadvline edl_ib
                                        WHERE edl_ib.edi_desadv_id = d.edi_desadv_id
                                          AND edl_ib.isactive = 'Y'
                                        ORDER BY edl_ib.line
                                        LIMIT 1),
               'DeliveryViaRule', COALESCE(d.deliveryviarule, io.deliveryviarule),
               'DesadvLineWithNoPacking', "de.metas.edi".get_desadv_lines_no_pack_json_fn(d.edi_desadv_id),
               'M_InOut_ID', io.m_inout_id,
               'DatePromised', o.datepromised)
       ) as embedded_json
FROM m_inout io
         LEFT JOIN c_order o ON io.c_order_id = o.c_order_id
         JOIN edi_desadv d ON io.edi_desadv_id = d.edi_desadv_id
         JOIN c_bpartner buyer ON d.c_bpartner_id = buyer.c_bpartner_id
    -- Joins for other lookup objects
         LEFT JOIN "de.metas.edi".edi_currency_object_v curr ON curr.c_currency_id = d.c_currency_id
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_buyer ON bp_buyer.c_bpartner_id = d.c_bpartner_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_buyer ON bpl_buyer.c_bpartner_location_id = d.c_bpartner_location_id
         LEFT JOIN c_bpartner_location bpl_bill_table ON bpl_bill_table.c_bpartner_location_id = d.bill_location_id -- helper, bc desadv has no direct invoicee
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_bill ON bp_bill.c_bpartner_id = bpl_bill_table.c_bpartner_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_bill ON bpl_bill.c_bpartner_location_id = d.bill_location_id
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_handover ON bp_handover.c_bpartner_id = d.handover_partner_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_handover ON bpl_handover.c_bpartner_location_id = d.handover_location_id
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_dropship ON bp_dropship.c_bpartner_id = d.dropship_bpartner_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_dropship ON bpl_dropship.c_bpartner_location_id = d.dropship_location_id
    -- Joins for the supplier. Note that we not output the m_inout.m_warehouse's partner, because the WH might be owned by a third party
         LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
         JOIN "de.metas.edi".edi_bpartner_object_v bp_supplier ON bp_supplier.c_bpartner_id = org.org_bpartner_id
         JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_supplier ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id

WHERE io.isactive = 'Y'
  AND io.docstatus IN ('CO', 'CL')
;

-- Example query
--select * from M_InOut_Export_EDI_DESADV_JSON_V where m_inout_id=1001857;
