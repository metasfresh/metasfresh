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


CREATE OR REPLACE FUNCTION ops.reindex_c_bpartner_fts()
    RETURNS void AS $$
BEGIN
    TRUNCATE TABLE C_BPartner_FTS;
    WITH BPartnerText AS (
        SELECT
            bp.c_bpartner_id,
            get_c_bpartner_aggregated_text(bp.c_bpartner_id) AS aggregated_text
        FROM c_bpartner bp
        WHERE bp.c_bpartner_id IS NOT NULL
    )
    INSERT INTO C_BPartner_FTS (c_bpartner_id, fts_string, fts_document, updated)
    SELECT
        c_bpartner_id,
        aggregated_text,
        to_tsvector(get_fts_config(), aggregated_text), -- Reuse the result
        now()
    FROM BPartnerText;
END;
$$ LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.reindex_c_bpartner_fts() IS 'Rebuilds the entire FTS index for all C_BPartner records.'
;

-- SELECT ops.reindex_c_bpartner_fts()
-- ;
