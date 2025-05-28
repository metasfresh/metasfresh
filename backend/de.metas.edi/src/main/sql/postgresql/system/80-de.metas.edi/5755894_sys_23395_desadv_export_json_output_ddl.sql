-- Required views
CREATE OR REPLACE VIEW "de.metas.edi".edi_bpartner_object_v AS
SELECT bp.c_bpartner_id,
       JSONB_BUILD_OBJECT(
               'Value', bp.value,
               'Name', bp.name,
               'Name2', bp.name2
       ) AS bpartner_json
FROM c_bpartner bp
;

CREATE OR REPLACE VIEW "de.metas.edi".edi_bpartner_location_object_v AS
SELECT bpl.c_bpartner_location_id,
       JSONB_BUILD_OBJECT(
               'GLN', bpl.gln,
               'Address1', l.address1,
               'Address2', l.address2,
               'Postal', l.postal,
               'City', l.city,
               'CountryCode', c.countrycode
       ) AS bpartner_location_json
FROM c_bpartner_location bpl
         JOIN c_location l ON l.c_location_id = bpl.c_location_id
         LEFT JOIN c_country c ON c.c_country_id = l.c_country_id
;

CREATE OR REPLACE VIEW "de.metas.edi".edi_currency_object_v AS
SELECT c.c_currency_id,
       JSONB_BUILD_OBJECT(
               'ISO_Code', c.iso_code,
               'CurSymbol', c.cursymbol
       ) AS currency_json
FROM c_currency c
;

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
               'DesadvLine', edl.line,
               'EDI_DesadvLine_ID', edl.edi_desadvline_id
       ) AS desadv_line_object_json
FROM edi_desadvline edl
         JOIN m_product p ON p.m_product_id = edl.m_product_id
         JOIN c_uom edl_uom ON edl_uom.c_uom_id = edl.c_uom_id
         LEFT JOIN c_uom grossweightUom ON grossweightUom.c_uom_id = p.grossweight_uom_id
         LEFT JOIN c_orderline ol ON ol.edi_desadvline_id = edl.edi_desadvline_id
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN m_inoutline iol ON iol.edi_desadvline_id = edl.edi_desadvline_id
;


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
                           'SeqNo', edp_lat.seqno,
                           'IPA_SSCC18', edp_lat.ipa_sscc18,
                           'M_HU_PackagingCode_Text', pc_lu.packagingcode,
                           'Line_Item', COALESCE(pack_items_lat.items_data, '[]'::jsonb),
                           'GTIN_PackingMaterial', edp_lat.gtin_packingmaterial
                   ) ORDER BY edp_lat.seqno
           )
    INTO v_packs_json
    FROM edi_desadv_pack edp_lat
             LEFT JOIN m_hu_packagingcode pc_lu ON pc_lu.m_hu_packagingcode_id = edp_lat.m_hu_packagingcode_id
             LEFT JOIN LATERAL (
        SELECT JSONB_AGG(
                       JSONB_BUILD_OBJECT(
                               'BestBeforeDate', edpi_lat.bestbeforedate,
                               'LotNumber', edpi_lat.lotnumber,
                               'QtyCUsPerTU', edpi_lat.qtycuspertu,
                               'QtyCUsPerLU', edpi_lat.qtycusperlu,
                               'QtyCUsPerLU_InInvoiceUOM', edpi_lat.qtycusperlu_ininvoiceuom,
                               'QtyCUsPerTU_InInvoiceUOM', edpi_lat.qtycuspertu_ininvoiceuom,
                               'QtyTU', edpi_lat.qtytu,
                               'EDI_DesadvLine_ID', COALESCE(line_obj.desadv_line_object_json, '{}'::jsonb),
                               'M_HU_PackagingCode_TU_Text', pc_lu.packagingcode,
                               'Line', edpi_lat.line,
                               'GTIN_TU_PackingMaterial', edpi_lat.gtin_tu_packingmaterial
                       ) ORDER BY edpi_lat.line
               ) AS items_data
        FROM edi_desadv_pack_item edpi_lat
                 LEFT JOIN "de.metas.edi".edi_desadv_line_object_v line_obj ON line_obj.edi_desadvline_id = edpi_lat.edi_desadvline_id
                 LEFT JOIN m_hu_packagingcode pc_tu ON pc_tu.m_hu_packagingcode_id = edpi_lat.m_hu_packagingcode_tu_id
        WHERE edpi_lat.edi_desadv_pack_id = edp_lat.edi_desadv_pack_id
          AND edpi_lat.isactive = 'Y'
        ) pack_items_lat ON TRUE
    WHERE edp_lat.edi_desadv_id = p_edi_desadv_id
      AND edp_lat.isactive = 'Y';

    RETURN COALESCE(v_packs_json, '[]'::jsonb);
END;
$$
    LANGUAGE plpgsql STABLE
;

-- Main view that uses the functions and other views
drop VIEW if exists M_InOut_Export_EDI_DESADV_JSON_V;
CREATE OR REPLACE VIEW M_InOut_Export_EDI_DESADV_JSON_V AS
SELECT mio.m_inout_id,
       JSON_BUILD_OBJECT('metasfresh_DESADV', JSONB_BUILD_OBJECT(
               'Buyer', COALESCE(bp_main.bpartner_json, '{}'::jsonb),
               'Buyer_Location', COALESCE(bpl_main.bpartner_location_json, '{}'::jsonb),
               'DateOrdered', ed.dateordered,
               'ShipmentDocumentNo', mio.documentno,
               'EDI_Desadv_ID', ed.edi_desadv_id,
               'MovementDate', mio.movementdate,
               'POReference', COALESCE(ed.poreference, mio.poreference),
               'Packing', "de.metas.edi".get_desadv_packs_json_fn(ed.edi_desadv_id), -- Call function
               'Invoicee_Location', COALESCE(bpl_bill.bpartner_location_json, '{}'::jsonb),
               'Currency', COALESCE(curr.currency_json, '{}'::jsonb),
               'DeliveryParty_Location', COALESCE(bpl_handover.bpartner_location_json, '{}'::jsonb),
               'UltimateConsignee', COALESCE(bp_dropship.bpartner_json, '{}'::jsonb),
               'UltimateConsignee_Location', COALESCE(bpl_dropship.bpartner_location_json, '{}'::jsonb),
               'DeliveryParty', COALESCE(bp_handover_partner.bpartner_json, '{}'::jsonb),
               'InvoicableQtyBasedOn', (SELECT edl_ib.invoicableqtybasedon
                                        FROM edi_desadvline edl_ib
                                        WHERE edl_ib.edi_desadv_id = ed.edi_desadv_id
                                          AND edl_ib.isactive = 'Y'
                                        ORDER BY edl_ib.line
                                        LIMIT 1),
               'DeliveryViaRule', COALESCE(ed.deliveryviarule, mio.deliveryviarule),
               'DesadvLineWithNoPacking', "de.metas.edi".get_desadv_lines_no_pack_json_fn(ed.edi_desadv_id), -- Call function

               'M_InOut_ID', mio.m_inout_id,
               'DatePromised', mio.movementdate
                                              )
       ) as embedded_json
FROM m_inout mio
         JOIN edi_desadv ed ON mio.edi_desadv_id = ed.edi_desadv_id AND ed.isactive = 'Y'

    -- Joins for other lookup objects
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_main ON bp_main.c_bpartner_id = ed.c_bpartner_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_main ON bpl_main.c_bpartner_location_id = ed.c_bpartner_location_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_bill ON bpl_bill.c_bpartner_location_id = ed.bill_location_id
         LEFT JOIN "de.metas.edi".edi_currency_object_v curr ON curr.c_currency_id = ed.c_currency_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_handover ON bpl_handover.c_bpartner_location_id = ed.handover_location_id
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_dropship ON bp_dropship.c_bpartner_id = ed.dropship_bpartner_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_dropship ON bpl_dropship.c_bpartner_location_id = ed.dropship_location_id
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_handover_partner ON bp_handover_partner.c_bpartner_id = ed.handover_partner_id

WHERE mio.isactive = 'Y'
  AND mio.docstatus IN ('CO', 'CL')
;

-- Example query
--select * from M_InOut_Export_EDI_DESADV_JSON_V where m_inout_id=1273429;