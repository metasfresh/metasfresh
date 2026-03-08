-- gh#26915: DESADV JSON export — filter packs and no-pack lines by shipment (m_inout_id)
-- When a DESADV is shared across multiple shipments, the JSON export must only include
-- packs and lines belonging to the specific shipment being exported.

-- 1) Drop the view first (it depends on the old single-parameter functions)
DROP VIEW IF EXISTS M_InOut_Export_EDI_DESADV_JSON_V;

-- 2) Drop old single-parameter functions
DROP FUNCTION IF EXISTS "de.metas.edi".get_desadv_packs_json_fn(NUMERIC);
DROP FUNCTION IF EXISTS "de.metas.edi".get_desadv_lines_no_pack_json_fn(NUMERIC);

-- 3) Recreate get_desadv_packs_json_fn with p_m_inout_id parameter
CREATE OR REPLACE FUNCTION "de.metas.edi".get_desadv_packs_json_fn(p_edi_desadv_id NUMERIC, p_m_inout_id NUMERIC)
    RETURNS JSONB
AS
$$
DECLARE
    v_packs_json JSONB;
BEGIN
    WITH pack_item_comp AS (
        SELECT epi.edi_desadv_pack_item_id,
               epi.edi_desadv_pack_id,
               epi.edi_desadvline_id,
               dl.line AS desadv_line,
               ol.c_order_compensationgroup_id AS comp_group_id,
               ol.line AS order_line
        FROM edi_desadv_pack_item epi
                 JOIN edi_desadv_pack ep ON ep.edi_desadv_pack_id = epi.edi_desadv_pack_id
                 JOIN edi_desadvline dl ON dl.edi_desadvline_id = epi.edi_desadvline_id
                 LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = epi.m_inoutline_id
                 LEFT JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id
            AND ol.c_order_compensationgroup_id IS NOT NULL
                 JOIN m_inoutline iol_filter ON iol_filter.m_inoutline_id = epi.m_inoutline_id
            AND iol_filter.m_inout_id = p_m_inout_id
        WHERE ep.edi_desadv_id = p_edi_desadv_id
          AND ep.isactive = 'Y'
          AND epi.isactive = 'Y'
    ),
         main_per_group AS (
             SELECT DISTINCT ON (comp_group_id)
                 comp_group_id,
                 edi_desadvline_id AS main_desadvline_id,
                 desadv_line       AS main_desadv_line
             FROM pack_item_comp
             WHERE comp_group_id IS NOT NULL
             ORDER BY comp_group_id, order_line
         ),
         pack_with_role AS (
             SELECT ep.edi_desadv_pack_id,
                    ep.seqno,
                    ep.ipa_sscc18,
                    ep.m_hu_packagingcode_id,
                    ep.gtin_packingmaterial,
                    epi.edi_desadv_pack_item_id,
                    epi.edi_desadvline_id,
                    epi.m_inoutline_id,
                    epi.bestbeforedate,
                    epi.lotnumber,
                    epi.qtycuspertu,
                    epi.qtycusperlu,
                    epi.qtycusperlu_ininvoiceuom,
                    epi.qtycuspertu_ininvoiceuom,
                    epi.qtytu,
                    epi.line AS pi_line,
                    epi.gtin_tu_packingmaterial,
                    epi.m_hu_packagingcode_tu_id,
                    pic.comp_group_id,
                    mg.main_desadvline_id,
                    mg.main_desadv_line,
                    COALESCE(pic.comp_group_id IS NOT NULL
                                 AND pic.edi_desadvline_id = mg.main_desadvline_id, false)  AS is_main_article,
                    COALESCE(pic.comp_group_id IS NOT NULL
                                 AND pic.edi_desadvline_id <> mg.main_desadvline_id, false) AS is_sub_article
             FROM edi_desadv_pack ep
                      JOIN edi_desadv_pack_item epi
                           ON epi.edi_desadv_pack_id = ep.edi_desadv_pack_id AND epi.isactive = 'Y'
                      JOIN m_inoutline iol_filter ON iol_filter.m_inoutline_id = epi.m_inoutline_id
                           AND iol_filter.m_inout_id = p_m_inout_id
                      LEFT JOIN pack_item_comp pic ON pic.edi_desadv_pack_item_id = epi.edi_desadv_pack_item_id
                      LEFT JOIN main_per_group mg ON mg.comp_group_id = pic.comp_group_id
             WHERE ep.edi_desadv_id = p_edi_desadv_id
               AND ep.isactive = 'Y'
         ),
         main_packs AS (
             SELECT DISTINCT ON (comp_group_id)
                 comp_group_id, edi_desadv_pack_id AS main_pack_id
             FROM pack_with_role
             WHERE is_main_article
             ORDER BY comp_group_id, seqno
         ),
         items_assigned AS (
             SELECT pwr.*,
                    CASE
                        WHEN pwr.is_sub_article THEN mp.main_pack_id
                        ELSE pwr.edi_desadv_pack_id
                        END AS effective_pack_id
             FROM pack_with_role pwr
                      LEFT JOIN main_packs mp ON mp.comp_group_id = pwr.comp_group_id
         ),
         pack_header AS (
             SELECT DISTINCT ON (ia.effective_pack_id)
                 ia.effective_pack_id,
                 ep.seqno,
                 ep.ipa_sscc18,
                 ep.m_hu_packagingcode_id,
                 ep.gtin_packingmaterial
             FROM items_assigned ia
                      JOIN edi_desadv_pack ep ON ep.edi_desadv_pack_id = ia.effective_pack_id
             ORDER BY ia.effective_pack_id, ep.seqno
         )
    SELECT JSONB_AGG(
                   JSONB_BUILD_OBJECT(
                           'SeqNo', ph.seqno,
                           'IPA_SSCC18', ph.ipa_sscc18,
                           'M_HU_PackagingCode_Text', pc_lu.packagingcode,
                           'LineItems', COALESCE(items_lat.items_data, '[]'::jsonb),
                           'GTIN_PackingMaterial', ph.gtin_packingmaterial
                   ) ORDER BY ph.seqno
           )
    INTO v_packs_json
    FROM pack_header ph
             LEFT JOIN m_hu_packagingcode pc_lu
                       ON pc_lu.m_hu_packagingcode_id = ph.m_hu_packagingcode_id
             LEFT JOIN LATERAL (
        SELECT JSONB_AGG(
                       JSONB_BUILD_OBJECT(
                               'BestBeforeDate', ia.bestbeforedate,
                               'LotNumber', ia.lotnumber,
                               'QtyCUsPerTU', ia.qtycuspertu,
                               'QtyCUsPerLU', ia.qtycusperlu,
                               'QtyCUsPerLU_InInvoiceUOM', ia.qtycusperlu_ininvoiceuom,
                               'QtyCUsPerTU_InInvoiceUOM', ia.qtycuspertu_ininvoiceuom,
                               'QtyTU', ia.qtytu,
                               'DesadvLine', JSONB_BUILD_OBJECT(
                                       'Product', JSONB_BUILD_OBJECT(
                                               'SupplierProductNo', p.value,
                                               'Name', p.name,
                                               'Description', p.description,
                                               'BuyerProductNo', COALESCE(dl.productno, bpp.productno),
                                               'GTIN_CU', COALESCE(dl.gtin_cu, bpp.gtin, p.gtin),
                                               'GTIN_TU', COALESCE(dl.gtin_tu, pip.gtin),
                                               'NetWeight', p.weight,
                                               'GrossWeight', p.grossweight,
                                               'GrossWeightUOM', COALESCE(gw_uom.uom_json, '{}'::jsonb)
                                       ),
                                       'QtyOrderedInDesadvLineUOM', dl.qtyentered,
                                       'QtyDeliveredInDesadvLineUOM', dl.qtydeliveredinuom,
                                       'DesadvLineUOM', COALESCE(dl_uom.uom_json, '{}'::jsonb),
                                       'QtyDeliveredInInvoicingUOM', dl.qtydeliveredininvoiceuom,
                                       'InvoicingUOM', COALESCE(inv_uom.uom_json, '{}'::jsonb),
                                       'OrderLine', ol.line,
                                       'ShipmentLine', iol.line,
                                       'OrderPOReference', o.poreference,
                                       'OrderDocumentNo', o.documentno,
                                       'DesadvLine', dl.line
                               ),
                               'M_HU_PackagingCode_TU_Text', pc_tu.packagingcode,
                               'Line', ia.pi_line,
                               'GTIN_TU_PackingMaterial', ia.gtin_tu_packingmaterial,
                               'IsSubArticle', ia.is_sub_article,
                               'MainArticleLine',
                               CASE WHEN ia.is_sub_article THEN ia.main_desadv_line ELSE NULL END
                       ) ORDER BY ia.is_sub_article, ia.pi_line
               ) AS items_data
        FROM items_assigned ia
                 JOIN edi_desadvline dl ON dl.edi_desadvline_id = ia.edi_desadvline_id
                 JOIN edi_desadv d ON d.edi_desadv_id = dl.edi_desadv_id
                 JOIN m_product p ON p.m_product_id = dl.m_product_id
                 JOIN "de.metas.edi".edi_uom_object_v dl_uom ON dl_uom.c_uom_id = dl.c_uom_id
                 LEFT JOIN "de.metas.edi".edi_uom_object_v inv_uom ON inv_uom.c_uom_id = dl.c_uom_invoice_id
                 LEFT JOIN "de.metas.edi".edi_uom_object_v gw_uom ON gw_uom.c_uom_id = p.grossweight_uom_id
                 LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = ia.m_inoutline_id
                 LEFT JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id
                 LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
                 LEFT JOIN LATERAL (
            SELECT gtin, productno
            FROM c_bpartner_product
            WHERE isactive = 'Y'
              AND m_product_id = p.m_product_id
              AND c_bpartner_id = d.c_bpartner_id
            LIMIT 1
            ) bpp ON TRUE
                 LEFT JOIN LATERAL (
            SELECT gtin
            FROM m_hu_pi_item_product
            WHERE isactive = 'Y'
              AND m_product_id = p.m_product_id
              AND COALESCE(c_bpartner_id, d.c_bpartner_id) = d.c_bpartner_id
            ORDER BY c_bpartner_id NULLS LAST
            LIMIT 1
            ) pip ON TRUE
                 LEFT JOIN m_hu_packagingcode pc_tu
                           ON pc_tu.m_hu_packagingcode_id = ia.m_hu_packagingcode_tu_id
        WHERE ia.effective_pack_id = ph.effective_pack_id
        ) items_lat ON TRUE;

    RETURN COALESCE(v_packs_json, '[]'::jsonb);
END;
$$
    LANGUAGE plpgsql STABLE
;

-- 4) Recreate get_desadv_lines_no_pack_json_fn with p_m_inout_id parameter
CREATE OR REPLACE FUNCTION "de.metas.edi".get_desadv_lines_no_pack_json_fn(p_edi_desadv_id NUMERIC, p_m_inout_id NUMERIC)
    RETURNS JSONB AS $$
DECLARE
    v_lines_no_pack_json JSONB;
BEGIN
    SELECT
        jsonb_agg(
                jsonb_build_object(
                        'DesadvLine', COALESCE(line_obj_no_pack.desadv_line_object_json, '{}'::jsonb)
                ) ORDER BY edl_lat.line
        )
    INTO v_lines_no_pack_json
    FROM edi_desadvline edl_lat
             LEFT JOIN "de.metas.edi".edi_desadv_line_object_v line_obj_no_pack ON line_obj_no_pack.edi_desadvline_id = edl_lat.edi_desadvline_id
    WHERE edl_lat.edi_desadv_id = p_edi_desadv_id
      AND edl_lat.isactive = 'Y'
      AND EXISTS (
        SELECT 1 FROM m_inoutline iol_exist
        WHERE iol_exist.m_inout_id = p_m_inout_id
          AND iol_exist.edi_desadvline_id = edl_lat.edi_desadvline_id
    )
      AND NOT EXISTS (
        SELECT 1
        FROM edi_desadv_pack_item edpi_check
                 JOIN edi_desadv_pack edp_check ON edp_check.edi_desadv_pack_id = edpi_check.edi_desadv_pack_id
            AND edp_check.edi_desadv_id = edl_lat.edi_desadv_id
            AND edp_check.isactive = 'Y'
                 JOIN m_inoutline iol_check ON iol_check.m_inoutline_id = edpi_check.m_inoutline_id
            AND iol_check.m_inout_id = p_m_inout_id
        WHERE edpi_check.edi_desadvline_id = edl_lat.edi_desadvline_id
          AND edpi_check.isactive = 'Y'
    );

    RETURN COALESCE(v_lines_no_pack_json, '[]'::jsonb);
END;
$$ LANGUAGE plpgsql STABLE;

-- 5) Recreate the view to pass io.m_inout_id to both functions
DROP VIEW IF EXISTS M_InOut_Export_EDI_DESADV_JSON_V;
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
               'Packings', "de.metas.edi".get_desadv_packs_json_fn(d.edi_desadv_id, io.m_inout_id),
               'Currency', COALESCE(curr.currency_json, '{}'::jsonb),
               'InvoicableQtyBasedOn', (SELECT edl_ib.invoicableqtybasedon
                                FROM edi_desadvline edl_ib
                                WHERE edl_ib.edi_desadv_id = d.edi_desadv_id
                                  AND edl_ib.isactive = 'Y'
                                ORDER BY edl_ib.line
                                LIMIT 1),
               'DeliveryViaRule', COALESCE(d.deliveryviarule, io.deliveryviarule),
               'DesadvLineWithNoPacking', "de.metas.edi".get_desadv_lines_no_pack_json_fn(d.edi_desadv_id, io.m_inout_id),
               'M_InOut_ID', io.m_inout_id,
               'DatePromised', o.datepromised)
       ) as embedded_json
                         FROM m_inout io
                         LEFT JOIN c_order o ON io.c_order_id = o.c_order_id
                         JOIN edi_desadv d ON io.edi_desadv_id = d.edi_desadv_id
                         JOIN c_bpartner buyer ON d.c_bpartner_id = buyer.c_bpartner_id
                         LEFT JOIN "de.metas.edi".edi_currency_object_v curr ON curr.c_currency_id = d.c_currency_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_buyer ON bp_buyer.c_bpartner_id = d.c_bpartner_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_buyer ON bpl_buyer.c_bpartner_location_id = d.c_bpartner_location_id
                         LEFT JOIN c_bpartner_location bpl_bill_table ON bpl_bill_table.c_bpartner_location_id = d.bill_location_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_bill ON bp_bill.c_bpartner_id = bpl_bill_table.c_bpartner_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_bill ON bpl_bill.c_bpartner_location_id = d.bill_location_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_handover ON bp_handover.c_bpartner_id = d.handover_partner_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_handover ON bpl_handover.c_bpartner_location_id = d.handover_location_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_dropship ON bp_dropship.c_bpartner_id = d.dropship_bpartner_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_dropship ON bpl_dropship.c_bpartner_location_id = d.dropship_location_id
                         LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
                         JOIN "de.metas.edi".edi_bpartner_object_v bp_supplier ON bp_supplier.c_bpartner_id = org.org_bpartner_id
                         JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_supplier ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
                         WHERE io.isactive = 'Y'
  AND io.docstatus IN ('CO', 'CL')
;
