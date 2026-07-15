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


CREATE OR REPLACE FUNCTION get_fts_config()
    RETURNS regconfig
AS
$$
BEGIN
    RETURN 'pg_catalog.simple';
END;
$$
    LANGUAGE plpgsql IMMUTABLE
;

COMMENT ON FUNCTION get_fts_config() IS 'Returns the FTS configuration to be used for indexing C_BPartner records.'
;


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


CREATE OR REPLACE FUNCTION c_bpartner_fts_trigger_function()
    RETURNS trigger
AS
$$
BEGIN
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        PERFORM ops.reindex_c_bpartner_fts(NEW.c_bpartner_id);
    END IF;
    -- The DELETE case is handled automatically by the "ON DELETE CASCADE" constraint.
    -- CONSTRAINT CBPartner_CBPartnerFTS FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED
    RETURN NULL;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION c_bpartner_fts_trigger_function() IS 'Refresh the C_BPartner_FTS table when a C_BPartner record is inserted or updated'
;


CREATE OR REPLACE FUNCTION ad_user_fts_trigger_function()
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

COMMENT ON FUNCTION ad_user_fts_trigger_function() IS 'Refresh the C_BPartner_FTS table when an AD_User is updated.'
;


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



CREATE OR REPLACE FUNCTION ops.update_c_bpartner_fts_if_active()
    RETURNS void
AS
$$
BEGIN
    DROP TRIGGER IF EXISTS c_bpartner_fts_trigger ON c_bpartner;
    DROP TRIGGER IF EXISTS ad_user_fts_trigger ON ad_user;
    DROP TRIGGER IF EXISTS c_bpartner_location_fts_trigger ON c_bpartner_location;

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

    TRUNCATE TABLE C_BPartner_FTS;
    PERFORM ops.reindex_c_bpartner_fts();
    ANALYSE C_BPartner_FTS;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION ops.update_c_bpartner_fts_if_active() IS 'Rebuilds the entire FTS index for all C_BPartner records and enables the triggers. This is a maintenance operation and not intended for frequent use.'
;
