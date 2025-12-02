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


CREATE OR REPLACE FUNCTION c_country_fts_trigger_function()
    RETURNS trigger AS $$
DECLARE
    bpartner_id_to_update NUMERIC;
BEGIN
    IF (TG_OP = 'UPDATE' AND NEW.name IS DISTINCT FROM OLD.name) THEN
        -- Find all BPartners in the updated country and refresh them.
        FOR bpartner_id_to_update IN
            SELECT bpl.c_bpartner_id
            FROM c_bpartner_location bpl
                     JOIN c_location l ON bpl.c_location_id = l.c_location_id
            WHERE l.c_country_id = OLD.c_country_id
            LOOP
                PERFORM refresh_c_bpartner_fts(bpartner_id_to_update);
            END LOOP;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION c_country_fts_trigger_function() IS 'Triggers the refresh of the FTS index of the C_BPartner table when a C_Country is updated.';

DROP TRIGGER IF EXISTS c_country_fts_trigger ON c_country;
CREATE TRIGGER c_country_fts_trigger AFTER UPDATE ON c_country
    FOR EACH ROW EXECUTE PROCEDURE c_country_fts_trigger_function()
;
