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

CREATE OR REPLACE FUNCTION refresh_c_bpartner_fts(p_bpartner_id NUMERIC)
    RETURNS void AS $$
DECLARE
    aggregated_text TEXT;
BEGIN
    IF p_bpartner_id IS NULL THEN
        RETURN;
    END IF;

    aggregated_text := get_c_bpartner_aggregated_text(p_bpartner_id);

    -- If the aggregation is null (e.g., bpartner was just deleted and this is called from a trigger),
    -- the ON DELETE CASCADE on the foreign key handles the cleanup, so we can just exit.
    IF aggregated_text IS NULL THEN
        RETURN;
    END IF;

    -- Perform an "UPSERT" into the FTS table.
    INSERT INTO C_BPartner_FTS (c_bpartner_id, fts_document, fts_string, updated)
    VALUES (
               p_bpartner_id,
               to_tsvector(get_fts_config(), aggregated_text),
               aggregated_text,
               now()
           )
    ON CONFLICT (c_bpartner_id) DO UPDATE
        SET
            fts_document = EXCLUDED.fts_document,
            fts_string = EXCLUDED.fts_string,
            updated = now();
END;
$$ LANGUAGE plpgsql
;

COMMENT ON FUNCTION refresh_c_bpartner_fts(NUMERIC) IS 'Calculates and upserts the FTS data for a given C_BPartner_ID.'
;
