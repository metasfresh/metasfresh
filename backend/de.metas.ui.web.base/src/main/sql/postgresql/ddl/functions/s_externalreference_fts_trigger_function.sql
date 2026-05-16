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


CREATE OR REPLACE FUNCTION s_externalreference_fts_trigger_function()
    RETURNS trigger
AS
$$
DECLARE
    v_c_bpartner_table_id CONSTANT numeric := get_table_id('C_BPartner');
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        IF NEW.record_id IS DISTINCT FROM OLD.record_id THEN
            IF OLD.Type = 'BPartner' AND OLD.referenced_ad_table_id = v_c_bpartner_table_id THEN
                PERFORM ops.reindex_c_bpartner_fts(OLD.record_id);
            END IF;
            IF NEW.Type = 'BPartner' AND NEW.referenced_ad_table_id = v_c_bpartner_table_id THEN
                PERFORM ops.reindex_c_bpartner_fts(NEW.record_id);
            END IF;
        ELSE
            IF NEW.Type = 'BPartner' AND NEW.referenced_ad_table_id = v_c_bpartner_table_id THEN
                PERFORM ops.reindex_c_bpartner_fts(NEW.record_id);
            END IF;
        END IF;
    ELSIF (TG_OP = 'DELETE') THEN
        IF OLD.Type = 'BPartner' AND OLD.referenced_ad_table_id = v_c_bpartner_table_id THEN
            PERFORM ops.reindex_c_bpartner_fts(OLD.record_id);
        END IF;
    ELSIF (TG_OP = 'INSERT') THEN
        IF NEW.Type = 'BPartner' AND NEW.referenced_ad_table_id = v_c_bpartner_table_id THEN
            PERFORM ops.reindex_c_bpartner_fts(NEW.record_id);
        END IF;
    END IF;
    RETURN NULL;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION s_externalreference_fts_trigger_function() IS 'Refresh the C_BPartner_FTS table when a S_ExternalReference record is inserted or updated'
;
