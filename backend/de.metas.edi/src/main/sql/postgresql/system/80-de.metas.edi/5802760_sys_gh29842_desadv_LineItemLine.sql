-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/functions/desadv_json/get_desadv_packs_json_fn.sql
-- https://github.com/metasfresh/me03/issues/29842 — expose pack-item line number inside DesadvLine
-- sub-object as `LineItemLine`, so EDIFACT mappings that read the line context from DesadvLine.*
-- obtain the same value as LineItems[].Line — preventing LIN-vs-packing-allocation mismatches on
-- partial-delivery shipments.
CREATE OR REPLACE FUNCTION "de.metas.edi".get_desadv_packs_json_fn(p_edi_desadv_id NUMERIC, p_m_inout_id NUMERIC)
    RETURNS JSONB
AS
$$
DECLARE
    v_packs_json JSONB;
BEGIN
    WITH pack_item_comp AS (
        -- For each pack_item, resolve its compensation group via:
        -- pack_item.m_inoutline_id -> m_inoutline.c_orderline_id -> c_orderline.c_order_compensationgroup_id
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
             -- The main line in each comp group = the one with the lowest order_line
             SELECT DISTINCT ON (comp_group_id)
                 comp_group_id,
                 edi_desadvline_id AS main_desadvline_id,
                 desadv_line       AS main_desadv_line
             FROM pack_item_comp
             WHERE comp_group_id IS NOT NULL
             ORDER BY comp_group_id, order_line
         ),
         pack_with_role AS (
             -- Enrich each pack_item with its role (main vs sub-article)
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
             -- Identify the main pack per compensation group
             -- (the pack containing the main article item, i.e. lowest order_line).
             -- Uses DISTINCT ON to pick one main pack per group regardless of seqno ordering.
             SELECT DISTINCT ON (comp_group_id)
                 comp_group_id, edi_desadv_pack_id AS main_pack_id
             FROM pack_with_role
             WHERE is_main_article
             ORDER BY comp_group_id, seqno
         ),
         items_assigned AS (
             -- Sub-article items are reassigned to the main pack in their compensation group
             SELECT pwr.*,
                    CASE
                        WHEN pwr.is_sub_article THEN mp.main_pack_id
                        ELSE pwr.edi_desadv_pack_id
                        END AS effective_pack_id
             FROM pack_with_role pwr
                      LEFT JOIN main_packs mp ON mp.comp_group_id = pwr.comp_group_id
         ),
         pack_header AS (
             -- Use the main pack's header info (SSCC, packaging code) for each effective_pack_id
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
        -- Build LineItems JSON per effective pack.
        -- Inlines the edi_desadv_line_object_v logic using ia.m_inoutline_id
        -- to get the correct orderline/order context (avoiding 1:N multiplication).
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
                                               'GTIN_CU', COALESCE(dl.gtin_cu, bpp.gtin, bpp.ean_cu, p.gtin),
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
                                       'DesadvLine', dl.line,
                                       'LineItemLine', ia.pi_line,
                                       'IsDeliveryClosed', COALESCE(diol.desadvlinetotalqtydelivered >= COALESCE(dl.qtyordered_override, dl.qtyordered), true)
                               ),
                               'M_HU_PackagingCode_TU_Text', pc_tu.packagingcode,
                               'Line', ia.pi_line,
                               'GTIN_TU_PackingMaterial', ia.gtin_tu_packingmaterial,
                               'IsSubArticle', ia.is_sub_article,
                               'MainArticleLine',
                               CASE WHEN ia.is_sub_article THEN ia.main_desadv_line END
                       ) ORDER BY ia.is_sub_article, ia.pi_line
               ) AS items_data
        FROM items_assigned ia
                 JOIN edi_desadvline dl ON dl.edi_desadvline_id = ia.edi_desadvline_id
                 JOIN edi_desadv d ON d.edi_desadv_id = dl.edi_desadv_id
                 JOIN m_product p ON p.m_product_id = dl.m_product_id
                 JOIN "de.metas.edi".edi_uom_object_v dl_uom ON dl_uom.c_uom_id = dl.c_uom_id
                 LEFT JOIN "de.metas.edi".edi_uom_object_v inv_uom ON inv_uom.c_uom_id = dl.c_uom_invoice_id
                 LEFT JOIN "de.metas.edi".edi_uom_object_v gw_uom ON gw_uom.c_uom_id = p.grossweight_uom_id
            -- Use pack_item's m_inoutline_id for 1:1 join (not desadvline-level N:M)
                 LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = ia.m_inoutline_id
                 LEFT JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id
                 LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
            -- Junction table for per-shipment-line delivery totals (used for IsDeliveryClosed)
                 LEFT JOIN edi_desadvline_inoutline diol ON diol.m_inoutline_id = ia.m_inoutline_id AND diol.edi_desadvline_id = dl.edi_desadvline_id
            -- BPartner product lookup
                 LEFT JOIN LATERAL (
            SELECT gtin, ean_cu, productno
            FROM c_bpartner_product
            WHERE isactive = 'Y'
              AND m_product_id = p.m_product_id
              AND c_bpartner_id = d.c_bpartner_id
            LIMIT 1
            ) bpp ON TRUE
            -- Packing instruction product lookup
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
