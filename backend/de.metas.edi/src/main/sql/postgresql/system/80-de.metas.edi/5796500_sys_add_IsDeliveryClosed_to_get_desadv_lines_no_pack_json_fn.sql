-- Add IsDeliveryClosed to DESADV JSON no-pack lines function
-- This flag indicates whether the delivery for a given DESADV line is complete
-- (total qty delivered >= qty ordered), used for partial delivery scenarios.

CREATE OR REPLACE FUNCTION "de.metas.edi".get_desadv_lines_no_pack_json_fn(p_edi_desadv_id NUMERIC, p_m_inout_id NUMERIC)
    RETURNS JSONB AS $$
DECLARE
    v_lines_no_pack_json JSONB;
BEGIN
    SELECT
        jsonb_agg(
                jsonb_build_object(
                        'DesadvLine', COALESCE(line_obj_no_pack.desadv_line_object_json, '{}'::jsonb)
                                          || jsonb_build_object('IsDeliveryClosed', COALESCE(diol.desadvlinetotalqtydelivered >= COALESCE(edl_lat.qtyordered_override, edl_lat.qtyordered), true))
                ) ORDER BY edl_lat.line
        )
    INTO v_lines_no_pack_json
    FROM edi_desadvline edl_lat
             -- Crucial: Ensure v_edi_desadv_line_object is efficient or inline its logic.
             LEFT JOIN "de.metas.edi".edi_desadv_line_object_v line_obj_no_pack ON line_obj_no_pack.edi_desadvline_id = edl_lat.edi_desadvline_id
             -- Junction table for per-shipment-line delivery totals (used for IsDeliveryClosed)
             LEFT JOIN m_inoutline iol_diol ON iol_diol.m_inout_id = p_m_inout_id AND iol_diol.edi_desadvline_id = edl_lat.edi_desadvline_id
             LEFT JOIN edi_desadvline_inoutline diol ON diol.m_inoutline_id = iol_diol.m_inoutline_id AND diol.edi_desadvline_id = edl_lat.edi_desadvline_id
    WHERE edl_lat.edi_desadv_id = p_edi_desadv_id -- Filter by parameter!
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
