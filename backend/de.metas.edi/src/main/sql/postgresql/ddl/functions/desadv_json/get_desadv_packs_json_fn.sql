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
                               'DesadvLine', COALESCE(line_obj.desadv_line_object_json, '{}'::jsonb),
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
