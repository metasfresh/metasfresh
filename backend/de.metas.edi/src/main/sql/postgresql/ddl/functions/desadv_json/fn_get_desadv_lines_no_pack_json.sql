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

-- Function for desadv lines with no pack
CREATE OR REPLACE FUNCTION "de.metas.edi".fn_get_desadv_lines_no_pack_json(p_edi_desadv_id NUMERIC)
    RETURNS JSONB AS $$
DECLARE
    v_lines_no_pack_json JSONB;
BEGIN
    SELECT
        jsonb_agg(
                jsonb_build_object(
                        'EDI_DesadvLine_ID', COALESCE(line_obj_no_pack.desadv_line_object_json, '{}'::jsonb)
                ) ORDER BY edl_lat.line
        )
    INTO v_lines_no_pack_json
    FROM edi_desadvline edl_lat
             LEFT JOIN "de.metas.edi".edi_desadv_line_object_v line_obj_no_pack ON line_obj_no_pack.edi_desadvline_id = edl_lat.edi_desadvline_id
    WHERE edl_lat.edi_desadv_id = p_edi_desadv_id
      AND edl_lat.isactive = 'Y'
      AND NOT EXISTS (
        SELECT 1
        FROM edi_desadv_pack_item edpi_check
                 JOIN edi_desadv_pack edp_check ON edp_check.edi_desadv_pack_id = edpi_check.edi_desadv_pack_id
            AND edp_check.edi_desadv_id = edl_lat.edi_desadv_id
            AND edp_check.isactive = 'Y'
        WHERE edpi_check.edi_desadvline_id = edl_lat.edi_desadvline_id
          AND edpi_check.isactive = 'Y'
    );

    RETURN COALESCE(v_lines_no_pack_json, '[]'::jsonb);
END;
$$ LANGUAGE plpgsql STABLE;
