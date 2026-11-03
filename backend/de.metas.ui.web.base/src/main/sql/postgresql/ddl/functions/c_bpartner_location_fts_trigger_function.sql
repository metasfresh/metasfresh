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

CREATE OR REPLACE FUNCTION c_bpartner_location_fts_trigger_function()
    RETURNS trigger
AS
$$
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        IF NEW.c_bpartner_id IS DISTINCT FROM OLD.c_bpartner_id THEN
            PERFORM ops.reindex_c_bpartner_fts(OLD.c_bpartner_id);
            PERFORM ops.reindex_c_bpartner_fts(NEW.c_bpartner_id);
        ELSE
            PERFORM ops.reindex_c_bpartner_fts(NEW.c_bpartner_id);
        END IF;
    ELSIF (TG_OP = 'DELETE') THEN
        PERFORM ops.reindex_c_bpartner_fts(OLD.c_bpartner_id);
    ELSIF (TG_OP = 'INSERT') THEN
        PERFORM ops.reindex_c_bpartner_fts(NEW.c_bpartner_id);
    END IF;
    RETURN NULL;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION c_bpartner_location_fts_trigger_function() IS 'Refreshes the C_BPartner_Location_FTS table when a C_BPartner_Location record is inserted, updated or deleted.'
;
