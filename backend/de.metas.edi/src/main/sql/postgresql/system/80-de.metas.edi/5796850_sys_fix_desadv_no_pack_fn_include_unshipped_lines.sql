-- Fix: include unshipped DESADV lines in DesadvLineWithNoPacking
-- Previously, the function only included lines that had a matching m_inoutline
-- in the current shipment. Lines with QtyDelivered=0 (not shipped at all) were
-- excluded because no m_inoutline exists for them.
-- Now: also include DESADV lines that have no completed shipment line anywhere.
-- Also fixes IsDeliveryClosed for unshipped lines: returns false when there's
-- outstanding ordered qty and no delivery tracking row, instead of defaulting to true.

CREATE OR REPLACE FUNCTION "de.metas.edi".get_desadv_lines_no_pack_json_fn(p_edi_desadv_id NUMERIC, p_m_inout_id NUMERIC)
    RETURNS JSONB AS $$
DECLARE
    v_lines_no_pack_json JSONB;
BEGIN
    SELECT
        jsonb_agg(
                jsonb_build_object(
                        'DesadvLine', COALESCE(line_obj_no_pack.desadv_line_object_json, '{}'::jsonb)
                                          || jsonb_build_object('IsDeliveryClosed',
                                                CASE WHEN diol.desadvlinetotalqtydelivered IS NOT NULL
                                                     THEN diol.desadvlinetotalqtydelivered >= COALESCE(edl_lat.qtyordered_override, edl_lat.qtyordered)
                                                     WHEN COALESCE(edl_lat.qtyordered_override, edl_lat.qtyordered, 0) = 0
                                                     THEN true
                                                     ELSE false
                                                END)
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
      -- Include lines that either have a shipment line in this InOut (shipped but no pack),
      -- OR have no shipment line at all (not shipped — qty delivered = 0, must still appear in DESADV)
      AND (
          EXISTS (
              SELECT 1 FROM m_inoutline iol_exist
              WHERE iol_exist.m_inout_id = p_m_inout_id
                AND iol_exist.edi_desadvline_id = edl_lat.edi_desadvline_id
          )
          OR NOT EXISTS (
              SELECT 1 FROM m_inoutline iol_any
                       JOIN m_inout io_any ON io_any.m_inout_id = iol_any.m_inout_id AND io_any.docstatus IN ('CO', 'CL')
              WHERE iol_any.edi_desadvline_id = edl_lat.edi_desadvline_id
          )
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
