/*
 * #%L
 * de.metas.ui.web.base
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

CREATE OR REPLACE FUNCTION get_c_bpartner_aggregated_text(p_bpartner_id NUMERIC)
    RETURNS TEXT AS $$
DECLARE
    aggregated_text TEXT;
BEGIN
    SELECT
        bp.name || ' ' || bp.value || ' ' || coalesce(bp.debtorid::TEXT, '') || ' ' || coalesce(bp.creditorid::TEXT, '') || ' ' ||
        coalesce((
                     SELECT string_agg(
                                    u.name || ' ' || coalesce(u.firstname, '') || ' ' || coalesce(u.lastname, '') || ' ' ||
                                    coalesce(u.email, '') || ' ' || coalesce(u.phone, '') || ' ' || coalesce(u.mobilephone, ''),
                                    ' ')
                     FROM ad_user u WHERE u.c_bpartner_id = bp.c_bpartner_id
                 ), '') || ' ' ||
        coalesce((
                     SELECT string_agg(
                                    bpl.name || ' ' || coalesce(bpl.phone, '') || ' ' || coalesce(bpl.email, '') || ' ' ||
                                    coalesce(l.city, '') || ' ' || coalesce(l.postal, '') || ' ' || c.name,
                                    ' ')
                     FROM c_bpartner_location bpl
                              JOIN c_location l ON bpl.c_location_id = l.c_location_id
                              JOIN c_country c ON l.c_country_id = c.c_country_id
                     WHERE bpl.c_bpartner_id = bp.c_bpartner_id
                 ), '')
    INTO aggregated_text
    FROM c_bpartner bp
    WHERE bp.c_bpartner_id = p_bpartner_id;

    RETURN aggregated_text;
END;
$$ LANGUAGE plpgsql STABLE
;

COMMENT ON FUNCTION get_c_bpartner_aggregated_text(NUMERIC) IS 'Returns a textual representation of the given bpartner.'
;
