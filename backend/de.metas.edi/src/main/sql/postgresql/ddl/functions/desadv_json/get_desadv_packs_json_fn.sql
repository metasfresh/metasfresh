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
