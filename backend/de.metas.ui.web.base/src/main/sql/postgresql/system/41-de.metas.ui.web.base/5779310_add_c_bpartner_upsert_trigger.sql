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
AS $$
BEGIN
    RETURN 'pg_catalog.simple';
END;
$$ LANGUAGE plpgsql IMMUTABLE
;

COMMENT ON FUNCTION get_fts_config() IS 'Returns the FTS configuration to be used for indexing C_BPartner records.'
;


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


CREATE OR REPLACE FUNCTION c_bpartner_fts_trigger_function()
    RETURNS trigger AS $$
BEGIN
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        PERFORM refresh_c_bpartner_fts(NEW.c_bpartner_id);
    END IF;
    -- The DELETE case is handled automatically by the "ON DELETE CASCADE" constraint.
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION c_bpartner_fts_trigger_function() IS 'Refresh the C_BPartner_FTS table when a C_BPartner record is inserted or updated';

DROP TRIGGER IF EXISTS c_bpartner_fts_trigger ON c_bpartner;
CREATE TRIGGER c_bpartner_fts_trigger AFTER INSERT OR UPDATE ON c_bpartner
    FOR EACH ROW EXECUTE PROCEDURE c_bpartner_fts_trigger_function()
;


CREATE OR REPLACE FUNCTION ad_user_fts_trigger_function()
    RETURNS trigger AS $$
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        -- If the bpartner ID differs, refresh both old and new. Otherwise, refresh only once.
        IF NEW.c_bpartner_id IS DISTINCT FROM OLD.c_bpartner_id THEN
            PERFORM refresh_c_bpartner_fts(OLD.c_bpartner_id);
            PERFORM refresh_c_bpartner_fts(NEW.c_bpartner_id);
        ELSE
            PERFORM refresh_c_bpartner_fts(NEW.c_bpartner_id);
        END IF;
    ELSIF (TG_OP = 'DELETE') THEN
        PERFORM refresh_c_bpartner_fts(OLD.c_bpartner_id);
    ELSIF (TG_OP = 'INSERT') THEN
        PERFORM refresh_c_bpartner_fts(NEW.c_bpartner_id);
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql
;

COMMENT ON FUNCTION ad_user_fts_trigger_function() IS 'Refresh the C_BPartner_FTS table when an AD_User is updated.'
;

DROP TRIGGER IF EXISTS ad_user_fts_trigger ON ad_user;
CREATE TRIGGER ad_user_fts_trigger AFTER INSERT OR UPDATE OR DELETE ON ad_user
    FOR EACH ROW EXECUTE PROCEDURE ad_user_fts_trigger_function()
;


CREATE OR REPLACE FUNCTION c_bpartner_location_fts_trigger_function()
    RETURNS trigger AS $$
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        IF NEW.c_bpartner_id IS DISTINCT FROM OLD.c_bpartner_id THEN
            PERFORM refresh_c_bpartner_fts(OLD.c_bpartner_id);
            PERFORM refresh_c_bpartner_fts(NEW.c_bpartner_id);
        ELSE
            PERFORM refresh_c_bpartner_fts(NEW.c_bpartner_id);
        END IF;
    ELSIF (TG_OP = 'DELETE') THEN
        PERFORM refresh_c_bpartner_fts(OLD.c_bpartner_id);
    ELSIF (TG_OP = 'INSERT') THEN
        PERFORM refresh_c_bpartner_fts(NEW.c_bpartner_id);
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql
;

COMMENT ON FUNCTION c_bpartner_location_fts_trigger_function() IS 'Refreshes the C_BPartner_Location_FTS table when a C_BPartner_Location record is inserted, updated or deleted.';

DROP TRIGGER IF EXISTS c_bpartner_location_fts_trigger ON c_bpartner_location;
CREATE TRIGGER c_bpartner_location_fts_trigger AFTER INSERT OR UPDATE OR DELETE ON c_bpartner_location
    FOR EACH ROW EXECUTE PROCEDURE c_bpartner_location_fts_trigger_function()
;


CREATE OR REPLACE FUNCTION c_location_fts_trigger_function()
    RETURNS trigger AS $$
DECLARE
    bpartner_id_to_update NUMERIC;
BEGIN
    IF (TG_OP = 'UPDATE') THEN
        -- Find all BPartners using the updated location and refresh them.
        FOR bpartner_id_to_update IN
            SELECT c_bpartner_id FROM c_bpartner_location WHERE c_location_id = OLD.c_location_id
            LOOP
                PERFORM refresh_c_bpartner_fts(bpartner_id_to_update);
            END LOOP;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql
;

COMMENT ON FUNCTION c_location_fts_trigger_function() IS 'Refresh the FTS index of all BPartners using the given location.'
;

DROP TRIGGER IF EXISTS c_location_fts_trigger ON c_location;
CREATE TRIGGER c_location_fts_trigger AFTER UPDATE ON c_location
    FOR EACH ROW EXECUTE PROCEDURE c_location_fts_trigger_function()
;


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

