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
    RETURNS JSONB AS $$
DECLARE
    v_packs_json JSONB;
BEGIN
    SELECT
        jsonb_agg(
                jsonb_build_object(
                        'SeqNo', edp_lat.seqno,
                        'IPA_SSCC18', edp_lat.ipa_sscc18,
                        'M_HU_PackagingCode_Text', pc_lu.packagingcode,
                        'EDI_Exp_Desadv_Pack_Item', COALESCE(pack_items_lat.items_data, '[]'::jsonb),
                        'GTIN_PackingMaterial', edp_lat.gtin_packingmaterial
                ) ORDER BY edp_lat.seqno
        )
    INTO v_packs_json
    FROM edi_desadv_pack edp_lat
             LEFT JOIN m_hu_packagingcode pc_lu ON pc_lu.m_hu_packagingcode_id = edp_lat.m_hu_packagingcode_id
             LEFT JOIN LATERAL (
        SELECT
            jsonb_agg(
                    jsonb_build_object(
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
        WHERE edpi_lat.edi_desadv_pack_id = edp_lat.edi_desadv_pack_id AND edpi_lat.isactive = 'Y'
        ) pack_items_lat ON true
    WHERE edp_lat.edi_desadv_id = p_edi_desadv_id AND edp_lat.isactive = 'Y';

    RETURN COALESCE(v_packs_json, '[]'::jsonb);
END;
$$ LANGUAGE plpgsql STABLE;
