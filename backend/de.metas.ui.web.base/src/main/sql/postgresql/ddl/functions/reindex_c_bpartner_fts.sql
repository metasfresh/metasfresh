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


CREATE OR REPLACE FUNCTION ops.reindex_c_bpartner_fts(p_c_bpartner_id NUMERIC DEFAULT NULL)
    RETURNS void
AS
$$
WITH UserText AS (
    -- Pre-aggregate user data
    SELECT u.c_bpartner_id,
           STRING_AGG(u.name || ' ' || COALESCE(u.firstname, '') || ' ' || COALESCE(u.lastname, '') || ' ' ||
                      COALESCE(u.email, '') || ' ' || COALESCE(u.phone, '') || ' ' || COALESCE(u.mobilephone, ''), ' ') AS text
    FROM ad_user u
    WHERE p_c_bpartner_id IS NULL
       OR u.c_bpartner_id = p_c_bpartner_id
    GROUP BY u.c_bpartner_id),
     LocationText AS (
         -- Pre-aggregate location data
         SELECT bpl.c_bpartner_id,
                STRING_AGG(bpl.name || ' ' || COALESCE(bpl.phone, '') || ' ' || COALESCE(bpl.email, '') || ' ' ||
                           COALESCE(l.city, '') || ' ' || COALESCE(l.postal, '') || ' ' || c.name, ' ') AS text
         FROM c_bpartner_location bpl
                  JOIN c_location l ON bpl.c_location_id = l.c_location_id
                  JOIN c_country c ON l.c_country_id = c.c_country_id
         WHERE p_c_bpartner_id IS NULL
            OR bpl.c_bpartner_id = p_c_bpartner_id
         GROUP BY bpl.c_bpartner_id),
     BPartnerText AS (SELECT bp.c_bpartner_id,
                             (
                                 bp.name || ' ' || COALESCE(bp.name2, '') || ' ' || bp.value || ' ' ||
                                 COALESCE(bp.debtorid::TEXT, '') || ' ' || COALESCE(bp.creditorid::TEXT, '') || ' ' ||
                                 COALESCE(ut.text, '') || ' ' ||
                                 COALESCE(lt.text, '')
                                 ) AS aggregated_text
                      FROM c_bpartner bp
                               LEFT JOIN UserText ut ON bp.c_bpartner_id = ut.c_bpartner_id
                               LEFT JOIN LocationText lt ON bp.c_bpartner_id = lt.c_bpartner_id
                      WHERE (p_c_bpartner_id IS NULL OR bp.c_bpartner_id = p_c_bpartner_id))
-- Perform an "UPSERT" into the FTS table.
INSERT
INTO C_BPartner_FTS (c_bpartner_id, fts_string, fts_document, updated)
SELECT BPartnerText.c_bpartner_id,
       BPartnerText.aggregated_text,
       TO_TSVECTOR(get_fts_config(), BPartnerText.aggregated_text),
       NOW()
FROM BPartnerText
ON CONFLICT (c_bpartner_id) DO UPDATE
    SET fts_document = EXCLUDED.fts_document,
        fts_string   = EXCLUDED.fts_string,
        updated      = NOW();
$$
    LANGUAGE sql
;


COMMENT ON FUNCTION ops.reindex_c_bpartner_fts(NUMERIC) IS 'Rebuilds the FTS index for all C_BPartner records if no ID is provided or updates the index for a single C_BPartner if an ID is provided.'
;
