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

CREATE OR REPLACE FUNCTION ops.update_c_bpartner_fts_if_active()
    RETURNS void
AS
$$
BEGIN
    DROP TRIGGER IF EXISTS c_bpartner_fts_trigger ON c_bpartner;
    DROP TRIGGER IF EXISTS ad_user_fts_trigger ON ad_user;
    DROP TRIGGER IF EXISTS c_bpartner_location_fts_trigger ON c_bpartner_location;
    DROP TRIGGER IF EXISTS s_externalreference_fts_trigger ON s_externalreference;

    IF (get_sysconfig_value('de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled', 'N') <> 'Y') THEN
        RETURN;
    END IF;

    CREATE TRIGGER c_bpartner_fts_trigger
        AFTER INSERT OR UPDATE
        ON c_bpartner
        FOR EACH ROW
    EXECUTE PROCEDURE c_bpartner_fts_trigger_function();

    CREATE TRIGGER ad_user_fts_trigger
        AFTER INSERT OR UPDATE OR DELETE
        ON ad_user
        FOR EACH ROW
    EXECUTE PROCEDURE ad_user_fts_trigger_function();

    CREATE TRIGGER c_bpartner_location_fts_trigger
        AFTER INSERT OR UPDATE OR DELETE
        ON c_bpartner_location
        FOR EACH ROW
    EXECUTE PROCEDURE c_bpartner_location_fts_trigger_function();

    CREATE TRIGGER s_externalreference_fts_trigger
        AFTER INSERT OR UPDATE OR DELETE
        ON s_externalreference
        FOR EACH ROW
    EXECUTE PROCEDURE s_externalreference_fts_trigger_function();

    -- Reindex all (UPSERT handles existing records without ACCESS EXCLUSIVE lock)
    PERFORM ops.reindex_c_bpartner_fts();
    -- Clean up stale FTS entries whose source records no longer exist
    DELETE FROM C_BPartner_FTS WHERE NOT EXISTS (SELECT 1 FROM C_BPartner WHERE C_BPartner.C_BPartner_ID = C_BPartner_FTS.C_BPartner_ID);
    ANALYSE C_BPartner_FTS;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.update_c_bpartner_fts_if_active() IS 'Rebuilds the entire FTS index for all C_BPartner records and enables the triggers. This is a maintenance operation and not intended for frequent use.'
;

