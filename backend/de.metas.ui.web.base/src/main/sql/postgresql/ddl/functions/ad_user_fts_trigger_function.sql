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

CREATE OR REPLACE FUNCTION ad_user_fts_trigger_function()
    RETURNS trigger
AS
$$
BEGIN
    IF TG_OP = 'UPDATE' THEN

        IF OLD.c_bpartner_id IS NOT NULL
            AND OLD.c_bpartner_id IS DISTINCT FROM NEW.c_bpartner_id THEN
            PERFORM ops.reindex_c_bpartner_fts(OLD.c_bpartner_id);
        END IF;

        IF NEW.c_bpartner_id IS NOT NULL THEN
            PERFORM ops.reindex_c_bpartner_fts(NEW.c_bpartner_id);
        END IF;

    ELSIF TG_OP = 'DELETE' AND OLD.c_bpartner_id IS NOT NULL THEN
        PERFORM ops.reindex_c_bpartner_fts(OLD.c_bpartner_id);

    ELSIF TG_OP = 'INSERT' AND NEW.c_bpartner_id IS NOT NULL THEN
        PERFORM ops.reindex_c_bpartner_fts(NEW.c_bpartner_id);

    END IF;

    RETURN NULL;
END;
$$
    LANGUAGE plpgsql;

COMMENT ON FUNCTION ad_user_fts_trigger_function() IS 'Refresh the C_BPartner_FTS table when an AD_User is updated.'
;
